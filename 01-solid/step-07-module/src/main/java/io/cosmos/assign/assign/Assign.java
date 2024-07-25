package io.cosmos.assign.assign;

import io.cosmos.assign.customer.Customer;
import io.cosmos.assign.surveyor.Surveyor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Assign {
    private Customer customer;
    private Surveyor surveyor;
}
