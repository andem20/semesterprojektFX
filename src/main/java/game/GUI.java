/**
 * The graphics in each "room" is laoded from an individual .fxml-file
 * Each room is assigned a controller to control their different functionality
 * The player can interact though the controller
 * Each room is divided into 60x60 px cell, to manage collision control easier
 */

package game;

import javafx.application.Application;
import javafx.stage.Stage;
import game.presentation.GameManager;
import game.presentation.SceneManager;

public class GUI extends Application {
  @Override
  public void start(Stage stage) {
    SceneManager sceneManager = new SceneManager(stage);
    GameManager gameManager = new GameManager(sceneManager);
    gameManager.play();
  }

  public static void main(String[] args) {
    launch(args);
  }
}