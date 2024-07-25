package io.cosmos.assign;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignServiceTest {

    @Test
    public void assign() {
        AssignService assignService = new AssignService();

        Customer customer1 = new Customer("C1", "고객#1", "19911001", "W");
        Customer customer2 = new Customer("C2", "고객#2", "19901001", "M");
        Customer customer3 = new Customer("C3", "고객#3", "19891001", "M");
        Customer customer4 = new Customer("C4", "고객#4", "19971001", "W");
        Customer customer5 = new Customer("C5", "고객#5", "19921001", "M");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);

        for (Customer customer: customers) {
            System.out.println(customer.getName() + "(" + assignService.calculateAge(customer) + ")");
        }
        System.out.println("");

        Surveyor surveyor1 = new Surveyor("S1", "조사원#1");
        Surveyor surveyor2 = new Surveyor("S2", "조사원#2");

        List<Surveyor> surveyors = new ArrayList<>();
        surveyors.add(surveyor1);
        surveyors.add(surveyor2);

        List<Customer> filteredCustomer = assignService.assign(customers, surveyors);
        for (Customer customer: filteredCustomer) {
            System.out.println(customer.getName() + "(" + assignService.calculateAge(customer) + "): " + customer.getSurveyorId());
        }

        assertEquals("S2", filteredCustomer.get(1).getSurveyorId());
    }

    @Test
    public void calculateAge() {
        Customer customer = new Customer("1", "홍길동", "19901001", "M");
        AssignService assignService = new AssignService();
        int actual = assignService.calculateAge(customer);
        // 테스트를 실행하는 날짜 기준으로 예상 결과가 다릅니다.
        assertEquals(35, actual);
    }
}
