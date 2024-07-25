package io.cosmos.assign;

public class AgeAssignRule implements AssignRule {

    private int min;
    private int max;

    public AgeAssignRule(int min, int max) {
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
    public int compare(Customer before, Customer after) {
        return before.getBirthday().getAge()
                .compareTo(after.getBirthday().getAge());
    }
}
