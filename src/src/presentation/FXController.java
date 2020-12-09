package src.presentation;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import src.domain.characters.Player;

public abstract class FXController {
  private final SceneManager sceneManager;
  private final Scene scene;
  private final Player playerClass;
  private Bounds playerBounds;

  @FXML ImageView player;
  @FXML Label help;

  public FXController(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    this.scene = sceneManager.getScene();
    this.playerClass = sceneManager.getPlayerClass();
  }

  public abstract void update();

  public abstract void onKeyPressed(KeyCode keyCode);

  public SceneManager getSceneManager() {
    return sceneManager;
  }

  public void showHelpMessage(String msg) {
    help.setTranslateX(getSceneManager().getPlayerClass().getX());
    help.setTranslateY(getSceneManager().getPlayerClass().getY() - help.getHeight());
    help.setText(msg);
    help.setVisible(true);
  }

  public void hideHelpMessage() {
    help.setVisible(false);
  }

  public void setScene(String sceneName) {
    getSceneManager().setScene(sceneName);
  }

  public Scene getScene() {
    return scene;
  }

  public Player getPlayerClass() {
    return playerClass;
  }

  public ImageView getPlayer() {
    return player;
  }

  public Bounds getPlayerBounds() {
    return playerBounds;
  }

  public void updatePlayerBounds() {
    playerBounds = player.getBoundsInParent();
  }
}
