package game.enums;

public enum ParameterWord {
  EAST("east"),
  WEST("west"),
  NORTH("north"),
  SOUTH("south"),
  INVENTORY("inventory"),
  STOCK("stock"),
  COINS("coins"),
  MAP("map"),
  DAYS("days"),
  KNOWLEDGE("knowledge"),
  UNKNOWN("?"),
  STATUS("status"),
  FIELDHEALTH("fieldhealth"),
  // Croptypes
  BEANS(CropType.BEANS.toString()),
  MAIZE(CropType.MAIZE.toString()),
  WHEAT(CropType.WHEAT.toString()),
  SORGUM(CropType.SORGHUM.toString()),
  CHICKPEAS(CropType.CHICKPEAS.toString()),
  // Items
  FERTILIZER(ItemType.FERTILIZER.toString());

  private final String parameterString;

  ParameterWord(String secondString) {
    this.parameterString = secondString.toLowerCase();
  }

  public String toString() {
    return parameterString;
  }
}

