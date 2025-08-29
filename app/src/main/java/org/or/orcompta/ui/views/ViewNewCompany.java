package org.or.orcompta.ui.views;

import java.io.File;
import java.io.IOException;

import org.or.orcompta.ui.controls.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ViewNewCompany implements View{
    
    private Stage stage;
    private Scene scene;
    private Controller controller;

    private int width = 700;
    private int height = 500;

    private TextField name;
    private TextField numero;
    private TextField address;
    private TextField address2;
    private TextField postalCode;
    private TextField city;
    private ComboBox<String> legalForm;
    private TextField siret;
    private TextField naf;
    private TextField shareCapital;
    private TextField saveDirectory;
    
    @Override
    public void initView(Stage stage, Controller controller) {
        this.stage = stage;        
        this.controller = controller;
        this.createView();
    }

    @Override
    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setTitle("Créer une nouvelle entreprise.");        
        this.stage.show();
    }

    @Override
    public void createView() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);        
        Label labelTitre01 = new Label("Entrez les informations relatives");
        Label labelTitre02 = new Label("à votre entreprise.");
        Font font = Font.font(labelTitre01.getFont().getName(), FontWeight.BOLD, 20.0);
        labelTitre01.setFont(font);
        labelTitre02.setFont(font);
        grid.add(labelTitre01, 0, 0);
        grid.add(labelTitre02, 1, 0);
        Label labelName = new Label("Raison sociale");
        grid.add(labelName, 0, 1);
        name = new TextField();
        grid.add(name, 1, 1);
        Label labelNumero = new Label("N°");
        grid.add(labelNumero, 0, 2);
        numero = new TextField();
        grid.add(numero, 1, 2);
        Label labelAddress = new Label("Adresse:");
        grid.add(labelAddress, 0, 3);
        address = new TextField();
        grid.add(address, 1, 3);
        Label labelAddress2 = new Label("Complément d'adresse");
        grid.add(labelAddress2, 0, 4);
        address2 = new TextField();
        grid.add(address2, 1, 4);
        Label labelPostalCode = new Label("Code Postal");
        grid.add(labelPostalCode, 0, 5);
        postalCode = new TextField();
        grid.add(postalCode, 1, 5);
        Label labelCity = new Label("Ville");
        grid.add(labelCity, 0, 6);
        city = new TextField();
        grid.add(city, 1, 6);
        Label labelLegalForm = new Label("Forme juridique");
        grid.add(labelLegalForm, 0, 7);
        legalForm = new ComboBox<>();
        legalForm.getItems().addAll("SARL", "EURL", "SA", "SAS");
        grid.add(legalForm, 1, 7);
        Label labelSiret = new Label("SIRET");
        grid.add(labelSiret, 0, 8);
        siret = new TextField();
        grid.add(siret, 1, 8);
        Label labelNaf = new Label("Code NAF");
        grid.add(labelNaf, 0, 9);
        naf = new TextField();
        grid.add(naf, 1, 9);
        Label labelShareCapital = new Label("Montant du capital social");
        grid.add(labelShareCapital, 0, 10);
        shareCapital = new TextField();
        grid.add(shareCapital, 1, 10);
        DirectoryChooser directory = new DirectoryChooser();
        Label labelDirectory = new Label("Sélectionnez un répertoire de sauvegarde.");
        grid.add(labelDirectory, 0, 11);
        saveDirectory = new TextField();
        grid.add(saveDirectory, 1, 11);
        Button buttonDirectory = new Button("Select");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {                   
                    File file = directory.showDialog(stage);
                    if (file != null) {
                        saveDirectory.setText(file.getAbsolutePath());
                    }
                }
        };
        buttonDirectory.setOnAction(event);
        grid.add(buttonDirectory, 2, 11);        
        Button buttonValid = new Button("Valider");
        grid.add(buttonValid, 0, 12);
        buttonValid.setOnAction(_ -> valid());
        Button buttonCancel = new Button("Annuler");
        grid.add(buttonCancel, 1, 12);
        buttonCancel.setOnAction(_ -> controller.displayView());
        this.scene = new Scene(grid, width, height);
    }

    private void valid() {
        try {
            controller.createNewCompany(name.getText(), numero.getText(), address.getText(), address2.getText(), postalCode.getText(), city.getText(), legalForm.getValue(), siret.getText(), naf.getText(), Double.parseDouble(shareCapital.getText()), saveDirectory.getText());
        } catch (NumberFormatException e) {            
            e.printStackTrace();
        } catch (IOException e) {            
            e.printStackTrace();
        }
        controller.displayView();
    }


}
