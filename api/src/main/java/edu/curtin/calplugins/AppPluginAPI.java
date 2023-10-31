package edu.curtin.calplugins;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public interface AppPluginAPI {
    Map<String, String> getInfo();
    void newCalendarEvent(LocalDate date, String event);
    void newCalendarEvent(LocalDate date, LocalTime time, int duration, String event);
    ArrayList<Map<String, Object>> getEvents();
}