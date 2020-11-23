package src;


import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

public abstract class FXController {
  private final Main main;

  public FXController(Main main) {
    this.main = main;
  }

  public abstract void update();

  public abstract void onKeyPressed(KeyCode keyCode);

  public Main getMain() {
    return main;
  }

  public void helpMessage(String msg, Label label) {
    label.setTranslateX(getMain().getCharacter().getX());
    label.setTranslateY(getMain().getCharacter().getY() - 30);
    label.toFront();
    label.setText(msg);
    label.setVisible(true);
  }
}
