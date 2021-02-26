package game.enums;

public enum GameSettings {
  TILESIZE(60),
  VELOCITY(3),
  DAY(2),
  POPGROWTH(0.25),
  TAX(3),
  MAX_MESSAGES(20),
  SEED_AMOUNT(5),
  YIELD_FACTOR(1.5),
  HARVEST_TIME(10),
  FERTILIZER_COLLECT_TIME(100);

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
