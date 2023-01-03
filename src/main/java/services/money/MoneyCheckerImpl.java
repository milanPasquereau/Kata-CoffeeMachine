package services.money;

import model.OrderType;

import java.math.BigDecimal;
import java.util.Map;

public class MoneyCheckerImpl implements MoneyChecker {

    private BigDecimal currentAmount;

    public MoneyCheckerImpl() {
        this.currentAmount = BigDecimal.ZERO;
    }

    @Override
    public BigDecimal isAffordable(OrderType orderType) {
        this.currentAmount = this.currentAmount.subtract(orderType.getPrice());
        return this.currentAmount;
    }

    @Override
    public void insertMoney(BigDecimal amount) {
        this.currentAmount = this.currentAmount.add(amount);
    }
}
