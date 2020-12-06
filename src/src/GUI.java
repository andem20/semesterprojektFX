/**
 * Spillet er opbygget af forskellige rum i form af .fxml-filer.
 * Hvert rum er tildelt sin egen controller som styrer deres individuelle metoder.
 * Et rum skal associeres med sin controller, så når spiller skifter rum, bruges den korrekte controller.
 * Spilleren skal kunne interagere med genstand ved at stille sig op ad den og trykke på en knap.
 * Alle baner er opdelt i 60x60px felter. Hele vinduet er 960x540 = 16x9 felter
 */

package src;

import javafx.application.Application;
import javafx.stage.Stage;
import src.presentation.GameManager;
import src.presentation.SceneManager;

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