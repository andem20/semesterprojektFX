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
        Bounds boardBounds = getMain().getView().lookup("#board").getBoundsInParent();

        Label help = (Label) getMain().getView().lookup("#help");
        Label board = (Label) getMain().getView().lookup("#lecture");

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
        Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
        Bounds switchSceneBounds = getMain().getView().lookup("#exit").getBoundsInParent();

        if(keyCode == KeyCode.F && playerBounds.intersects(switchSceneBounds)) {
            getMain().setView("map");
            getMain().getCharacter().setX((int) getMain().getView().lookup("#school").getLayoutX());
            getMain().getCharacter().setY((int) getMain().getView().lookup("#school").getLayoutY());
        }
    }
}
