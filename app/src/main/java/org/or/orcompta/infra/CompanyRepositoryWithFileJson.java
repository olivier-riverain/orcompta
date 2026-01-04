package org.or.orcompta.infra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;
import org.or.orcompta.domain.Account;
import org.or.orcompta.domain.AddressCompany;
import org.or.orcompta.domain.Company;
import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.CompanyRepository;
import org.or.orcompta.domain.DateEntry;
import org.or.orcompta.domain.Entry;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.Exercice;
import org.or.orcompta.domain.ExerciceId;
import org.or.orcompta.domain.LineEntry;
import org.or.orcompta.domain.LineEntryId;




public class CompanyRepositoryWithFileJson  implements CompanyRepository{
    private final File orcomptaConfigFile;
    private Company company;
    private Exercice exercice;
    private ExerciceId idExercice;

    public CompanyRepositoryWithFileJson(File orcomptaConfigFile) {
        this.orcomptaConfigFile = orcomptaConfigFile;
    }
    
    public Company findCompanyById(CompanyId idCompany) {        
        Map<String, String> companyParameters = loadFileCompany(idCompany);
        this.company = new Company(idCompany, companyParameters.get("name"), new AddressCompany(companyParameters.get("numero"), companyParameters.get("address"), companyParameters.get("address2"), companyParameters.get("postalCode"), companyParameters.get("city")), companyParameters.get("legalForm"), companyParameters.get("siret"), companyParameters.get("naf"), Double.parseDouble(companyParameters.get("shareCapital")), companyParameters.get("saveDirectory"));
        company.setLastIdExercice(companyParameters.get("lastIdExercice"));
        System.out.println("findCompanyById = " + company);
        for(Map.Entry<String, String> exerciceItem : companyParameters.entrySet()) {
            if(exerciceItem.getKey().contains("exercice_")) {
                String tab[] = exerciceItem.getKey().split("_");                
                company.addExerciceInList(tab[1], exerciceItem.getValue()); 
            }                                
        }
        return company;
    }

    public void saveCompany(Company company) {
        String name = company.getName();
        String idCompany = company.getIdCompany().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idCompany", idCompany);
        jsonObject.put("name", name);
        Map<String, String> addressMap = company.getAddressMap();
        JSONArray address = new JSONArray();
        address.put(addressMap.get("numero"));
        address.put(addressMap.get("address"));
        address.put(addressMap.get("address2"));
        address.put(addressMap.get("postalCode"));
        address.put(addressMap.get("city"));        
        jsonObject.put("address", address);
        jsonObject.put("legalForm", company.getLegalForm());
        jsonObject.put("siret", company.getSiret());
        jsonObject.put("naf", company.getNaf());
        jsonObject.put("shareCapital", company.getShareCapital().toString());
        jsonObject.put("saveDirectory", company.getSaveDirectory());
        JSONArray exercices = new JSONArray();
        exercices.put(company.getLastIdExercice().toString());
        exercices.put(company.getNbExercices().toString());
        Map<String, String> listOfExercices = company.getListOfExercices();
        for(Map.Entry<String, String> exercice : listOfExercices.entrySet()) {
            exercices.put("exercice_" + exercice.getKey());
            exercices.put(exercice.getValue());
        }
        jsonObject.put("exercices", exercices);
        name = name.replace(' ', '-');
        File fileName = new File(company.getSaveDirectory() + idCompany + "-" + name  + ".json");
        try {
            fileName.createNewFile();
            FileWriter file = new FileWriter(fileName);
            file.write(jsonObject.toString(4));        
            file.close();
        } catch (IOException e) {         
         e.printStackTrace();
      }
    }

