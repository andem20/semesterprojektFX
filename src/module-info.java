module semesterprojektFX {
  requires java.desktop;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  opens src.presentation to javafx.fxml;
  opens src.presentation.controllers to javafx.fxml;
  exports src;
}