package org.or.orcompta.infra;

import java.io.File;
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
import org.or.orcompta.domain.Company;

public class BalancePdf  extends PdfPageEventHelper{
    
    private Balance balance;
    private Company company;
    private String fileName;
    private float width;
    private float height;
    private float yPos;

    public BalancePdf(Company company, Balance balance){        
        this.balance = balance;
        this.company = company;
        String companyName = this.company.getName().replace(" ", "-");
        String dateBegin = balance.getDateBegin().toString().replace("/", "-");
        String dateEnd = balance.getDateEnd().toString().replace("/", "-");
        System.out.println("BalancePdf " + " dateBegin = " + dateBegin + " dateEnd = " + dateEnd);
        fileName = this.company.getSaveDirectory() + this.company.getIdCompany() + "-" + companyName + "_balance_" + dateBegin + "_" + dateEnd + ".pdf";
    }

    public void createPdf() {
        System.out.println("BalancePdf createPdf");
        Document document = new Document(PageSize.A4);
        width = document.getPageSize().getWidth();
        System.out.println("BalancePdf createPdf width = " + width);
        height = document.getPageSize().getHeight();
        System.out.println("BalancePdf createPdf height = " + height);
        yPos = height;
        PdfWriter writer = null;;
        try {            
            writer = PdfWriter.getInstance(document, new FileOutputStream(this.fileName));           
            document.addAuthor("ORcompta"); 
            document.addSubject("Balance de l'exercice.");
            writer.setPageEvent(this); 
            document.open();
            writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));            
            PdfPTable tableTitle = new PdfPTable(5);        
            tableTitle.setTotalWidth(width);
            tableTitle.setWidths(new float[]{15.0F, 40.0F, 15.0F, 15.0F, 15.0F});
            tableTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableTitle.getDefaultCell().setPaddingBottom(5);
            tableTitle.getDefaultCell().setBorder(Rectangle.BOX);
            Font fontTitle = new Font(Font.HELVETICA, 8, Font.BOLD);
            Phrase compteName = new Phrase("COMPTE", fontTitle);
            PdfPCell compteCell = new PdfPCell(compteName);
            tableTitle.addCell(compteCell);
            Phrase libelleName = new Phrase("LIBELLE", fontTitle);
            PdfPCell libelleCell = new PdfPCell(libelleName);
            tableTitle.addCell(libelleCell);                       
            Phrase debitName = new Phrase("DEBIT", fontTitle);
            PdfPCell debitCell = new PdfPCell(debitName);
            tableTitle.addCell(debitCell);
            Phrase creditName = new Phrase("DEBIT", fontTitle);
            PdfPCell creditCell = new PdfPCell(creditName);
            tableTitle.addCell(creditCell);
            Phrase soldeName = new Phrase("SOLDE", fontTitle);
            PdfPCell soldeCell = new PdfPCell(soldeName);
            tableTitle.addCell(soldeCell);
            Font fontItem = new Font(Font.HELVETICA, 8);
            Map<String, Double[]> accounts = balance.getAccounts();
            for(Map.Entry<String, Double[]> accountItem: accounts.entrySet()) {
                compteName = new Phrase(accountItem.getKey(), fontItem);
                compteCell = new PdfPCell(compteName);
                tableTitle.addCell(compteCell);
                String libelle = balance.getAccountLibelle(accountItem.getKey());
                libelleName = new Phrase(libelle, fontItem);
                libelleCell = new PdfPCell(libelleName);
                tableTitle.addCell(libelleCell);                       
                debitName = new Phrase(accountItem.getValue()[0].toString(), fontItem);
                debitCell = new PdfPCell(debitName);
                tableTitle.addCell(debitCell);
                creditName = new Phrase(accountItem.getValue()[1].toString(), fontItem);
                creditCell = new PdfPCell(creditName);
                tableTitle.addCell(creditCell);
                Double solde = accountItem.getValue()[0] - accountItem.getValue()[1];
                soldeName = new Phrase(solde.toString(), fontItem);
                soldeCell = new PdfPCell(soldeName);
                tableTitle.addCell(soldeCell);
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
        PdfPTable tableTitle = new PdfPTable(2);        
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
    }

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
 