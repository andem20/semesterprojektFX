package src.controllers;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import src.FXController;
import src.Main;

public class VillageController extends FXController {

  public VillageController(Main main) {
    super(main);
  }

  @Override
  public void update() {

  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds switchSceneBounds = getMain().getView().lookup("#exit").getBoundsInParent();

    if(keyCode == KeyCode.F && playerBounds.intersects(switchSceneBounds)) {
      getMain().setView("map");
      getMain().getCharacter().setX(9 * 60);
      getMain().getCharacter().setY(120);
    }
  }
}
