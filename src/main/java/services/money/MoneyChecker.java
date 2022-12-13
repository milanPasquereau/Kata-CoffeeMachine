package services.money;

import model.OrderType;

import java.math.BigDecimal;

public interface MoneyChecker {

    BigDecimal isAffordable(OrderType orderType);

    void insertMoney(BigDecimal amount);
}
