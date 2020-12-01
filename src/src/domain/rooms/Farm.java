package src.domain.rooms;

import src.domain.Room;
import src.domain.characters.Wife;

public class Farm extends Room {

    private final Wife wife;

    public Farm(String description) {
        super(description);

        this.wife = new Wife("wife");
    }

    public Wife getWife() {
        return wife;
    }
}
