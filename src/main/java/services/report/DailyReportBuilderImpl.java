package services.report;

import model.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DailyReportBuilderImpl implements DailyReportBuilder {

    private static final double PRICE_TEA = 0.4;
    private static final double PRICE_COFFEE = 0.6;
    private static final double PRICE_CHOCOLATE = 0.5;
    private static final double PRICE_ORANGE_JUICE = 0.6;

    @Override
    public void printReport(List<Order> orders) {
        double totalAmount = 0;
        int nbSoldCoffees = 0;
        int nbSoldChocolates = 0;
        int nbSoldTeas = 0;
        int nbSoldOrangeJuices = 0;
        for(Order order : orders) {
            switch (order.getOrderType()) {
                case COFFEE -> {
                    totalAmount += PRICE_COFFEE;
                    nbSoldCoffees++;
                }
                case TEA -> {
                    totalAmount += PRICE_TEA;
                    nbSoldTeas++;
                }
                case CHOCOLATE -> {
                    totalAmount += PRICE_CHOCOLATE;
                    nbSoldChocolates++;
                }
                case ORANGE_JUICE -> {
                    totalAmount += PRICE_ORANGE_JUICE;
                    nbSoldOrangeJuices++;
                }
            }
        }
        System.out.print(buildReportFromData(totalAmount, nbSoldCoffees, nbSoldChocolates, nbSoldTeas, nbSoldOrangeJuices));
    }

    private String buildReportFromData(double totalAmount, int nbSoldCoffees,
                                       int nbSoldChocolates, int nbSoldTeas,
                                       int nbSoldOrangeJuices) {
        BigDecimal bd = BigDecimal.valueOf(totalAmount).setScale(2, RoundingMode.HALF_UP);
        return "Sales made:"
                + "\nCoffees: " + nbSoldCoffees
                + "\nChocolates: " + nbSoldChocolates
                + "\nTeas: " + nbSoldTeas
                + "\nOrange juices: " + nbSoldOrangeJuices
                + "\nTotal amount of sales: " + bd.doubleValue() + " €";
    }
}
