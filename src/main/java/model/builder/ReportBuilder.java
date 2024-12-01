package model.builder;

import model.Report;

import java.time.LocalDate;

public class ReportBuilder {
    private Report report;

    public ReportBuilder(){
        report = new Report();
    }

    public ReportBuilder setUsername(String username){
        report.setUsername(username);
        return this;
    }

    public ReportBuilder setOrderId(Long id){
        report.setOrderId(id);
        return this;
    }

    public ReportBuilder setOrderDate(LocalDate date){
        report.setOrderDate(date);
        return this;
    }

    public ReportBuilder setBookTitle(String title){
        report.setBookTitle(title);
        return this;
    }

    public ReportBuilder setQuantity(Integer quantity){
        report.setQuantity(quantity);
        return this;
    }

    public ReportBuilder setPrice(Float price){
        report.setPrice(price);
        return this;
    }

    public Report build(){
        return report;
    }
}
