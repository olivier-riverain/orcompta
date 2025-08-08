package org.or.orcompta.ui.controls;

import java.util.Collection;
import java.util.Vector;

import org.or.orcompta.application.CompanyServices;
import org.or.orcompta.domain.Account;
import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.Entry;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.ExerciceId;
import org.or.orcompta.domain.LineEntry;
import org.or.orcompta.domain.LineEntryId;
import org.or.orcompta.ui.models.Model;
import org.or.orcompta.ui.views.ViewMain;
import javafx.stage.Stage;


public class Controller {

    private ViewMain viewMain;
    private CompanyServices companyServices;
    private Model model;
    
    public Controller(Stage stage) {
        this.companyServices = new CompanyServices(); 
        this.model = new Model();
        this.viewMain = new ViewMain();
        this.initView(stage, this.viewMain);                       
    }

    private void initView(Stage stage, ViewMain viewMain) {
        viewMain.initView(stage, this);
    }

    public void run() {
        viewMain.show();
    }

    public CompanyId getIdCompany() {
        return this.model.getIdCompany();
    }

    public ExerciceId getIdExercice() {
        return this.model.getIdExercice();
    }

    public EntryId getIdEntry() {
        return this.model.getIdEntry();
    }

    public double getEntryTotalDebit() {        
        return this.companyServices.getEntryTotalDebit(this.model.getIdCompany(), this.model.getIdExercice(), this.model.getIdEntry());
    }

    public double getEntryTotalCredit() {
        return this.companyServices.getEntryTotalCredit(this.model.getIdCompany(), this.model.getIdExercice(), this.model.getIdEntry());
    }

    public Vector<String> retrieveYears() {
        Vector<String> years = new Vector<>();
        // penser à récupérer les années d'exercice dans le modèle à partir des info de l'exercice encours
        years.add("2024");
        years.add("2025"); 
        return years;
    }

    public Vector<String> retrieveJournal() {
        Vector<String> journals = new Vector<>();
        // penser à récupérer les journaux d'exercice dans le modèle à partir des info de l'exercice encours
        journals.add("AC - Achats");
        journals.add("VT - Ventes"); 
        return journals;
    }

    public CompanyId createNewCompany(String name, String numero, String address, String address2, String postalCode, String city, String legalForm, String siret, String naf, Double shareCapital) {
        CompanyId idCompany = companyServices.createNewCompany(name, numero, address, address2, postalCode, city, legalForm, siret, naf, shareCapital);
        model.setIdCompany(idCompany);
        return idCompany; 
    }

    public ExerciceId createNewExercice(CompanyId idCompany, String beginjj,  String beginmm,  String beginyy, String endjj, String endmm, String endyy) {
        ExerciceId idExercice = companyServices.createNewExercice(idCompany, beginjj, beginmm, beginyy, endjj, endmm, endyy);        
        model.setIdExercice(idExercice);
        return idExercice;
    }
    
    public EntryId createNewEntry(String jj, String mm, String yy, String journal, String voucher) {
        CompanyId idCompany = this.model.getIdCompany();
        ExerciceId idExercice = this.model.getIdExercice();
        EntryId idEntry = companyServices.createNewEntry(idCompany, idExercice, jj, mm, yy, journal, voucher);        
        model.setIdEntry(idEntry);
        model.setIdLineEntry(companyServices.resetIdLineEntry(idCompany, idExercice, idEntry));               
        return idEntry;
    }

    public void saveNewEntry() {
        CompanyId idCompany = this.model.getIdCompany();
        ExerciceId idExercice = this.model.getIdExercice();
        EntryId idEntry = this.model.getIdEntry();
        Entry newEntry = companyServices.getEntry(idCompany, idExercice, idEntry);
        this.addEntryInTabViewEntries(newEntry); 
    }

    public void saveLineEntry(String idEntry, String jj, String mm, String yy, String journal, String voucher, String account, double amountDebit, double amountCredit) {
        System.out.println("saveLineEntry this.model.getIdEntry().toString() = " + this.model.getIdEntry().toString() + " idEntry = " + idEntry);
        if(!(this.model.getIdEntry().toString().equals(idEntry))) {
            System.out.println("saveLineEntry " + this.model.getIdEntry().toString() + " != " + idEntry);
            EntryId idnewEntry = createNewEntry(jj, mm, yy, journal, voucher);
        }
        LineEntryId idNewLineEntry = createNewLineEntry(account, amountDebit, amountCredit);
    }

    public LineEntryId createNewLineEntry(String account, double amountDebit, double amountCredit) {
        CompanyId idCompany = this.model.getIdCompany();
        ExerciceId idExercice = this.model.getIdExercice();
        EntryId idEntry = this.model.getIdEntry();
        LineEntryId idLineEntry = companyServices.createNewLineEntry(idCompany, idExercice, idEntry, account, amountDebit, amountCredit);        
        model.setIdLineEntry(idLineEntry);
        LineEntry newLineEntry = companyServices.getLineEntry(idCompany, idExercice, idEntry, idLineEntry);
        this.addEntryInTabViewLinesEntry(newLineEntry); 
        return idLineEntry;
    }

    
    public Collection<Entry> getEntriesInExercice(CompanyId idCompany, ExerciceId idExercice) {
        Collection<Entry> entries = companyServices.getEntriesInExercice(idCompany, idExercice);
        return entries;
    }

    public Collection<LineEntry> getLInesEntryInEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Collection<LineEntry> linesEntry = companyServices.getLInesEntryInEntry(idCompany, idExercice, idEntry);
        return linesEntry;
    }

    public Collection<Account> getAccountsInExercice(CompanyId idCompany, ExerciceId idExercice) {
        Collection<Account> accounts = companyServices.getAccountsInExercice(idCompany, idExercice);
        return accounts;
    }

    public void initTabViewEntries() {
        this.viewMain.initTabViewEntries();
    }

    private void addEntryInTabViewEntries(Entry newEntry) {        
        this.viewMain.addEntryInTabViewEntries(newEntry);
    }

    private void updateEntryInTabViewEntries(Entry newEntry, LineEntryId idNewLineEntry) {

    }

    private void addEntryInTabViewLinesEntry(LineEntry newLineEntry) {        
        this.viewMain.addLineEntryInTabViewLinesEntry(newLineEntry);
    }

    private void resetTabViewLinesEntry() {
        this.viewMain.resetTabViewLinesEntry();
    }

    public void computeIdNewEntry() {
        resetTabViewLinesEntry();
        EntryId idNewEntry =  companyServices.computeIdNewEntry(model.getIdEntry());
        this.viewMain.newEntryNumber(idNewEntry);        
    }

    public void computeIdNewLineEntry() {
        LineEntryId idNewLineEntry =  companyServices.computeIdNewLineEntry(model.getIdLineEntry());
        this.viewMain.newLineEntryNumber(idNewLineEntry);  
    }

    public void initTabViewAccounts() {
        this.viewMain.initTabViewAccounts();
    }

    public void createNewAccount(CompanyId idCompany, ExerciceId idExercice, String account, String description) {
        Account newAccount = companyServices.createNewAccount(idCompany, idExercice,account, description);
        if(newAccount != null) {
            addAccountInTabViewAccounts(newAccount);
        } else {
            System.out.println("Ce compte existe déjà dans l'exercice.");
        }
    }

    private void addAccountInTabViewAccounts(Account newAccount) {        
        this.viewMain.addAccountInTabViewAccounts(newAccount);
    }

    
    
}
