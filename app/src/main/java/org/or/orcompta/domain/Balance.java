package org.or.orcompta.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Balance {
    
private final BalanceId idBalance;
private Exercice exercice;
private DateEntry dateBegin;
private DateEntry dateEnd;
private Map<String, Double[]> accounts;
private Map<String, String> accountsLibelle;
private Double totalAmountDebit;
private Double totalAmountCredit;
private Double soldeProduit;
private Double soldeCharge;

public Balance(BalanceId idBalance, Exercice exercice, DateEntry dateBegin, DateEntry dateEnd) {
    this.idBalance = idBalance;
    this.exercice = exercice;
    this.dateBegin = dateBegin;
    this.dateEnd = dateEnd;
    accounts = new TreeMap<>();
    accountsLibelle = new LinkedHashMap<>();
    totalAmountDebit = 0.0;
    totalAmountCredit = 0.0;
    soldeProduit = 0.0;
    soldeCharge = 0.0;
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

public Map<String, Double[]> getAccounts() {
    return accounts;
}

public Map<String, String> getAccountsLibelle() {
    return accountsLibelle;
}

public String getAccountLibelle(String key) {
    String libelle = "";
    for(Map.Entry<String, String> libelleItem: accountsLibelle.entrySet()) {
        if(libelleItem.getKey().equals(key)) {
            libelle = libelleItem.getValue();
        }
    }
    return libelle;
}

public void computeBalance() {    
    Collection<Entry> entriesInExercice = exercice.getEntries();
    for(Entry entry : entriesInExercice) {        
        DateEntry date = entry.getDate();
        System.out.println("Balance computeBalance" + " dateBegin = " + dateBegin + " dateEnd = " + dateEnd + " date = " + date);
        if(date.between(dateBegin, dateEnd)) {
            Collection<LineEntry> linesEntryInEntry = entry.getLinesEntry();
            for(LineEntry lineEntry : linesEntryInEntry) {
                Account account = lineEntry.getAccount();                
                Double amountDebit = lineEntry.getAmountDebit();                
                Double amountCredit = lineEntry.getAmountCredit();
                totalAmountDebit += amountDebit;
                totalAmountCredit += amountCredit;
                if(account.getName().substring(0,1).compareTo("6") == 0) {                    
                    soldeCharge += amountDebit - amountCredit;                    
                }
                if(account.getName().substring(0,1).compareTo("7") == 0) {
                    soldeProduit += amountCredit - amountDebit;
                }
                if(accounts.containsKey(account.getName())) {
                    amountDebit +=  accounts.get(account.getName())[0];
                    amountCredit +=  accounts.get(account.getName())[1];
                }
                Double [] amounts = {amountDebit, amountCredit};
                accounts.put(account.getName(), amounts);
                accountsLibelle.put(account.getName(), account.getDescription());                
            }        
        }
    }
}

public String toString() {
    String output = new String();
    output =  "Montant debit total = " + totalAmountDebit + " Montant credit total = " + totalAmountCredit + " " + "Total produit = " +
    soldeProduit + " Total charges = " + soldeCharge + " Resultat de l'exercice = " + (soldeProduit-soldeCharge) + "\n";
    for(Map.Entry<String, Double []> account: accounts.entrySet()) {
        output += " " + account.getKey() + " " + account.getValue()[0] + " " + account.getValue()[1] + "\n";
    }
    return output;
}

}
