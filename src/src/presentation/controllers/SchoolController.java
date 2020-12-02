package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.presentation.FXController;
import src.GUI;

public class SchoolController extends FXController {

    public SchoolController(GUI GUI) {
        super(GUI);
    }

    @Override
    public void update() {
        Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
        Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
        Bounds boardBounds = getGUI().getView().lookup("#board").getBoundsInParent();

        Label help = (Label) getGUI().getView().lookup("#help");
        Label board = (Label) getGUI().getView().lookup("#lecture");

        if(playerBounds.intersects(exitBounds)) {
            helpMessage("Press 'F' to exit.", help);
        } else {
            help.setVisible(false);
        }
        //TODO: exitbounds for market, hometown and farm (doorSouth, doorNorth and doorEast) - find exitBounds

        if (playerBounds.intersects(boardBounds)) {
            helpMessage("lecture", board);
        } else {
            board.setVisible(false);
        } //TODO: get lectures from School.class one at a time
    }


    @Override
    public void onKeyPressed(KeyCode keyCode) {
        Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
        Bounds switchSceneBounds = getGUI().getView().lookup("#exit").getBoundsInParent();

        if(keyCode == KeyCode.F && playerBounds.intersects(switchSceneBounds)) {
            getGUI().setView("map");
            getGUI().getCharacter().setX((int) getGUI().getView().lookup("#school").getLayoutX());
            getGUI().getCharacter().setY((int) getGUI().getView().lookup("#school").getLayoutY());
        }
    }
}
