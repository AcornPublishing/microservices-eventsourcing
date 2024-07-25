package io.cosmos.assign;

import java.util.*;

public class AssignService {

    public List<Assign> assign(List<Customer> customers, List<Surveyor> surveyors) {

        //AgeRule ageRule = new AgeRule(30, 100);
        GenderAssignRule rule = new GenderAssignRule("M");
        List<Customer> filteredCustomer = new ArrayList<>();
        for (Customer customer: customers) {
            if (rule.isSatisfied(customer)) {
                filteredCustomer.add(customer);
            }
        }

        filteredCustomer.sort(rule);

        int i = 0;
        List<Assign> assigns = new ArrayList<>();
        for (Customer customer: filteredCustomer) {
            Surveyor surveyor = surveyors.get(i % surveyors.size());
            Assign newAssign = new Assign(customer, surveyor);
            assigns.add(newAssign);
            i++;
        }

        return assigns;
    }
}
