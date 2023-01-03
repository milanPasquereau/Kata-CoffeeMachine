package services.report;

import model.Order;
import model.OrderType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DailyReportBuilderImpl implements DailyReportBuilder {

    @Override
    public void printReport(List<Order> orders) {
        Map<OrderType, Long> countDrinks = orders
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderType, Collectors.counting()));
        System.out.print(buildReportFromData(countDrinks));
    }

    private String buildReportFromData(Map<OrderType, Long> countDrinks) {
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};
        countDrinks.forEach((orderType, nb) -> totalAmount[0] = totalAmount[0].add(orderType.getPrice().multiply(BigDecimal.valueOf(nb))));
        return "Sales made:"
                + "\nCoffees: " + formatCountDrinks(countDrinks.get(OrderType.COFFEE))
                + "\nChocolates: " + formatCountDrinks(countDrinks.get(OrderType.CHOCOLATE))
                + "\nTeas: " + formatCountDrinks(countDrinks.get(OrderType.TEA))
                + "\nOrange juices: " + formatCountDrinks(countDrinks.get(OrderType.ORANGE_JUICE))
                + "\nTotal amount of sales: " + totalAmount[0].setScale(2, RoundingMode.HALF_UP).doubleValue() + " €";
    }

    private Long formatCountDrinks(Long count) {
        return count != null ? count : 0;
    }
}
