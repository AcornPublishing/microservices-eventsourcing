package io.cosmos.assign.rule;

import io.cosmos.assign.customer.Customer;

import java.util.List;

public class Sortables {
    private Sortable sortable;

    public Sortables(Sortable sortable) {
        this.sortable = sortable;
    }

    public List<Customer> sort(List<Customer> customers) {
        customers.sort(sortable);
        return customers;
    }
}
