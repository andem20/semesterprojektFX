package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import src.domain.Character;
import src.domain.Item;
import src.presentation.FXController;
import src.domain.rooms.Market;
import src.presentation.SceneManager;

import java.util.Map;

public class MarketController extends FXController {

  @FXML private ImageView player;
  @FXML private ImageView seller;
  @FXML private Label help;
  @FXML private ImageView exit;
  @FXML private HBox marketInventory;
  @FXML private Label description;
  @FXML private Label coins;
  @FXML private VBox nameBox;
  @FXML private VBox amountBox;
  @FXML private VBox priceBox;
  @FXML private VBox actionBox;

  private Bounds playerBounds;
  private Bounds exitBounds;
  private Bounds sellerBounds;
  private Bounds marketBounds;
  private Bounds schoolBounds;
  private Bounds farmBounds;

  private final Market market;

  public MarketController(SceneManager sceneManager) {
    super(sceneManager);

    market = new Market("Market");
  }

  @Override
  public void update() {
    playerBounds = player.getBoundsInParent();
    exitBounds = exit.getBoundsInParent();
    sellerBounds = seller.getBoundsInParent();

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit.", help);
    } else if(playerBounds.intersects(sellerBounds)) {
      helpMessage("Press 'E' to sell.\nPress 'R' to buy.", help);
    } else {
      help.setVisible(false);
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    marketInventory.getParent().setVisible(false);

    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(exitBounds)) {
        setScene("map");
        Node marketExit = getSceneManager().getScene().lookup("#market");
        getPlayer().setX((int) marketExit.getLayoutX());
        getPlayer().setY((int) marketExit.getLayoutY());
      }
    }

    // Buy
    if(keyCode == KeyCode.R) {
      if(playerBounds.intersects(sellerBounds)) {
        showInventory(0);
      }
    }

    // Sell
    if(keyCode == KeyCode.E) {
      if(playerBounds.intersects(sellerBounds)) {
        showInventory(1);
      }
    }
  }

  private void showInventory(int action) {
    Character buyer = action == 1 ? market.getNPC() : getPlayer();
    Character seller = action == 1 ? getPlayer() : market.getNPC();

    marketInventory.getParent().setVisible(true);

    description.setText((action == 0 ? "Buy" : "Sell") + " items");

    coins.setText((action == 0 ? "Your" : "Market") + " coins: $" + buyer.getCoins());

    nameBox.getChildren().clear();
    amountBox.getChildren().clear();
    priceBox.getChildren().clear();
    actionBox.getChildren().clear();

    for(Map.Entry<String, Item> item : seller.getInventory().entrySet()) {
      String name = item.getKey();
      int amount = item.getValue().getAmount();
      int price = item.getValue().getPrice();
      if(amount > 0) {
        nameBox.getChildren().add(new Label(name));
        amountBox.getChildren().add(new Label(Integer.toString(amount)));
        priceBox.getChildren().add(new Label("$" + price));

        Button actionButton = createActionButton((action == 0 ? "Buy" : "Sell"));
        actionButton.setOnMouseClicked(mouseEvent -> {
          String message = "Insufficient funds!";
          if(buyer.getCoins() >= item.getValue().getPrice()) {
            buyer.getItem(item.getKey()).increaseAmount();
            buyer.removeCoins(item.getValue().getPrice());

            item.getValue().decreaseAmount();
            seller.addCoins(item.getValue().getPrice());

            message = item.getValue().getName() + " was " + (action == 0 ? "added" : "removed") + " your inventory.";
          }

          getSceneManager().getGameOverlay().updateMessages(message);

          // Update inventory
          showInventory(action);
        });

        actionBox.getChildren().add(actionButton);
      }
    }
  }

  private Button createActionButton(String action) {
    Button actionButton = new Button(action);
    actionButton.setStyle("-fx-background-color: #2a7918; -fx-font-weight: bold;");
    actionButton.setPadding(new Insets(0, 5, 0, 5));
    actionButton.setTextFill(Color.WHITE);
    actionButton.setCursor(Cursor.HAND);

    actionButton.setOnMouseEntered(mouseEvent ->
        actionButton.setStyle("-fx-background-color: #359b1d; -fx-font-weight: bold;")
    );
    actionButton.setOnMouseExited(mouseEvent ->
        actionButton.setStyle("-fx-background-color: #2a7918; -fx-font-weight: bold;")
    );
    actionButton.setOnMousePressed(mouseEvent ->
        actionButton.setStyle("-fx-background-color: #146e0f; -fx-font-weight: bold;")
    );
    actionButton.setOnMouseReleased(mouseEvent ->
        actionButton.setStyle("-fx-background-color: #2a7918; -fx-font-weight: bold;")
    );

    return actionButton;
  }
}
