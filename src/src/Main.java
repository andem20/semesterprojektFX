package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import src.enums.CropType;
import src.enums.ItemType;

import java.util.Scanner;

public class Main extends Application {

    private Character character;

    @FXML
    private Rectangle player;

    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("market.fxml"));

        stage.setTitle("Hunger Game");

        stage.setResizable(false);

        createCharacter("Anders");

        MarketController marketController = new MarketController(character);

        root.setController(marketController);

        scene = new Scene(root.load(), 500, 600);

        stage.setScene(scene);
        stage.show();

        boolean[] keys = new boolean[4];

        System.out.println(stage.getScene().getRoot().getBoundsInParent());

        AnimationTimer timer = new AnimationTimer() {
            private int y = 0;
            private int x = 0;

            @Override
            public void handle(long l) {
                stage.getScene().setOnKeyPressed(keyEvent -> {
                    switch(keyEvent.getCode()) {
                        case D -> keys[0] = true;
                        case A -> keys[1] = true;
                        case S -> keys[2] = true;
                        case W -> keys[3] = true;
                    }
                });

                stage.getScene().setOnKeyReleased(keyEvent -> {
                    switch(keyEvent.getCode()) {
                        case D -> keys[0] = false;
                        case A -> keys[1] = false;
                        case S -> keys[2] = false;
                        case W -> keys[3] = false;
                    }
                });

                Node player = stage.getScene().getRoot().lookup("#player");

                if(keys[0] && player.getTranslateX() + player.getBoundsInLocal().getWidth() < stage.getScene().getWidth()) x += 4;
                if(keys[1] && player.getTranslateX() > 0) x -= 4;
                if(keys[2] && player.getTranslateY() + player.getBoundsInLocal().getHeight() < stage.getScene() .getHeight()) y += 4;
                if(keys[3] && player.getTranslateY() > 0) y -= 4;

                player.setTranslateY(y);
                player.setTranslateX(x);
            }
        };

        timer.start();
    }

    private void createCharacter(String name) {
        character = new Character(name, 100);

        // Inventory
        character.addItem(CropType.BEANS.toString(), new src.Crop(0, CropType.BEANS));
        character.addItem(CropType.MAIZE.toString(), new src.Crop(5, CropType.MAIZE));
        character.addItem(CropType.WHEAT.toString(), new src.Crop(10, CropType.WHEAT));
        character.addItem(CropType.CHICKPEAS.toString(), new src.Crop(0, CropType.CHICKPEAS));
        character.addItem(CropType.RICE.toString(), new src.Crop(0, CropType.RICE));
        character.addItem(CropType.SORGHUM.toString(), new src.Crop(0, CropType.SORGHUM));
        character.addItem(ItemType.FERTILIZER.toString(), new src.Item(ItemType.FERTILIZER.toString(), 1, 50));
    }



    public static void main(String[] args) {
        launch(args);
    }
}
