package edu.curtin.terminalgriddemo;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventObject {
    private LocalDate date;
    private LocalTime time;
    private int duration;
    private String event;
    private boolean isItAllday;

    public EventObject(LocalDate date) {
        this.date = date;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setItAllday(boolean itAllday) {
        isItAllday = itAllday;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

