package client;

import gameScene.Game;

import java.io.*;

public class WorkerRunnable implements Runnable{

    InputStream servInput = null;
    OutputStream servOutput = null;

    Game game = null;

    public WorkerRunnable(Game game) {
        this.game = game;
    }

    public void run() {
        try {
            servInput = game.getClient().getClientSocket().getInputStream();
            servOutput = game.getClient().getClientSocket().getOutputStream();

            game.gameMainLoop();

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}