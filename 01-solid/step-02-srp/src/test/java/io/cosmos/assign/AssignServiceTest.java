package io.cosmos.assign;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AssignServiceTest {

    @Test
    public void assign() {
        AssignService assignService = new AssignService();

        Customer customer3 = new Customer("C3", "고객#1", "19911001", "", "", "W");
        Customer customer1 = new Customer("C1", "고객#2", "19901001", "", "", "M");
        Customer customer5 = new Customer("C5", "고객#5", "19891001", "", "", "M");
        Customer customer2 = new Customer("C2", "고객#3", "19971001", "", "", "W");
        Customer customer4 = new Customer("C4", "고객#4", "19921001", "", "", "M");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);

        for (Customer customer: customers) {
            System.out.println(customer.getName() + ", " + customer.getBirthday().getAge());
        }
        System.out.println("");

        Surveyor surveyor1 = new Surveyor("S1", "조사원#1");
        Surveyor surveyor2 = new Surveyor("S2", "조사원#2");

        List<Surveyor> surveyors = new ArrayList<>();
        surveyors.add(surveyor1);
        surveyors.add(surveyor2);

        List<Assign> assigns = assignService.assign(customers, surveyors);
        for (Assign assign: assigns) {
            System.out.println(assign.getCustomer().getName() + ", " + assign.getCustomer().getBirthday().getAge() + ", " + assign.getSurveyor().getId());
        }
    }
}
