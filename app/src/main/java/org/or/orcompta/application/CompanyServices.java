package org.or.orcompta.application;

import org.or.orcompta.domain.Account;
import org.or.orcompta.domain.AddressCompany;
import org.or.orcompta.domain.FactoryCompanies;
import org.or.orcompta.domain.LineEntry;
import org.or.orcompta.domain.LineEntryId;
import org.or.orcompta.domain.Company;
import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.DateEntry;
import org.or.orcompta.domain.Entry;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.Exercice;
import org.or.orcompta.domain.ExerciceId;

public class CompanyServices {

    private FactoryCompanies companies;
    
    public CompanyServices() {
        companies = new FactoryCompanies();
    }

    public CompanyId createNewCompany(String name, String numero, String address, String address2, String postalCode, String city, String legalForm, String siret, String naf, Double shareCapital) {
        AddressCompany addressCompany = new AddressCompany(numero, address, address2, postalCode, city);
        CompanyId idCompany = companies.addCompany(name, addressCompany, legalForm, siret, naf, shareCapital);        
        return idCompany;
    }

    public ExerciceId createNewExercice(CompanyId idCompany, String beginjj,  String beginmm,  String beginyy, String endjj, String endmm, String endyy) {
        Company company = companies.getCompany(idCompany);
        ExerciceId idExercice = company.getIdNewExercice();
        DateEntry dateBegin = new DateEntry(beginjj, beginmm, beginyy);
        DateEntry dateEnd = new DateEntry(endjj, endmm, endyy);
        Exercice newExercice = new Exercice(idExercice, dateBegin, dateEnd);
        company.addExercice(newExercice);
        // mettre à jour la bd
        System.out.println("createNewExercice " + newExercice);
        return idExercice;
    }
    
    public EntryId createNewEntry(CompanyId idCompany, ExerciceId idExercice, String jj, String mm, String yy, String journal, String voucher) {       
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        EntryId idEntry = exercice.getIdNewEntry();
        DateEntry date = new DateEntry(jj, mm, yy);
        Entry newEntry = new Entry(idEntry, date, journal, voucher);
        exercice.addEntry(newEntry);
        System.out.println("createNewEntry " + newEntry);        
        return idEntry;
    }

    public LineEntryId createNewLineEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry, String account, double amountDebit, double amountCredit) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        Entry entry = exercice.getEntry(idEntry);
        LineEntryId idLineEntry = entry.getIdNewLineEntry();
        Account newAccount = exercice.getAccount(account);
        LineEntry newLineEntry = new LineEntry(idLineEntry, newAccount, amountDebit, amountCredit);
        entry.addLineEntry(newLineEntry);
        System.out.println("createNewLineEntry " + newLineEntry);
        return idLineEntry;
    }

    public String toString() {
        return "companies : " + companies;
    }

    
}
