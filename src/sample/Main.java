package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.util.Constants;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(Constants.GAME.TITLE);
        primaryStage.getIcons().add(Manager.getImage("SolemnManIcon.png"));
        Scene scene = Manager.getScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
