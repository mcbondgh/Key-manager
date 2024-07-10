package app.documents;


import app.conf.AlertDialogs;
import app.entities.KeyTransactionsEntity;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.BorderCollapsePropertyValue;
import com.itextpdf.layout.property.TextAlignment;
import javafx.scene.control.TableView;

import javax.swing.text.StyleConstants;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DocGenerator {

    public static boolean createPdfDocument(String docName, TableView<KeyTransactionsEntity> tableView) {
        String desktopPath = System.getProperty("user.home") + "/Desktop/" + docName;
        try {
            PdfWriter pdfWriter = new PdfWriter(desktopPath);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            int tableColumnSize = tableView.getVisibleLeafColumns().size();
            Table table = new Table(tableColumnSize);
            table.setFontSize(9);
            table.useAllAvailableWidth();
            table.setMarginBottom(5);

            Cell headerCell = new Cell(1, tableColumnSize)
                    .add(new Paragraph("KEY TRANSACTION REPORT").setBold())
                    .setTextAlignment(TextAlignment.CENTER).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setFontSize(16);
            table.addHeaderCell(headerCell);

            //add data to table cell
            tableView.getVisibleLeafColumns().forEach(headers -> {
                table.addHeaderCell(new Paragraph(headers.getText()).setBold());
            });

            for (KeyTransactionsEntity item : tableView.getItems()) {
                table.addCell(String.valueOf(item.getId()));
                table.addCell(item.getKeyCode());
                table.addCell(item.getBlockAlias());
                table.addCell(item.getCardNumber());
                table.addCell(item.getTransactionDate().toString());
                table.addCell(item.getReturnedDate() == null ? "null" : item.getReturnedDate().toString());
                table.addCell(item.getReturnedBy() == null ? "null" : item.getIssuedBy());
                table.addCell(item.getIssuedBy());
                table.addCell(item.getPurpose());
            }
            String createdDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(LocalDate.now());
            Paragraph newLine = new Paragraph("Date generated"+createdDate).setFontSize(9).setItalic();


            document.add(table).add(new LineSeparator(new SolidLine(1)))
                    .add(newLine);
            document.close();
           return true;
        } catch (FileNotFoundException e) {

            new AlertDialogs("OPENED DOCUMENT", "The process cannot access the file because it is being used by another process", "please close all references to this file before you export");
        }
        return false;
    }
}//END OF CLASS...
