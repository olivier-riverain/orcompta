package org.or.orcompta.domain;

public interface CompanyRepository {
    public Company findCompanyById(CompanyId idCompany);
    public void saveCompany(Company company);
    public void updateCompany(Company company);
    public Exercice findExerciceById(CompanyId idCompany, ExerciceId idExercice);
    public void saveExercice(Exercice newExercice);
    public void importExercice();
    public String getIdExerciceBefore(String idExercice);
      
}
