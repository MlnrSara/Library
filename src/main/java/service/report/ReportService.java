package service.report;

public interface ReportService {
    boolean generateFullPDFReport();

    boolean generatePDFReportForEmployeeWithUsername(String username);
}
