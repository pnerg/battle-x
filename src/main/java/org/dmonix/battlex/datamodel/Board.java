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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The actual board and all the pieces on it.<br>
 * This contains all pieces placed on the board according to an absolute coordinate system.<br>
 * Player one is always on row 0 (Y) and player 2 is always on row 9 (Y).<br>
 * So in practice the lower left corner (when facing the board) for player 1 is 0/0 and for player 2 it is 9/9.
 * 
 * <pre>
 * <i>X/Y</i>
 *               <i>Player2</i>
 * 9 --- --- --- --- --- --- --- --- --- --- 
 * 8 --- --- --- --- --- --- --- --- --- --- 
 * 7 --- --- --- --- --- --- --- --- --- --- 
 * 6 --- --- --- --- --- --- --- --- --- --- 
 * 5 --- --- XXX XXX --- --- XXX XXX --- --- 
 * 4 --- --- XXX XXX --- --- XXX XXX --- --- 
 * 3 --- --- --- --- --- --- --- --- --- --- 
 * 2 --- --- --- --- --- --- --- --- --- --- 
 * 1 --- --- --- --- --- --- --- --- --- --- 
 * 0 --- --- --- --- --- --- --- --- --- --- 
 *    0   1   2   3   4   5   6   7   8   9  
 *               <i>Player1</i>
 * </pre>
 * 
 * @author Peter Nerg
 * 
 */
public final class Board {
    public static final int RESULT_WIN = 1;
    public static final int RESULT_DRAW = 0;
    public static final int RESULT_LOOSE = -1;
    public static final int RESULT_MOVE_NO_BATTLE = 100;
    public static final int RESULT_WIN_GAME = 69;

    private static final Logger logger = LoggerFactory.getLogger(Board.class);

    /** Matrix representing all 10x10 positions on the board. */
    private final Position[][] positions = new Position[10][10];

    /** Used to mark an empty position. */
    private static final EmptyPosition EmptyPosition = new EmptyPosition();

    /** Used to mark an illegal position, that is the water squares in the middle. */
    private static final InvalidPosition InvalidPosition = new InvalidPosition();

    public Board() {
        for (int y = 9; y >= 0; y--) {
            for (int x = 0; x < 10; x++) {
                positions[x][y] = EmptyPosition;
            }
        }
        // mark the left lake as invalid
        positions[2][4] = InvalidPosition;
        positions[2][5] = InvalidPosition;
        positions[3][4] = InvalidPosition;
        positions[3][5] = InvalidPosition;

        // mark the right lake as invalid
        positions[6][4] = InvalidPosition;
        positions[6][5] = InvalidPosition;
        positions[7][4] = InvalidPosition;
        positions[7][5] = InvalidPosition;
    }

    void clearAllPlayerPieces(int player) {
        for (Piece piece : getPiecesForPlayer(player)) {
            emptySquare(piece.getSquare());
        }
    }

    public Piece getPiece(Square square) {
        Square absolute = square.getAbsolute();
        return positions[absolute.getX()][absolute.getY()].getPiece();
    }

    public void addPiece(Piece piece) {
        logger.debug("Adding piece to board [{}]", piece);
        Square absolute = piece.getSquare();
        positions[absolute.getX()][absolute.getY()] = new PiecePosition(piece);
    }

    public void removePiece(Piece piece) {
        logger.debug("Removing piece from board [{}]", piece);
        emptySquare(piece.getSquare());
    }

    // public void removePiece(Square square) {
    // int x = square.getAbsolute().getX();
    // int y = square.getAbsolute().getY();
    // logger.debug("Removing piece piece from board x[{}] y[{}]", x, y);
    // pieces[x][y] = null;
    // }

    public boolean isEmpty(Square square) {
        Square abs = square.getAbsolute();
        return positions[abs.getX()][abs.getY()] == EmptyPosition;
    }

