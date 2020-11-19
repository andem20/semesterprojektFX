package src;


public abstract class FXController {
  private final Main main;

  public FXController(Main main) {
    this.main = main;
  }

  public abstract void update();

  public Main getMain() {
    return main;
  }
}
