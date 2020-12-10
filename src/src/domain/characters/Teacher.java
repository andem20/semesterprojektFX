package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Teacher extends Character {

    private final ArrayList<String> responses = new ArrayList<>();

    public Teacher(String name) {
        super(name);

        responses.add("Hello student!");
        responses.add("Today I'll be teaching about\nsustainable farming.");
    }

    public String getResponse(int index) {
        return responses.get(index);
    }
}

