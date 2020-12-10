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

  public ParameterWord getParameterWord(String parameterWord) {
    ParameterWord parameter = validCommands.get(parameterWord);
    if(parameter != null) {
      return parameter;
    } else {
      return ParameterWord.UNKNOWN;
    }
  }

  public boolean isParameterWord(String aString) {
    return validCommands.containsKey(aString);
  }

  public void showAll() {
    for(String parameter : validCommands.keySet()) {
      System.out.print(parameter + "  ");
    }

    System.out.println();
  }
}
