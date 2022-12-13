package services;

import model.Message;
import model.Order;
import repositories.OrderRepository;
import services.drinkMaker.DrinkMaker;
import services.moneyChecking.MoneyChecker;
import services.report.DailyReportBuilder;
import services.shortage.BeverageQuantityChecker;
import services.shortage.EmailNotifier;

public class CommandServiceImpl implements CommandService {

    private final DrinkMaker drinkMaker;

    private final MoneyChecker moneyChecker;

    private final DailyReportBuilder dailyReportBuilder;

    private final OrderRepository orderRepository;

    private final EmailNotifier emailNotifier;

    private final BeverageQuantityChecker beverageQuantityChecker;

    public CommandServiceImpl(DrinkMaker drinkMaker, MoneyChecker moneyChecker, DailyReportBuilder dailyReportBuilder, OrderRepository orderRepository, EmailNotifier emailNotifier, BeverageQuantityChecker beverageQuantityChecker) {
        this.drinkMaker = drinkMaker;
        this.moneyChecker = moneyChecker;
        this.dailyReportBuilder = dailyReportBuilder;
        this.orderRepository = orderRepository;
        this.emailNotifier = emailNotifier;
        this.beverageQuantityChecker = beverageQuantityChecker;
    }

    @Override
    public void sendOrderToDrinkMaker(Order order) {
        double amountInMachine = moneyChecker.isAffordable(order.getOrderType());
        if(amountInMachine >= 0) {
            String translatedOrder = buildStringFromOrder(order);
            if(!beverageQuantityChecker.isEmpty(translatedOrder)) {
                drinkMaker.makeDrink(translatedOrder);
                orderRepository.save(order);
            } else {
                emailNotifier.notifyMissingDrink(translatedOrder);
            }
        } else {
            sendMessageToDrinkMaker(new Message("Monney is missing: "+Math.abs(amountInMachine)+" €"));
        }
    }

    @Override
    public void sendMessageToDrinkMaker(Message message) {
        String translatedMessage = buildStringFromMessage(message);
        this.drinkMaker.sendMessage(translatedMessage);
    }

    @Override
    public void insertMoney(double amount) {
        this.moneyChecker.insertMoney(amount);
    }

    @Override
    public void printDailyReport() {
        dailyReportBuilder.printReport(orderRepository.getAllOrders());
    }

    private String buildStringFromOrder(Order order) {
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

    private String buildStringFromMessage(Message message) {
        return "M"+":"+message.content();
    }
}
