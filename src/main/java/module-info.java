module semesterprojektFX {
  requires java.desktop;
  requires javafx.controls;
  requires javafx.fxml;
  opens game.presentation to javafx.fxml;
  opens game.presentation.controllers to javafx.fxml;
  exports game;
}