    private Map<String, String> loadFileCompany(CompanyId idCompany) {
        ArrayList<String> companyParameters = findCompanyParameters(idCompany);
        Map<String, String> company = new HashMap<>();
        FileReader file;
        String name = new String(companyParameters.get(1));
        name = name.replace(' ', '-');
        String fileCompany = new String(companyParameters.get(2) + companyParameters.get(0) + "-" + name + ".json");
        System.out.println("loadFileCompany fileCompany = " + fileCompany);
        try {
            file = new FileReader(fileCompany);
            JSONObject jsonObjectcompany = new JSONObject(new JSONTokener(file));
            JSONArray addressList = jsonObjectcompany.getJSONArray("address");
            JSONArray exercices = jsonObjectcompany.getJSONArray("exercices");
            company.put("idCompany", jsonObjectcompany.getString("idCompany"));
            company.put("name", jsonObjectcompany.getString("name"));
            company.put("numero", addressList.get(0).toString());
            company.put("address", addressList.get(1).toString());
            company.put("address2", addressList.get(2).toString());
            company.put("postalCode", addressList.get(3).toString());
            company.put("city", addressList.get(4).toString());
            company.put("legalForm", jsonObjectcompany.getString("legalForm"));
            company.put("siret", jsonObjectcompany.getString("siret"));
            company.put("naf", jsonObjectcompany.getString("naf"));
            company.put("shareCapital", jsonObjectcompany.getString("shareCapital"));
            company.put("saveDirectory", jsonObjectcompany.getString("saveDirectory"));
            company.put("lastIdExercice", exercices.get(0).toString());
            company.put("nbExercice", exercices.get(1).toString());
            for(int i=2; i<exercices.length(); i=i+2) {
                company.put(exercices.get(i).toString(), exercices.get(i+1).toString());
            }
        
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }
        return company;
    }

    private ArrayList<String> findCompanyParameters(CompanyId idCompany) {
        ArrayList<String> companyParameters = new ArrayList<>();        
        FileReader file;
        try {
            file = new FileReader(orcomptaConfigFile);
            JSONObject configFile = new JSONObject(new JSONTokener(file));                    
            JSONArray companies = configFile.getJSONArray("companies");
            for(int i=0; i<companies.length(); i++) {
                JSONObject company = companies.getJSONObject(i);
                if(company.getString("idCompany").equals(idCompany.toString())) {
                    companyParameters.add(idCompany.toString());
                    companyParameters.add(company.getString("name"));
                    companyParameters.add(company.getString("saveDirectory"));
                    break;
                }                                
            }
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }

        return companyParameters;
    }

    public void updateCompany(Company company) {

    }

    public void saveExercice(Exercice newExercice) {
        this.exercice = newExercice;
        this.idExercice = newExercice.getIdExercice();              
        String name = company.getName();
        String idCompany = company.getIdCompany().toString();
        saveCompany(company);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idExercice", this.exercice.getIdExercice().toString());
        jsonObject.put("lastIdEntry", this.exercice.getLastIdEntry().toString());
        jsonObject.put("beginDate", this.exercice.getBeginDate().toString());
        jsonObject.put("endDate", this.exercice.getEndDate().toString());
        JSONArray entries = new JSONArray();
        for(Entry entry: newExercice.getEntries()) {
            JSONObject jsonobjectEntry = new JSONObject();
            jsonobjectEntry.put("idEntry", entry.getIdEntry().toString());
            jsonobjectEntry.put("date", entry.getDate().toString());
            jsonobjectEntry.put("journal", entry.getJournal());
            jsonobjectEntry.put("justificatif", entry.getVoucher());
            jsonobjectEntry.put("nbLineEntry", entry.getNbLinesEntry());
            jsonobjectEntry.put("amountDebit", entry.getAmountDebit());
            jsonobjectEntry.put("amountCredit", entry.getAmountCredit());
            JSONArray linesEntry = new JSONArray();
            for(LineEntry lineEntry: entry.getLinesEntry() ) {            
                JSONObject jsonobjectLineEntry = new JSONObject();
                jsonobjectLineEntry.put("idLineEntry", lineEntry.getIdLineEntry().toString());
                jsonobjectLineEntry.put("accountLineEntry", lineEntry.getAccount().getName());
                jsonobjectLineEntry.put("accountDescriptionLineEntry", lineEntry.getAccount().getDescription());
                jsonobjectLineEntry.put("amountDebitLineEntry", lineEntry.getAmountDebit());
                jsonobjectLineEntry.put("amountCreditLineEntry", lineEntry.getAmountCredit());
                linesEntry.put(jsonobjectLineEntry);
            }
            jsonobjectEntry.put("linesEntry", linesEntry);
        entries.put(jsonobjectEntry);
        }
        jsonObject.put("entries", entries);
        JSONArray accounts = new JSONArray();
        for(Account account: newExercice.getAccounts()) {
            JSONObject jsonObjectAccount = new JSONObject();
            jsonObjectAccount.put("nameAccount", account.getName());
            jsonObjectAccount.put("descriptionAccount", account.getDescription());
            accounts.put(jsonObjectAccount);
        }            
        jsonObject.put("accounts", accounts);
        jsonObject.put("exerciceClosed", this.exercice.getIsClosed());
        
        name = name.replace(' ', '-');
        File fileName = new File(company.getSaveDirectory() + idCompany + "-" + name  + "-exercice-" + this.exercice.getIdExercice().toString() + ".json");
        try {
            fileName.createNewFile();
            FileWriter file = new FileWriter(fileName);
            file.write(jsonObject.toString(4));        
            file.close();
        } catch (IOException e) {         
         e.printStackTrace();
        }
    }

