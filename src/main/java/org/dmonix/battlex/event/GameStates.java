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

/**
 * @author Peter Nerg
 * 
 */
public final class GameStates {
    public static final int STATE_IDLE = 0;
    public static final int STATE_CONNECTING = 100;
    public static final int STATE_GAME_SETUP = 200;
    public static final int STATE_GAME_SETUP_RECEIVED_SETUP = 210;
    public static final int STATE_SETUP_WAIT_OPPONENT_SETUP = 220;
    public static final int STATE_IN_GAME = 300;
    public static final int STATE_IN_GAME_PLAYER_TURN = 310;
    public static final int STATE_IN_GAME_OPPONENT_TURN = 320;

    private GameStates() {
    }
}
