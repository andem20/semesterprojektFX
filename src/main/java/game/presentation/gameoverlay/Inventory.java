package game.presentation.gameoverlay;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import game.domain.Item;

import java.util.Map;
import java.util.Set;

public class Inventory extends VBox {
  private final Set<Map.Entry<String, Item>> inventory;

  public Inventory(Set<Map.Entry<String, Item>> inventory) {
    this.inventory = inventory;

    getStyleClass().add("inventory");
  }

  public void update() {
    getChildren().clear();
    Label title = new Label("Inventory");
    title.setStyle("-fx-font-size: 18;");
    getChildren().add(title);

    inventory.stream().filter(item -> item.getValue().getAmount() > 0).forEach(item -> {
      Label label = new Label(item.getValue().getAmount() + "x " + item.getKey());
      getChildren().add(label);
    });
  }
}
