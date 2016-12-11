package sample;

import gameScene.Game;
import gameScene.GameSceneController;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;
import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Controller {
    @FXML
    private Button button1;
    @FXML
    private TextField text1;

    @FXML
    public void onClickMethod(ActionEvent event) throws Exception{
        Client client = new Client(9007);
        JSONObject obj = new JSONObject();

        obj.put("event", Game.SET_NAME_EVENT);
        obj.put("name", text1.getCharacters().toString());

        Game.ownName = text1.getCharacters().toString();

        System.out.println(obj.toString());

        client.sendMail(obj.toString());

        GameSceneController gameSceneObj = new GameSceneController();
        gameSceneObj.show(event, client);
    }
}
