package io.cosmos.assign.distribute;

import io.cosmos.assign.assign.Assign;
import io.cosmos.assign.customer.Customer;
import io.cosmos.assign.surveyor.Surveyor;

import java.util.List;

public interface Distributable {
    List<Assign> distribute(List<Customer> customers, List<Surveyor> surveyors);
}
