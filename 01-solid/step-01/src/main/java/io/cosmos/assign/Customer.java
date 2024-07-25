package io.cosmos.assign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {

    private String id;
    private String name;

    private String birthday;
    private String landlinePhone;
    private String mobilePhone;
    private String gender;

    private String surveyorId;

    public Customer(String id, String name, String birthday, String gender) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }
}
