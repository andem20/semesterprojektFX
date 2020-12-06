package src.presentation;

import javafx.scene.input.KeyCode;

public class Input {

  private final SceneManager sceneManager;
  private final boolean[] keys = new boolean[4];

  public Input(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  public void setKeyInput() {
    sceneManager.getScene().setOnKeyPressed(keyEvent -> {

      if(keyEvent.getCode() != KeyCode.I) sceneManager.getGameOverlay().getInventoryBox().setVisible(false);
      if(sceneManager.getGameOverlay().getStoryPane().isVisible() && keyEvent.getCode() == KeyCode.SPACE) {
        sceneManager.getGameOverlay().hideStoryPane();
      }

      switch(keyEvent.getCode()) {
        case D -> keys[0] = true;
        case A -> keys[1] = true;
        case S -> keys[2] = true;
        case W -> keys[3] = true;
        case P -> sceneManager.getGameOverlay().getStatusBar().playPause();
        case I -> sceneManager.getGameOverlay().toggleInventoryBox();
      }

      sceneManager.getController().onKeyPressed(keyEvent.getCode());
    });

    sceneManager.getScene().setOnKeyReleased(keyEvent -> {
      switch(keyEvent.getCode()) {
        case D -> keys[0] = false;
        case A -> keys[1] = false;
        case S -> keys[2] = false;
        case W -> keys[3] = false;
      }
    });
  }

  public void setMouseInput() {
    // Hide messages
    sceneManager.getScene().setOnMouseClicked(mouseEvent -> {
      if(!sceneManager.getGameOverlay().getStatusBar().getMessagesImage().isHover()) {
        sceneManager.getGameOverlay().getMessagesBox().setVisible(false);
        sceneManager.getGameOverlay().updateMessages();
      }
    });
  }

  public boolean[] getKeys() {
    return keys;
  }
}
