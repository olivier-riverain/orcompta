package org.or.orcompta.ui.views;


import org.or.orcompta.ui.controls.Controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ViewCreateExercice implements View{

    private Stage stage;
    private Scene scene;
    private Controller controller;
    private View viewOpenExercice;

    private int width = 700;
    private int height = 500;

    private TextField fromjj;
    private TextField frommm;
    private TextField fromaa;
    private TextField tojj;
    private TextField tomm;
    private TextField toaa;

    public ViewCreateExercice(View view) {
        this.viewOpenExercice = view;
    }

    public void initView(Stage stage, Controller controller) {
        this.stage = stage;        
        this.controller = controller;
        this.createView();
    }
    
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setTitle("Créer un nouvel exercice.");        
        this.stage.show();
    }
    
    public void createView() {
        VBox vbox1 = new VBox(10);       
        Label labelTitre = new Label("Entrez les informations relatives à l'exercice.");       
        Font font = Font.font(labelTitre.getFont().getName(), FontWeight.BOLD, 20.0);
        labelTitre.setFont(font);
        HBox hbox0 = new HBox();
        hbox0.getChildren().add(labelTitre);        
        HBox hbox1 = new HBox(5);
        Label labelFromjj = new Label("DU jj ");
        fromjj = new TextField();
        Label labelFrommm = new Label("mm ");
        frommm = new TextField();
        Label labelFromaa = new Label("aa ");
        fromaa = new TextField();
        hbox1.getChildren().addAll(labelFromjj, fromjj, labelFrommm, frommm, labelFromaa, fromaa);        
        HBox hbox2 = new HBox(5);
        Label labelTojj = new Label("AU jj ");
        tojj = new TextField();
        Label labelTommm = new Label("mm ");
        tomm = new TextField();
        Label labelToaa = new Label("aa ");
        toaa = new TextField();
        hbox2.getChildren().addAll(labelTojj, tojj, labelTommm, tomm, labelToaa, toaa);
        Label labelExercicePrecedent = new Label("Exercice précédent");
        ComboBox<String> exercicePrecedent = new ComboBox<>();
        HBox hbox3 = new HBox(5);
        hbox3.getChildren().addAll(labelExercicePrecedent, exercicePrecedent);
        HBox hboxButton = new HBox(5);
        Button buttonValid = new Button("Valider");        
        buttonValid.setOnAction(_ -> valid());
        Button buttonCancel = new Button("Annuler");        
        buttonCancel.setOnAction(_ -> controller.displayView(viewOpenExercice));
        hboxButton.getChildren().addAll(buttonValid, buttonCancel);
        vbox1.getChildren().addAll(hbox0, hbox1, hbox2, hbox3, hboxButton);
        this.scene = new Scene(vbox1, width, height);
    }

    private void valid() {
        
        controller.displayView(viewOpenExercice);
    }

    
}
