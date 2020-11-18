package src.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.Character;
import src.FXController;
import src.rooms.Market;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MarketController extends FXController implements Initializable {
  private final Market market;
  private Scene scene;

  @FXML
  private Button switchScene;

  public MarketController(Character character) throws IOException {
    super(character);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/fxml/market.fxml"));

    scene = new Scene(loader.load(), 400, 500);

    loader.setController(this);

    this.market = new Market("Market");
  }


  public void buy() {
      String success = "added to";

      getCharacter().trade(market.getNPC(), "beans", success);
  }

  public String showStock() {
    return "hej";

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    switchScene.setOnAction(e -> {
      Stage stage = (Stage) switchScene.getScene().getWindow();

      FXMLLoader root = new FXMLLoader(getClass().getResource("fxml/hometown.fxml"));
      HometownController hometownController = new HometownController(getCharacter());

      root.setController(hometownController);

      Scene scene = null;
      try {
        scene = new Scene(root.load(), 500, 600);
        getCharacter().setY(0);
        getCharacter().setX(100);
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      stage.setScene(scene);
      stage.show();
    });
  }

  @Override
  public void update() {
    scene.getRoot().lookup("#showStock").setOnMousePressed(e -> market.showStock());
    scene.getRoot().lookup("#showInventory").setOnMousePressed(e -> getCharacter().showInventory());
    scene.getRoot().setOnKeyPressed(e -> {
      if(e.getCode() == KeyCode.B) System.out.println("B");
    });
  }

  @Override
  public Parent getParent() {
    return scene.getRoot();
  }
}
