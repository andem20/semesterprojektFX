package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import src.domain.Crop;
import src.presentation.FXController;
import src.Main;
import src.domain.Timer;
import src.enums.CropType;
import src.domain.rooms.Field;

public class FieldController extends FXController {

  private final Field field;
  private final Image[] fieldImages = new Image[5];

  public FieldController(Main main) {
    super(main);

    field = new Field("Field");

    fieldImages[0] = new Image("/images/field_empty.png");
    fieldImages[1] = new Image("/images/field_growing_1.png");
    fieldImages[2] = new Image("/images/field_growing_2.png");
    fieldImages[3] = new Image("/images/field_growing_3.png");
    fieldImages[4] = new Image("/images/field_ready.png");
  }

  @Override
  public void update() {
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getMain().getView().lookup("#exit").getBoundsInParent();
    Bounds field1 = getMain().getView().lookup("#field1").getBoundsInParent();
    Bounds field2 = getMain().getView().lookup("#field2").getBoundsInParent();
    Bounds field3 = getMain().getView().lookup("#field3").getBoundsInParent();

    Label help = (Label) getMain().getView().lookup("#help");
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
    Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getMain().getView().lookup("#exit").getBoundsInParent();
    Bounds field1 = getMain().getView().lookup("#field1").getBoundsInParent();
    Bounds field2 = getMain().getView().lookup("#field2").getBoundsInParent();
    Bounds field3 = getMain().getView().lookup("#field3").getBoundsInParent();


    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(exitBounds)) {
        getMain().setView("map");
        getMain().getCharacter().setX((int) getMain().getView().lookup("#field").getLayoutX());
        getMain().getCharacter().setY((int) getMain().getView().lookup("#field").getLayoutY());
      }

      if(playerBounds.intersects(field1) || playerBounds.intersects(field2) || playerBounds.intersects(field3)) {
        // TODO show crops are growing
        if(!field.isSowed()) {
          setFieldImage(fieldImages[1]);
          field.sow(new Crop(5, CropType.MAIZE), new Timer(10, "Your crops are ready"));
        } else if(field.isReadyCrops()) {
          field.harvest();
          setFieldImage(fieldImages[0]);
        }
      }
    }
  }

  private void setFieldImage(Image image) {
    getMain().getView().getRoot().getChildrenUnmodifiable().stream().filter(node ->
        node instanceof ImageView && node.getId() == null
    ).forEach(node -> {
      ImageView imageView = (ImageView) node;
      if(imageView.getImage() != null) imageView.setImage(image);
    });
  }
}
