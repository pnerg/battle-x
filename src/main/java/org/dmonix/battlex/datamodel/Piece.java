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

/**
 * @author Peter Nerg
 * @version 1.0
 */
public final class Piece {
    private final int player;
    private final String type;

    /** The location of the piece on the board */
    private Square square;

    public Piece(int player, String type, Square square) {
        this.player = player;
        this.type = type;
        this.square = square.absolute();
    }

    /**
     * Returns the maximum distance in squares that the piece can move.
     * 
     * @return The distance
     */
    public int getMoveDistance() {
        return PieceData.getMoveDistance(type);
    }

    /**
     * Get the absolute square for this Piece.
     * 
     * @return
     */
    public Square getSquare() {
        return square.absolute();
    }

    /**
     * Set the location of the piece
     */
    public void setLocation(Square square) {
        this.square = square.absolute();
    }

    public int getPieceStrength() {
        return PieceData.getPieceStrength(type);
    }

    /**
     * Return which player the piece belongs to.
     * 
     * @return
     */
    public int getPlayer() {
        return player;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Square pos = getSquare();
        sb.append("Player = " + player);
        sb.append(":Position = [" + pos.x() + "][" + pos.y() + "]");
        sb.append(":Type = " + type);
        return sb.toString();
    }
}