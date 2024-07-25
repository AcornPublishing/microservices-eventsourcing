package io.cosmos.assign;

public class AgeRule implements Rule {

    private int min;
    private int max;

    public AgeRule(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isSatisfied(Customer customer) {
        if (this.min <= customer.getBirthday().getAge() &&
                customer.getBirthday().getAge() <= this.max) {
            return true;
        }
        return false;
    }

    @Override
    public int compare(Customer customer1, Customer customer2) {
        return customer1.getBirthday().getAge()
                .compareTo(customer2.getBirthday().getAge());
    }
}
