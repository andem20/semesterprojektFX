package src;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Character {
  private final String name;
  private final HashMap<String, Item> inventory;
  private int coins;
  private int knowledgeLevel = 1;
  private int x;
  private int y;

  public Character(String name, int coins) {
    this.name = name;
    this.inventory = new HashMap<>();
    this.coins = coins;
  }

  public Character(String name) {
    this.name = name;
    this.inventory = new HashMap<>();
    this.coins = 0;
  }

  public Item getItem(String name) {
    return inventory.get(name);
  }

  public void addItem(String name, Item item) {
    inventory.put(name, item);
  }

  public String getName() {
    return name;
  }

  public int getCoins() {
    return coins;
  }

  public void setCoins(int coins) {
    this.coins = coins;
  }

  public HashMap<String, Item> getInventory() {
    return inventory;
  }

  public void showInventory() {
    int width = 0;

    // calculate width for formatting
    for(Map.Entry<String, Item> entry : inventory.entrySet()) {
      width = Math.max(width, entry.getValue().getName().length() + Integer.toString(entry.getValue().getAmount()).length());
    }

    // Create top line
    for(int i = 0; i < width+21; i++) {
      System.out.print((i == 0 || i == width+20 ? "+" : "-"));
    }

    System.out.println();

    System.out.printf("|  %-" + width + "s: %-7s: %-4s |%n", "Item", "Amount", "Price");
    System.out.printf("|  %-" + width + "s  %-14s |%n", "", "", "");
    // Print inventory formatted
    for(Map.Entry<String, Item> entry : inventory.entrySet()) {
      if(entry.getValue().getAmount() > 0) {
        String itemName = entry.getValue().getName();
        int amount = entry.getValue().getAmount();
        int price = entry.getValue().price;
        System.out.printf("|  %-" + width + "s: %-7s: %-5s |%n", itemName, amount, price);
      }
    }

    // Create bottom line
    for(int i = 0; i < width+21; i++) {
      System.out.print((i == 0 || i == width+20 ? "+" : "-"));
    }

    System.out.println();
  }

  public boolean exchangeItem(Item item, Character seller) {
    System.out.print("Amount: ");
    Scanner scanner = new Scanner(System.in);

    String input = scanner.nextLine();

    if(input.isEmpty() || !input.matches("([0-9])\\w*")) {
      System.out.println("You have to specify an amount!");
      return false;
    }

    int amount = Integer.parseInt(input);

//    Check amount
    if(amount > item.getAmount()) {
      System.out.println("There is only " + item.getAmount() + " " + item.getName() + " available.");
      return false;
    }

//    Check total price
    if(amount * item.price > coins) {
      System.out.println("Insufficient funds!");
      return false;
    }

//    Exchange coins
    setCoins(getCoins() - (amount * item.price));
    seller.setCoins(seller.getCoins() + (amount * item.price));

//    Exchange item
    item.setAmount(item.getAmount() - amount);

    getItem(item.getName())
        .setAmount(getItem(item.getName()).getAmount() + amount);

    System.out.print(amount + " " + item.getName() + " ");

    return true;
  }

  public boolean trade(Character seller, String itemName, String success) {
    for(Map.Entry<String, Item> entry : seller.getInventory().entrySet()) {
      Item item = entry.getValue();

      if(itemName.equals(item.getName())) {
        if(exchangeItem(item, seller)) {
          System.out.print("was " + success +" your inventory.");
          System.out.println();
        }

        return true;
      }
    }

    return false;
  }

  public int getKnowledgeLevel() {
    return knowledgeLevel;
  }

  public void incKnowledgeLevel() {
    this.knowledgeLevel++;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
