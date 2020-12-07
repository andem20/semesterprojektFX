package src.enums;

public enum CropType {
  WHEAT(7, 3, 0.5),
  BEANS(4, 8, 0.8),
  MAIZE(6, 2, 0.3),
  SORGHUM(2, 2, 0.5),
  CHICKPEAS(4,9, 0.8);

  private final int yield;
  private final int price;
  private final double nutrition;

  CropType(int yield, int price, double nutrition) {
    this.yield = yield;
    this.price = price;
    this.nutrition = nutrition;
  }

  public int getYield() {
    return yield;
  }

  public int getPrice() {
    return price;
  }

  public double getNutrition() {
    return nutrition;
  }
}
