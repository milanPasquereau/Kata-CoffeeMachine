package services;

import model.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import services.moneyChecking.MoneyCheckerImpl;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MoneyCheckerImplTest {

    @InjectMocks
    private MoneyCheckerImpl moneyChecker;

    @Test
    @DisplayName("should return negative amount of money when there is no money")
    void shouldReturnNegativeAmountOfMoneyWithNoMoney() {
        double amountMissing = moneyChecker.isAffordable(OrderType.COFFEE);
        assertEquals(-0.6, amountMissing);
    }

    @Test
    @DisplayName("should return positive amount of money when there is sufficient money")
    void shouldReturnPositiveAmountOfMoneyWithSufficientMoney() {
        moneyChecker.insertMoney(5.0);
        double amount = moneyChecker.isAffordable(OrderType.COFFEE);
        assertEquals(4.4, amount);
    }
}