package org.or.orcompta.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Company {
    private final CompanyId idCompany;
    private String name;
    private AddressCompany address;
    private String legalForm;
    private String siret;
    private String naf;
    private Double shareCapital;
    private String saveDirectory;
    private ExerciceId lastIdExercice;
    private ExerciceId newIdExercice;

    private Map<String, Exercice> exercices;
    private Map<String, String> journals;
    private Map<String, String> listOfExercices;

    public Company(CompanyId idCompany, String name, AddressCompany address, String legalForm, String siret, String naf, Double shareCapital, String saveDirectory) {
        this.lastIdExercice = new ExerciceId(-1);
        this.newIdExercice = this.lastIdExercice;
        this.idCompany = idCompany;
        this.name = name;
        this.address = address;
        this.legalForm = legalForm;
        this.siret = siret;
        this.naf = naf;
        this.shareCapital = shareCapital;
        this.saveDirectory = saveDirectory;        
        exercices = new HashMap<>();
        listOfExercices = new LinkedHashMap<>();
        journals = new LinkedHashMap<>();
        journals.put("AC", "Achats");
        journals.put("AN", "A nouveau");
        journals.put("BQ", "Banque");
        journals.put("CS", "Caisse");
        journals.put("OD", "Opérations diverses");
        journals.put("SA", "Salaires");
        journals.put("VT", "Ventes");
    }

    public CompanyId getIdCompany() {
        return idCompany;
    }

    public String getName() {
        return this.name;
    }

    public AddressCompany getAddress() {
        return this.address;
    }

    public String getLegalForm() {
        return this.legalForm;
    }

    public String getSiret() {
        return this.siret;
    }

    public String getNaf() {
        return this.naf;
    }

    public Double getShareCapital() {
        return this.shareCapital;
    }

    public String getSaveDirectory() {
        return this.saveDirectory;
    }

    public Map<String, String> getJournals() {
        return this.journals;
    }    

    public Exercice getExercice(String idExercice) {
        if(exercices.containsKey(idExercice)) {
            return exercices.get(idExercice);
        }        
        return null;
    }    

    public ExerciceId getIdNewExercice() {        
        return lastIdExercice.nextId();
    }

    public void addExercice(Exercice newExercice) {
        this.newIdExercice = newExercice.getIdExercice();
        exercices.put(this.newIdExercice.toString(), newExercice);
        this.lastIdExercice = this.newIdExercice;
        listOfExercices.put(this.newIdExercice.toString(), "du " + newExercice.getBeginDate().toString() + " au " + newExercice.getEndDate().toString());
    }

    public Map<String, String> getAddressMap() {
        Map<String, String> addressMap = address.getAddressMap();        
        return addressMap;
    }

    public void setLastIdExercice(String lastIdExercice) {
        this.lastIdExercice = new ExerciceId(lastIdExercice);
    }

    public ExerciceId getLastIdExercice() {
        return this.lastIdExercice;
    }

    public void addExerciceInList(String idExercice, String nameExercice) {
        listOfExercices.put(idExercice, nameExercice);
    }

    public Map<String, String> getListOfExercices() {
        return listOfExercices;
    }

    public Integer getNbExercices() {
        return listOfExercices.size();
    }
    
    @Override
    public String toString() {
        return idCompany + " " + name + " " + address + " " + legalForm + " " + siret + " " + naf + " " + shareCapital + " euros " + lastIdExercice + "\n" + exercices;
    }

    

    



}
