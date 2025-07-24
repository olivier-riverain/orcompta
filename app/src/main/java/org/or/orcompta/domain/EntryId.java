package org.or.orcompta.domain;

public class EntryId {
    
    private final Integer idEntry;

    public EntryId(Integer idEntry) {
        this.idEntry = idEntry;
    }

    public Integer getId() {
        return this.idEntry;
    }      

    public EntryId nextId() {
        return new EntryId(getId() + 1);
    }

    public String toString() {
        return "" + this.idEntry;
    }
}
