package game.domain;

import game.enums.GameSettings;
import java.util.ArrayList;

public class Timer {
    public static ArrayList<Timer> timers = new ArrayList<>();
    private final long startTime;
    private final String message;
    private final int time;
    private final int days;

    public Timer(int days, String message) {
        this.days = days;
        this.message = message;
        time = days * GameSettings.DAY.toInt();
        startTime = System.nanoTime();
        timers.add(this);
    }

    public String update(){
        if((System.nanoTime() - startTime) / 1e9 >= time) {
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
        return Math.min((System.nanoTime() - startTime) / 1e9, time);
    }
}