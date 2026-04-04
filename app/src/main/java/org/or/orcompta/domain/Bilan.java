package org.or.orcompta.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bilan {

    private final BilanId idBilan;
    private Map<String, Double> bilan;
    private Map<String, String> libelle;
    private Balance balance;
    private String[] actifCode = {"AA", "AAA","AA_AAA", "AB", "AC", "AB_AC", "CX", "CQ", "CX_CQ", "AF", "AG", "AF_AG", "AH", "AI", "AH_AI", "AJ", "AK", "AJ_AK", "AL", "AM", "AL_AM", "AN", "AO", "AN_AO", "AP", "AQ", "AP_AQ", "AR", "AS", "AR_AS", "AT", "AU", "AT_AU", "AV", "AW", "AV_AW", "AX", "AY", "AX_AY", "CS", "CT", "CS_CT", "CU", "CV", "CU_CV", "BB", "BC", "BB_BC", "BD", "BE", "BD_BE", "BF", "BG", "BF_BG", "BH", "BI", "BH_BI", "BJ", "BK", "BJ_BK", "BL", "BM", "BL_BM", "BN", "BO", "BN_BO", "BP", "BQ", "BP_BQ", "BR", "BS", "BR_BS", "BT", "BU", "BT_BU", "BV", "BW", "BV_BW", "BX", "BY", "BX_BY", "BZ", "CA", "BZ_CA", "CB", "CC", "CB_CC", "CD_AP", "CD", "CE", "CD_CE", "CF", "CG", "CF_CG", "CH", "CI", "CH_CI", "CJ", "CK", "CJ_CK", "CW", "CWW", "CW_CWW", "CM", "CMM", "CM_CMM", "CN", "CNN", "CN_CNN", "CO", "1A", "CO_1A", "CP", "CR"} ;
    private String[] passifCode = {"DA_V", "DA", "DB", "EK", "DC", "DD", "DE", "B1", "DF", "EJ", "DG", "DH", "DI", "DJ", "DK", "DL", "DM", "DN", "DO", "DP", "DQ", "DR", "DS", "DT", "DU", "EI", "DV", "DW", "DX", "DY", "DZ", "EA", "EB", "EC", "ED", "EE", "1B", "1C", "1D", "1E", "EF", "EG", "EH"} ;

    public Bilan(BilanId idBilan, Balance balance) {
        this.idBilan = idBilan;
        this.balance = balance;
        bilan = new LinkedHashMap<>();
        initBilan();
        initLibelle();
    }

    private void initBilan() {
        for(String code: actifCode) {
            bilan.put(code, 0.0);
        }
        for(String code: passifCode) {
            bilan.put(code, 0.0);
        }
    }
    
    public void createBilan() {       
        Double totalProduits = 0.0;
        Double totalCharges = 0.0;        
        for(Map.Entry<String, Double[]> account : balance.getAccounts().entrySet()) {
            String cpte = account.getKey();
            Double debit = account.getValue()[0];
            Double credit = account.getValue()[1];
            String first = cpte.substring(0, 1);                       
            switch(first) {
                case "1":                                      
                    if (cpte.substring(0,4).equals("1011") || cpte.substring(0,3).equals("109") ) bilan.put("AA",  bilan.get("AA") + debit - credit); 
                    bilan.put("AA_AAA", bilan.get("AA") - bilan.get("AAA"));

                    if (cpte.substring(0,4).equals("1013")) bilan.put("DA_V",  bilan.get("DA_V") + credit - debit); 
                    
                    if (cpte.substring(0,3).equals("101") || cpte.substring(0,3).equals("108") || cpte.substring(0,3).equals("109")) bilan.put("DA",  bilan.get("DA") + credit - debit); 

                    if (cpte.substring(0,3).equals("104")) bilan.put("DB",  bilan.get("DB") + credit - debit); 

                    if (cpte.substring(0,3).equals("107")) bilan.put("EK",  bilan.get("EK") + credit - debit); 

                    if (cpte.substring(0,3).equals("105") || cpte.substring(0,3).equals("107")) bilan.put("DC",  bilan.get("DC") + credit - debit); 

                    if (cpte.substring(0,4).equals("1061")) bilan.put("DD",  bilan.get("EK") + credit - debit); 

                    if (cpte.substring(0,4).equals("1062") || cpte.substring(0,4).equals("1063")) bilan.put("DE",  bilan.get("DE") + credit - debit); 

                    if (cpte.substring(0,4).equals("1064")) bilan.put("B1",  bilan.get("B1") + credit - debit);

                    if (cpte.substring(0,4).equals("1064")) bilan.put("DF",  bilan.get("DF") + credit - debit); 

                    if (cpte.substring(0,4).equals("106x")) bilan.put("EJ",  bilan.get("EJ") + credit - debit); 

                    if (cpte.substring(0,4).equals("1068")) bilan.put("DG",  bilan.get("DG") + credit - debit); 

                    if (cpte.substring(0,2).equals("11")) bilan.put("DH",  bilan.get("DH") + credit - debit);                     

                    if (cpte.substring(0,2).equals("12")) bilan.put("DI",  bilan.get("DI") + credit - debit); 

                    if (cpte.substring(0,2).equals("13")) bilan.put("DJ",  bilan.get("DJ") + credit - debit); 

                    if (cpte.substring(0,2).equals("14")) bilan.put("DK",  bilan.get("DK") + credit - debit); 

                    if (cpte.substring(0,2).equals("12")) bilan.put("DI",  bilan.get("DI") + credit - debit); 

                    if (cpte.substring(0,3).equals("151")) bilan.put("DP",  bilan.get("DP") + credit - debit); 

                    if (cpte.substring(0,3).equals("153") || cpte.substring(0,3).equals("154") || cpte.substring(0,3).equals("155") || cpte.substring(0,3).equals("156") || cpte.substring(0,3).equals("157") || cpte.substring(0,3).equals("158")) bilan.put("DP",  bilan.get("DP") + credit - debit); 

                    if (cpte.substring(0,3).equals("161")) bilan.put("DS",  bilan.get("DS") + credit - debit); 

                    if (cpte.substring(0,3).equals("163")) bilan.put("DT",  bilan.get("DT") + credit - debit); 

                    if (cpte.substring(0,3).equals("164")) bilan.put("DU",  bilan.get("DU") + credit - debit); 

                    if (cpte.substring(0,4).equals("1675")) bilan.put("EI",  bilan.get("EI") + credit - debit); 

                    if (cpte.substring(0,3).equals("165") || cpte.substring(0,3).equals("166") || cpte.substring(0,3).equals("167") || cpte.substring(0,3).equals("168") || cpte.substring(0,3).equals("169")) bilan.put("DV",  bilan.get("DV") + credit - debit); 

                    if (cpte.substring(0,4).equals("1051")) bilan.put("1C",  bilan.get("1C") + credit - debit);

                    if (cpte.substring(0,4).equals("1052")) bilan.put("1D",  bilan.get("1D") + credit - debit); 

                    if (cpte.substring(0,4).equals("1053")) bilan.put("1E",  bilan.get("1E") + credit - debit); 

                    if (cpte.substring(0,5).equals("10641")) bilan.put("EF",  bilan.get("EF") + credit - debit); 

                    break;

                case "2":                                         
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

                    //if (cpte.substring(0,2).equals("26")) bilan.put("CS",  bilan.get("CS") + debit - credit); 
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

                    if (cpte.substring(0,3).equals("206")) bilan.put("DDB",  bilan.get("DDB") + debit - credit); 
                                        
                    break;
                
                case "3":                                         
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
                    
                    if ((cpte.substring(0,3).equals("462")) || (cpte.substring(0,3).equals("465")) || (cpte.substring(0,4).equals("4687")) || (cpte.substring(0,4).equals("4096")) || (cpte.substring(0,4).equals("4097"))  || (cpte.substring(0,4).equals("4098"))) {
                        if(debit-credit>=0) {                            
                            bilan.put("BZ",  bilan.get("BZ") + debit - credit);       
                        } else {                            
                            bilan.put("EA",  bilan.get("EA") + credit - debit);
                        }
                    }

                    if ((cpte.substring(0,3).equals("495")) || (cpte.substring(0,3).equals("496"))) bilan.put("CA",  bilan.get("CA") + debit - credit); 
                    bilan.put("BZ_CA", bilan.get("BZ") - bilan.get("CA"));

                    if (cpte.substring(0,4).equals("4562")) bilan.put("CB",  bilan.get("CB") + debit - credit); 
                    //il n'y a pas de compte provision pour CC
                    bilan.put("CB_CC", bilan.get("CB") - bilan.get("CC"));

                    if (cpte.substring(0,3).equals("486")) bilan.put("CH",  bilan.get("CH") + debit - credit); 
                    //il n'y a pas de compte provision pour CI
                    bilan.put("CH_CI", bilan.get("CH") - bilan.get("CI"));

                    if (cpte.substring(0,4).equals("4816")) bilan.put("CW",  bilan.get("CW") + debit - credit); 
                    bilan.put("CW_CWW", bilan.get("CW") - bilan.get("CWW"));

                    if (cpte.substring(0,3).equals("481")) bilan.put("CW",  bilan.get("CW") + debit - credit); 
                    bilan.put("CW_CWW", bilan.get("CW") - bilan.get("CWW"));

                    bilan.put("CM_CMM", bilan.get("CM") - bilan.get("CMM"));

                    if (cpte.substring(0,3).equals("476")) bilan.put("CN",  bilan.get("CN") + debit - credit); 
                    bilan.put("CN_CNN", bilan.get("CN") - bilan.get("CNN"));

                    if (cpte.substring(0,3).equals("419")) bilan.put("DW",  bilan.get("DW") + debit - credit); 
                                       
                    if (cpte.substring(0,3).equals("400") || cpte.substring(0,3).equals("401") || cpte.substring(0,3).equals("403") || cpte.substring(0,4).equals("4081") || cpte.substring(0,4).equals("4088")) {
                        if(credit-debit>=0) {
                            bilan.put("DX",  bilan.get("DX") + credit - debit);       
                        } else {                            
                            bilan.put("BZ",  bilan.get("BZ") + debit - credit);
                        }
                    }

                    if (cpte.substring(0,2).equals("42") || cpte.substring(0,2).equals("43") || (cpte.substring(0,2).equals("44") && !cpte.substring(0,4).equals("4456") && !cpte.substring(0,5).equals("44586"))) {
                        if(credit-debit>=0) {                           
                            bilan.put("DY",  bilan.get("DY") + credit - debit);       
                        } else {
                            bilan.put("BZ",  bilan.get("BZ") + debit - credit);
                        }
                    }

                    if(cpte.substring(0,2).equals("45")) bilan.put("DV",  bilan.get("DV") + credit - debit);
                    
                    if (cpte.substring(0,3).equals("404") || cpte.substring(0,3).equals("405") || cpte.substring(0,4).equals("4084")) bilan.put("DZ",  bilan.get("DZ") + credit - debit); 

                    if (cpte.substring(0,2).equals("46") ) bilan.put("EA",  bilan.get("EA") + credit - debit); 

                    if (cpte.substring(0,3).equals("487") ) bilan.put("EB",  bilan.get("EB") + credit - debit); 

                    if (cpte.substring(0,3).equals("477") ) bilan.put("ED",  bilan.get("ED") + credit - debit); 

                    break;

                case "5":                                        
                    if (cpte.substring(0,2).equals("50")) bilan.put("CD",  bilan.get("CD") + debit - credit); 
                    //il n'y a pas de compte provision pour CE
                    bilan.put("CD_CE", bilan.get("CD") - bilan.get("CE"));

                    if ((cpte.substring(0,2).equals("51")) || (cpte.substring(0,2).equals("52"))  || (cpte.substring(0,2).equals("53")) || (cpte.substring(0,2).equals("54")  || (cpte.substring(0,2).equals("58")))) bilan.put("CF",  bilan.get("CF") + debit - credit); 
                    bilan.put("CF_CG", bilan.get("CF") - bilan.get("CG"));

                    if (cpte.substring(0,3).equals("519") ) bilan.put("EH",  bilan.get("EH") + debit - credit); 
                    
                    break;

                case "6":                   
                    totalCharges += debit - credit; 
                    break;
                
                case "7":                    
                    totalProduits += credit - debit;
                    break;
                
                default:
                    break;                
            }

        }

        bilan.put("BJ", bilan.get("AB") + bilan.get("CX") + bilan.get("AF") + bilan.get("AH") + bilan.get("AJ") + bilan.get("AL") + bilan.get("AN") + bilan.get("AP") + bilan.get("AR") + bilan.get("AT") + bilan.get("AV") + bilan.get("AX") + bilan.get("CS") + bilan.get("CU") + bilan.get("BB") + bilan.get("BD") + bilan.get("BF") + bilan.get("BH")); 
        bilan.put("BK", bilan.get("AC") + bilan.get("CQ") + bilan.get("AG") + bilan.get("AI") + bilan.get("AK") + bilan.get("AM") + bilan.get("AO") + bilan.get("AQ") + bilan.get("AS") + bilan.get("AU") + bilan.get("AW") + bilan.get("AY") + bilan.get("CT") + bilan.get("CV") + bilan.get("BC") + bilan.get("BE") + bilan.get("BG") + bilan.get("BI")); 
        bilan.put("BJ_BK", bilan.get("BJ") - bilan.get("BK"));
        bilan.put("CK", bilan.get("BM") + bilan.get("BO") + bilan.get("BQ") + bilan.get("BS") + bilan.get("BU") + bilan.get("BW") + bilan.get("BY") + bilan.get("CA") + bilan.get("CC") + bilan.get("CE") + bilan.get("CG") + bilan.get("CI")); 
        bilan.put("CJ", bilan.get("BL") + bilan.get("BN") + bilan.get("BP") + bilan.get("BR") + bilan.get("BT") + bilan.get("BV") + bilan.get("BX") + bilan.get("BZ") + bilan.get("CB") + bilan.get("CD") + bilan.get("CF") + bilan.get("CH")); 
        bilan.put("CJ_CK", bilan.get("CJ") - bilan.get("CK"));
        bilan.put("CO", bilan.get("AA") + bilan.get("BJ") + bilan.get("CJ") + bilan.get("CW") + bilan.get("CM") + bilan.get("CN")); 
        bilan.put("1A", bilan.get("BK") + bilan.get("CK"));
        bilan.put("CO_1A", bilan.get("CO") - bilan.get("1A"));
        bilan.put("DI", totalProduits - totalCharges);
        bilan.put("DL", bilan.get("DA") + bilan.get("DB") + bilan.get("DC") + bilan.get("DD") + bilan.get("DE") + bilan.get("DF") + bilan.get("DG") + bilan.get("DH") + bilan.get("DI") + bilan.get("DJ") + bilan.get("DK")); 
        bilan.put("DO", bilan.get("DM") + bilan.get("DN"));
        bilan.put("DR", bilan.get("DP") + bilan.get("DQ"));
        bilan.put("EC", bilan.get("DS") + bilan.get("DT") + bilan.get("DU") + bilan.get("DV") + bilan.get("DW") + bilan.get("DX") + bilan.get("DY") + bilan.get("DZ") + bilan.get("EA") + bilan.get("EB")); 
        bilan.put("EE", bilan.get("DL") + bilan.get("DO") + bilan.get("DR") + bilan.get("EC") + bilan.get("ED")); 

    }

    public Map<String, Double> getCodesBilan() {
        return this.bilan;
    }

    private void initLibelle() {        
        // bilan actif
        libelle.put("AA", "Capital souscrit non appelé (I)");
        libelle.put("AB", "Frais d'établissement");
        libelle.put("CX", "Frais de développement");
        libelle.put("AF", "Concessions, brevets et droits similaires");
        libelle.put("AH", "Fonds commercial (1)");
        libelle.put("AJ", "Autres immobilisations incorporelles");
        libelle.put("AL", "Avances et acomptes sur immobilisations incorporelles");
        libelle.put("AN", "Terrrains");
        libelle.put("AP", "Constructions");
        libelle.put("AR", "Installations techniques, matériel et outillage industriels");        
        libelle.put("AT", "Autres immobilisations corporelles");
        libelle.put("AV", "Immobilisations en cours");
        libelle.put("AX", "Avances et acomptes");
        libelle.put("CS", "Participations évaluées selon la méthode de mise en équiv");
        libelle.put("CU", "Autres participations");
        libelle.put("BB", "Créances rattachées à des participations");
        libelle.put("BD", "Autres titres immobilisés");
        libelle.put("BF", "Prêts");
        libelle.put("BH", "Autres immobilisations financières");
        libelle.put("BJ", "TOTAL (II)");
        libelle.put("BL", "Matières premières, approvisionnements");
        libelle.put("BN", "En cours de production de biens");
        libelle.put("BP", "En cours de production de services");
        libelle.put("BR", "Produits intermédiaires et finis");
        libelle.put("BT", "Marchandises");
        libelle.put("BV", "Avances et acomptes versés sur commandes");
        libelle.put("BX", "Clients et comptes rattachés (3)");
        libelle.put("BZ", "Autres créances (3)");
        libelle.put("CB", "Capital souscrit et appelé, non versé");
        libelle.put("CD", "Valeurs mobilières de placement (dont actions propres: ");
        libelle.put("CF", "Disponibilités");
        libelle.put("CH", "Charges constatées d'avance (3)");
        libelle.put("CJ", "TOTAL (III)");
        libelle.put("CW", "Frais d'émission d'emprunt à étaler     (IV)");
        libelle.put("CM", "Primes de remboursement des obligations (V)");
        libelle.put("CN", "Écarts de conversion actif              (VI)");
        libelle.put("CO", "TOTAL (I à VI)");
        libelle.put("CP", "(2) Parts à moins d'un an des immobilisations financières nettes :");
        libelle.put("CR", "(3) Part à plus d'un an");

        //bilan passif
        libelle.put("DA", "Capital social ou individuel (1) (Dont versé : ");
        libelle.put("DB", "Primes d'émission, de fusion, d'apport,...");
        libelle.put("DC", "Écarts de réévaluation (2) (dont écart d'équivalence");
        libelle.put("DD", "Réserve légale (3)");
        libelle.put("DE", "Réserves statutaires ou contractuelles");
        libelle.put("DF", "Réserves réglementées (3) (Dont réserve spéciale des provisions pour fluctuation des cours");
        libelle.put("DG", "Autres réserves (Dont réserve relative à l'achat d'oeuvres originales d'artistes vivants");
        libelle.put("DH", "Report à nouveau");
        libelle.put("DI", "RÉSULTAT DE L'EXERCICE (bénéfice ou perte)");
        libelle.put("DJ", "Subventions d'investissement");
        libelle.put("DK", "Provisions réglementées");
        libelle.put("DL", "TOTAL (I)");
        libelle.put("DM", "Produit des émissions de titres participatifs");
        libelle.put("DN", "Avances conditionnées");
        libelle.put("DO", "TOTAL (II)");
        libelle.put("DP", "Provisions pour risques");
        libelle.put("DQ", "Provisions pour charges");
        libelle.put("DR", "TOTAL (III)");
        libelle.put("DS", "Emprunts obligataires convertibles");
        libelle.put("DT", "Autres emprunts obligataires");
        libelle.put("DU", "Emprunts et dettes auprès des établissements de crédit (5)");
        libelle.put("DV", "Emprunts et dettes financières divers (Dont emprunts participatifs");
        libelle.put("DW", "Avances et acomptes reçus sur commandes en cours");
        libelle.put("DX", "Dettes fournisseurs et comptes rattachés");
        libelle.put("DY", "Dettes fiscales et sociales");
        libelle.put("DZ", "Dettes sur immobilisations et comptes rattachés");
        libelle.put("EA", "Autres dettes");
        libelle.put("EB", "Produits constatés d'avance (4)");
        libelle.put("EC", "TOTAL (IV)");
        libelle.put("ED", "Écarts de conversion passif (V)");
        libelle.put("EE", "TOTAL GÉNÉRAL (I À V)");
        libelle.put("1B", "Écarts de réévaluation incorporé au capital");
        libelle.put("1C", "Réserve spéciale de réévaluation (1959)");
        libelle.put("1D", "Écarts de réévaluation libre");
        libelle.put("1E", "Réserve de réévaluation (1976)");
        libelle.put("EF", "Dont réserve spéciale des plus-values à long terme");
        libelle.put("EG", "Dettes et produits constatés d'avance à moins d'un an");
        libelle.put("EH", "Dont concours bancaires courants et soldes créditeurs de banques et CCP");
    }

    public Map<String, String> getLibelleBilan() {
        return this.libelle;
    }


}
