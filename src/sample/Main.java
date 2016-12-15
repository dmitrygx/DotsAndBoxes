package sample;

import gameScene.Game;
import gameScene.GameSceneController;
import gameScene.SharedData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    public static Game game;

    public static FXMLLoader gameSceneLoader = null;

    public static SharedData sharedData = new SharedData();

    public static Scene gameScene = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent gameParent = fxmlLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Dots and Boxes");

        Scene scene = new Scene(gameParent);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
