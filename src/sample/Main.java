package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.contexts.GameContext;
import sample.contexts.MenuContext;
import sample.resources.ImageLoader;
import sample.util.Constants;

import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        Scene scene = new Scene(root, Constants.CONTEXTS.GAME.WIDTH, Constants.CONTEXTS.GAME.HEIGHT);

        ApplicationRunner runner = new ApplicationRunner(scene,
                List.of(
                        new MenuContext(controller.menu),
                        new GameContext(
                                controller.game,
                                Constants.CONTEXTS.MENU.HEIGHT
                        )
                )
        );
        runner.start();

        primaryStage.setTitle(Constants.GAME.TITLE);
        primaryStage.getIcons().add(ImageLoader.getImage("SolemnManIcon"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
