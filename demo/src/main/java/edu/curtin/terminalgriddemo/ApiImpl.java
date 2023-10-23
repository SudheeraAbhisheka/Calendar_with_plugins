package edu.curtin.terminalgriddemo;

import edu.curtin.calplugins.AppPluginAPI;

import java.time.LocalDate;

public class ApiImpl implements AppPluginAPI {
    private TerminalGridDemo obj;

    public ApiImpl(TerminalGridDemo obj){
        this.obj = obj;
    }

    @Override
    public Object getInfo(){
        return obj.getInfo();
    }

    @Override
    public void newCalendarEvent(LocalDate date, String event) {
        obj.setNewCalendarEvent(date, event);
    }

}