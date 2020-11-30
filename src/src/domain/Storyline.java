package src.domain;

import java.util.ArrayList;
import java.util.List;

public class Storyline {
  private final List<String> storyline;
  private int level = 1;

  public Storyline() {
    this.storyline = new ArrayList<>();

    addStory("Congratulations you have reached level 2!\n" +
        "your next target is a hungerlevel of 0.6\n" +
        "If this seems like a tough task then, we recommend trying to go to school,\n" +
        "and learn about some new methods and crops");

    addStory("congratulations on level 3 your town has a lot of food now, and People seem to be flocking to you\n" +
        "But be careful, these new people mean that your population is steadily growing and fast!\n" +
        "so be careful and watch your foodSupply so you don't regress to the earlier levels.");

    addStory("Level 4! you're almost done with ridding the town from hunger!\n " +
        "Keep learning and pushing those numbers and get to level 5 finish the game!");

    addStory("Level 5!!! You have managed to clear the hunger in your town.\n" +
        "You managed to learn about the benefits of growing different crops, rebuilding the soil, \n" +
        "and build network using the market \n" +
        "Thank you very much for playing our game and we hope you leave with some new insights and knowledge \n" +
        "about agriculture in the remote areas of the world \n " +
        "Sincerely Team 3");
  }

  // TODO decrease level when hungerlevel rises.
  public void printStoryline(double hungerLevel) {
    if(hungerLevel >= 0.4 && level == 1) printStory();

    if(hungerLevel >= 0.5 && level == 2) printStory();

    if(hungerLevel >= 0.7 && level == 3) printStory();

    if(hungerLevel <= 0.8 && level == 4) printStory();
  }

  public void printStory() {
    System.out.println("________________________________________________");
    System.out.println(getStory(level));
    System.out.println("________________________________________________");
    level++;
  }

  public void addStory(String story) {
    storyline.add(story);
  }

  public String getStory(int level) {
    return storyline.get(level - 1);
  }
}
