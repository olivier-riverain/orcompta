package org.or.orcompta.ui.views;

import java.util.Map;

import org.or.orcompta.ui.controls.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewOpenExercice implements View{

    private Stage stage;
    private Scene scene;
    private Controller controller;

    private int width = 700;
    private int height = 500;

    private ComboBox<String> companies;
    private ComboBox<String> exercices;

    private Map<String, String> companyList;

    public void initView(Stage stage, Controller controller) {
        this.stage = stage;        
        this.controller = controller;
        this.createView();
    }
    
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setTitle("Créer/ouvrir un nouvel exercice.");        
        this.stage.show();
    }
    
    public void createView() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label labelName = new Label("Entreprise");
        grid.add(labelName, 0, 1);
        companies = new ComboBox<>();
        initCompanies();
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {                   
                    initExercices();
                }
        };
        companies.setOnAction(event);;
        grid.add(companies, 1, 1);
        Label labelExercice = new Label("Exercice");
        grid.add(labelExercice, 0, 2);
        exercices = new ComboBox<>();        
        grid.add(exercices, 1, 2);
        Button buttonCreateExercice = new Button("Créer un exercice");
        grid.add(buttonCreateExercice, 3, 2);
        buttonCreateExercice.setOnAction(_ -> controller.displayCreateExercice());
        Button buttonValid = new Button("Valider");
        grid.add(buttonValid, 0, 12);
        buttonValid.setOnAction(_ -> valid());
        Button buttonCancel = new Button("Annuler");
        grid.add(buttonCancel, 1, 12);
        buttonCancel.setOnAction(_ -> controller.displayView());
        this.scene = new Scene(grid, width, height);
    }

    private void valid() {
        
        controller.displayView();
    }

    private void initCompanies() {
        companyList = controller.getCompanies();
        for(Map.Entry<String, String> companyItem : companyList.entrySet()) {
            companies.getItems().add(companyItem.getKey() + "-" + companyItem.getValue());            
        }        
    }

    private void initExercices() {
        String company = companies.getValue();
        String[] tab = company.split("-");
        Map<String, String> exercicesList = this.controller.getExercices(tab[0]);
        for(Map.Entry<String, String> exerciceItem : exercicesList.entrySet()) {
            exercices.getItems().add(exerciceItem.getKey() + "-" + exerciceItem.getValue());            
        } 
    }
    
}
