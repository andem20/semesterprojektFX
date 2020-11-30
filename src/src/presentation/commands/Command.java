/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two parts: a CommandWord and a string
 * (for example, if the command was "take map", then the two parts
 * are TAKE and "map").
 * 
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the CommandWord is UNKNOWN.
 *
 * If the command had only one word, then the second word is <null>.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

package src.presentation.commands;

import src.domain.enums.CommandWord;
import src.domain.enums.ParameterWord;

public class Command {
    private final CommandWord commandWord;
    private final ParameterWord parameterWord;

    public Command(CommandWord commandWord, ParameterWord parameterWord) {
        this.commandWord = commandWord;
        this.parameterWord = parameterWord;
    }

    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    public ParameterWord getSecondWord() {
        return parameterWord;
    }

    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }

    public boolean hasSecondWord()
    {
        return (parameterWord != ParameterWord.UNKNOWN);
    }
}

