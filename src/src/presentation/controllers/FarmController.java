package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import src.domain.rooms.Farm;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class FarmController extends FXController {

  @FXML private ImageView player;
  @FXML private ImageView exit;
  @FXML private ImageView spouse;
  @FXML private Label help;
  @FXML private Label spouseLabel;
  @FXML private Pane farmPane;

  private Bounds playerBounds;
  private Bounds exitBounds;
  private Bounds spouseBounds;
  private Bounds farmBounds;

  private final Farm farm;
  private int messageIndex = 0;

  public FarmController(SceneManager sceneManager) {
    super(sceneManager);

    farm = new Farm("farm");
  }

  @Override
  public void update() {
    playerBounds = player.getBoundsInParent();
    exitBounds = exit.getBoundsInParent();
    spouseBounds = spouse.getBoundsInParent();
    farmBounds = farmPane.getBoundsInParent();

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else if(playerBounds.intersects(spouseBounds)) {
      helpMessage("Press 'T' to talk with spouse.", help);
    } else if(playerBounds.intersects(farmBounds)){
      helpMessage("Press 'E' to collect manure", help);
    } else {
      getSceneManager().getGameOverlay().getConversationLabel().setVisible(false);
      spouseLabel.setVisible(false);
      help.setVisible(false);
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(exitBounds)) {
        setScene("map");
        Node farmExit = getSceneManager().getScene().lookup("#farm");
        getPlayer().setX((int) farmExit.getLayoutX());
        getPlayer().setY((int) farmExit.getLayoutY());
        return;
      }
    }

    if(keyCode == KeyCode.T) {
      if(playerBounds.intersects(spouseBounds)) {

        getSceneManager().getGameOverlay().setConversationLabel(getPlayer().getSpouseMessages().get(messageIndex));

        spouseLabel.setText(farm.getSpouse().getResponse(messageIndex));
        spouseLabel.setVisible(true);

        messageIndex = Math.min(messageIndex + 1, getPlayer().getSpouseMessages().size() - 1);
      } else {
        spouseLabel.setVisible(false);
      }
    }
  }
}
