package edu.curtin.calplugins;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Repeat implements AppPlugin {
    @Override
    public void startPlugin(AppPluginAPI api) {
        LocalDate startDate;
        String title;
        int repeat;
        LocalDate nextDay;
        LocalDate afterOneYear;

        Map<String, String> properties = (Map<String, String>) api.getInfo();

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
