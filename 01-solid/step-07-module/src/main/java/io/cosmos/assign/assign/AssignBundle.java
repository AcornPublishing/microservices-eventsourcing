package io.cosmos.assign.assign;

import io.cosmos.assign.distribute.Distributable;
import io.cosmos.assign.rule.Filterables;
import io.cosmos.assign.distribute.RoundRobinDistributor;
import io.cosmos.assign.rule.Sortables;
import io.cosmos.assign.customer.Customer;
import io.cosmos.assign.surveyor.Surveyor;

import java.util.*;

public class AssignBundle {

    private List<Customer> customers;
    private List<Surveyor> surveyors;

    private Filterables filterables;
    private Sortables sortables;
    private Distributable distributable;

    public AssignBundle(List<Customer> customers, List<Surveyor> surveyors) {
        this.customers = customers;
        this.surveyors = surveyors;
    }

    public void setFilterables(Filterables filterables) {
        this.filterables = filterables;
    }

    public void setSortables(Sortables sortables) {
        this.sortables = sortables;
    }

    public void setDistributable(Distributable distributable) {
        this.distributable = distributable;
    }

    public List<Assign> assign() {
        List<Customer> filteredCustomer = filterables.filter(customers);
        filteredCustomer = sortables.sort(filteredCustomer);

        Distributable distributable = new RoundRobinDistributor();
        List<Assign> assigns = distributable.distribute(filteredCustomer, surveyors);

        return assigns;
    }
}
