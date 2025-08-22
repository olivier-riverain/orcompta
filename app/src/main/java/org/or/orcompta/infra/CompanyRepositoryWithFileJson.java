package org.or.orcompta.infra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import org.or.orcompta.domain.Company;
import org.or.orcompta.domain.CompanyRepository;


public class CompanyRepositoryWithFileJson  implements CompanyRepository{
    private final File orcomptaConfigFile;

    public CompanyRepositoryWithFileJson(File orcomptaConfigFile) {
        this.orcomptaConfigFile = orcomptaConfigFile;
    }
    
    public Company findCompanyById(Integer idCompany) {
        return null;
    }

    public void saveCompany(Company company) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idCompany", company.getIdCompany());
        jsonObject.put("name", company.getName());
        jsonObject.put("saveDirectory", company.getSaveDirectory());
        try {
         FileWriter file = new FileWriter(orcomptaConfigFile);
         file.append(jsonObject.toString());
         file.close();
        } catch (IOException e) {         
         e.printStackTrace();
      }
    }

    public void updateCompany(Company company) {

    }

}
