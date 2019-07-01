package sample.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import sample.util.Constants;

import java.io.IOException;

public class Menu extends Canvas {

    public Menu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        load(fxmlLoader);

        initialize();
    }

    private void initialize() {
        this.setHeight(Constants.CONTEXTS.MENU.HEIGHT);
        this.setWidth(Constants.CONTEXTS.MENU.WIDTH);
    }

    private void load(FXMLLoader loader) {
        try {
            loader.load();
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
}
