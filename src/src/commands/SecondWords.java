package src.commands;

import src.enums.SecondWord;

import java.util.HashMap;

public class SecondWords {
  private final HashMap<String, SecondWord> validCommands;

  public SecondWords() {
    validCommands = new HashMap<>();
    for(SecondWord command : SecondWord.values()) {
      if(command != SecondWord.UNKNOWN) {
        validCommands.put(command.toString(), command);
      }
    }
  }

  public SecondWord getSecondWord(String secondWord) {
    SecondWord second = validCommands.get(secondWord);
    if(second != null) {
      return second;
    } else {
      return SecondWord.UNKNOWN;
    }
  }

  public boolean isSecondWord(String aString) {
    return validCommands.containsKey(aString);
  }

  public void showAll() {
    for(String second : validCommands.keySet()) {
      System.out.print(second + "  ");
    }

    System.out.println();
  }
}
