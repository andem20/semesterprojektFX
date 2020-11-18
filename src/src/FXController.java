package src;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public abstract class FXController {
  private final Character character;

  public FXController(Character character) {
    this.character = character;
  }

  public abstract void update();
  public abstract Parent getParent();

  public Character getCharacter() {
    return character;
  }
}
