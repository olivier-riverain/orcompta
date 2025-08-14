package org.or.orcompta.domain;

public class LineEntry {
    
    private final LineEntryId idLineEntry;
    private Account account;
    private double amountDebit = 0.0;
    private double amountCredit = 0.0;

    public LineEntry(LineEntryId idLineEntry, Account account, double amountDebit, double amountCredit) {
        this.idLineEntry = idLineEntry;
        this.account = account;
        this.amountDebit = amountDebit;
        this.amountCredit = amountCredit;
    }

    public LineEntryId getIdLineEntry() {
        return this.idLineEntry;
    }

    public Account getAccount() {
        return this.account;
    }

    public String getIdAccount() {
        return this.account.getName();
    }
    
     public String getAccountLibelle() {
        return this.account.getDescription();
    }

    public double getAmountDebit() {
        return amountDebit;
    }

    public double getAmountCredit() {
        return amountCredit;
    }
    
    public String toString() {
        return  " " + this.idLineEntry + " | " + this.account + " | " + this.amountDebit + " | " +  this.amountCredit;
    }
}
