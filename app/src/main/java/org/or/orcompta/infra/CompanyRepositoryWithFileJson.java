package org.or.orcompta.infra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.or.orcompta.domain.AddressCompany;
import org.or.orcompta.domain.Company;
import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.CompanyRepository;
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
            file.write(jsonObject.toString());        
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
        String name = company.getName();
        String idCompany = company.getIdCompany().toString();
        saveCompany(company);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idExercice", newExercice.getIdExercice().toString());
        jsonObject.put("lastIdEntry", newExercice.getLastIdEntry().toString());
        jsonObject.put("beginDate", newExercice.getBeginDate().toString());
        jsonObject.put("endDate", newExercice.getEndDate().toString());
        JSONArray entries = new JSONArray();

        jsonObject.put("entries", entries);
        JSONArray accounts = new JSONArray();
        
        jsonObject.put("accounts", accounts);
        jsonObject.put("exerciceClosed", newExercice.getIsClosed());
        
        name = name.replace(' ', '-');
        File fileName = new File(company.getSaveDirectory() + idCompany + "-" + name  + "-exercice-" + newExercice.getIdExercice().toString() + ".json");
        try {
            fileName.createNewFile();
            FileWriter file = new FileWriter(fileName);
            file.write(jsonObject.toString());        
            file.close();
        } catch (IOException e) {         
         e.printStackTrace();
      }
    }

    public Exercice findExerciceById(ExerciceId idExercice) {
        Map<String, String> listOfExercices = company.getListOfExercices();
        for(Map.Entry<String, String> exercice : listOfExercices.entrySet()) {
            if(exercice.getKey().equals(idExercice.toString())) {
                Map<String, String> exerciceParameters = loadFileExercice(idExercice);
            }
        }
        
        return null;
    }

    private Map<String, String> loadFileExercice(ExerciceId idExercice) {        
        Map<String, String> exercice = new HashMap<>();
        FileReader file;
        String name = company.getName();        
        name = name.replace(' ', '-');
        String fileExercice = new String(company.getIdCompany().toString() + "-" + name + "-" + "exercice" + idExercice.toString() + ".json");
        System.out.println("loadFileExercice fileExercice = " + fileExercice);
        try {
            file = new FileReader(fileExercice);
            JSONObject jsonObjectExercice = new JSONObject(new JSONTokener(file));
            //"lastIdEntry":"-1","beginDate":"1/1/2024","entries":[],"exerciceClosed":false,"idExercice":"0","endDate":"31/12/2024","accounts":[]
           
            exercice.put("idExercice", jsonObjectExercice.getString("idExercice"));
            exercice.put("lastIdEntry", jsonObjectExercice.getString("lastIdEntry"));
            exercice.put("beginDate", jsonObjectExercice.getString("beginDate"));
            exercice.put("endDate", jsonObjectExercice.getString("endDate"));
            exercice.put("exerciceClosed", jsonObjectExercice.getString("exerciceClosed"));
            JSONArray entries = jsonObjectExercice.getJSONArray("entries");

            JSONArray accounts = jsonObjectExercice.getJSONArray("accounts");
        
        } catch (FileNotFoundException e) {            
            e.printStackTrace();
        }
        return exercice;
    }

    public void saveEntry(Entry newEntry) {        
        JSONObject jsonobjectEntry = new JSONObject();
        jsonobjectEntry.put("idEntry", newEntry.getIdEntry().toString());
        jsonobjectEntry.put("date", newEntry.getDate().toString());
        jsonobjectEntry.put("journal", newEntry.getJournal());
        jsonobjectEntry.put("justificatif", newEntry.getVoucher());
        jsonobjectEntry.put("nbLineEntry", newEntry.getNbLinesEntry());
        jsonobjectEntry.put("amountDebit", newEntry.getAmountDebit());
        jsonobjectEntry.put("amountCredit", newEntry.getAmountCredit());
        JSONArray linesEntry = new JSONArray();
        for(LineEntry lineEntry: newEntry.getLinesEntry() ) {            
            linesEntry.put(lineEntry.getIdLineEntry().toString());
            linesEntry.put(lineEntry.getAccount().getName());
            linesEntry.put(lineEntry.getAmountDebit());
            linesEntry.put(lineEntry.getAmountCredit());
        }
        jsonobjectEntry.put("linesEntry", linesEntry);
        Map<String, String> exerciceParameters = loadFileExercice(this.idExercice);

        saveExercice(exercice);

    }



}
