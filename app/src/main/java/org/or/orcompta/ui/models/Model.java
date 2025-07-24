package org.or.orcompta.ui.models;

import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.EntryId;
import org.or.orcompta.domain.ExerciceId;

public class Model {
    
    private CompanyId idCompany;
    private ExerciceId idExercice;
    private EntryId idEntry;

    public Model() {
        this.idCompany = null;
    }

    public CompanyId getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(CompanyId idCompany) {
        this.idCompany = idCompany;
    }

    public ExerciceId getIdexercice() {
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


}
