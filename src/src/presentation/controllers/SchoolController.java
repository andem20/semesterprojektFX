package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.presentation.FXController;
import src.Main;

public class SchoolController extends FXController {

    public SchoolController(Main main) {
        super(main);
    }

    @Override
    public void update() {
        Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
        Bounds exitBounds = getMain().getView().lookup("#exit").getBoundsInParent();

        Label help = (Label) getMain().getView().lookup("#help");

        if(playerBounds.intersects(exitBounds)) {
            helpMessage("Press 'F' to exit.", help);
        } else {
            help.setVisible(false);
        }
    }
//TO DO: exitbounds for market, hometown and farm (doorSouth, doorNorth and doorEast) - find exitBounds
    // going into school from map does not function - FIX!

    @Override
    public void onKeyPressed(KeyCode keyCode) {
        Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
        Bounds switchSceneBounds = getMain().getView().lookup("#exit").getBoundsInParent();

        if(keyCode == KeyCode.F && playerBounds.intersects(switchSceneBounds)) {
            getMain().setView("map");
            getMain().getCharacter().setX((int) getMain().getView().lookup("#school").getLayoutX());
            getMain().getCharacter().setY((int) getMain().getView().lookup("#school").getLayoutY());
        }
    }
}