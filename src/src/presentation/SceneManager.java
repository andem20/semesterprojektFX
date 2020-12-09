package src.presentation;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import src.domain.characters.Player;
import src.enums.CropType;
import src.enums.GameSettings;
import src.enums.ItemType;
import src.presentation.controllers.*;
import src.presentation.gameoverlay.GameOverlay;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SceneManager {

  private final Stage stage;
  private HashMap<String, FXController> fxControllers;
  private HashMap<String, Scene> fxmls;
  private Player playerClass;
  private FXController controller;
  private Node[][] grid;
  private final int TILESIZE = GameSettings.TILESIZE.toInt();
  private ImageView playerModel;
  private final GameOverlay gameOverlay;
  private AnimationTimer gameLoop;
  private final Input input;
  private final ControllerFactory controllerFactory;

  public SceneManager(Stage stage) {
    this.stage = stage;

    stage.setTitle("Hunger Game");
    stage.setResizable(false);

    createPlayer("Player");

    controllerFactory = new ControllerFactory(this);

    fxControllers = new HashMap<>();

    initFXML();
//    initControllers();

    gameOverlay = new GameOverlay(this);
    input = new Input(this);

    setScene("map");

    getPlayerClass().setY(120);

    stage.show();
  }

  public void initFXML() {
    // Preloading all fxml files
    fxmls = new HashMap<>();
    // Get fxml files in directory
    File f = new File(getClass().getResource("/fxml").getPath());
    for(File fxml : f.listFiles()) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxml.getName()));

        String name = fxml.getName().split("\\.")[0];

        FXController fxController = controllerFactory.createController(name);

        loader.setController(fxController);

        Scene scene = new Scene(loader.load(), 960, 540);

        fxControllers.put(name, fxController);
        fxmls.put(name, scene);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void createPlayer(String name) {
    playerClass = new Player(name, 100);

    // Create player's inventory
    playerClass.addItemAmount(CropType.MAIZE.toString(), 5);
    playerClass.addItemAmount(CropType.WHEAT.toString(), 10);
    playerClass.addItemAmount(ItemType.FERTILIZER.toString(), 1);
  }

  public void setGrid() {
    // Could be calculated after how many tiles an imageview takes up
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
    playerModel = (ImageView) getScene().lookup("#player");
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

  public ImageView getPlayerModel() {
    return playerModel;
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
