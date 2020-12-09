package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import src.domain.Item;
import src.domain.rooms.Farm;
import src.enums.GameSettings;
import src.enums.ItemType;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class FarmController extends FXController {

  @FXML private ImageView exit;
  @FXML private ImageView spouse;
  @FXML private Label spouseLabel;
  @FXML private Pane farmPane;

  private Bounds exitBounds;
  private Bounds spouseBounds;
  private Bounds farmBounds;

  private final Farm farm;
  private int messageIndex = 0;
  private long fertilizerTime = 0;
  private boolean task = false;

  public FarmController(SceneManager sceneManager) {
    super(sceneManager);

    farm = new Farm("farm");
  }

  @Override
  public void update() {
    updatePlayerBounds();
    exitBounds = exit.getBoundsInParent();
    spouseBounds = spouse.getBoundsInParent();
    farmBounds = farmPane.getBoundsInParent();

    if(getPlayerBounds().intersects(exitBounds)) {
      showHelpMessage("Press 'F' to exit.");
    } else if(getPlayerBounds().intersects(spouseBounds)) {
      showHelpMessage("Press 'T' to talk with spouse.");
    } else if(getPlayerBounds().intersects(farmBounds) && isFertilizerReady()){
      showHelpMessage("Press 'E' to collect manure");
    } else {
      hideConversationLabel();
      spouseLabel.setVisible(false);
      hideHelpMessage();
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    // Exit
    if(keyCode == KeyCode.F) {
      if(getPlayerBounds().intersects(exitBounds)) {
        setScene("map");
        Node farmExit = getSceneManager().getScene().lookup("#farm");
        getPlayerClass().setX((int) farmExit.getLayoutX());
        getPlayerClass().setY((int) farmExit.getLayoutY());
      }
    }

    // Spouse interaction
    if(keyCode == KeyCode.T) {
      if(getPlayerBounds().intersects(spouseBounds)) {
        showConversationLabel();
      } else {
        spouseLabel.setVisible(false);
      }
    }

    // Fertilizer from farm
    if(keyCode == KeyCode.E) {
      if(getPlayerBounds().intersects(farmBounds)) {
        String message = null;
        if(isFertilizerReady()) {
          fertilizerTime = System.nanoTime();
          Item item = getPlayerClass().getItem(ItemType.FERTILIZER.toString());
          item.setAmount(item.getAmount() + 2);
          message = "2x Fertilizer was added to your inventory";
        }

        if(message != null) getSceneManager().getGameOverlay().updateMessages(message);
      }

      if(getPlayerBounds().intersects(spouseBounds) && hasApple()) {
        getPlayerClass().getItem(ItemType.APPLE.toString()).decreaseAmount();
        farm.getSpouse().getItem(ItemType.APPLE.toString()).increaseAmount();
        farm.getSpouse().addResponse("Just what I needed. Thank you...");
        getPlayerClass().addSpouseMessage("");
        messageIndex++;
        showConversationLabel();
        getSceneManager().getGameOverlay().updateMessages("COMPLETED: Get an apple from the village.");
      }
    }
  }

  private void hideConversationLabel() {
    getSceneManager().getGameOverlay().getConversationLabel().setVisible(false);
  }

  private void showConversationLabel() {
    hideConversationLabel();
    if(!getPlayerClass().getSpouseMessages().get(messageIndex).equals("")) {
      getSceneManager().getGameOverlay().setConversationLabel(getPlayerClass().getSpouseMessages().get(messageIndex));
    }

    if(messageIndex == 4 && !task) {
      task = true;
      getSceneManager().getGameOverlay().updateMessages("TASK: Get an apple from the village.");
    }

    spouseLabel.setText(farm.getSpouse().getResponse(messageIndex));
    spouseLabel.setVisible(true);
    messageIndex = Math.min(messageIndex + 1, getPlayerClass().getSpouseMessages().size() - 1);
  }

  private boolean isFertilizerReady() {
    double time = (System.nanoTime() - fertilizerTime) / 1e9;
    return (time >= GameSettings.MANURE_COLLECT_TIME.toInt() || fertilizerTime == 0);
  }

  private boolean hasApple() {
    return getPlayerClass().getItem(ItemType.APPLE.toString()).getAmount() > 0;
  }
}
