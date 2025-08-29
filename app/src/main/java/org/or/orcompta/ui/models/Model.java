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

    public Model() {
        this.idCompany = new CompanyId(0);
        this.idExercice = new ExerciceId(0);
        this.configFile = new LinkedHashMap<>();
    }

    public CompanyId getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(CompanyId idCompany) {
        this.idCompany = idCompany;
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

    public Map<String, String> getIdNameFromConfigFile() {
        Map<String, String> idName = new LinkedHashMap<>();
        for(Map.Entry<String, ArrayList<String>> item: configFile.entrySet()) {           
            idName.put(item.getKey(), item.getValue().get(0));
        }
        return idName;
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


}
