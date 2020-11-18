package src;

public class Item {
  private final String name;
  private int amount;
  public int price;

  public Item(String name, int amount, int price) {
    this.name = name;
    this.amount = amount;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
}