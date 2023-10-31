package edu.curtin.calplugins;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class Repeat implements AppPlugin {
    @Override
    public void startPlugin(AppPluginAPI api) {
        LocalDate startDate;
        LocalTime startTime;
        int duration;
        String title;
        int repeat;
        LocalDate nextDay;
        LocalDate afterOneYear;

        Map<String, String> properties = api.getInfo();

        if(properties.containsKey("startTime")){

            title = properties.get("title");
            startDate = LocalDate.parse(properties.get("startDate"));
            startTime = LocalTime.parse(properties.get("startTime"));
            duration = Integer.parseInt(properties.get("duration"));
            repeat = Integer.parseInt(properties.get("repeat"));

            afterOneYear = startDate.plusYears(1);

            api.newCalendarEvent(startDate, startTime, duration, title);
            nextDay = startDate.plusDays(repeat);

            while (nextDay.isBefore(afterOneYear)) {
                api.newCalendarEvent(nextDay, startTime, duration, title);
                nextDay = nextDay.plusDays(repeat);
            }

        }
        else{
            title = properties.get("title");
            startDate = LocalDate.parse(properties.get("startDate"));
            repeat = Integer.parseInt(properties.get("repeat"));

            afterOneYear = startDate.plusYears(1);

            api.newCalendarEvent(startDate, title);
            nextDay = startDate.plusDays(repeat);

            while (nextDay.isBefore(afterOneYear)) {
                api.newCalendarEvent(nextDay, title);
                nextDay = nextDay.plusDays(repeat);
            }
        }


    }
}
