package src.presentation.gameoverlay;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
  private final Label conversationLabel = new Label();
  private final Inventory inventoryBox;
  private final Messages messages = new Messages();
  private final Label shortMessage = new Label();
  private final StackPane storyPane = new StackPane();
  private final Label storyLabel = new Label();
  private final Button storyButton = new Button();
  private final ImageView keyboardImage = new ImageView(new Image(getClass().getResource("/images/keyboard.png").toExternalForm()));
  private final VBox storyContainer = new VBox();

  public GameOverlay(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    statusBar = new StatusBar();

    // Conversation label
    conversationLabel.setId("characterLabel");
    conversationLabel.getStyleClass().setAll("conversation-label");
    conversationLabel.setVisible(false);

    // Inventory
    Set<Map.Entry<String, Item>> itemNames = sceneManager.getPlayerClass().getInventory().entrySet();
    inventoryBox = new Inventory(itemNames);

    // Messages
    getStatusBar().getMessagesImage().setOnMouseClicked(mouseEvent -> {
      messages.setVisible(!messages.isVisible());
      if(messages.isVisible()) {
        messages.getMessages().forEach(message -> message.setSeen(true));
      } else {
        updateMessages();
      }
    });

    // Helplabel
    getStatusBar().getHelpIcon().setOnMouseClicked(mouseEvent -> showControlsPane());

    shortMessage.getStyleClass().setAll("short-message");

    shortMessage.setLayoutX(getMessagesBox().getLayoutX());
    shortMessage.setLayoutY(getMessagesBox().getLayoutY());
    shortMessage.setVisible(false);

    initStoryline();
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
    conversationLabel.setTranslateX(sceneManager.getPlayerClass().getX());
    conversationLabel.setTranslateY(sceneManager.getPlayerClass().getY() - conversationLabel.getHeight());
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
    inventoryBox.setTranslateX(sceneManager.getPlayerClass().getX() + 20);
    inventoryBox.setTranslateY(sceneManager.getPlayerClass().getY() + 20);
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
    // Show short message for 4 seconds
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

  private void initStoryline() {
    AnchorPane.setLeftAnchor(storyPane, 0.0);
    AnchorPane.setRightAnchor(storyPane, 0.0);
    AnchorPane.setBottomAnchor(storyPane, 0.0);
    AnchorPane.setTopAnchor(storyPane, 0.0);
    storyPane.setAlignment(Pos.CENTER);

    storyContainer.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    storyContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    storyContainer.setSpacing(20);

    storyLabel.getStyleClass().add("story-label");

    storyButton.setText("Continue");
    storyButton.getStyleClass().add("story-button");

    storyButton.setOnMouseClicked(mouseEvent -> hideStoryPane());

    keyboardImage.setFitWidth(500);
    keyboardImage.setPreserveRatio(true);
    storyContainer.getChildren().addAll(storyLabel, storyButton);

    storyPane.getChildren().addAll(storyContainer);
    storyPane.setVisible(false);
  }

  public StackPane getStoryPane() {
    return storyPane;
  }

  public Label getStoryLabel() {
    return storyLabel;
  }

  public Button getStoryButton() {
    return storyButton;
  }

  public void showStoryPane() {
    storyContainer.getChildren().remove(keyboardImage);
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

  private void showControlsPane() {
    getStoryPane().setStyle("-fx-background-color: #4681c9");

    getStoryLabel().setText(
        "Move: W, A, S, D / Arrow-keys\n" +
        "Show inventory: I\n" +
        "Play/pause music: P\n" +
        "Interact: F, E, R, T");
    showStoryPane();
    storyContainer.getChildren().add(0, keyboardImage);
  }
}
