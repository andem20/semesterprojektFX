package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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
import src.presentation.FXController;
import src.GUI;
import src.domain.rooms.Field;

public class FieldController extends FXController {

  private final Field field;
  private final Image[] fieldImages = new Image[5];
  private Crop selectedCrop = null;

  public FieldController(GUI GUI) {
    super(GUI);

    field = new Field("Field");

    fieldImages[0] = new Image("/images/field_empty.png");
    fieldImages[1] = new Image("/images/field_growing_1.png");
    fieldImages[2] = new Image("/images/field_growing_2.png");
    fieldImages[3] = new Image("/images/field_growing_3.png");
    fieldImages[4] = new Image("/images/field_ready.png");
  }

  @Override
  public void update() {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds field1 = getGUI().getView().lookup("#field1").getBoundsInParent();
    Bounds field2 = getGUI().getView().lookup("#field2").getBoundsInParent();
    Bounds field3 = getGUI().getView().lookup("#field3").getBoundsInParent();

    Label help = (Label) getGUI().getView().lookup("#help");
    help.setVisible(false);

    if(field.isSowed()) {
      int fieldImageNum = (int) (field.getTimer().getCurrentTime() / (field.getTimer().getTime() / 3)) + 1;
      setFieldImage(fieldImages[fieldImageNum]);
    } else {
      setFieldImage(fieldImages[0]);
    }

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    }

    // TODO show "harvest crops" when crops are ready
    if(playerBounds.intersects(field1) || playerBounds.intersects(field2) || playerBounds.intersects(field3)) {
      if(field.isSowed()) {
        if(field.isReadyCrops()) {
          helpMessage("Press 'F' to harvest crops.", help);
        }
       } else {
        helpMessage("Press 'F' to sow seeds.", help);
      }
    }
  }


  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds field1 = getGUI().getView().lookup("#field1").getBoundsInParent();
    Bounds field2 = getGUI().getView().lookup("#field2").getBoundsInParent();
    Bounds field3 = getGUI().getView().lookup("#field3").getBoundsInParent();
    VBox cropsList = (VBox) getGUI().getView().lookup("#cropsList");
    cropsList.getParent().setVisible(false);


    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(exitBounds)) {
        getGUI().setView("map");
        getGUI().getCharacter().setX((int) getGUI().getView().lookup("#field").getLayoutX());
        getGUI().getCharacter().setY((int) getGUI().getView().lookup("#field").getLayoutY());
      }

      if(playerBounds.intersects(field1) || playerBounds.intersects(field2) || playerBounds.intersects(field3)) {
        if(!field.isSowed()) {
          sow();
        } else if(field.isReadyCrops()) {
          harvest();
        }
      }
    }
  }

  private void setFieldImage(Image image) {
    getGUI().getView().getRoot().getChildrenUnmodifiable().stream().filter(node ->
        node instanceof ImageView && node.getId() == null
    ).forEach(node -> {
      ImageView imageView = (ImageView) node;
      if(imageView.getImage() != null) imageView.setImage(image);
    });
  }

  private void sow() {
    // Show list of available crops
    VBox cropsList = (VBox) getGUI().getView().lookup("#cropsList");
    Parent parent = cropsList.getParent();
    cropsList.getChildren().clear();
    parent.setLayoutX(getGUI().getCharacter().getX());
    parent.setLayoutY(getGUI().getCharacter().getY() + 40);
    parent.setVisible(true);

    for(Item crop : getGUI().getCharacter().getInventory().values()){
      if(crop instanceof Crop && crop.getAmount() > 0) {
        Label label = new Label(crop.getName());
        label.setTextFill(Color.WHITE);
        label.setCursor(Cursor.HAND);
        label.setPrefWidth(100);
        label.setPadding(new Insets(5));
        label.setOnMouseClicked(mouseEvent -> {
          cropsList.getChildren().forEach(node -> node.setStyle(""));
          selectedCrop = (Crop) crop;
          label.setStyle("-fx-background-color: #359b1d; -fx-text-fill: #FFFFFF;-fx-background-radius: 5;");
        });
        cropsList.getChildren().add(label);
      }
    }

    Button sowButton = (Button) getGUI().getView().lookup("#sowButton");
    sowButton.setCursor(Cursor.HAND);
    sowButton.setOnAction(actionEvent -> {
      cropsList.getParent().setVisible(false);
      if(selectedCrop != null) {
        setFieldImage(fieldImages[1]);
        field.sow(selectedCrop, new Timer(10, "Your " + selectedCrop.getName() + " are ready"));
        selectedCrop = null;
      } else {
        System.out.println("No crop seleceted!!!");
      }
    });
  }

  private void harvest() {
    String harvest = field.harvest();
    getGUI().getGameOverlay().getMessagesBox().addMessage(harvest);
    getGUI().getGameOverlay().updateMessages();
    getGUI().getGameOverlay().setShortMessage(harvest);
    getGUI().getGameOverlay().showShortMessage();
    setFieldImage(fieldImages[0]);
  }
}
