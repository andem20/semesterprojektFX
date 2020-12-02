package src.presentation;


import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.GUI;

public abstract class FXController {
  private final GUI GUI;

  public FXController(GUI GUI) {
    this.GUI = GUI;
  }

  public abstract void update();

  public abstract void onKeyPressed(KeyCode keyCode);

  public GUI getGUI() {
    return GUI;
  }

  public void helpMessage(String msg, Label label) {
    label.setTranslateX(getGUI().getCharacter().getX());
    label.setTranslateY(getGUI().getCharacter().getY() - 30);
    label.toFront();
    label.setText(msg);
    label.setVisible(true);
  }
}
