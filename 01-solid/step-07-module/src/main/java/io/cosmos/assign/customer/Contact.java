package io.cosmos.assign.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Contact {

    private ContactType type;
    private String phoneNumber;

    public static enum ContactType {
        Landline,
        Mobile;
    }
}
