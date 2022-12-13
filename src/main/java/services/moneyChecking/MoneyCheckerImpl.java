package services.moneyChecking;

import model.OrderType;
import services.moneyChecking.MoneyChecker;

public class MoneyCheckerImpl implements MoneyChecker {

    private double currentAmount;
    private static final double PRICE_TEA = 0.4;
    private static final double PRICE_COFFEE = 0.6;
    private static final double PRICE_CHOCOLATE = 0.5;
    private static final double PRICE_ORANGE_JUICE = 0.6;

    public MoneyCheckerImpl() {
        this.currentAmount = 0;
    }

    @Override
    public double isAffordable(OrderType orderType) {
        double price = switch (orderType) {
            case CHOCOLATE -> PRICE_CHOCOLATE;
            case COFFEE -> PRICE_COFFEE;
            case TEA -> PRICE_TEA;
            case ORANGE_JUICE -> PRICE_ORANGE_JUICE;
        };
        return this.currentAmount - price;
    }

    @Override
    public void insertMoney(double amount) {
        this.currentAmount += amount;
    }
}
