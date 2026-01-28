package org.or.orcompta.domain;

import java.util.ArrayList;
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
    private ExerciceId idExerciceBefore;
    

    public Exercice(ExerciceId idExercice, DateEntry beginDate, DateEntry endDate, ExerciceId idExerciceBefore) {
        this.idExercice = idExercice;
        this.lastIdEntry = new EntryId(-1);        
        this.beginDate= beginDate;
        this.endDate = endDate;
        this.idExerciceBefore = idExerciceBefore;
        accounts = new LinkedHashMap<>();
        entries = new LinkedHashMap<>();
    }

    public Exercice(ExerciceId idExercice, DateEntry beginDate, DateEntry endDate, String lastIdEntry, boolean exerciceClosed, ExerciceId idExerciceBefore) {
        this.idExercice = idExercice;
        this.lastIdEntry = new EntryId(-1);        
        this.beginDate= beginDate;
        this.endDate = endDate;
        this.idExerciceBefore = idExerciceBefore;
        accounts = new LinkedHashMap<>();
        entries = new LinkedHashMap<>();
        this.lastIdEntry = new EntryId(lastIdEntry);        
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

    public ExerciceId getIdExerciceBefore() {
        return this.idExerciceBefore;
    }

    public Account getAccount(String account) {
        if(accounts.containsKey(account)) {
            //System.out.println("Exercice getAccount find account = " + account);
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

    public boolean exerciceIsClosed() {
        return this.exerciceClosed;
    }

    public void setExerciceClosed() {
        this.exerciceClosed = true;
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

    public Entry closeExercice(EntryId newIdEntry, DateEntry dateEntry) {
        String journal = "OD";
        String justificatif = "Clôture exercice précédent";
        Entry entry = new Entry(newIdEntry, dateEntry, journal, justificatif);
        Map <String, Double> accounts =  new LinkedHashMap<>();
        Collection<Entry> entriesInExercice = getEntries();
        for(Entry entryItem : entriesInExercice) {                  
            Collection<LineEntry> linesEntryInEntry = entryItem.getLinesEntry();
            for(LineEntry lineEntry : linesEntryInEntry) {                
                Double newAmount = 0.0;                
                Account account = lineEntry.getAccount();                
                Double amountDebit = lineEntry.getAmountDebit();                
                Double amountCredit = lineEntry.getAmountCredit();                
                newAmount = amountDebit - amountCredit;                                             
                if(accounts.containsKey(account.getName())) {                
                    newAmount +=  accounts.get(account.getName());                    
                }
                accounts.put(account.getName(), newAmount);
            }           
        }
        LineEntryId idLineEntry = new LineEntryId();
        System.out.println("acoounts.size() = " + accounts.size());
        for(Map.Entry<String, Double> accountItem: accounts.entrySet()) {
            Double amountDebit = 0.0;
            Double amountCredit = 0.0;
            Account account = getAccount(accountItem.getKey());            
            if(accountItem.getValue() >= 0) {
                amountDebit = accountItem.getValue();
                if(amountDebit<0.1) amountDebit = 0.0;
            } else {
                amountCredit = -1.0 * accountItem.getValue();
                if(amountCredit<0.1) amountCredit = 0.0;
            }
            
            LineEntry lineEntry = new LineEntry(idLineEntry, account, amountDebit, amountCredit);
            entry.addLineEntry(lineEntry);
            idLineEntry = idLineEntry.nextId();
        }
        return entry;
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
