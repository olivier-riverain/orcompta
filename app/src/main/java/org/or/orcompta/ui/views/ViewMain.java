package org.or.orcompta.ui.views;

import java.util.Vector;

import org.or.orcompta.domain.Account;
import org.or.orcompta.domain.Entry;
import org.or.orcompta.domain.LineEntry;
import org.or.orcompta.ui.controls.Controller;

import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewMain {

    private Stage stage;
    private Scene scene;
    private Controller controller;
    private TableView<LineEntry> entryLines;
    private TableView<Entry> entries;
    private TableView<Account> accounts;
    private TableView<LineEntry> operationsOnAccount;

    private VBox mainVbox;

    private int width = 1300;
    private int height = 700;
    
    public ViewMain() {
        entryLines = new TableView<LineEntry>();
        entries = new TableView<Entry>();
        accounts = new TableView<Account>();
        operationsOnAccount = new TableView<LineEntry>();

    }

    public void initView(Stage stage, Controller controller) {
        this.stage = stage;        
        this.controller = controller;
        this.createView();
    }

    public void show() {
        this.stage.setScene(this.scene);
        this.stage.setTitle("ORCOMPTA");        
        this.stage.show();
    }

    private void createView() {
        mainVbox = new VBox(8);
        Menu menuFile = new Menu("Fichier");
        MenuItem openExercice = new MenuItem("Ouvrir dossier exercice");
        MenuItem saveExercice = new MenuItem("Exporter l'exercice");
        MenuItem importExercice = new MenuItem("Importer un exercice");
        MenuItem quitExercice = new MenuItem("Quitter");
        menuFile.getItems().addAll(openExercice, saveExercice, importExercice, quitExercice);
        Menu menuTraitement = new Menu("Traitement");
        MenuItem closeExercice = new MenuItem("Clôturer cet exercice");
        MenuItem checkExercice = new MenuItem("Vérifier l'équilibre");
        menuTraitement.getItems().addAll(closeExercice, checkExercice);
        Menu menuConfiguration = new Menu("Configuration");
        MenuItem configurationDossier = new MenuItem("Dossier");
        MenuItem configurationExercice = new MenuItem("Exercice");
        MenuItem configurationJournaux = new MenuItem("Journaux");
        menuConfiguration.getItems().addAll(configurationDossier, configurationExercice, configurationJournaux);
        MenuBar menuBar = new MenuBar(menuFile, menuTraitement, menuConfiguration);
        
        TabPane tabPane = new TabPane();
        Tab tabSaisieStandard = new Tab();
        tabSaisieStandard.setClosable(false);
        tabSaisieStandard.setText("Saisie standard");
        VBox vbox1 = new VBox(5);

        HBox line1 = new HBox(5);
        Label labelDatejj = new Label("Date jj: ");
        ComboBox<String> jj = new ComboBox<>();
        for(Integer i=1; i<32; i++) {
            jj.getItems().add(i.toString());
        }
        Label labelDatemm = new Label("mm ");
        ComboBox<String> mm = new ComboBox<>();
        for(Integer i=1; i<13; i++) {
            mm.getItems().add(i.toString());
        }
        Label labelDateaa = new Label("aa ");
        ComboBox<String> aa = new ComboBox<>();
        Vector<String> yearsExercice = controller.retrieveYears();
        for(Integer i=0; i<yearsExercice.size(); i++) {
            aa.getItems().add(yearsExercice.get(i));
        }
        Label labelEcrituren = new Label("Ecriture n° : ");
        Label entryNumber = new Label("0");
        Label labelLignen = new Label("Ligne n° : ");
        Label lineNumber = new Label("0");
        Label labelJournal = new Label("Journal : ");
        ComboBox<String> journal = new ComboBox<>();
        Vector<String> journalList = controller.retrieveJournal();
        for(Integer i=0; i<journalList.size(); i++) {
            journal.getItems().add(journalList.get(i));
        }
        line1.getChildren().addAll(labelDatejj, jj, labelDatemm, mm, labelDateaa, aa, labelEcrituren, entryNumber, labelLignen, lineNumber, labelJournal, journal);
        
        HBox line2 = new HBox(5);
        Label labelCompten = new Label("Numéro de compte : ");
        TextField account = new TextField();
        Label labelLibelle = new Label("Libellé : ");
        TextField libelle = new TextField();
        Label labelMontantDebit = new Label("Montant débit : ");
        TextField montantDebit = new TextField();
        Label labelMontantCredit = new Label("Montant credit : ");
        TextField montantCredit = new TextField();
        Label labelJustificatif = new Label("Numéro du justificatif : ");
        TextField justificatif = new TextField();
        line2.getChildren().addAll(labelCompten, account, labelLibelle, libelle, labelMontantDebit,  montantDebit, labelMontantCredit, montantCredit, labelJustificatif, justificatif);

        Button buttonSaveEntry = new Button("Enregistrer la saisie");

        TableColumn<LineEntry,String> entryColumnDate = new TableColumn<LineEntry,String>("Date");        
        TableColumn<LineEntry,Integer> entryColumnNEntry = new TableColumn<LineEntry,Integer>("Ecriture n°");
        TableColumn<LineEntry,Integer> entryColumnNLine = new TableColumn<LineEntry,Integer>("Ligne n°");
        TableColumn<LineEntry,String> entryColumnJournal = new TableColumn<LineEntry,String>("Journal");
        TableColumn<LineEntry,String> entryColumnAccount = new TableColumn<LineEntry,String>("Compte");
        TableColumn<LineEntry,String> entryColumnLibelle = new TableColumn<LineEntry,String>("Libelle");
        entryColumnLibelle.setMinWidth(200);
        TableColumn<LineEntry,Float> entryColumnAmountDebit = new TableColumn<LineEntry,Float>("Montant du débit");
        entryColumnAmountDebit.setMinWidth(140);
        TableColumn<LineEntry,Float> entryColumnAmountCredit = new TableColumn<LineEntry,Float>("Montant du crédit");
        entryColumnAmountCredit.setMinWidth(140);
        TableColumn<LineEntry,String> entryColumnJustificatif = new TableColumn<LineEntry,String>("Justificatif");
        entryColumnJustificatif.setMinWidth(140);

        
        entryColumnNEntry.setCellValueFactory(
                new PropertyValueFactory<LineEntry,Integer>("idLineEntry")
        );
        entryColumnNLine.setCellValueFactory(
                new PropertyValueFactory<LineEntry,Integer>("idLine")
        );

        this.entryLines.getColumns().addAll(entryColumnDate, entryColumnNEntry, entryColumnNLine, entryColumnJournal, entryColumnAccount, entryColumnLibelle, entryColumnAmountDebit,  entryColumnAmountCredit, entryColumnJustificatif);
                

        HBox line3 = new HBox(5);
        Button newEntry = new Button("Nouvelle écriture");
        Button modifyLine  = new Button("Modifier une ligne");
        Button delLine = new Button("Supprimer une ligne");
        Button addLine = new Button("Ajouter une ligne");
        Label labelTotalDebit = new Label("Total débit : ");
        Label totalDebit = new Label("0");
        Label labelTotalCredit = new Label("Total crédit : ");
        Label totalCredit = new Label("0");
        Label labelSolde = new Label("Solde : ");
        Label solde = new Label("0");
        Button solderEntry = new Button("Solder l'écriture");
        line3.getChildren().addAll(newEntry,  modifyLine, delLine, addLine, labelTotalDebit, totalDebit, labelTotalCredit, totalCredit, labelSolde, solde, solderEntry);

        TableColumn<Entry,String> entriesColumnDate = new TableColumn<Entry,String>("Date");
        TableColumn<Entry,Integer> entriesColumnNEntry = new TableColumn<Entry,Integer>("Ecriture n°");
        TableColumn<Entry,Integer> entriesColumnNLine = new TableColumn<Entry,Integer>("Ligne n°");
        TableColumn<Entry,String> entriesColumnJournal = new TableColumn<Entry,String>("Journal");
        TableColumn<Entry,String> entriesColumnAccount = new TableColumn<Entry,String>("Compte");
        TableColumn<Entry,String> entriesColumnLibelle = new TableColumn<Entry,String>("Libelle");
        entriesColumnLibelle.setMinWidth(200);
        TableColumn<Entry,Float> entriesColumnAmountDebit = new TableColumn<Entry,Float>("Montant du débit");
        entriesColumnAmountDebit.setMinWidth(140);
        TableColumn<Entry,Float> entriesColumnAmountCredit = new TableColumn<Entry,Float>("Montant du crédit");
        entriesColumnAmountCredit.setMinWidth(140);
        TableColumn<Entry,String> entriesColumnJustificatif = new TableColumn<Entry,String>("Justificatif");
        entriesColumnJustificatif.setMinWidth(140);
       
        entriesColumnNEntry.setCellValueFactory(
                new PropertyValueFactory<Entry,Integer>("idEntry")
        );
        
        this.entries.getColumns().addAll(entriesColumnDate, entriesColumnNEntry, entriesColumnNLine, entriesColumnJournal, entriesColumnAccount, entriesColumnLibelle, entriesColumnAmountDebit,  entriesColumnAmountCredit, entriesColumnJustificatif);

        HBox line4 = new HBox(5);
        Button loadEntry = new Button("Charger une écriture");
        Button delEntry = new Button("Supprimer une écriture");
        Button replicateEntry = new Button("Répliquer une écriture");
        line4.getChildren().addAll(loadEntry, delEntry, replicateEntry);

        TableColumn<Account,String> accountsColumnAccount = new TableColumn<Account,String>("Compte");
        TableColumn<Account,String> accountsColumnLibelle = new TableColumn<Account,String>("Libelle");
        accountsColumnLibelle.setMinWidth(200);

        accountsColumnAccount.setCellValueFactory(
                new PropertyValueFactory<Account,String>("idAccount")
        );

        this.accounts.getColumns().addAll(accountsColumnAccount, accountsColumnLibelle);


        HBox line5 = new HBox(5);
        Label labelNumCompte = new Label("Numéro de compte : ");
        TextField compte = new TextField();
        Label labelLibelleCompte = new Label("Libellé : ");
        TextField libelleCompte = new TextField();
        Button saveAccount = new Button("Enregistrer la saisie du compte");
        line5.getChildren().addAll(labelNumCompte, compte, labelLibelleCompte, libelleCompte, saveAccount);

        HBox line6 = new HBox(5);
        Button addAccount = new Button("Ajouter un compte");
        Button modifyAccount  = new Button("Modifier un compte");
        Button delAccount = new Button("Supprimer un compte");
        line6.getChildren().addAll(addAccount, modifyAccount, delAccount);

        vbox1.getChildren().addAll(line1, line2, buttonSaveEntry, entryLines, line3, entries, line4, accounts, line5, line6);
        //vbox1.getChildren().addAll(line1, line2, buttonSaveEntry, entryLines, line3, line4, line5, line6);
        tabSaisieStandard.setContent(vbox1);


        Tab tabDocumentsComptables = new Tab();
        tabDocumentsComptables.setClosable(false);
        tabDocumentsComptables.setText("Documents comptables");
        VBox vbox2 = new VBox(5);
        HBox hbox2_1 = new HBox(5);
        Label labelFromjj = new Label("DU jj ");
        TextField fromjj = new TextField();
        Label labelFrommm = new Label("mm ");
        TextField frommm = new TextField();
        Label labelFromaa = new Label("aa ");
        TextField fromaa = new TextField();
        Label labelTojj = new Label("AU jj ");
        TextField tojj = new TextField();
        Label labelTommm = new Label("mm ");
        TextField tomm = new TextField();
        Label labelToaa = new Label("aa ");
        TextField toaa = new TextField();
        hbox2_1.getChildren().addAll(labelFromjj, fromjj, labelFrommm, frommm, labelFromaa, fromaa, labelTojj, tojj, labelTommm, tomm, labelToaa, toaa);

        HBox hbox2_2 = new HBox(5);
        Button editBalance = new Button("Editer la balance");
        Button editBilanCompteResultat = new Button("Bilan - Compte de résultat");
        hbox2_2.getChildren().addAll(editBalance, editBilanCompteResultat);

        HBox hbox2_3 = new HBox(5); 
        Label labelEditNumCompte = new Label("Numéro de compte : ");
        TextField editCompte = new TextField();
        Label labelEditLibelleCompte = new Label("Libellé : ");
        TextField editLibelleCompte = new TextField();
        Button editAccount = new Button("Editer les opérations sur ce compte");
        hbox2_3.getChildren().addAll(labelEditNumCompte, editCompte, labelEditLibelleCompte, editLibelleCompte,  editAccount);

        TableColumn<LineEntry,String> operationAccountColumnDate = new TableColumn<LineEntry,String>("Date");        
        TableColumn<LineEntry,Integer> operationAccountColumnNEntry = new TableColumn<LineEntry,Integer>("Ecriture n°");
        TableColumn<LineEntry,Integer> operationAccountColumnNLine = new TableColumn<LineEntry,Integer>("Ligne n°");
        TableColumn<LineEntry,String> operationAccountColumnJournal = new TableColumn<LineEntry,String>("Journal");
        TableColumn<LineEntry,String> operationAccountColumnAccount = new TableColumn<LineEntry,String>("Compte");
        TableColumn<LineEntry,String> operationAccountColumnLibelle = new TableColumn<LineEntry,String>("Libelle");
        operationAccountColumnLibelle.setMinWidth(200);
        TableColumn<LineEntry,Float> operationAccountColumnAmountDebit = new TableColumn<LineEntry,Float>("Montant du débit");
        operationAccountColumnAmountDebit.setMinWidth(140);
        TableColumn<LineEntry,Float> operationAccountColumnAmountCredit = new TableColumn<LineEntry,Float>("Montant du crédit");
        operationAccountColumnAmountCredit.setMinWidth(140);
        TableColumn<LineEntry,String> operationAccountColumnJustificatif = new TableColumn<LineEntry,String>("Justificatif");
        operationAccountColumnJustificatif.setMinWidth(140);        
        
        this.operationsOnAccount.getColumns().addAll(operationAccountColumnDate, operationAccountColumnNEntry, operationAccountColumnNLine, operationAccountColumnJournal, operationAccountColumnAccount, operationAccountColumnLibelle, operationAccountColumnAmountDebit, operationAccountColumnAmountCredit, operationAccountColumnJustificatif);

        HBox hbox2_4 = new HBox(5);
        Label labelEditJournal = new Label("Journal : ");
        ComboBox<String> editJournal = new ComboBox<>();       
        for(Integer i=0; i<journalList.size(); i++) {
            editJournal.getItems().add(journalList.get(i));
        }
        Button buttonEditJournal = new Button("Editer le journal");
        hbox2_4.getChildren().addAll(labelEditJournal, editJournal,  buttonEditJournal);

        Button editGrandLivre = new Button("Editer le grand-livre");

        vbox2.getChildren().addAll(hbox2_1, hbox2_2, hbox2_3, operationsOnAccount, hbox2_4, editGrandLivre);
        tabDocumentsComptables.setContent(vbox2);
        
        
        tabPane.getTabs().addAll(tabSaisieStandard, tabDocumentsComptables);

        mainVbox.getChildren().addAll(menuBar, tabPane);

        this.scene = new Scene(this.mainVbox, width, height);
    }
    
}