    public Exercice findExerciceById(CompanyId idCompany, ExerciceId idExercice) {
        Exercice exerciceLoaded = null;
        Map<String, String> listOfExercices = company.getListOfExercices();
        for(Map.Entry<String, String> exercice : listOfExercices.entrySet()) {
            if(exercice.getKey().equals(idExercice.toString())) {
                exerciceLoaded = loadFileExercice(idExercice);
            }
        }
        
        return exerciceLoaded;
    }

    private Exercice loadFileExercice(ExerciceId idExercice) {        
        Exercice exercice = null;
        FileReader file;
        String name = company.getName();        
        name = name.replace(' ', '-');
        String fileExercice = new String(company.getSaveDirectory() + company.getIdCompany().toString() + "-" + name + "-" + "exercice-" + idExercice.toString() + ".json");
        System.out.println("loadFileExercice fileExercice = " + fileExercice);
        try {
            file = new FileReader(fileExercice);
            JSONObject jsonObjectExercice = new JSONObject(new JSONTokener(file));           
            DateEntry beginDate = new DateEntry(jsonObjectExercice.getString("beginDate"));
            DateEntry endDate = new DateEntry(jsonObjectExercice.getString("endDate"));                      
            exercice = new Exercice(idExercice, beginDate, endDate, jsonObjectExercice.getString("lastIdEntry"), jsonObjectExercice.getBoolean("exerciceClosed"));
            System.out.println("loadFileExercice exerciceId = " + exercice.getIdExercice());
            JSONArray entries = jsonObjectExercice.getJSONArray("entries");
            for(int i=0; i<entries.length(); i++) {
                JSONObject jsonobjectEntry = entries.getJSONObject(i);
                DateEntry date = new DateEntry(jsonobjectEntry.getString("date"));
                Entry entry = new Entry(new EntryId(jsonobjectEntry.getString("idEntry")), date, jsonobjectEntry.getString("journal"), jsonobjectEntry.getString("justificatif"), Double.toString(jsonobjectEntry.getDouble("amountDebit")), Double.toString(jsonobjectEntry.getDouble("amountCredit")));
                JSONArray linesEntry =  jsonobjectEntry.getJSONArray("linesEntry");
                for(int j=0; j<linesEntry.length(); j++) {
                    JSONObject jsonobjectLineEntry = linesEntry.getJSONObject(j);                
                    LineEntry lineEntry = new LineEntry(new LineEntryId(jsonobjectLineEntry.getString("idLineEntry")), new Account(jsonobjectLineEntry.getString("accountLineEntry"), jsonobjectLineEntry.getString("accountDescriptionLineEntry")), jsonobjectLineEntry.getDouble("amountDebitLineEntry"), jsonobjectLineEntry.getDouble("amountCreditLineEntry"));
                    entry.addLineEntry(lineEntry);
                }
                exercice.addEntry(entry);

            }
            
            JSONArray accounts = jsonObjectExercice.getJSONArray("accounts");
            for(int i=0; i< accounts.length(); i++) {
                //JSONObject jsonObjectAccount = 
            }
        
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }
        return exercice;
    }

