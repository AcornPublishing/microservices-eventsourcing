package io.cosmos.assign;

import java.util.List;

public interface Distributable {
    List<Assign> distribute(List<Customer> customers, List<Surveyor> surveyors);
}
