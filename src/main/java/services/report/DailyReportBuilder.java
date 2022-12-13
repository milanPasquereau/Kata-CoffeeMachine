package services.report;

import model.Order;

import java.util.List;

public interface DailyReportBuilder {

    void printReport(List<Order> orders);
}
