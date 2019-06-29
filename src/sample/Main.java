package sample;

import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import sample.contexts.ApplicationContext;
import sample.contexts.GameContext;
import sample.contexts.MenuContext;
import sample.resources.ImageLoader;
import sample.util.Constants;

import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(Constants.GAME.TITLE);
        primaryStage.getIcons().add(ImageLoader.getImage("SolemnManIcon"));

        ApplicationRunner runner = new ApplicationRunner(List.of(
                new MenuContext(
                        new Canvas(Constants.CONTEXTS.MENU.WIDTH, Constants.CONTEXTS.MENU.HEIGHT)
                ),
                new GameContext(
                        new Canvas(Constants.CONTEXTS.GAME.WIDTH, Constants.CONTEXTS.GAME.HEIGHT),
                        Constants.CONTEXTS.MENU.HEIGHT
                )
        ));

        runner.start();
        primaryStage.setScene(runner.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
