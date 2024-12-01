package repository.report;

import model.Report;
import model.User;
import model.builder.ReportBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryMySQL implements ReportRepository{
    private Connection connection;
    public ReportRepositoryMySQL(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Report> findAllFromLastMonth() {
        String sql = "SELECT user.username, `order`.id, `order`.timestamp, `order`.title, `order`.stock, `order`.price FROM `order` INNER JOIN user ON `order`.employee_id = user.id WHERE `order`.timestamp BETWEEN (CURRENT_DATE() - INTERVAL 1 MONTH) AND CURRENT_DATE() GROUP BY user.username, `order`.id, `order`.timestamp, `order`.title, `order`.stock, `order`.price;";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Report> reports = new ArrayList<>();
            while (resultSet.next()){
                reports.add(getReportFromResultSet(resultSet));
            }
            return reports;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Report> findAllByEmployee(User user) {
        String sql = "SELECT user.username, `order`.id, `order`.timestamp, `order`.title, `order`.stock, `order`.price FROM `order` INNER JOIN user ON `order`.employee_id = user.id WHERE user.id = \'" + user.getId() +"\';";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Report> reports = new ArrayList<>();
            while (resultSet.next()){
                reports.add(getReportFromResultSet(resultSet));
            }
            return reports;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    private Report getReportFromResultSet(ResultSet resultSet) throws SQLException{
        return new ReportBuilder()
                .setOrderDate(resultSet.getDate("timestamp").toLocalDate())
                .setUsername(resultSet.getString("username"))
                .setPrice(resultSet.getFloat("price"))
                .setBookTitle(resultSet.getString("title"))
                .setQuantity(resultSet.getInt("stock"))
                .setOrderId(resultSet.getLong("id"))
                .build();
    }
}
