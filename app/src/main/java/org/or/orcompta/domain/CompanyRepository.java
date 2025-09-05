package org.or.orcompta.domain;

public interface CompanyRepository {
    public Company findCompanyById(CompanyId idCompany);
    public void saveCompany(Company company);
    public void updateCompany(Company company);    
}
