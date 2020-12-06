package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import src.domain.Item;
import src.enums.CropType;
import src.enums.ItemType;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class VillageController extends FXController {

  @FXML private ImageView player;
  @FXML private ImageView exit;
  @FXML private ImageView maize;
  @FXML private ImageView chickpeas;
  @FXML private ImageView fertilizer;
  @FXML private ImageView apple;
  @FXML private Label help;

  private Bounds playerBounds;
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
    playerBounds = player.getBoundsInParent();
    exitBounds = exit.getBoundsInParent();
    maizeBounds = maize.getBoundsInParent();
    chickpeasBounds = chickpeas.getBoundsInParent();
    fertilizerBounds = fertilizer.getBoundsInParent();
    appleBounds = apple.getBoundsInParent();

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else if(playerBounds.intersects(maizeBounds) && maize.isVisible() ||
              playerBounds.intersects(chickpeasBounds) && chickpeas.isVisible() ||
              playerBounds.intersects(fertilizerBounds) && fertilizer.isVisible()) {
      helpMessage("Press 'E' to collect item.", help);
    } else {
      help.setVisible(false);
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    if(keyCode == KeyCode.F && playerBounds.intersects(exitBounds)) {
      setScene("map");
      Node village = getSceneManager().getScene().lookup("#village");
      getPlayer().setX((int) village.getLayoutX());
      getPlayer().setY((int) village.getLayoutY());
    }

    if(keyCode == KeyCode.E) {

      String message = null;

      if(playerBounds.intersects(maizeBounds) && maize.isVisible()) {
        Item item = getPlayer().getInventory().get(CropType.MAIZE.toString());
        item.setAmount(item.getAmount() + 5);
        message = "5x Maize";
        maize.setVisible(false);
      }

      if(playerBounds.intersects(chickpeasBounds) && chickpeas.isVisible()) {
        Item item = getPlayer().getInventory().get(CropType.CHICKPEAS.toString());
        item.setAmount(item.getAmount() + 5);
        message = "5x Chickpeas";
        chickpeas.setVisible(false);
      }

      if(playerBounds.intersects(fertilizerBounds) && fertilizer.isVisible()) {
        Item item = getPlayer().getInventory().get(ItemType.FERTILIZER.toString());
        item.setAmount(item.getAmount() + 2);
        message = "2x Fertilizer";
        fertilizer.setVisible(false);
      }

      if(playerBounds.intersects(appleBounds) && apple.isVisible()) {
        Item item = getPlayer().getInventory().get(ItemType.APPLE.toString());
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
}
