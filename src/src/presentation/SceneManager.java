package src.presentation;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import src.domain.characters.Player;
import src.enums.CropType;
import src.enums.GameSettings;
import src.enums.ItemType;
import src.presentation.controllers.*;
import src.presentation.gameoverlay.GameOverlay;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {

  private final Stage stage;
  private HashMap<String, FXController> fxControllers;
  private HashMap<String, Scene> fxmls;
  private Player player;
  private FXController controller;
  private Node[][] grid;
  private int TILESIZE = GameSettings.TILESIZE.toInt();
  private final boolean[] keys = new boolean[4];
  private ImageView playerModel;
  private GameOverlay gameOverlay;
  private AnimationTimer gameLoop;

  public SceneManager(Stage stage) {
    this.stage = stage;

    stage.setTitle("Hunger Game");
    stage.setResizable(false);

    createPlayer("Anders");

    initControllers();
    initFXML();

    gameOverlay = new GameOverlay(this);

    setScene("map");

    stage.show();
  }

  private void initControllers() {
    // Create a hashmap of all fxml and corresponding controllers because controllers needs have reference to GUI
    fxControllers = new HashMap<>();
    fxControllers.put("map", new MapController(this));
    fxControllers.put("village", new VillageController( this));
    fxControllers.put("field", new FieldController( this));
    fxControllers.put("farm", new FarmController( this));
    fxControllers.put("school", new SchoolController(this));
    fxControllers.put("market", new MarketController(this));
  }

  private void initFXML() {
    fxmls = new HashMap<>();
    fxControllers.forEach((name, controller) -> {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name +".fxml"));

        loader.setController(controller);

        Scene scene = new Scene(loader.load(), 960, 540);

        fxmls.put(name, scene);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  private void createPlayer(String name) {
    player = new Player(name, 100);

    // Create player's inventory
    player.addItemAmount(CropType.MAIZE.toString(), 5);
    player.addItemAmount(CropType.WHEAT.toString(), 10);
    player.addItemAmount(ItemType.FERTILIZER.toString(), 1);
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

  public boolean[] getKeyInput() {
    return keys;
  }

  public void setKeyInput() {
    getScene().setOnKeyPressed(keyEvent -> {

      if(keyEvent.getCode() != KeyCode.I) getGameOverlay().getInventoryBox().setVisible(false);
      if(getGameOverlay().getStoryPane().isVisible() && keyEvent.getCode() == KeyCode.SPACE) getGameOverlay().hideStoryPane();

      switch(keyEvent.getCode()) {
        case D -> keys[0] = true;
        case A -> keys[1] = true;
        case S -> keys[2] = true;
        case W -> keys[3] = true;
        case P -> getGameOverlay().getStatusBar().playPause();
        case I -> getGameOverlay().toggleInventoryBox();
      }

      getController().onKeyPressed(keyEvent.getCode());
    });



    getScene().setOnKeyReleased(keyEvent -> {
      switch(keyEvent.getCode()) {
        case D -> keys[0] = false;
        case A -> keys[1] = false;
        case S -> keys[2] = false;
        case W -> keys[3] = false;
      }
    });

    // Hide messages
    getScene().setOnMouseClicked(mouseEvent -> {
      if(!getGameOverlay().getStatusBar().getMessagesImage().isHover()) {
        getGameOverlay().getMessagesBox().setVisible(false);
        getGameOverlay().updateMessages();
      }
    });
  }

  public void setScene(String sceneName) {
    stage.setScene(fxmls.get(sceneName));
    controller = getFXController(sceneName);
    playerModel = (ImageView) getScene().lookup("#player");
    setGrid();
    setKeyInput();
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

  public Player getPlayer() {
    return player;
  }

  public void setGameLoop(AnimationTimer gameLoop) {
    this.gameLoop = gameLoop;
  }

  public AnimationTimer getGameLoop() {
    return gameLoop;
  }
}
