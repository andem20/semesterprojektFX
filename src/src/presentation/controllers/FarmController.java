package src.presentation.controllers;

import javafx.scene.control.Label;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import src.domain.rooms.Farm;
import src.presentation.FXController;
import src.GUI;

public class FarmController extends FXController {

  private final Farm farm;
  private int messageIndex = 0;

  public FarmController(GUI GUI) {
    super(GUI);

    farm = new Farm("farm");
  }

  @Override
  public void update() {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds wifeBounds = getGUI().getView().lookup("#wife").getBoundsInParent();

    Label help = (Label) getGUI().getView().lookup("#help");

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else if(playerBounds.intersects(wifeBounds)) {
      helpMessage("Press 'F' to talk with wife.", help);
    } else {
      getGUI().getGameOverlay().getCharacterLabel().setVisible(false);
      getGUI().getView().lookup("#wifeLabel").setVisible(false);
      help.setVisible(false);
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds switchSceneBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds wifeBounds = getGUI().getView().lookup("#wife").getBoundsInParent();

    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(switchSceneBounds)) {
        getGUI().setView("map");
        getGUI().getCharacter().setX((int) getGUI().getView().lookup("#farm").getLayoutX());
        getGUI().getCharacter().setY((int) getGUI().getView().lookup("#farm").getLayoutY());
        return;
      }

      if(playerBounds.intersects(wifeBounds)) {

//        Print all message options from player
//        Make possible to choose message

        getGUI().getGameOverlay().setCharacterLabel(getGUI().getCharacter().getWifeMessages().get(messageIndex));

        getGUI().getGameOverlay().getCharacterLabel().setTranslateX(getGUI().getCharacter().getX());
        getGUI().getGameOverlay().getCharacterLabel().setTranslateY(getGUI().getCharacter().getY());

        ((Label) getGUI().getView().lookup("#wifeLabel")).setText(farm.getWife().getResponse(messageIndex));
        getGUI().getView().lookup("#wifeLabel").setVisible(true);

        messageIndex = Math.min(messageIndex + 1, getGUI().getCharacter().getWifeMessages().size() - 1);
      } else {
        getGUI().getView().lookup("#wifeLabel").setVisible(false);
      }
    }
  }
}
