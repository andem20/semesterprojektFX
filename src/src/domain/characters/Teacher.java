package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Teacher extends Character {

    private final ArrayList<String> responses = new ArrayList<>();

    public Teacher(String name) {
        super(name);

        //responses.add("maybe");
        //responses.add("maybe2");
        //responses.add("maybe3");
    }

    public String getResponse(int index) {
        return responses.get(index);
    }
}

