package src.rooms;

import src.Character;
import src.Room;

public class Farm extends Room {

    private final Character family;

    public Farm(String description) {
        super(description);

        this.family = new Character("family");
    }
}
