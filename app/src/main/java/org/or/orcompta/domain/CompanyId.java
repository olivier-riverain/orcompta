package org.or.orcompta.domain;

public class CompanyId {
    private final Integer idCompany;

    public CompanyId(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public CompanyId() {
         this.idCompany = -1;
    }

    public CompanyId(String idCompany) {
         this.idCompany = Integer.parseInt(idCompany);
    }

    public Integer getId() {
        return this.idCompany;
    }

    public Integer nextId() {
        return getId() + 1;
    }

    public String toString() {
        return "" + this.idCompany;
    }
}
