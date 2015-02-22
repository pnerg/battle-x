package org.dmonix.battlex.event;

import java.util.ArrayList;
import java.util.List;

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

    private static GameStateController instance;
    private final List<GameStateChangeListener> listeners = new ArrayList<>();
    private int state = -1;

    private GameStateController() {
        state = GameStates.STATE_IDLE;
    }

    public synchronized static GameStateController getInstance() {
        if (instance == null) {
            instance = new GameStateController();
        }

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
        for (GameStateChangeListener listener : listeners) {
            listener.stateChanged(oldState, newState);
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