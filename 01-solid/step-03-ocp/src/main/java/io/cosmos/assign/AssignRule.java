package io.cosmos.assign;

import java.util.Comparator;

public interface AssignRule extends Comparator<Customer> {
    boolean isSatisfied(Customer customer);
}
