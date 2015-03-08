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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.dmonix.battlex.Battlex;
import org.dmonix.battlex.datamodel.Board;
import org.dmonix.battlex.event.EventCommunicator;
import org.dmonix.battlex.event.GameEventListener;
import org.dmonix.battlex.event.GameEventObject;
import org.dmonix.battlex.event.GameStateController;
import org.dmonix.battlex.event.GameStates;
import org.dmonix.battlex.resources.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Nerg
 */
public class BoardPanel extends JPanel implements GameEventListener {
    private static final long serialVersionUID = -8329675578149123690L;
    /** The logger instance for this class */
    private static final Logger log = LoggerFactory.getLogger(BoardPanel.class);
    private GameStateController gameStateObject = GameStateController.getInstance();
    private MainFrame owner;
    private boolean firstPaint = true;
    private static boolean revealOpponent = false;

    // composite used to draw translucent graphics
    private final Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
    private final Composite alphaComposite2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);

    private BufferedImage boardBackgroundImage = null;
    private float squareWidth = -1;
    private float squareHeight = -1;

    private final Board board = new Board();
    private Object[][] markedSquares = new Object[10][10];
    private int player = -1;
    private int otherPlayer = -1;

    private Piece currentPiece = null;
    private EventCommunicator eventCommunicator;

    public BoardPanel() {
        boardBackgroundImage = Resources.getBackgroundImage(1);
        this.setBackground(Battlex.BACKGOUND_COLOR);
        this.addMouseListener(new BoardPanel_this_mouseAdapter());
        repaint();
    }

    /**
     * Sets the owner of this panel
     * 
     * @param owner
     */
    public void setOwner(MainFrame owner) {
        this.owner = owner;
    }

    /**
     * Removes all the players pieces. <br>
     * It will also notify the other player of these changes This is only used during setup when a player loads a setup from file
     */
    public void clearAllPlayerPieces() {
        // TODO implement when loading of setup is implemented
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
        // repaint();
    }

    /**
     * Receive a game event
     * 
     * @param geo
     */
    public void gameEvent(GameEventObject geo) {
        log.debug("Received game event [{}] in state [{}] ", geo, gameStateObject.getState());

        /**
         * The game is on
         */
        if (gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN)) {
            gameStateObject.setState(GameStates.STATE_IN_GAME_PLAYER_TURN);
            movePiece(board.getPiece(geo.getOldXCoord(), geo.getOldYCoord()), geo.getNewXCoord(), geo.getNewYCoord());
        }
        /**
         * The game is being setup
         */
        else if (gameStateObject.inState(GameStates.STATE_GAME_SETUP) || gameStateObject.inState(GameStates.STATE_SETUP_WAIT_OPPONENT_SETUP)) {
            if (geo.getType() != Resources.PIECE_NO_PIECE) {
                // this.pieces[geo.getNewXCoord()][geo.getNewYCoord()] = new Piece(otherPlayer, geo.getType(), geo.getNewXCoord(), geo.getNewYCoord());
                board.addPiece(new Piece(otherPlayer, geo.getType(), geo.getNewXCoord(), geo.getNewYCoord()));
            } else {
                board.removePiece(geo.getNewXCoord(), geo.getNewYCoord());
            }

            repaint();
        }

    }

    /**
     * Sets up a new game
     * 
     * @param player
     *            The player
     * @param eventCommunicator
     *            The communicator
     */
    public void newGame(int player, EventCommunicator eventCommunicator) {
        boardBackgroundImage = Resources.getBackgroundImage(player);
        super.setSize(super.getSize());

        this.player = player;
        this.eventCommunicator = eventCommunicator;
        this.eventCommunicator.addEventListener(this);

        if (player == 1) {
            otherPlayer = 2;
        } else {
            otherPlayer = 1;
        }

        for (int y = 6; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                markedSquares[x][y] = new Object();
            }
        }
        repaint();
    }

    /**
     * Paints the entire board with all pieces
     * 
     * @param g
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        /**
         * pre-load all images (for both players) the first time, otherwise it might sometimes result in strange behavior where the image isn't rendered until
         * after a few repaints
         */
        if (firstPaint) {
            firstPaint = false;
            try {
                g2.drawImage(Resources.getImage(1, Resources.PIECE_BOMB_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_MARSHAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_GENERAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_COLONEL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_MAJOR_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_CAPTAIN_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_LIEUTENANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_SERGEANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_MINER_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_SCOUT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_SPY_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_FLAG_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(1, Resources.PIECE_EMPTY_TYPE), 100, 100, null);

                g2.drawImage(Resources.getImage(2, Resources.PIECE_BOMB_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_MARSHAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_GENERAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_COLONEL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_MAJOR_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_CAPTAIN_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_LIEUTENANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_SERGEANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_MINER_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_SCOUT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_SPY_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_FLAG_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(2, Resources.PIECE_EMPTY_TYPE), 100, 100, null);
            } catch (Exception ex) {
            }
        }

        Dimension dim = super.getSize();
        Composite originalComposite = g2.getComposite();

        g2.clearRect(0, 0, dim.width, dim.height);
        if (boardBackgroundImage != null)
            g2.drawImage(boardBackgroundImage, 0, 0, dim.width, dim.height, null);

        // calculate the dimension of the squares
        squareWidth = (float) dim.width / 10;
        squareHeight = (float) dim.height / 10;

        // System.out.println("Boardpanel w="+dim.width+"; h="+dim.height+"   #   sw="+squareWidth+": sh="+squareHeight);

        /**
         * ======================================== Draw the lines of the board ========================================
         */
        // draw the x-lines
        for (int y = 1; y < 10; y++) {
            if (y == 5) {
                g2.drawLine(0, (int) squareHeight * y, (int) squareWidth * 2, (int) squareHeight * y);
                g2.drawLine((int) squareWidth * 4, (int) squareHeight * y, (int) squareWidth * 6, (int) squareHeight * y);
                g2.drawLine((int) squareWidth * 8, (int) squareHeight * y, dim.width, (int) squareHeight * y);
            } else
                g2.drawLine(0, (int) squareHeight * y, dim.width, (int) squareHeight * y);
        }

        // draw the y-lines
        for (int x = 1; x < 10; x++) {
            if (x == 3 || x == 7) {
                g2.drawLine((int) squareWidth * x, 0, (int) squareWidth * x, (int) squareHeight * 4);
                g2.drawLine((int) squareWidth * x, (int) squareHeight * 6, (int) squareWidth * x, dim.height);
            } else
                g2.drawLine((int) squareWidth * x, 0, (int) squareWidth * x, dim.height);
        }

        /**
         * ======================================== paint the marked squares ========================================
         */
        if ((currentPiece != null && gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN))
                || (currentPiece == null && gameStateObject.inState(GameStates.STATE_GAME_SETUP))
                || (currentPiece == null && gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP))) {
            g2.setComposite(alphaComposite);

            Color fill;
            if (this.player == 1)
                fill = Color.red;
            else
                fill = Color.blue;

            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    if (markedSquares[x][y] != null) {
                        // log.debug("Marking/painting square x[{}] y[{}]", x, y);
                        g2.setColor(fill);
                        g2.fill(new Rectangle((int) (squareWidth * x), (int) (squareHeight * y), (int) squareWidth, (int) squareHeight));
                    }
                }
            }
            g2.setComposite(originalComposite);
        }

        /**
         * ======================================== paint the pieces ========================================
         */
        Image scaledImage;

        for (Piece piece : board.getPieces()) {
            Point point = translatePieceLocationToLocalLayout(piece);
            int x = point.x;
            int y = point.y;
            if (!revealOpponent)
                scaledImage = piece.getImage(this.player);
            else
                scaledImage = piece.getImage();

            if (currentPiece != null && piece != currentPiece)
                g2.setComposite(alphaComposite2);
            log.debug("Painting [{}] at x[{}] y[{}]", piece, x, y);
            g2.drawImage(scaledImage, (int) squareWidth * x + scaledImage.getWidth(null) / 2, (int) squareHeight * y + scaledImage.getHeight(null) / 8, null);
            g2.setComposite(originalComposite);
        }
    }

    private Point translatePieceLocationToLocalLayout(Piece piece) {
        int x = piece.getXCoord();
        int y = piece.getYCoord();
        Point point;
        if (player == 1) {
            point = new Point(x, 9 - y);
        } else {
            point = new Point(x - 9, y);
        }

        return point;
    }

    /**
     * The user has clicked on the board during a game.
     * 
     * @param e
     */
    void inGameClick(MouseEvent e) {
        Point point = getClickedSquare(e);

        /**
         * If the user has pressed any other mouse button than button 1 then clear the selection
         */
        if (e.getButton() != MouseEvent.BUTTON1) {
            currentPiece = null;
            clearMarkedSquares();
            repaint();
            return;
        }

        log.debug("Clicked in: " + e.getPoint() + " x_coord=" + point.x + " y_coord=" + point.y);

        Piece selectedPiece = getPieceAtPoint(point);

        // not selected any piece and clicked in an empty space
        if (selectedPiece == null && currentPiece == null)
            return;

        /**
         * first click, no selected piece
         */
        if (selectedPiece != null && currentPiece == null) {
            // it's not allowed to move the other players pieces
            if (selectedPiece.getPlayer() != player)
                return;

            currentPiece = selectedPiece;
            log.debug("Selected [{}]", currentPiece);

            markSquares(selectedPiece);
            super.repaint();
            return;
        }

        log.debug("Current object in position [{}] ", selectedPiece);

        /**
         * If a piece has been selected it's only valid to move to highlighted squares
         */
        if (currentPiece != null && markedSquares[point.x][point.y] == null)
            return;

        eventCommunicator.sendEvent(new GameEventObject(currentPiece.getXCoord(), currentPiece.getYCoord(), point.x, point.y));

        movePiece(currentPiece, point.x, point.y);
        gameStateObject.setState(GameStates.STATE_IN_GAME_OPPONENT_TURN);
        currentPiece = null;

    }

    /**
     * Get the piece at a specific point on the board
     * 
     * @param x
     * @param y
     * @return
     */
    public Piece getPieceAtPoint(int x, int y) {
        return board.getPiece(x, y);
    }

    /**
     * Get the piece at a specific point on the board
     * 
     * @param x
     * @param y
     * @return
     */
    private Piece getPieceAtPoint(Point point) {
        return board.getPiece(point.x, point.y);
    }

    /**
     * Sets a piece at a specific point
     * 
     * @param point
     *            The point for the piece
     * @param pieceType
     *            The type of piece
     */
    private void setPieceAtPoint(Point point, String pieceType) {
        // if there is a piece in the square, remove it
        Piece piece = board.getPiece(point.x, point.y);
        if (piece != null && piece.getPlayer() == player) {
            // remove the piece from the opponents board
            eventCommunicator.sendEvent(new GameEventObject(Resources.PIECE_NO_PIECE, point.x, point.y));
            owner.addPiece(player, piece.getType());
            board.removePiece(point.x, point.y);
            this.markedSquares[point.x][point.y] = new Object();
            repaint();
        }

        // can only add pieces to marked squares
        if (this.markedSquares[point.x][point.y] == null)
            return;

        if (pieceType == Resources.PIECE_NO_PIECE)
            return;

        board.addPiece(new Piece(this.player, pieceType, point.x, point.y));
        this.markedSquares[point.x][point.y] = null;
        eventCommunicator.sendEvent(new GameEventObject(pieceType, point.x, point.y));

        repaint();
    }

    private void gameSetupClick(MouseEvent e) {
        // find the point for the click
        Point point = getClickedSquare(e);
        // can only click on own squares
        if ((player == 1 && point.y > 3) || (player == 2 && point.y < 6))
            return;
        // get the selected setup piece
        String pieceType = owner.getSelectedSetupPiece(this.player);
        setPieceAtPoint(point, pieceType);
    }

    /**
     * Finds out which square has been selected.<br>
     * The coordinate system goes from 0/0 - 9/9. <br>
     * The coordinate system is absolute for both players, i.e. player 1 plays from y=0 and player 2 plays from y=9. Where 0/0 is the left down corner and 9/9
     * is right upper corner for player 1.
     * 
     * <pre>
     *    Player2
     * 0/9 .... 9/9
     * .
     * .
     * .
     * 0/0 .... 9/0
     *    Player1
     * </pre>
     * 
     * @param e
     * @return
     */
    private Point getClickedSquare(MouseEvent e) {
        Dimension d = super.getSize();
        squareWidth = d.width / 10;
        squareHeight = d.height / 10;

        final int x = e.getPoint().x / (int) squareWidth;
        final int y = e.getPoint().y / (int) squareHeight;
        int x_transposed, y_transposed;
        if (player == 1) {
            x_transposed = x;
            y_transposed = 9 - y;
        } else {
            x_transposed = 9 - x;
            y_transposed = y;
        }
        Point point = new Point(x_transposed, y_transposed);
        log.debug("Player [{}] clicked in square x[{}] y[{}]", player, point.x, point.y);
        return point;
    }

    /**
     * Move a piece.
     * 
     * @param attacker
     * @param defender
     */
    private void movePiece(Piece attacker, int x_coord_defender, int y_coord_defender) {
        int result = board.movePiece(attacker, x_coord_defender, y_coord_defender);

        Piece defender = board.getPiece(x_coord_defender, y_coord_defender);

        /**
         * empty space, go ahead and move the piece to that location
         */
        if (result == Board.RESULT_MOVE_NO_BATTLE) {
            return;
        }

        // attacker wins
        if (result == Board.RESULT_WIN) {
            owner.subtractPiece(defender.getPlayer(), defender.getType());

            ResolveStrikeDialog.showStrikeResult(owner, "Player " + attacker.getPlayer() + " wins", attacker.getPlayer(), attacker.getType(),
                    defender.getPlayer(), defender.getType());
        }
        // draw
        else if (result == Board.RESULT_DRAW) {
            owner.subtractPiece(attacker.getPlayer(), attacker.getType());
            owner.subtractPiece(defender.getPlayer(), defender.getType());

            ResolveStrikeDialog.showStrikeResult(owner, "Draw", attacker.getPlayer(), attacker.getType(), defender.getPlayer(), defender.getType());
        }
        // defender wins
        else if (result == Board.RESULT_LOOSE) {
            owner.subtractPiece(attacker.getPlayer(), attacker.getType());

            ResolveStrikeDialog.showStrikeResult(owner, "Player " + defender.getPlayer() + " wins", attacker.getPlayer(), attacker.getType(),
                    defender.getPlayer(), defender.getType());

        }
        // The flag was found, attacker wins, game ends
        else {
            ResolveStrikeDialog.showStrikeResult(owner, "Game over! Player" + attacker.getPlayer() + " wins", attacker.getPlayer(), attacker.getType(),
                    defender.getPlayer(), defender.getType());
            gameStateObject.setState(GameStates.STATE_IDLE);
        }

        checkIfAnyPlayerCanMove();

        clearMarkedSquares();
        repaint();
    }

    /**
     * Checks if any of the players can move a piece. If not a message it displayed stating the victory of one of the players
     */
    private void checkIfAnyPlayerCanMove() {
        boolean player1Move = board.checkIfPlayerCanMove(1);
        boolean player2Move = board.checkIfPlayerCanMove(2);

        // neither player can move
        if (!player1Move && !player2Move) {
            JOptionPane.showMessageDialog(owner, "It's a draw\nNeither player can move", "Game over!", JOptionPane.INFORMATION_MESSAGE);
            gameStateObject.setState(GameStates.STATE_IDLE);
        }
        // player 1 cannot move
        else if (!player1Move && player2Move) {
            JOptionPane.showMessageDialog(owner, "Player 2 wins\nPlayer 1 cannot move", "Game over!", JOptionPane.INFORMATION_MESSAGE);
            gameStateObject.setState(GameStates.STATE_IDLE);
        }
        // player 2 cannot move
        else if (player1Move && !player2Move) {
            JOptionPane.showMessageDialog(owner, "Player 1 wins\nPlayer 2 cannot move", "Game over!", JOptionPane.INFORMATION_MESSAGE);
            gameStateObject.setState(GameStates.STATE_IDLE);
        }

    }

    /**
     * Highlights the squares that the currently selected piece can move to
     * 
     * @param piece
     */
    private void markSquares(Piece piece) {
        clearMarkedSquares();
        int moveDistance = piece.getMoveDistance();

        if (moveDistance == 0)
            return;

        boolean[][] allowedMoves = board.getAllowedMoves(piece);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                markedSquares[x][y] = allowedMoves[x][y] ? new Object() : null;
            }
        }
        // // check up
        // for (int i = 1; i <= moveDistance; i++) {
        // if (y_coord - i < 0 || ((x_coord == 2 || x_coord == 3 || x_coord == 6 || x_coord == 7) && (y_coord - i == 4 || y_coord - i == 5))
        // || board.isPlayerPiece(player, x_coord, y_coord-i)(pieces[x_coord][y_coord - i] != null && pieces[x_coord][y_coord - i].getPlayer() ==
        // piece.getPlayer()))
        // break;
        //
        // markedSquares[x_coord][y_coord - i] = new Object();
        //
        // if (pieces[x_coord][y_coord - i] != null && pieces[x_coord][y_coord - i].getPlayer() != piece.getPlayer())
        // break;
        // }
        //
        // // check down
        // for (int i = 1; i <= moveDistance; i++) {
        // if (y_coord + i > 9 || ((x_coord == 2 || x_coord == 3 || x_coord == 6 || x_coord == 7) && (y_coord + i == 4 || y_coord + i == 5))
        // || (pieces[x_coord][y_coord + i] != null && pieces[x_coord][y_coord + i].getPlayer() == piece.getPlayer()))
        // break;
        //
        // markedSquares[x_coord][y_coord + i] = new Object();
        //
        // if (pieces[x_coord][y_coord + i] != null && pieces[x_coord][y_coord + i].getPlayer() != piece.getPlayer())
        // break;
        // }
        //
        // // check right
        // for (int i = 1; i <= moveDistance; i++) {
        // if (x_coord + i > 9 || ((x_coord + i == 2 || x_coord + i == 3 || x_coord + i == 6 || x_coord + i == 7) && (y_coord == 4 || y_coord == 5))
        // || (pieces[x_coord + i][y_coord] != null && pieces[x_coord + i][y_coord].getPlayer() == piece.getPlayer()))
        // break;
        //
        // markedSquares[x_coord + i][y_coord] = new Object();
        //
        // if (pieces[x_coord + i][y_coord] != null && pieces[x_coord + i][y_coord].getPlayer() != piece.getPlayer())
        // break;
        // }
        //
        // // check left
        // for (int i = 1; i <= moveDistance; i++) {
        // if (x_coord - i < 0 || ((x_coord - i == 2 || x_coord - i == 3 || x_coord - i == 6 || x_coord - i == 7) && (y_coord == 4 || y_coord == 5))
        // || (pieces[x_coord - i][y_coord] != null && pieces[x_coord - i][y_coord].getPlayer() == piece.getPlayer()))
        // break;
        //
        // markedSquares[x_coord - i][y_coord] = new Object();
        //
        // if (pieces[x_coord - i][y_coord] != null && pieces[x_coord - i][y_coord].getPlayer() != piece.getPlayer())
        // break;
        // }
    }

    /**
     * Clears all the highlighted squares
     */
    private void clearMarkedSquares() {
        markedSquares = null;
        markedSquares = new Object[10][10];
    }

    private class BoardPanel_this_mouseAdapter extends java.awt.event.MouseAdapter {
        private GameStateController gameStateObject = GameStateController.getInstance();

        public void mouseClicked(MouseEvent e) {
            if (e.isAltDown() && e.isShiftDown() && e.isControlDown()) {
                revealOpponent = !revealOpponent;
                repaint();
            }

            if (gameStateObject.inState(GameStates.STATE_GAME_SETUP) || gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP))
                gameSetupClick(e);
            else if (gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN))
                inGameClick(e);
        }
    }

}