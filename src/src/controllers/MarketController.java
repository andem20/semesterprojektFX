package src.controllers;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.FXController;
import src.Main;
import src.rooms.Market;

public class MarketController extends FXController {

    private Market market;

    public MarketController(Main main) {
        super(main);

        market = new Market("Market");
    }

    @Override
    public void update() {
        Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
        Bounds exitBounds = getMain().getView().lookup("#exit").getBoundsInParent();

        Label help = (Label) getMain().getView().lookup("#help");

        if(playerBounds.intersects(exitBounds)) {
            helpMessage("Press' F' to exit", help);
        } else  {
            help.setVisible(false);
        }
    }


    @Override
    public void onKeyPressed(KeyCode keyCode) {
        Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
        Bounds switchSceneBounds = getMain().getView().lookup("#exit").getBoundsInParent();

        if(keyCode == KeyCode.F &&  playerBounds.intersects(switchSceneBounds)) {
            getMain().setView("map");
            getMain().getCharacter().setX((int) getMain().getView().lookup("#market").getLayoutX());
            getMain().getCharacter().setY((int) getMain().getView().lookup("#market").getLayoutY());
        }

    }
}
