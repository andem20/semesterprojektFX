package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import src.domain.rooms.School;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class SchoolController extends FXController {

    @FXML private ImageView player;
    @FXML private Pane board;
    @FXML private ImageView exit;
    @FXML private ImageView exit2;
    @FXML private ImageView exit3;
    @FXML private Label help;
    @FXML private ImageView teacher;

    private Bounds playerBounds;
    private Bounds exit1Bounds;
    private Bounds exit2Bounds;
    private Bounds exit3Bounds;
    private Bounds boardBounds;
    private Bounds teacherBounds;

    private final School school;

    public SchoolController(SceneManager sceneManager) {
        super(sceneManager);

        this.school = new School("School");
    }

    @Override
    public void update() {
        playerBounds = player.getBoundsInParent();
        exit1Bounds = exit.getBoundsInParent();
        exit2Bounds = exit2.getBoundsInParent();
        exit3Bounds = exit3.getBoundsInParent();
        boardBounds = board.getBoundsInParent();
        teacherBounds = teacher.getBoundsInParent();

        if(playerBounds.intersects(exit1Bounds) || playerBounds.intersects(exit2Bounds) || playerBounds.intersects(exit3Bounds)) {
            helpMessage("Press 'F' to exit.", help);
        } else {
            help.setVisible(false);
        }

        if(playerBounds.intersects(boardBounds)) {
            helpMessage("Press 'E' to lecture.", help);
        } else if(playerBounds.intersects(teacherBounds)) {
            helpMessage("Press 'E' to lecture.", help);
        } else {
            board.setVisible(false);
        }
    }


    @Override
    public void onKeyPressed(KeyCode keyCode) {
        if(keyCode == KeyCode.F) {
            if((playerBounds.intersects(exit1Bounds) || playerBounds.intersects(exit2Bounds) || playerBounds.intersects(exit3Bounds))) {
                setScene("map");
                Node schoolExit = getSceneManager().getScene().lookup("#school");
                getPlayer().setX((int) schoolExit.getLayoutX());
                getPlayer().setY((int) schoolExit.getLayoutY());
            }
        }

        if(keyCode == KeyCode.E && playerBounds.intersects(boardBounds)) {
            getSceneManager().getGameOverlay().getStoryPane().setStyle("-fx-background-color: #333e39");
            getSceneManager().getGameOverlay().getStoryLabel().setText(school.teach());
            school.increaseLevel();
            getSceneManager().getGameOverlay().showStoryPane();
        }
        if(keyCode == KeyCode.E && playerBounds.intersects(teacherBounds)) {
            getSceneManager().getGameOverlay().getStoryPane().setStyle("-fx-background-color: #333e39");
            getSceneManager().getGameOverlay().getStoryLabel().setText(school.teach());
            school.increaseLevel();
            getSceneManager().getGameOverlay().showStoryPane();
        }
    }
}
