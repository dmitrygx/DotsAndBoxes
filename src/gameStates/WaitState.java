package gameStates;

import gameScene.DataUpdater;
import gameScene.Game;
import gameScene.SharedData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import sample.Main;

/**
 * Created by Дмитрий on 12/8/2016.
 */
public class WaitState extends State implements DataUpdater {

    SharedData data;

    public WaitState() {
        setData(Main.sharedData);
    }

    @Override
    public void setData(SharedData data) {
        this.data = data;
    }

    @Override
    public void recvNewState(short state) {
        Main.game.setCurrentState(state);
        if (state == Game.ACTION) {
            updateGameStateOnAction();
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
}
