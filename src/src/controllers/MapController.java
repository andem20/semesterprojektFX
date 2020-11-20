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

    Label help = (Label) getMain().getView().lookup("#help");

    if(playerBounds.intersects(villageBounds)) {
      help.setTranslateX(getMain().getCharacter().getX());
      help.setTranslateY(getMain().getCharacter().getY() - 30);
      help.toFront();
      help.setText("Press 'F' to enter village.");
      help.setVisible(true);
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
}
