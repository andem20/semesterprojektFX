package src.domain;

import src.enums.ParameterWord;

import java.util.HashMap;
import java.util.Set;

public abstract class Room {
    private final String description;
    private final HashMap<String, Room> exits;

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
    }

    public void setExit(ParameterWord direction, Room neighbor) {
        exits.put(direction.toString(), neighbor);
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

