package src.domain;

import java.util.ArrayList;
import java.util.List;

public class Storyline {
  private final List<String> storyline;
  private int level = 1;

  public Storyline() {
    this.storyline = new ArrayList<>();

    addStory("Your Journey starts in Sub-Saharan Africa, in a small remote village.\n" +
        "You've just inherited your family's farm.\n" +
        "It's now your job to grow some crops,\n" +
        "and gather enough food to feed your family and the entire village!\n" +
        "Your first goal is to reach a hunger level below 60%.");

    addStory("Congratulations you have reached level 2!\n" +
        "your next target is a hungerlevel of 0.6\n" +
        "If this seems like a tough task then, we recommend trying to go to school,\n" +
        "and learn about some new methods and crops");

    addStory("Congratulations! On level 3 your town has a lot of food now, and People seem to be flocking to you\n" +
        "But be careful, these new people mean that your population is steadily growing and faster!\n" +
        "So be careful and watch your foodSupply, so you don't regress to to a lower level.");

    addStory("Level 4! You're almost done with ridding the town from hunger!\n " +
        "Keep learning and pushing those numbers and get to level 5 to finish the game!");

    addStory("Level 5!!! You have managed to clear hunger in your town.\n" +
        "You managed to learn about the benefits of growing different crops, rebuilding the soil, \n" +
        "and building a network using the market. \n" +
        "Thank you very much for playing our game and we hope you leave with some new insights and knowledge \n" +
        "about agriculture in the remote areas of the world \n " +
        "Sincerely Team 3");
  }

  public void addStory(String story) {
    storyline.add(story);
  }

  public String getStory() {
    return storyline.get(getLevel() - 1);
  }

  public int getLevel() {
    return level;
  }

  public void increaseLevel() {
    this.level++;
  }
}
