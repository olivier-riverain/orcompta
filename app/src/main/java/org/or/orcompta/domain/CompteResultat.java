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
                    if (cpte.substring(0,3).equals("607")) compteResultat.put("FS",  compteResultat.get("FS") + debit - credit);
                    if (cpte.substring(0,4).equals("6037")) compteResultat.put("FT",  compteResultat.get("FT") + debit - credit);
                    if (cpte.substring(0,3).equals("601") || cpte.substring(0,3).equals("602")) compteResultat.put("FU",  compteResultat.get("FU") + debit - credit);
                    if (cpte.substring(0,4).equals("6031") || cpte.substring(0,4).equals("6032")) compteResultat.put("FV",  compteResultat.get("FV") + debit - credit);
                    if (cpte.substring(0,3).equals("604") || cpte.substring(0,3).equals("605") || cpte.substring(0,3).equals("606") || cpte.substring(0,2).equals("61") || cpte.substring(0,2).equals("62")) compteResultat.put("FW",  compteResultat.get("FW") + debit - credit);
                    if (cpte.substring(0,2).equals("63")) compteResultat.put("FX",  compteResultat.get("FX") + debit - credit);
                    if (cpte.substring(0,3).equals("641") || cpte.substring(0,3).equals("644")) compteResultat.put("FY",  compteResultat.get("FY") + debit - credit);
                    if (cpte.substring(0,3).equals("645") || cpte.substring(0,3).equals("646") || cpte.substring(0,3).equals("647") || cpte.substring(0,3).equals("648")) compteResultat.put("FZ",  compteResultat.get("FZ") + debit - credit);








//if (substr($_,0,4) eq "6811")   { $cr {GA} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if (substr($_,0,4) eq "6816")   { $cr {GB} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if (substr($_,0,4) eq "6817")   { $cr {GC} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if (substr($_,0,4) eq "6815")   { $cr {GD} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if ( (substr($_,0,3) eq "651") || (substr($_,0,3) eq "653") || (substr($_,0,3) eq "654") || (substr($_,0,3) eq "658") || (substr($_,0,4) eq "6812" ) )   { $cr {GE} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if (substr($_,0,4) eq "6551")   { $cr {GH} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//if (substr($_,0,4) eq "6555")   { $cr {GI} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }


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
