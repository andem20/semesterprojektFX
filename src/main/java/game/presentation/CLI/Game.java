package game.presentation.CLI;

import game.domain.characters.Player;
import game.presentation.CLI.commands.Command;
import game.presentation.CLI.commands.Parser;
import game.domain.*;
import game.domain.Character;
import game.enums.CommandWord;
import game.enums.ItemType;
import game.enums.ParameterWord;
import game.domain.rooms.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private final Parser parser;
    private Room currentRoom;
    private final Status status;
    private Player character;
    private final GameMap gameMap;
    private final Storyline storyline;

    public Game() {
        createRooms();
        createPlayer();

        parser = new Parser();
        status = new Status(100);

        gameMap = new GameMap("game/src/map.txt");

        storyline = new Storyline();
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while(!finished) {
            Command command = parser.getCommand();
            finished = status.update() || processCommand(command);

            if(Timer.timers.iterator().hasNext()) {
                String timerMessage = Timer.timers.iterator().next().update();
                if(timerMessage != null) {
                    System.out.println(timerMessage);
                }
            }

            printStoryline(status.getHungerLevel());

            if(status.getHungerLevel() >= 1.0) {
                System.out.println();
                System.out.println("********************************************");
                System.out.println("************ CONGRATULATIONS!!! ************");
                System.out.println("********************************************");
                System.out.println("You successfully put hunger in your city to an end!");
                finished = true;
            }

            if(status.getHungerLevel() < 0.2) {
                System.out.println();
                System.out.println("*************************************");
                System.out.println("************ YOU LOST!!! ************");
                System.out.println("*************************************");
                System.out.println("You didn't manage to keep up the foodsupply!");
                finished = true;
            }
        }

        System.out.println("Thank you for playing.  Good bye.");
    }

    private void createRooms() {
        School school;
        Farm farm;
        Market market;
        Village village;
        Field field;

        village = new Village("in the hometown");
        market = new Market("in the market");
        school = new School("in the school");
        farm = new Farm("in the farm");
        field = new Field("in the field");

        village.setExit(ParameterWord.WEST, school);
        village.setExit(ParameterWord.EAST, market);
        village.setExit(ParameterWord.SOUTH, field);
        market.setExit(ParameterWord.SOUTH, field);
        market.setExit(ParameterWord.WEST, village);
        market.setExit(ParameterWord.NORTH, school);
        school.setExit(ParameterWord.EAST, market);
        school.setExit(ParameterWord.SOUTH, farm);
        field.setExit(ParameterWord.WEST, farm);
        field.setExit(ParameterWord.EAST, market);
        farm.setExit(ParameterWord.NORTH, school);
        farm.setExit(ParameterWord.EAST, field);

        currentRoom = village;
    }

    private void createPlayer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your name:");
        System.out.print("> ");

        character = new Player(scanner.nextLine(), 100);
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Hello " + character.getName() + ", welcome to our game about hunger!");
        System.out.println("Your Journey starts in Sub-Saharan Africa, in a small remote village.");
        System.out.println("You've just inherited your family's farm.");
        System.out.println("It's now your job to grow some crops,");
        System.out.println("and gather enough food to feed your family and the entire village!");
        System.out.println();
        System.out.println("Start out by typing 'show map' to see where you can go, ");
        System.out.println("but watch out!\nTime is ticking and population is growing!");
        System.out.println("See your status by typing 'show status'.");
        System.out.println("If hungerlevel gets over 0.8 you loose the game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch(commandWord) {
            case HELP : printHelp(); break;
            case GO : goRoom(command); break;
            case QUIT : wantToQuit = quit(command); break;
            case SHOW : show(command); break;
            case BUY : trade(command); break;
            case SELL : trade(command); break;
            case SOW : sow(command); break;
            case HARVEST : harvest(); break;
            case FERTILIZE : fertilize(); break;
            case LEARN : learn(); break;
            default : {
                System.out.println("I don't know what you mean...");
                return false;
            }
        }

        return wantToQuit;
    }

    private void trade(Command command) {
        if(currentRoom instanceof Market) {

            if(!command.hasParameterWord()) {
                System.out.println(command.getCommandWord() + " what?");
                return;
            }

            Market market = (Market) currentRoom;
            Character buyer = character;
            Character seller = market.getNPC();

            String success = "added to";

            if(command.getCommandWord() == CommandWord.SELL) {
                buyer = market.getNPC();
                seller = character;
                success = "removed from";
            }

            Item sellerItem = seller.getItem(command.getParameterWord().toString().toUpperCase());
            Item buyerItem = buyer.getItem(command.getParameterWord().toString().toUpperCase());

            System.out.print("Amount: ");
            Scanner scanner = new Scanner(System.in);

            String input = scanner.nextLine();

            if(input.isEmpty() || !input.matches("([0-9])\\w*")) {
                System.out.println("You have to specify an amount!");
                return;
            }

            int amount = Integer.parseInt(input);

//    Check amount
            if(amount > sellerItem.getAmount()) {
                System.out.println("There is only " + sellerItem.getAmount() + " " + sellerItem.getName() + " available.");
                return;
            }

//    Check total price
            if(amount * sellerItem.getPrice() > buyer.getCoins()) {
                System.out.println("Insufficient funds!");
                return;
            }

            buyer.addItemAmount(buyerItem, amount);
            buyer.removeCoins(sellerItem.getPrice() * amount);
            seller.removeItemAmount(sellerItem, amount);
            seller.addCoins(sellerItem.getPrice() * amount);

            System.out.print(amount + " " + sellerItem.getName() + " was " + success +" your inventory.");
            System.out.println();

            return;
        }

        System.out.println("You have to go to the market!");
    }

    private void show(Command command) {
        if(!command.hasParameterWord()) {
            System.out.println("Show what?");
            return;
        }

        switch(command.getParameterWord()){
            case INVENTORY : showInventory(character.getInventory()); break;
            case STOCK : {
                if(currentRoom instanceof Market) {
                    Character NPC = ((Market) currentRoom).getNPC();
                    System.out.println("+--------------------+");
                    System.out.printf("| Market Coins: %-4s |\n", NPC.getCoins());
                    showInventory(NPC.getInventory());
                } else {
                    System.out.println("You have to go to the market to see the stock.");
                }
            } break;
            case MAP : gameMap.showMap(); break;
            case COINS : System.out.println("Coins: " + character.getCoins()); break;
            case DAYS : System.out.println(status.getPassedTime() + " days has passed."); break;
            case STATUS : printStatus(); break;
            case FIELDHEALTH : {
                if(currentRoom instanceof Field) {
                    System.out.println("Fieldhealth: " + ((Field) currentRoom).getFieldHealth() * 100 + "%");
                } else {
                    System.out.println("You have to go to the field to see fieldhealth.");
                }
            } break;

            default : System.out.println("I don't know what you mean...");
        }
    }

    public void showInventory(HashMap<String, Item> inventory) {
        int width = 0;

        // calculate width for formatting
        for(Map.Entry<String, Item> entry : inventory.entrySet()) {
            width = Math.max(width, entry.getValue().getName().length() + Integer.toString(entry.getValue().getAmount()).length());
        }

        // Create top line
        for(int i = 0; i < width+21; i++) {
            System.out.print((i == 0 || i == width+20 ? "+" : "-"));
        }

        System.out.println();

        System.out.printf("|  %-" + width + "s: %-7s: %-4s |%n", "Item", "Amount", "Price");
        System.out.printf("|  %-" + width + "s  %-14s |%n", "", "", "");
        // Print inventory formatted
        for(Map.Entry<String, Item> entry : inventory.entrySet()) {
            if(entry.getValue().getAmount() > 0) {
                String itemName = entry.getValue().getName();
                int amount = entry.getValue().getAmount();
                int price = entry.getValue().getPrice();
                System.out.printf("|  %-" + width + "s: %-7s: %-5s |%n", itemName, amount, price);
            }
        }

        // Create bottom line
        for(int i = 0; i < width+21; i++) {
            System.out.print((i == 0 || i == width+20 ? "+" : "-"));
        }

        System.out.println();
    }

    private void sow(Command command) {
        if(currentRoom instanceof Field) {
            if(!command.hasParameterWord()) {
                System.out.println("Sow what?");
                return;
            }

            Field field = (Field) currentRoom;

            // Get the specific crop. toUpperCase because of the Enum
            Item item = character.getItem(command.getParameterWord().toString().toUpperCase());

            LinkedList<Crop> crops = new LinkedList<>();

            if(item instanceof Crop) {
                crops.add((Crop) item);
                System.out.println(
                    field.sow(crops, new Timer(5, "Your crops are ready to harvest!"))
                );
                return;
            }

            System.out.println("You can only sow crops!");
            return;
        }

        System.out.println("You have to go to the field.");
    }

    private void harvest() {
        if(currentRoom instanceof Field) {
            System.out.println(((Field) currentRoom).harvest());
            return;
        }

        System.out.println("You have to go to the field.");
    }

    private void fertilize() {
        if(currentRoom instanceof Field) {
            Field field = ((Field) currentRoom);
            System.out.println(
                field.fertilize(character.getItem(ItemType.FERTILIZER.toString()))
            );
            return;
        }

        System.out.println("You have to go to the field.");
    }

    private void learn() {
        if(currentRoom instanceof School) {

            School school = (School) currentRoom;
            System.out.println(school.teach());

            school.increaseLevel();

            return;
        }

        System.out.println("You have to go to the school.");
    }

    private void printStoryline(double hungerLevel) {
        if(hungerLevel <= 0.6 && storyline.getLevel() == 1) printStory();

        if(hungerLevel <= 0.5 && storyline.getLevel() == 2) printStory();

        if(hungerLevel <= 0.3 && storyline.getLevel() == 3) printStory();

        if(hungerLevel <= 0.2 && storyline.getLevel() == 4) printStory();
    }

    private void printStory() {
        System.out.println("________________________________________________");
        System.out.println(storyline.getStory());
        System.out.println("________________________________________________");
        storyline.increaseLevel();
    }

    private void printStatus() {
        System.out.println("Days passed: " + status.getDays());
        System.out.println("Population: " + status.getPopulation());
        System.out.println("Foodsupply: " + status.getFoodSupply());
        System.out.println("Hungerlevel: " + status.getHungerLevel());
    }

    private void printHelp() {
        System.out.println("You can combine a commandword with a secondword.");
        System.out.println("\"go east\", \"buy beans\"");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
        System.out.println();
        System.out.println("Your second words are:");
        parser.showParameters();
    }

    private void goRoom(Command command) {
        if(!command.hasParameterWord()) {
            System.out.println("Go where?");
            System.out.println(currentRoom.getLongDescription());
            return;
        }

        String direction = command.getParameterWord().toString();

        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) {
        if(command.hasParameterWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }
}
