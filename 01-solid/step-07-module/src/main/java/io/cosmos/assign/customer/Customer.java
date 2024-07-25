package io.cosmos.assign.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Customer {

    private String id;
    private String name;

    private Birthday birthday;
    private List<Contact> contacts;
    private String gender;

    private Address address;

    private Customer() {
        this.contacts = new ArrayList<>();
    }

    public Customer(String id, String name, String birthday, String landlinePhone, String mobilePhone, String gender) {
        this();

        this.id = id;
        this.name = name;
        this.birthday = new Birthday(birthday);
        if (!landlinePhone.isEmpty()) {
            this.contacts.add(new Contact(Contact.ContactType.Landline, landlinePhone));
        }
        if (!mobilePhone.isEmpty()) {
            this.contacts.add(new Contact(Contact.ContactType.Mobile, mobilePhone));
        }
        this.gender = gender;
    }
}
