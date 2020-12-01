package src.presentation.controllers;

import javafx.scene.control.Label;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import src.domain.rooms.Farm;
import src.presentation.FXController;
import src.Main;

public class FarmController extends FXController {

  private Farm farm;
  private int messageIndex = 0;

  public FarmController(Main main) {
    super(main);

    farm = new Farm("farm");
  }

  @Override
  public void update() {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getMain().getView().lookup("#exit").getBoundsInParent();
    Bounds wifeBounds = getMain().getView().lookup("#wife").getBoundsInParent();

    Label help = (Label) getMain().getView().lookup("#help");

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else if(playerBounds.intersects(wifeBounds)) {
      helpMessage("Press 'F' to talk with wife.", help);
    } else {
      getMain().getGameOverlay().getCharacterLabel().setVisible(false);
      getMain().getView().lookup("#wifeLabel").setVisible(false);
      help.setVisible(false);
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds switchSceneBounds = getMain().getView().lookup("#exit").getBoundsInParent();
    Bounds wifeBounds = getMain().getView().lookup("#wife").getBoundsInParent();

    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(switchSceneBounds)) {
        getMain().setView("map");
        getMain().getCharacter().setX((int) getMain().getView().lookup("#farm").getLayoutX());
        getMain().getCharacter().setY((int) getMain().getView().lookup("#farm").getLayoutY());
        return;
      }

      if(playerBounds.intersects(wifeBounds)) {

//        Print all message options from player
//        Make possible to choose message

        getMain().getGameOverlay().setCharacterLabel(getMain().getCharacter().getWifeMessages().get(messageIndex));

        getMain().getGameOverlay().getCharacterLabel().setTranslateX(getMain().getCharacter().getX());
        getMain().getGameOverlay().getCharacterLabel().setTranslateY(getMain().getCharacter().getY());

        ((Label) getMain().getView().lookup("#wifeLabel")).setText(farm.getWife().getResponse(messageIndex));
        getMain().getView().lookup("#wifeLabel").setVisible(true);

        messageIndex = Math.min(messageIndex + 1, getMain().getCharacter().getWifeMessages().size() - 1);
      } else {
        getMain().getView().lookup("#wifeLabel").setVisible(false);
      }
    }
  }
}
