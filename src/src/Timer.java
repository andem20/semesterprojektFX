package src;

import java.util.ArrayList;

public class Timer {
    public static ArrayList<Timer> timers = new ArrayList<>();
    private final long startTime;
    private final String message;
    private final int time;

    public Timer(int time, String message) {
        this.time = time;
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

    public int getTime() {
        return time;
    }
}