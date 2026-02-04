package org.or.orcompta.infra;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfName;
import org.openpdf.text.pdf.PdfString;
import org.openpdf.text.pdf.PdfWriter;
import org.or.orcompta.domain.Balance;
import org.or.orcompta.domain.Company;

public class BalancePdf {
    
    private Balance balance;
    private Company company;
    private String fileName;

    public BalancePdf(Company company, Balance balance) {        
        this.balance = balance;
        this.company = company;
        String companyName = this.company.getName().replace(" ", "-");
        fileName = this.company.getSaveDirectory() + this.company.getIdCompany() + "-" + companyName + "_balance_" + balance.getDateBegin() + "_" + balance.getDateEnd() + ".pdf";
    }

    public void createPdf() {
        Document document = new Document();
        try {            
            final PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(this.fileName));           
            document.addAuthor("ORcompta"); 
            document.addSubject("Balance de l'exercice."); 
            document.open();
            instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));            
            document.add(new Paragraph("Hello World"));
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }

        document.close();
    }



}
 