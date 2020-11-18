package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMap {

  private final String filename;

  public GameMap(String filename) {
    this.filename = filename;
  }

  public void showMap() {
    try {
      File file = new File(filename);
      Scanner reader = new Scanner(file);

      while(reader.hasNextLine()) {
        System.out.println(reader.nextLine());
      }
    } catch (FileNotFoundException e) {
      System.out.println("Couldn't load the map.");
      e.printStackTrace();
    }
  }
}
