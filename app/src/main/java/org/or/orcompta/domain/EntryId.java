package org.or.orcompta.domain;

public class EntryId {
    
    private final Integer idEntry;

    public EntryId(Integer idEntry) {
        this.idEntry = idEntry;
    }

    public EntryId(String idEntry) {
        this.idEntry = Integer.parseInt(idEntry);
    }

    public EntryId() {
        this.idEntry = -1;
    }

    public EntryId getId() {
        return new EntryId(this.idEntry);
    }      

    public EntryId nextId() {
        return new EntryId(this.idEntry + 1);
    }

    public String toString() {
        return "" + this.idEntry;
    }
}
