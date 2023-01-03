package model;

import java.math.BigDecimal;

public enum OrderType {
    TEA("TEA", BigDecimal.valueOf(0.4)),
    COFFEE("COFFEE",BigDecimal.valueOf(0.6)),
    CHOCOLATE("CHOCOLATE",BigDecimal.valueOf(0.5)),
    ORANGE_JUICE("ORANGE_JUICE",BigDecimal.valueOf(0.6));

    private final String drinkName;
    private final BigDecimal price;

    OrderType(String drinkName, BigDecimal price) {
        this.drinkName = drinkName;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
