package org.or.orcompta.application;

import java.util.Collection;

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

    public Collection<Entry> getEntriesInExercice(CompanyId idCompany, ExerciceId idExercice) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        return exercice.getEntries();
    }

    public Collection<LineEntry> getLInesEntryInEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        Entry entry = exercice.getEntry(idEntry);
        return entry.getLinesEntry();
    }

    public Collection<Account> getAccountsInExercice(CompanyId idCompany, ExerciceId idExercice) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        return exercice.getAccounts();
    }

    public Entry getEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        Entry entry = exercice.getEntry(idEntry);
        return entry;
    }

    public LineEntry getLineEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry,LineEntryId idLineEntry) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        Entry entry = exercice.getEntry(idEntry);
        LineEntry lineEntry = entry.getLineEntry(idLineEntry);
        return lineEntry;
    }

    public double getEntryTotalDebit(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Entry entry = getEntry(idCompany, idExercice, idEntry);
        return entry.getAmountDebit();
    }

    public double getEntryTotalCredit(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Entry entry = getEntry(idCompany, idExercice, idEntry);
        return entry.getAmountCredit();
    }    

    public EntryId computeIdNewEntry(EntryId idEntry) {
        return idEntry.nextId();
    }

    public LineEntryId computeIdNewLineEntry(LineEntryId idLineEntry) {
        return idLineEntry.nextId();
    }

    public LineEntryId resetIdLineEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Entry entry = getEntry(idCompany, idExercice, idEntry);
        return entry.resetIdLineEntry();
    }

    public Account createNewAccount(CompanyId idCompany, ExerciceId idExercice, String account, String description) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice);
        Account newAccount = exercice.createNewAccount(account, description);
        return newAccount;
    }

    public String toString() {
        return "companies : " + companies;
    }

    
}
