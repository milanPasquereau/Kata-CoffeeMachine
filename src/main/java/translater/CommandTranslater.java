package translater;

import drinkmaker.DrinkMaker;
import model.Message;
import model.Order;

public class CommandTranslater {

    private DrinkMaker drinkMaker;

    public CommandTranslater(DrinkMaker drinkMaker) {
        this.drinkMaker = drinkMaker;
    }

    public String sendOrderToDrinkMaker(Order order) {
        String translatedOrder = buildStringFromOrder(order);
        this.drinkMaker.makeDrink(translatedOrder);
        return translatedOrder;
    }

    public String sendMessageToDringMaker(Message message) {
        String translatedMessage = buildStringFromMessage(message);
        this.drinkMaker.sendMessage(translatedMessage);
        return translatedMessage;
    }

    private static String buildStringFromOrder(Order order) {
        String orderType = "C";
        switch (order.getOrderType()) {
            case TEA -> orderType = "T";
            case CHOCOLATE -> orderType = "H";
        }
        return orderType+":"+ (order.getSugars() > 0 ? order.getSugars() : "")+":"+(order.isWithAStick() ? 0 : "");
    }

    private static String buildStringFromMessage(Message message) {
        return "M"+":"+message.content();
    }
}
