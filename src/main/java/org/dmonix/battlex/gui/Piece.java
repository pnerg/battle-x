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
package org.dmonix.battlex.gui;

import java.awt.Image;

import org.dmonix.battlex.resources.Resources;

/**
 * @author Peter Nerg
 * @version 1.0
 */
public class Piece {
    public static final int RESULT_WIN = 1;
    public static final int RESULT_DRAW = 0;
    public static final int RESULT_LOOSE = -1;
    public static final int RESULT_WIN_GAME = 69;

    private final int moveDistance;
    private final Image image;
    private final int player;
    private final String type;
    private int x_coord, y_coord;

    public Piece(int player, String type, int x_coord, int y_coord) {
        this.player = player;
        this.type = type;
        this.x_coord = x_coord;
        this.y_coord = y_coord;

        if (type == Resources.PIECE_BOMB_TYPE || type == Resources.PIECE_FLAG_TYPE)
            moveDistance = 0;
        else if (type == Resources.PIECE_SCOUT_TYPE)
            moveDistance = 10;
        else
            moveDistance = 1;

        image = Resources.getImage(player, type);
    }

    /**
     * Returns the maximum distance in squares that the piece can move.
     * 
     * @return The distance
     */
    public int getMoveDistance() {
        return moveDistance;
    }

    public int getXCoord() {
        return x_coord;
    }

    public int getYCoord() {
        return y_coord;
    }

    /**
     * Set th elcoation of the piece
     * 
     * @param x_coord
     *            new x-coord
     * @param y_coord
     *            new y-coord
     */
    public void setLocation(int x_coord, int y_coord) {
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    /**
     * Return which player the piece belongs to.
     * 
     * @return
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Returns the image for the piece.
     * 
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Get the image for the piece.
     * 
     * @param currentPlayer
     * @return
     */
    public Image getImage(int currentPlayer) {
        if (currentPlayer == this.player)
            return image;
        else
            return Resources.getImage(this.player, Resources.PIECE_EMPTY_TYPE);
    }

    public String getType() {
        return this.type;
    }

    @Deprecated
    public void destroy() {
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Player = " + player + "\n");
        sb.append("Position = " + x_coord + ":" + y_coord + "\n");
        sb.append("Type = " + type);
        return sb.toString();
    }

}