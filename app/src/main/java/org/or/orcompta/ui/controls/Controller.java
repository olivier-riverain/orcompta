package org.or.orcompta.ui.controls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.or.orcompta.application.CompanyServices;
import org.or.orcompta.domain.Account;
import org.or.orcompta.domain.BalanceId;
import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.Entry;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.ExerciceId;
import org.or.orcompta.domain.LineEntry;
import org.or.orcompta.domain.LineEntryId;
import org.or.orcompta.ui.models.Model;
import org.or.orcompta.ui.views.View;
import org.or.orcompta.ui.views.ViewCreateExercice;
import org.or.orcompta.ui.views.ViewMain;
import org.or.orcompta.ui.views.ViewNewCompany;
import org.or.orcompta.ui.views.ViewOpenCompany;
import org.or.orcompta.ui.views.ViewOpenExercice;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class Controller {

    private ViewMain viewMain;
    private ViewNewCompany viewNewCompany;
    private ViewOpenCompany viewOpenCompany;
    private ViewOpenExercice viewOpenExercice;
    private ViewCreateExercice viewCreatExercice;
    private CompanyServices companyServices;
    private Model model;
    private Stage stage;
    
    public Controller(Stage stage) {
        this.stage = stage;
        this.model = new Model();
        loadFileConfig();
        this.companyServices = new CompanyServices(this.model.getConfigFile());        
        this.viewMain = new ViewMain();
        this.viewNewCompany = new ViewNewCompany();
        this.viewOpenCompany = new ViewOpenCompany();
        this.viewOpenExercice = new ViewOpenExercice();
        this.viewCreatExercice = new ViewCreateExercice(viewOpenExercice);
        this.initView(stage, this.viewMain);
        this.initView(stage, this.viewNewCompany);
        this.initView(stage, viewOpenCompany);
        this.initView(stage, this.viewOpenExercice);
        this.initView(stage, this.viewCreatExercice);        

    }

    private void initView(Stage stage, View view) {
        view.initView(stage, this);
    }

    public void displayView(View view) {
        view.show();
    }
    
    public void displayView() {
       displayView(viewMain);
    }

    public void initForCompany() {
        viewMain.initForCompany();
    }

    public CompanyId getIdCompany() {
        return this.model.getIdCompany();
    }

    public ExerciceId getIdExercice() {
        return this.model.getIdExercice();
    }

    public void setIdExercice(String idExercice) {
        this.model.setIdExercice(idExercice);
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

    private void loadFileConfig() {
        String pwd = System.getProperty("user.dir");        
        File orcomptaConfigFile = new File(pwd + File.separator + "src" + File.separator +"main" + File.separator + "resources" + File.separator + "configuration" + File.separator + "orcomptaConfig.json");
        System.out.println("controller loadFileConfig = " + orcomptaConfigFile.getAbsolutePath());
        if(!orcomptaConfigFile.exists()) {
            try {
                orcomptaConfigFile.createNewFile();
                FileWriter file = new FileWriter(orcomptaConfigFile);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", "Fichier de configuration de ORCompta");
                CompanyId lastIdCompany = new CompanyId();                
                jsonObject.put("lastIdCompany", lastIdCompany.toString());
                JSONArray companies = new JSONArray();
                jsonObject.put("companies", companies);
                file.write(jsonObject.toString());
                file.close();
            } catch (IOException e) {            
                e.printStackTrace();
            }
        }
        this.model.setConfigFile(orcomptaConfigFile);
        FileReader file;
        try {
            file = new FileReader(orcomptaConfigFile);
            JSONObject configFile = new JSONObject(new JSONTokener(file));
            String lastIdCompany = configFile.getString("lastIdCompany");
            this.model.setLastIdCompany(lastIdCompany);         
            JSONArray companies = configFile.getJSONArray("companies");
            for(int i=0; i<companies.length(); i++) {
                JSONObject company = companies.getJSONObject(i);
                String idCompany = company.getString("idCompany");
                String name = company.getString("name");
                String saveDirectory = company.getString("saveDirectory");
                ArrayList<String> parameters = new ArrayList<>();
                parameters.add(name);
                parameters.add(saveDirectory);
                this.model.addItemInConfigFile(idCompany, parameters);                
            }
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }
    }

    public void saveFileConfig(String idCompany, String name, String saveDirectory) throws IOException {
        File orcomptaConfigFile = this.model.getConfigFile();
        FileReader fileReader;
        JSONObject configFile;
        JSONArray companies;
        FileWriter fileWriter;

        try {
            fileReader = new FileReader(orcomptaConfigFile);
            configFile = new JSONObject(new JSONTokener(fileReader));
            fileReader.close();                   
            companies = configFile.getJSONArray("companies");
            JSONObject newCompany = new JSONObject();
            newCompany.put("idCompany", idCompany);
            newCompany.put("name", name);
            newCompany.put("saveDirectory", saveDirectory);
            companies.put(newCompany);
            configFile.remove("lastIdCompany");
            configFile.put("lastIdCompany", idCompany);

            fileWriter = new FileWriter(orcomptaConfigFile);
            fileWriter.write(configFile.toString());
            fileWriter.close();

        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }
        

    }

    public Vector<String> retrieveYears() {
        Vector<String> years = new Vector<>();
        // penser à récupérer les années d'exercice dans le modèle à partir des info de l'exercice encours
        years.add("2024");
        years.add("2025"); 
        return years;
    }

    public Map<String, String> getJournals() {
        Map<String, String> journals = companyServices.getJournals(this.model.getIdCompany());       
        return journals;
    }

    public CompanyId createNewCompany(String name, String numero, String address, String address2, String postalCode, String city, String legalForm, String siret, String naf, Double shareCapital, String saveDirectory) throws IOException {
        CompanyId idCompany = companyServices.createNewCompany(name, numero, address, address2, postalCode, city, legalForm, siret, naf, shareCapital, saveDirectory + File.separator);
        saveFileConfig(idCompany.toString(), name, saveDirectory);
        return idCompany; 
    }

    public ExerciceId createNewExercice(CompanyId idCompany, String beginjj,  String beginmm,  String beginyy, String endjj, String endmm, String endyy) {
        System.out.println("controller createNewExercice idCompany = " + idCompany);
        ExerciceId idExercice = companyServices.createNewExercice(idCompany, beginjj, beginmm, beginyy, endjj, endmm, endyy);        
        model.setIdCreateExercice(idExercice);
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

    public Boolean saveNewEntry() {
        CompanyId idCompany = this.model.getIdCompany();
        ExerciceId idExercice = this.model.getIdExercice();
        EntryId idEntry = this.model.getIdEntry();
        Entry newEntry = companyServices.getEntry(idCompany, idExercice, idEntry);
        if(newEntry.checkAmountsEntry()) {
            this.addEntryInTabViewEntries(newEntry);
            return true;
        } else {
            Alert error = new Alert(AlertType.ERROR);
            error.setContentText("L'écriture n'est pas équilibrée et ne peut donc pas être enregistrée.");
            error.show();
            return false;
        }
         
    }

    public void saveLineEntry(String idEntry, String jj, String mm, String yy, String journal, String voucher, String account, double amountDebit, double amountCredit) {
        System.out.println("saveLineEntry this.model.getIdEntry().toString() = " + this.model.getIdEntry().toString() + " idEntry = " + idEntry);
        if(!(this.model.getIdEntry().toString().equals(idEntry))) {
            System.out.println("saveLineEntry " + this.model.getIdEntry().toString() + " != " + idEntry);
            createNewEntry(jj, mm, yy, journal, voucher);
        }
        createNewLineEntry(account, amountDebit, amountCredit);
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

    public String getAccountDescription(String account) {    
        return companyServices.getAccountDescription(this.model.getIdCompany(), this.model.getIdExercice(), account);
    }

    public void setIdEntryLoaded(EntryId idEntryLoaded) {
        this.model.setIdEntryLoaded(idEntryLoaded);
    }

    public double getEntryLoadedTotalDebit() {        
        return this.companyServices.getEntryTotalDebit(this.model.getIdCompany(), this.model.getIdExercice(), this.model.getIdEntryLoaded());
    }

    public double getEntryLoadedTotalCredit() {
        return this.companyServices.getEntryTotalCredit(this.model.getIdCompany(), this.model.getIdExercice(), this.model.getIdEntryLoaded());
    }

    public String getDateJJEntry(EntryId idEntry) {
        return companyServices.getDateJJEntry(this.model.getIdCompany(), this.model.getIdExercice(), this.model.getIdEntryLoaded());
    }

    public String getDateMMEntry(EntryId idEntry) {
        return companyServices.getDateMMEntry(this.model.getIdCompany(), this.model.getIdExercice(),  this.model.getIdEntryLoaded());
    }

    public String getDateAAEntry(EntryId idEntry) {
        return companyServices.getDateAAEntry(this.model.getIdCompany(), this.model.getIdExercice(),  this.model.getIdEntryLoaded());
    }

    public String getJournalEntry(EntryId idEntry) {
        return companyServices.getJournalEntry(this.model.getIdCompany(), this.model.getIdExercice(),  this.model.getIdEntryLoaded());
    }

    public String getVoucherEntry(EntryId idEntry) {
        return companyServices.getVoucherEntry(this.model.getIdCompany(), this.model.getIdExercice(),  this.model.getIdEntryLoaded());
    }
        
    public BalanceId computeBalance(String beginjj, String beginmm, String beginyy, String endjj, String endmm, String endyy) {
        BalanceId idBalance = companyServices.computeBalance(this.model.getIdCompany(), this.model.getIdExercice(), beginjj, beginmm, beginyy, endjj, endmm, endyy);
        return idBalance;
    }

    public void editBalance(String fromjj, String frommm, String fromaa, String tojj,  String tomm, String toaa) {
        companyServices.editBalance(this.model.getIdCompany(), this.model.getIdExercice(), fromjj, frommm, fromaa, tojj, tomm, toaa);
    }

    public void displayCreateNewCompany() {
        this.viewNewCompany.show();
    }

    public void displayOpenCompany() {
        this.viewOpenCompany.show();
    }
    
    public void displayOpenNewExercice() {
        displayView(this.viewOpenExercice);
    }

    public void displayCreateExercice(String idCompany, String company) {
        this.model.setIdCompanyViewCreateExercice(idCompany, company);
        displayView(viewCreatExercice);
    }

    public Map<String, ArrayList<String>> getCompanies() {        
        return this.model.getCompaniesFromConfigFile();
    }

    public ArrayList<String> getCompany(String idCompany) {        
        return this.model.getCompanyFromConfigFile(new CompanyId(idCompany));
    }    

    public Map<String, String> getExercices(String idCompany) {
        Map<String, String> exercicesList = companyServices.loadListExercicesFromCompany(idCompany);       
        return exercicesList;
    }    

    public ArrayList<String> getIdCompanyViewCreateExercice() {
        return this.model.getIdCompanyViewCreateExercice();
    }

    public void loadCompany(String idCompany, String nameCompany) {
        companyServices.loadCompany(idCompany);
        this.model.setIdCompany(idCompany);
        setTitleNameCompany(idCompany + " - " + nameCompany);
        this.model.setIdExercice();
        setTitleNameExercice("");
    }

    private void setTitleNameCompany(String title) {
        this.model.setTitleNameCompany(title);
    }

    public String getNameCompany() {
        return this.model.getTitleNameCompany();
    }

    public void setTitleNameExercice(String title) {
        this.model.setTitleNameExercice(title);
    }

    public String getNameExercice() {
        return this.model.getTitleNameExercice();
    }

    public void loadExercice(String idCompany, String idExercice, String nameExercice) {
        companyServices.loadExercice(idCompany, idExercice);
        this.model.setIdExercice(idExercice);        
        setTitleNameExercice(idExercice + " - " + nameExercice);
    }  

       
    
}
