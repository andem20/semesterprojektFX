package game.presentation;

import javafx.scene.Node;
import game.enums.GameSettings;

public class Collision {

  private static final int TILESIZE = GameSettings.TILESIZE.toInt();

  public static void checkPlayerCollision(SceneManager sceneManager) {
    Node playerModel = sceneManager.getController().getPlayer();
    Node[][] grid = sceneManager.getGrid();
    boolean[] keys = sceneManager.getKeys();
    
    // Reference for the player coordinates
    int x = sceneManager.getPlayerClass().getX();
    int y = sceneManager.getPlayerClass().getY();

    int playerWidth = (int) playerModel.getBoundsInLocal().getWidth();
    int playerHeight = (int) playerModel.getBoundsInLocal().getHeight();

    int viewWidth = (int) sceneManager.getScene().getWidth();
    int viewHeight = (int) sceneManager.getScene().getHeight();

    // Players current tile
    int colLeft = Math.max((x - x % TILESIZE) / TILESIZE, 0);
    int rowTop = Math.max((y - y % TILESIZE) / TILESIZE, 0);
    int colRight = Math.min((x + playerWidth % TILESIZE) / TILESIZE, grid[0].length - 1);
    int rowBottom = Math.min((y + playerHeight % TILESIZE) / TILESIZE, grid.length-1);
    // Neighbour tiles
    int right = Math.min(colLeft+1, grid[0].length-1);
    int left = Math.max(colLeft-1, 0);
    int top = Math.max(rowTop-1, 0);
    int bottom = Math.min(rowTop+1, grid.length-1);

    // Right
    if(keys[0]) {
      for(int i = 0; i < GameSettings.VELOCITY.toInt(); i++) {
        if((grid[rowTop][right] != null && grid[rowBottom][right] != null) || (x + playerWidth) % TILESIZE < TILESIZE - 1) {
          if(x + playerWidth < viewWidth) {
            x++;
            playerModel.setRotate(90);
          }
        }
      }
    }

    // Left
    if(keys[1]) {
      for(int i = 0; i < GameSettings.VELOCITY.toInt(); i++) {
        if((grid[rowTop][left] != null && grid[rowBottom][left] != null) || x % TILESIZE > 0) {
          if(x > 0) {
            x--;
            playerModel.setRotate(90);
          }
        }
      }
    }

    // Down
    if(keys[2]) {
      for(int i = 0; i < GameSettings.VELOCITY.toInt(); i++) {
        if((grid[bottom][colLeft] != null && grid[bottom][colRight] != null) || (y + playerHeight) % TILESIZE < TILESIZE - 1) {
          if(y + playerHeight < viewHeight) {
            y++;
            playerModel.setRotate(0);
          }
        }
      }
    }

    // Up
    if(keys[3]) {
      for(int i = 0; i < GameSettings.VELOCITY.toInt(); i++) {
        if((grid[top][colLeft] != null && grid[top][colRight] != null) || y % TILESIZE > 0) {
          if(y > 0) {
            y--;
            playerModel.setRotate(0);
          }
        }
      }
    }

    // Update character position
    sceneManager.getPlayerClass().setX(x);
    sceneManager.getPlayerClass().setY(y);
    // Update rendered player's position
    playerModel.setTranslateX(x);
    playerModel.setTranslateY(y);
  }
}
