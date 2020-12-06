package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class MapController extends FXController {

  @FXML private ImageView player;
  @FXML private Pane village;
  @FXML private Pane field;
  @FXML private Pane market;
  @FXML private Pane school;
  @FXML private Pane farm;
  @FXML private Label help;

  private Bounds playerBounds;
  private Bounds villageBounds;
  private Bounds fieldBounds;
  private Bounds marketBounds;
  private Bounds schoolBounds;
  private Bounds farmBounds;


  public MapController(SceneManager sceneManager) {
    super(sceneManager);
  }

  @Override
  public void update() {
    playerBounds = player.getBoundsInParent();
    villageBounds = village.getBoundsInParent();
    fieldBounds = field.getBoundsInParent();
    marketBounds = market.getBoundsInParent();
    schoolBounds = school.getBoundsInParent();
    farmBounds = farm.getBoundsInParent();

    if(playerBounds.intersects(villageBounds)) {
      helpMessage("Press 'F' to enter village.", help);
    } else if(playerBounds.intersects(fieldBounds)) {
      helpMessage("Press 'F' to enter field.", help);
    } else if (playerBounds.intersects(marketBounds)) {
      helpMessage("Press 'F' to enter market.", help);
    } else if (playerBounds.intersects(schoolBounds)) {
      helpMessage("Press 'F' to enter school.", help);
    } else if (playerBounds.intersects(farmBounds)) {
      helpMessage("Press 'F' to enter farm.", help);
    } else {
      help.setVisible(false);
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(villageBounds)) setScene("village");
      if(playerBounds.intersects(fieldBounds)) setScene("field");
      if(playerBounds.intersects(marketBounds)) setScene("market");
      if(playerBounds.intersects(schoolBounds)) setScene("school");
      if(playerBounds.intersects(farmBounds)) setScene("farm");

      if(getSceneManager().getController() != this) {
        Node exit = getSceneManager().getScene().lookup("#exit");
        getPlayer().setX((int) exit.getLayoutX());
        getPlayer().setY((int) exit.getLayoutY());
      }
    }
  }
}
