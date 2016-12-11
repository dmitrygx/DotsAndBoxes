package gameScene;

import client.WorkerRunnable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable, DataUpdater {

    Client client = null;

    SharedData data;

    @FXML
    private Label label1;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData(Main.sharedData);
    }

    Parent gameParent = null;

    public void show(ActionEvent event, Client client) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameScene/gameScene.fxml"));
        gameParent = fxmlLoader.load(getClass().getResource("/gameScene/gameScene.fxml"));

        this.client = client;
        Scene gameScene = new Scene(gameParent);
        Stage applicationStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        applicationStage.setScene(gameScene);
        applicationStage.show();

        Main.gameScene = gameScene;

        fxmlLoader.setController(this);

        Main.game = new Game(client);
        Main.game.setData(Main.sharedData);
        Main.gameSceneLoader = fxmlLoader;

        new Thread(new WorkerRunnable(Main.game)).start();
    }

    @Override
    public void setData(SharedData data) {
        this.data = data;
        label1.setText(data.getStringStateProp().get());
        data.getStringStateProp()
                .addListener((ObservableValue<? extends String> observable,
                              String oldValue, String newValue) -> {
                    label1.setText(newValue);
                });

        data.getStringLineProp()
                .addListener((ObservableValue<? extends String> observable,
                              String oldValue, String newValue) -> {
                    String array[] = newValue.split("-");
                    Color color = Integer.parseInt(array[1]) == 0 ? Color.RED : Color.BLUE ;

                    System.out.println(newValue + ", also know as " + array[0] + " and " + array[1]);
                    Line line = (Line) Main.gameScene.lookup("#" + array[0]);
                    line.setStroke(color);
                });

        data.getStringRectProp()
                .addListener((ObservableValue<? extends String> observable,
                              String oldValue, String newValue) -> {
                    String array[] = newValue.split("-");
                    Color color = Integer.parseInt(array[1]) == 0 ? Color.RED : Color.BLUE ;

                    System.out.println(newValue + ", also know as " + array[0] + " and " + array[1]);
                    Rectangle rect = (Rectangle) Main.gameScene.lookup("#" + array[0]);

                    rect.setFill(color);
                });

        data.getStringLabelColorProp()
                .addListener((ObservableValue<? extends String> observable,
                              String oldValue, String newValue) -> {
                    String array[] = newValue.split("-");

                    labelPlayer1.setText(array[0]);
                    labelPlayer2.setText(array[1]);
                });
    }

    @FXML
    public void onMouseClick(MouseEvent event) throws Exception {
        JSONObject action = new JSONObject();

        action.put("event", Game.PERFORM_ACTION);
        action.put("line", ((Line)event.getSource()).getId());
        Main.game.getClient().sendMail(action.toString());


        System.out.println("This line id is - " + ((Line)event.getSource()).getId());
    }
}

