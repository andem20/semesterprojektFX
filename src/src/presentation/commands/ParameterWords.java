package src.presentation.commands;

import src.enums.ParameterWord;

import java.util.HashMap;

public class ParameterWords {
  private final HashMap<String, ParameterWord> validCommands;

  public ParameterWords() {
    validCommands = new HashMap<>();
    for(ParameterWord command : ParameterWord.values()) {
      if(command != ParameterWord.UNKNOWN) {
        validCommands.put(command.toString(), command);
      }
    }
  }

  public ParameterWord getSecondWord(String secondWord) {
    ParameterWord second = validCommands.get(secondWord);
    if(second != null) {
      return second;
    } else {
      return ParameterWord.UNKNOWN;
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
