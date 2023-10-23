package edu.curtin.calplugins;

import java.time.LocalDate;
import java.util.Map;

public class Repeat implements AppPlugin{
    @Override
    public void startPlugin(AppPluginAPI api) {
        Map<String, Object> repeatHashMap;
        LocalDate startDate;
        String title;
        int repeat;
        LocalDate nextDay;
        LocalDate afterOneYear;

        repeatHashMap = (Map<String, Object>) api.getInfo();

        startDate = (LocalDate) repeatHashMap.get("startDate");
        title = repeatHashMap.get("title").toString();
        repeat = Integer.parseInt(repeatHashMap.get("repeat").toString());

        afterOneYear = startDate.plusYears(1);

        api.newCalendarEvent(startDate, title);
        nextDay = startDate.plusDays(repeat);

        // nextDay.isBefore(afterOneYear)
        while(nextDay.isBefore(afterOneYear)){
            api.newCalendarEvent(nextDay, title);
            nextDay = nextDay.plusDays(repeat);
        }
    }
}
