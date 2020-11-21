package src.controllers;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.FXController;
import src.Main;

public class VillageController extends FXController {

  public VillageController(Main main) {
    super(main);
  }

  @Override
  public void update() {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getMain().getView().lookup("#exit").getBoundsInParent();

    Label help = (Label) getMain().getView().lookup("#help");

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else {
      help.setVisible(false);
    }
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

  public void helpMessage(String msg, Label label) {
    label.setTranslateX(getMain().getCharacter().getX());
    label.setTranslateY(getMain().getCharacter().getY() - 30);
    label.toFront();
    label.setText(msg);
    label.setVisible(true);
  }
}
