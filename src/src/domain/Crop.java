package src.domain;

import src.enums.CropType;

public class Crop extends Item {
  private final CropType cropType;
  public final int yield;
  public final double nutrition;

  public Crop(int amount, CropType cropType) {
    super(cropType.toString(), amount, cropType.price);

    this.yield = cropType.yield;
    this.cropType = cropType;
    this.nutrition = cropType.nutrition;
  }
}
