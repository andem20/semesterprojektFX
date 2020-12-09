package src.presentation.gameoverlay;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MusicIcon extends ImageView {

  private final Image musicOn = new Image(getClass().getResource("/images/music_on.png").toExternalForm());
  private final Image musicOff = new Image(getClass().getResource("/images/music_off.png").toExternalForm());

  public MusicIcon() {
    // Music icon
    setId("music");
    setFitHeight(30);
    setFitWidth(30);
    preserveRatioProperty();
    setImage(musicOn);
    setCursor(Cursor.HAND);
    on();
  }

  public void off() {
    setImage(musicOff);
  }

  public void on() {
    setImage(musicOn);
  }
}
