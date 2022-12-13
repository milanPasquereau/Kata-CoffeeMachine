package services;

import model.OrderType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import services.money.MoneyCheckerImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MoneyCheckerImplTest {

    @InjectMocks
    private MoneyCheckerImpl moneyChecker;

    @Test
    @DisplayName("should return negative amount of money when there is no money")
    void shouldReturnNegativeAmountOfMoneyWithNoMoney() {
        final BigDecimal amountMissing = moneyChecker.isAffordable(OrderType.COFFEE);
        assertEquals(BigDecimal.valueOf(-0.6), amountMissing);
    }

    @Test
    @DisplayName("should return positive amount of money when there is sufficient money")
    void shouldReturnPositiveAmountOfMoneyWithSufficientMoney() {
        moneyChecker.insertMoney(BigDecimal.valueOf(5));
        final BigDecimal amount = moneyChecker.isAffordable(OrderType.COFFEE);
        assertEquals(BigDecimal.valueOf(4.4), amount);
    }
}