package src.presentation.controllers;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import src.domain.Character;
import src.domain.Item;
import src.presentation.FXController;
import src.GUI;
import src.domain.rooms.Market;

import java.util.Map;

public class MarketController extends FXController {

  private final Market market;

  public MarketController(GUI GUI) {
    super(GUI);

    market = new Market("Market");
  }

  @Override
  public void update() {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds exitBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds sellerBounds = getGUI().getView().lookup("#seller").getBoundsInParent();

    Label help = (Label) getGUI().getView().lookup("#help");

    if(playerBounds.intersects(exitBounds)) {
      helpMessage("Press 'F' to exit", help);
    } else if(playerBounds.intersects(sellerBounds)) {
      helpMessage("Press 'E' to sell or 'R' buy", help);
    } else {
      help.setVisible(false);
    }
  }

  @Override
  public void onKeyPressed(KeyCode keyCode) {
    Bounds playerBounds = getGUI().getView().lookup("#player").getBoundsInParent();
    Bounds switchSceneBounds = getGUI().getView().lookup("#exit").getBoundsInParent();
    Bounds sellerBounds = getGUI().getView().lookup("#seller").getBoundsInParent();

    HBox marketInventory = (HBox) getGUI().getView().lookup("#marketInventory");
    marketInventory.getParent().setVisible(false);

    if(keyCode == KeyCode.F) {
      if(playerBounds.intersects(switchSceneBounds)) {
        getGUI().setView("map");
        getGUI().getCharacter().setX((int) getGUI().getView().lookup("#market").getLayoutX());
        getGUI().getCharacter().setY((int) getGUI().getView().lookup("#market").getLayoutY());
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
    Character buyer = action == 1 ? market.getNPC() : getGUI().getCharacter();
    Character seller = action == 1 ? getGUI().getCharacter() : market.getNPC();

    HBox marketInventory = (HBox) getGUI().getView().lookup("#marketInventory");
    marketInventory.getParent().setVisible(true);

    Label description = (Label) marketInventory.getParent().lookup("#description");
    description.setText((action == 0 ? "Buy" : "Sell") + " items");

    Label coins = (Label) marketInventory.getParent().lookup("#coins");
    coins.setText((action == 0 ? "Your" : "Market") + " coins: $" + buyer.getCoins());

    VBox nameBox = (VBox) marketInventory.lookup("#name");
    VBox amountBox = (VBox) marketInventory.lookup("#amount");
    VBox priceBox = (VBox) marketInventory.lookup("#price");
    VBox actionBox = (VBox) marketInventory.lookup("#action");

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

          getGUI().getGameOverlay().getMessagesBox().addMessage(message);
          getGUI().getGameOverlay().updateMessages();
          getGUI().getGameOverlay().setShortMessage(message);
          getGUI().getGameOverlay().showShortMessage();

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
