package lab7.task_2_2;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by olenasyrota on 6/28/16.
 */
public class Company {

    private final String name;
    private final List<Customer> customers = new ArrayList<>();
    // suppliers are array based.
    private Supplier[] suppliers = new Supplier[0];

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addCustomer(Customer aCustomer) {
        this.customers.add(aCustomer);
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public List<Order> getOrders() {
        assert (false);// Refactor this code to use lambdas

        return this.customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toList());
    }

    public Customer getMostRecentCustomer() {
        return this.customers.get(customers.size()-1);
    }

    public void addSupplier(Supplier supplier) {
        // need to replace the current array of suppliers with another, larger array
        // Of course, normally one would not use an array.
        this.suppliers = Stream.concat(Arrays.stream(this.suppliers), Stream.of(supplier))
                .toArray(Supplier[]::new);
    }

    public Supplier[] getSuppliers() {
        return this.suppliers;
    }

    public Customer getCustomerNamed(String name) {
        return this.customers.stream()
                .filter(customer -> customer.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}