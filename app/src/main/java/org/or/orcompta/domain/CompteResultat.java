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
                    if (cpte.substring(0,3).equals("707")) compteResultat.put("FA",  compteResultat.get("FA") + credit - debit);
                    if (cpte.substring(0,3).equals("707")) compteResultat.put("FC",  compteResultat.get("FC") + credit - debit);      
                




//#if ( (substr($_,0,3) eq "701") || (substr($_,0,3) eq "702") || (substr($_,0,3) eq "703") )  { $cr {FD} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//#if ( (substr($_,0,3) eq "701") || (substr($_,0,3) eq "702") || (substr($_,0,3) eq "703") )  { $cr {FE} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//if ( (substr($_,0,3) eq "701") || (substr($_,0,3) eq "702") || (substr($_,0,3) eq "703") )  { $cr {FF} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//#if ( (substr($_,0,3) eq "704") || (substr($_,0,3) eq "705") || (substr($_,0,3) eq "706") || (substr($_,0,3) eq "708") )  { $cr {FG} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//#if ( (substr($_,0,3) eq "704") || (substr($_,0,3) eq "705") || (substr($_,0,3) eq "706") || (substr($_,0,3) eq "708") )  { $cr {FH} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//if ( (substr($_,0,3) eq "704") || (substr($_,0,3) eq "705") || (substr($_,0,3) eq "706")  || (substr($_,0,3) eq "708") )  { $cr {FI} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//#if (substr($_,0,4) eq "7097")   { $cr {FA} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//#if (substr($_,0,4) eq "7097")   { $cr {FB} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if (substr($_,0,4) eq "7097")   { $cr {FC} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//#if ( (substr($_,0,4) eq "7091") || (substr($_,0,4) eq "7092")  )  { $cr {FD} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//#if ( (substr($_,0,4) eq "7091") || (substr($_,0,4) eq "7092")  )  { $cr {FE} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if ( (substr($_,0,4) eq "7091") || (substr($_,0,4) eq "7092")  )  { $cr {FF} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//#if ( (substr($_,0,4) eq "7094") || (substr($_,0,4) eq "7095") || (substr($_,0,4) eq "7096")  || (substr($_,0,4) eq "7098") )  { $cr {FG} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//#if ( (substr($_,0,4) eq "7094") || (substr($_,0,4) eq "7095") || (substr($_,0,4) eq "7096")  || (substr($_,0,4) eq "7098") )  { $cr {FH} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if ( (substr($_,0,4) eq "7094") || (substr($_,0,4) eq "7095") || (substr($_,0,4) eq "7096")  || (substr($_,0,4) eq "7098") )  { $cr {FI} {montant} += $balance {$_} {montant_debit} - $balance {$_} {montant_credit} ; }

//if (substr($_,0,2) eq "71")   { $cr {FM} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//if (substr($_,0,2) eq "72")   { $cr {FN} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//if (substr($_,0,2) eq "74")   { $cr {FO} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//if ( (substr($_,0,3) eq "781") || (substr($_,0,3) eq "791") )  { $cr {FP} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

//if (substr($_,0,2) eq "75")   { $cr {FQ} {montant} += $balance {$_} {montant_credit} - $balance {$_} {montant_debit} ; }

                    
                break;

                default:

                break;
                    
            }
        }
    }
    
}
