package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Wife extends Character {

  private ArrayList<String> responses = new ArrayList<>();

  public Wife(String name) {
    super(name);

    responses.add("I'm good, but starving!!!");
    responses.add("I don't know...");
  }

  public String getResponse(int index) {
    return responses.get(index);
  }
}
