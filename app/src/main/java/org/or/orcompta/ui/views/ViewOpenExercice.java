package org.or.orcompta.ui.views;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.or.orcompta.ui.controls.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewOpenExercice implements View{

    private Stage stage;
    private Scene scene;
    private Controller controller;

    private int width = 700;
    private int height = 500;
    private final String noExercice = new String("Aucun exercice");

    
    private ComboBox<String> exercices;
    private Label companyOpened;

    public void initView(Stage stage, Controller controller) {
        this.stage = stage;        
        this.controller = controller;
        this.createView();
    }
    
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setTitle("Créer/ouvrir un nouvel exercice.");
        companyOpened.setText(controller.getNameCompany());
        initExercices();
        this.stage.show();
    }
    
    public void createView() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label labelName = new Label("Entreprise");
        grid.add(labelName, 0, 1);
        companyOpened = new Label("");        
        grid.add(companyOpened, 1, 1);
        Label labelExercice = new Label("Exercice");
        grid.add(labelExercice, 0, 2);
        exercices = new ComboBox<>();        
        grid.add(exercices, 1, 2);
        Button buttonCreateExercice = new Button("Créer un exercice");
        grid.add(buttonCreateExercice, 3, 2);
        buttonCreateExercice.setOnAction(_ -> displayCreateExercice());
        Button buttonValid = new Button("Valider");
        grid.add(buttonValid, 0, 12);
        buttonValid.setOnAction(_ -> valid());
        Button buttonCancel = new Button("Annuler");
        grid.add(buttonCancel, 1, 12);
        buttonCancel.setOnAction(_ -> controller.displayView());
        this.scene = new Scene(grid, width, height);
    }

    private void displayCreateExercice() {
        String [] companySelected = companyOpened.getText().split(" - ");
        String idCompany = companySelected[0];
        String company = companySelected[1];
        controller.displayCreateExercice(idCompany, company);
    }

    private void valid() {        
        if(exercices.getSelectionModel().getSelectedItem().equals(noExercice)) {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setContentText("Aucun exercice n'a été sélectionné."); 
            final Optional<ButtonType> result = alertMessage.showAndWait(); 
            result.ifPresent(button -> { 
                if (button == ButtonType.OK) { 
                    return;
                } 
            });
        } else {
            controller.displayView();
        }
        
    }    

    private void initExercices() {        
        exercices.getItems().clear();
        String[] tab = companyOpened.getText().split(" - ");
        Map<String, String> exercicesList = this.controller.getExercices(tab[0]);
        if(exercicesList.size() > 0) {
            for(Map.Entry<String, String> exerciceItem : exercicesList.entrySet()) {
                exercices.getItems().add(exerciceItem.getKey() + "-" + exerciceItem.getValue());            
            }
        } else {
            exercices.getItems().add(noExercice);
        }
        exercices.getSelectionModel().select(0);
    }
    
}
