package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class SchoolController extends FXController {

    @FXML private ImageView player;
    @FXML private Pane board;
    @FXML private ImageView exit;
    @FXML private Label help;

    private Bounds playerBounds;
    private Bounds exitBounds;
    private Bounds boardBounds;

    public SchoolController(SceneManager sceneManager) {
        super(sceneManager);
    }

    @Override
    public void update() {
        playerBounds = player.getBoundsInParent();
        exitBounds = exit.getBoundsInParent();
        boardBounds = board.getBoundsInParent();

        if(playerBounds.intersects(exitBounds)) {
            helpMessage("Press 'F' to exit.", help);
        } else {
            help.setVisible(false);
        }
        //TODO: exitbounds for market, hometown and farm (doorSouth, doorNorth and doorEast) - find exitBounds

        if(playerBounds.intersects(boardBounds)) {
            helpMessage("Press 'E' to lecture.", help);
        } else {
            board.setVisible(false);
        } //TODO: get lectures from School.class one at a time
    }


    @Override
    public void onKeyPressed(KeyCode keyCode) {
        if(keyCode == KeyCode.F && playerBounds.intersects(exitBounds)) {
            setScene("map");
            Node schoolExit = getSceneManager().getScene().lookup("#school");
            getPlayer().setX((int) schoolExit.getLayoutX());
            getPlayer().setY((int) schoolExit.getLayoutY());
        }
    }
}
