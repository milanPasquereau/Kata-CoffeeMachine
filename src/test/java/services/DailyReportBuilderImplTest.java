package services;

import model.Order;
import model.OrderType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import services.report.DailyReportBuilderImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DailyReportBuilderImplTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    @InjectMocks
    private DailyReportBuilderImpl dailyReportBuilder;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("should print daily report")
    void shouldPrintDailyReport() {
        final List<Order> orders = List.of(
                new Order(OrderType.COFFEE, 2, false),
                new Order(OrderType.CHOCOLATE, 0, true),
                new Order(OrderType.ORANGE_JUICE, 0, false),
                new Order(OrderType.COFFEE, 1, true));

        final String resultExpected = """
                Sales made:
                Coffees: 2
                Chocolates: 1
                Teas: 0
                Orange juices: 1
                Total amount of sales: 2.3 €""";
        dailyReportBuilder.printReport(orders);
        assertEquals(resultExpected, outContent.toString());
    }

}