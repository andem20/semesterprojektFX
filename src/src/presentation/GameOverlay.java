package src.presentation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import src.Main;
import src.domain.Audio;

public class GameOverlay {
  private AnchorPane container;
  private HBox hbox = new HBox();
  private final Audio audio;
  private final Main main;
  // Imageicon
  private final ImageView musicIcon = new ImageView();
  private final Image musicOn = new Image("/images/music_on.png");
  private final Image musicOff = new Image("/images/music_off.png");
  // Status
  private final Label popLabel = new Label("Population:");
  private final Label popAmount = new Label();
  private final Label hungerLabel = new Label("Hunger level:");
  private final Label hungerAmount = new Label();
  private final Label daysLabel = new Label("Days: ");
  private final Label daysAmount = new Label();

  public GameOverlay(Main main) {
    this.main = main;

    // Load music
    audio = new Audio("soundtrack.wav");
    audio.setVolume(-15);

    hbox.setAlignment(Pos.CENTER_RIGHT);
    hbox.setPadding(new Insets(0, 10, 0, 10));
    hbox.setSpacing(10);
    hbox.setBackground(new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 0.6), new CornerRadii(5), Insets.EMPTY)));

    // Music icon
    musicIcon.setId("music");
    musicIcon.setFitHeight(30);
    musicIcon.setFitWidth(30);
    musicIcon.preserveRatioProperty();
    musicIcon.setOnMouseClicked(mouseEvent -> playPause());
    musicIcon.setImage(musicOn);

    // Add elements
    hbox.getChildren().addAll(
        daysLabel, daysAmount,
        popLabel, popAmount,
        hungerLabel, hungerAmount,
        musicIcon);

    hbox.getChildren().forEach(label -> label.setStyle("-fx-text-fill: #FFFFFF;"));

    // Set numbers to bold font
    popAmount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
    hungerAmount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
    daysAmount.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
  }

  public void setContainer(Node container) {
    this.container = (AnchorPane) container;
    AnchorPane.setRightAnchor(hbox, 0.0);
    this.container.getChildren().add(hbox);
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
      ((ImageView) container.lookup("#music")).setImage(musicOff);
    } else {
      audio.play();
      ((ImageView) container.lookup("#music")).setImage(musicOn);
    }
  }
}
