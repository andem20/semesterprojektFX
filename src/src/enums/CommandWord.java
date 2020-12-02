package src.enums;

public enum CommandWord {
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    UNKNOWN("?"),
    SHOW("show"),
    INVENTORY("inventory"),
    BUY("buy"),
    SELL("sell"),
    SOW("sow"),
    HARVEST("harvest"),
    FERTILIZE("fertilize"),
    LEARN("learn");

    private final String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }
}