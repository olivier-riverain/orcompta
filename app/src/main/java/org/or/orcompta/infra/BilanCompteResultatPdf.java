package org.or.orcompta.infra;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfName;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfPageEventHelper;
import org.openpdf.text.pdf.PdfString;
import org.openpdf.text.pdf.PdfWriter;

import org.or.orcompta.domain.Balance;
import org.or.orcompta.domain.Bilan;
import org.or.orcompta.domain.Company;
import org.or.orcompta.domain.CompteResultat;
import org.or.orcompta.domain.DateEntry;

public class BilanCompteResultatPdf  extends PdfPageEventHelper{
    
    private Bilan bilan;
    private Map<String, Double> codesBilan;
    private CompteResultat compteResultat;
    private Map<String, Double> codesCompteResultat;
    private String dateBegin;
    private String dateEnd;
    private Company company;
    private String fileName;
    private float width;
    private float height;
    private float yPos;

    public BilanCompteResultatPdf(Company company, Bilan bilan, CompteResultat compteResultat){        
        this.bilan = bilan;
        this.compteResultat = compteResultat;
        this.company = company;
        codesBilan = bilan.getCodesBilan();
        String companyName = this.company.getName().replace(" ", "-");
        dateBegin = compteResultat.getDateBegin().toString().replace("/", "-");
        dateEnd = compteResultat.getDateEnd().toString().replace("/", "-");
        System.out.println("BilanCompteResultat " + " dateBegin = " + dateBegin + " dateEnd = " + dateEnd);
        fileName = this.company.getSaveDirectory() + this.company.getIdCompany() + "-" + companyName + "_bilanCompteResultat_" + dateBegin + "_" + dateEnd + ".pdf";
    }

    public void createPdf() {
        System.out.println("BilanCompteResultatPdf createPdf");
        Document document = new Document(PageSize.A4);
        width = document.getPageSize().getWidth();
        System.out.println("BilanCompteResultatPdf createPdf width = " + width);
        height = document.getPageSize().getHeight();
        System.out.println("BilanCompteResultatPdf createPdf height = " + height);
        yPos = height;
        PdfWriter writer = null;;
        try {            
            writer = PdfWriter.getInstance(document, new FileOutputStream(this.fileName));           
            document.addAuthor("ORcompta"); 
            document.addSubject("Bilan Compte de résultat de l'exercice.");
            writer.setPageEvent(this); 
            document.open();
            writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));            
            PdfPTable tableTitle = new PdfPTable(2);        
            tableTitle.setTotalWidth(width);
            tableTitle.setWidths(new float[]{30.0F, 60.0F});
            tableTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableTitle.getDefaultCell().setPaddingBottom(5);
            tableTitle.getDefaultCell().setBorder(Rectangle.BOX);
            Font fontTitle = new Font(Font.HELVETICA, 8, Font.BOLD);
            Phrase codeName = new Phrase("CODE", fontTitle);
            PdfPCell codeCell = new PdfPCell(codeName);
            tableTitle.addCell(codeCell);
            Phrase amount = new Phrase("Montant", fontTitle);
            PdfPCell amountCell = new PdfPCell(amount);
            tableTitle.addCell(amountCell);            
            Font fontItem = new Font(Font.HELVETICA, 8);
            codesBilan = bilan.getCodesBilan();
            for(Map.Entry<String, Double> codeItem: codesBilan.entrySet()) {
                codeName = new Phrase(codeItem.getKey(), fontItem);
                codeCell = new PdfPCell(codeName);
                tableTitle.addCell(codeCell);
                amount = new Phrase(codeItem.getValue().toString(), fontItem);
                amountCell = new PdfPCell(amount);
                tableTitle.addCell(amountCell);
                //String libelle = balance.getAccountLibelle(accountItem.getKey());
                //libelleName = new Phrase(libelle, fontItem);
                //libelleCell = new PdfPCell(libelleName);
                //tableTitle.addCell(libelleCell);                       
                
            }
            document.add(tableTitle);

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }

        document.close();
        writer.close();
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        // create a header                
        /*PdfPTable tableTitle = new PdfPTable(2);        
        tableTitle.setTotalWidth(width);
        tableTitle.setWidths(new float[]{50.0F, 50.0F});
        tableTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTitle.getDefaultCell().setPaddingBottom(40);
        tableTitle.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        Font fontTitle = new Font(Font.HELVETICA, 20, Font.BOLD);
        Phrase title =  new Phrase("Balance du " + balance.getDateBegin() + " au " + balance.getDateEnd(), fontTitle);
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingTop(20);
        titleCell.setPaddingBottom(20);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setBorder(Rectangle.SECTION);        
        titleCell.setColspan(800);        
        tableTitle.addCell(titleCell);                        
        Font cellFont = new Font(Font.HELVETICA, 8);        
        Phrase companyName = new Phrase(company.getName() + " " + company.getAddress(), cellFont);
        PdfPCell companyCell = new PdfPCell(companyName);
        companyCell.setBorder(Rectangle.LIST);        
        companyCell.setPaddingTop(5);
        companyCell.setPaddingBottom(5);              
        tableTitle.addCell(companyCell);
        Phrase siret = new Phrase(company.getSiret(), cellFont);
        PdfPCell siretCell = new PdfPCell(siret);
        siretCell.setBorder(Rectangle.LIST);                
        siretCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        siretCell.setPaddingTop(5);
        siretCell.setPaddingBottom(5);        
        tableTitle.addCell(siretCell);            
        document.add(tableTitle);
    */}

    @Override
    public void onEndPage(PdfWriter writer, Document document) {        
        PdfPTable tableTitle = new PdfPTable(1);
        tableTitle.setTotalWidth(width);
        //tableTitle.setWidths(new int[]{50, 50});
        tableTitle.getDefaultCell().setPaddingBottom(5);
        tableTitle.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        Paragraph pageNumberText =  new Paragraph("Page " + document.getPageNumber(), new Font(Font.HELVETICA, 10));
        PdfPCell pageNumberCell = new PdfPCell(pageNumberText);
        pageNumberCell.setPaddingTop(4);
        pageNumberCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pageNumberCell.setBorder(Rectangle.NO_BORDER);
        tableTitle.addCell(pageNumberCell);

        tableTitle.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
    }




}
 