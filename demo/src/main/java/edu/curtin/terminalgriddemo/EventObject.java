package edu.curtin.terminalgriddemo;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventObject {
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private String event;
    private boolean isItAllday;

    public EventObject(LocalDate date, String event) {
        this.date = date;
        this.event = event;

        isItAllday = true;
    }

    public EventObject(LocalDate date, LocalTime time, int duration, String event) {
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.event = event;

        isItAllday = false;
    }

    public LocalDate getDate(){
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
    
    public int getHour() {
        return time.getHour();
    }

    public String getEvent() {
        return event;
    }
    public int getDuration() {
        return duration;
    }

    public boolean isItAllday() {
        return isItAllday;
    }
    @Override
    public String toString() {
        return "EventObject{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration=" + duration +
                ", event='" + event + '\'' +
                ", isItAllday=" + isItAllday +
                '}';
    }
}

