package src.presentation;


import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import src.domain.characters.Player;

public abstract class FXController {
  private final SceneManager sceneManager;
  private final Scene scene;
  private final Player player;

  public FXController(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    this.scene = sceneManager.getScene();
    this.player = sceneManager.getPlayer();
  }

  public abstract void update();

  public abstract void onKeyPressed(KeyCode keyCode);

  public SceneManager getSceneManager() {
    return sceneManager;
  }

  public void helpMessage(String msg, Label label) {
    label.setTranslateX(getSceneManager().getPlayer().getX());
    label.setTranslateY(getSceneManager().getPlayer().getY() - label.getHeight());
    label.setText(msg);
    label.setVisible(true);
  }

  public void setScene(String sceneName) {
    getSceneManager().setScene(sceneName);
  }

  public Scene getScene() {
    return scene;
  }

  public Player getPlayer() {
    return player;
  }
}
