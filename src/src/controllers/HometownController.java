package src.controllers;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import src.FXController;
import src.Main;

public class HometownController extends FXController {

  public HometownController(Main main) {
    super(main);
  }

  @Override
  public void update() {

  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    System.out.println(keyCode);
  }
}
