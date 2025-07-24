package org.or.orcompta.domain;

public class CompanyId {
    private final Integer idCompany;

    public CompanyId(Integer idCompany) {
        this.idCompany = idCompany;
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