    public boolean isPlayerPiece(int player, Square square) {
        Piece piece = getPiece(square);
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
    public int movePiece(final Piece attacker, Square target) {
        final int x_coord_defender = target.getAbsolute().getX();
        final int y_coord_defender = target.getAbsolute().getY();
        final Square squareAttacker = attacker.getSquare();

        logger.debug("Resolve movement [{}] to x[{}]", attacker, target);

        Piece defender = positions[x_coord_defender][y_coord_defender].getPiece();
        int result = resolveStrike(attacker, defender);

        /**
         * empty space, go ahead and move the piece to that location
         */
        if (result == RESULT_MOVE_NO_BATTLE || result == RESULT_WIN) {
            logger.debug("Piece [{}] won against [{}]", attacker, defender);
            attacker.setLocation(SquareFactory.createAbsolute(x_coord_defender, y_coord_defender)); // set new location to
            addPiece(attacker);// move attacker to defender square
        }

        // draw
        else if (result == RESULT_DRAW) {
            logger.debug("Piece [{}] draw against [{}]", attacker, defender);
            removePiece(getPiece(target)); // remove the defender as it lost/tied
        }

        // empty the old position for the piece that moved
        emptySquare(squareAttacker);

        return result;
    }

    private void emptySquare(Square square) {
        Square abs = square.getAbsolute();
        logger.debug("Marking square [{}] as empty", square);
        positions[abs.getX()][abs.getY()] = EmptyPosition;
    }

    public List<Piece> getPieces() {
        List<Piece> pieceList = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Position position = positions[x][y];
                if (position.containsPiece()) {
                    pieceList.add(position.getPiece());
                }
            }
        }
        return pieceList;
    }

    public List<Piece> getPiecesForPlayer(int player) {
        List<Piece> pieceList = new ArrayList<>();
        for (Piece piece : getPieces()) {
            if (piece.getPlayer() == player) {
                pieceList.add(piece);
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
        // get all pieces
        for (Piece piece : getPieces()) {
            // any piece for the player that can move counts
            if (piece.getPlayer() == checkPlayer && canPieceMove(piece)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the provided Piece can move in any direction at all.
     * 
     * @param piece
     * @return
     */
    private boolean canPieceMove(Piece piece) {
        // TODO implement
        // for (int y = 0; y < 10; y++) {
        // for (int x = 0; x < 10; x++) {
        // Position position = pieces[x][y];
        // // ignore empty squares, non-checkPlayer pieces and pieces that cannot move
        // if (pieces[x][y] == null || pieces[x][y].getPlayer() != checkPlayer || pieces[x][y].getMoveDistance() < 1)
        // continue;
        //
        // // check up
        // if (y > 0 && (pieces[x][y - 1] == null || pieces[x][y - 1].getPlayer() != checkPlayer))
        // return true;
        //
        // // check down
        // if (y < 9 && (pieces[x][y + 1] == null || pieces[x][y + 1].getPlayer() != checkPlayer))
        // return true;
        //
        // // check left
        // if (x > 0 && (pieces[x - 1][y] == null || pieces[x - 1][y].getPlayer() != checkPlayer))
        // return true;
        //
        // // check right
        // if (x < 9 && (pieces[x + 1][y] == null || pieces[x + 1][y].getPlayer() != checkPlayer))
        // return true;
        //
        // }
        // }
        //

        return false;
    }

    private static int resolveStrike(Piece attacker, Piece defender) {
        int result = 0;
        // moving to empty square
        if (defender == null) {
            result = RESULT_MOVE_NO_BATTLE;
        } else {
            String atkType = attacker.getType();
            String defType = defender.getType();
            int atkStrength = attacker.getPieceStrength();
            int defStrength = defender.getPieceStrength();

            // the flag always looses
            if (defType == PieceData.PIECE_FLAG_TYPE) {
                result = RESULT_WIN_GAME;
            }
            // the bomb wins over everything except the miner
            else if (atkType == PieceData.PIECE_MINER_TYPE && defType == PieceData.PIECE_BOMB_TYPE) {
                result = RESULT_WIN;
            }

            // the spy wins over the marshal only if the spy strikes
            else if (atkType == PieceData.PIECE_SPY_TYPE && defType == PieceData.PIECE_MARSHAL_TYPE) {
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
        logger.debug("Resolving strike [{}] vs [{}] = [{}]", attacker, defender, result);
        return result;
    }

    /**
     * Creates a debug printout of the board.<br>
     * Each non-empty square is represented by the piece (two first letters) and which player it belongs to.<br>
     * Example:
     * 
     * <pre>
     * 9 --- --- --- --- --- --- --- --- bo2 fl1 
     * 8 --- --- --- --- --- --- --- --- bo2 bo2 
     * 7 --- --- --- bo2 ge2 --- --- --- --- --- 
     * 6 --- --- --- mi1 --- ca2 --- --- --- --- 
     * 5 --- --- --- --- --- sc2 --- --- --- --- 
     * 4 --- --- --- --- --- co1 --- --- --- --- 
     * 3 --- --- --- --- --- --- --- --- --- --- 
     * 2 --- --- --- --- sc1 --- --- --- --- --- 
     * 1 bo1 bo1 --- --- --- --- --- --- --- --- 
     * 0 fl1 bo1 --- --- --- --- --- --- --- --- 
     *    0   1   2   3   4   5   6   7   8   9
     * </pre>
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        // loop on the row, starting from the highest
        for (int y = 9; y >= 0; y--) {
            // loop on the column starting from left
            sb.append(y).append(" ");
            for (int x = 0; x < 10; x++) {
                Position position = positions[x][y];
                sb.append(position).append(" ");
            }
            sb.append("\n");
        }
        // print the X-coord system
        sb.append("   ");
        for (int i = 0; i < 10; i++) {
            sb.append(i).append("   ");
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * Represents a position on the board.
     * 
     * @author Peter Nerg
     * 
     */
    private interface Position {
        boolean isValid();

        boolean isEmpty();

        boolean containsPiece();

        Piece getPiece();
    }

    /**
     * Represents an empty position on the board.
     * 
     * @author Peter Nerg
     */
    private static final class EmptyPosition implements Position {

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean containsPiece() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString() {
            return "---";
        }

    }

    /**
     * Represents an invalid/water position on the board.
     * 
     * @author Peter Nerg
     */
    private static final class InvalidPosition implements Position {

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsPiece() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString() {
            return "XXX";
        }

    }

    /**
     * Represents a position on the board containing a {@link Piece}.
     * 
     * @author Peter Nerg
     */
    private static final class PiecePosition implements Position {
        private final Piece piece;

        private PiecePosition(Piece piece) {
            this.piece = piece;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsPiece() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return piece;
        }

        @Override
        public String toString() {
            return piece.getType().substring(0, 2) + piece.getPlayer();
        }
    }

}
