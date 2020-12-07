package src.presentation;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import src.domain.Status;
import src.domain.Storyline;
import src.domain.Timer;
import src.domain.characters.Player;

public class GameManager {

  private SceneManager sceneManager;
  // Player animation images
  private final Image moving1 = new Image("/images/player/moving1.png");
  private final Image moving2 = new Image("/images/player/moving2.png");
  private final Image still = new Image("/images/player/still.png");

  private Status status;

  private Storyline storyline;

  public GameManager(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
    status = new Status(100);
    storyline = new Storyline();
  }

  public void play() {
    AnimationTimer gameLoop = new AnimationTimer() {
      final long start = System.nanoTime();

      @Override
      public void handle(long l) {
        // Animating player model every 250 ms
        double loopTimer = ((l - start) / 1e9) % 0.5;
        animatePlayer(loopTimer, 0.25);

        // Call current controller update method
        sceneManager.getController().update();

        Collision.checkPlayerCollision(sceneManager);

        // Updating timers
        if(Timer.timers.iterator().hasNext()) {
          String timerMessage = Timer.timers.iterator().next().updateTimer();
          if(timerMessage != null) sceneManager.getGameOverlay().updateMessages(timerMessage);
        }

        // Check win / lose condition
        if(status.checkStatus()) {
          showLoseMessage();
        }

        updateStatusBar();

        checkStoryline();
      }
    };

    sceneManager.setGameLoop(gameLoop);

    gameLoop.start();
  }

  public void animatePlayer(double timer, double duration) {
    boolean[] keys = sceneManager.getKeys();
    if((keys[0] || keys[1] || keys[2] || keys[3])) {
      sceneManager.getPlayerModel().setImage(timer < duration ? moving1 : moving2);
    } else {
      sceneManager.getPlayerModel().setImage(still);
    }
  }

  public Player getPlayer() {
    return sceneManager.getPlayer();
  }

  private void updateStatusBar() {
    sceneManager.getGameOverlay().getStatusBar().setStatusText(
        status.getPopulation(),
        status.getHungerLevel(),
        status.getDays()
    );
  }

  public void checkStoryline() {
    if(status.getHungerLevel() > 0.6 && storyline.getLevel() == 1) showStory();
    if(status.getHungerLevel() <= 0.6 && storyline.getLevel() == 2) showStory();
    if(status.getHungerLevel() <= 0.5 && storyline.getLevel() == 3) showStory();
    if(status.getHungerLevel() <= 0.3 && storyline.getLevel() == 4) showStory();
    if(status.getHungerLevel() <= 0.2 && storyline.getLevel() == 5) showStory();
  }

  public void showStory() {
    sceneManager.getGameOverlay().getStoryPane().setStyle("-fx-background-color: #8b3322;");
    sceneManager.getGameOverlay().getStoryLabel().setText(storyline.getStory());
    sceneManager.getGameOverlay().showStoryPane();
    storyline.increaseLevel();
  }

  private void showLoseMessage() {
    sceneManager.getGameOverlay().getStoryLabel().setText("YOU LOST!");
    sceneManager.getGameOverlay().getStoryButton().setText("Try again");
    sceneManager.getGameOverlay().showStoryPane();
    sceneManager.getGameOverlay().getStoryButton().setOnMouseClicked(mouseEvent -> {
      status = new Status(100);
      storyline = new Storyline();
      sceneManager = new SceneManager(sceneManager.getStage());
      play();
    });
  }
}
