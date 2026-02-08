package org.or.orcompta.infra;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
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
        PdfWriter writer = null;;
        try {            
            writer = PdfWriter.getInstance(document, new FileOutputStream(this.fileName));           
            document.addAuthor("ORcompta"); 
            document.addSubject("Balance de l'exercice.");
            writer.setPageEvent(this); 
            document.open();
            writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));            
            //document.add(new Paragraph("Hello World"));

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }

        document.close();
        writer.close();
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        // create a header
        PdfPTable tableTitle = new PdfPTable(1);
        tableTitle.setTotalWidth(width);
        //tableTitle.setWidths(new float[]{38, 38});
        tableTitle.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableTitle.getDefaultCell().setPaddingBottom(5);
        tableTitle.getDefaultCell().setBorder(Rectangle.BOX);
        Paragraph title =  new Paragraph("Balance du " + balance.getDateBegin() + " au " + balance.getDateEnd(), new Font(Font.COURIER, 20, Font.BOLD));
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingBottom(10);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setBorder(Rectangle.NO_BORDER);
        tableTitle.addCell(titleCell);
        tableTitle.writeSelectedRows(0, -1, 34, 828, writer.getDirectContent());
        
        Font cellFont = new Font(Font.HELVETICA, 8);
        PdfPTable tableInfo = new PdfPTable(2);
        tableInfo.setTotalWidth(width);
        tableInfo.setWidths(new float[]{38, 38});
        tableInfo.setWidths(new float[]{38, 38});
        tableInfo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableInfo.getDefaultCell().setPaddingBottom(5);
        tableInfo.getDefaultCell().setBorder(Rectangle.BOX);
        Paragraph companyName = new Paragraph(company.getName() + " " + company.getAddress(), cellFont);
        PdfPCell companyCell = new PdfPCell(companyName);
        companyCell.setBorder(Rectangle.NO_BORDER);
        tableInfo.addCell(companyCell);
        Paragraph siret = new Paragraph(company.getSiret(), cellFont);
        PdfPCell siretCell = new PdfPCell(siret);
        siretCell.setBorder(Rectangle.NO_BORDER);
        tableInfo.addCell(siretCell);
        

        tableInfo.writeSelectedRows(0, -1, 34, 828, writer.getDirectContent());
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
 