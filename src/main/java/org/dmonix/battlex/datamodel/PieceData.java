/**
 *  Copyright 2015 Peter Nerg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dmonix.battlex.datamodel;

import java.util.HashMap;
import java.util.Map;

/**
 * Holder for the piece types of the game
 * 
 * @author Peter Nerg
 */
public final class PieceData {
    public static final String PIECE_NO_PIECE = "NO_PIECE";// ????
    public static final String PIECE_EMPTY_TYPE = "empty";
    public static final String PIECE_BOMB_TYPE = "bomb";
    public static final String PIECE_MARSHAL_TYPE = "marshal";
    public static final String PIECE_GENERAL_TYPE = "general";
    public static final String PIECE_COLONEL_TYPE = "colonel";
    public static final String PIECE_MAJOR_TYPE = "major";
    public static final String PIECE_CAPTAIN_TYPE = "captain";
    public static final String PIECE_LIEUTENANT_TYPE = "lieutenant";
    public static final String PIECE_SERGEANT_TYPE = "sergeant";
    public static final String PIECE_MINER_TYPE = "miner";
    public static final String PIECE_SCOUT_TYPE = "scout";
    public static final String PIECE_SPY_TYPE = "spy";
    public static final String PIECE_FLAG_TYPE = "flag";

    private static final Map<String, Integer> pieceCount = new HashMap<>();
    private static final Map<String, Integer> pieceStrength = new HashMap<>();

    static {
        // pre-load type to strength values
        // lower value -> lower strength
        pieceStrength.put(PIECE_FLAG_TYPE, 0);
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

        // pre-load type to count values
        pieceCount.put(PIECE_SPY_TYPE, 1);
        pieceCount.put(PIECE_SCOUT_TYPE, 8);
        pieceCount.put(PIECE_MINER_TYPE, 5);
        pieceCount.put(PIECE_SERGEANT_TYPE, 4);
        pieceCount.put(PIECE_LIEUTENANT_TYPE, 4);
        pieceCount.put(PIECE_CAPTAIN_TYPE, 4);
        pieceCount.put(PIECE_MAJOR_TYPE, 3);
        pieceCount.put(PIECE_COLONEL_TYPE, 2);
        pieceCount.put(PIECE_GENERAL_TYPE, 1);
        pieceCount.put(PIECE_MARSHAL_TYPE, 1);
        pieceCount.put(PIECE_BOMB_TYPE, 6);
        pieceCount.put(PIECE_FLAG_TYPE, 1);
    }

    /**
     * Inhibitive constructor.
     */
    private PieceData() {
    }

    /**
     * Get the allowed move distance (in squares) for a piece type.
     * 
     * @param pieceType
     * @return
     */
    public static int getMoveDistance(String pieceType) {
        int moveDistance;
        if (pieceType == PIECE_BOMB_TYPE || pieceType == PIECE_FLAG_TYPE) {
            moveDistance = 0;
        } else if (pieceType == PIECE_SCOUT_TYPE) {
            moveDistance = 10;
        } else {
            moveDistance = 1;
        }
        return moveDistance;
    }

    /**
     * Get the attack strength for a piece type.
     * 
     * @param pieceType
     * @return
     */
    public static int getPieceStrength(String pieceType) {
        return pieceStrength.get(pieceType);
    }

    /**
     * Get the total count (per player) a piece type.
     * 
     * @param pieceType
     * @return
     */
    public static int getPieceCount(String pieceType) {
        return pieceCount.get(pieceType);
    }

    /**
     * Get the total count (per player) of pieces.
     * 
     * @return
     */
    public static int getTotalPieceCount() {
        int count = 0;
        for (int i : pieceCount.values()) {
            count += i;
        }
        return count;
    }
}
