package src.domain.characters;

import src.domain.Character;
import src.enums.CropType;
import src.enums.ItemType;

import java.util.ArrayList;

public class Player extends Character {

  private final ArrayList<String> spouseMessages = new ArrayList<>();
  private final ArrayList<String> teacherMessages = new ArrayList<>();

  public Player(String name, int coins) {
    super(name, coins);

    spouseMessages.add("Hello");
    spouseMessages.add("How are you?");
    spouseMessages.add("Do you know what to do?");
    spouseMessages.add("");
    spouseMessages.add("Can I help you with anything?");

    teacherMessages.add("Hello");
    teacherMessages.add("");

    // Create player's inventory
    addItemAmount(CropType.MAIZE.toString(), 5);
    addItemAmount(CropType.WHEAT.toString(), 10);
    addItemAmount(ItemType.FERTILIZER.toString(), 1);
  }

  public ArrayList<String> getSpouseMessages() {
    return spouseMessages;
  }
  public ArrayList<String> getTeacherMessages() {
    return teacherMessages;
  }

  public void addSpouseMessage(String message) {
    spouseMessages.add(message);
  }
}
