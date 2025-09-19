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

    @Override
    public String toString() {
        return "" + this.idCompany;
    }

    @Override
    public boolean equals(Object o) {        
        if(o == this) return true;
        if(!(o instanceof CompanyId)) return false;
        CompanyId id = (CompanyId) o;        
        return Integer.compare(idCompany, id.idCompany) == 0;
    }
}
