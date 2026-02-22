package org.or.orcompta.domain;

public class CompteResultatId {
private final Integer idCompteResultat;
    
    public CompteResultatId(Integer idCompteResultat) {
        this.idCompteResultat = idCompteResultat;
    }

    public Integer getId() {
        return this.idCompteResultat;
    }    

    public CompteResultatId nextId() {
        return new CompteResultatId(getId() + 1);
    }

    public String toString() {
        return "" + this.idCompteResultat;
    }      
}
