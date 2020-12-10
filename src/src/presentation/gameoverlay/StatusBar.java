package src.presentation.gameoverlay;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import src.presentation.Audio;

public class StatusBar extends HBox {
  private final Audio audio;
  private final MusicIcon musicIcon = new MusicIcon();
  private final Label popAmount = new Label();
  private final Label hungerAmount = new Label();
  private final Label daysAmount = new Label();
  // Message counter
  private final Label messageCounter = new Label("0");
  private final ImageView messagesImage = new ImageView();
  private final Label helpLabel = new Label("?");

  public StatusBar() {
    // Load music
    audio = new Audio("soundtrack.wav");
    audio.setVolume(-15);

    getStyleClass().add("status-bar");

    musicIcon.setOnMouseClicked(mouseEvent -> playPause());

    setMessageIcon();

    // Status
    Label popLabel = new Label("Population:");
    Label hungerLabel = new Label("Hunger level:");
    Label daysLabel = new Label("Days: ");

    // Helplabel
    helpLabel.getStyleClass().setAll("help-label");

    getChildren().addAll(
        messagesImage, messageCounter,
        daysLabel, daysAmount,
        popLabel, popAmount,
        hungerLabel, hungerAmount,
        musicIcon, helpLabel);

    // Set numbers to bold font
    popAmount.getStyleClass().add("status-bar-number");
    hungerAmount.getStyleClass().add("status-bar-number");
    daysAmount.getStyleClass().add("status-bar-number");
    messageCounter.getStyleClass().add("message-counter");
  }

  public void setStatusText(int population, float hungerLevel, int days) {
    // Update population amount
    popAmount.setText(population + " ");
    // Update hunger level amount
    hungerAmount.setText(Math.round(hungerLevel * 100) + "%");
    // Update days amount
    daysAmount.setText(days + " ");
  }

  public void playPause() {
    if(audio.isPlaying()) {
      audio.pause();
      musicIcon.off();
    } else {
      audio.play();
      musicIcon.on();
    }
  }

  private void setMessageIcon() {
    messagesImage.setImage(new Image(getClass().getResource("/images/mail.png").toExternalForm()));
    messagesImage.getStyleClass().add("message-icon");
    messagesImage.setFitWidth(20);
    messagesImage.setFitHeight(20);
    messagesImage.setPreserveRatio(true);

    messageCounter.setTranslateX(-35);
    messageCounter.setTranslateY(5);
    messageCounter.setVisible(false);
  }

  public void setMessageCounter(int n) {
    if(n < 1) {
      messageCounter.setVisible(false);
      return;
    }

    messageCounter.setText(Integer.toString(n));
    messageCounter.setVisible(true);
  }

  public ImageView getMessagesImage() {
    return messagesImage;
  }

  public Label getHelpLabel() {
    return helpLabel;
  }
}
