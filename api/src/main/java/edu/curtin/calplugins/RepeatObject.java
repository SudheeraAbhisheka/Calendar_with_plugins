package edu.curtin.calplugins;

public class RepeatObject {
    // title, startDate, startTime, duration, repeat
    private String title;
    private String startDate;
    private String startTime;
    private String duration;
    private String repeat;

    public RepeatObject(String title, String startDate, String repeat) {
        this.title = title;
        this.startDate = startDate;
        this.repeat = repeat;
    }

    public RepeatObject(String title, String startDate, String startTime, String duration, String repeat) {
        this.title = title;
        this.startDate = startDate;
        this.startTime = startTime;
        this.duration = duration;
        this.repeat = repeat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
