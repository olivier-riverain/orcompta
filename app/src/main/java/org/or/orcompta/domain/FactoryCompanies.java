package org.or.orcompta.domain;

import java.util.HashMap;
import java.util.Map;

public class FactoryCompanies {
    private Map<CompanyId, Company> companies;
    private CompanyId lastIdcompany = null;

    public FactoryCompanies() {
        this.companies = new HashMap<>();
        lastIdcompany = new CompanyId(-1);
    }

    public CompanyId addCompany(String name, AddressCompany addressCompany, String legalForm, String siret, String naf, Double shareCapital, String saveDirectory) {        
        CompanyId idNewCompany = getNewIdCompany();
        Company newCompany = new Company(idNewCompany,name, addressCompany, legalForm, siret, naf, shareCapital, saveDirectory);       
        companies.put(idNewCompany, newCompany);
        lastIdcompany = idNewCompany;
        System.out.println("addCompany " + newCompany);
        return idNewCompany;
    }

    public CompanyId getLastCompanyId() {
        return lastIdcompany;
    }
    
    public CompanyId getNewIdCompany() {
        return new CompanyId(this.lastIdcompany.nextId());    
    }

    public Company getCompany(CompanyId idCompany) {
        if(companies.containsKey(idCompany)) {
            return companies.get(idCompany);
        }        
        return null;
    }
}
