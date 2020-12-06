package src.enums;

public enum GameSettings {
  TILESIZE(60),
  VELOCITY(3),
  DAY(1),
  // https://www.economist.com/special-report/2020/03/26/africas-population-will-double-by-2050
  // SSA growth percentage = 2.5% pr. year
  POPGROWTH(0.25),
  TAX(3),
  MAX_MESSAGES(20),
  SEED_AMOUNT(5),
  YIELD_FACTOR(10);

  private final double value;

  GameSettings(double value) {
    this.value = value;
  }

  public int toInt() {
    return (int) value;
  }

  public double toDouble() {
    return value;
  }
}
