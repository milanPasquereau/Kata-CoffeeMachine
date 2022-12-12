package translater;

import drinkmaker.DrinkMaker;
import model.Message;
import model.Order;
import model.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class CommandTranslaterTest {

    @InjectMocks
    private CommandTranslater commandTranslater;

    @Mock
    private DrinkMaker drinkMaker;

    @Test
    @DisplayName("should translate a coffee order with sugar")
    void shouldTranslateCoffeeOrderWithSugarToString() {
        Order order = new Order(OrderType.COFFEE, 2);
        String translatedOrder = commandTranslater.sendOrderToDrinkMaker(order);
        String expectedOrder = "C:2:0";

        assertEquals(expectedOrder, translatedOrder);

        InOrder inOrder = inOrder(drinkMaker);
        inOrder.verify(drinkMaker).makeDrink(expectedOrder);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("should translate a tee order without sugar")
    void shouldTranslateTeaOrderWithoutSugarToString() {
        Order order = new Order(OrderType.TEA, 0);
        String translatedOrder = commandTranslater.sendOrderToDrinkMaker(order);
        String expectedOrder = "T::";

        assertEquals(expectedOrder, translatedOrder);

        InOrder inOrder = inOrder(drinkMaker);
        inOrder.verify(drinkMaker).makeDrink(expectedOrder);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("should send message to drink maker")
    void shouldSendMessageToDrinkMaker() {
        Message message = new Message("Contenu du message");
        String translatedMsg = commandTranslater.sendMessageToDringMaker(message);
        String expectedMsg = "M:Contenu du message";

        assertEquals(expectedMsg, translatedMsg);

        InOrder inOrder = inOrder(drinkMaker);
        inOrder.verify(drinkMaker).sendMessage(expectedMsg);
        inOrder.verifyNoMoreInteractions();
    }

}