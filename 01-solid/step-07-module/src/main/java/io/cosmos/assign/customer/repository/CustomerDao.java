package io.cosmos.assign.customer.repository;

import io.cosmos.assign.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    //
    private List<Customer> customers;

    public CustomerDao() {
        //
        this.customers = new ArrayList<>();

        Customer customer1 = new Customer("C1", "고객#2", "19901001", "", "", "M");
        Customer customer3 = new Customer("C3", "고객#1", "19911001", "", "", "W");
        Customer customer5 = new Customer("C5", "고객#5", "19891001", "", "", "M");
        Customer customer2 = new Customer("C2", "고객#3", "19971001", "", "", "W");
        Customer customer4 = new Customer("C4", "고객#4", "19921001", "", "", "M");

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);
    }
    public List<Customer> selectAll() {
        //
        return customers;
    }
}
