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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.controllers.FieldController;
import src.controllers.VillageController;
import src.controllers.MapController;
import src.enums.CropType;
import src.enums.ItemType;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
  private FXController current;
  private Character character;
  private HashMap<String, FXController> fxControllers;
  private Stage window;
  private final int VELOCITY = 3;
  private final boolean[] keys = new boolean[4];

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

    character.setY(120);

    // Gameloop
    AnimationTimer timer = new AnimationTimer() {
      private Node processName = null;
      private boolean intersect = false;

      @Override
      public void handle(long l) {
        Node player = getView().lookup("#player");
        player.toFront();

        int x = character.getX();
        int y = character.getY();


        getCurrent().update();

        List<Node> nodes = getView().getRoot().getChildrenUnmodifiable().stream()
            .filter(node -> !node.equals(player) && player.getBoundsInParent().intersects(node.getBoundsInParent())).collect(Collectors.toList());

        // TODO fix collision. Player can get stuck
        for(Node node : nodes) {
          if(keys[0] && player.getTranslateX() + player.getBoundsInLocal().getWidth() < (node.getLayoutX() + node.getBoundsInLocal().getWidth())) {
            character.setX(x + VELOCITY);
            player.setRotate(90);
          }

          if(keys[1] && player.getTranslateX() > node.getLayoutX()) {
            character.setX(x - VELOCITY);
            player.setRotate(90);
          }

          if(keys[2] && player.getTranslateY() + player.getBoundsInLocal().getHeight() < (node.getLayoutY() + node.getBoundsInLocal().getHeight())) {
            character.setY(y + VELOCITY);
            player.setRotate(0);
          }

          if(keys[3] && player.getTranslateY() > node.getLayoutY()) {
            character.setY(y - VELOCITY);
            player.setRotate(0);
          }
        }

        // Update player position
        player.setTranslateX(x);
        player.setTranslateY(y);
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
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/fxml/"+ name +".fxml"));

      Scene scene = new Scene(loader.load(), 960, 540);

      setCurrent(getFxControllers(name));

      loader.setController(getCurrent());

      getWindow().setScene(scene);

      // Key-array for checking if pressed (avoiding delay)
      setKeyInput();
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

  public static void main(String[] args) {
    launch(args);
  }
}
