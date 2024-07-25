package io.cosmos.assign.rule;

import io.cosmos.assign.customer.Customer;

public class GenderRule implements Filterable, Sortable {

    private String gender;

    public GenderRule(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean isSatisfied(Customer customer) {
        if (this.gender.equals(customer.getGender())) {
            return true;
        }
        return false;
    }

    @Override
    public int compare(Customer customer1, Customer customer2) {
        return customer1.getGender()
                .compareTo(customer2.getGender());
    }
}
