package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Player extends Character {

  private ArrayList<String> wifeMessages = new ArrayList<>();

  public Player(String name, int coins) {
    super(name, coins);

    wifeMessages.add("How are you?");
    wifeMessages.add("When is supper!!!???");
  }

  public ArrayList<String> getWifeMessages() {
    return wifeMessages;
  }
}
