package services.shortage;

public interface StockService {

    boolean checkIfBeverageIsEmpty(String order);

    void notifyMissingDrink(String order);
}
