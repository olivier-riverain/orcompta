package org.or.orcompta.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleIntegerProperty;

public class Entry {

    private final EntryId idEntry;
    private LineEntryId lastIdLineEntry;
    private LineEntryId newIdLineEntry;  
    private DateEntry date;
    private String journal;
    private String voucher;
    private Map <LineEntryId, LineEntry> linesEntry;
    //private Integer nbLinesEntry;
    private SimpleIntegerProperty nbLinesEntry;
    private double amountDebit;
    private double amountCredit;

    
    public Entry(EntryId idEntry, DateEntry date, String journal, String voucher) {
        this.idEntry = idEntry;
        this.lastIdLineEntry = new LineEntryId(-1);
        this.date = date;
        this.journal = journal;
        this.voucher = voucher;
        linesEntry = new HashMap<>();
        //this.nbLinesEntry = 0;
        this.nbLinesEntry = new SimpleIntegerProperty(0);
        this.amountDebit = 0.0;
        this.amountCredit = 0.0;
    }

    public EntryId getIdEntry() {
        return this.idEntry;
    }

    public DateEntry getDate() {
        return this.date;
    }

    public String getJournal() {
        return this.journal;
    }

    public String getVoucher() {
        return this.voucher;
    }

    /*public Integer getNbLinesEntry() {
        return this.nbLinesEntry;
    }*/
    public Integer getNbLinesEntry() {
        return this.nbLinesEntry.get();
    }

    public double getAmountDebit() {
        return this.amountDebit;
    }

    public double getAmountCredit() {
        return this.amountCredit;
    }

    public LineEntryId getIdNewLineEntry() {
        return lastIdLineEntry.nextId();
    }

    public LineEntryId resetIdLineEntry() {
        return new LineEntryId();
    }

    public LineEntry getLineEntry(LineEntryId idLineEntry) {
        if(linesEntry.containsKey(idLineEntry)) {
            return linesEntry.get(idLineEntry);
        }        
        return null;
    }

    public Collection<LineEntry> getLinesEntry() {
        return this.linesEntry.values();
    }

    boolean checkEntry() {

        return true;
    }

    void closeEntry() {

    }

    public void addLineEntry(LineEntry newLineEntry) {
        this.newIdLineEntry = newLineEntry.getIdLineEntry();        
        linesEntry.put(this.newIdLineEntry, newLineEntry);
        this.lastIdLineEntry = this.newIdLineEntry;
        //this.nbLinesEntry++;
        this.nbLinesEntry.set(this.nbLinesEntry.get()+1);
        System.out.println("Entry addLineEntry this.nbLinesEntry = " + getNbLinesEntry());
        this.amountDebit += newLineEntry.getAmountDebit();
        this.amountCredit += newLineEntry.getAmountCredit();
    }

    public void modifyLineEntry() {

    }

    public void deleteLineEntry() {

    }

    public boolean checkAmountsEntry() {
        return this.amountDebit == this.amountCredit;
    }

    public String toString() {
        return this.idEntry + " | " + this.date + " | " + this.journal + " | " +  this.voucher + "\n" + linesEntry;
    }

}
