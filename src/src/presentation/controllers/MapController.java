package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.presentation.FXController;
import src.GUI;

public class MapController extends FXController {

  public MapController(GUI GUI) {
    super(GUI);
  }

  @Override
  public void update() {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds villageBounds = getGUI().getView().lookup("#village").getBoundsInParent();
    Bounds fieldBounds = getGUI().getView().lookup("#field").getBoundsInParent();
    Bounds marketBounds = getGUI().getView().lookup("#market").getBoundsInParent();
    Bounds schoolBounds = getGUI().getView().lookup("#school").getBoundsInParent();
    Bounds farmBounds = getGUI().getView().lookup("#farm").getBoundsInParent();

    Label help = (Label) getGUI().getView().lookup("#help");

    if(playerBounds.intersects(villageBounds)) {
      helpMessage("Press 'F' to enter village.", help);
    } else if(playerBounds.intersects(fieldBounds)) {
      helpMessage("Press 'F' to enter field.", help);
    } else if (playerBounds.intersects(marketBounds)) {
      helpMessage("Press 'F' to enter market", help);
    } else if (playerBounds.intersects(schoolBounds)) {
      helpMessage("Press 'F' to enter school", help);
    } else if (playerBounds.intersects(farmBounds)) {
      helpMessage("Press 'F' to enter farm", help);
    } else {
      help.setVisible(false);
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds villageBounds = getGUI().getView().lookup("#village").getBoundsInParent();
    Bounds fieldBounds = getGUI().getView().lookup("#field").getBoundsInParent();
    Bounds marketBounds = getGUI().getView().lookup("#market").getBoundsInParent();
    Bounds schoolBounds = getGUI().getView().lookup("#school").getBoundsInParent();
    Bounds farmBounds = getGUI().getView().lookup("#farm").getBoundsInParent();

    if(keyCode == KeyCode.F && playerBounds.intersects(villageBounds)) {
      getGUI().setView("village");
      getGUI().getCharacter().setX((int) getGUI().getView().lookup("#exit").getLayoutX());
      getGUI().getCharacter().setY((int) getGUI().getView().lookup("#exit").getLayoutY());
    }

    if(keyCode == KeyCode.F && playerBounds.intersects(fieldBounds)) {
      getGUI().setView("field");
      getGUI().getCharacter().setX((int) getGUI().getView().lookup("#exit").getLayoutX());
      getGUI().getCharacter().setY((int) getGUI().getView().lookup("#exit").getLayoutY());
    }
    if (keyCode == KeyCode.F && playerBounds.intersects(marketBounds)) {
      getGUI().setView("market");
      getGUI().getCharacter().setX((int) getGUI().getView().lookup("#exit").getLayoutX());
      getGUI().getCharacter().setY((int) getGUI().getView().lookup("#exit").getLayoutY());
    }

    if (keyCode == KeyCode.F && playerBounds.intersects(schoolBounds)) {
      getGUI().setView("school");
      getGUI().getCharacter().setX((int) getGUI().getView().lookup("#exit").getLayoutX());
      getGUI().getCharacter().setY((int) getGUI().getView().lookup("#exit").getLayoutY());
    }

    if (keyCode == KeyCode.F && playerBounds.intersects(farmBounds)) {
      getGUI().setView("farm");
      getGUI().getCharacter().setX((int) getGUI().getView().lookup("#exit").getLayoutX());
      getGUI().getCharacter().setY((int) getGUI().getView().lookup("#exit").getLayoutY());
    }
  }
}
