package lab7.task_2_2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Exercise2OrdersTest extends CompanyDomain {
    /**
     * Aggregate the total order values by city.
     */
    @Test
    public void totalOrderValuesByCity() {
        Map<String, Double> map = company.getCustomers().stream()
                .collect(Collectors.groupingBy(
                        Customer::getCity,
                        Collectors.summingDouble(Customer::getTotalOrderValue)
                ));

        assertEquals(2, map.size());
        assertEquals(446.25, map.get("London"), 0.0);
        assertEquals(857.0, map.get("Liphook"), 0.0);
    }

    /**
     * Extra credit. Create a map where the values are customers and the key is the price of
     * the most expensive item that the customer ordered.
     */

    @Test
    public void mostExpensiveItem() {
        Map<Double, List<Customer>> map = company.getCustomers().stream()
                .collect(Collectors.groupingBy(Customer::getMostExpensiveItemValue));

        assertEquals(2, map.size());
        assertEquals(2, map.entrySet().size());
        assertEquals(
                Arrays.asList(
                        this.company.getCustomerNamed("Fred"),
                        this.company.getCustomerNamed("Bill")),
                map.get(50.0));
    }

}
