package org.or.orcompta.infra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
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
        String name = company.getName();
        String idCompany = company.getIdCompany().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idCompany", idCompany);
        jsonObject.put("name", name);
        jsonObject.put("address", company.getAddress());
        jsonObject.put("legalForm", company.getLegalForm());
        jsonObject.put("siret", company.getSiret());
        jsonObject.put("naf", company.getNaf());
        jsonObject.put("saveDirectory", company.getSaveDirectory());
        name = name.replace(' ', '-');
        File fileName = new File(company.getSaveDirectory() + idCompany + "-" + name  + ".json");
        try {
            fileName.createNewFile();
            FileWriter file = new FileWriter(fileName);
            file.write(jsonObject.toString());        
            file.close();
        } catch (IOException e) {         
         e.printStackTrace();
      }
    }

    public void updateCompany(Company company) {

    }

}
