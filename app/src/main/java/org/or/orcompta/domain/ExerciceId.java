package org.or.orcompta.domain;

public class ExerciceId {
    private final Integer idExercice;

    public ExerciceId(Integer idExercice) {
        this.idExercice = idExercice;
    }

    public ExerciceId() {
        this.idExercice = -1;
    }

    public ExerciceId(String idExercice) {
        this.idExercice = Integer.parseInt(idExercice);           
    }

    public Integer getId() {
        return this.idExercice;
    }    

    public ExerciceId nextId() {
        return new ExerciceId(getId() + 1);
    }

    public String toString() {
        return "" + this.idExercice;
    }
    
    public boolean exerciceNotNull() {
        return this.getId() != -1;
    }
}
