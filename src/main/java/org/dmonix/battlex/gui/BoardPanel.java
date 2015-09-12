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
package org.dmonix.battlex.gui;

import static org.dmonix.battlex.datamodel.Player.PlayerBlue;
import static org.dmonix.battlex.datamodel.Player.PlayerRed;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.dmonix.battlex.Battlex;
import org.dmonix.battlex.datamodel.Board;
import org.dmonix.battlex.datamodel.Piece;
import org.dmonix.battlex.datamodel.PieceData;
import org.dmonix.battlex.datamodel.Player;
import org.dmonix.battlex.datamodel.Square;
import org.dmonix.battlex.event.ClearSquareEventObject;
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

    /** Used to keep track if we've painted the gfx once. */
    private final AtomicBoolean paintedOnce = new AtomicBoolean(false);

    // composite used to draw translucent graphics
    private final Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
    private final Composite alphaComposite2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);

    private BufferedImage boardBackgroundImage = null;

    private final Board board = new Board();

    // defaulting to value just not to be null
    private Player player = Player.PlayerBlue;

    private Piece currentPiece = null;
    private EventCommunicator eventCommunicator;

    public BoardPanel() {
        boardBackgroundImage = Resources.getBackgroundImage(PlayerRed);
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
            movePiece(board.getPiece(geo.getOldCoord()), geo.getNewCoord());
        }
        /**
         * The game is being setup
         */
        else if (gameStateObject.inState(GameStates.STATE_GAME_SETUP) || gameStateObject.inState(GameStates.STATE_SETUP_WAIT_OPPONENT_SETUP)) {
            if (geo.getType() != PieceData.PIECE_NO_PIECE) {
                board.addPiece(new Piece(player.other(), geo.getType(), geo.getNewCoord()));
            } else {
                board.emptySquare(geo.getNewCoord());
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
    public void newGame(Player player, EventCommunicator eventCommunicator) {
        boardBackgroundImage = Resources.getBackgroundImage(player);
        super.setSize(super.getSize());

        this.player = player;
        this.eventCommunicator = eventCommunicator;
        this.eventCommunicator.addEventListener(this);

        repaint();
    }

    /**
     * Paints the entire board with all pieces
     * 
     * @param g
     */
    public void paint(final Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        /**
         * pre-load all images (for both players) the first time, otherwise it might sometimes result in strange behavior where the image isn't rendered until
         * after a few repaints
         */
        if (paintedOnce.compareAndSet(false, true)) {
            try {
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_BOMB_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_MARSHAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_GENERAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_COLONEL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_MAJOR_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_CAPTAIN_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_LIEUTENANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_SERGEANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_MINER_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_SCOUT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_SPY_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_FLAG_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerRed, PieceData.PIECE_EMPTY_TYPE), 100, 100, null);

                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_BOMB_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_MARSHAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_GENERAL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_COLONEL_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_MAJOR_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_CAPTAIN_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_LIEUTENANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_SERGEANT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_MINER_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_SCOUT_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_SPY_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_FLAG_TYPE), 100, 100, null);
                g2.drawImage(Resources.getImage(PlayerBlue, PieceData.PIECE_EMPTY_TYPE), 100, 100, null);
            } catch (Exception ex) {
            }
        }

        Dimension dim = super.getSize();
        Composite originalComposite = g2.getComposite();

        g2.clearRect(0, 0, dim.width, dim.height);
        if (boardBackgroundImage != null) {
            g2.drawImage(boardBackgroundImage, 0, 0, dim.width, dim.height, null);
        }

        // calculate the dimension of the squares
        final float squareWidth = (float) dim.width / 10;
        final float squareHeight = (float) dim.height / 10;

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
         * ======================================== during setup ======================================== <br>
         * ======================================== paint the lowest four rows ========================================
         */
        if (gameStateObject.inState(GameStates.STATE_GAME_SETUP) || gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP)) {
            g2.setComposite(alphaComposite);
            for (int y = 6; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    paintSquare(g2, x, y);
                }
            }
            g2.setComposite(originalComposite);
        }

        /**
         * ======================================== during game ======================================== <br>
         * ======================================== paint the allowed move squares ========================================
         */
        if (currentPiece != null && gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN)) {
            g2.setComposite(alphaComposite);
            for (Square square : board.getAllowedMoves(currentPiece)) {
                Square relative = square.relative(player);
                paintSquare(g2, relative.x(), relative.y());
            }
            g2.setComposite(originalComposite);
        }

        // if ((currentPiece != null && gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN))
        // || (currentPiece == null && gameStateObject.inState(GameStates.STATE_GAME_SETUP))
        // || (currentPiece == null && gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP))) {
        // g2.setComposite(alphaComposite);
        //
        // Color fill;
        // if (this.player == 1)
        // fill = Color.red;
        // else
        // fill = Color.blue;
        //
        // for (int y = 0; y < 10; y++) {
        // for (int x = 0; x < 10; x++) {
        // if (markedSquares[x][y] != null) {
        // // log.debug("Marking/painting square x[{}] y[{}]", x, y);
        // g2.setColor(fill);
        // g2.fill(new Rectangle((int) (squareWidth * x), (int) (squareHeight * y), (int) squareWidth, (int) squareHeight));
        // }
        // }
        // }
        // g2.setComposite(originalComposite);
        // }
        /**
         * ======================================== paint the pieces ========================================
         */
        Image scaledImage;

        for (Piece piece : board.getPieces()) {
            Square relativePieceSquare = piece.location().relative(player);
            int x = relativePieceSquare.x();
            int y = relativePieceSquare.y();
            if (piece.getPlayer() == player)
                scaledImage = Resources.getImage(player, piece.getType());
            else
                scaledImage = Resources.getImage(piece.getPlayer(), PieceData.PIECE_EMPTY_TYPE);

            if (currentPiece != null && piece != currentPiece) {
                g2.setComposite(alphaComposite2);
            }
            log.debug("Painting [{}] at x[{}] y[{}]", piece, x, y);
            g2.drawImage(scaledImage, (int) squareWidth * x + scaledImage.getWidth(null) / 2, (int) squareHeight * y + scaledImage.getHeight(null) / 8, null);
            g2.setComposite(originalComposite);
        }
    }

    private void paintSquare(Graphics2D g2, int x, int y) {
        log.trace("Painting square [" + x + "][" + y + "]", x, y);
        Color fill;
        if (this.player != null && this.player.isPlayerRed()) {
            fill = Color.red;
        } else {
            fill = Color.blue;
        }
        final Dimension dim = super.getSize();
        final float squareWidth = (float) dim.width / 10;
        final float squareHeight = (float) dim.height / 10;
        g2.setColor(fill);
        g2.fill(new Rectangle((int) (squareWidth * x), (int) (squareHeight * y), (int) squareWidth, (int) squareHeight));
    }

    /**
     * The user has clicked on the board during a game.
     * 
     * @param e
     */
    void inGameClick(MouseEvent e) {
        Square clickedSquare = getClickedSquare(e);

        /**
         * If the user has pressed any other mouse button than button 1 then clear the selection
         */
        if (e.getButton() != MouseEvent.BUTTON1) {
            currentPiece = null;
            repaint();
            return;
        }

        // not selected any piece and clicked in an empty space
        if (board.isEmpty(clickedSquare) && currentPiece == null)
            return;

        /**
         * first click, no selected piece
         */
        if (currentPiece == null) {
            Piece selectedPiece = board.getPiece(clickedSquare);

            // it's not allowed to move the other players pieces
            if (selectedPiece.getPlayer() != player)
                return;

            currentPiece = selectedPiece;
            log.debug("Selected [{}]", currentPiece);

            super.repaint();
            return;
        }
        /*
         * If a piece has been selected it's only valid to move to highlighted/valid squares
         */
        else if (!board.canMoveTo(currentPiece, clickedSquare)) {
            return;
        }

        eventCommunicator.sendEvent(new GameEventObject(currentPiece.location(), clickedSquare));

        movePiece(currentPiece, clickedSquare);
        gameStateObject.setState(GameStates.STATE_IN_GAME_OPPONENT_TURN);
        currentPiece = null;
    }

    // /**
    // * Get the piece at a specific point on the board
    // *
    // * @param x
    // * @param y
    // * @return
    // */
    // private Piece getPieceAtPoint(Point point) {
    // return board.getPiece(SquarePointConverter.point2Square(point, player));
    // }

    // /**
    // * Sets a piece at a specific point
    // *
    // * @param point
    // * The point for the piece
    // * @param pieceType
    // * The type of piece
    // */
    // private void setPieceAtPoint(Point point, String pieceType) {
    // Square square = SquarePointConverter.point2Square(point, player);
    // // if there is a piece in the square, remove it
    // if (!board.isEmpty(square) && board.getPiece(square).getPlayer() == player) {
    // Piece piece = board.getPiece(square);
    // // remove the piece from the opponents board
    // eventCommunicator.sendEvent(new GameEventObject(PieceData.PIECE_NO_PIECE, square));
    // owner.addPiece(player, piece.getType());
    // board.removePiece(piece);
    // this.markedSquares[point.x][point.y] = new Object();
    // repaint();
    // }
    //
    // // can only add pieces to marked squares
    // if (this.markedSquares[point.x][point.y] == null)
    // return;
    //
    // if (pieceType == PieceData.PIECE_NO_PIECE)
    // return;
    //
    // board.addPiece(new Piece(this.player, pieceType, square));
    // this.markedSquares[point.x][point.y] = null;
    // eventCommunicator.sendEvent(new GameEventObject(pieceType, square));
    //
    // repaint();
    // }

    private void gameSetupClick(MouseEvent e) {
        // find the point for the click
        Square clickedSquare = getClickedSquare(e).relative(player);

        // only allowed to place piece on the 4 lines closest to the player
        if (clickedSquare.y() < 6) {
            return;
        }

        // get the selected setup piece
        String pieceType = owner.getSelectedSetupPiece(this.player);

        // empty square, add the selected piece
        if (board.isEmpty(clickedSquare)) {
            // ignore if clicking in an empty square with no selected piece
            if (pieceType != PieceData.PIECE_NO_PIECE) {
                board.addPiece(new Piece(this.player, pieceType, clickedSquare));
                eventCommunicator.sendEvent(new GameEventObject(pieceType, clickedSquare));
            }
        }
        // if there is a piece in the square, remove it
        else {
            Piece piece = board.getPiece(clickedSquare);
            // remove the piece from the opponents board
            eventCommunicator.sendEvent(new ClearSquareEventObject(clickedSquare));
            owner.addPiece(player, piece.getType());
            board.removePiece(piece);
        }

        repaint();
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
    private Square getClickedSquare(MouseEvent e) {
        Dimension d = super.getSize();
        final float squareWidth = d.width / 10;
        final float squareHeight = d.height / 10;
        final int x = e.getPoint().x / (int) squareWidth;
        final int y = e.getPoint().y / (int) squareHeight;
        Square square = Square.apply(player, x, y);
        // debug the square clicked and its contents
        if (log.isDebugEnabled()) {
            String pieceAtClickedPoint = "EMPTY";
            if (!board.isEmpty(square)) {
                pieceAtClickedPoint = board.getPiece(square).toString();
            }
            log.debug("Player [{}] clicked in gfx square/point x[{}] y[{}] - [{}]", player, x, y, pieceAtClickedPoint);
        }
        return square;
    }

    /**
     * Move a piece.
     * 
     * @param attacker
     * @param defender
     */
    private void movePiece(Piece attacker, Square target) {
        Piece defender = board.getPiece(target);

        int result = board.movePiece(attacker, target);

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

        repaint();
    }

    /**
     * Checks if any of the players can move a piece. If not a message it displayed stating the victory of one of the players
     */
    private void checkIfAnyPlayerCanMove() {
        boolean player1Move = board.checkIfPlayerCanMove(PlayerRed);
        boolean player2Move = board.checkIfPlayerCanMove(PlayerBlue);

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

    private class BoardPanel_this_mouseAdapter extends java.awt.event.MouseAdapter {
        private GameStateController gameStateObject = GameStateController.getInstance();

        public void mouseClicked(MouseEvent e) {
            if (e.isAltDown() && e.isShiftDown() && e.isControlDown()) {
                // revealOpponent = !revealOpponent;
                repaint();
            }

            if (gameStateObject.inState(GameStates.STATE_GAME_SETUP) || gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP))
                gameSetupClick(e);
            else if (gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN))
                inGameClick(e);
        }
    }

    /**
     * @return
     */
    public Board getBoard() {
        return board;
    }

}