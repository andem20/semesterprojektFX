package game.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import game.domain.Item;
import game.enums.CropType;
import game.enums.ItemType;
import game.presentation.FXController;
import game.presentation.SceneManager;

public class VillageController extends FXController {

  @FXML private ImageView exit;
  @FXML private ImageView maize;
  @FXML private ImageView chickpeas;
  @FXML private ImageView fertilizer;
  @FXML private ImageView apple;

  private Bounds exitBounds;
  private Bounds maizeBounds;
  private Bounds chickpeasBounds;
  private Bounds fertilizerBounds;
  private Bounds appleBounds;

  public VillageController(SceneManager sceneManager) {
    super(sceneManager);
  }

  @Override
  public void update() {
    updatePlayerBounds();
    exitBounds = exit.getBoundsInParent();
    maizeBounds = maize.getBoundsInParent();
    chickpeasBounds = chickpeas.getBoundsInParent();
    fertilizerBounds = fertilizer.getBoundsInParent();
    appleBounds = apple.getBoundsInParent();

    if(getPlayerBounds().intersects(exitBounds)) {
      showHelpMessage("Press 'F' to exit.");
    } else if(intersectsCrop()) {
      showHelpMessage("Press 'E' to collect item.");
    } else {
      hideHelpMessage();
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    if(keyCode == KeyCode.F && getPlayerBounds().intersects(exitBounds)) {
      setScene("map");
      Node village = getSceneManager().getScene().lookup("#village");
      getPlayerClass().setX((int) village.getLayoutX());
      getPlayerClass().setY((int) village.getLayoutY());
    }

    if(keyCode == KeyCode.E) {

      String message = null;

      if(getPlayerBounds().intersects(maizeBounds) && maize.isVisible()) {
        Item item = getPlayerClass().getInventory().get(CropType.MAIZE.toString());
        item.setAmount(item.getAmount() + 5);
        message = "5x Maize";
        maize.setVisible(false);
      }

      if(getPlayerBounds().intersects(chickpeasBounds) && chickpeas.isVisible()) {
        Item item = getPlayerClass().getInventory().get(CropType.CHICKPEAS.toString());
        item.setAmount(item.getAmount() + 5);
        message = "5x Chickpeas";
        chickpeas.setVisible(false);
      }

      if(getPlayerBounds().intersects(fertilizerBounds) && fertilizer.isVisible()) {
        Item item = getPlayerClass().getInventory().get(ItemType.FERTILIZER.toString());
        item.setAmount(item.getAmount() + 2);
        message = "2x Fertilizer";
        fertilizer.setVisible(false);
      }

      if(getPlayerBounds().intersects(appleBounds) && apple.isVisible()) {
        Item item = getPlayerClass().getInventory().get(ItemType.APPLE.toString());
        item.setAmount(item.getAmount() + 1);
        message = "1x apple";
        apple.setVisible(false);
      }

      if(message != null) {
        message += " was added to your inventory";
        getSceneManager().getGameOverlay().updateMessages(message);
      }
    }
  }

  private boolean intersectsCrop() {
    return getPlayerBounds().intersects(maizeBounds) && maize.isVisible()
        || getPlayerBounds().intersects(chickpeasBounds) && chickpeas.isVisible()
        || getPlayerBounds().intersects(fertilizerBounds) && fertilizer.isVisible()
        || getPlayerBounds().intersects(appleBounds) && apple.isVisible();
  }
}
