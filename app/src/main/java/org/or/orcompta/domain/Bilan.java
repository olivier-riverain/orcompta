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

                    if (cpte.substring(0,3).equals("238")) bilan.put("AX",  bilan.get("AX") + debit - credit); 
                    // il n'y a pas de compte amortissement et provision pour AY
                    bilan.put("AX_AY", bilan.get("AX") - bilan.get("AY"));

                    if (cpte.substring(0,2).equals("26")) bilan.put("CS",  bilan.get("CS") + debit - credit); 
                    // il n'y a pas de compte amortissement et provision pour CT
                    bilan.put("CS_CT", bilan.get("CS") - bilan.get("CT"));

                    if ((cpte.substring(0,2).equals("25")) || (cpte.substring(0,3).equals("261")) || (cpte.substring(0,3).equals("266"))) bilan.put("CU",  bilan.get("CU") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2961")) || (cpte.substring(0,4).equals("2966"))) bilan.put("CV",  bilan.get("CV") + credit - debit);
                    bilan.put("CU_CV", bilan.get("CU") - bilan.get("CV"));

                    if ((cpte.substring(0,3).equals("267")) || (cpte.substring(0,3).equals("268")) || (cpte.substring(0,3).equals("269"))) bilan.put("BB",  bilan.get("BB") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2967")) || (cpte.substring(0,4).equals("2968"))) bilan.put("BC",  bilan.get("BC") + credit - debit);
                    bilan.put("BB_BC", bilan.get("BB") - bilan.get("BC"));

                    if ((cpte.substring(0,3).equals("271")) || (cpte.substring(0,3).equals("272")) || (cpte.substring(0,3).equals("273"))) bilan.put("BD",  bilan.get("BD") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2971")) || (cpte.substring(0,4).equals("2972")) || (cpte.substring(0,4).equals("2973"))) bilan.put("BE",  bilan.get("BE") + credit - debit);
                    bilan.put("BB_BC", bilan.get("BB") - bilan.get("BC"));

                    if (cpte.substring(0,3).equals("274")) bilan.put("BF",  bilan.get("BF") + debit - credit); 
                    if (cpte.substring(0,4).equals("2974")) bilan.put("BG",  bilan.get("BG") + credit - debit);
                    bilan.put("BF_BG", bilan.get("BF") - bilan.get("BG"));

                    if ((cpte.substring(0,3).equals("275")) || (cpte.substring(0,3).equals("276")) || (cpte.substring(0,3).equals("277")) || (cpte.substring(0,3).equals("279"))) bilan.put("BH",  bilan.get("BH") + debit - credit); 
                    if ((cpte.substring(0,4).equals("2975")) || (cpte.substring(0,4).equals("2976")) || (cpte.substring(0,4).equals("2973"))) bilan.put("BI",  bilan.get("BI") + credit - debit);
                    bilan.put("BB_BC", bilan.get("BB") - bilan.get("BC"));
                    break;
                
                case "3":
                    // code block
                    if ((cpte.substring(0,2).equals("31")) || (cpte.substring(0,2).equals("32"))) bilan.put("BL",  bilan.get("BL") + debit - credit); 
                    if ((cpte.substring(0,3).equals("391")) || (cpte.substring(0,3).equals("392"))) bilan.put("BI",  bilan.get("BM") + credit - debit);
                    bilan.put("BL_BM", bilan.get("BL") - bilan.get("BM"));

                    if (cpte.substring(0,2).equals("33")) bilan.put("BN",  bilan.get("BN") + debit - credit); 
                    if (cpte.substring(0,3).equals("393")) bilan.put("BO",  bilan.get("BO") + credit - debit);
                    bilan.put("BN_BO", bilan.get("BN") - bilan.get("BO"));

                    if (cpte.substring(0,2).equals("34")) bilan.put("BP",  bilan.get("BP") + debit - credit); 
                    if (cpte.substring(0,3).equals("394")) bilan.put("BQ",  bilan.get("BQ") + credit - debit);
                    bilan.put("BP_BQ", bilan.get("BP") - bilan.get("BQ"));

                    if (cpte.substring(0,2).equals("35")) bilan.put("BR",  bilan.get("BR") + debit - credit); 
                    if (cpte.substring(0,3).equals("395")) bilan.put("BS",  bilan.get("BS") + credit - debit);
                    bilan.put("BR_BS", bilan.get("BR") - bilan.get("BS"));

                    if (cpte.substring(0,2).equals("37")) bilan.put("BT",  bilan.get("BT") + debit - credit); 
                    if (cpte.substring(0,3).equals("397")) bilan.put("BU",  bilan.get("BU") + credit - debit);
                    bilan.put("BT_BU", bilan.get("BT") - bilan.get("BU"));

                    break;
                
                case "4":
                    // code block
                    if (cpte.substring(0,4).equals("4091")) bilan.put("BV",  bilan.get("BV") + debit - credit); 
                    //il n'y a pas de compte provision pour BW
                    bilan.put("BV_BW", bilan.get("BV") - bilan.get("BW"));

                    if ((cpte.substring(0,3).equals("410")) || (cpte.substring(0,3).equals("411"))  || (cpte.substring(0,3).equals("413")) || (cpte.substring(0,3).equals("416")  || (cpte.substring(0,3).equals("418")))) bilan.put("BX",  bilan.get("BX") + debit - credit); 
                    if (cpte.substring(0,3).equals("491")) bilan.put("BY",  bilan.get("BY") + credit - debit);
                    bilan.put("BX_BY", bilan.get("BX") - bilan.get("BY"));

                    if ((cpte.substring(0,3).equals("425")) || (cpte.substring(0,4).equals("4287")) || (cpte.substring(0,4).equals("4387")) || (cpte.substring(0,3).equals("441")) || (cpte.substring(0,3).equals("442")  || (cpte.substring(0,3).equals("443"))) || (cpte.substring(0,4).equals("4456")) || (cpte.substring(0,5).equals("44581")) || (cpte.substring(0,5).equals("44582")) || (cpte.substring(0,5).equals("44583")) || (cpte.substring(0,5).equals("44586")) || (cpte.substring(0,4).equals("4487"))) {
                        if(debit-credit>=0) {
                            bilan.put("BZ",  bilan.get("BZ") + debit - credit);       
                        } else {
                            bilan.put("DY",  bilan.get("DY") + credit - debit);
                        }
                    } 
                    
                    




//if ( (substr($_,0,3) eq "425") || (substr($_,0,4) eq "4287") || (substr($_,0,4) eq "4387") || (substr($_,0,3) eq "441") || (substr($_,0,3) eq "442") || (substr($_,0,3) eq "443") || (substr($_,0,4) eq "4456") || (substr($_,0,5) eq "44581") || (substr($_,0,5) eq "44582") || (substr($_,0,5) eq "44583") || (substr($_,0,5) eq "44586") || (substr($_,0,4) eq "4487") )
//{
//if ($balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} >= 0)
//{
//$bilanp{BZ}{montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; 
//} else {
//$bilanp{DY}{montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; 
//}
//}

//if ((substr($_,0,3) eq "462") || (substr($_,0,3) eq "465") || (substr($_,0,4) eq "4687") || (substr($_,0,4) eq "4096") || (substr($_,0,4) eq "4097") || (substr($_,0,4) eq "4098") )
//{
//if ($balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} >= 0)
//{
//$bilanp{BZ}{montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; 
//} else {
//$bilanp{EA}{montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; 
//}
//}



//if ( (substr($_,0,3) eq "495") || (substr($_,0,3) eq "496") )  { $bilanp {CA} {montant} += $balancep {$_} {montant_credit} - $balancep {$_} {montant_debit} ; }
//$bilanp {BZ_CA} {montant} = $bilanp {BZ} {montant} - $bilanp {CA} {montant} ;

//if (substr($_,0,4) eq "4562") { $bilanp {CB} {montant} += $balancep {$_} {montant_debit} - $balancep {$_} {montant_credit} ; }
//# il n'y a pas de compte provision pour CC
//$bilanp {CB_CC} {montant} = $bilanp {CB} {montant} - $bilanp {CC} {montant} ;

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
