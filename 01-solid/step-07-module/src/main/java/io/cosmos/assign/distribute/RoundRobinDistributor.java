package io.cosmos.assign.distribute;

import io.cosmos.assign.assign.Assign;
import io.cosmos.assign.customer.Customer;
import io.cosmos.assign.surveyor.Surveyor;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinDistributor implements Distributable {
    @Override
    public List<Assign> distribute(List<Customer> customers, List<Surveyor> surveyors) {
        int i = 0;
        List<Assign> assigns = new ArrayList<>();
        for (Customer customer: customers) {
            Surveyor surveyor = surveyors.get(i % surveyors.size());
            Assign newAssign = new Assign(customer, surveyor);
            assigns.add(newAssign);
            i++;
        }
        return assigns;
    }
}
