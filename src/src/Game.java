package src;

import src.presentation.commands.Command;
import src.presentation.commands.Parser;
import src.domain.*;
import src.domain.Character;
import src.enums.CommandWord;
import src.enums.CropType;
import src.enums.ItemType;
import src.enums.ParameterWord;
import src.domain.rooms.*;

import java.util.Scanner;

public class Game {
    private final Parser parser;
    private Room currentRoom;
    private final Status status;
    private Character character;
    private final GameMap gameMap;
    private final Storyline storyline;

    public Game() {
        createRooms();
        createCharacter();

        parser = new Parser();
        status = new Status(100);

        gameMap = new GameMap("src/map.txt");

        storyline = new Storyline();
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while(!finished) {
            Command command = parser.getCommand();
            finished = status.checkStatus() || processCommand(command);

            if(Timer.timers.iterator().hasNext()) {
                String timerMessage = Timer.timers.iterator().next().updateTimer();
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
        Hometown hometown;
        Field field;

        hometown = new Hometown("in the hometown");
        market = new Market("in the market");
        school = new School("in the school");
        farm = new Farm("in the farm");
        field = new Field("in the field");

        hometown.setExit(ParameterWord.WEST, school);
        hometown.setExit(ParameterWord.EAST, market);
        hometown.setExit(ParameterWord.SOUTH, field);
        market.setExit(ParameterWord.SOUTH, field);
        market.setExit(ParameterWord.WEST, hometown);
        market.setExit(ParameterWord.NORTH, school);
        school.setExit(ParameterWord.EAST, market);
        school.setExit(ParameterWord.SOUTH, farm);
        field.setExit(ParameterWord.WEST, farm);
        field.setExit(ParameterWord.EAST, market);
        farm.setExit(ParameterWord.NORTH, school);
        farm.setExit(ParameterWord.EAST, field);

        currentRoom = hometown;
    }

    public void createCharacter() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your name:");
        System.out.print("> ");

        character = new Character(scanner.nextLine(), 100);

        // Inventory
        character.addItem(CropType.BEANS.toString(), new Crop(0, CropType.BEANS));
        character.addItem(CropType.MAIZE.toString(), new Crop(5, CropType.MAIZE));
        character.addItem(CropType.WHEAT.toString(), new Crop(10, CropType.WHEAT));
        character.addItem(CropType.CHICKPEAS.toString(), new Crop(0, CropType.CHICKPEAS));
        character.addItem(CropType.RICE.toString(), new Crop(0, CropType.RICE));
        character.addItem(CropType.SORGHUM.toString(), new Crop(0, CropType.SORGHUM));
        character.addItem(ItemType.FERTILIZER.toString(), new Item(ItemType.FERTILIZER.toString(), 1, 50));
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Hello " + character.getName() + ", welcome to our game about hunger!");
        System.out.println("Your Journey starts in Sub-saharan africa, in a small remote village.");
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

    private boolean trade(Command command) {
        if(currentRoom instanceof Market) {

            if(!command.hasSecondWord()) {
                System.out.println(command.getCommandWord() + " what?");
                return false;
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

            return buyer.trade(seller, command.getSecondWord().toString().toUpperCase(), success);
        }

        System.out.println("You have to go to the market!");

        return false;
    }

    private void show(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Show what?");
            return;
        }

        switch(command.getSecondWord()){
            case INVENTORY : character.showInventory(); break;
            case STOCK : {
                if(currentRoom instanceof Market) {
                    ((Market) currentRoom).showStock();
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

    private void sow(Command command) {
        if(currentRoom instanceof Field) {
            if(!command.hasSecondWord()) {
                System.out.println("Sow what?");
                return;
            }

            Field field = (Field) currentRoom;

            // Get the specific crop. toUpperCase because of the Enum
            Item item = character.getItem(command.getSecondWord().toString().toUpperCase());

            if(item instanceof Crop) {
                System.out.println(
                    field.sow((Crop) item, new Timer(5, "Your crops are ready to harvest!"))
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
            school.teach(character.getKnowledgeLevel());

            character.incKnowledgeLevel();

            return;
        }

        System.out.println("You have to go to the school.");
    }

    // TODO decrease level when hungerlevel rises.
    public void printStoryline(double hungerLevel) {
        if(hungerLevel <= 0.6 && storyline.getLevel() == 1) printStory();

        if(hungerLevel <= 0.5 && storyline.getLevel() == 2) printStory();

        if(hungerLevel <= 0.3 && storyline.getLevel() == 3) printStory();

        if(hungerLevel <= 0.2 && storyline.getLevel() == 4) printStory();
    }

    public void printStory() {
        System.out.println("________________________________________________");
        System.out.println(storyline.getStory());
        System.out.println("________________________________________________");
        storyline.increaseLevel();
    }

    public void printStatus() {
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
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            System.out.println(currentRoom.getLongDescription());
            return;
        }

        String direction = command.getSecondWord().toString();

        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }
}
