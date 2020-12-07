package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import src.domain.Crop;
import src.domain.Item;
import src.domain.Timer;
import src.enums.GameSettings;
import src.enums.ItemType;
import src.presentation.FXController;
import src.domain.rooms.Field;
import src.presentation.SceneManager;

import java.util.LinkedList;

public class FieldController extends FXController {

  @FXML private ImageView player;
  @FXML private ImageView exit;
  @FXML private ImageView field1;
  @FXML private ImageView field2;
  @FXML private ImageView field3;
  @FXML private Label help;
  @FXML private VBox cropsList;
  @FXML private Button sowButton;

  private Bounds playerBounds;
  private Bounds exitBounds;
  private Bounds fieldBounds1;
  private Bounds fieldBounds2;
  private Bounds fieldBounds3;

  private final Field field;
  private final Image[] fieldImages = new Image[5];
  private final LinkedList<Crop> selectedCrops = new LinkedList<>();
  private int fertilizerAmount;

  public FieldController(SceneManager sceneManager) {
    super(sceneManager);

    field = new Field("Field");

    fieldImages[0] = new Image("/images/field_empty.png");
    fieldImages[1] = new Image("/images/field_growing_1.png");
    fieldImages[2] = new Image("/images/field_growing_2.png");
    fieldImages[3] = new Image("/images/field_growing_3.png");
    fieldImages[4] = new Image("/images/field_ready.png");
  }

  @Override
  public void update() {
    playerBounds = player.getBoundsInParent();
    exitBounds = exit.getBoundsInParent();
    fieldBounds1 = field1.getBoundsInParent();
    fieldBounds2 = field2.getBoundsInParent();
    fieldBounds3 = field3.getBoundsInParent();

    help.setVisible(false);

    fertilizerAmount = getSceneManager().getPlayer().getItem(ItemType.FERTILIZER.toString()).getAmount();

    if(field.isSowed()) {
      int fieldImageNum = (int) (field.getTimer().getCurrentTime() / (field.getTimer().getTime() / 3)) + 1;
      setFieldImage(fieldImages[fieldImageNum]);
    } else {
      setFieldImage(fieldImages[0]);
    }

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    }

    // TODO fix slight lag
    if(playerBounds.intersects(fieldBounds1) || playerBounds.intersects(fieldBounds2) || playerBounds.intersects(fieldBounds3)) {
      if(field.isSowed()) {
        if(field.isReady()) {
          helpMessage("Press 'E' to harvest crops." + "\nPress 'R' to see fieldhealth." +
              (fertilizerAmount > 0 ? "\nPress 'F' to use fertilizer." : ""), help);
        } else {
          helpMessage("Press 'R' to see fieldhealth." + (fertilizerAmount > 0 ? "\nPress 'F' to use fertilizer." : ""), help);
        }
      } else {
        helpMessage("Press 'E' to sow seeds" +
            (fertilizerAmount > 0 ? "\nPress 'F' to use fertilizer." : "") + "\nPress 'R' to see fieldhealth.", help);
      }
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    cropsList.getParent().setVisible(false);

    getSceneManager().getGameOverlay().getConversationLabel().setVisible(false);

    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(exitBounds)) {
        setScene("map");
        Node fieldExit = getSceneManager().getScene().lookup("#field");
        getPlayer().setX((int) fieldExit.getLayoutX());
        getPlayer().setY((int) fieldExit.getLayoutY());
      }

      if(playerBounds.intersects(fieldBounds1) || playerBounds.intersects(fieldBounds2) || playerBounds.intersects(fieldBounds3)) {
        if(fertilizerAmount > 0) fertilize();
      }
    }

    if(keyCode == KeyCode.E) {
      if(playerBounds.intersects(fieldBounds1) || playerBounds.intersects(fieldBounds2) || playerBounds.intersects(fieldBounds3)) {
        if(!field.isSowed()) {
          sow();
        } else if(field.isReady()) {
          harvest();
        }
      }
    }

    if(keyCode == KeyCode.R) {
      getSceneManager().getGameOverlay().setConversationLabel("Fieldhealth is " + (int) (field.getFieldHealth() * 100) + "%");
    }
  }

  private void setFieldImage(Image image) {
    getSceneManager().getScene().getRoot().getChildrenUnmodifiable().stream().filter(node ->
        node instanceof ImageView && node.getId() == null
    ).forEach(node -> {
      ImageView imageView = (ImageView) node;
      if(imageView.getImage() != null) imageView.setImage(image);
    });
  }

  private void sow() {
    // Show list of available crops
    Parent parent = cropsList.getParent();
    cropsList.getChildren().clear();
    parent.setLayoutX(getPlayer().getX());
    parent.setLayoutY(getPlayer().getY() + 40);
    parent.setVisible(true);

    for(Item crop : getPlayer().getInventory().values()){
      if(crop instanceof Crop && crop.getAmount() >= 5) {
        Label label = new Label("5x " + crop.getName());
        label.setTextFill(Color.WHITE);
        label.setCursor(Cursor.HAND);
        label.setPrefWidth(100);
        label.setId(crop.getName());
        label.setPadding(new Insets(5));
        label.setOnMouseClicked(mouseEvent -> {
          if(!selectedCrops.contains(crop)) {
            selectedCrops.addFirst((Crop) crop);
          } else {
            selectedCrops.remove(crop);
          }

          if(selectedCrops.size() > 2) selectedCrops.removeLast();
          cropsList.getChildren().forEach(node -> {
            if(selectedCrops.stream().anyMatch(c -> c.getName().equals(node.getId()))) {
              node.setStyle("-fx-background-color: #359b1d; -fx-text-fill: #FFFFFF;-fx-background-radius: 5;");
            } else {
              node.setStyle("");
            }
          });
        });

        cropsList.getChildren().add(label);
      }
    }

    sowButton.setCursor(Cursor.HAND);
    sowButton.setOnAction(actionEvent -> {
      cropsList.getParent().setVisible(false);
      if(selectedCrops.size() > 0) {
        setFieldImage(fieldImages[1]);
        field.sow(selectedCrops, new Timer(GameSettings.HARVEST_TIME.toInt(), "Your crops are ready"));
        selectedCrops.clear();
      }
    });
  }

  private void harvest() {
    String message = field.harvest();
    getSceneManager().getGameOverlay().updateMessages(message);
    setFieldImage(fieldImages[0]);
  }

  private void fertilize() {
    String message = field.fertilize(getPlayer().getItem(ItemType.FERTILIZER.toString()));
    getSceneManager().getGameOverlay().updateMessages(message);
  }
}
