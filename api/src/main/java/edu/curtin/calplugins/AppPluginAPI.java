package edu.curtin.calplugins;

import java.time.LocalDate;
import java.util.EventObject;

public interface AppPluginAPI {
    Object getInfo();
    void newCalendarEvent(LocalDate date, String event);
}