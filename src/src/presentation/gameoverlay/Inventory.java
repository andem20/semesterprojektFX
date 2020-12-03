package src.presentation.gameoverlay;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import src.domain.Item;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Inventory extends VBox {

  private ArrayList<Item> selected;

  public Inventory(Set<Map.Entry<String, Item>> inventory) {

    selected = new ArrayList<>();

    inventory.forEach(item -> {
      Label label = new Label(item.getKey());
      label.setOnMouseClicked(mouseEvent -> {
        selected.add(item.getValue());
      });
      getChildren().add(label);
    });
  }

  public ArrayList<Item> getSelected() {
    return selected;
  }
}
