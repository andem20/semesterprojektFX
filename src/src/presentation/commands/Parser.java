package src.presentation.commands;

import java.util.Scanner;

public class Parser {
    private final CommandWords commands;
    private final ParameterWords parameter;
    private final Scanner reader;

    public Parser() {
        commands = new CommandWords();
        parameter = new ParameterWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand() {
        String inputLine;
        String command;
        String second;

        System.out.print("> "); 

        inputLine = reader.nextLine();

        String[] inputArray = inputLine.split(" ");

        command = inputArray[0].toLowerCase();
        second = inputArray.length > 1 ? inputArray[1].toLowerCase() : null;

        return new Command(commands.getCommandWord(command), parameter.getSecondWord(second));
    }

    public void showCommands() {
        commands.showAll();
    }

    public void showParameters() {
        parameter.showAll();
    }
}
