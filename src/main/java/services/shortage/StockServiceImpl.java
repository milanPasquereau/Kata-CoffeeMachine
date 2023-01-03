package services.shortage;

import services.shortage.BeverageQuantityChecker;
import services.shortage.EmailNotifier;
import services.shortage.StockService;

public class StockServiceImpl implements StockService {

    private final EmailNotifier emailNotifier;

    private final BeverageQuantityChecker beverageQuantityChecker;

    public StockServiceImpl(EmailNotifier emailNotifier, BeverageQuantityChecker beverageQuantityChecker) {
        this.emailNotifier = emailNotifier;
        this.beverageQuantityChecker = beverageQuantityChecker;
    }

    @Override
    public boolean checkIfBeverageIsEmpty(String order) {
        return beverageQuantityChecker.isEmpty(order);
    }

    @Override
    public void notifyMissingDrink(String order) {
        emailNotifier.notifyMissingDrink(order);
    }
}
