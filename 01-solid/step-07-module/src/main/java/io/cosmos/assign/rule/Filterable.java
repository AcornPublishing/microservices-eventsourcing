package io.cosmos.assign.rule;

import io.cosmos.assign.customer.Customer;

public interface Filterable {
    boolean isSatisfied(Customer customer);
}
