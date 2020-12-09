package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class MapController extends FXController {

  @FXML private Pane village;
  @FXML private Pane field;
  @FXML private Pane market;
  @FXML private Pane school;
  @FXML private Pane farm;

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
    updatePlayerBounds();
    villageBounds = village.getBoundsInParent();
    fieldBounds = field.getBoundsInParent();
    marketBounds = market.getBoundsInParent();
    schoolBounds = school.getBoundsInParent();
    farmBounds = farm.getBoundsInParent();

    if(getPlayerBounds().intersects(villageBounds)) {
      showHelpMessage("Press 'F' to enter village.");
    } else if(getPlayerBounds().intersects(fieldBounds)) {
      showHelpMessage("Press 'F' to enter field.");
    } else if (getPlayerBounds().intersects(marketBounds)) {
      showHelpMessage("Press 'F' to enter market.");
    } else if (getPlayerBounds().intersects(schoolBounds)) {
      showHelpMessage("Press 'F' to enter school.");
    } else if (getPlayerBounds().intersects(farmBounds)) {
      showHelpMessage("Press 'F' to enter farm.");
    } else {
      hideHelpMessage();
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    if(keyCode == KeyCode.F) {
      if(getPlayerBounds().intersects(villageBounds)) setScene("village");
      if(getPlayerBounds().intersects(fieldBounds)) setScene("field");
      if(getPlayerBounds().intersects(marketBounds)) setScene("market");
      if(getPlayerBounds().intersects(schoolBounds)) setScene("school");
      if(getPlayerBounds().intersects(farmBounds)) setScene("farm");

      if(getSceneManager().getController() != this) {
        Node exit = getSceneManager().getScene().lookup("#exit");
        getPlayerClass().setX((int) exit.getLayoutX());
        getPlayerClass().setY((int) exit.getLayoutY());
      }
    }
  }
}
