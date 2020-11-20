package src;


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
}
