package io.cosmos.assign;

import io.cosmos.assign.customer.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BirthdayTest {

    @Test
    public void getAge() {
        Birthday birthday = new Birthday("19901001");
        int actual = birthday.getAge();
        Assertions.assertEquals(35, actual);
    }
}
