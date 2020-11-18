package src.rooms;

import src.Room;

import java.util.ArrayList;

public class School extends Room {

  private final ArrayList<String> knowledges;

  public School(String description) {
    super(description);

    knowledges = new ArrayList<>();

    knowledges.add(
        "Hello!\n" +
        "Welcome to your first lecture!\n" +
        "You should take care of the soil as you do with your basic crops.\n" +
        "The soil can be full of nutrients, but planting the same crop over and over again\n" +
        "could lead to soil exhaustion and a lowered level of nutrients for your crops.\n" +
        "This could lead to a lower yield and an increase of hunger in your village.\n" +
        "Instead try improving the soil by rotating your crops.\n" +
        "Try buying several crops and sow them in your field!"
    );

    knowledges.add(
        "Hello!\n" +
        "Welcome to your second lecture!\n" +
        "Nutrient food is important when fighting hunger.\n" +
        "Pulses are known to be full of nutrients and also good for the soil health.\n" +
        "But these grows slower and are more expensive than cereals.\n" +
        "See what happens when you buy and sow chickpeas and beans in rotation with your basic crops.\n" +
        "Back to the market and the field!!"
    );

    knowledges.add(
        "Hello!\n" +
        "Welcome to your third lecture!\n" +
        "Using fertilizer can be a very efficient way for your crops to get enough nutrients.\n" +
        "It can also reduce the exhaustion of your soil and make it more sustainable.\n" +
        "You can buy fertilizer at the market!\n" +
        "Off you go!"
    );
  }

  public void teach(int knowledgeLevel) {
    System.out.println(getKnowledge(knowledgeLevel));
  }

  public String getKnowledge(int knowledgeLevel) {
    return knowledges.get((knowledgeLevel - 1) % knowledges.size());
  }

  public ArrayList<String> getKnowledges() {
    return knowledges;
  }
}
