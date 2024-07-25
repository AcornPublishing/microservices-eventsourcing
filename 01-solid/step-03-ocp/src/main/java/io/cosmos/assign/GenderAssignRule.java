package io.cosmos.assign;

public class GenderAssignRule implements AssignRule {

    private String gender;

    public GenderAssignRule(String gender) {
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
    public int compare(Customer before, Customer after) {
        return before.getGender()
                .compareTo(after.getGender());
    }
}
