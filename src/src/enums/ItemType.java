package src.enums;

public enum ItemType {
  FERTILIZER(50),
  APPLE(2);

  private int price;
  ItemType(int price) {
    this.price = price;
  }

  public int getPrice() {
    return price;
  }
}