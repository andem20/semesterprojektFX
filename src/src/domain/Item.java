package src.domain;

public class Item {
  private final String name;
  private int amount;
  private int price;

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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}