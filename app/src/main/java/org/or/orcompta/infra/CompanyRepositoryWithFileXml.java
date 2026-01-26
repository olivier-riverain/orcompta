package org.or.orcompta.infra;

import org.or.orcompta.domain.Company;
import org.or.orcompta.domain.CompanyId;
import org.or.orcompta.domain.CompanyRepository;
import org.or.orcompta.domain.Exercice;
import org.or.orcompta.domain.ExerciceId;

public class CompanyRepositoryWithFileXml implements CompanyRepository{

    public Company findCompanyById(CompanyId idCompany) {
        return null;
    }

    public void saveCompany(Company company) {

    }

    public void updateCompany(Company company) {

    }

    public Exercice findExerciceById(CompanyId idCompany, ExerciceId idExercice) {
        return null;
    }

    public void saveExercice(Exercice newExercice) {

    }

    
    public void importExercice() {
        
    }

    public String getIdExerciceBefore(String idExercice) {
        return "";
    }
    
    
}
