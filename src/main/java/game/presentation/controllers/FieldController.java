package game.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import game.domain.Crop;
import game.domain.Item;
import game.domain.Timer;
import game.enums.GameSettings;
import game.enums.ItemType;
import game.presentation.FXController;
import game.domain.rooms.Field;
import game.presentation.SceneManager;

import java.util.LinkedList;

public class FieldController extends FXController {

  @FXML private ImageView exit;
  @FXML private ImageView field1;
  @FXML private ImageView field2;
  @FXML private ImageView field3;
  @FXML private VBox cropsList;
  @FXML private Button sowButton;

  private Bounds exitBounds;
  private Bounds fieldBounds1;
  private Bounds fieldBounds2;
  private Bounds fieldBounds3;

  private final Field field;
  private final Image[] fieldImages = new Image[5];
  private final LinkedList<Crop> selectedCrops = new LinkedList<>();

  public FieldController(SceneManager sceneManager) {
    super(sceneManager);

    field = new Field("Field");

    fieldImages[0] = new Image(getClass().getResource("/images/field_empty.png").toExternalForm());
    fieldImages[1] = new Image(getClass().getResource("/images/field_growing_1.png").toExternalForm());
    fieldImages[2] = new Image(getClass().getResource("/images/field_growing_2.png").toExternalForm());
    fieldImages[3] = new Image(getClass().getResource("/images/field_growing_3.png").toExternalForm());
    fieldImages[4] = new Image(getClass().getResource("/images/field_ready.png").toExternalForm());
  }

  @Override
  public void update() {
    updatePlayerBounds();
    exitBounds = exit.getBoundsInParent();
    fieldBounds1 = field1.getBoundsInParent();
    fieldBounds2 = field2.getBoundsInParent();
    fieldBounds3 = field3.getBoundsInParent();

    if(field.isSowed()) {
      int fieldImageNum = (int) (field.getTimer().getCurrentTime() / (field.getTimer().getTime() / 3)) + 1;
      setFieldImage(fieldImages[fieldImageNum]);
    } else {
      setFieldImage(fieldImages[0]);
    }

    if(getPlayerBounds().intersects(exitBounds)) {
      showHelpMessage("Press 'F' to exit.");
    } else if(intersectsField()) {
      String fertilizerString = (getFertilizerAmount() > 0 ? "\nPress 'F' to use fertilizer." : "");
      if(field.isSowed()) {
        if(field.isReady()) {
          showHelpMessage("Press 'E' to harvest crops.\nPress 'R' to see fieldhealth." + fertilizerString);
        } else {
          showHelpMessage("Press 'R' to see fieldhealth." + fertilizerString);
        }
      } else {
        showHelpMessage("Press 'E' to sow seeds\nPress 'R' to see fieldhealth." + fertilizerString);
      }
    } else {
      hideHelpMessage();
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    cropsList.getParent().setVisible(false);

    getSceneManager().getGameOverlay().getConversationLabel().setVisible(false);

    if(keyCode == KeyCode.F) {
      if(getPlayerBounds().intersects(exitBounds)) {
        setScene("map");
        Node fieldExit = getSceneManager().getScene().lookup("#field");
        getPlayerClass().setX((int) fieldExit.getLayoutX());
        getPlayerClass().setY((int) fieldExit.getLayoutY());
      }

      if(intersectsField()) {
        if(getFertilizerAmount() > 0) fertilize();
      }
    }

    if(keyCode == KeyCode.E) {
      if(intersectsField()) {
        if(!field.isSowed()) {
          sow();
        } else if(field.isReady()) {
          harvest();
        }
      }
    }

    if(keyCode == KeyCode.R) {
      showFieldHealth();
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
    parent.setLayoutX(getPlayerClass().getX());
    parent.setLayoutY(getPlayerClass().getY() + 40);
    parent.setVisible(true);

    for(Item crop : getPlayerClass().getInventory().values()){
      if(crop instanceof Crop && crop.getAmount() >= 5) {
        Label label = new Label("5x " + crop.getName());
        label.getStyleClass().add("sow-label");
        label.setId(crop.getName());
        label.setOnMouseClicked(mouseEvent -> {
          if(!selectedCrops.contains(crop)) {
            selectedCrops.addFirst((Crop) crop);
          } else {
            selectedCrops.remove(crop);
          }

          if(selectedCrops.size() > 2) selectedCrops.removeLast();

          cropsList.getChildren().forEach(node -> {
            if(selectedCrops.stream().anyMatch(c -> c.getName().equals(node.getId()))) {
              node.getStyleClass().add("sow-label-selected");
            } else {
              node.getStyleClass().clear();
              node.getStyleClass().addAll("label", "sow-label");
            }
          });
        });

        cropsList.getChildren().add(label);
      }
    }

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
    String message = field.fertilize(getPlayerClass().getItem(ItemType.FERTILIZER.toString()));
    getSceneManager().getGameOverlay().updateMessages(message);
  }

  private boolean intersectsField() {
    return getPlayerBounds().intersects(fieldBounds1)
        || getPlayerBounds().intersects(fieldBounds2)
        || getPlayerBounds().intersects(fieldBounds3);
  }

  private void showFieldHealth() {
    getSceneManager().getGameOverlay().setConversationLabel("Fieldhealth is " + (int) (field.getFieldHealth() * 100) + "%");
  }

  private int getFertilizerAmount() {
    return getSceneManager().getPlayerClass().getItem(ItemType.FERTILIZER.toString()).getAmount();
  }
}
