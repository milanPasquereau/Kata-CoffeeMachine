package services.report;

import model.Order;
import services.report.DailyReportBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.lang.Math.round;

public class DailyReportBuilderImpl implements DailyReportBuilder {

    private static final double PRICE_TEA = 0.4;
    private static final double PRICE_COFFEE = 0.6;
    private static final double PRICE_CHOCOLATE = 0.5;
    private static final double PRICE_ORANGE_JUICE = 0.6;

    @Override
    public void printReport(List<Order> orders) {
        double totalAmount = 0;
        int nbSelledCoffees = 0;
        int nbSelledChocolates = 0;
        int nbSelledTeas = 0;
        int nbSelledOrangeJuices = 0;
        for(Order order : orders) {
            switch (order.getOrderType()) {
                case COFFEE -> {
                    totalAmount += PRICE_COFFEE;
                    nbSelledCoffees++;
                }
                case TEA -> {
                    totalAmount += PRICE_TEA;
                    nbSelledTeas++;
                }
                case CHOCOLATE -> {
                    totalAmount += PRICE_CHOCOLATE;
                    nbSelledChocolates++;
                }
                case ORANGE_JUICE -> {
                    totalAmount += PRICE_ORANGE_JUICE;
                    nbSelledOrangeJuices++;
                }
            }
        }
        System.out.print(buildReportFromData(totalAmount, nbSelledCoffees, nbSelledChocolates, nbSelledTeas, nbSelledOrangeJuices));
    }

    private String buildReportFromData(double totalAmount, int nbSelledCoffees,
                                       int nbSelledChocolates, int nbSelledTeas,
                                       int nbSelledOrangeJuices) {
        BigDecimal bd = BigDecimal.valueOf(totalAmount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return "Sales made:"
                + "\nCoffees: " + nbSelledCoffees
                + "\nChocolates: " + nbSelledChocolates
                + "\nTeas: " + nbSelledTeas
                + "\nOrange juices: " + nbSelledOrangeJuices
                + "\nTotal amount of sales: " + bd.doubleValue() + " €";
    }
}
