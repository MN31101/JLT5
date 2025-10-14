package lab7.task_2_2;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by olenasyrota on 6/28/16.
 */
public class Order {

    private static int nextOrderNumber = 1;

    private final int orderNumber;
    private final List<LineItem> lineItems = new ArrayList<>();
    private boolean isDelivered;

    public Order() {
        this.orderNumber = nextOrderNumber;
        nextOrderNumber += 1;
    }

    public static void resetNextOrderNumber() {
        nextOrderNumber = 1;
    }

    public void deliver() {
        this.isDelivered = true;
    }

    public boolean isDelivered() {
        return this.isDelivered;
    }

    public void addLineItem(LineItem aLineItem) {
        this.lineItems.add(aLineItem);
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    @Override
    public String toString() {
        return "order " + this.orderNumber + " items: " + this.lineItems.size();
    }

    public double getValue() {
        return this.lineItems.stream()
                .mapToDouble(LineItem::getValue)
                .sum();
    }

    public double getMostExpensiveItemValue() {
        return this.lineItems.stream()
                .mapToDouble(LineItem::getValue)
                .max()
                .orElse(0.0);
    }
}