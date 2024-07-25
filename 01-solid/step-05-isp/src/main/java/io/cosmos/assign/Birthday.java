package io.cosmos.assign;

import java.util.Calendar;
import java.util.Date;

public class Birthday {

    private Date value;

    public Birthday(String birthday) {
        String year = birthday.substring(0, 4);
        String month = birthday.substring(5, 6);
        String day = birthday.substring(7, 8);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(year));
        calendar.set(Calendar.MONTH, Integer.valueOf(month)-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));

        this.value = calendar.getTime();
    }

    public Integer getAge() {
        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.setTime(this.value);
        Calendar todayCalendar = Calendar.getInstance();

        return todayCalendar.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR);
    }
}
