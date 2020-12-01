package src.domain.rooms;

import src.domain.Character;
import src.domain.Room;

public class Farm extends Room {

    private final Character family;

    public Farm(String description) {
        super(description);

        this.family = new Character("wife");
    }

    public void talkWithWife() {
        System.out.println("Hello darling! are you back already?");
    }
}
