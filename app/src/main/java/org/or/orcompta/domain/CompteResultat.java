package org.or.orcompta.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class CompteResultat {

    private final CompteResultatId idCompteResultat;
    private Map<String, Double> compteResultat;
    private Balance balance;
    private String[] codeResultat1 = {"FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW"};
    private String[] codeResultat2 =  {"HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HY", "1G", "HP", "HQ", "1H", "1J", "1K", "HX", "A1", "A2", "A3", "A4", "A6", "A9"} ;

    public CompteResultat(CompteResultatId idCompteResultat, Balance balance) {
        this.idCompteResultat = idCompteResultat;
        compteResultat = new LinkedHashMap<>();
        initCompteResultat();
    
    }

    private void initCompteResultat() {
        for(String code: codeResultat1) {
            compteResultat.put(code, 0.0);
        }
        for(String code: codeResultat2) {
            compteResultat.put(code, 0.0);
        }
    }

    public void createCompteResultat(Balance balance) {        
        for(Map.Entry<String, Double[]> account : balance.getAccounts().entrySet()) {
            String cpte = account.getKey();
            Double debit = account.getValue()[0];
            Double credit = account.getValue()[1];
            String first = cpte.substring(0, 1);            
            switch(first) {
                case "6":


                break;

                case "7":
                    if (cpte.substring(0,3).equals("707") || cpte.substring(0,4).equals("7097")) compteResultat.put("FA",  compteResultat.get("FA") + credit - debit);
                    if (cpte.substring(0,3).equals("707") || cpte.substring(0,4).equals("7097")) compteResultat.put("FC",  compteResultat.get("FC") + credit - debit);      
                
                    if (cpte.substring(0,3).equals("701")|| cpte.substring(0,3).equals("702") || cpte.substring(0,3).equals("703") || cpte.substring(0,4).equals("7091")) compteResultat.put("FD",  compteResultat.get("FD") + credit - debit);
                    if (cpte.substring(0,3).equals("701")|| cpte.substring(0,3).equals("702") || cpte.substring(0,3).equals("703") || cpte.substring(0,4).equals("7091")) compteResultat.put("FF",  compteResultat.get("FF") + credit - debit);

                    if (cpte.substring(0,3).equals("704")|| cpte.substring(0,3).equals("705") || cpte.substring(0,3).equals("706")) compteResultat.put("FG",  compteResultat.get("FG") + credit - debit);
                    if (cpte.substring(0,3).equals("704")|| cpte.substring(0,3).equals("705") || cpte.substring(0,3).equals("706")) compteResultat.put("FI",  compteResultat.get("FI") + credit - debit);

                    if (cpte.substring(0,4).equals("7094")|| cpte.substring(0,4).equals("7095") || cpte.substring(0,4).equals("7096") || cpte.substring(0,4).equals("7098")) compteResultat.put("FG",  compteResultat.get("FG") + credit - debit);
                    if (cpte.substring(0,4).equals("7094")|| cpte.substring(0,4).equals("7095") || cpte.substring(0,4).equals("7096") || cpte.substring(0,4).equals("7098")) compteResultat.put("FI",  compteResultat.get("FI") + credit - debit);

                    if (cpte.substring(0,2).equals("71")) compteResultat.put("FM",  compteResultat.get("FM") + credit - debit);
                    if (cpte.substring(0,2).equals("72")) compteResultat.put("FN",  compteResultat.get("FN") + credit - debit);
                    if (cpte.substring(0,2).equals("74")) compteResultat.put("FO",  compteResultat.get("FO") + credit - debit);
                    if (cpte.substring(0,3).equals("781") || cpte.substring(0,3).equals("791")) compteResultat.put("FP",  compteResultat.get("FP") + credit - debit);
                    if (cpte.substring(0,2).equals("75")) compteResultat.put("FQ",  compteResultat.get("FQ") + credit - debit);


                    
                break;

                default:

                break;
                    
            }
        }
    }
    
}
