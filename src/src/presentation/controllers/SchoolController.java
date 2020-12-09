package src.presentation.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import src.domain.rooms.School;
import src.presentation.FXController;
import src.presentation.SceneManager;

public class SchoolController extends FXController {

    @FXML private ImageView exit;
    @FXML private ImageView exit2;
    @FXML private ImageView exit3;
    @FXML private ImageView teacher;

    private Bounds exit1Bounds;
    private Bounds exit2Bounds;
    private Bounds exit3Bounds;
    private Bounds teacherBounds;

    private final School school;

    public SchoolController(SceneManager sceneManager) {
        super(sceneManager);

        this.school = new School("School");
    }

    @Override
    public void update() {
        updatePlayerBounds();
        exit1Bounds = exit.getBoundsInParent();
        exit2Bounds = exit2.getBoundsInParent();
        exit3Bounds = exit3.getBoundsInParent();
        teacherBounds = teacher.getBoundsInParent();

        if(intersectsExit()) {
            showHelpMessage("Press 'F' to exit.");
        } else if(getPlayerBounds().intersects(teacherBounds)) {
            showHelpMessage("Press 'E' to lecture.");
        } else {
            hideHelpMessage();
        }
    }


    @Override
    public void onKeyPressed(KeyCode keyCode) {
        if(keyCode == KeyCode.F) {
            if(intersectsExit()) {
                setScene("map");
                Node schoolExit = getSceneManager().getScene().lookup("#school");
                getPlayerClass().setX((int) schoolExit.getLayoutX());
                getPlayerClass().setY((int) schoolExit.getLayoutY());
            }
        }

        if(keyCode == KeyCode.E && getPlayerBounds().intersects(teacherBounds)) {
            getSceneManager().getGameOverlay().getStoryPane().setStyle("-fx-background-color: #333e39");
            getSceneManager().getGameOverlay().getStoryLabel().setText(school.teach());
            school.increaseLevel();
            getSceneManager().getGameOverlay().showStoryPane();
        }
    }

    private boolean intersectsExit() {
        return getPlayerBounds().intersects(exit1Bounds) || getPlayerBounds().intersects(exit2Bounds) || getPlayerBounds().intersects(exit3Bounds);
    }
}
