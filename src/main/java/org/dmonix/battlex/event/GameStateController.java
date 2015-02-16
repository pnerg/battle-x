package org.dmonix.battlex.event;

import java.util.Vector;

/**
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @version 1.0
 */
public final class GameStateController {
    public static final int STATE_IDLE = 0;
    public static final int STATE_CONNECTING = 100;
    public static final int STATE_GAME_SETUP = 200;
    public static final int STATE_GAME_SETUP_RECEIVED_SETUP = 210;
    public static final int STATE_SETUP_WAIT_OPPONENT_SETUP = 220;
    public static final int STATE_IN_GAME = 300;
    public static final int STATE_IN_GAME_PLAYER_TURN = 310;
    public static final int STATE_IN_GAME_OPPONENT_TURN = 320;

    private static GameStateController instance;
    private int state = -1;
    private Vector listeners = new Vector();

    private GameStateController() {
        state = STATE_IDLE;
    }

    public synchronized static GameStateController getInstance() {
        if (instance == null)
            instance = new GameStateController();

        return instance;
    }

    public boolean inState(int checkState) {
        return instance.state == checkState;
    }

    public int getState() {
        return state;
    }

    public synchronized void setState(int newState) {
        int oldState = state;
        state = newState;
        for (int i = 0; i < this.listeners.size(); i++) {
            ((GameStateChangeListener) this.listeners.elementAt(i)).stateChanged(oldState, newState);
        }
    }

    /**
     * Add a state change listener
     * 
     * @param listener
     *            the listener
     */
    public void addStateChangeListener(GameStateChangeListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Remove a state change listener
     * 
     * @param listener
     *            the listener
     */
    public void removeStateChangeListener(GameStateChangeListener listener) {
        this.listeners.remove(listener);
    }

}