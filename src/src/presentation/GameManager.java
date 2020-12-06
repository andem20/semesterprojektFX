package src.presentation;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import src.domain.Status;
import src.domain.Storyline;
import src.domain.Timer;
import src.domain.characters.Player;
import src.presentation.gameoverlay.GameOverlay;

public class GameManager {

  private final SceneManager sceneManager;
  private AnimationTimer gameLoop;
  // Player animation images
  private final Image moving1 = new Image("/images/player/moving1.png");
  private final Image moving2 = new Image("/images/player/moving2.png");
  private final Image still = new Image("/images/player/still.png");
  // Status
  private final Status status;
  // Storyline
  private final Storyline storyline;

  public GameManager(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    status = new Status(100);
    storyline = new Storyline();
  }

  public void play() {
    gameLoop = new AnimationTimer() {
      final long start = System.nanoTime();

      @Override
      public void handle(long l) {
        // Animating player model every 250 ms
        double loopTimer = ((l - start) / 1e9) % 0.5;
        animatePlayer(loopTimer, 0.25);

        // Call current controller update method
        sceneManager.getController().update();

        Collision.checkCollision(sceneManager);
//
        // Updating timers
        if(Timer.timers.iterator().hasNext()) {
          String timerMessage = Timer.timers.iterator().next().updateTimer();
          if(timerMessage != null) {
            sceneManager.getGameOverlay().getMessagesBox().addMessage(timerMessage);
            sceneManager.getGameOverlay().updateMessages();
            sceneManager.getGameOverlay().setShortMessage(timerMessage);
            sceneManager.getGameOverlay().showShortMessage();
          }
        }
//
//        // Check win / lose condition
//        if(getStatus().checkStatus()) this.stop();
          status.checkStatus();
//
        sceneManager.getGameOverlay().getStatusBar().setStatusText(status.getPopulation(), status.getHungerLevel(), status.getDays());
//
        showStoryline();
      }
    };

    sceneManager.setGameLoop(gameLoop);

    gameLoop.start();
  }

  public void animatePlayer(double timer, double duration) {
    boolean[] keys = sceneManager.getKeyInput();
    if((keys[0] || keys[1] || keys[2] || keys[3])) {
      if(timer < duration) {
        sceneManager.getPlayerModel().setImage(moving1);
      } else {
        sceneManager.getPlayerModel().setImage(moving2);
      }
    }

    if(!(keys[0] || keys[1] || keys[2] || keys[3])) {
      getSceneManager().getPlayerModel().setImage(still);
    }
  }

  public Player getPlayer() {
    return sceneManager.getPlayer();
  }

  public void showStoryline() {
    if(status.getHungerLevel() > 0.6 && storyline.getLevel() == 1) {
      ((Label) sceneManager.getGameOverlay().getStoryPane().getChildren().get(0)).setText(storyline.getStory());
      sceneManager.getGameOverlay().showStoryPane();
      storyline.increaseLevel();
    }

    if(status.getHungerLevel() <= 0.6 && storyline.getLevel() == 2) {
      ((Label) sceneManager.getGameOverlay().getStoryPane().getChildren().get(0)).setText(storyline.getStory());
      sceneManager.getGameOverlay().showStoryPane();
      storyline.increaseLevel();
    }

    if(status.getHungerLevel() <= 0.5 && storyline.getLevel() == 3) {
      ((Label) sceneManager.getGameOverlay().getStoryPane().getChildren().get(0)).setText(storyline.getStory());
      sceneManager.getGameOverlay().showStoryPane();
      storyline.increaseLevel();
    }

    if(status.getHungerLevel() <= 0.3 && storyline.getLevel() == 4) {
      ((Label) sceneManager.getGameOverlay().getStoryPane().getChildren().get(0)).setText(storyline.getStory());
      sceneManager.getGameOverlay().showStoryPane();
      storyline.increaseLevel();
    }

    if(status.getHungerLevel() <= 0.2 && storyline.getLevel() == 5) {
      ((Label) sceneManager.getGameOverlay().getStoryPane().getChildren().get(0)).setText(storyline.getStory());
      sceneManager.getGameOverlay().showStoryPane();
      storyline.increaseLevel();
    }
  }

  public SceneManager getSceneManager() {
    return sceneManager;
  }

  public AnimationTimer getGameLoop() {
    return gameLoop;
  }
}
