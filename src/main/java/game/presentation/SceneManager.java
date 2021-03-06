package game.presentation;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import game.domain.characters.Player;
import game.enums.GameSettings;
import game.presentation.gameoverlay.GameOverlay;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

public class SceneManager {

  private final Stage stage;
  private final HashMap<String, FXController> fxControllers;
  private HashMap<String, Scene> fxmls;
  private Player playerClass;
  private FXController controller;
  private Node[][] grid;
  private final int TILESIZE = GameSettings.TILESIZE.toInt();
  private final GameOverlay gameOverlay;
  private AnimationTimer gameLoop;
  private final Input input;
  private final FXControllerFactory fxControllerFactory;

  public SceneManager(Stage stage) {
    this.stage = stage;

    stage.setTitle("Hunger Game");
    stage.setResizable(false);

    createPlayer("Player");

    fxControllerFactory = new FXControllerFactory(this);
    fxControllers = new HashMap<>();

    initFXML();

    gameOverlay = new GameOverlay(this);
    input = new Input(this);

    setScene("map");

    getPlayerClass().setY(120);

    stage.show();
  }

  private void initFXML() {
    // Preloading all fxml files
    fxmls = new HashMap<>();
    // Get fxml files in directory
    File fxmlDir = null;
    try {
      fxmlDir = new File(getClass().getResource("/fxml").toURI());
      for(File fxml : Objects.requireNonNull(fxmlDir.listFiles())) {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxml.getName()));

          String name = fxml.getName().split("\\.")[0];
          FXController fxController = fxControllerFactory.createController(name);
          loader.setController(fxController);

          Scene scene = new Scene(loader.load(), 960, 540);

          fxControllers.put(name, fxController);
          fxmls.put(name, scene);
      }
    } catch (URISyntaxException | IOException e) {
      e.printStackTrace();
    }
  }

  private void createPlayer(String name) {
    playerClass = new Player(name, 100);
  }

  private void setGrid() {
    // Could instead be calculated after how many tiles an imageview takes up
    // Collect all ImageViews in an 2d array
    grid = new Node[(int) getScene().getHeight() / TILESIZE][(int) getScene().getWidth() / TILESIZE];
    getScene().getRoot().getChildrenUnmodifiable().stream()
        .filter(node -> (node.getId() == null || !node.getId().equals("player")) && node instanceof ImageView)
        .forEach(node -> {
          if(!node.isDisabled()) {
            int row = (int) node.getLayoutY() / TILESIZE;
            int col = (int) node.getLayoutX() / TILESIZE;
            grid[row][col] = node;
          }
        });
  }

  public Node[][] getGrid() {
    return grid;
  }

  public boolean[] getKeys() {
    return input.getKeys();
  }

  public void setScene(String sceneName) {
    stage.setScene(fxmls.get(sceneName));
    controller = getFXController(sceneName);
    setGrid();
    input.setKeyInput();
    input.setMouseInput();
    gameOverlay.showOverlay();
  }

  public GameOverlay getGameOverlay() {
    return gameOverlay;
  }

  public Scene getScene() {
    return stage.getScene();
  }

  public Stage getStage() {
    return stage;
  }

  public FXController getFXController(String key) {
    return fxControllers.get(key);
  }

  public FXController getController() {
    return controller;
  }

  public Player getPlayerClass() {
    return playerClass;
  }

  public void setGameLoop(AnimationTimer gameLoop) {
    this.gameLoop = gameLoop;
  }

  public AnimationTimer getGameLoop() {
    return gameLoop;
  }
}
