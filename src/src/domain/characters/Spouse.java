package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Spouse extends Character {

  private final ArrayList<String> responses = new ArrayList<>();

  public Spouse(String name) {
    super(name);

    responses.add("I'm good, but starving!!!");
    responses.add("I don't know...");
  }

  public String getResponse(int index) {
    return responses.get(index);
  }
}
