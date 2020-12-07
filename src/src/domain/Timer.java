package src.domain;

import src.enums.GameSettings;
import java.util.ArrayList;

public class Timer {
    public static ArrayList<Timer> timers = new ArrayList<>();
    private final long startTime;
    private final String message;
    private final int time;
    private final int days;
    private long currentTime;

    public Timer(int days, String message) {
        this.days = days;
        this.message = message;
        time = days * GameSettings.DAY.toInt();
        startTime = System.nanoTime();
        timers.add(this);
        currentTime = startTime;
    }

    public String updateTimer(long currentTime){
        this.currentTime = currentTime;
        if((currentTime - startTime) / 1e9 >= time) {
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
        return Math.min((currentTime - startTime) / 1e9, time);
    }
}