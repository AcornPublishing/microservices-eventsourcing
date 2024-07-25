package io.cosmos.assign;

import java.util.*;

public class AssignService {

    public List<Assign> assign(List<Customer> customers, List<Surveyor> surveyors) {
        GenderRule rule = new GenderRule("M");
        Filterables filterables = new Filterables(rule);
        List<Customer> filteredCustomer = filterables.filter(customers);

        Sortables sortables = new Sortables(rule);
        filteredCustomer = sortables.sort(filteredCustomer);

        Distributable distributable = new RoundRobinDistributor();
        List<Assign> assigns = distributable.distribute(filteredCustomer, surveyors);

        return assigns;
    }
}
