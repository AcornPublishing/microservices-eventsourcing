package io.cosmos.assign;

import java.util.*;

public class AssignService {

    public List<Assign> assign(List<Customer> customers, List<Surveyor> surveyors) {

        List<Customer> filteredCustomer = new ArrayList<>();
        for (Customer customer: customers) {
            if (customer.getBirthday().getAge() > 30) {
                filteredCustomer.add(customer);
            }
        }

        filteredCustomer.sort(new Comparator<Customer>() {
            @Override
            public int compare(Customer customer1, Customer customer2) {
                return customer1.getBirthday().getAge().compareTo(customer2.getBirthday().getAge());
            }
        });

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
