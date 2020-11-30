package src;

import src.rooms.Market;

import java.util.Map;

public class Status {
  private int population;
  private final int startPop;
  private int foodSupply;
  private float hungerLevel;
  private final long startTime;
  private final double POPGROWTH = .7;

  public Status(int population) {
    this.population = population;
    this.startPop = population;
    this.foodSupply = 0;
    this.startTime = System.currentTimeMillis();
  }

  public boolean checkStatus() {
    int foodSupply = 0;
    for(Map.Entry<String, src.Item> entry : Market.NPC.getInventory().entrySet()) {
      if(entry.getValue().getClass().equals(src.Crop.class)) {
        foodSupply += entry.getValue().getAmount() * ((src.Crop) entry.getValue()).nutrition;
      }
    }

    this.foodSupply = foodSupply;

//  "Rentes rente" k = k0 * (1 + P)^n
    population = (int) (startPop * Math.pow(1 + POPGROWTH, (double) getPassedTime() / 500));

    hungerLevel= 1 - (float) this.foodSupply / (float) population;

    return hungerLevel >= 0.2;
  }

  public void printStatus() {
    System.out.println("Days passed: " + getPassedTime());
    System.out.println("Population: " + population);
    System.out.println("Foodsupply: " + foodSupply);
    System.out.println("Hungerlevel: " + hungerLevel);
  }

  public int getPassedTime() {
    return (int) (System.currentTimeMillis() - startTime) / 1000;
  }

  public float getHungerLevel() {
    return hungerLevel;
  }

  public int getPopulation() {
    return population;
  }
}
