package src.domain.rooms;

import src.domain.Character;
import src.domain.Room;

public class Farm extends Room {

    private final Character family;

    public Farm(String description) {
        super(description);

        this.family = new Character("family");
    }
}
