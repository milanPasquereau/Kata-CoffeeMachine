package services;

import model.Message;
import model.Order;
import repositories.OrderRepository;
import services.drinkMaker.DrinkMaker;
import services.money.MoneyChecker;
import services.report.DailyReportBuilder;
import services.shortage.StockService;

import java.math.BigDecimal;

public class CommandServiceImpl implements CommandService {

    private final DrinkMaker drinkMaker;
    private final MoneyChecker moneyChecker;
    private final DailyReportBuilder dailyReportBuilder;
    private final StockService stockService;
    private final OrderRepository orderRepository;

    public CommandServiceImpl(DrinkMaker drinkMaker, MoneyChecker moneyChecker, DailyReportBuilder dailyReportBuilder, OrderRepository orderRepository, StockService stockService) {
        this.drinkMaker = drinkMaker;
        this.moneyChecker = moneyChecker;
        this.dailyReportBuilder = dailyReportBuilder;
        this.orderRepository = orderRepository;
        this.stockService = stockService;
    }

    @Override
    public void sendOrderToDrinkMaker(Order order) {
        BigDecimal amountInMachine = moneyChecker.isAffordable(order.getOrderType());
        if(amountInMachine.compareTo(BigDecimal.ZERO) >= 0) {
            String translatedOrder = buildStringFromOrder(order);
            if(!stockService.checkIfBeverageIsEmpty(translatedOrder)) {
                drinkMaker.makeDrink(translatedOrder);
                orderRepository.save(order);
            } else {
                stockService.notifyMissingDrink(translatedOrder);
            }
        } else {
            sendMessageToDrinkMaker(new Message("Monney is missing: "+amountInMachine.abs()+" €"));
        }
    }

    @Override
    public void sendMessageToDrinkMaker(Message message) {
        String translatedMessage = buildStringFromMessage(message);
        this.drinkMaker.sendMessage(translatedMessage);
    }

    @Override
    public void insertMoney(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)> 0) {
            this.moneyChecker.insertMoney(amount);
        }
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
