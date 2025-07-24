package org.or.orcompta.domain;

import java.util.HashMap;
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
        accounts = new HashMap<>();
        entries = new HashMap<>();
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

    public EntryId getLastIdEntry() {
        return lastIdEntry;
    }

    public Account getAccount(String account) {
        if(accounts.containsKey(account)) {
            return accounts.get(account);
        }        
        return null;
    }

    public boolean getIsClosed() {
        return this.exerciceClosed;
    }

    boolean checkBalance() {
        return true;
    }

    void closeExercice() {
        exerciceClosed = true;
    }

    void editDocument() {

    }

    void addAccount(String name, String description) {
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

    void replicateEntry() {

    }

    @Override
    public String toString() {
        return this.idExercice + " "  +  this.beginDate + " -> " + this.endDate + "\n" + entries;        
    }

}
