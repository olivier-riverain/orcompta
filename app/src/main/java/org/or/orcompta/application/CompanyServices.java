package org.or.orcompta.application;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.or.orcompta.domain.Account;
import org.or.orcompta.domain.AddressCompany;
import org.or.orcompta.domain.Balance;
import org.or.orcompta.domain.BalanceId;
import org.or.orcompta.domain.FactoryCompanies;
import org.or.orcompta.domain.LineEntry;
import org.or.orcompta.domain.LineEntryId;
import org.or.orcompta.domain.Company;
import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.CompanyRepository;
import org.or.orcompta.domain.DateEntry;
import org.or.orcompta.domain.Entry;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.Exercice;
import org.or.orcompta.domain.ExerciceId;
import org.or.orcompta.infra.CompanyRepositoryWithFileJson;

public class CompanyServices {

    private FactoryCompanies companies;
    private CompanyRepository repository;
    
    public CompanyServices(File orcomptaConfigFile) {
        companies = new FactoryCompanies();
        repository = new CompanyRepositoryWithFileJson(orcomptaConfigFile);
    }

    public CompanyId createNewCompany(String name, String numero, String address, String address2, String postalCode, String city, String legalForm, String siret, String naf, Double shareCapital, String saveDirectory) {
        AddressCompany addressCompany = new AddressCompany(numero, address, address2, postalCode, city);
        CompanyId idCompany = companies.addCompany(name, addressCompany, legalForm, siret, naf, shareCapital, saveDirectory);        
        Company company = companies.getCompany(idCompany);
        repository.saveCompany(company);
        return idCompany;
    }

    public ExerciceId createNewExercice(CompanyId idCompany, String beginjj,  String beginmm, String beginyy, String endjj, String endmm, String endyy, String idExerciceBefore) {
        Company company = companies.getCompany(idCompany);
        ExerciceId idExercice = company.getIdNewExercice();
        DateEntry dateBegin = new DateEntry(beginjj, beginmm, beginyy);
        DateEntry dateEnd = new DateEntry(endjj, endmm, endyy);
        Exercice newExercice = new Exercice(idExercice, dateBegin, dateEnd, new ExerciceId(idExerciceBefore));
        company.addExercice(newExercice);
        repository.saveExercice(newExercice);        
        return idExercice;
    }
    
    public EntryId createNewEntry(CompanyId idCompany, ExerciceId idExercice, String jj, String mm, String yy, String journal, String voucher) {       
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        EntryId idEntry = exercice.getIdNewEntry();
        DateEntry date = new DateEntry(jj, mm, yy);
        Entry newEntry = new Entry(idEntry, date, journal, voucher);
        exercice.addEntry(newEntry);              
        return idEntry;
    }

