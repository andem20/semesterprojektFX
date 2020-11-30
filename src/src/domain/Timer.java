package src.domain;

import src.domain.enums.GameSettings;

import java.util.ArrayList;

public class Timer {
    public static ArrayList<Timer> timers = new ArrayList<>();
    private final long startTime;
    private final String message;
    private final int time;
    private final int days;

    public Timer(int days, String message) {
        this.days = days;
        this.time = days * GameSettings.DAY.toInt();
        this.startTime = System.currentTimeMillis();
        this.message = message;
        timers.add(this);
    }

    public void updateTimer(){
        if((System.currentTimeMillis() - startTime) / 1000 >= time) {
            System.out.println(message);
            timers.remove(this);
        }
    }

    public int getDays() {
        return days;
    }

    public int getTime() {
        return time;
    }

    public double getCurrentTime() {
        return Math.min((System.currentTimeMillis() - startTime) / 1000.0, time);
    }
}