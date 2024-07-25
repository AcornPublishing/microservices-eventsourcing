package io.cosmos.assign.rule;

import io.cosmos.assign.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class Filterables {
    private Filterable filterable;

    public Filterables(Filterable filterable) {
        this.filterable = filterable;
    }

    public List<Customer> filter(List<Customer> customers) {
        List<Customer> results = new ArrayList<>();

        for (Customer customer: customers) {
            if (filterable.isSatisfied(customer)) {
                results.add(customer);
            }
        }

        return results;
    }
}
