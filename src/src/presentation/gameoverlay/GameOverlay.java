package src.presentation.gameoverlay;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
  private long startTime;

  public GameOverlay(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    statusBar = new StatusBar();

    // Conversation label
    conversationLabel.setId("characterLabel");
    conversationLabel.setPadding(new Insets(5));
    conversationLabel.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10;-fx-text-fill: #000000");
    conversationLabel.setVisible(false);

    // Inventory
    Set<Map.Entry<String, Item>> itemNames = sceneManager.getPlayer().getInventory().entrySet();
    inventoryBox = new Inventory(itemNames);

    // Messages
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

  public void initStoryline() {
    AnchorPane.setLeftAnchor(storyPane, 0.0);
    AnchorPane.setRightAnchor(storyPane, 0.0);
    AnchorPane.setBottomAnchor(storyPane, 0.0);
    AnchorPane.setTopAnchor(storyPane, 0.0);
    storyPane.setAlignment(Pos.CENTER);

    VBox storyContainer = new VBox();
    storyContainer.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    storyContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    storyContainer.setSpacing(20);

    storyLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-size: 20;");
    storyLabel.setWrapText(true);
    storyLabel.setMaxWidth(740);
    storyLabel.setLineSpacing(2);

    storyButton.setText("Continue");
    storyButton.setPadding(new Insets(10, 20, 10, 20));
    storyButton.setStyle("-fx-background-color: #EEEEEE;-fx-font-weight: bold;");
    storyButton.setOnMouseClicked(mouseEvent -> hideStoryPane());
    storyButton.setOnMouseEntered(mouseEvent -> {
      storyButton.setStyle("-fx-background-color: #DDDDDD;-fx-font-weight: bold;");
    });

    storyButton.setOnMouseExited(mouseEvent -> {
      storyButton.setStyle("-fx-background-color: #EEEEEE;-fx-font-weight: bold;");
    });

    storyButton.setCursor(Cursor.HAND);

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
