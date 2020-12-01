package src.domain.rooms;

import src.domain.Character;
import src.domain.Crop;
import src.domain.Item;
import src.domain.Room;
import src.enums.CropType;
import src.enums.ItemType;

import java.util.Map;

public class Market extends Room {

  public static Character NPC;
  private final int TAX = 3;

  public Market(String description) {
    super(description);

    NPC = new Character("seller", 5000);
    NPC.addItem(CropType.BEANS.toString(), new Crop(10, CropType.BEANS));
    NPC.addItem(CropType.MAIZE.toString(), new Crop(10, CropType.MAIZE));
    NPC.addItem(CropType.WHEAT.toString(), new Crop(10, CropType.WHEAT));
    NPC.addItem(CropType.CHICKPEAS.toString(), new Crop(10, CropType.CHICKPEAS));
    NPC.addItem(CropType.RICE.toString(), new Crop(10, CropType.RICE));
    NPC.addItem(CropType.SORGHUM.toString(), new Crop(10, CropType.SORGHUM));
    NPC.addItem(ItemType.FERTILIZER.toString(), new Item(ItemType.FERTILIZER.toString(), 10, 50));

    addTax();
  }

  public Character getNPC() {
    return NPC;
  }

  public void showStock() {
    System.out.println("+--------------------+");
    System.out.printf("| Market Coins: %-4s |\n", NPC.getCoins());
    NPC.showInventory();
  }

  private void addTax() {
    for(Map.Entry<String, Item> entry : NPC.getInventory().entrySet()) {
      entry.getValue().price += TAX;
    }
  }
}
