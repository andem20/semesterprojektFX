package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.domain.Item;
import src.enums.CropType;
import src.enums.ItemType;
import src.presentation.FXController;
import src.GUI;

public class VillageController extends FXController {

  public VillageController(GUI GUI) {
    super(GUI);
  }

  @Override
  public void update() {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds maizeBounds = getGUI().getView().lookup("#maize").getBoundsInParent();
    Bounds chickpeasBounds = getGUI().getView().lookup("#chickpeas").getBoundsInParent();
    Bounds fertilizerBounds = getGUI().getView().lookup("#fertilizer").getBoundsInParent();
    Bounds appleBounds = getGUI().getView().lookup("#apple").getBoundsInParent();

    Label help = (Label) getGUI().getView().lookup("#help");

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else if(playerBounds.intersects(maizeBounds) ||
              playerBounds.intersects(chickpeasBounds) ||
              playerBounds.intersects(fertilizerBounds)||
              playerBounds.intersects(appleBounds)) {
      helpMessage("Press 'E' to collect item.", help);
    } else {
      help.setVisible(false);
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds switchSceneBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds maizeBounds = getGUI().getView().lookup("#maize").getBoundsInParent();
    Bounds chickpeasBounds = getGUI().getView().lookup("#chickpeas").getBoundsInParent();
    Bounds fertilizerBounds = getGUI().getView().lookup("#fertilizer").getBoundsInParent();
    Bounds appleBounds = getGUI().getView().lookup("#apple").getBoundsInParent();

    if(keyCode == KeyCode.F && playerBounds.intersects(switchSceneBounds)) {
      getGUI().setView("map");
      getGUI().getCharacter().setX((int) getGUI().getView().lookup("#village").getLayoutX());
      getGUI().getCharacter().setY((int) getGUI().getView().lookup("#village").getLayoutY());
    }

    if(keyCode == KeyCode.E) {

      String message = null;

      if(playerBounds.intersects(maizeBounds)) {
        Item item = getGUI().getCharacter().getInventory().get(CropType.MAIZE.toString());
        item.setAmount(item.getAmount() + 5);
        message = "5x Maize";
        getGUI().getView().lookup("#maize").setVisible(false);
      }

      if(playerBounds.intersects(chickpeasBounds)) {
        Item item = getGUI().getCharacter().getInventory().get(CropType.CHICKPEAS.toString());
        item.setAmount(item.getAmount() + 5);
        message = "5x Chickpeas";
        getGUI().getView().lookup("#chickpeas").setVisible(false);
      }

      if(playerBounds.intersects(fertilizerBounds)) {
        Item item = getGUI().getCharacter().getInventory().get(ItemType.FERTILIZER.toString());
        item.setAmount(item.getAmount() + 2);
        message = "2x Fertilizer";
        getGUI().getView().lookup("#fertilizer").setVisible(false);
      }

      if(playerBounds.intersects(appleBounds)) {
        Item item = getGUI().getCharacter().getInventory().get(ItemType.APPLE.toString());
        item.setAmount(item.getAmount() + 1);
        message = "1x apple";
        getGUI().getView().lookup("#apple").setVisible(false);
      }

      if(message != null) {
        message += " was added to your inventory";
        getGUI().getGameOverlay().getMessagesBox().addMessage(message);
        getGUI().getGameOverlay().updateMessages();
        getGUI().getGameOverlay().setShortMessage(message);
        getGUI().getGameOverlay().showShortMessage();
      }
    }
  }
}
