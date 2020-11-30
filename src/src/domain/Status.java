package src.domain;

import src.domain.enums.GameSettings;
import src.domain.rooms.Market;

import java.util.Map;

public class Status {
  private int population;
  private final int startPop;
  private int foodSupply;
  private float hungerLevel;
  private final long startTime;

  public Status(int population) {
    this.population = population;
    this.startPop = population;
    this.foodSupply = 0;
    this.startTime = System.currentTimeMillis();
  }

  public boolean checkStatus() {
    int foodSupply = 0;
    for(Map.Entry<String, Item> entry : Market.NPC.getInventory().entrySet()) {
      if(entry.getValue().getClass().equals(Crop.class)) {
        foodSupply += entry.getValue().getAmount() * ((Crop) entry.getValue()).nutrition;
      }
    }

    this.foodSupply = foodSupply;

//  "Rentes rente" k = k0 * (1 + p)^n
    double p = GameSettings.POPGROWTH.toDouble();
    double n = (getPassedTime() / GameSettings.DAY.toInt()) * .365;
    population = (int) (startPop * Math.pow(1 + p, n));

    hungerLevel = 1 - (float) this.foodSupply / population;

    return hungerLevel >= 0.8;
  }

  public void printStatus() {
    System.out.println("Days passed: " + getDays());
    System.out.println("Population: " + getPopulation());
    System.out.println("Foodsupply: " + getFoodSupply());
    System.out.println("Hungerlevel: " + getHungerLevel());
  }

  public double getPassedTime() {
    // Convert miliseconds to seconds
    return (System.currentTimeMillis() - startTime) / 1000.0;
  }

  public int getDays() {
    // 1 day = 5 seconds
    return (int) getPassedTime() / GameSettings.DAY.toInt();
  }

  public float getHungerLevel() {
    return hungerLevel;
  }

  public int getPopulation() {
    return population;
  }

  public int getFoodSupply() {
    return foodSupply;
  }
}
