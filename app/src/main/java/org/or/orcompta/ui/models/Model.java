package org.or.orcompta.ui.models;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.ExerciceId;
import org.or.orcompta.domain.LineEntryId;

public class Model {
    
    private CompanyId idCompany;
    private ExerciceId idExercice;
    private EntryId idEntry;
    private EntryId idEntryLoaded;
    private LineEntryId idLineEntry;
    private File orcomptaConfigFile;
    private File companySaveDirectory;
    private Map<String, ArrayList<String>> configFile;
    private CompanyId lastIdCompany;
    private ArrayList<String> idCompanyViewCreateExercice;
    private String titleNameCompany;
    private ExerciceId idCreateExercice;   

    public Model() {
        this.idCompany = new CompanyId();
        this.idExercice = new ExerciceId();
        this.configFile = new LinkedHashMap<>();
        this.idCompanyViewCreateExercice = new ArrayList<String>();
        this.titleNameCompany = new String("");
        this.idCreateExercice = new ExerciceId();
    }

    public CompanyId getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = new CompanyId(idCompany);
    }

    public ExerciceId getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(ExerciceId idExercice) {
        this.idExercice = idExercice;
    }

    public EntryId getIdEntry() {
        return idEntry;
    }    

    public void setIdEntry(EntryId idEntry) {
        this.idEntry = idEntry;
    }

    public EntryId getIdEntryLoaded() {
        return idEntryLoaded;
    }    

    public void setIdEntryLoaded(EntryId idEntryLoaded) {
        this.idEntryLoaded = idEntryLoaded;
    }

    public LineEntryId getIdLineEntry() {
        return idLineEntry;
    }
    
    public void setIdLineEntry(LineEntryId idLineEntry) {
        this.idLineEntry = idLineEntry;
    }

    public void setConfigFile(File orcomptaConfigFile) {
        this.orcomptaConfigFile = orcomptaConfigFile;
    }

    public File getConfigFile() {
        return this.orcomptaConfigFile;
    }

    public void setSaveDirectory(File companySaveDirectory) {
        this.companySaveDirectory = companySaveDirectory;
    }

    public File getSaveDirectory() {
        return this.companySaveDirectory;
    }

    public void addItemInConfigFile(String id, ArrayList<String> parameters) {
        configFile.put(id, parameters);
    }

    public Map<String, ArrayList<String>> getCompaniesFromConfigFile() {
        return configFile;
    }       

    public ArrayList<String> getCompanyFromConfigFile(CompanyId idCompany) {
        ArrayList<String> parameters = new ArrayList<>();
        for(Map.Entry<String, ArrayList<String>> item: configFile.entrySet()) {           
            if(item.getKey().equals(idCompany.toString())) {
                parameters.add(item.getKey());
                parameters.add(item.getValue().get(0));
                parameters.add(item.getValue().get(1));
            }
        }
        return parameters;
    }

    public CompanyId getLastIdCompany() {
        return this.lastIdCompany;
    }

    public void setLastIdCompany(String lastIdCompany) {
        this.lastIdCompany = new CompanyId(Integer.parseInt(lastIdCompany));
    }

    public void setLastIdCompany(CompanyId lastIdCompany) {
        this.lastIdCompany = lastIdCompany;
    }

    public void setIdCompanyViewCreateExercice(String idCompany, String company) {
        this.idCompanyViewCreateExercice.clear();
        this.idCompanyViewCreateExercice.add(0, idCompany);
        this.idCompanyViewCreateExercice.add(1, company);
    }

    public ArrayList<String> getIdCompanyViewCreateExercice() {
        return this.idCompanyViewCreateExercice;
    }

    public void setTitleNameCompany(String titleNameCompany) {
        this.titleNameCompany = titleNameCompany;
    }

    public String getTitleNameCompany() {
        return titleNameCompany;
    }

    public void setIdCreateExercice(ExerciceId idExercice) {
        this.idCreateExercice = idExercice;
    }

    public ExerciceId getIdCreateExercice() {
        return this.idCreateExercice;
    }

    

}
