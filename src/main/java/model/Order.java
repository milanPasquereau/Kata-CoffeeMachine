package model;

public class Order {
    private final OrderType orderType;
    private final int sugars;
    private final boolean withAStick;
    private final boolean extraHot;

    public Order(OrderType orderType, int sugars, boolean extraHot) {
        this.orderType = orderType;
        this.sugars = sugars;
        this.withAStick = sugars > 0;
        this.extraHot = extraHot;
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

    public boolean isExtraHot() {
        return extraHot;
    }
}
