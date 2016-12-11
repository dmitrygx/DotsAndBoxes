package gameStates;

import gameScene.DataUpdater;
import gameScene.Game;
import gameScene.SharedData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import sample.Main;

public class WaitForGameState extends State implements DataUpdater {

    SharedData data;

    public WaitForGameState() {
        setData(Main.sharedData);
    }

    @Override
    public void recvNewState(short state) {
        Main.game.setCurrentState(state);
        if (state == Game.ACTION) {
            updateGameStateOnAction();
        } else if (state == Game.WAIT) {
            updateGameStateOnWait();
        }
    }

    private void updateGameStateOnAction() {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        data.getStringStateProp().set(Game.getOwnName() + ", Your move!");
                    }
                });
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    private void updateGameStateOnWait() {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        data.getStringStateProp().set("Wait move of " + Game.getEnemyName());
                    }
                });
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void recvAck(short result) {
        super.recvAck(result);
    }

    @Override
    public void setData(SharedData data) {
        this.data = data;
    }
}
