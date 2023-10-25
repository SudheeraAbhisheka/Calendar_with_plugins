package edu.curtin.calplugins;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public interface AppPluginAPI {
    Object getInfo();
    void newCalendarEvent(LocalDate date, String event);
    ArrayList<Map<String, Object>> getEvents();
}