    public void saveEntry(Entry newEntry) {        
        saveExercice(this.exercice);
    }

    public void importExercice_first_version() {
        FileReader file;
        JSONObject jsonObject = null;
        String fileExercice = new String(company.getSaveDirectory() + "ORCOMPTA_alterelec_01012024-31122024.xml");
        try {
            file = new FileReader(fileExercice);
            jsonObject = XML.toJSONObject(file);            
            File fileName = new File(company.getSaveDirectory() + "ORCOMPTA_alterelec_01012024-31122024.json");
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(jsonObject.toString(4));        
            fileWriter.close();
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        } catch (IOException e) {           
            e.printStackTrace();
        }
        
    }

    public void importExercice() {
        Exercice exercice = null;
        JSONObject jsonObjectRead = null;        
        String fileExercice = new String(company.getSaveDirectory() + "ORCOMPTA_alterelec_01012024-31122024.json");
        FileReader file;
        try {
            file = new FileReader(fileExercice);
            jsonObjectRead = new JSONObject(new JSONTokener(file));
            DateEntry beginDate = new DateEntry("1/1/2024");
            DateEntry endDate = new DateEntry("31/12/2024"); 
            ExerciceId idExercice = new ExerciceId(0);                     
            exercice = new Exercice(idExercice, beginDate, endDate, "-1", false);      
            JSONObject orcompta = jsonObjectRead.getJSONObject("ORCOMPTA");
            JSONObject compte = orcompta.getJSONObject("COMPTE");
            JSONArray ligne = compte.getJSONArray("LIGNE");
            for(int i=0; i<ligne.length(); i++) {
                JSONObject jsonObjectAccount = ligne.getJSONObject(i);
                String numCompte = jsonObjectAccount.getString("num_compte");
                String libelle = jsonObjectAccount.getString("libelle");
                Account account = exercice.createNewAccount(numCompte, libelle);               
            }
            JSONObject ecriture = orcompta.getJSONObject("ECRITURE");
            ligne = ecriture.getJSONArray("LIGNE");
            Integer numEcritureP = 0;
             Entry entry = null;
            for(int i=0; i<ligne.length(); i++) {            
                JSONObject jsonObjectEcriture = ligne.getJSONObject(i);
                Integer  numEcriture = jsonObjectEcriture.getInt( "num_ecriture");
                Integer numLigne = jsonObjectEcriture.getInt("num_ligne");
                Integer dateEcriture = jsonObjectEcriture.getInt("date_ecriture");
                String journal = jsonObjectEcriture.getString("code_journal");
                String justificatif = jsonObjectEcriture.getString("num_piece");
                Double amountDebit = jsonObjectEcriture.getDouble("montant_debit");
                Double amountCredit = jsonObjectEcriture.getDouble("montant_credit");
                String numCompte = jsonObjectEcriture.getString("num_compte");
                String libelleCompte = jsonObjectEcriture.getString("libelle");
                Date date = new Date(dateEcriture*1000L);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateEcritureFormat = dateFormat.format(date);
                DateEntry dateEntry = new DateEntry(dateEcritureFormat);
                if(numEcriture != numEcritureP) {
                    entry = new Entry(new EntryId(numEcriture), dateEntry, journal, justificatif, "0.0", "0.0");
                    numEcritureP = numEcriture;
                    exercice.addEntry(entry);
                }
                LineEntry lineEntry = new LineEntry(new LineEntryId(numLigne), new Account(numCompte, libelleCompte), amountDebit, amountCredit);
                entry.addLineEntry(lineEntry);                
            }

        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }
        System.out.println("exercice = " + exercice);
        System.out.println("idExercice = " + exercice.getIdExercice());
        company.addExercice(exercice);
        saveExercice(exercice);

    }


}
