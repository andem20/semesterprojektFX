package game.domain.rooms;

import game.domain.Room;
import game.domain.characters.Spouse;

public class Farm extends Room {

    private final Spouse spouse;

    public Farm(String description) {
        super(description);

        this.spouse = new Spouse("Spouse");
    }

    public Spouse getSpouse() {
        return spouse;
    }
}
