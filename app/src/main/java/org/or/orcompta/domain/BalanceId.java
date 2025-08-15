package org.or.orcompta.domain;

public class BalanceId {
    private final Integer idBalance;
    
    public BalanceId(Integer idBalance) {
        this.idBalance = idBalance;
    }

    public Integer getId() {
        return this.idBalance;
    }    

    public BalanceId nextId() {
        return new BalanceId(getId() + 1);
    }

    public String toString() {
        return "" + this.idBalance;
    }    
}
