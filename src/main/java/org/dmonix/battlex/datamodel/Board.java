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

package org.dmonix.battlex.datamodel;

import static org.dmonix.battlex.resources.Resources.PIECE_BOMB_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_CAPTAIN_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_COLONEL_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_FLAG_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_GENERAL_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_LIEUTENANT_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_MAJOR_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_MARSHAL_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_MINER_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_SCOUT_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_SERGEANT_TYPE;
import static org.dmonix.battlex.resources.Resources.PIECE_SPY_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dmonix.battlex.gui.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual board and all the pieces on it.
 * 
 * @author Peter Nerg
 * 
 */
public class Board {
    public static final int RESULT_WIN = 1;
    public static final int RESULT_DRAW = 0;
    public static final int RESULT_LOOSE = -1;
    public static final int RESULT_MOVE_NO_BATTLE = 100;
    public static final int RESULT_WIN_GAME = 69;

    private static final Logger logger = LoggerFactory.getLogger(Board.class);
    private final Piece[][] pieces = new Piece[10][10];
    private final Map<String, Integer> pieceStrength = new HashMap<>();

    public Board() {
        // pre-load type to strength values
        // lower value -> lower strength
        pieceStrength.put(PIECE_SPY_TYPE, 1);
        pieceStrength.put(PIECE_SCOUT_TYPE, 2);
        pieceStrength.put(PIECE_MINER_TYPE, 3);
        pieceStrength.put(PIECE_SERGEANT_TYPE, 4);
        pieceStrength.put(PIECE_LIEUTENANT_TYPE, 5);
        pieceStrength.put(PIECE_CAPTAIN_TYPE, 6);
        pieceStrength.put(PIECE_MAJOR_TYPE, 7);
        pieceStrength.put(PIECE_COLONEL_TYPE, 8);
        pieceStrength.put(PIECE_GENERAL_TYPE, 9);
        pieceStrength.put(PIECE_MARSHAL_TYPE, 10);
        pieceStrength.put(PIECE_BOMB_TYPE, 100);
    }

    void clearAllPlayerPieces(int player) {
        // for (int y = 6; y < 10; y++) {
        // for (int x = 0; x < 10; x++) {
        // if (pieces[x][y] != null && pieces[x][y].getPlayer() == player) {
        // owner.addPiece(player, pieces[x][y].getType());
        // pieces[x][y] = null;
        // markedSquares[x][y] = new Object();
        // eventCommunicator.sendEvent(new GameEventObject(Resources.PIECE_NO_PIECE, x, y));
        // }
        // }
        // }
    }

    public Piece getPiece(int x, int y) {
        return pieces[x][y];
    }

    public void addPiece(Piece piece) {
        logger.debug("Adding piece to board [{}]", piece);
        pieces[piece.getXCoord()][piece.getYCoord()] = piece;
    }

    public void removePiece(int x, int y) {
        logger.debug("Removing piece piece from board x[{}] y[{}]", x, y);
        pieces[x][y] = null;
    }

    public boolean isEmpty(int x, int y) {
        return pieces[x][y] != null;
    }

    public boolean isPlayerPiece(int player, int x, int y) {
        Piece piece = pieces[x][y];
        return piece != null ? piece.getPlayer() == player : false;
    }

    // TODO implement
    public boolean[][] getAllowedMoves(Piece piece) {
        boolean[][] allowedMoves = new boolean[9][9];

        return allowedMoves;
    }

    /**
     * Move a piece.
     * 
     * @param attacker
     * @param defender
     */
    public int movePiece(final Piece attacker, final int x_coord_defender, final int y_coord_defender) {
        final int x_coord_attacker = attacker.getXCoord();
        final int y_coord_attacker = attacker.getYCoord();

        logger.debug("Resolve movement [{}] to x[{}] y[{}]", attacker, x_coord_defender, y_coord_defender);

        Piece defender = pieces[x_coord_defender][y_coord_defender];
        int result = resolveStrike(attacker, defender);

        /**
         * empty space, go ahead and move the piece to that location
         */
        if (result == RESULT_MOVE_NO_BATTLE || result == RESULT_WIN) {
            logger.debug("Piece [{}] won against [{}]", attacker, defender);
            pieces[x_coord_defender][y_coord_defender] = attacker; // move attacker to defender square
            pieces[x_coord_defender][y_coord_defender].setLocation(x_coord_defender, y_coord_defender); // set new location to attacker piece
        }

        // draw
        else if (result == RESULT_DRAW) {
            logger.debug("Piece [{}] draw against [{}]", attacker, defender);
            removePiece(x_coord_defender, y_coord_defender); // remove the defender as it lost/tied
        }

        // remove the old position for the piece that moved
        removePiece(x_coord_attacker, y_coord_attacker); // empty space where attacker moved from

        return result;
    }

    public List<Piece> getPieces() {
        List<Piece> pieceList = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (pieces[x][y] != null) {
                    pieceList.add(pieces[x][y]);
                }
            }
        }
        return pieceList;
    }

    /**
     * Checks all pieces for a player to see if the player can move any of them
     * 
     * @param checkPlayer
     *            the player to check
     * @return
     */
    public boolean checkIfPlayerCanMove(int checkPlayer) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                // ignore empty squares, non-checkPlayer pieces and pieces that cannot move
                if (pieces[x][y] == null || pieces[x][y].getPlayer() != checkPlayer || pieces[x][y].getMoveDistance() < 1)
                    continue;

                // check up
                if (y > 0 && (pieces[x][y - 1] == null || pieces[x][y - 1].getPlayer() != checkPlayer))
                    return true;

                // check down
                if (y < 9 && (pieces[x][y + 1] == null || pieces[x][y + 1].getPlayer() != checkPlayer))
                    return true;

                // check left
                if (x > 0 && (pieces[x - 1][y] == null || pieces[x - 1][y].getPlayer() != checkPlayer))
                    return true;

                // check right
                if (x < 9 && (pieces[x + 1][y] == null || pieces[x + 1][y].getPlayer() != checkPlayer))
                    return true;

            }
        }

        return false;
    }

    private int resolveStrike(Piece attacker, Piece defender) {
        int result = 0;
        // moving to empty square
        if (defender == null) {
            result = RESULT_MOVE_NO_BATTLE;
        } else {
            String atkType = attacker.getType();
            String defType = defender.getType();
            int atkStrength = pieceStrength.get(atkType);
            int defStrength = pieceStrength.get(defType);

            // the flag always looses
            if (defType == PIECE_FLAG_TYPE) {
                result = RESULT_WIN_GAME;
            }
            // the bomb wins over everything except the miner
            else if (atkType == PIECE_MINER_TYPE && defType == PIECE_BOMB_TYPE) {
                result = RESULT_WIN;
            }

            // the spy wins over the marshal only if the spy strikes
            else if (atkType == PIECE_SPY_TYPE && defType == PIECE_MARSHAL_TYPE) {
                result = RESULT_WIN;
            }

            // determine the winner based on the value of the type
            else if (atkStrength > defStrength) {
                result = RESULT_WIN;
            }
            // equal pieces is a draw
            else if (atkStrength == defStrength)
                result = RESULT_DRAW;
            else
                result = RESULT_LOOSE;
        }
        logger.debug("Resolving strike [{}][{}] = [{}]", attacker, defender, result);
        return result;
    }
}
