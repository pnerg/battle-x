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

import java.util.List;

import org.dmonix.battlex.BaseAssert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test the {@link Board} class.<br>
 * In this test the board is pre-populated as follows.
 * 
 * <pre>
 *  fl - Flag
 *  bo - Bomb
 *  mi - Miner
 *  sc - Scout
 *  ge - General
 *  co - Colonel
 *  ca - Captain
 *  sp - Spy
 *  ma - Marshal
 *  <i>X/Y</i>
 *             <i>Player2</i>
 * 9 --- --- --- --- --- --- --- --- bo2 fl2 
 * 8 --- ca1 sp2 --- --- --- --- --- bo2 bo2 
 * 7 --- --- ma1 bo2 ge2 --- --- --- mi2 mi1 
 * 6 --- --- --- mi1 --- ca2 --- --- --- --- 
 * 5 --- --- XXX XXX --- sc2 XXX XXX --- --- 
 * 4 --- --- XXX XXX --- co1 XXX XXX --- --- 
 * 3 --- --- --- --- --- --- --- --- --- --- 
 * 2 --- --- --- --- sc1 --- --- --- --- --- 
 * 1 bo1 bo1 --- --- --- --- --- --- --- --- 
 * 0 fl1 bo1 --- --- --- --- --- --- --- --- 
 *    0   1   2   3   4   5   6   7   8   9   
 *             <i>Player1</i>
 * </pre>
 * 
 * @author Peter Nerg
 * 
 */
public class TestBoard extends BaseAssert {
    private static final Logger logger = LoggerFactory.getLogger(TestBoard.class);
    private final Board board = new Board();

    public TestBoard() {
        board.addPiece(new Piece(1, PieceData.PIECE_FLAG_TYPE, SquareFactory.createAbsolute(0, 0)));
        board.addPiece(new Piece(1, PieceData.PIECE_BOMB_TYPE, SquareFactory.createAbsolute(1, 0)));
        board.addPiece(new Piece(1, PieceData.PIECE_CAPTAIN_TYPE, SquareFactory.createAbsolute(1, 8)));
        board.addPiece(new Piece(1, PieceData.PIECE_BOMB_TYPE, SquareFactory.createAbsolute(0, 1)));
        board.addPiece(new Piece(1, PieceData.PIECE_BOMB_TYPE, SquareFactory.createAbsolute(1, 1)));
        board.addPiece(new Piece(1, PieceData.PIECE_SCOUT_TYPE, SquareFactory.createAbsolute(4, 2)));
        board.addPiece(new Piece(1, PieceData.PIECE_COLONEL_TYPE, SquareFactory.createAbsolute(5, 4)));
        board.addPiece(new Piece(1, PieceData.PIECE_MINER_TYPE, SquareFactory.createAbsolute(3, 6)));
        board.addPiece(new Piece(1, PieceData.PIECE_MARSHAL_TYPE, SquareFactory.createAbsolute(2, 7)));
        board.addPiece(new Piece(1, PieceData.PIECE_MINER_TYPE, SquareFactory.createAbsolute(9, 7)));

        board.addPiece(new Piece(2, PieceData.PIECE_SPY_TYPE, SquareFactory.createAbsolute(2, 8)));
        board.addPiece(new Piece(2, PieceData.PIECE_FLAG_TYPE, SquareFactory.createAbsolute(9, 9)));
        board.addPiece(new Piece(2, PieceData.PIECE_BOMB_TYPE, SquareFactory.createAbsolute(8, 9)));
        board.addPiece(new Piece(2, PieceData.PIECE_MINER_TYPE, SquareFactory.createAbsolute(8, 7)));
        board.addPiece(new Piece(2, PieceData.PIECE_BOMB_TYPE, SquareFactory.createAbsolute(9, 8)));
        board.addPiece(new Piece(2, PieceData.PIECE_BOMB_TYPE, SquareFactory.createAbsolute(8, 8)));
        board.addPiece(new Piece(2, PieceData.PIECE_BOMB_TYPE, SquareFactory.createAbsolute(3, 7)));
        board.addPiece(new Piece(2, PieceData.PIECE_GENERAL_TYPE, SquareFactory.createAbsolute(4, 7)));
        board.addPiece(new Piece(2, PieceData.PIECE_SCOUT_TYPE, SquareFactory.createAbsolute(5, 5)));
        board.addPiece(new Piece(2, PieceData.PIECE_CAPTAIN_TYPE, SquareFactory.createAbsolute(5, 6)));
    }

