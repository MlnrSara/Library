package service.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Report;
import model.User;
import repository.report.ReportRepository;
import repository.user.UserRepository;

import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class ReportServiceImpl implements ReportService{

    private ReportRepository reportRepository;
    private UserRepository userRepository;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository){
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean generateFullPDFReport(){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("fullReport.pdf"));

            document.open();
            List<Report> reports = reportRepository.findAllFromLastMonth();
            PdfPTable table = new PdfPTable(6);
            addTableHeader(table);
            addRows(table, reports);

            document.add(table);
            document.close();

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean generatePDFReportForEmployeeWithUsername(String username) {
        User user = userRepository.findByUsername(username);
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("oneEmployeeReport.pdf"));

            document.open();
            List<Report> reports = reportRepository.findAllByEmployee(user);
            PdfPTable table = new PdfPTable(6);
            addTableHeader(table);
            addRows(table, reports);

            document.add(table);
            document.close();

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Username", "Order id", "Order date", "Book title", "Quantity", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<Report> reports) {
        reports.forEach(report ->{
            table.addCell(report.getUsername());
            table.addCell(String.valueOf(report.getOrderId()));
            table.addCell(String.valueOf(report.getOrderDate()));
            table.addCell(report.getBookTitle());
            table.addCell(String.valueOf(report.getQuantity()));
            table.addCell(String.valueOf(report.getPrice()));
        });
    }
}
