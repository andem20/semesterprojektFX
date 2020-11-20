package src.controllers;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import src.FXController;
import src.Main;

public class MapController extends FXController {

  public MapController(Main main) {
    super(main);
  }

  @Override
  public void update() {

  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds switchSceneBounds = getMain().getView().lookup("#hometown").getBoundsInParent();

    if(keyCode == KeyCode.F && playerBounds.intersects(switchSceneBounds)) {
      getMain().setView("hometown");
    }
  }
}
