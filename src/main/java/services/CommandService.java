package services;

import model.Message;
import model.Order;

import java.math.BigDecimal;

public interface CommandService {

    void sendOrderToDrinkMaker(Order order);
    void sendMessageToDrinkMaker(Message message);
    void insertMoney(BigDecimal amount);
    void printDailyReport();
}
