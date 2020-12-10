package src.domain;

import src.enums.CropType;

public class Crop extends Item {
  private final int yield;
  private final double nutrition;

  public Crop(int amount, CropType cropType) {
    super(cropType.toString(), amount, cropType.getPrice());

    this.yield = cropType.getYield();
    this.nutrition = cropType.getNutrition();
  }

  public int getYield() {
    return yield;
  }

  public double getNutrition() {
    return nutrition;
  }
}
