package src.commands;

import java.util.Scanner;

public class Parser {
    private final CommandWords commands;
    private final SecondWords seconds;
    private final Scanner reader;

    public Parser() {
        commands = new CommandWords();
        seconds = new SecondWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand() {
        String inputLine;
        String command = null;
        String second = null;

        System.out.print("> "); 

        inputLine = reader.nextLine();

        String[] inputArray = inputLine.split(" ");

        command = inputArray[0].toLowerCase();
        second = inputArray.length > 1 ? inputArray[1].toLowerCase() : null;

        return new Command(commands.getCommandWord(command), seconds.getSecondWord(second));
    }

    public void showCommands() {
        commands.showAll();
    }

    public void showSeconds() {
        seconds.showAll();
    }
}
