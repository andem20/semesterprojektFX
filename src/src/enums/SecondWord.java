package src.enums;

public enum SecondWord {
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
  RICE(CropType.RICE.toString()),
  WHEAT(CropType.WHEAT.toString()),
  SORGUM(CropType.SORGHUM.toString()),
  CHICKPEAS(CropType.CHICKPEAS.toString()),
  // Items
  FERTILIZER(ItemType.FERTILIZER.toString());

  private String secondString;

  SecondWord(String secondString) {
    this.secondString = secondString.toLowerCase();
  }

  public String toString() {
    return secondString;
  }
}

