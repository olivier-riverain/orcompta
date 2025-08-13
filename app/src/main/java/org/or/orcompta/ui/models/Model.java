package org.or.orcompta.ui.models;

import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.ExerciceId;
import org.or.orcompta.domain.LineEntryId;

public class Model {
    
    private CompanyId idCompany;
    private ExerciceId idExercice;
    private EntryId idEntry;
    private EntryId idEntryLoaded;
    private LineEntryId idLineEntry;

    public Model() {
        this.idCompany = new CompanyId(0);
        this.idExercice = new ExerciceId(0);
    }

    public CompanyId getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(CompanyId idCompany) {
        this.idCompany = idCompany;
    }

    public ExerciceId getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(ExerciceId idExercice) {
        this.idExercice = idExercice;
    }

    public EntryId getIdEntry() {
        return idEntry;
    }    

    public void setIdEntry(EntryId idEntry) {
        this.idEntry = idEntry;
    }

    public EntryId getIdEntryLoaded() {
        return idEntryLoaded;
    }    

    public void setIdEntryLoaded(EntryId idEntryLoaded) {
        this.idEntryLoaded = idEntryLoaded;
    }

    public LineEntryId getIdLineEntry() {
        return idLineEntry;
    }
    
    public void setIdLineEntry(LineEntryId idLineEntry) {
        this.idLineEntry = idLineEntry;
    }


}
