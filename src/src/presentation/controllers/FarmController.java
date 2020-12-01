package src.presentation.controllers;

import javafx.scene.control.Label;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import src.domain.rooms.Farm;
import src.presentation.FXController;
import src.Main;

public class FarmController extends FXController {

  private Farm farm;

  public FarmController(Main main) {
    super(main);

    farm = new Farm("farm");

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
      getMain().getCharacter().setX((int) getMain().getView().lookup("#farm").getLayoutX());
      getMain().getCharacter().setY((int) getMain().getView().lookup("#farm").getLayoutY());
    }
  }
}
