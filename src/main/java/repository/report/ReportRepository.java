package repository.report;

import model.Report;
import model.User;

import java.util.List;

public interface ReportRepository {
    List<Report> findAllFromLastMonth();

    List<Report> findAllByEmployee(User user);
}
