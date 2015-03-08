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

    private Piece[][] pieces = new Piece[10][10];
    private Object[][] markedSquares = new Object[10][10];
    private int player = -1;
    private int otherPlayer = -1;

    private Piece currentPiece = null;
    private EventCommunicator eventCommunicator;

    public BoardPanel() {
        boardBackgroundImage = Resources.getBackgroundImage(1);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        for (int y = 6; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (pieces[x][y] != null && pieces[x][y].getPlayer() == player) {
                    owner.addPiece(player, pieces[x][y].getType());
                    pieces[x][y] = null;
                    markedSquares[x][y] = new Object();
                    eventCommunicator.sendEvent(new GameEventObject(Resources.PIECE_NO_PIECE, x, y));
                }
            }
        }
        repaint();
    }

    /**
     * Receive a game event
     * 
     * @param geo
     */
    public void gameEvent(GameEventObject geo) {
        log.debug("Received game event [{}] ", geo);

        /**
         * The game is on
         */
        if (gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN)) {
            gameStateObject.setState(GameStates.STATE_IN_GAME_PLAYER_TURN);
            movePiece(pieces[geo.getOldXCoord()][geo.getOldYCoord()], geo.getNewXCoord(), geo.getNewYCoord());
        }
        /**
         * The game is being setup
         */
        else if (gameStateObject.inState(GameStates.STATE_GAME_SETUP) || gameStateObject.inState(GameStates.STATE_SETUP_WAIT_OPPONENT_SETUP)) {
            if (geo.getType() != Resources.PIECE_NO_PIECE)
                this.pieces[geo.getNewXCoord()][geo.getNewYCoord()] = new Piece(otherPlayer, geo.getType(), geo.getNewXCoord(), geo.getNewYCoord());
            else
                this.pieces[geo.getNewXCoord()][geo.getNewYCoord()] = null;

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
        this.otherPlayer = 2;
        this.eventCommunicator = eventCommunicator;
        this.eventCommunicator.addEventListener(this);

        if (player != 1)
            otherPlayer = 1;

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
         * pre-load all images (for both players) the first time, otherwise it might sometimes result in strange behaviour where the image isn't rendered until
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

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (pieces[x][y] == null)
                    continue;

                if (!revealOpponent)
                    scaledImage = pieces[x][y].getImage(this.player);
                else
                    scaledImage = pieces[x][y].getImage();

                if (currentPiece != null && pieces[x][y] != currentPiece)
                    g2.setComposite(alphaComposite2);

                g2.drawImage(scaledImage, (int) squareWidth * x + scaledImage.getWidth(null) / 2, (int) squareHeight * y + scaledImage.getHeight(null) / 8,
                        null);
                g2.setComposite(originalComposite);
            }
        }
    }

    private void jbInit() throws Exception {
        this.setBackground(Battlex.BACKGOUND_COLOR);
        this.addMouseListener(new BoardPanel_this_mouseAdapter(this));
    }

    /**
     * The user has clicked on the board during a game.
     * 
     * @param e
     */
    void inGameClick(MouseEvent e) {
        Point point = getClickedPoint(e);

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

        Piece selectedPiece = pieces[point.x][point.y];

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

        eventCommunicator.sendEvent(new GameEventObject(currentPiece.getLocation().x, currentPiece.getLocation().y, point.x, point.y));

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
        return pieces[x][y];
    }

    /**
     * Sets a piece at a specific point
     * 
     * @param point
     *            The point for the piece
     * @param pieceType
     *            The type of piece
     */
    public void setPieceAtPoint(Point point, String pieceType) {
        // if there is a piece in the square, remove it
        if (pieces[point.x][point.y] != null) {
            // remove the piece from the opponents board
            eventCommunicator.sendEvent(new GameEventObject(Resources.PIECE_NO_PIECE, point.x, point.y));
            owner.addPiece(player, pieces[point.x][point.y].getType());
            pieces[point.x][point.y] = null;
            this.markedSquares[point.x][point.y] = new Object();
            repaint();
        }

        // can only add pieces to marked squares
        if (this.markedSquares[point.x][point.y] == null)
            return;

        if (pieceType == Resources.PIECE_NO_PIECE)
            return;

        this.pieces[point.x][point.y] = new Piece(this.player, pieceType, point.x, point.y);
        this.markedSquares[point.x][point.y] = null;
        eventCommunicator.sendEvent(new GameEventObject(pieceType, point.x, point.y));

        repaint();
    }

    void gameSetupClick(MouseEvent e) {
        // find the point for the click
        Point point = getClickedPoint(e);

        // get the selected setup piece
        String pieceType = owner.getSelectedSetupPiece(this.player);

        // can only click on own squares
        if (point.y < 6)
            return;

        setPieceAtPoint(point, pieceType);
    }

    /**
     * Finds out which square has been selected
     * 
     * @param e
     * @return
     */
    private Point getClickedPoint(MouseEvent e) {
        Dimension d = super.getSize();
        squareWidth = d.width / 10;
        squareHeight = d.height / 10;

        return new Point(e.getPoint().x / (int) squareWidth, e.getPoint().y / (int) squareHeight);
    }

    /**
     * Move a piece.
     * 
     * @param attacker
     * @param defender
     */
    private void movePiece(Piece attacker, int x_coord_defender, int y_coord_defender) {
        int x_coord_attacker = attacker.getLocation().x;
        int y_coord_attacker = attacker.getLocation().y;

        log.debug("Resolve movement : \nattacker=" + x_coord_attacker + ":" + y_coord_attacker + "\ndefender=" + x_coord_defender + ":" + y_coord_defender);

        Piece defender = pieces[x_coord_defender][y_coord_defender];

        /**
         * empty space, go ahead and move the piece to that location
         */
        if (defender == null && attacker != null) {
            log.debug("Moved: " + attacker.toString() + "\nto " + x_coord_defender + ":" + y_coord_defender);

            pieces[x_coord_attacker][y_coord_attacker] = null;
            pieces[x_coord_defender][y_coord_defender] = attacker;
            pieces[x_coord_defender][y_coord_defender].setLocation(x_coord_defender, y_coord_defender);
        }

        /**
         * Exists piece for the opponent player in the new position.
         */
        else if (defender != null && attacker != null && defender.getPlayer() != attacker.getPlayer()) {
            int result = attacker.resolveStrike(defender);

            // attacker wins
            if (result == Piece.RESULT_WIN) {
                pieces[x_coord_defender][y_coord_defender].destroy();
                pieces[x_coord_defender][y_coord_defender] = null;
                pieces[x_coord_defender][y_coord_defender] = pieces[x_coord_attacker][y_coord_attacker];
                pieces[x_coord_defender][y_coord_defender].setLocation(x_coord_defender, y_coord_defender);
                owner.subtractPiece(defender.getPlayer(), defender.getType());

                ResolveStrikeDialog.showStrikeResult(owner, "Player " + attacker.getPlayer() + " wins", attacker.getPlayer(), attacker.getType(),
                        defender.getPlayer(), defender.getType());
            }
            // draw
            else if (result == Piece.RESULT_DRAW) {
                pieces[x_coord_defender][y_coord_defender].destroy();
                pieces[x_coord_defender][y_coord_defender] = null;
                owner.subtractPiece(attacker.getPlayer(), attacker.getType());
                owner.subtractPiece(defender.getPlayer(), defender.getType());

                ResolveStrikeDialog.showStrikeResult(owner, "Draw", attacker.getPlayer(), attacker.getType(), defender.getPlayer(), defender.getType());
            }
            // defender wins
            else if (result == Piece.RESULT_LOOSE) {
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

            // remove the old position for the piece that moved
            pieces[x_coord_attacker][y_coord_attacker] = null;
            checkIfAnyPlayerCanMove();
        }

        clearMarkedSquares();
        System.gc();
        repaint();
    }

    /**
     * Checks if any of the players can move a piece. If not a message it displayed stating the victory of one of the players
     */
    private void checkIfAnyPlayerCanMove() {
        boolean player1Move = checkIfPlayerCanMove(1);
        boolean player2Move = checkIfPlayerCanMove(2);

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
     * Checks all pieces for a player to see if the player can move any of them
     * 
     * @param checkPlayer
     *            the player to check
     * @return
     */
    private boolean checkIfPlayerCanMove(int checkPlayer) {
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

        int x_coord = piece.getLocation().x;
        int y_coord = piece.getLocation().y;

        // check up
        for (int i = 1; i <= moveDistance; i++) {
            if (y_coord - i < 0 || ((x_coord == 2 || x_coord == 3 || x_coord == 6 || x_coord == 7) && (y_coord - i == 4 || y_coord - i == 5))
                    || (pieces[x_coord][y_coord - i] != null && pieces[x_coord][y_coord - i].getPlayer() == piece.getPlayer()))
                break;

            markedSquares[x_coord][y_coord - i] = new Object();

            if (pieces[x_coord][y_coord - i] != null && pieces[x_coord][y_coord - i].getPlayer() != piece.getPlayer())
                break;
        }

        // check down
        for (int i = 1; i <= moveDistance; i++) {
            if (y_coord + i > 9 || ((x_coord == 2 || x_coord == 3 || x_coord == 6 || x_coord == 7) && (y_coord + i == 4 || y_coord + i == 5))
                    || (pieces[x_coord][y_coord + i] != null && pieces[x_coord][y_coord + i].getPlayer() == piece.getPlayer()))
                break;

            markedSquares[x_coord][y_coord + i] = new Object();

            if (pieces[x_coord][y_coord + i] != null && pieces[x_coord][y_coord + i].getPlayer() != piece.getPlayer())
                break;
        }

        // check right
        for (int i = 1; i <= moveDistance; i++) {
            if (x_coord + i > 9 || ((x_coord + i == 2 || x_coord + i == 3 || x_coord + i == 6 || x_coord + i == 7) && (y_coord == 4 || y_coord == 5))
                    || (pieces[x_coord + i][y_coord] != null && pieces[x_coord + i][y_coord].getPlayer() == piece.getPlayer()))
                break;

            markedSquares[x_coord + i][y_coord] = new Object();

            if (pieces[x_coord + i][y_coord] != null && pieces[x_coord + i][y_coord].getPlayer() != piece.getPlayer())
                break;
        }

        // check left
        for (int i = 1; i <= moveDistance; i++) {
            if (x_coord - i < 0 || ((x_coord - i == 2 || x_coord - i == 3 || x_coord - i == 6 || x_coord - i == 7) && (y_coord == 4 || y_coord == 5))
                    || (pieces[x_coord - i][y_coord] != null && pieces[x_coord - i][y_coord].getPlayer() == piece.getPlayer()))
                break;

            markedSquares[x_coord - i][y_coord] = new Object();

            if (pieces[x_coord - i][y_coord] != null && pieces[x_coord - i][y_coord].getPlayer() != piece.getPlayer())
                break;
        }
    }

    /**
     * Clears all the highlighted squares
     */
    private void clearMarkedSquares() {
        markedSquares = null;
        markedSquares = new Object[10][10];
    }

    private class BoardPanel_this_mouseAdapter extends java.awt.event.MouseAdapter {
        private BoardPanel adaptee;
        private GameStateController gameStateObject = GameStateController.getInstance();

        private BoardPanel_this_mouseAdapter(BoardPanel adaptee) {
            this.adaptee = adaptee;
        }

        public void mouseClicked(MouseEvent e) {
            if (e.isAltDown() && e.isShiftDown() && e.isControlDown()) {
                revealOpponent = !revealOpponent;
                repaint();
            }

            if (gameStateObject.inState(GameStates.STATE_GAME_SETUP) || gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP))
                adaptee.gameSetupClick(e);
            else if (gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN))
                adaptee.inGameClick(e);
        }
    }

}