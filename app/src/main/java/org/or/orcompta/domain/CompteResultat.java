package org.or.orcompta.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class CompteResultat {

    private final CompteResultatId idCompteResultat;
    private Map<String, Double> compteResultat;
    private Map<String, String> libelle;
    private Balance balance;
    private DateEntry dateBegin;
    private DateEntry dateEnd;
    private String[] codeResultat1 = {"FA", "FB", "FC", "FD", "FE", "FF", "FG", "FH", "FI", "FJ", "FK", "FL", "FM", "FN", "FO", "FP", "FQ", "FR", "FS", "FT", "FU", "FV", "FW", "FX", "FY", "FZ", "GA", "GB", "GC", "GD", "GE", "GF", "GG", "GH", "GI", "GJ", "GK", "GL", "GM", "GN", "GO", "GP", "GQ", "GR", "GS", "GT", "GU", "GV", "GW"};
    private String[] codeResultat2 =  {"HA", "HB", "HC", "HD", "HE", "HF", "HG", "HH", "HI", "HJ", "HK", "HL", "HM", "HN", "HO", "HY", "1G", "HP", "HQ", "1H", "1J", "1K", "HX", "A1", "A2", "A3", "A4", "A6", "A9"} ;

    public CompteResultat(CompteResultatId idCompteResultat, Balance balance) {
        this.idCompteResultat = idCompteResultat;
        this.balance = balance;
        compteResultat = new LinkedHashMap<>();
        dateBegin = balance.getDateBegin();
        dateEnd = balance.getDateEnd();
        initCompteResultat();
        initLibelle();
    
    }

    private void initCompteResultat() {
        for(String code: codeResultat1) {
            compteResultat.put(code, 0.0);
        }
        for(String code: codeResultat2) {
            compteResultat.put(code, 0.0);
        }
    }

    public DateEntry getDateBegin() {
        return dateBegin;
    }

    public DateEntry getDateEnd() {
        return dateEnd;
    }

    public void createCompteResultat() {        
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
                    if (cpte.substring(0,4).equals("6811")) compteResultat.put("GA",  compteResultat.get("GA") + debit - credit);
                    if (cpte.substring(0,4).equals("6816")) compteResultat.put("GB",  compteResultat.get("GB") + debit - credit);
                    if (cpte.substring(0,4).equals("6817")) compteResultat.put("GC",  compteResultat.get("GC") + debit - credit);
                    if (cpte.substring(0,4).equals("6815")) compteResultat.put("GD",  compteResultat.get("GD") + debit - credit);
                    if (cpte.substring(0,3).equals("651") || cpte.substring(0,3).equals("653") || cpte.substring(0,3).equals("654") || cpte.substring(0,3).equals("658") || cpte.substring(0,4).equals("6812")) compteResultat.put("GE",  compteResultat.get("GE") + debit - credit);
                    if (cpte.substring(0,4).equals("6551")) compteResultat.put("GH",  compteResultat.get("GH") + debit - credit);
                    if (cpte.substring(0,4).equals("6555")) compteResultat.put("GI",  compteResultat.get("GI") + debit - credit);

                    if (cpte.substring(0,3).equals("686")) compteResultat.put("GQ",  compteResultat.get("GQ") + debit - credit);                    
                    if (cpte.substring(0,3).equals("661") || cpte.substring(0,3).equals("664") || cpte.substring(0,3).equals("665") || cpte.substring(0,3).equals("668")) compteResultat.put("GR",  compteResultat.get("GR") + debit - credit);
                    if (cpte.substring(0,3).equals("666")) compteResultat.put("GS",  compteResultat.get("GS") + debit - credit);
                    if (cpte.substring(0,3).equals("667")) compteResultat.put("GT",  compteResultat.get("GT") + debit - credit);

                    if (cpte.substring(0,3).equals("671") || cpte.substring(0,3).equals("672") || cpte.substring(0,3).equals("678")) compteResultat.put("HE",  compteResultat.get("HE") + debit - credit);                    
                    if (cpte.substring(0,3).equals("675")) compteResultat.put("HF",  compteResultat.get("HF") + debit - credit);
                    if (cpte.substring(0,3).equals("687")) compteResultat.put("HG",  compteResultat.get("HG") + debit - credit);
                    if (cpte.substring(0,3).equals("691")) compteResultat.put("HJ",  compteResultat.get("HJ") + debit - credit);
                    if (cpte.substring(0,3).equals("695") || cpte.substring(0,3).equals("696") || cpte.substring(0,3).equals("697") || cpte.substring(0,4).equals("6989")) compteResultat.put("HK",  compteResultat.get("HK") + debit - credit);                    
                    if (cpte.substring(0,4).equals("6981")) compteResultat.put("HJ",  compteResultat.get("HJ") + credit - debit);

                    if (cpte.substring(0,4).equals("6122")) compteResultat.put("HP",  compteResultat.get("HP") + debit - credit);
                    if (cpte.substring(0,4).equals("6125")) compteResultat.put("HQ",  compteResultat.get("HQ") + debit - credit);
                    if (cpte.substring(0,4).equals("6713")) compteResultat.put("HX",  compteResultat.get("HX") + debit - credit);
                    if (cpte.substring(0,3).equals("646")) compteResultat.put("A2",  compteResultat.get("A2") + debit - credit);
                    if (cpte.substring(0,3).equals("651")) compteResultat.put("A4",  compteResultat.get("A4") + debit - credit);

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

                    if (cpte.substring(0,3).equals("761")) compteResultat.put("GJ",  compteResultat.get("GJ") + credit - debit);
                    if (cpte.substring(0,3).equals("762") || cpte.substring(0,3).equals("763") || cpte.substring(0,3).equals("765")) compteResultat.put("GK",  compteResultat.get("GK") + credit - debit);
                    if (cpte.substring(0,3).equals("764") || cpte.substring(0,3).equals("768")) compteResultat.put("GL",  compteResultat.get("GL") + credit - debit);
                    if (cpte.substring(0,3).equals("786") || cpte.substring(0,3).equals("796")) compteResultat.put("GM",  compteResultat.get("GM") + credit - debit);
                    if (cpte.substring(0,3).equals("766")) compteResultat.put("GN",  compteResultat.get("GN") + credit - debit);
                    if (cpte.substring(0,3).equals("767")) compteResultat.put("GO",  compteResultat.get("GO") + credit - debit);

                    if (cpte.substring(0,3).equals("771") || cpte.substring(0,3).equals("772") || cpte.substring(0,3).equals("778")) compteResultat.put("HA",  compteResultat.get("HA") + credit - debit);
                    if (cpte.substring(0,3).equals("775") || cpte.substring(0,3).equals("777")) compteResultat.put("HB",  compteResultat.get("HB") + credit - debit);
                    if (cpte.substring(0,3).equals("787") || cpte.substring(0,3).equals("797")) compteResultat.put("HC",  compteResultat.get("HC") + credit - debit);

                    if (cpte.substring(0,3).equals("752")) compteResultat.put("HY",  compteResultat.get("HY") + credit - debit);
                    if (cpte.substring(0,3).equals("791")) compteResultat.put("A1",  compteResultat.get("A1") + credit - debit);
                    if (cpte.substring(0,3).equals("751")) compteResultat.put("A3",  compteResultat.get("A3") + credit - debit);
                   
                break;

                default:

                break;
                    
            }
        }

        compteResultat.put("FJ", compteResultat.get("FA") + compteResultat.get("FD") + compteResultat.get("FG"));
        compteResultat.put("FK", compteResultat.get("FB") + compteResultat.get("FE") + compteResultat.get("FH"));
        compteResultat.put("FL", compteResultat.get("FC") + compteResultat.get("FF") + compteResultat.get("FI"));
        compteResultat.put("FR", compteResultat.get("FL") + compteResultat.get("FM") + compteResultat.get("FN") + compteResultat.get("FO") + compteResultat.get("FP") + compteResultat.get("FQ"));
        compteResultat.put("GF", compteResultat.get("FS") + compteResultat.get("FT") + compteResultat.get("FU") + compteResultat.get("FV") + compteResultat.get("FW") + compteResultat.get("FX") + compteResultat.get("FY") + compteResultat.get("FZ") + compteResultat.get("GA") + compteResultat.get("GB") + compteResultat.get("GC") + compteResultat.get("GD") + compteResultat.get("GE"));
        compteResultat.put("GG", compteResultat.get("FR") - compteResultat.get("GF"));
        compteResultat.put("GP", compteResultat.get("GJ") + compteResultat.get("GK") + compteResultat.get("GL") + compteResultat.get("GM") + compteResultat.get("GN") + compteResultat.get("GO"));
        compteResultat.put("GU", compteResultat.get("GQ") + compteResultat.get("GR") + compteResultat.get("GS") + compteResultat.get("GT"));
        compteResultat.put("GV", compteResultat.get("GP") - compteResultat.get("GU"));
        compteResultat.put("GW", compteResultat.get("FR") - compteResultat.get("GF") + compteResultat.get("GH") - compteResultat.get("GI") + compteResultat.get("GP") - compteResultat.get("GU"));
        compteResultat.put("HD", compteResultat.get("HA") + compteResultat.get("HB") + compteResultat.get("HC"));
        compteResultat.put("HH", compteResultat.get("HE") + compteResultat.get("HF") + compteResultat.get("HG"));
        compteResultat.put("HI", compteResultat.get("HD") - compteResultat.get("HH"));
        compteResultat.put("HL", compteResultat.get("FR") + compteResultat.get("GH") + compteResultat.get("GP") + compteResultat.get("HD"));
        compteResultat.put("HM", compteResultat.get("GF") + compteResultat.get("GI") + compteResultat.get("GU") + compteResultat.get("HH") + compteResultat.get("HJ") + compteResultat.get("HK"));
        compteResultat.put("HN", compteResultat.get("HL") - compteResultat.get("HM"));

    }

    public Map<String, Double> getCodesCompteResultat() {
        return this.compteResultat;
    }

    private void initLibelle() { 
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");
        libelle.put("", "");


//$cr {FC} {libelle} = "Ventes de marchandises" ;
//$cr {FF} {libelle} = "Production vendue de biens" ;
//$cr {FI} {libelle} = "Production vendue de services" ;
//$cr {FL} {libelle} = "Chiffres d'affaires nets" ;
//$cr {FM} {libelle} = "Production stockée" ;
//$cr {FN} {libelle} = "Production immobilisée" ;
//$cr {FO} {libelle} = "Subventions d'exploitation" ;
//$cr {FP} {libelle} = "Reprises sur amortissements et provisions, transferts de charges (9)" ;
//$cr {FQ} {libelle} = "Autres produits (1) (11)" ;
//$cr {FR} {libelle} = "Total des produits d'exploitation (2) (1)" ;
//$cr {FS} {libelle} = "Achats de marchandises (y compris droits de douane)" ;
//$cr {FT} {libelle} = "Variation de stock (marchandises)" ;
//$cr {FU} {libelle} = "Achats de matières premières et autres approvisionnements (y compris droits de douane)" ;
//$cr {FV} {libelle} = "Variation de stock (matières premières et approvisionnements)" ;
//$cr {FW} {libelle} = "Autres achats et charges externes (3) (6 bis)" ;
//$cr {FX} {libelle} = "Impôts, taxes et versements assimilés" ;
//$cr {FY} {libelle} = "Salaires et traitements" ;
//$cr {FZ} {libelle} = "Charges sociales (10)" ;
//$cr {GA} {libelle} = "Sur immobilisations : dotations aux amortissements" ;
//$cr {GB} {libelle} = "Sur immobilisations : dotations aux provisions" ;
//$cr {GC} {libelle} = "Sur actif circulant : dotations aux provisions" ;
//$cr {GD} {libelle} = "Pour risques et charges : dotations aux provisions" ;
//$cr {GE} {libelle} = "Autres charges (12)" ;
//$cr {GF} {libelle} = "Total des charges d'exploitation (4) (II)" ;
//$cr {GG} {libelle} = "1 - RÉSULTAT D'EXPLOITATION (I - II)" ;
//$cr {GH} {libelle} = "Bénéfice attribué ou perte transférée (III)" ;
//$cr {GI} {libelle} = "Perte supportée ou bénéfice transféré (IV)" ;
//$cr {GJ} {libelle} = "Produits financiers de participations (5)" ;
//$cr {GK} {libelle} = "Produits des autres valeurs mobilières et créances de l'actif immobilisé (5)" ;
//$cr {GL} {libelle} = "Autres intérêts et produits assimilés (5)" ;
//$cr {GM} {libelle} = "Reprises sur provisions et transferts de charges" ;
//$cr {GN} {libelle} = "Différences positives de change" ;
//$cr {GO} {libelle} = "Produits nets sur cessions de valeurs mobilières de placement" ;
//$cr {GP} {libelle} = "Total de produits financiers (V)" ;
//$cr {GQ} {libelle} = "Dotations financières aux amortissements et provisions" ;
//$cr {GR} {libelle} = "Intérêts et charges assimilées (6)" ;
//$cr {GS} {libelle} = "Différences négatives de change" ;
//$cr {GT} {libelle} = "Charges nettes sur cessions de valeurs mobilières de placement" ;
//$cr {GU} {libelle} = "Total des charges financières (VI)" ;
//$cr {GV} {libelle} = "2 - RÉSULTAT FINANCIER (V - VI)" ;
//$cr {GW} {libelle} = "3 - RÉSULTAT COURANT AVANT IMPÔTS (I - II + III - IV + V - VI)" ;
//$cr {HA} {libelle} = "Produits exceptionnels sur opérations de gestion" ;
//$cr {HB} {libelle} = "Produits exceptionnels sur opérations en capital" ;
//$cr {HC} {libelle} = "Reprises sur provisions et transferts de charges" ;
//$cr {HD} {libelle} = "Total des produits exceptionnels (7) (VII)" ;
//$cr {HE} {libelle} = "Charges exceptionnelles sur opérations de gestion (6 bis)" ;
//$cr {HF} {libelle} = "Charges exceptionnelles sur opérations en capital" ;
//$cr {HG} {libelle} = "Dotations exceptionnelles aux amortissements et provisions" ;
//$cr {HH} {libelle} = "Total des charges exceptionnelles (7) (VIII)" ;
//$cr {HI} {libelle} = "4- RÉSULTAT EXCEPTIONNEL (VII - VIII)" ;
//$cr {HJ} {libelle} = "Participation des salariés aux résultats de l'entreprise (IX)" ;
//$cr {HK} {libelle} = "Impôts sur les bénéfices (X)" ;
//$cr {HL} {libelle} = "TOTAL DES PRODUITS (I + III + V + VII)" ;
//$cr {HM} {libelle} = "TOTAL DES CHARGES (II + IV + VI + VIII + IX + X)" ;
//$cr {HN} {libelle} = "5- BÉNÉFICE OU PERTE (Total des produits - total des charges)" ;
//$cr {HO} {libelle} = "(1) Dont produits nets partiels sur opérations à long terme" ;
//$cr {HY} {libelle} = "(2) Dont produits de locations immobilières" ;
//$cr {"1G"} {libelle} = "(2) Dont produits d'exploitation afférents à des exercices antérieurs (à détailler au (8) ci-dessous)" ;
//$cr {HP} {libelle} = "(3) Dont Crédit-bail mobilier" ;
//$cr {HQ} {libelle} = "(3) Dont Crédit-bail immobilier" ;
//$cr {"1H"} {libelle} = "(4) Dont charges d'exploitation afférentes à des exercices antérieurs (à détailler au (8) ci-dessous)" ;
//$cr {"1J"} {libelle} = "(5) Dont produits concernant les entreprises liées" ;
//$cr {"1K"} {libelle} = "(6) Dont intérêts concernant les entreprises liées" ;
//$cr {HX} {libelle} = "(6bis) Dont dons faits aux organismes d'intérêt général (art. 238 bis du C.G.I.)" ;
//$cr {"A1"} {libelle} = "(9) Dont tranferts de charges" ;
//$cr {"A2"} {libelle} = "(10) Dont cotisations personnelles de l'exploitant (13)" ;
//$cr {"A3"} {libelle} = "(11) Dont redevances pour concessions de brevets, de licences (produits)" ;
//$cr {"A4"} {libelle} = "(12) Dont redevances pour concessions de brevets, de licences (charges)" ;
    
    }

    public Map<String, String> getLibelleBilan() {
        return this.libelle;
    }
    
}
