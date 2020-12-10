package src.presentation;

import src.presentation.controllers.*;

public class FXControllerFactory {

  private final SceneManager sceneManager;

  public FXControllerFactory(SceneManager sceneManager) {
    this.sceneManager = sceneManager;
  }

  public FXController createController(String type) {
    return switch(type.toLowerCase()) {
      case "farm" -> new FarmController(sceneManager);
      case "field" -> new FieldController(sceneManager);
      case "map" -> new MapController(sceneManager);
      case "market" -> new MarketController(sceneManager);
      case "school" -> new SchoolController(sceneManager);
      case "village" -> new VillageController(sceneManager);
      default -> null;
    };
  }
}
