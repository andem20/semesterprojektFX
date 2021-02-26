package game.domain.rooms;

import game.domain.Character;
import game.domain.Item;
import game.domain.Room;
import game.enums.CropType;
import game.enums.GameSettings;
import game.enums.ItemType;

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
