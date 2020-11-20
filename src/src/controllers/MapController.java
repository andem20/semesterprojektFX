package src.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.Character;
import src.FXController;
import src.Main;
import src.rooms.Market;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MapController extends FXController implements Initializable {
  private final Market market;
  private Scene scene;

  public MapController(Main main) {
    super(main);

    this.market = new Market("Market");
  }


  public void buy() {
      String success = "added to";

    getMain().getCharacter().trade(market.getNPC(), "beans", success);
  }

  public String showStock() {
    return "hej";

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
  }

  @Override
  public void update() {
//    getMain().getView().lookup("#showStock").setOnMousePressed(e -> market.showStock());
//    getMain().getView().lookup("#showInventory").setOnMousePressed(e -> getMain().getCharacter().showInventory());

//    getMain().getView().getRoot().setOnKeyPressed(e -> {
//      Bounds playerBounds = getMain().getView().lookup("#player").getBoundsInParent();
//      Bounds switchSceneBounds = getMain().getView().lookup("#switchScene").getBoundsInParent();
//
//      if(e.getCode() == KeyCode.F && playerBounds.intersects(switchSceneBounds)) {
//        getMain().setView("hometown");
//      }
//    });
  }
}
