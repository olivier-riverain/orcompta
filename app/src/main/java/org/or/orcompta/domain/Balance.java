package org.or.orcompta.domain;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Balance {
    
private final BalanceId idBalance;
private Exercice exercice;
private DateEntry dateBegin;
private DateEntry dateEnd;
private Map<Account, Double[]> accounts;

public Balance(BalanceId idBalance, Exercice exercice, DateEntry dateBegin, DateEntry dateEnd) {
    this.idBalance = idBalance;
    this.exercice = exercice;
    this.dateBegin = dateBegin;
    this.dateEnd = dateEnd;
    accounts = new TreeMap<>();
}

public BalanceId getIdBalance() {
        return this.idBalance;
    }

public DateEntry getDateBegin() {
    return this.dateBegin;
}

public DateEntry getDateEnd() {
    return this.dateEnd;
}

public void computeBalance() {    
    Collection<Entry> entriesInExercice = exercice.getEntries();
    for(Entry entry : entriesInExercice) {
        DateEntry date = entry.getDate();
        if(date.between(dateBegin, dateEnd)) {
            Collection<LineEntry> linesEntryInEntry = entry.getLinesEntry();
            for(LineEntry lineEntry : linesEntryInEntry) {
                Account account = lineEntry.getAccount();
                Double amountDebit = lineEntry.getAmountCredit();
                Double amountCredit = lineEntry.getAmountCredit();
                if(accounts.containsKey(account)) {
                    amountDebit +=  accounts.get(account)[0];
                    amountCredit +=  accounts.get(account)[1];
                }
                Double [] amounts = {amountDebit, amountCredit};
                accounts.put(account, amounts);
            }        
        }
    }
}

public String toString() {
    return accounts.toString();
}

}
