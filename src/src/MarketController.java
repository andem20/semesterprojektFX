package src;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.rooms.Market;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
  private final Market market;
  private final Character character;

  @FXML
  private Button showStock;
  @FXML
  private Button showInventory;
  @FXML
  private Button switchScene;

  public MarketController(Character character) {
    this.market = new Market("Market");
    this.character = character;
  }

  public void buy() {
      String success = "added to";

      character.trade(market.getNPC(), "beans", success);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    showStock.setOnAction(e -> market.showStock());
    showInventory.setOnAction(e -> character.showInventory());
    switchScene.setOnAction(e -> {
      Stage stage = (Stage) switchScene.getScene().getWindow();

      FXMLLoader root = new FXMLLoader(getClass().getResource("hometown.fxml"));
      HometownController hometownController = new HometownController();

      root.setController(hometownController);

      Scene scene = null;
      try {
        scene = new Scene(root.load(), 500, 600);
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      stage.setScene(scene);
      stage.show();
    });
  }
}
