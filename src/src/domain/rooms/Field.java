package src.domain.rooms;

import src.domain.Crop;
import src.domain.Item;
import src.domain.Room;
import src.domain.Timer;

public class Field extends Room {

    private double fieldHealth = 1;
    private Crop prevYield = null;
    private boolean sowed = false;
    private final int SEED_AMOUNT = 5;
    private Timer timer;

    //TODO vejrgenerator
    public Field(String description) {
        super(description);
    }

    public void sow(Crop crop, Timer timer) {
        this.timer = timer;
        // Check if field is occupied
        if(sowed) {
            System.out.println("You have to harvest your crops before you can sow again.");
            Timer.timers.remove(timer);
            return;
        }

        // Check if we have enough crops
        if(crop.getAmount() < SEED_AMOUNT) {
            System.out.println("You need " + SEED_AMOUNT + " " + crop.getName() + "!");
            return;
        }

        // Check if we are sowing the same crop
        if(prevYield != null) {
            if(crop.getName().equals(prevYield.getName())) {
                fieldHealth *= 0.6;
            }
        }

        System.out.println(crop.getName() + " was sowed... They'll be ready in " + timer.getTime() + " days...");
        crop.setAmount(crop.getAmount() - SEED_AMOUNT);

        prevYield = crop;
        sowed = true;
    }

    public void harvest() {
        if(!sowed) {
            System.out.println("You haven't sowed anything!");
            return;
        }

        if(isReadyCrops()) {
            int yield = (int) ((SEED_AMOUNT * prevYield.yield) * fieldHealth);
            prevYield.setAmount(prevYield.getAmount() + yield);
            System.out.println("You harvested " + yield + " " + prevYield.getName());
            sowed = false;
            return;
        }

        System.out.println("Crops not ready yet!");
    }

    public void fertilize(Item fertilizer) {
        // Check if we have enough fertilizer
        if(fertilizer.getAmount() < 1) {
            System.out.println("You need don't have enough fertilizer!");
            return;
        }

        fertilizer.setAmount(fertilizer.getAmount() - 1);
        fieldHealth *= 1.5;
        fieldHealth = Math.min(1, fieldHealth);
    }

    public double getFieldHealth() {
        return fieldHealth;
    }

    public boolean isSowed() {
        return sowed;
    }

    public boolean isReadyCrops() {
        return !Timer.timers.contains(timer);
    }

    public Timer getTimer() {
        return timer;
    }
}


