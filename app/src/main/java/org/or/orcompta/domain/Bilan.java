package org.or.orcompta.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bilan {

    private final BilanId idBilan;
    private Map<String, Double> bilan;
    private Balance balance;
    private String[] actifCode = {"AA", "AAA","AA_AAA", "AB", "AC", "AB_AC", "CX", "CQ", "CX_CQ", "AF", "AG", "AF_AG", "AH", "AI", "AH_AI", "AJ", "AK", "AJ_AK", "AL", "AM", "AL_AM", "AN", "AO", "AN_AO", "AP", "AQ", "AP_AQ", "AR", "AS", "AR_AS", "AT", "AU", "AT_AU", "AV", "AW", "AV_AW", "AX", "AY", "AX_AY", "CS", "CT", "CS_CT", "CU", "CV", "CU_CV", "BB", "BC", "BB_BC", "BD", "BE", "BD_BE", "BF", "BG", "BF_BG", "BH", "BI", "BH_BI", "BJ", "BK", "BJ_BK", "BL", "BM", "BL_BM", "BN", "BO", "BN_BO", "BP", "BQ", "BP_BQ", "BR", "BS", "BR_BS", "BT", "BU", "BT_BU", "BV", "BW", "BV_BW", "BX", "BY", "BX_BY", "BZ", "CA", "BZ_CA", "CB", "CC", "CB_CC", "CD_AP", "CD", "CE", "CD_CE", "CF", "CG", "CF_CG", "CH", "CI", "CH_CI", "CJ", "CK", "CJ_CK", "CW", "CWW", "CW_CWW", "CM", "CMM", "CM_CMM", "CN", "CNN", "CN_CNN", "CO", "1A", "CO_1A", "CP", "CR"} ;
    private String[] passifCode = {"DA_V", "DA", "DB", "EK", "DC", "DD", "DE", "B1", "DF", "EJ", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "EI", "DV", "DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "1B", "1C", "1D", "1E", "EF", "EG", "EH"} ;

    public Bilan(BilanId idBilan, Balance balance) {
        this.idBilan = idBilan;
        this.balance = balance;
        bilan = new LinkedHashMap<>();
        initBilan();
    }

    private void initBilan() {
        for(String code: actifCode) {
            bilan.put(code, 0.0);
        }
        for(String code: passifCode) {
            bilan.put(code, 0.0);
        }

    }
    
    public void createBilan(Balance balance) {
        createActif();
        createPassif();
        for(Map.Entry<String, Double[]> account : balance.getAccounts().entrySet()) {
            String cpte = account.getKey();
            Double debit = account.getValue()[0];
             Double credit = account.getValue()[1];
            String first = cpte.substring(0, 1);            
            switch(first) {
                case "1":
                    // code block
                    if (cpte.substring(0,4).equals("1011") || cpte.substring(0,3).equals("109") ) bilan.put("AA",  bilan.get("AA") + debit - credit); 
                    bilan.put("AA_AAA", bilan.get("AA") - bilan.get("AAA"));





                    break;
                case "2":
                    // code block
                    if (cpte.substring(0,3).equals("201")) bilan.put("AB",  bilan.get("AB") + debit - credit); 
                    if (cpte.substring(0,4).equals("2801")) bilan.put("AC",  bilan.get("AC") + credit - debit);
                    bilan.put("AB_AC", bilan.get("AB") - bilan.get("AC"));

                    if (cpte.substring(0,3).equals("203")) bilan.put("CX",  bilan.get("CX") + debit - credit); 
                    if (cpte.substring(0,4).equals("2803")) bilan.put("CQ",  bilan.get("CQ") + credit - debit);
                    bilan.put("CX_CQ", bilan.get("CX") - bilan.get("CQ"));
                    
                    if (cpte.substring(0,3).equals("205")) bilan.put("AF",  bilan.get("AF") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2805")) || (cpte.substring(0,4).equals("2905"))) bilan.put("AG",  bilan.get("AG") + credit - debit);
                    bilan.put("AF_AG", bilan.get("AF") - bilan.get("AG"));

                    if ((cpte.substring(0,3).equals("206")) || (cpte.substring(0,3).equals("207"))) bilan.put("AH",  bilan.get("AH") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2806")) || (cpte.substring(0,4).equals("2907"))) bilan.put("AI",  bilan.get("AI") + credit - debit);
                    bilan.put("AF_AG", bilan.get("AF") - bilan.get("AG"));

                    if (cpte.substring(0,3).equals("208")) bilan.put("AJ",  bilan.get("AJ") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2808")) || (cpte.substring(0,4).equals("2908"))) bilan.put("AK",  bilan.get("AK") + credit - debit);
                    bilan.put("AJ_AK", bilan.get("AJ") - bilan.get("AK"));

                    if (cpte.substring(0,3).equals("237")) bilan.put("AL",  bilan.get("AL") + debit - credit); 
                    // il n'y a pas de compte amortissement et provision pour AM
                    bilan.put("AL_AM", bilan.get("AL") - bilan.get("AM"));

                    if ((cpte.substring(0,3).equals("211")) || (cpte.substring(0,3).equals("212"))) bilan.put("AN",  bilan.get("AN") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2811")) || (cpte.substring(0,4).equals("2812")) || (cpte.substring(0,4).equals("2911"))) bilan.put("AO",  bilan.get("AO") + credit - debit);
                    bilan.put("AN_AO", bilan.get("AN") - bilan.get("AO"));

                    if ((cpte.substring(0,3).equals("213")) || (cpte.substring(0,3).equals("214"))) bilan.put("AP",  bilan.get("AP") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2813")) || (cpte.substring(0,4).equals("2814"))) bilan.put("AQ",  bilan.get("AQ") + credit - debit);
                    bilan.put("AP_AQ", bilan.get("AP") - bilan.get("AQ"));

                    if (cpte.substring(0,3).equals("215")) bilan.put("AR",  bilan.get("AR") + debit - credit); 
                    if (cpte.substring(0,4).equals("2815")) bilan.put("AS",  bilan.get("AS") + credit - debit);
                    bilan.put("AR_AS", bilan.get("AR") - bilan.get("AS"));

                    if ((cpte.substring(0,3).equals("218")) || (cpte.substring(0,2).equals("22")) ) bilan.put("AT",  bilan.get("AT") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2818")) || (cpte.substring(0,3).equals("292")) || (cpte.substring(0,3).equals("282"))) bilan.put("AU",  bilan.get("AU") + credit - debit);
                    bilan.put("AP_AQ", bilan.get("AP") - bilan.get("AQ"));

                    if ((cpte.substring(0,3).equals("231")) || (cpte.substring(0,3).equals("232")) ) bilan.put("AV",  bilan.get("AV") + debit - credit); 
                    if ((cpte.substring(0,3).equals("293"))) bilan.put("AW",  bilan.get("AW") + credit - debit);
                    bilan.put("AV_AW", bilan.get("AV") - bilan.get("AW"));



//if (substr($_,0,3) eq "238") { $bilanp {AX} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; }
// il n'y a pas de compte amortissement  et provision pour AY
//$bilanp {AX_AY} {montant} = $bilanp {AX} {montant} - $bilanp {AY} {montant} ;

 //if (substr($_,0,3) eq "26") { $bilanp {CS} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; } 
// il n'y a pas de compte amortissement et provision pour CT
//$bilanp {CS_CT} {montant} = $bilanp {CS} {montant} - $bilanp {CT} {montant} ;

//if ( (substr($_,0,2) eq "25") || (substr($_,0,3) eq "261") || (substr($_,0,3) eq "266") ) { $bilanp {CU} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; }
//if ( (substr($_,0,4) eq "2961") || (substr($_,0,4) eq "2966") ) { $bilanp {CV} {montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; }
//$bilanp {CU_CV} {montant} = $bilanp {CU} {montant} - $bilanp {CV} {montant} ;

//if ( (substr($_,0,3) eq "267") || (substr($_,0,3) eq "268") || (substr($_,0,3) eq "269") ) { $bilanp {BB} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; }
//if ( (substr($_,0,4) eq "2967") || (substr($_,0,4) eq "2968") ) { $bilanp {BC} {montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; }
//$bilanp {BB_BC} {montant} = $bilanp {BB} {montant} - $bilanp {BC} {montant} ;

//if ( (substr($_,0,3) eq "271") || (substr($_,0,3) eq "272") || (substr($_,0,3) eq "273") ) { $bilanp {BD} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; }
//if ( (substr($_,0,4) eq "2971") || (substr($_,0,4) eq "2972") || (substr($_,0,4) eq "2973") ) { $bilanp {BE} {montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; }
//$bilanp {BD_BE} {montant} = $bilanp {BD} {montant} - $bilanp {BE} {montant} ;

//if (substr($_,0,3) eq "274") { $bilanp {BF} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; }
//if (substr($_,0,4) eq "2974") { $bilanp {BG} {montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; }
//$bilanp {BF_BG} {montant} = $bilanp {BF} {montant} - $bilanp {BG} {montant} ;

//if ( (substr($_,0,3) eq "275") || (substr($_,0,3) eq "276") || (substr($_,0,3) eq "277") || (substr($_,0,3) eq "279") ) { $bilanp {BH} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; }
//if ( (substr($_,0,4) eq "2975") || (substr($_,0,4) eq "2976") ) { $bilanp {BI} {montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; }
//$bilanp {BH_BI} {montant} = $bilanp {BH} {montant} - $bilanp {BI} {montant} ;



                    break;
                case "3":
                    // code block


                    break;
                case "4":
                    // code block


                    break;
                case "5":
                    // code block


                    break;
                default:
                    break;
                
            }

        }
    }

    private void createActif() {
        //codeBilan.put("AA", 0.0);
        

    }

    private void createPassif() {

    }

}
