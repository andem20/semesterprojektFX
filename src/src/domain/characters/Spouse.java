package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Spouse extends Character {

  private final ArrayList<String> responses = new ArrayList<>();

  public Spouse(String name) {
    super(name);

    responses.add("Hello my dear.");
    responses.add("I'm good, the yield from the fields is not good this year.");
    responses.add("I don't know... Try go to the school.");
    responses.add("We might have some fertilizer ready to collect in the farm.");
    responses.add("I'm hungry. Get me an apple from the village.\n" +
        "Press 'E' to give an apple.");
  }

  public String getResponse(int index) {
    return responses.get(index);
  }

  public void addResponse(String response) {
    responses.add(response);
  }
}
