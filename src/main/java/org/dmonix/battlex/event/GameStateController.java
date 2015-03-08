/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dmonix.battlex.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Nerg
 */
public final class GameStateController {

    private static GameStateController instance;
    private final List<GameStateChangeListener> listeners = new ArrayList<>();
    private int state = -1;

    private GameStateController() {
        state = GameStates.STATE_IDLE;
    }

    // TODO Must get rid of this singleton
    @Deprecated
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