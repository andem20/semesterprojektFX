package src.presentation.gameoverlay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import src.domain.Audio;

public class StatusBar extends HBox {
  private final Audio audio;
  private final MusicIcon musicIcon;
  // Status
  private final Label popLabel = new Label("Population:");
  private final Label popAmount = new Label();
  private final Label hungerLabel = new Label("Hunger level:");
  private final Label hungerAmount = new Label();
  private final Label daysLabel = new Label("Days: ");
  private final Label daysAmount = new Label();
  // Message counter
  private final Label messageCounter = new Label("0");
  private final ImageView messagesImage = new ImageView();

  public StatusBar() {
    // Load music
    audio = new Audio("soundtrack.wav");
    audio.setVolume(-15);

    setAlignment(Pos.CENTER_RIGHT);
    setPadding(new Insets(0, 10, 0, 10));
    setSpacing(10);
    setBackground(
        new Background(
            new BackgroundFill(
                new Color(0.2, 0.2, 0.2, 0.6),
                new CornerRadii(5), Insets.EMPTY
            )
        )
    );

    musicIcon = new MusicIcon();
    musicIcon.setOnMouseClicked(mouseEvent -> playPause());

    messagesImage.setImage(new Image("/images/mail.png"));
    messagesImage.setFitWidth(20);
    messagesImage.setFitHeight(20);
    messagesImage.setPreserveRatio(true);
    messagesImage.setCursor(Cursor.HAND);

    messageCounter.setTranslateX(-35);
    messageCounter.setTranslateY(5);
    messageCounter.setPadding(new Insets(0, 3, 0, 3));
    messageCounter.setFont(new Font("System Bold", 7));
    messageCounter.setVisible(false);

    // Add elements
    getChildren().addAll(
        messagesImage, messageCounter,
        daysLabel, daysAmount,
        popLabel, popAmount,
        hungerLabel, hungerAmount,
        musicIcon);

    getChildren().forEach(label -> label.setStyle("-fx-text-fill: #FFFFFF;"));

    // Set numbers to bold font
    popAmount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
    hungerAmount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
    daysAmount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
    messageCounter.setStyle("-fx-background-color: #EE0000; -fx-background-radius: 50;-fx-text-fill: #FFFFFF;-fx-font-weight: bold;");

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
}
