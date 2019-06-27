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

        List<ApplicationContext> contexts = List.of(
                new MenuContext(new Canvas(800, 90)),
                new GameContext(new Canvas(800, 600))
        );

        ApplicationRunner runner = new ApplicationRunner(contexts);
        runner.start();
        primaryStage.setScene(runner.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