    @Test
    public void testToString() {
        logger.debug("Empty board\n{}", new Board());
        logger.debug("The board\n{}", board);
    }

    @Test
    public void getPieces() {
        List<Piece> pieces = board.getPieces();
        assertNotNull(pieces);
        assertEquals(20, pieces.size());
    }

    @Test
    public void clearAllPlayerPieces_p1() {
        board.clearAllPlayerPieces(1);
        assertEquals(0, board.getPiecesForPlayer(1).size());
    }

    @Test
    public void clearAllPlayerPieces_p2() {
        board.clearAllPlayerPieces(2);
        assertEquals(0, board.getPiecesForPlayer(2).size());
    }

    @Test
    public void clearAllPlayerPieces_all() {
        board.clearAllPlayerPieces(1);
        board.clearAllPlayerPieces(2);
        assertEquals(0, board.getPieces().size());
    }

    @Test
    public void getPiece_x0y0() {
        Piece piece = board.getPiece(SquareFactory.createAbsolute(0, 0));
        assertNotNull(piece);
        assertEquals(PieceData.PIECE_FLAG_TYPE, piece.getType());
        assertEquals(1, piece.getPlayer());
        assertEquals(SquareFactory.createAbsolute(0, 0), piece.getSquare());
    }

    @Test
    public void getPiece_x9y9() {
        Piece piece = board.getPiece(SquareFactory.createAbsolute(9, 9));
        assertNotNull(piece);
        assertEquals(PieceData.PIECE_FLAG_TYPE, piece.getType());
        assertEquals(2, piece.getPlayer());
        assertEquals(SquareFactory.createAbsolute(9, 9), piece.getSquare());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPiece_x4y4_emptySquare() {
        board.getPiece(SquareFactory.createAbsolute(4, 4));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getPiece_x3y4_illegalSquare() {
        board.getPiece(SquareFactory.createAbsolute(3, 4));
    }

    @Test
    public void isEmpty_x4y2_nonEmpty() {
        assertFalse(board.isEmpty(SquareFactory.createAbsolute(4, 2)));
    }

    @Test
    public void isEmpty_x4y4_emptySquare() {
        assertTrue(board.isEmpty(SquareFactory.createAbsolute(4, 3)));
    }

    /**
     * Scout -> Empty
     */
    @Test
    public void move_x4y2_x4y3() {
        Square originalPosition = SquareFactory.createAbsolute(4, 2);
        Square movedPosition = SquareFactory.createAbsolute(4, 3);
        assertMove(originalPosition, movedPosition, Board.RESULT_MOVE_NO_BATTLE);
    }

    /**
     * Colonel -> Scout
     */
    @Test
    public void move_x5y4_x5y5() {
        Square originalPosition = SquareFactory.createAbsolute(5, 4);
        Square movedPosition = SquareFactory.createAbsolute(5, 5);
        assertMove(originalPosition, movedPosition, Board.RESULT_WIN);
    }

    /**
     * Marshal -> Bomb
     */
    @Test
    public void move_x2y7_x3y7() {
        Square originalPosition = SquareFactory.createAbsolute(2, 7);
        Square movedPosition = SquareFactory.createAbsolute(3, 7);
        assertMove(originalPosition, movedPosition, Board.RESULT_LOOSE);
    }

    /**
     * Marshal -> Spy
     */
    @Test
    public void move_x2y7_x2y8() {
        Square originalPosition = SquareFactory.createAbsolute(2, 7);
        Square movedPosition = SquareFactory.createAbsolute(2, 8);
        assertMove(originalPosition, movedPosition, Board.RESULT_WIN);
    }

    /**
     * Spy -> Marshal
     */
    @Test
    public void move_x2y8_x2y7() {
        Square originalPosition = SquareFactory.createAbsolute(2, 8);
        Square movedPosition = SquareFactory.createAbsolute(2, 7);
        assertMove(originalPosition, movedPosition, Board.RESULT_WIN);
    }

    /**
     * Spy -> Captain
     */
    @Test
    public void move_x2y8_x1y8() {
        Square originalPosition = SquareFactory.createAbsolute(2, 8);
        Square movedPosition = SquareFactory.createAbsolute(1, 8);
        assertMove(originalPosition, movedPosition, Board.RESULT_LOOSE);
    }

    /**
     * Miner -> Bomb
     */
    @Test
    public void move_x9y7_x9y8() {
        Square originalPosition = SquareFactory.createAbsolute(9, 7);
        Square movedPosition = SquareFactory.createAbsolute(9, 8);

        assertMove(originalPosition, movedPosition, Board.RESULT_WIN);
    }

    /**
     * Miner -> Miner
     */
    @Test
    public void move_x9y7_x8y7() {
        Square originalPosition = SquareFactory.createAbsolute(9, 7);
        Square movedPosition = SquareFactory.createAbsolute(8, 7);

        assertMove(originalPosition, movedPosition, Board.RESULT_DRAW);
    }

    /**
     * Miner -> Bomb -> Flag
     */
    @Test
    public void move_x9y7_x9y8_x9y9() {
        move_x9y7_x9y8();
        Square originalPosition = SquareFactory.createAbsolute(9, 8);
        Square movedPosition = SquareFactory.createAbsolute(9, 9);

        assertMove(originalPosition, movedPosition, Board.RESULT_WIN_GAME);
    }

    private void assertMove(Square originalPosition, Square movedPosition, int expectedResult) {
        Piece atk = board.getPiece(originalPosition);
        int result = board.movePiece(atk, movedPosition);
        assertEquals(expectedResult, result);

        // assert there is nothing at the original position
        assertPositionEmpty(originalPosition);

        // attacker wins or moved to empty square
        if (result == Board.RESULT_WIN || result == Board.RESULT_MOVE_NO_BATTLE) {
            assertPieceAtPosition(movedPosition, atk.getType(), atk.getPlayer());
        }
        // defender wins
        else if (result == Board.RESULT_LOOSE) {
            Piece def = board.getPiece(movedPosition);
            assertPieceAtPosition(movedPosition, def.getType(), def.getPlayer());
        }
        // draw - both pieces are removed
        else if (result == Board.RESULT_DRAW) {
            assertPositionEmpty(originalPosition);
            assertPositionEmpty(movedPosition);
        }
    }

    /**
     * Assert that the provided square is empty.
     * 
     * @param position
     */
    public void assertPositionEmpty(Square position) {
        assertTrue("Expected square @ [" + position + "] to be empty", board.isEmpty(position));
    }

    /**
     * Assert that the provided square contains the expected {@link Piece}.
     * 
     * @param position
     * @param expectedType
     * @param expectedPlayer
     */
    public void assertPieceAtPosition(Square position, String expectedType, int expectedPlayer) {
        assertFalse("Unexpected empty square @ [" + position + "]", board.isEmpty(position));
        Piece piece = board.getPiece(position);
        assertNotNull("Expected valid piece @ [" + position + "]", piece);
        assertEquals("Expected piece @ [" + position + "]", expectedType, piece.getType());
        assertEquals("Expected piece @ [" + position + "]", expectedPlayer, piece.getPlayer());
    }
}
