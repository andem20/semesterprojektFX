package src.presentation.gameoverlay;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import src.GUI;
import src.domain.Item;
import src.enums.GameSettings;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameOverlay {
  private GUI gui;
  private final StatusBar statusBar;
  // Character label
  private final Label conversationLabel;
  // Inventory
  private final Inventory inventoryBox;
  // Messages
  private final Messages messages;
  private final Label shortMessage;

  public GameOverlay(GUI gui) {
    this.gui = gui;
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

    shortMessage = new Label();
    shortMessage.setBackground(
        new Background(
            new BackgroundFill(
                new Color(1, 1, 1, 0.6),
                new CornerRadii(5), Insets.EMPTY
            )
        )
    );

    shortMessage.setPadding(new Insets(5));

    shortMessage.setLayoutX(570);
    shortMessage.setLayoutY(30);
    shortMessage.setVisible(false);
  }

  public void setContainer(Node container) {
    inventoryBox.setVisible(false);
    AnchorPane anchorPane = (AnchorPane) container;
    AnchorPane.setRightAnchor(statusBar, 0.0);
    anchorPane.getChildren().addAll(
        statusBar,
        conversationLabel,
        messages,
        shortMessage,
        inventoryBox
    );
  }

  public void setConversationLabel(String message) {
    conversationLabel.setTranslateX(gui.getCharacter().getX());
    conversationLabel.setTranslateY(gui.getCharacter().getY() - conversationLabel.getHeight());
    conversationLabel.setVisible(true);
    conversationLabel.setText(message);
  }

  public Label getConversationLabel() {
    return conversationLabel;
  }

  public StatusBar getStatusBar() {
    return statusBar;
  }

  public Inventory getInventoryBox() {
    return inventoryBox;
  }

  public void toggleInventoryBox() {
    getInventoryBox().update();
    inventoryBox.setTranslateX(gui.getCharacter().getX() + 20);
    inventoryBox.setTranslateY(gui.getCharacter().getY() + 20);
    inventoryBox.setVisible(!inventoryBox.isVisible());
  }

  public void updateMessages() {
    messages.getChildren().clear();
    List<Message> filteredMessages = messages.getMessages().stream()
                                        .filter(message -> !message.isSeen())
                                        .limit(GameSettings.MAX_MESSAGES.toInt())
                                        .collect(Collectors.toList());

    statusBar.setMessageCounter(filteredMessages.size());
    filteredMessages.forEach(message -> {
        Label messageLabel = new Label(message.getContent());
        messages.getChildren().add(messageLabel);
    });
  }

  public Messages getMessagesBox() {
    return messages;
  }

  public void setShortMessage(String content) {
    shortMessage.setText(content);
  }

  public void showShortMessage() {

    AnimationTimer timer = new AnimationTimer() {
      private final Long startTime = System.nanoTime();
      @Override
      public void handle(long l) {
        shortMessage.setVisible(true);
        if((l - startTime) / 1e9 >= 4) {
          shortMessage.setVisible(false);
          this.stop();
        }
      }
    };

    timer.start();
  }
}
