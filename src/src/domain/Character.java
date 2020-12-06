package src.domain;

import src.enums.CropType;
import src.enums.ItemType;

import java.util.HashMap;

public class Character {
  private final String name;
  private final HashMap<String, Item> inventory;
  private int coins;
  private int x;
  private int y;

  public Character(String name, int coins) {
    this.name = name;
    this.inventory = new HashMap<>();
    this.coins = coins;

    for(CropType crop : CropType.values()) {
      addItem(crop.toString(), new Crop(0, crop));
    }

    for(ItemType item : ItemType.values()) {
      addItem(item.toString(), new Item(item.toString(), 0, item.getPrice()));
    }
  }

  public Character(String name) {
    this(name, 0);
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

  public void addItemAmount(Item item, int amount) {
    item.setAmount(item.getAmount() + amount);
  }

  public void addItemAmount(String itemType, int amount) {
    Item item = getItem(itemType);
    item.setAmount(item.getAmount() + amount);
  }

  public void removeItemAmount(Item item, int amount) {
    item.setAmount(item.getAmount() - amount);
  }

  public void addCoins(int amount) {
    setCoins(getCoins() + amount);
  }

  public void removeCoins(int amount) {
    setCoins(getCoins() - amount);
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
