package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.controllers.FieldController;
import src.controllers.HometownController;
import src.controllers.MarketController;
import src.enums.CropType;
import src.enums.ItemType;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {
  private FXController current;
  private Character character;

  @Override
  public void start(Stage stage) throws Exception {
    createCharacter("Anders");

    stage.setTitle("Hunger Game");
    stage.setResizable(false);

    // Create container for all the scenes
    StackPane root = new StackPane();
    stage.setScene(new Scene(root, 500, 600));

    // Create a hashmap of all fxml and corresponding controllers
    HashMap<String, FXController> fxControllers = new HashMap<>();
    fxControllers.put("market", new MarketController(character));
    fxControllers.put("hometown", new HometownController(character));
    fxControllers.put("field", new FieldController(character));

    current = fxControllers.get("market");

    root.getChildren().add(current.getParent());

    stage.show();


    // Key-array for checking if pressed (avoiding delay)
    boolean[] keys = new boolean[4];

    // Gameloop
    AnimationTimer timer = new AnimationTimer() {
      private Node processName = null;
      private boolean intersect = false;

      @Override
      public void handle(long l) {
        Node player = stage.getScene().getRoot().lookup("#player");

        current.update();

        int x = character.getX();
        int y = character.getY();

        List<Node> nodes = stage.getScene().getRoot().getChildrenUnmodifiable().stream()
            .filter(node -> !node.equals(player)).collect(Collectors.toList());

        // Check what should be processed
        for(Node node : nodes){
          if(player.getBoundsInParent().intersects(node.getBoundsInParent())) {
            processName = node;
            intersect = true;
            break;
          } else {
            intersect = false;
          }
        }

        processName = intersect ? processName : null;

        stage.getScene().setOnKeyPressed(keyEvent -> {
          switch(keyEvent.getCode()) {
            case D -> keys[0] = true;
            case A -> keys[1] = true;
            case S -> keys[2] = true;
            case W -> keys[3] = true;
            case F -> process(processName);
          }
        });

        stage.getScene().setOnKeyReleased(keyEvent -> {
          switch(keyEvent.getCode()) {
            case D -> keys[0] = false;
            case A -> keys[1] = false;
            case S -> keys[2] = false;
            case W -> keys[3] = false;
          }
        });


        if(keys[0] && player.getTranslateX() + player.getBoundsInLocal().getWidth() < stage.getScene().getWidth()) character.setX(x + 4);
        if(keys[1] && player.getTranslateX() > 0) character.setX(x - 4);
        if(keys[2] && player.getTranslateY() + player.getBoundsInLocal().getHeight() < stage.getScene() .getHeight()) character.setY(y + 4);
        if(keys[3] && player.getTranslateY() > 0) character.setY(y - 4);;

        player.setTranslateX(x);
        player.setTranslateY(y);
      }
    };

    timer.start();
  }

  private void createCharacter(String name) {
    character = new Character(name, 100);

    // Inventory
    character.addItem(CropType.BEANS.toString(), new src.Crop(0, CropType.BEANS));
    character.addItem(CropType.MAIZE.toString(), new src.Crop(5, CropType.MAIZE));
    character.addItem(CropType.WHEAT.toString(), new src.Crop(10, CropType.WHEAT));
    character.addItem(CropType.CHICKPEAS.toString(), new src.Crop(0, CropType.CHICKPEAS));
    character.addItem(CropType.RICE.toString(), new src.Crop(0, CropType.RICE));
    character.addItem(CropType.SORGHUM.toString(), new src.Crop(0, CropType.SORGHUM));
    character.addItem(ItemType.FERTILIZER.toString(), new src.Item(ItemType.FERTILIZER.toString(), 1, 50));
  }

  private void process(Node node) {
    if(node == null) return;
    System.out.println(node.getId());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
