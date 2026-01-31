package org.or.orcompta.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class FactoryCompanies {
    private Map<CompanyId, Company> companies;
    private CompanyId lastIdcompany = null;

    public FactoryCompanies() {
        this.companies = new LinkedHashMap<>();
        lastIdcompany = new CompanyId();
    }

    public CompanyId addCompany(String name, AddressCompany addressCompany, String legalForm, String siret, String naf, Double shareCapital, String saveDirectory) {        
        CompanyId idNewCompany = getNewIdCompany();
        Company newCompany = new Company(idNewCompany, name, addressCompany, legalForm, siret, naf, shareCapital, saveDirectory);       
        companies.put(idNewCompany, newCompany);
        lastIdcompany = idNewCompany;        
        return idNewCompany;
    }

    public void addCompany(Company company) {
        companies.put(company.getIdCompany(), company);
    }

    public CompanyId getLastCompanyId() {
        return lastIdcompany;
    }
    
    public CompanyId getNewIdCompany() {
        return new CompanyId(this.lastIdcompany.nextId());    
    }

    public Company getCompany(CompanyId idCompany) {        
        for(Map.Entry<CompanyId, Company> companyItem : companies.entrySet()) {
            if(companyItem.getKey().equals(idCompany)) return companyItem.getValue(); 
        }
        return null;
    }

    public Map<CompanyId, Company> getCompanies() {
        return companies;
    }

    
}
