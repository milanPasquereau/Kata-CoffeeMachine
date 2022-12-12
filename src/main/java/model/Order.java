package model;

public class Order {
    private OrderType orderType;
    private int sugars;
    private boolean withAStick;

    public Order(OrderType orderType, int sugars) {
        this.orderType = orderType;
        this.sugars = sugars;
        this.withAStick = sugars > 0;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public int getSugars() {
        return sugars;
    }

    public boolean isWithAStick() {
        return withAStick;
    }
}
