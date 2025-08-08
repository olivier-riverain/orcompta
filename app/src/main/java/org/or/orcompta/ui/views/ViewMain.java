package org.or.orcompta.ui.views;

import java.util.Collection;
import java.util.Vector;

import org.or.orcompta.domain.Account;
import org.or.orcompta.domain.DateEntry;
import org.or.orcompta.domain.Entry;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.LineEntry;
import org.or.orcompta.domain.LineEntryId;
import org.or.orcompta.ui.controls.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<String> jj;
    private ComboBox<String> mm;
    private ComboBox<String> aa;
    private ComboBox<String> journal;
    private Label entryNumber;
    private Label lineNumber;
    private Label totalDebit;
    private Label totalCredit;
    private Label solde;
    private TableView<LineEntry> tableViewLinesEntry;
    private TableView<Entry> tableViewEntries;
    private TableView<Account> tableViewAccounts;
    private TableView<LineEntry> operationsOnAccount;
    private TextField account;
    private TextField libelle;
    private TextField justificatif;
    private TextField montantDebit;
    private TextField montantCredit;
    private ObservableList<Entry> listOfEntries;
    private ObservableList<Account> listOfAccounts;

    private VBox mainVbox;

    private int width = 1300;
    private int height = 700;
    
    public ViewMain() {
        tableViewLinesEntry = new TableView<LineEntry>();
        tableViewEntries = new TableView<Entry>();
        tableViewAccounts = new TableView<Account>();
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
        jj = new ComboBox<>();
        for(Integer i=1; i<32; i++) {
            jj.getItems().add(i.toString());
        }
        Label labelDatemm = new Label("mm ");
        mm = new ComboBox<>();
        for(Integer i=1; i<13; i++) {
            mm.getItems().add(i.toString());
        }
        Label labelDateaa = new Label("aa ");
        aa = new ComboBox<>();
        Vector<String> yearsExercice = controller.retrieveYears();
        for(Integer i=0; i<yearsExercice.size(); i++) {
            aa.getItems().add(yearsExercice.get(i));
        }
        Label labelEcrituren = new Label("Ecriture n° : ");
        this.entryNumber = new Label("0");
        Label labelLignen = new Label("Ligne n° : ");
        this.lineNumber = new Label("0");
        Label labelJournal = new Label("Journal : ");
        journal = new ComboBox<>();
        Vector<String> journalList = controller.retrieveJournal();
        for(Integer i=0; i<journalList.size(); i++) {
            journal.getItems().add(journalList.get(i));
        }
        Label labelJustificatif = new Label("Numéro du justificatif : ");
        justificatif = new TextField();
        line1.getChildren().addAll(labelDatejj, jj, labelDatemm, mm, labelDateaa, aa, labelEcrituren, this.entryNumber, labelLignen, this.lineNumber, labelJournal, journal, labelJustificatif, justificatif);
        
        HBox line2 = new HBox(5);
        Label labelCompten = new Label("Numéro de compte : ");
        account = new TextField();
        Label labelLibelle = new Label("Libellé : ");
        libelle = new TextField();
        Label labelMontantDebit = new Label("Montant débit : ");
        montantDebit = new TextField();
        Label labelMontantCredit = new Label("Montant credit : ");
        montantCredit = new TextField();        
        line2.getChildren().addAll(labelCompten, account, labelLibelle, libelle, labelMontantDebit,  montantDebit, labelMontantCredit, montantCredit);

        Button buttonSaveEntry = new Button("Enregistrer la saisie");
        buttonSaveEntry.setOnAction(e -> saveLineEntry());

       // TableColumn<LineEntry,String> entryColumnDate = new TableColumn<LineEntry,String>("Date");        
        //TableColumn<Entry,EntryId> entryColumnNEntry = new TableColumn<Entry,EntryId>("Ecriture n°");
        TableColumn<LineEntry,Integer> entryColumnNLine = new TableColumn<LineEntry,Integer>("Ligne n°");
       // TableColumn<LineEntry,String> entryColumnJournal = new TableColumn<LineEntry,String>("Journal");
        TableColumn<LineEntry,String> entryColumnAccount = new TableColumn<LineEntry,String>("Compte");
        TableColumn<LineEntry,String> entryColumnLibelle = new TableColumn<LineEntry,String>("Libelle");
        entryColumnLibelle.setMinWidth(200);
        TableColumn<LineEntry,Float> entryColumnAmountDebit = new TableColumn<LineEntry,Float>("Montant du débit");
        entryColumnAmountDebit.setMinWidth(140);
        TableColumn<LineEntry,Float> entryColumnAmountCredit = new TableColumn<LineEntry,Float>("Montant du crédit");
        entryColumnAmountCredit.setMinWidth(140);
        //TableColumn<LineEntry,String> entryColumnJustificatif = new TableColumn<LineEntry,String>("Justificatif");
        //entryColumnJustificatif.setMinWidth(140);

        
       /* entryColumnNEntry.setCellValueFactory(
                new PropertyValueFactory<Entry,EntryId>("idEntry")
        );*/
        entryColumnNLine.setCellValueFactory(
                new PropertyValueFactory<LineEntry,Integer>("idLineEntry")
        );
        entryColumnAccount.setCellValueFactory(
                new PropertyValueFactory<LineEntry, String>("account")
        );
        /*entryColumnLibelle.setCellValueFactory(
                new PropertyValueFactory<LineEntry, String>("libelle")
        );*/
        entryColumnAmountDebit.setCellValueFactory(
                new PropertyValueFactory<LineEntry,Float>("amountDebit")
        );
        entryColumnAmountCredit.setCellValueFactory(
                new PropertyValueFactory<LineEntry,Float>("amountCredit")
        );

        this.tableViewLinesEntry.getColumns().addAll(entryColumnNLine, entryColumnAccount, entryColumnLibelle, entryColumnAmountDebit,  entryColumnAmountCredit);
                

        HBox line3 = new HBox(5);
        Button newEntry = new Button("Nouvelle écriture");
        newEntry.setOnAction(e -> controller.computeIdNewEntry());
        Button saveEntry = new Button("Enregistrer l'écriture");
        saveEntry.setOnAction(e -> saveNewEntry());
        Button modifyLine  = new Button("Modifier une ligne");
        Button delLine = new Button("Supprimer une ligne");
        Button addLine = new Button("Ajouter une ligne");
        Label labelTotalDebit = new Label("Total débit : ");
        totalDebit = new Label("0");
        Label labelTotalCredit = new Label("Total crédit : ");
        totalCredit = new Label("0");
        Label labelSolde = new Label("Solde : ");
        solde = new Label("0");
        Button solderEntry = new Button("Solder l'écriture");
        line3.getChildren().addAll(newEntry, saveEntry, modifyLine, delLine, addLine, labelTotalDebit, totalDebit, labelTotalCredit, totalCredit, labelSolde, solde, solderEntry);

        
        TableColumn<Entry,DateEntry> entriesColumnDate = new TableColumn<Entry,DateEntry>("Date");
        TableColumn<Entry,EntryId> entriesColumnNEntry = new TableColumn<Entry,EntryId>("Ecriture n°");
        TableColumn<Entry,Integer> entriesColumnNLine = new TableColumn<Entry,Integer>("Nombre de lignes");
        entriesColumnNLine.setMinWidth(140);
        TableColumn<Entry,String> entriesColumnJournal = new TableColumn<Entry,String>("Journal");
        //TableColumn<Entry,String> entriesColumnAccount = new TableColumn<Entry,String>("Compte");
        //TableColumn<Entry,String> entriesColumnLibelle = new TableColumn<Entry,String>("Libelle");
        //entriesColumnLibelle.setMinWidth(200);
        TableColumn<Entry,Float> entriesColumnAmountDebit = new TableColumn<Entry,Float>("Montant du débit");
        entriesColumnAmountDebit.setMinWidth(140);
        TableColumn<Entry,Float> entriesColumnAmountCredit = new TableColumn<Entry,Float>("Montant du crédit");
        entriesColumnAmountCredit.setMinWidth(140);
        TableColumn<Entry,String> entriesColumnJustificatif = new TableColumn<Entry,String>("Justificatif");
        entriesColumnJustificatif.setMinWidth(140);
       
        entriesColumnDate.setCellValueFactory(new PropertyValueFactory<Entry,DateEntry>("date"));
        entriesColumnNEntry.setCellValueFactory(new PropertyValueFactory<Entry,EntryId>("idEntry"));
        entriesColumnNLine.setCellValueFactory(new PropertyValueFactory<Entry,Integer>("nbLinesEntry"));
        entriesColumnJournal.setCellValueFactory(new PropertyValueFactory<Entry,String>("journal"));
        //entriesColumnAccount.setCellValueFactory(new PropertyValueFactory<Entry,String>("account"));
        //entriesColumnLibelle.setCellValueFactory(new PropertyValueFactory<Entry,String>("libelle"));
        entriesColumnAmountDebit.setCellValueFactory(new PropertyValueFactory<Entry,Float>("amountDebit"));
        entriesColumnAmountCredit.setCellValueFactory(new PropertyValueFactory<Entry,Float>("amountCredit"));
        entriesColumnJustificatif.setCellValueFactory(new PropertyValueFactory<Entry,String>("voucher"));

        this.tableViewEntries.getColumns().addAll(entriesColumnDate, entriesColumnNEntry, entriesColumnNLine, entriesColumnJournal, entriesColumnAmountDebit,  entriesColumnAmountCredit, entriesColumnJustificatif);

        HBox line4 = new HBox(5);
        Button loadEntry = new Button("Charger une écriture");
        Button delEntry = new Button("Supprimer une écriture");
        Button replicateEntry = new Button("Répliquer une écriture");
        line4.getChildren().addAll(loadEntry, delEntry, replicateEntry);

        TableColumn<Account,String> accountsColumnAccount = new TableColumn<Account,String>("Compte");
        TableColumn<Account,String> accountsColumnLibelle = new TableColumn<Account,String>("Libelle");
        accountsColumnLibelle.setMinWidth(200);

        accountsColumnAccount.setCellValueFactory(
                new PropertyValueFactory<Account,String>("name")
        );
        accountsColumnLibelle.setCellValueFactory(
                new PropertyValueFactory<Account,String>("description")
        );

        this.tableViewAccounts.getColumns().addAll(accountsColumnAccount, accountsColumnLibelle);


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

        vbox1.getChildren().addAll(line1, line2, buttonSaveEntry, tableViewLinesEntry, line3, tableViewEntries, line4, tableViewAccounts, line5, line6);        
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

    public void initTabViewEntries() {
        //Collection<Entry> listOfEntries = this.controller.getEntriesInExercice(this.controller.getIdCompany(), this.controller.getIdExercice());
       // this.tableViewEntries.getItems().addAll(listOfEntries);
       listOfEntries = FXCollections.observableArrayList(this.controller.getEntriesInExercice(this.controller.getIdCompany(), this.controller.getIdExercice()));
       this.tableViewEntries.setItems(listOfEntries);
       System.out.println("initTabViewEntries listOfEntries.size() = " + listOfEntries.size());
       System.out.println("initTabViewEntries + this.tableViewEntries.getItems().size() = " + this.tableViewEntries.getItems().size());   
    }

    public void addEntryInTabViewEntries(Entry newEntry) {        
        this.listOfEntries.add(newEntry);
        System.out.println("addEntryInTabViewEntries listOfEntries.size() = " + listOfEntries.size());
        System.out.println("addEntryInTabViewEntries + this.tableViewEntries.getItems().size() = " + this.tableViewEntries.getItems().size());   
    }

    public void initTabViewLinesEntry() {
        Collection<LineEntry> listOfLinesEntry = this.controller.getLInesEntryInEntry(this.controller.getIdCompany(), this.controller.getIdExercice(), this.controller.getIdEntry());
        this.tableViewLinesEntry.getItems().addAll(listOfLinesEntry);        
    }

    public void addLineEntryInTabViewLinesEntry(LineEntry newLineEntry) {        
        System.out.println("addLineEntryInTabViewLinesEntry " + newLineEntry);
        this.tableViewLinesEntry.getItems().add(newLineEntry);
    }

    public void resetTabViewLinesEntry() {
        this.tableViewLinesEntry.getItems().clear();
    }

    public void initTabViewAccounts() {
        listOfAccounts = FXCollections.observableArrayList(this.controller.getAccountsInExercice(this.controller.getIdCompany(), this.controller.getIdExercice()));
       this.tableViewAccounts.setItems(listOfAccounts);     
    }

    public void addAccountInTabViewAccounts(Account newAccount) {        
        System.out.println("addAccountInTabViewLinesAccount " + newAccount);
        this.tableViewAccounts.getItems().add(newAccount);
    }

 
    private void majEntryNumber(EntryId idNewEntry) {       
        entryNumber.setText(idNewEntry.toString());        
    }

    private void majLineEntryNumber(EntryId idNewLineEntry) {       
        lineNumber.setText(idNewLineEntry.toString());        
    }

    private void resetInputTextField(TextField inputTextField, String input) {
      inputTextField.setText(input);
    }

    private void resetInputLabel(Label inputLabel, String input) {
        inputLabel.setText(input);
    }

    public void newEntryNumber(EntryId idNewEntry) {
        majEntryNumber(idNewEntry);
        resetEntry();
        resetLineEntry();        
    }

    private void resetEntry() {        
        resetInputTextField(justificatif, "");
        resetInputLabel(totalDebit, "0");
        resetInputLabel(totalCredit, "0");
        resetInputLabel(solde, "0");
    }

    private void saveNewEntry() {
        this.controller.saveNewEntry();
        controller.computeIdNewEntry();
        resetAfterSaveNewEntry();
        resetTabViewLinesEntry();

    }

    public void newLineEntryNumber(LineEntryId idNewLineEntry) {
        lineNumber.setText(idNewLineEntry.toString());            
    }

    private void resetLineEntry() {
        resetInputTextField(account, "");
        resetInputTextField(libelle, "");
        resetInputTextField(montantDebit, "0");
        resetInputTextField(montantCredit, "0");       
    }

    private void updateAfterNewLineEntry() {
        totalDebit.setText(Double.toString(controller.getEntryTotalDebit()));
        totalCredit.setText(Double.toString(controller.getEntryTotalCredit()));
        solde.setText(Double.toString(controller.getEntryTotalDebit() - controller.getEntryTotalCredit()));
    }

    private void resetAfterSaveNewEntry() {
        totalDebit.setText("0");
        totalCredit.setText("0");
        solde.setText("0");
        lineNumber.setText("0");
        justificatif.setText("");
    }

    private void saveLineEntry() {        
        this.controller.saveLineEntry(this.entryNumber.getText(), jj.getValue(), mm.getValue(), aa.getValue(), journal.getValue(), justificatif.getText(), account.getText(), Double.parseDouble(montantDebit.getText()), Double.parseDouble(montantCredit.getText()));
        resetLineEntry();
        updateAfterNewLineEntry();
        this.controller.computeIdNewLineEntry();
        account.requestFocus();
    }

    
    
}
