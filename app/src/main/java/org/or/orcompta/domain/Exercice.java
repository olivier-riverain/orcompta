package org.or.orcompta.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


public class Exercice {
   
    private final ExerciceId idExercice; 
    private EntryId lastIdEntry;
    private EntryId newIdEntry;  
    private DateEntry beginDate;
    private DateEntry endDate;
    private Map<EntryId, Entry> entries;    
    private Map <String, Account> accounts;
    private boolean exerciceClosed = false;
    

    public Exercice(ExerciceId idExercice, DateEntry beginDate, DateEntry endDate) {
        this.idExercice = idExercice;
        this.lastIdEntry = new EntryId(-1);        
        this.beginDate= beginDate;
        this.endDate = endDate;
        accounts = new LinkedHashMap<>();
        entries = new LinkedHashMap<>();
    }

    public Exercice(ExerciceId idExercice, DateEntry beginDate, DateEntry endDate, String lastIdEntry, boolean exerciceClosed) {
        this.idExercice = idExercice;
        this.lastIdEntry = new EntryId(-1);        
        this.beginDate= beginDate;
        this.endDate = endDate;
        accounts = new LinkedHashMap<>();
        entries = new LinkedHashMap<>();
        this.lastIdEntry = new EntryId(lastIdEntry);
        //if(exerciceClosed.equals("true")) this.exerciceClosed = true; else this.exerciceClosed = false;
        
    }

    public ExerciceId getIdExercice() {
        return this.idExercice;
    }

    public Entry getEntry(EntryId idEntry) {
        if(entries.containsKey(idEntry)) {
            return entries.get(idEntry);
        }        
        return null;
    }

    public EntryId getIdNewEntry() {
        return lastIdEntry.nextId();
    }

    public DateEntry getBeginDate() {
        return beginDate;
    }

    public DateEntry getEndDate() {
        return endDate;
    }

    public EntryId getLastIdEntry() {
        return lastIdEntry;
    }

    public void setLastIdEntry(String idEntry) {
        this.lastIdEntry = new EntryId(idEntry);
    }

    public EntryId resetIdEntry() {
        return new EntryId();
    }

    public Account getAccount(String account) {
        if(accounts.containsKey(account)) {
            return accounts.get(account);
        }        
        return null;
    }

    public Account createNewAccount(String name, String description) {
        Account newAccount = null;
        if(!(accounts.containsKey(name))) { 
            newAccount = new Account(name, description);
            accounts.put(name, newAccount); 
            return newAccount;           
        }
        return newAccount;       
    }

    public boolean getIsClosed() {
        return this.exerciceClosed;
    }

    public Collection<Entry> getEntries() {
        return this.entries.values();
    }

    public Collection<Account> getAccounts() {
        return this.accounts.values();
    }

    public String getAccountDescription(String account) {
        String description = new String("Ce compte n'existe pas dans l'exercice.");
        if(accounts.containsKey(account)) {
            return accounts.get(account).getDescription();
        }
        return description;
    }

    boolean checkBalance() {
        return true;
    }

    public void closeExercice() {
        exerciceClosed = true;
    }

    public void editDocument() {

    }

    public void addAccount(String name, String description) {
        Account newAccount = new Account(name, description);
        accounts.put(name, newAccount);
    }

    public void addEntry(Entry newEntry) {
        this.newIdEntry = newEntry.getIdEntry();        
        entries.put(this.newIdEntry, newEntry);
        this.lastIdEntry = this.newIdEntry;
    }

    void loadEntry() {

    }

    void deleteEntry() {

    }

    public void replicateEntry() {

    }

    @Override
    public String toString() {
        return this.idExercice + " "  +  this.beginDate + " -> " + this.endDate + "\n" + entries + "\n" + accounts;        
    }

}
