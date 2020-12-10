package src.presentation.gameoverlay;

import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class Messages extends VBox {

  private final LinkedList<Message> messages;

  public Messages() {
    messages = new LinkedList<>();

    getStyleClass().add("messages");

    // Positioning right under mail icon
    setLayoutX(540);
    setLayoutY(30);
    setVisible(false);
  }

  public void addMessage(String content) {
    messages.addFirst(new Message(content));
  }

  public LinkedList<Message> getMessages() {
    return messages;
  }
}
