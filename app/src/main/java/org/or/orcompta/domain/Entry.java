package org.or.orcompta.domain;

import java.util.HashMap;
import java.util.Map;

public class Entry {

    private final EntryId idEntry;
    private LineEntryId lastIdLineEntry;
    private LineEntryId newIdLineEntry;  
    private DateEntry date;
    private String journal;
    private String voucher;
    private Map <LineEntryId, LineEntry> linesEntry;
    private double amountDebit;
    private double amountCredit;

    
    public Entry(EntryId idEntry, DateEntry date, String journal, String voucher) {
        this.idEntry = idEntry;
        this.lastIdLineEntry = new LineEntryId(-1);
        this.date = date;
        this.journal= journal;
        this.voucher = voucher;
        linesEntry = new HashMap<>();
        this.amountDebit = 0.0;
        this.amountCredit = 0.0;
    }

    public EntryId getIdEntry() {
        return this.idEntry;
    }

    public LineEntryId getIdNewLineEntry() {
        return lastIdLineEntry.nextId();
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
