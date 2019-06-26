package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.entities.HorizontalWall;
import sample.entities.MessagePane;
import sample.entities.VerticalWall;
import sample.util.Constants;
import sample.util.Point;

import java.util.List;
import java.util.function.Consumer;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(Constants.GAME.TITLE);
        primaryStage.getIcons().add(Manager.getImage("SolemnManIcon"));
        VBox pane = new VBox();
        Canvas menu = new Canvas(800, 90);
        // menu.getGraphicsContext2D().strokeRect(0, 0, 10, 10);
        Consumer<HorizontalWall> hzConsumer = wall -> wall.draw(menu.getGraphicsContext2D());
        Consumer<VerticalWall> vwConsumer = wall -> wall.draw(menu.getGraphicsContext2D());
        HorizontalWall.Walls(2, 0, 44).forEach(hzConsumer);
        VerticalWall.Walls(800 - 6, 0, 30).forEach(vwConsumer);
        VerticalWall.Walls(1, 0, 4).forEach(vwConsumer);
        HorizontalWall.Walls(1, 84, 44).forEach(hzConsumer);
        Canvas game = new Canvas(800, 500);
        game.getGraphicsContext2D().strokeRect(0, 0, 10, 10);
        // Add in reverse order to stackpane...
        ObservableList<Node> children = pane.getChildren();
        children.add(menu);
        children.add(game);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        /*
        Scene scene = Manager.getScene();
        primaryStage.setScene(scene);
        */
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
