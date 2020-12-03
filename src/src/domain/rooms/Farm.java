package src.domain.rooms;

import src.domain.Room;
import src.domain.characters.Spouse;

public class Farm extends Room {

    private final Spouse spouse;

    public Farm(String description) {
        super(description);

        this.spouse = new Spouse("wife");
    }

    public Spouse getWife() {
        return spouse;
    }
}
