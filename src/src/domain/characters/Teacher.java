package src.domain.characters;

import src.domain.Character;

import java.util.ArrayList;

public class Teacher extends Character {

    private final ArrayList<String> responses = new ArrayList<>();

    public Teacher(String name) {
        super(name);

        responses.add("I'm good, the yield from the fields is not good this year.");
        responses.add("I don't know... Try go to the school.");
        responses.add("I'm hungry. Get me an apple from the village.");
    }

    public String getResponse(int index) {
        return responses.get(index);
    }
}

