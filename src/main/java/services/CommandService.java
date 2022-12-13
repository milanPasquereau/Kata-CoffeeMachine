package services;

import model.Message;
import model.Order;

public interface CommandService {

    void sendOrderToDrinkMaker(Order order);
    void sendMessageToDrinkMaker(Message message);
    void insertMoney(double amount);
    void printDailyReport();
}
