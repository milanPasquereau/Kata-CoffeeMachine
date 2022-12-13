package repositories;

import model.Order;

import java.util.List;

public interface OrderRepository {

    void save(Order order);

    List<Order> getAllOrders();
}
