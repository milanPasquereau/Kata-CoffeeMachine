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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

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
        List<Order> orders = List.of(
                new Order(OrderType.COFFEE, 2, false),
                new Order(OrderType.CHOCOLATE, 0, true),
                new Order(OrderType.ORANGE_JUICE, 0, false),
                new Order(OrderType.COFFEE, 1, true));

        String resultExpected ="Sales made:" +
                "\nCoffees: 2" +
                "\nChocolates: 1" +
                "\nTeas: 0" +
                "\nOrange juices: 1" +
                "\nTotal amount of sales: 2.3 €";
        dailyReportBuilder.printReport(orders);
        assertEquals(resultExpected, outContent.toString());
    }

}