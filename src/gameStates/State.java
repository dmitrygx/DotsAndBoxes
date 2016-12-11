package gameStates;

/**
 * Created by Дмитрий on 12/4/2016.
 */
public abstract class State {

    public void recvNewState(short state) {
        return;
    }

    public void recvAck(short result) {
        return;
    }
}
