package src.presentation.gameoverlay;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import src.GUI;
import src.domain.Item;

import java.util.Map;
import java.util.Set;

public class GameOverlay {
  private GUI gui;
  private final StatusBar statusBar;
  // Character label
  private final Label conversationLabel;
  // Inventory
  private final VBox inventoryBox;
  // Messages
  private Messages messages;

  public GameOverlay(GUI gui) {

    statusBar = new StatusBar();

    // Conversation label
    conversationLabel = new Label();
    conversationLabel.setId("characterLabel");
    conversationLabel.setPadding(new Insets(5));
    conversationLabel.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10;-fx-text-fill: #000000");
    conversationLabel.setVisible(false);

    // Inventory
    Set<Map.Entry<String, Item>> itemNames = gui.getCharacter().getInventory().entrySet();
    inventoryBox = new Inventory(itemNames);

    // Messages
    messages = new Messages();

    getStatusBar().getMessagesImage().setOnMouseClicked(mouseEvent -> {
      getMessagesBox().setVisible(!getMessagesBox().isVisible());
      if(getMessagesBox().isVisible()) {
        for(Message message : getMessagesBox().getMessages()) {
          message.setSeen(true);
        }
      } else {
        updateMessages();
      }
    });
  }

  public void setContainer(Node container) {
    AnchorPane anchorPane = (AnchorPane) container;
    AnchorPane.setRightAnchor(statusBar, 0.0);
    anchorPane.getChildren().addAll(
        statusBar,
        conversationLabel,
        messages
    );
  }

  public void setConversationLabel(String message) {
    conversationLabel.setVisible(true);
    conversationLabel.setText(message);
  }

  public Label getConversationLabel() {
    return conversationLabel;
  }

  public StatusBar getStatusBar() {
    return statusBar;
  }

  public VBox getInventoryBox() {
    return inventoryBox;
  }

  public void updateMessages() {
    messages.getChildren().clear();
    statusBar.setMessageCounter((int) messages.getMessages().stream().filter(message -> !message.isSeen()).count());
    for(Message message : messages.getMessages()) {
      if(!message.isSeen()) {
        Label messageLabel = new Label(message.getContent());
        messages.getChildren().add(messageLabel);
      }
    }
  }

  public Messages getMessagesBox() {
    return messages;
  }
}
