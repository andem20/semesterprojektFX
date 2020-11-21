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
    Bounds farmBounds = getMain().getView().lookup("#farm").getBoundsInParent();

    Label help = (Label) getMain().getView().lookup("#help");

    if(playerBounds.intersects(villageBounds)) {
      helpMessage("Press 'F' to enter village.", help);
    } else if(playerBounds.intersects(farmBounds)) {
      helpMessage("Press 'F' to enter farm.", help);
    } else {
      help.setVisible(false);
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds villageBounds = getMain().getView().lookup("#village").getBoundsInParent();

    if(keyCode == KeyCode.F && playerBounds.intersects(villageBounds)) {
      getMain().setView("village");
    }
  }

  public void helpMessage(String msg, Label label) {
    label.setTranslateX(getMain().getCharacter().getX());
    label.setTranslateY(getMain().getCharacter().getY() - 30);
    label.toFront();
    label.setText(msg);
    label.setVisible(true);
  }
}
