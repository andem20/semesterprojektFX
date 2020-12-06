package src.domain.rooms;

import src.domain.Crop;
import src.domain.Item;
import src.domain.Room;
import src.domain.Timer;
import src.enums.CropType;
import src.enums.GameSettings;

import java.util.LinkedList;

public class Field extends Room {

    private double fieldHealth = 1;
    private final LinkedList<Crop> prevYield = new LinkedList<>();
    private boolean sowed = false;
    private final int SEED_AMOUNT = 5;
    private Timer timer;

    public Field(String description) {
        super(description);
    }

    public String sow(LinkedList<Crop> crops, Timer timer) {
        this.timer = timer;
        // Check if field is occupied
        if(sowed) {
            Timer.timers.remove(timer);
            return "You have to harvest your crops before you can sow again.";
        }

        // Check if we have enough crops
        if(crops.stream().anyMatch(c -> c.getAmount() < GameSettings.SEED_AMOUNT.toInt())) {
            return "You need " + SEED_AMOUNT + " " + crops.get(0).getName() + "!";
        }

        // Check if we are sowing the same crop and with pulses
        if(prevYield.size() != 0) {
            boolean pulses = crops.stream().anyMatch(c -> c.getName().equals("BEANS") || c.getName().equals("CHICKPEAS"));
            if(crops.stream().anyMatch(c -> prevYield.stream().anyMatch(p -> p.equals(c)))) {
                if(pulses) {
                    fieldHealth *= 0.95;
                } else {
                    fieldHealth *= 0.6;
                }
            } else if(!pulses) {
                fieldHealth *= 0.85;
            }
        }

        crops.forEach(c -> c.setAmount(c.getAmount() - GameSettings.SEED_AMOUNT.toInt()));

        prevYield.clear();
        prevYield.addAll(crops);
        sowed = true;

        return crops.get(0).getName() + " was sowed... They'll be ready in " + timer.getDays() + " days...";
    }

    public String harvest() {
        if(!sowed) {
            return "You haven't sowed anything!";
        }

        if(isReady()) {
            String message = "";
            int i = 0;
            for(Crop crop : prevYield) {
                int cropYield = (int) ((GameSettings.SEED_AMOUNT.toInt() * crop.yield) * fieldHealth) / prevYield.size();
                cropYield *= GameSettings.YIELD_FACTOR.toInt();
                crop.setAmount(crop.getAmount() + cropYield );
                message += cropYield + " " + crop.getName() + (i < prevYield.size() - 1 ? " and " : "");
                i++;
            }

            sowed = false;
            return "You harvested " + message;
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

    public boolean isReady() {
        return !Timer.timers.contains(timer);
    }

    public Timer getTimer() {
        return timer;
    }
}


