package src.controllers;

import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import src.Crop;
import src.FXController;
import src.Main;
import src.Timer;
import src.enums.CropType;
import src.rooms.Field;

public class FieldController extends FXController {

  private Field field;
  private Image cropsSowed = new Image("/images/fieldpart1crops.png");

  public FieldController(Main main) {
    super(main);

    field = new Field("Field");
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

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    }

    if(playerBounds.intersects(field1) || playerBounds.intersects(field2) || playerBounds.intersects(field3)) {
      helpMessage("Press 'F' to sow seeds.", help);
    }

    if(field.isSowed()) setCropsImage();
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
        if(!field.isSowed()) setCropsImage();

        field.sow(new Crop(5, CropType.MAIZE), new Timer(10, "Your crops are ready"));
      }
    }
  }

  private void setCropsImage() {
    getMain().getView().getRoot().getChildrenUnmodifiable().stream().filter(node ->
        node instanceof ImageView && node.getId() == null
    ).forEach(node -> {
      ImageView imageView = (ImageView) node;
      if(imageView.getImage() != null) imageView.setImage(cropsSowed);
    });
  }
}
