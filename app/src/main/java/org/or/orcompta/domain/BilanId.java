package org.or.orcompta.domain;

public class BilanId {
    private final Integer idBilan;
    
    public BilanId(Integer idBilan) {
        this.idBilan = idBilan;
    }

    public Integer getId() {
        return this.idBilan;
    }    

    public BilanId nextId() {
        return new BilanId(getId() + 1);
    }

    public String toString() {
        return "" + this.idBilan;
    }      
}
