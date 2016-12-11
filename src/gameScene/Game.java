package gameScene;

import client.Client;
import gameStates.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sample.Main;

public class Game implements DataUpdater {

    static public String ownName = null;
    static public String enemyName = null;

    Client client;

    SharedData data;

    public static final short INITIAL = 0;
    public static final short WAIT_FOR_GAME = 1;
    public static final short ACTION = 2;
    public static final short WAIT = 3;
    public static final short END = 4;


    public static final short CHANGE_STATE = 0;
    public static final short CONFIRMATION = 1;
    public static final short UPDATE = 2;

    public static final short SET_NAME_EVENT = 0;
    public static final short PERFORM_ACTION = 1;

    private short currentState;

    public short getCurrentState() {
        return currentState;
    }

    public void setCurrentState(short currentState) {
        this.currentState = currentState;
    }

    public State[] getStates() {
        return states;
    }

    public void setStates(State[] states) {
        this.states = states;
    }

    private State[] states;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Game() {

    }

    public Game(Client client) {
        this.client = client;

        states = new State[]{
                new InitialState(),
                new WaitForGameState(),
                new ActionState(),
                new WaitState()
        };
        currentState = INITIAL;
    }

    static public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    static public String getOwnName() {
        return ownName;
    }

    public void setOwnName(String ownName) {
        this.ownName = ownName;
    }

    @Override
    public void setData(SharedData data) {
        this.data = data;
    }

    public void gameMainLoop() {

        while(true) {
            JSONParser parser = new JSONParser();
            String receivedMsg = client.recvMail();

            try {
                Object obj = parser.parse(receivedMsg);
                JSONObject jsonObj = (JSONObject) obj;
                long event = (Long) jsonObj.get("event");
                int intEvent = (int) event;

                switch (intEvent)
                {
                    case CHANGE_STATE:
                    {
                        long state = (Long)jsonObj.get("state");

                        if ((state == ACTION) || (state == WAIT)) {
                            setEnemyName((String)jsonObj.get("name_of_enemy"));
                        }

                        states[getCurrentState()].recvNewState((short)state);

                        break;
                    }
                    case CONFIRMATION:
                    {
                        long result = (Long)jsonObj.get("result");
                        String line = (String)jsonObj.get("line");
                        long color = (Long)jsonObj.get("color");

                        if (result == 1) {
                            updateLine(line, color);
                        }

                        break;
                    }
                    case UPDATE:
                    {
                        String line = (String)jsonObj.get("line");
                        long color = (Long)jsonObj.get("color");

                        updateLine(line, color);

                        break;
                    }
                }

                System.out.println(receivedMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateLine(String line, long color) {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        data.getStringLineProp().set(line + "-" + color);
                    }
                });
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
