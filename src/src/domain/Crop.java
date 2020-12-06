package src.domain;

import src.enums.CropType;

public class Crop extends Item {
  private final CropType cropType;
  public final int yield;
  public final double nutrition;

  public Crop(int amount, CropType cropType) {
    super(cropType.toString(), amount, cropType.getPrice());

    this.yield = cropType.getYield();
    this.cropType = cropType;
    this.nutrition = cropType.getNutrition();
  }
}
