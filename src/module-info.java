module semesterprojektFX {
  requires javafx.base;
  requires javafx.graphics;
  requires javafx.fxml;
  requires javafx.controls;
  requires java.desktop;
  opens src.presentation.controllers to javafx.fxml;
  opens src.presentation to javafx.fxml;
  exports src;
}