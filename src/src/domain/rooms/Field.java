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

    public String sow(Crop crop, Timer timer) {
        this.timer = timer;
        // Check if field is occupied
        if(sowed) {
            Timer.timers.remove(timer);
            return "You have to harvest your crops before you can sow again.";
        }

        // Check if we have enough crops
        if(crop.getAmount() < SEED_AMOUNT) {
            return "You need " + SEED_AMOUNT + " " + crop.getName() + "!";
        }

        // Check if we are sowing the same crop
        if(prevYield != null) {
            if(crop.getName().equals(prevYield.getName())) {
                fieldHealth *= 0.6;
            }
        }

        crop.setAmount(crop.getAmount() - SEED_AMOUNT);

        prevYield = crop;
        sowed = true;

        return crop.getName() + " was sowed... They'll be ready in " + timer.getDays() + " days...";
    }

    public String harvest() {
        if(!sowed) {
            return "You haven't sowed anything!";
        }

        if(isReadyCrops()) {
            int yield = (int) ((SEED_AMOUNT * prevYield.yield) * fieldHealth);
            prevYield.setAmount(prevYield.getAmount() + yield);
            sowed = false;
            return "You harvested " + yield + " " + prevYield.getName();
        }

        return "Crops not ready yet!";
    }

    public String fertilize(Item fertilizer) {
        // Check if we have enough fertilizer
        if(fertilizer.getAmount() < 1) {
            return "You need don't have enough fertilizer!";
        }

        fertilizer.setAmount(fertilizer.getAmount() - 1);
        fieldHealth *= 1.5;
        fieldHealth = Math.min(1, fieldHealth);

        return "Fertilizer was spread on the field.";
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


