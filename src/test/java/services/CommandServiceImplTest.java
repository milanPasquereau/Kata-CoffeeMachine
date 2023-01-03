package services;

import model.Message;
import model.Order;
import model.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.OrderRepository;
import services.drinkMaker.DrinkMaker;
import services.money.MoneyChecker;
import services.report.DailyReportBuilder;
import services.shortage.StockService;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandServiceImplTest {

    @Mock
    private DrinkMaker drinkMaker;

    @Mock
    private MoneyChecker moneyChecker;

    @Mock
    private DailyReportBuilder dailyReportBuilder;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StockService stockService;

    @InjectMocks
    private CommandServiceImpl commandService;

    @Test
    @DisplayName("should translate a coffee order with sugar and sufficient money")
    void shouldOrderCoffeeWithSugar() {
        final Order order = new Order(OrderType.COFFEE, 2, false);
        when(moneyChecker.isAffordable(OrderType.COFFEE)).thenReturn(BigDecimal.valueOf(1.0));
        commandService.sendOrderToDrinkMaker(order);
        final String expectedOrder = "C:2:0";

        verify(drinkMaker).makeDrink(expectedOrder);
        verifyNoMoreInteractions(drinkMaker);
        verify(orderRepository).save(order);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    @DisplayName("should translate a hot coffee order with sugar and sufficient money")
    void shouldOrderHotCoffeeWithSugar() {
        final Order order = new Order(OrderType.COFFEE, 2, true);
        when(moneyChecker.isAffordable(OrderType.COFFEE)).thenReturn(BigDecimal.valueOf(1.0));
        commandService.sendOrderToDrinkMaker(order);
        final String expectedOrder = "Ch:2:0";

        verify(drinkMaker).makeDrink(expectedOrder);
        verifyNoMoreInteractions(drinkMaker);
        verify(orderRepository).save(order);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    @DisplayName("should translate a orange juice order with sufficient money")
    void shouldOrderOrangeJuice() {
        final Order order = new Order(OrderType.ORANGE_JUICE, 0, false);
        when(moneyChecker.isAffordable(OrderType.ORANGE_JUICE)).thenReturn(BigDecimal.valueOf(1.0));
        commandService.sendOrderToDrinkMaker(order);
        final String expectedOrder = "O::";

        verify(drinkMaker).makeDrink(expectedOrder);
        verifyNoMoreInteractions(drinkMaker);
        verify(orderRepository).save(order);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    @DisplayName("should translate a coffee order with sugar and send message")
    void shouldNotOrderCoffeeWithoutMoneyAndSendMsg() {
        final Order order = new Order(OrderType.COFFEE, 2, false);
        when(moneyChecker.isAffordable(OrderType.COFFEE)).thenReturn(BigDecimal.valueOf(-0.5));
        commandService.sendOrderToDrinkMaker(order);

        verify(drinkMaker).sendMessage("M:Monney is missing: 0.5 €");
        verifyNoMoreInteractions(drinkMaker);
        verifyNoInteractions(orderRepository);
    }

    @Test
    @DisplayName("should translate a tee order without sugar and sufficient money")
    void shouldTranslateTeaOrderWithoutSugarToString() {
        when(moneyChecker.isAffordable(OrderType.TEA)).thenReturn(BigDecimal.ZERO);
        final Order order = new Order(OrderType.TEA, 0, false);
        final String expectedOrder = "T::";
        commandService.sendOrderToDrinkMaker(order);

        verify(drinkMaker).makeDrink(expectedOrder);
        verifyNoMoreInteractions(drinkMaker);
        verify(orderRepository).save(order);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    @DisplayName("should send message to drink maker")
    void shouldSendMessageToDrinkMaker() {
        final Message message = new Message("Contenu du message");
        commandService.sendMessageToDrinkMaker(message);
        final String expectedMsg = "M:Contenu du message";

        verify(drinkMaker).sendMessage(expectedMsg);
        verifyNoMoreInteractions(drinkMaker);
    }

    @Test
    @DisplayName("should add money to coffee machine")
    void shouldAddMoneyToMachine() {
        commandService.insertMoney(BigDecimal.valueOf(5.5));

        verify(moneyChecker).insertMoney(BigDecimal.valueOf(5.5));
        verifyNoMoreInteractions(moneyChecker);
    }

    @Test
    @DisplayName("should print daily report")
    void shouldPrintDailyReport() {
        commandService.printDailyReport();

        verify(dailyReportBuilder).printReport(any());
        verifyNoMoreInteractions(dailyReportBuilder);
        verify(orderRepository).getAllOrders();
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    @DisplayName("should not make a drink with shortage and send message")
    void shouldNotMakeDrinkWithShortageAndSendMessage() {
        when(moneyChecker.isAffordable(OrderType.COFFEE)).thenReturn(BigDecimal.ZERO);
        when(stockService.checkIfBeverageIsEmpty("C::")).thenReturn(true);
        final Order order = new Order(OrderType.COFFEE, 0, false);

        commandService.sendOrderToDrinkMaker(order);

        verify(stockService).notifyMissingDrink("C::");
        verifyNoMoreInteractions(stockService);
        verifyNoInteractions(orderRepository);
    }
}