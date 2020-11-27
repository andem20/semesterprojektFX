package src.controllers;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.FXController;
import src.Main;

public class MapController extends FXController {

  public MapController(Main main) {
    super(main);
  }

  @Override
  public void update() {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds villageBounds = getMain().getView().lookup("#village").getBoundsInParent();
    Bounds farmBounds = getMain().getView().lookup("#field").getBoundsInParent();
    Bounds marketBounds = getMain().getView().lookup("#market").getBoundsInParent();
    Bounds schoolBounds = getMain().getView().lookup("#school").getBoundsInParent();

    Label help = (Label) getMain().getView().lookup("#help");

    if(playerBounds.intersects(villageBounds)) {
      helpMessage("Press 'F' to enter village.", help);
    } else if(playerBounds.intersects(farmBounds)) {
      helpMessage("Press 'F' to enter field.", help);
    } else if (playerBounds.intersects(marketBounds)) {
      helpMessage("Press 'F' to enter market", help);
    } else if (playerBounds.intersects(schoolBounds)) {
      helpMessage("Press 'F' to enter school", help);
    } else {
      help.setVisible(false);
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds villageBounds = getMain().getView().lookup("#village").getBoundsInParent();
    Bounds farmBounds = getMain().getView().lookup("#field").getBoundsInParent();
    Bounds marketBounds = getMain().getView().lookup("#market").getBoundsInParent();
    Bounds schoolBounds = getMain().getView().lookup("#school").getBoundsInParent();

    if(keyCode == KeyCode.F && playerBounds.intersects(villageBounds)) {
      getMain().setView("village");
      getMain().getCharacter().setX((int) getMain().getView().lookup("#exit").getLayoutX());
      getMain().getCharacter().setY((int) getMain().getView().lookup("#exit").getLayoutY());
    }

    if(keyCode == KeyCode.F && playerBounds.intersects(farmBounds)) {
      getMain().setView("field");
      getMain().getCharacter().setX((int) getMain().getView().lookup("#exit").getLayoutX());
      getMain().getCharacter().setY((int) getMain().getView().lookup("#exit").getLayoutY());
    }
    if (keyCode == KeyCode.F && playerBounds.intersects(marketBounds)) {
      getMain().setView("market");
      getMain().getCharacter().setX((int) getMain().getView().lookup("#exit").getLayoutX());
      getMain().getCharacter().setY((int) getMain().getView().lookup("#exit").getLayoutY());
    }

    if (keyCode == KeyCode.F && playerBounds.intersects(schoolBounds)) {
      getMain().setView("school");
      getMain().getCharacter().setX((int) getMain().getView().lookup("#exit").getLayoutX());
      getMain().getCharacter().setY((int) getMain().getView().lookup("#exit").getLayoutY());
    }
  }
}
