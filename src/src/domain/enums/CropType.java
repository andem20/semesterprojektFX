package src.domain.enums;

public enum CropType {
  WHEAT(6, 3, 0.5),
  RICE(7, 5, 0.5),
  BEANS(3, 8, 0.9),
  MAIZE(4, 2, 0.3),
  SORGHUM(2, 2, 0.5),
  CHICKPEAS(2,9, 1.0);

  public final int yield;
  public final int price;
  public final double nutrition;

  CropType(int yield, int price, double nutrition) {
    this.yield = yield;
    this.price = price;
    this.nutrition = nutrition;
  }
}
