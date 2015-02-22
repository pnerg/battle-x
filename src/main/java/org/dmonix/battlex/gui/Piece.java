package org.dmonix.battlex.gui;

import java.awt.Image;
import java.awt.Point;

import org.dmonix.battlex.resources.Resources;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
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
public class Piece {
    public static int RESULT_WIN = 1;
    public static int RESULT_DRAW = 0;
    public static int RESULT_LOOSE = -1;
    public static int RESULT_WIN_GAME = 69;

    private final int moveDistance;
    private final Image image;
    private final int player, type;
    private final Point coord;

    public Piece(int player, int type, int x_coord, int y_coord) {
        this.player = player;
        this.type = type;
        coord = new Point(x_coord, y_coord);

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

    /**
     * Get the location of the piece.
     * 
     * @return
     */
    public Point getLocation() {
        return coord;
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
        this.coord.setLocation(x_coord, y_coord);
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
     * Returns the empty (backside) image for the piece.
     * 
     * @return the image
     */
    // public Image getEmptyImage()
    // {
    // return emptyImage;
    // }

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

    public int getType() {
        return this.type;
    }

    /**
     * Resolve the outcome of the strike.
     * 
     * @param defender
     *            defending piece
     * @return -1 defender wins, 0 draw, 1 attacker wins
     */
    public int resolveStrike(Piece defender) {
        int defenderType = defender.getType();

        // equal pieces is a draw
        if (this.type == defenderType)
            return RESULT_DRAW;

        // the flag always looses
        if (defenderType == Resources.PIECE_FLAG_TYPE)
            return RESULT_WIN_GAME;

        // the bomb wins over everything except the miner
        if (this.type == Resources.PIECE_MINER_TYPE && defenderType == Resources.PIECE_BOMB_TYPE)
            return RESULT_WIN;

        // the spy wins over the marshal only if the spy strikes
        if (this.type == Resources.PIECE_SPY_TYPE && defenderType == Resources.PIECE_MARSHAL_TYPE)
            return RESULT_WIN;

        // determine the winner based on the value of the type
        if (this.type < defenderType)
            return RESULT_WIN;
        else
            return RESULT_LOOSE;
    }

    @Deprecated
    public void destroy() {
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Player = " + player + "\n");
        sb.append("Position = " + coord.x + ":" + coord.y + "\n");
        sb.append("Type = " + Resources.getPieceName(type));
        return sb.toString();
    }

}