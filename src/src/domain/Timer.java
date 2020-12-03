package src.domain;

import src.GUI;
import src.enums.GameSettings;

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

    public String updateTimer(){
        if((System.currentTimeMillis() - startTime) / 1000 >= time) {
            timers.remove(this);
            return message;
        }

        return null;
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