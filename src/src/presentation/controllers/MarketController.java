package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.presentation.FXController;
import src.GUI;
import src.domain.rooms.Market;

public class MarketController extends FXController {

    private final Market market;

    public MarketController(GUI GUI) {
        super(GUI);

        market = new Market("Market");
    }

    @Override
    public void update() {
        Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
        Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();

        Label help = (Label) getGUI().getView().lookup("#help");

        if(playerBounds.intersects(exitBounds)) {
            helpMessage("Press' F' to exit", help);
        } else  {
            help.setVisible(false);
        }
    }


    @Override
    public void onKeyPressed(KeyCode keyCode) {
        Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
        Bounds switchSceneBounds = getGUI().getView().lookup("#exit").getBoundsInParent();

        if(keyCode == KeyCode.F &&  playerBounds.intersects(switchSceneBounds)) {
            getGUI().setView("map");
            getGUI().getCharacter().setX((int) getGUI().getView().lookup("#market").getLayoutX());
            getGUI().getCharacter().setY((int) getGUI().getView().lookup("#market").getLayoutY());
        }

    }
}
