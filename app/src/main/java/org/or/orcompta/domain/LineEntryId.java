package org.or.orcompta.domain;

public class LineEntryId {
     private final Integer idLineEntry;

    public LineEntryId(Integer idLineEntry) {
        this.idLineEntry = idLineEntry;
    }

    public LineEntryId() {
        this.idLineEntry = 0;
    }

    public Integer getId() {
        return this.idLineEntry;
    }

    public LineEntryId nextId() {
        return new LineEntryId(getId() + 1);
    }

    public LineEntryId resetId() {
        return new LineEntryId(0);
    }

    public String toString() {
        return "" + this.idLineEntry;
    }
}
