package src.domain.rooms;

import src.domain.Character;
import src.domain.Item;
import src.domain.Room;
import src.enums.CropType;
import src.enums.GameSettings;
import src.enums.ItemType;

import java.util.Map;

public class Market extends Room {

  public static Character NPC;

  public Market(String description) {
    super(description);

    NPC = new Character("Seller", 5000);
    NPC.addItemAmount(CropType.BEANS.toString(), 10);
    NPC.addItemAmount(CropType.MAIZE.toString(), 10);
    NPC.addItemAmount(CropType.WHEAT.toString(), 10);
    NPC.addItemAmount(CropType.CHICKPEAS.toString(), 10);
    NPC.addItemAmount(CropType.SORGHUM.toString(), 10);
    NPC.addItemAmount(ItemType.FERTILIZER.toString(), 10);

    addTax();
  }

  public Character getNPC() {
    return NPC;
  }

  private void addTax() {
    for(Map.Entry<String, Item> entry : NPC.getInventory().entrySet()) {
      entry.getValue().setPrice(entry.getValue().getPrice() + GameSettings.TAX.toInt());
    }
  }
}
