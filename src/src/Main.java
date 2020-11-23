/**
 * Spillet er opbygget af forskellige rum i form af .fxml-filer.
 * Hvert rum er tildelt sin egen controller som styrer deres individuelle metoder.
 * Et rum skal associeres med sin controller, så når spiller skifter rum, bruges den korrekte controller.
 * Spilleren skal kunne interagere med genstand ved at stille sig op ad den og trykke på en knap.
 */

package src;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import src.controllers.FieldController;
import src.controllers.VillageController;
import src.controllers.MapController;
import src.enums.CropType;
import src.enums.ItemType;

import java.io.IOException;
import java.util.HashMap;

public class Main extends Application {
  private FXController current;
  private Character character;
  private HashMap<String, FXController> fxControllers;
  private Stage window;
  private final int VELOCITY = 3;
  // Key-array for checking if pressed (avoiding delay)
  private final boolean[] keys = new boolean[4];
  private final int TILESIZE = 60;
  private Node[][] grid;

  @Override
  public void start(Stage stage) {
    window = stage;

    createCharacter("Anders");

    stage.setTitle("Hunger Game");
    window.setResizable(false);

    // Create a hashmap of all fxml and corresponding controllers
    fxControllers = new HashMap<>();
    fxControllers.put("map", new MapController(this));
    fxControllers.put("village", new VillageController( this));
    fxControllers.put("field", new FieldController( this));

    setView("map");

    window.show();

    // Positioning player on the path
    getCharacter().setY(120);

    // Gameloop
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long l) {
        // Find player and bring it to the front
        Node player = getView().lookup("#player");
        player.toFront();

        // Call current controller update method
        getCurrent().update();

        checkCollision(player);
      }
    };

    // Start gameloop
    timer.start();
  }

  private void createCharacter(String name) {
    character = new Character(name, 100);

    // Create player's inventory
    character.addItem(CropType.BEANS.toString(), new src.Crop(0, CropType.BEANS));
    character.addItem(CropType.MAIZE.toString(), new src.Crop(5, CropType.MAIZE));
    character.addItem(CropType.WHEAT.toString(), new src.Crop(10, CropType.WHEAT));
    character.addItem(CropType.CHICKPEAS.toString(), new src.Crop(0, CropType.CHICKPEAS));
    character.addItem(CropType.RICE.toString(), new src.Crop(0, CropType.RICE));
    character.addItem(CropType.SORGHUM.toString(), new src.Crop(0, CropType.SORGHUM));
    character.addItem(ItemType.FERTILIZER.toString(), new src.Item(ItemType.FERTILIZER.toString(), 1, 50));
  }

  public FXController getCurrent() {
    return current;
  }

  public void setCurrent(FXController current) {
    this.current = current;
  }

  public Character getCharacter() {
    return character;
  }

  public FXController getFxControllers(String key) {
    return fxControllers.get(key);
  }

  public void setView(String name) {
    // TODO skal optimers så hver fil ikke bliver loaded igen og igen
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name +".fxml"));

      Scene scene = new Scene(loader.load(), 960, 540);

      setCurrent(getFxControllers(name));

      loader.setController(getCurrent());

      getWindow().setScene(scene);

      setKeyInput();
      setGrid();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Scene getView() {
    return window.getScene();
  }

  public Stage getWindow() {
    return window;
  }

  public void setKeyInput() {
    getView().setOnKeyPressed(keyEvent -> {
      switch(keyEvent.getCode()) {
        case D -> keys[0] = true;
        case A -> keys[1] = true;
        case S -> keys[2] = true;
        case W -> keys[3] = true;
      }

      getCurrent().onKeyPressed(keyEvent.getCode());
    });

    getView().setOnKeyReleased(keyEvent -> {
      switch(keyEvent.getCode()) {
        case D -> keys[0] = false;
        case A -> keys[1] = false;
        case S -> keys[2] = false;
        case W -> keys[3] = false;
      }
    });
  }

  public void setGrid() {
    // Collect all nodes in an 2d array
    grid = new Node[(int) getView().getHeight() / TILESIZE][(int) getView().getWidth() / TILESIZE];
    getView().getRoot().getChildrenUnmodifiable().forEach(node -> {
      if((node.getId() == null || !node.getId().equals("player")) && node instanceof ImageView) {
        int row = (int) node.getLayoutY() / TILESIZE;
        int col = (int) node.getLayoutX() / TILESIZE;
        grid[row][col] = node;
      }
    });
  }

  public void checkCollision(Node player) {
    // Reference for the player coordinates
    int x = getCharacter().getX();
    int y = getCharacter().getY();

    int playerWidth = (int) player.getBoundsInLocal().getWidth();
    int playerHeight = (int) player.getBoundsInLocal().getHeight();

    int viewWidth = (int) getView().getWidth();
    int viewHeight = (int) getView().getHeight();

    // Players current tile
    int colLeft = Math.max((x - x % TILESIZE) / TILESIZE, 0);
    int rowTop = Math.max((y - y % TILESIZE) / TILESIZE, 0);
    int colRight = Math.min((x + playerWidth % TILESIZE) / TILESIZE, grid[0].length - 1);
    int rowBottom = Math.min((y + playerHeight % TILESIZE) / TILESIZE, grid.length-1);
    // Neighbour tiles
    int right = Math.min(colLeft+1, grid[0].length-1);
    int left = Math.max(colLeft-1, 0);
    int top = Math.max(rowTop-1, 0);
    int bottom = Math.min(rowTop+1, grid.length-1);

    // Right
    if(keys[0]) {
      for(int i = 0; i < VELOCITY; i++) {
        if((grid[rowTop][right] != null && grid[rowBottom][right] != null) || (x + playerWidth) % TILESIZE < TILESIZE - 1) {
          if(x + playerWidth < viewWidth) {
            x++;
            player.setRotate(90);
          }
        }
      }
    }

    // Left
    if(keys[1]) {
      for(int i = 0; i < VELOCITY; i++) {
        if((grid[rowTop][left] != null && grid[rowBottom][left] != null) || x % TILESIZE > 0) {
          if(x > 0) {
            x--;
            player.setRotate(90);
          }
        }
      }
    }

    // Down
    if(keys[2]) {
      for(int i = 0; i < VELOCITY; i++) {
        if((grid[bottom][colLeft] != null && grid[bottom][colRight] != null) || (y + playerHeight) % TILESIZE < TILESIZE - 1) {
          if(y + playerHeight < viewHeight) {
            y++;
            player.setRotate(0);
          }
        }
      }
    }

    // Up
    if(keys[3]) {
      for(int i = 0; i < VELOCITY; i++) {
        if((grid[top][colLeft] != null && grid[top][colRight] != null) || y % TILESIZE > 0) {
          if(y > 0) {
            y--;
            player.setRotate(0);
          }
        }
      }
    }

    // Update character position
    getCharacter().setX(x);
    getCharacter().setY(y);
    // Update rendered player's position
    player.setTranslateX(x);
    player.setTranslateY(y);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
