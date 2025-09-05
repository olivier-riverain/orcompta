package org.or.orcompta.ui.views;

import java.util.ArrayList;
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

public class ViewOpenCompany implements View{

    private Stage stage;
    private Scene scene;
    private Controller controller;

    private int width = 700;
    private int height = 500;

    private ComboBox<String> companies;   

    private Map<String, ArrayList<String>> companyList;

    public void initView(Stage stage, Controller controller) {
        this.stage = stage;        
        this.controller = controller;
        this.createView();
    }
    
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setTitle("Ouvrir une entreprise.");        
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
        grid.add(companies, 1, 1);
        Button buttonValid = new Button("Valider");
        grid.add(buttonValid, 0, 3);
        buttonValid.setOnAction(_ -> valid());
        Button buttonCancel = new Button("Annuler");
        grid.add(buttonCancel, 1, 3);
        buttonCancel.setOnAction(_ -> controller.displayView());
        this.scene = new Scene(grid, width, height);
    }

    

    private void valid() {        
        String company = companies.getValue();
        String[] tab = company.split("-");
        String idCompany = tab[0];
        controller.loadCompany(idCompany);
        controller.displayView();
    }

    private void initCompanies() {
        companyList = controller.getCompanies();
        for(Map.Entry<String, ArrayList<String>> companyItem : companyList.entrySet()) {
            companies.getItems().add(companyItem.getKey() + "-" + companyItem.getValue().get(0));            
        }        
    }

}
