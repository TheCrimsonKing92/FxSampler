package sample.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import sample.util.Constants;

import java.io.IOException;

public class Game extends Canvas {
    public Game() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        load(fxmlLoader);

        initialize();
    }

    private void initialize() {
        this.setHeight(Constants.CONTEXTS.GAME.HEIGHT);
        this.setWidth(Constants.CONTEXTS.GAME.WIDTH);
    }

    private void load(FXMLLoader loader) {
        try {
            loader.load();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
}
