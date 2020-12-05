package src.presentation.controllers;

import javafx.scene.control.Label;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import src.domain.Item;
import src.domain.rooms.Farm;
import src.enums.CropType;
import src.enums.ItemType;
import src.presentation.FXController;
import src.GUI;

public class FarmController extends FXController {

  private final Farm farm;
  private int messageIndex = 0;

  public FarmController(GUI GUI) {
    super(GUI);

    farm = new Farm("farm");
  }

  @Override
  public void update() {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds spouseBounds = getGUI().getView().lookup("#spouse").getBoundsInParent();
    Bounds fertilizerBounds = getGUI().getView().lookup("#fertilizer").getBoundsInParent();

    Label help = (Label) getGUI().getView().lookup("#help");

    if (playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else if (playerBounds.intersects(spouseBounds)) {
      helpMessage("Press 'T' to talk with spouse.", help);
    } else if (playerBounds.intersects(fertilizerBounds)) {
      helpMessage("Press 'E' to collect item.", help);
    } else {
      getGUI().getGameOverlay().getConversationLabel().setVisible(false);
      getGUI().getView().lookup("#spouseLabel").setVisible(false);
      help.setVisible(false);
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds switchSceneBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds spouseBounds = getGUI().getView().lookup("#spouse").getBoundsInParent();
    Bounds fertilizerBounds = getGUI().getView().lookup("#fertilizer").getBoundsInParent();

    if (keyCode == KeyCode.F) {
      if (playerBounds.intersects(switchSceneBounds)) {
        getGUI().setView("map");
        getGUI().getCharacter().setX((int) getGUI().getView().lookup("#farm").getLayoutX());
        getGUI().getCharacter().setY((int) getGUI().getView().lookup("#farm").getLayoutY());
        return;
      }
    }

    if (keyCode == KeyCode.T) {
      if (playerBounds.intersects(spouseBounds)) {

        getGUI().getGameOverlay().setConversationLabel(getGUI().getCharacter().getSpouseMessages().get(messageIndex));

        ((Label) getGUI().getView().lookup("#spouseLabel")).setText(farm.getSpouse().getResponse(messageIndex));
        getGUI().getView().lookup("#spouseLabel").setVisible(true);

        messageIndex = Math.min(messageIndex + 1, getGUI().getCharacter().getSpouseMessages().size() - 1);
      } else {
        getGUI().getView().lookup("#spouseLabel").setVisible(false);
      }
    }

    if (keyCode == KeyCode.E) {

      String message = null;

      if (playerBounds.intersects(fertilizerBounds)) {
        Item item = getGUI().getCharacter().getInventory().get(ItemType.FERTILIZER.toString());
        item.setAmount(item.getAmount() + 2);
        message = "2x Fertilizer";
        getGUI().getView().lookup("#fertilizer").setVisible(false);

      }

      if (message != null) {
        message += " was added to your inventory";
        getGUI().getGameOverlay().getMessagesBox().addMessage(message);
        getGUI().getGameOverlay().updateMessages();
        getGUI().getGameOverlay().setShortMessage(message);
        getGUI().getGameOverlay().showShortMessage();
      }

    }
  }
}
