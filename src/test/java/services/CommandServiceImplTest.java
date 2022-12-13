package services;

import model.Message;
import model.Order;
import model.OrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandServiceImplTest {

    @Mock
    private DrinkMaker drinkMaker;

    @Mock
    private MoneyChecker moneyChecker;

    @InjectMocks
    private CommandServiceImpl commandService;

    @BeforeEach
    void init() {
        commandService = new CommandServiceImpl(drinkMaker, moneyChecker);
    }

    @Test
    @DisplayName("should translate a coffee order with sugar and sufficient money")
    void shouldOrderCoffeeWithSugar() {
        Order order = new Order(OrderType.COFFEE, 2);
        when(moneyChecker.isAffordable(OrderType.COFFEE)).thenReturn(1.0);
        commandService.sendOrderToDrinkMaker(order);
        String expectedOrder = "C:2:0";

        verify(drinkMaker).makeDrink(expectedOrder);
        verifyNoMoreInteractions(drinkMaker);
    }

    @Test
    @DisplayName("should translate a coffee order with sugar and send message")
    void shouldNotOrderCoffeeWithoutMoneyAndSendMsg() {
        Order order = new Order(OrderType.COFFEE, 2);
        when(moneyChecker.isAffordable(OrderType.COFFEE)).thenReturn(-0.5);
        commandService.sendOrderToDrinkMaker(order);

        verify(drinkMaker).sendMessage("M:Monney is missing: 0.5 €");
        verifyNoMoreInteractions(drinkMaker);
    }

    @Test
    @DisplayName("should translate a tee order without sugar and sufficient money")
    void shouldTranslateTeaOrderWithoutSugarToString() {
        when(moneyChecker.isAffordable(OrderType.TEA)).thenReturn(0.0);
        Order order = new Order(OrderType.TEA, 0);
        commandService.sendOrderToDrinkMaker(order);
        String expectedOrder = "T::";

        verify(drinkMaker).makeDrink(expectedOrder);
        verifyNoMoreInteractions(drinkMaker);
    }

    @Test
    @DisplayName("should send message to drink maker")
    void shouldSendMessageToDrinkMaker() {
        Message message = new Message("Contenu du message");
        commandService.sendMessageToDringMaker(message);
        String expectedMsg = "M:Contenu du message";

        verify(drinkMaker).sendMessage(expectedMsg);
        verifyNoMoreInteractions(drinkMaker);
    }

    @Test
    @DisplayName("should add money to coffee machine")
    void shouldAddMoneyToMachine() {
        commandService.insertMoney(5.5);

        verify(moneyChecker).insertMoney(5.5);
        verifyNoMoreInteractions(moneyChecker);
    }
}