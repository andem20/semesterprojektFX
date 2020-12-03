package src.presentation.gameoverlay;

public class Message {

  private final String content;
  private boolean seen = false;

  public Message(String content) {
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public boolean isSeen() {
    return seen;
  }

  public void setSeen(boolean seen) {
    this.seen = seen;
  }
}
