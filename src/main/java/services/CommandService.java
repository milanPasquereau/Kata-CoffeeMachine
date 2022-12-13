package services;

import model.Message;
import model.Order;

public interface CommandService {

    void sendOrderToDrinkMaker(Order order);
    void sendMessageToDringMaker(Message message);
    void insertMoney(double amount);
}
