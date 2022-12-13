package services;

import model.Message;
import model.Order;

public class CommandServiceImpl implements CommandService {

    private final DrinkMaker drinkMaker;

    private final MoneyChecker moneyChecker;

    public CommandServiceImpl(DrinkMaker drinkMaker, MoneyChecker moneyChecker) {
        this.drinkMaker = drinkMaker;
        this.moneyChecker = moneyChecker;
    }

    @Override
    public void sendOrderToDrinkMaker(Order order) {
        double amountInMachine = moneyChecker.isAffordable(order.getOrderType());
        if(amountInMachine >= 0) {
            String translatedOrder = buildStringFromOrder(order);
            drinkMaker.makeDrink(translatedOrder);
        } else {
            sendMessageToDringMaker(new Message("Monney is missing: "+Math.abs(amountInMachine)+" €"));
        }
    }

    @Override
    public void sendMessageToDringMaker(Message message) {
        String translatedMessage = buildStringFromMessage(message);
        this.drinkMaker.sendMessage(translatedMessage);
    }

    @Override
    public void insertMoney(double amount) {
        this.moneyChecker.insertMoney(amount);
    }

    private static String buildStringFromOrder(Order order) {
        String orderType = switch (order.getOrderType()) {
            case TEA -> "T";
            case CHOCOLATE -> "H";
            case COFFEE -> "C";
            case ORANGE_JUICE -> "O";
        };
        return orderType
                + (order.isExtraHot()? "h":"")
                + ":"
                + (order.getSugars() > 0 ? order.getSugars() : "")
                + ":"
                + (order.isWithAStick() ? 0 : "");
    }

    private static String buildStringFromMessage(Message message) {
        return "M"+":"+message.content();
    }
}
