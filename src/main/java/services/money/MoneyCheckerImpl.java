package services.money;

import model.OrderType;

import java.math.BigDecimal;

public class MoneyCheckerImpl implements MoneyChecker {

    private BigDecimal currentAmount;
    private static final BigDecimal PRICE_TEA = BigDecimal.valueOf(0.4);
    private static final BigDecimal PRICE_COFFEE = BigDecimal.valueOf(0.6);
    private static final BigDecimal PRICE_CHOCOLATE = BigDecimal.valueOf(0.5);
    private static final BigDecimal PRICE_ORANGE_JUICE = BigDecimal.valueOf(0.6);

    public MoneyCheckerImpl() {
        this.currentAmount = BigDecimal.valueOf(0);
    }

    @Override
    public BigDecimal isAffordable(OrderType orderType) {
        BigDecimal price = switch (orderType) {
            case CHOCOLATE -> PRICE_CHOCOLATE;
            case COFFEE -> PRICE_COFFEE;
            case TEA -> PRICE_TEA;
            case ORANGE_JUICE -> PRICE_ORANGE_JUICE;
        };
        this.currentAmount = this.currentAmount.subtract(price);
        return this.currentAmount;
    }

    @Override
    public void insertMoney(BigDecimal amount) {
        this.currentAmount = this.currentAmount.add(amount);
    }
}
