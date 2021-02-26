package game.domain.rooms;

import game.domain.Room;
import game.domain.characters.Teacher;

import java.util.ArrayList;

public class School extends Room {

  private final ArrayList<String> knowledges;
  private int level = 1;

  private final Teacher teacher;

  public School(String description) {
    super(description);

    this.teacher = new Teacher("Teacher");
    knowledges = new ArrayList<>();

    knowledges.add(
        "Hello!\n" +
        "Welcome to your first lecture!\n" +
        "Maize and sorghum are the food staples in this area as they are resilient to the local climate.\n" +
        "Additionally, wheat is a good addition to your diet.\n" +
        "It is important to keep sowing these cereals to provide food for your people.\n" +
        "However, to keep up with the growing population, you'll need to increase your production. \n" +
        "This is not an easy task and requires some changes that might cost you more in the beginning," +
        "but will help you build a more sustainable and increased food production in the long term. \n" +
        "Research shows that there is plenty of potential to increase the yields.\n" +
        "I'll encourage you to try sowing and harvesting your first crops and see what happens.\n" +
        "See you at the next lecture!"
    );

    knowledges.add(
         "Welcome to your second lecture!\n" +
         "I hope you have had some successful yields!\n" +
         "It is important to take care of the soil as well as you do tend to your basic crops.\n" +
         "The soil can be full of nutrients, but planting the same crop over and over again\n" +
         "could lead to soil exhaustion and a lowered level of nutrients for your crops.\n" +
         "This could lead to a lower yield and increased  hunger in your village.\n" +
         "You might have noticed this when you first sowed your crops.\n" +
         "Try buying several different types of crops and rotating them on your field! \n" +
         "See you at the next lecture!"
    );

    knowledges.add(
        "Hello!\n" +
        "Welcome to your third lecture!\n" +
        "Nutrient food is important when fighting hunger.\n" +
        "Pulses such as beans and chickpeas are known to be full of nutrients and provide a good source\n" +
        "of plant protein to your diet.\n" +
        "To improve the well being of your people, it is important to add pulses to your crop selection.\n" +
        "Pulses are also good for the soil health, but more on that in the next lecture!\n" +
        "Back to the market and the field! Remember to sell your excess crops!\n" +
        "See you!"
    );

    knowledges.add(
       "Hello again!\n" +
       "Welcome to your fourth lecture!\n" +
       "Did you notice a difference in the hunger levels of your town after harvesting and selling some pulses?\n" +
       "They grow slower, but are necessary in eradicating hunger in your town.\n" +
       "Additionally, you might have noticed that your SOILHEALTH improved as well?\n" +
       "That is because pulses provide nutrients, especially phosphor and nitrogen, " +
       "to the soil and effectively slow down erosion.\n" +
       "When you sow maize or sorghum in rotation with pulses, you'll increase your food security, " +
       "because you can harvest throughout the season. \n" +
       "If you then sow pulses along with your maize and sorghum, you can increase the output.\n" +
       "Try mixing up the crops a couple of times and see what happens!\n" +
       "Successful yields!"
    );
    knowledges.add(
       "Hello again!\n" +
       "Welcome to your fifth lecture!\n" +
       "I hope your HUNGERLEVEL and SOILHEALTH are on the rise!\n" +
       "Although pulses improve the level of nutrients in the ground, this might not be enough" +
       "to reach the full potential of your crops.\n" +
       "It is sometimes necessary to use fertilizers as well. \n" +
       "Animal derived fertilizers work wonders to improve the yields and to feed your people.\n" +
       "They are easily accessible and affordable.\n" +
       "Fertilizers can also reduce the soil exhaustion and make land use more sustainable.\n" +
       "I suggest you give it a try in combination with the knowledge you've gained from the previous lectures.\n" +
       "This was your last lecture, good luck with increasing your food production! \n" +
       "Off you go!"
    );
  }

  public String teach() {
    return getKnowledge();
  }

  public String getKnowledge() {
    return knowledges.get((level - 1) % knowledges.size());
  }

  public void increaseLevel() {
    level++;
  }

  public Teacher getTeacher() {
    return teacher;
  }
}
