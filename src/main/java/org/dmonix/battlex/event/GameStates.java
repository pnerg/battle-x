/* 
 * Created : Feb 22, 2015
 * 
 * Copyright (c) 2015 Ericsson AB, Sweden. 
 * All rights reserved. 
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden. 
 * The program(s) may be used and/or copied with the written permission from Ericsson AB 
 * or in accordance with the terms and conditions stipulated in the agreement/contract 
 * under which the program(s) have been supplied. 
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
