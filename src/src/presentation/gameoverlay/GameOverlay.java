package src.presentation.gameoverlay;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import src.domain.Item;
import src.enums.GameSettings;
import src.presentation.SceneManager;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameOverlay {
  private final SceneManager sceneManager;
  private final StatusBar statusBar;
  // Character label
  private final Label conversationLabel;
  // Inventory
  private final Inventory inventoryBox;
  // Messages
  private final Messages messages;
  private final Label shortMessage;
  // Story
  private final StackPane storyPane = new StackPane();

  public GameOverlay(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    statusBar = new StatusBar();

    // Conversation label
    conversationLabel = new Label();
    conversationLabel.setId("characterLabel");
    conversationLabel.setPadding(new Insets(5));
    conversationLabel.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10;-fx-text-fill: #000000");
    conversationLabel.setVisible(false);

    // Inventory
    Set<Map.Entry<String, Item>> itemNames = sceneManager.getPlayer().getInventory().entrySet();
    inventoryBox = new Inventory(itemNames);

    // Messages
    messages = new Messages();

    getStatusBar().getMessagesImage().setOnMouseClicked(mouseEvent -> {
      messages.setVisible(!messages.isVisible());
      if(messages.isVisible()) {
        for(Message message : messages.getMessages()) {
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

    AnchorPane.setLeftAnchor(storyPane, 0.0);
    AnchorPane.setRightAnchor(storyPane, 0.0);
    AnchorPane.setBottomAnchor(storyPane, 0.0);
    AnchorPane.setTopAnchor(storyPane, 0.0);
    storyPane.setAlignment(Pos.CENTER);
    Label story = new Label();
    story.setTextFill(Color.WHITE);
    story.setFont(Font.font("System Bold", FontWeight.BOLD, 20));
    Button cont = new Button("Continue");
    cont.setStyle("-fx-background-color: #1b74ce;");
    storyPane.getChildren().addAll(story);
    storyPane.setStyle("-fx-background-color: #8b3322;");
    storyPane.setVisible(false);
  }

  public void showOverlay() {
    inventoryBox.setVisible(false);
    AnchorPane anchorPane = (AnchorPane) sceneManager.getScene().getRoot();
    AnchorPane.setRightAnchor(statusBar, 0.0);
    anchorPane.getChildren().addAll(
        statusBar,
        conversationLabel,
        messages,
        shortMessage,
        inventoryBox,
        storyPane
    );
  }

  public void setConversationLabel(String message) {
    conversationLabel.setTranslateX(sceneManager.getPlayer().getX());
    conversationLabel.setTranslateY(sceneManager.getPlayer().getY() - conversationLabel.getHeight());
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
    inventoryBox.setTranslateX(sceneManager.getPlayer().getX() + 20);
    inventoryBox.setTranslateY(sceneManager.getPlayer().getY() + 20);
    inventoryBox.setVisible(!inventoryBox.isVisible());
  }

  public void updateMessages(String msg) {
    messages.getChildren().clear();

    if(msg != null) {
      messages.addMessage(msg);
      setShortMessage(msg);
      showShortMessage();
    }

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

  public void updateMessages() {
    updateMessages(null);
  }

  public Messages getMessagesBox() {
    return messages;
  }

  public void setShortMessage(String content) {
    shortMessage.setText(content);
  }

  public void showShortMessage() {
    shortMessage.setVisible(true);

    AnimationTimer timer = new AnimationTimer() {
      private final Long startTime = System.nanoTime();
      @Override
      public void handle(long l) {
        if((l - startTime) / 1e9 >= 4) {
          shortMessage.setVisible(false);
          this.stop();
        }
      }
    };

    timer.start();
  }

  public StackPane getStoryPane() {
    return storyPane;
  }

  public void showStoryPane() {
    storyPane.setVisible(true);
    storyPane.setOpacity(1.0);
    sceneManager.getGameLoop().stop();
  }

  public void hideStoryPane() {
    FadeTransition ft = new FadeTransition(Duration.millis(500), storyPane);
    ft.setFromValue(1.0);
    ft.setToValue(0.0);
    ft.play();
    ft.onFinishedProperty().setValue(event -> storyPane.setVisible(false));
    sceneManager.getGameLoop().start();
  }
}
