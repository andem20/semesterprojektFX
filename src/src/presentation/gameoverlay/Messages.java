package src.presentation.gameoverlay;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Messages extends VBox {

  private final ArrayList<Message> messages;

  public Messages() {
    messages = new ArrayList<>();

    setBackground(
        new Background(
            new BackgroundFill(
                new Color(1, 1, 1, 0.6),
                new CornerRadii(5), Insets.EMPTY
            )
        )
    );

    setPadding(new Insets(5));

    setLayoutX(570);
    setLayoutY(30);
    setVisible(false);
  }

  public void addMessage(String content) {
    messages.add(new Message(content));
  }

  public void removeMessage(Message message) {
    messages.remove(message);
  }

  public ArrayList<Message> getMessages() {
    return messages;
  }
}
