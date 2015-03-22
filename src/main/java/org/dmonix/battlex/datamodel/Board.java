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

    public Board() {
        for (int y = 9; y >= 0; y--) {
            for (int x = 0; x < 10; x++) {
                positions[x][y] = new EmptyPosition(SquareFactory.createAbsolute(x, y));
            }
        }
        // mark the left lake as invalid
        positions[2][4] = new InvalidPosition(SquareFactory.createAbsolute(2, 4));
        positions[2][5] = new InvalidPosition(SquareFactory.createAbsolute(2, 5));
        positions[3][4] = new InvalidPosition(SquareFactory.createAbsolute(3, 4));
        positions[3][5] = new InvalidPosition(SquareFactory.createAbsolute(3, 5));

        // mark the right lake as invalid
        positions[6][4] = new InvalidPosition(SquareFactory.createAbsolute(6, 4));
        positions[6][5] = new InvalidPosition(SquareFactory.createAbsolute(6, 5));
        positions[7][4] = new InvalidPosition(SquareFactory.createAbsolute(7, 4));
        positions[7][5] = new InvalidPosition(SquareFactory.createAbsolute(7, 5));
    }

    void clearAllPlayerPieces(int player) {
        for (Piece piece : getPiecesForPlayer(player)) {
            emptySquare(piece.getSquare());
        }
    }

    /**
     * Gets all the allowed moves the piece can do and verify if the requested target is amongst these
     * 
     * @param piece
     * @param target
     * @return
     */
    public boolean canMoveTo(Piece piece, Square target) {
        for (Square allowedSquare : getAllowedMoves(piece)) {
            if (allowedSquare.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public Piece getPiece(Square square) {
        Square absolute = square.getAbsolute();
        return positions[absolute.getX()][absolute.getY()].getPiece();
    }

    public void addPiece(Piece piece) {
        if (!isEmpty(piece.getSquare())) {
            throw new IllegalArgumentException("Cannot add piece [" + piece + "] to non-empty square [" + piece.getSquare() + "]");
        }

        logger.debug("Adding piece to board [{}]", piece);
        Square absolute = piece.getSquare();
        positions[absolute.getX()][absolute.getY()] = new PiecePosition(piece);
    }

    public void removePiece(Piece piece) {
        emptySquare(piece.getSquare());
    }

    public boolean isEmpty(Square square) {
        Square abs = square.getAbsolute();
        return positions[abs.getX()][abs.getY()].isEmpty();
    }

    /**
     * Returns <code>true</code> if the provided square contains a Piece belonging to the provided player, <code>false</code> otherwise.
     * 
     * @param player
     * @param square
     * @return
     */
    public boolean isPlayerPiece(int player, Square square) {
        Square abs = square.getAbsolute();
        Position position = positions[abs.getX()][abs.getY()];
        return position.containsPiece() ? position.getPiece().getPlayer() == player : false;
    }

    // TODO implement
    public List<Square> getAllowedMoves(Piece piece) {
        // bombs and flags can't move at all.
        List<Position> positionsRight = getAllPositionsRight(piece);
        List<Position> positionsLeft = getAllPositionsLeft(piece);
        List<Position> positionsUp = getAllPositionsAbove(piece);
        List<Position> positionsDown = getAllPositionsBelow(piece);
        logger.debug("Possible positions for [{}] left:[{}], right:[{}], up:[{}], down:[{}]", piece, positionsLeft.size(), positionsRight.size(),
                positionsUp.size(), positionsDown.size());

        List<Square> allowedMovesRight = getAllowedMoves(positionsRight, piece);
        List<Square> allowedMovesLeft = getAllowedMoves(positionsLeft, piece);
        List<Square> allowedMovesUp = getAllowedMoves(positionsUp, piece);
        List<Square> allowedMovesDown = getAllowedMoves(positionsDown, piece);

        logger.debug("Allowed positions for [{}] left:[{}], right:[{}], up:[{}], down:[{}]", piece, allowedMovesLeft.size(), allowedMovesRight.size(),
                allowedMovesUp.size(), allowedMovesDown.size());

        List<Square> allowedMoves = new ArrayList<>();
        allowedMoves.addAll(allowedMovesRight);
        allowedMoves.addAll(allowedMovesLeft);
        allowedMoves.addAll(allowedMovesUp);
        allowedMoves.addAll(allowedMovesDown);

        return allowedMoves;
    }

    /**
     * Get all the possible positions to the <tt>left</tt> of the provided piece.<br>
     * This includes all positions, even those not valid/water and occupied positions.
     * 
     * @param piece
     * @return
     */
    private List<Position> getAllPositionsLeft(Piece piece) {
        List<Position> possiblePositions = new ArrayList<>();
        int xPos = piece.getSquare().getAbsolute().getX();
        int yPos = piece.getSquare().getAbsolute().getY();
        for (int x = xPos - 1; x >= 0; x--) {
            possiblePositions.add(positions[x][yPos]);
        }
        return possiblePositions;
    }

    /**
     * Get all the possible positions to the <tt>right</tt> of the provided piece.<br>
     * This includes all positions, even those not valid/water and occupied positions.
     * 
     * @param piece
     * @return
     */
    private List<Position> getAllPositionsRight(Piece piece) {
        List<Position> possiblePositions = new ArrayList<>();
        int xPos = piece.getSquare().getAbsolute().getX();
        int yPos = piece.getSquare().getAbsolute().getY();
        for (int x = xPos + 1; x < 10; x++) {
            possiblePositions.add(positions[x][yPos]);
        }
        return possiblePositions;
    }

    /**
     * Get all the possible positions to the <tt>up</tt> of the provided piece.<br>
     * This includes all positions, even those not valid/water and occupied positions.
     * 
     * @param piece
     * @return
     */
    private List<Position> getAllPositionsAbove(Piece piece) {
        List<Position> possiblePositions = new ArrayList<>();
        int xPos = piece.getSquare().getAbsolute().getX();
        int yPos = piece.getSquare().getAbsolute().getY();
        for (int y = yPos + 1; y < 10; y++) {
            possiblePositions.add(positions[xPos][y]);
        }
        return possiblePositions;
    }

    /**
     * Get all the possible positions to the <tt>down</tt> of the provided piece.<br>
     * This includes all positions, even those not valid/water and occupied positions.
     * 
     * @param piece
     * @return
     */
    private List<Position> getAllPositionsBelow(Piece piece) {
        List<Position> possiblePositions = new ArrayList<>();
        int xPos = piece.getSquare().getAbsolute().getX();
        int yPos = piece.getSquare().getAbsolute().getY();
        for (int y = yPos - 1; y >= 0; y--) {
            possiblePositions.add(positions[xPos][y]);
        }
        return possiblePositions;
    }

    private List<Square> getAllowedMoves(List<Position> possibleMoves, Piece piece) {
        int moveDistance = piece.getMoveDistance();
        if (moveDistance == 0) {
            return new ArrayList<>();
        }
        int player = piece.getPlayer();

        List<Square> allowedMoves = new ArrayList<>();
        int moveCounter = 0;
        for (Position position : possibleMoves) {
            moveCounter++;
            Square square2BeChecked = position.getSquare();
            // invalid/water found, break
            if (!position.isValid()) {
                logger.debug("checking position [{}], was invalid", square2BeChecked);
                break;
            }

            // non-empty square
            // not possible to move further
            if (position.containsPiece()) {
                logger.debug("checking position [{}], contained [{}]", square2BeChecked, position.getPiece());
                // non-empty square containing opponent piece
                // allowed to move there, if it's own piece then not allowed to move there
                if (position.getPiece().getPlayer() != player) {
                    allowedMoves.add(square2BeChecked);
                }
                break;
            }

            // empty square, allowed to move there
            logger.debug("checking position [{}], was empty", square2BeChecked);
            allowedMoves.add(square2BeChecked);

            // we've walked as far as this piece can move
            if (moveCounter >= moveDistance) {
                break;
            }
        }
        return allowedMoves;
    }

    /**
     * Move a piece.
     * 
     * @param attacker
     * @param defender
     */
    public int movePiece(final Piece attacker, Square target) {
        // final int x_coord_defender = target.getAbsolute().getX();
        // final int y_coord_defender = target.getAbsolute().getY();
        final Square squareDefender = target.getAbsolute();
        final Square squareAttacker = attacker.getSquare();

        logger.debug("Resolve movement [{}] to x[{}]", attacker, target);

        Position position = positions[squareDefender.getX()][squareDefender.getY()];
        int result;

        // empty the old position for the piece that moved
        emptySquare(squareAttacker);

        /**
         * empty space, go ahead and move the piece to that location
         */
        if (position.isEmpty()) {
            logger.debug("Piece [{}] moved to empty square [{}]", attacker, squareDefender);
            result = RESULT_MOVE_NO_BATTLE;
            attacker.setLocation(squareDefender); // set new location to
            addPiece(attacker);// move attacker to defender square
        }
        /**
         * non-empty space, resolve strike
         */
        else {
            Piece defender = position.getPiece();
            result = resolveStrike(attacker, defender);
            // attacker won
            if (result == RESULT_WIN) {
                logger.debug("Piece [{}] won against [{}]", attacker, defender);
                attacker.setLocation(squareDefender); // set new location to
                emptySquare(squareDefender);
                addPiece(attacker);// move attacker to defender square
            }
            // attacker lost
            else if (result == RESULT_LOOSE) {
                logger.debug("Piece [{}] lost against [{}]", attacker, defender);
            }
            // draw
            else if (result == RESULT_DRAW) {
                logger.debug("Piece [{}] draw against [{}]", attacker, defender);
                emptySquare(squareDefender);
            }
        }

        return result;
    }

    public void emptySquare(Square square) {
        Square abs = square.getAbsolute();
        if (logger.isDebugEnabled()) {
            Position position = positions[abs.getX()][abs.getY()];
            String pieceString = "EMPTY";
            if (position.containsPiece()) {
                pieceString = position.getPiece().toString();
            }
            logger.debug("Marking square [{}] containing [{}] as empty", abs, pieceString);
        }
        positions[abs.getX()][abs.getY()] = new EmptyPosition(abs);
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

        Square getSquare();
    }

    /**
     * Represents an empty position on the board.
     * 
     * @author Peter Nerg
     */
    private static final class EmptyPosition implements Position {

        private final Square square;

        EmptyPosition(Square square) {
            this.square = square;
        }

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
            throw new UnsupportedOperationException("Empty squares don't have pieces");
        }

        @Override
        public Square getSquare() {
            return square;
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

        private final Square square;

        InvalidPosition(Square square) {
            this.square = square;
        }

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
            throw new UnsupportedOperationException("Invalid squares don't have pieces");
        }

        @Override
        public Square getSquare() {
            return square;
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
        public Square getSquare() {
            return piece.getSquare();
        }

        @Override
        public String toString() {
            return piece.getType().substring(0, 2) + piece.getPlayer();
        }
    }

}
