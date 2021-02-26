package game.presentation;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import game.domain.Status;
import game.domain.Storyline;
import game.domain.Timer;
import game.domain.characters.Player;

public class GameManager {

  private SceneManager sceneManager;
  // Player animation images
  private final Image moving1 = new Image(getClass().getResource("/images/player/moving1.png").toExternalForm());
  private final Image moving2 = new Image(getClass().getResource("/images/player/moving2.png").toExternalForm());
  private final Image still = new Image(getClass().getResource("/images/player/still.png").toExternalForm());

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
          String timerMessage = Timer.timers.iterator().next().update();
          if(timerMessage != null) sceneManager.getGameOverlay().updateMessages(timerMessage);
        }

        // Check win / lose condition
        if(status.update()) {
          showLoseMessage();
        }

        updateStatusBar();

        checkStoryline();
      }
    };

    sceneManager.setGameLoop(gameLoop);

    gameLoop.start();
  }

  private void animatePlayer(double timer, double duration) {
    boolean[] keys = sceneManager.getKeys();
    if((keys[0] || keys[1] || keys[2] || keys[3])) {
      sceneManager.getController().getPlayer().setImage(timer < duration ? moving1 : moving2);
    } else {
      sceneManager.getController().getPlayer().setImage(still);
    }
  }

  public Player getPlayer() {
    return sceneManager.getPlayerClass();
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
    if(status.getHungerLevel() <= 0.2 && storyline.getLevel() == 5) {
      sceneManager.getGameOverlay().getStatusBar().playPause();
      showStory();
      sceneManager.getGameOverlay().getStoryButton().setText("Play again");
      sceneManager.getGameOverlay().getStoryPane().setStyle("-fx-background-color: #418822");
      setRestartButton();
    }
  }

  public void showStory() {
    sceneManager.getGameOverlay().getStoryPane().setStyle("-fx-background-color: #8b3322;");
    sceneManager.getGameOverlay().getStoryLabel().setText(storyline.getStory());
    sceneManager.getGameOverlay().showStoryPane();
    storyline.increaseLevel();
  }

  private void showLoseMessage() {
    sceneManager.getGameOverlay().getStatusBar().playPause();
    sceneManager.getGameOverlay().getStoryLabel().setText("YOU LOST!");
    sceneManager.getGameOverlay().getStoryButton().setText("Try again");
    sceneManager.getGameOverlay().showStoryPane();
    setRestartButton();
  }

  private void setRestartButton() {
    sceneManager.getGameOverlay().getStoryButton().setOnMouseClicked(mouseEvent -> {
      status = new Status(100);
      storyline = new Storyline();
      sceneManager = new SceneManager(sceneManager.getStage());
      play();
    });
  }
}
