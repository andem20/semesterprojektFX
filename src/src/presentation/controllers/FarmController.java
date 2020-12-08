package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import src.domain.Item;
import src.domain.characters.Player;
import src.domain.rooms.Farm;
import src.enums.GameSettings;
import src.enums.ItemType;
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
  private long fertilizerTime = 0;
  private boolean task = false;

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
    } else if(playerBounds.intersects(farmBounds) && isFertilizerReady()){
      helpMessage("Press 'E' to collect manure", help);
    } else {
      hideConversationLabel();
      spouseLabel.setVisible(false);
      help.setVisible(false);
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    // Exit
    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(exitBounds)) {
        setScene("map");
        Node farmExit = getSceneManager().getScene().lookup("#farm");
        getPlayer().setX((int) farmExit.getLayoutX());
        getPlayer().setY((int) farmExit.getLayoutY());
        return;
      }
    }

    // Spouse interaction
    if(keyCode == KeyCode.T) {
      if(playerBounds.intersects(spouseBounds)) {
        showConversationLabel();
      } else {
        spouseLabel.setVisible(false);
      }
    }

    // Fertilizer from farm
    if(keyCode == KeyCode.E) {
      if(playerBounds.intersects(farmBounds)) {
        String message = null;
        if(isFertilizerReady()) {
          fertilizerTime = System.nanoTime();
          Item item = getPlayer().getItem(ItemType.FERTILIZER.toString());
          item.setAmount(item.getAmount() + 2);
          message = "2x Fertilizer was added to your inventory";
        }

        if(message != null) getSceneManager().getGameOverlay().updateMessages(message);
      }

      if(playerBounds.intersects(spouseBounds) && hasApple()) {
        getPlayer().getItem(ItemType.APPLE.toString()).decreaseAmount();
        farm.getSpouse().getItem(ItemType.APPLE.toString()).increaseAmount();
        farm.getSpouse().addResponse("Just what I needed. Thank you...");
        getPlayer().addSpouseMessage("");
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
    if(!getPlayer().getSpouseMessages().get(messageIndex).equals("")) {
      getSceneManager().getGameOverlay().setConversationLabel(getPlayer().getSpouseMessages().get(messageIndex));
    }

    if(messageIndex == 4 && !task) {
      task = true;
      getSceneManager().getGameOverlay().updateMessages("TASK: Get an apple from the village.");
    }

    spouseLabel.setText(farm.getSpouse().getResponse(messageIndex));
    spouseLabel.setVisible(true);
    messageIndex = Math.min(messageIndex + 1, getPlayer().getSpouseMessages().size() - 1);
  }

  private boolean isFertilizerReady() {
    double time = (System.nanoTime() - fertilizerTime) / 1e9;
    return (time >= GameSettings.MANURE_COLLECT_TIME.toInt() || fertilizerTime == 0);
  }

  private boolean hasApple() {
    return getPlayer().getItem(ItemType.APPLE.toString()).getAmount() > 0;
  }
}
