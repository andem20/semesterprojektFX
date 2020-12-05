package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Player extends Character {

  private final ArrayList<String> spouseMessages = new ArrayList<>();

  public Player(String name, int coins) {
    super(name, coins);

    spouseMessages.add("How are you?");
    spouseMessages.add("When is supper!!!???");
    spouseMessages.add("Can I help you with anything?");
  }

  public ArrayList<String> getSpouseMessages() {
    return spouseMessages;
  }
}