    public LineEntryId createNewLineEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry, String account, double amountDebit, double amountCredit) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntry);
        LineEntryId idLineEntry = entry.getIdNewLineEntry();
        Account newAccount = exercice.getAccount(account);
        LineEntry newLineEntry = new LineEntry(idLineEntry, newAccount, amountDebit, amountCredit);
        entry.addLineEntry(newLineEntry);        
        return idLineEntry;
    }

    public Collection<Entry> getEntriesInExercice(CompanyId idCompany, ExerciceId idExercice) {
        Company company = companies.getCompany(idCompany);        
        Exercice exercice = company.getExercice(idExercice.toString());        
        return exercice.getEntries();
    }

    public Collection<LineEntry> getLInesEntryInEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntry);
        return entry.getLinesEntry();
    }

    public Collection<Account> getAccountsInExercice(CompanyId idCompany, ExerciceId idExercice) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());        
        return exercice.getAccounts();
    }

    public Entry getEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntry);
        return entry;
    }

    public LineEntry getLineEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntry,LineEntryId idLineEntry) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
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
        Exercice exercice = company.getExercice(idExercice.toString());
        Account newAccount = exercice.createNewAccount(account, description);
        return newAccount;
    }

    public String getAccountDescription(CompanyId idCompany, ExerciceId idExercice, String account) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        return exercice.getAccountDescription(account);
    }

    public Map<String, String> getJournals(CompanyId idCompany) {
        Company company = companies.getCompany(idCompany);
        Map<String, String> journals = company.getJournals();
        return journals;
    }

    public String getDateJJEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntryLoaded) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntryLoaded);
        return entry.getDateJJEntry();
    }

    public String getDateMMEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntryLoaded) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntryLoaded);
        return entry.getDateMMEntry();
    }

    public String getDateAAEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntryLoaded) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntryLoaded);
        return entry.getDateAAEntry();
    }

    public String getJournalEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntryLoaded) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntryLoaded);
        return entry.getJournal();
    }

    public String getVoucherEntry(CompanyId idCompany, ExerciceId idExercice, EntryId idEntryLoaded) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        Entry entry = exercice.getEntry(idEntryLoaded);
        return entry.getVoucher();
    }

    public BalanceId computeBalance(CompanyId idCompany, ExerciceId idExercice, String beginjj,  String beginmm, String beginyy, String endjj, String endmm, String endyy) {
        DateEntry dateBegin = new DateEntry(beginjj, beginmm, beginyy);
        DateEntry dateEnd = new DateEntry(endjj, endmm, endyy);
        return computeBalance(idCompany, idExercice, dateBegin, dateEnd);        
    }

    public BalanceId computeBalance(CompanyId idCompany, ExerciceId idExercice) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        DateEntry dateBegin = exercice.getBeginDate();
        DateEntry dateEnd = exercice.getEndDate();
        return computeBalance(idCompany, idExercice, dateBegin, dateEnd);
    }

    public BalanceId computeBalance(CompanyId idCompany, ExerciceId idExercice, DateEntry dateBegin, DateEntry dateEnd) {
        Company company = companies.getCompany(idCompany);
        Exercice exercice = company.getExercice(idExercice.toString());
        BalanceId idBalance = new BalanceId(0);
        Balance balance = new Balance(idBalance, exercice, dateBegin, dateEnd);
        balance.computeBalance();
        System.out.println("computeBalance => " + balance);
        return balance.getIdBalance();
    }



    public void editBalance(CompanyId idCompany, ExerciceId idExercice, String fromjj, String frommm, String fromaa, String tojj,  String tomm, String toaa) {
        BalanceId idBalance = computeBalance(idCompany, idExercice, fromjj, frommm, fromaa, tojj, tomm, toaa);
        System.out.println("idBalance = " + idBalance);
    }

    public void editBalance(CompanyId idCompany, ExerciceId idExercice) {
        BalanceId idBalance = computeBalance(idCompany, idExercice);
        System.out.println("idBalance = " + idBalance);    
    }

    
    public void loadCompany(String idCompany) {
        Company company = repository.findCompanyById(new CompanyId(idCompany));        
        if(companies.getCompany(company.getIdCompany()) == null) companies.addCompany(company);        
    }

    public Map<String, String> loadListExercicesFromCompany(String id) {        
        CompanyId idCompany = new CompanyId(id);        
        Company company = companies.getCompany(idCompany);        
        Map<String, String> listExercices = company.getListOfExercices();
        return listExercices;
    }

    public String loadExercice(String idCompany, String idExercice) {
        String idEntry = "";
        Company company = companies.getCompany(new CompanyId(idCompany));        
        if(company.getExercice(idExercice) == null) {
            Exercice exercice = repository.findExerciceById(company.getIdCompany(), new ExerciceId(idExercice));
            idEntry = exercice.getLastIdEntry().toString();
            company.addExercice(exercice);
        } else {
            Exercice exercice = company.getExercice(idExercice);
            idEntry = exercice.getLastIdEntry().toString();
        }
        return idEntry;       
    }

    public void importExercice() {
        repository.importExercice();          
    }

    public boolean closeExercice(String idCompany, String idExercice) {
        System.err.println("CompanyServices closeExercice");
        Company company = companies.getCompany(new CompanyId(idCompany));
        Exercice exercice = company.getExercice(idExercice);
        if(exercice.exerciceIsClosed()) return true;        
        String idExerciceAfter = getExerciceAfter(company, idExercice);
        loadExercice(idCompany, idExerciceAfter);
        Exercice exerciceAfter = company.getExercice(idExerciceAfter);
        DateEntry newDateEntry = exerciceAfter.getBeginDate();        
        Entry entry = exercice.closeExercice(exerciceAfter.getIdNewEntry(), newDateEntry);
        exerciceAfter.addEntry(entry);
        Collection<Account> accounts = exercice.getAccounts();
        Collection<Account> accountsExerciceAfter = exerciceAfter.getAccounts();
        Iterator<Account> iterator = accounts.iterator();
        Iterator<Account> iteratorAfter = accountsExerciceAfter.iterator();
        ArrayList<String> list = new ArrayList<>();        
        while (iteratorAfter.hasNext()) {
            Account accountItem = iteratorAfter.next();
            list.add(accountItem.getName());            
        }              
        while (iterator.hasNext()) {
            Account accountItem = iterator.next();
            if(!list.contains(accountItem.getName())) 
                exerciceAfter.addAccount(accountItem.getName(), accountItem.getDescription());
        }              
        exercice.setExerciceClosed();
        repository.saveExercice(exerciceAfter);
        repository.saveExercice(exercice);
        return false;
    }

    public String getExerciceAfter(Company company, String idExercice) {
        String idExerciceAfter = "";
        Map<String, String> listOfExercices = company.getListOfExercices();
        for(Map.Entry<String, String> exerciceItem: listOfExercices.entrySet()) {
            String idExerciceItem = exerciceItem.getKey();
            String idExerciceBefore = repository.getIdExerciceBefore(idExerciceItem);
            if(idExerciceBefore.equals(idExercice)) {
                idExerciceAfter = idExerciceItem;
                break;
            }
        }
        return idExerciceAfter;
    }

    @Override
    public String toString() {
        return "companies : " + companies;
    }

    

    
   
}
