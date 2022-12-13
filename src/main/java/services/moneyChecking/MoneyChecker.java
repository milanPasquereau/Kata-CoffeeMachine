package services.moneyChecking;

import model.OrderType;

public interface MoneyChecker {

    double isAffordable(OrderType orderType);

    void insertMoney(double amount);
}
