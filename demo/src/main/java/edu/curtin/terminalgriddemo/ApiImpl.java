package edu.curtin.terminalgriddemo;

import edu.curtin.calplugins.AppPluginAPI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class ApiImpl implements AppPluginAPI {
    private TerminalGridDemo obj;

    public ApiImpl(TerminalGridDemo obj){
        this.obj = obj;
    }

    @Override
    public Map<String, String> getInfo(){
        return obj.getInfo();
    }

    @Override
    public void newCalendarEvent(LocalDate date, String event) {
        obj.setNewCalendarEvent(date, event);
    }

    @Override
    public void newCalendarEvent(LocalDate date, LocalTime time, int duration, String event) {
        obj.setNewCalendarEvent(date, time, duration, event);
    }


    @Override
    public ArrayList<Map<String, Object>> getEvents() {
        return obj.getNotificationItems();
    }
}