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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.ConnectException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.dmonix.battlex.Battlex;
import org.dmonix.battlex.datamodel.Board;
import org.dmonix.battlex.datamodel.Player;
import org.dmonix.battlex.event.ControlEventListener;
import org.dmonix.battlex.event.ControlEventObject;
import org.dmonix.battlex.event.ControlEvents;
import org.dmonix.battlex.event.EventCommunicator;
import org.dmonix.battlex.event.GameStateChangeListener;
import org.dmonix.battlex.event.GameStateController;
import org.dmonix.battlex.event.GameStates;
import org.dmonix.battlex.resources.Configuration;
import org.dmonix.battlex.resources.OpponentConfigurationObject;
import org.dmonix.battlex.resources.OpponentsConfigurer;
import org.dmonix.gui.DMoniXLogoLabel;
import org.dmonix.gui.RollingProgressBar;
import org.dmonix.gui.SplashPanel;
import org.dmonix.net.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Nerg
 * @version 1.0
 */
public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1078453078430886435L;
    /** The logger instance for this class */
    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    private static final int majorVersion = 1;
    private static final int minorVersion = 2;

    private static final int WIDTH_BOARD = 706;
    private static final int WIDTH_PIECESONDISPLAY = 70;

    private ControlListener controlListener = new ControlListener();
    private GameEventListener gameEventListener = new GameEventListener();
    private EventCommunicator eventCommunicator;
    private GameStateController gameStateObject = GameStateController.getInstance();
    private Player player;

    private GridBagLayout gridBagLayoutFrame = new GridBagLayout();
    private GridBagLayout gridBagLayoutStatus = new GridBagLayout();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenu menuGame = new JMenu();
    private JMenuItem menuItemNewOpponent = new JMenuItem();
    private BoardPanel boardPanel = new BoardPanel();
    private JPanel panelStatus = new JPanel();
    private JLabel lblStatus = new JLabel();
    private PiecesOnDisplayPanel piecesOnDisplayPanelPlayerRed = new PiecesOnDisplayPanel(Player.PlayerRed);
    private PiecesOnDisplayPanel piecesOnDisplayPanelPlayerBlue = new PiecesOnDisplayPanel(Player.PlayerBlue);

    private JMenu menuHelp = new JMenu();
    private JMenuItem menuItemHelp = new JMenuItem();
    private JMenuItem menuItemAbout = new JMenuItem();
    private JMenuItem menuItemNewGame = new JMenuItem();
    private JMenu menuConnectTo = new JMenu();
    private RollingProgressBar progressBar = new RollingProgressBar();

    private Configuration configuration = new Configuration();
    private JMenuItem menuItemPreferences = new JMenuItem();
    private JMenuItem menuItemDisconnect = new JMenuItem();
    private DMoniXLogoLabel lblDmonixLogo = new DMoniXLogoLabel(new File(System.getProperty("user.home") + File.separator + "dmonix" + File.separator
            + "battlex" + File.separator + "log.properties"));

    public MainFrame() throws HeadlessException {
        try {
            logger.info("STARTING");
            if (System.getProperty("battlex.nosplash") != null) {
                SplashPanel.showSplash("BattleX", majorVersion, minorVersion);
            }

            jbInit();

            menuItemDisconnect.setEnabled(false);
            this.setTitle("BattleX");
            super.setSize(WIDTH_BOARD, 704);
            // super.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            super.validate();
            super.repaint();
            super.setResizable(false);
            super.setIconImage(ImageIO.read(MainFrame.class.getResource("/images/battlex-bullet.gif")));
            gameStateObject.addStateChangeListener(gameEventListener);
            boardPanel.setOwner(this);
            piecesOnDisplayPanelPlayerRed.setOwner(this);
            piecesOnDisplayPanelPlayerBlue.setOwner(this);
            piecesOnDisplayPanelPlayerRed.setVisible(false);
            piecesOnDisplayPanelPlayerBlue.setVisible(false);
            setOpponentMenuItems();
            this.setVisible(true);
        } catch (Exception e) {
            logger.error("Error in initialization", e);
        }
    }

    /**
     * Get which player (1,2) the current player is
     * 
     * @return
     */
    public Player getCurrentPlayer() {
        return this.player;
    }

    public void setOpponentMenuItems() {
        this.menuConnectTo.removeAll();
        for (OpponentConfigurationObject oco : OpponentsConfigurer.getOpponents()) {
            this.menuConnectTo.add(new OpponentMenuItem(this, oco));
        }

    }

    public void setBoardCursor(Cursor cursor) {
        this.boardPanel.setCursor(cursor);
        this.panelStatus.setCursor(cursor);
        this.piecesOnDisplayPanelPlayerRed.setCursor(cursor);
        this.piecesOnDisplayPanelPlayerBlue.setCursor(cursor);
    }

    public String getSelectedSetupPiece(Player player) {
        return player.isPlayerRed() ? piecesOnDisplayPanelPlayerRed.getSelectedPieceType() : piecesOnDisplayPanelPlayerBlue.getSelectedPieceType();
    }

    public void sendEvent(ControlEventObject ceo) {
        this.eventCommunicator.sendEvent(ceo);
    }

    public Board getBoard() {
        return boardPanel.getBoard();
    }

    public EventCommunicator getEventCommunicator() {
        return eventCommunicator;
    }

    public void addPiece(Player player, String type) {
        if (player.isPlayerRed())
            piecesOnDisplayPanelPlayerRed.addPiece(type);
        else
            piecesOnDisplayPanelPlayerBlue.addPiece(type);
    }

    public void subtractPiece(Player player, String type) {
        if (player.isPlayerRed())
            piecesOnDisplayPanelPlayerRed.subtractPiece(type);
        else
            piecesOnDisplayPanelPlayerBlue.subtractPiece(type);
    }

    public void connectToOpponent(String host, int port, boolean useProxy, String proxy, int proxyPort) {
        try {
            this.player = Player.PlayerBlue;
            if (useProxy)
                NetUtil.setProxy(proxy, "" + proxyPort);

            this.eventCommunicator = new EventCommunicator(host, port);
            this.eventCommunicator.addEventListener(controlListener);
            this.eventCommunicator.sendEvent(ControlEvents.EVENT_CONNECT);
            this.lblStatus.setText("Connecting");
            this.progressBar.startRolling();
            super.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            menuConnectTo.setEnabled(false);
            menuItemNewGame.setEnabled(false);
            menuItemDisconnect.setEnabled(true);
        } catch (ConnectException cex) {
            JOptionPane.showMessageDialog(this, cex.getMessage(), "Failed to connect", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            logger.warn("Failed create a new game", ex);
        }
    }

    private void jbInit() throws Exception {
        setBackground(Battlex.BACKGOUND_COLOR);
        this.getContentPane().setBackground(SystemColor.control);
        this.setJMenuBar(menuBar);
        this.addWindowListener(new MainFrame_this_windowAdapter());
        this.getContentPane().setLayout(gridBagLayoutFrame);
        menuFile.setEnabled(true);
        menuFile.setText("File");
        menuGame.setText("Game");
        menuItemNewOpponent.setText("Manage opponents");
        menuItemNewOpponent.addActionListener(new MainFrame_menuItemNewOpponent_actionAdapter());
        panelStatus.setBackground(Color.white);
        panelStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        panelStatus.setLayout(gridBagLayoutStatus);
        lblStatus.setFont(new java.awt.Font("Dialog", 0, 9));
        lblStatus.setText("Disconnected");
        menuHelp.setText("Help");
        menuItemHelp.setEnabled(false);
        menuItemHelp.setText("Help");
        menuItemAbout.setText("About");
        menuItemAbout.addActionListener(new MainFrame_menuItemAbout_actionAdapter());
        menuItemNewGame.setText("New game");
        menuItemNewGame.addActionListener(new MainFrame_menuItemNewGame_actionAdapter());
        menuConnectTo.setText("Connect to");
        piecesOnDisplayPanelPlayerRed.setMinimumSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayerRed.setPreferredSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayerBlue.setMinimumSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayerBlue.setPreferredSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayerRed.setRequestFocusEnabled(true);
        menuItemPreferences.setText("Preferences");
        menuItemPreferences.addActionListener(new MainFrame_menuItemPreferences_actionAdapter());
        menuItemDisconnect.setText("Disconnect");
        menuItemDisconnect.addActionListener(new MainFrame_menuItemDisconnect_actionAdapter());
        menuBar.add(menuFile);
        menuBar.add(menuGame);
        menuBar.add(menuHelp);
        menuGame.add(menuItemNewOpponent);
        menuGame.add(menuItemNewGame);
        menuGame.add(menuConnectTo);
        menuGame.add(menuItemDisconnect);
        menuHelp.add(menuItemHelp);
        menuHelp.add(menuItemAbout);

        panelStatus.add(lblStatus, new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0),
                0, 0));
        panelStatus.add(lblDmonixLogo, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0),
                0, 0));
        panelStatus.add(progressBar, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(5, 5, 0, 5), 0, 0));

        this.getContentPane().add(piecesOnDisplayPanelPlayerRed,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(6, 6, 0, 0), 0, 0));
        this.getContentPane().add(piecesOnDisplayPanelPlayerBlue,
                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(6, 5, 0, 5), 0, 0));
        this.getContentPane().add(boardPanel,
                new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(panelStatus,
                new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 6, 0, 0), 0, 0));
        menuFile.add(menuItemPreferences);

    }

    public void menuItemNewGame_actionPerformed(ActionEvent e) {
        try {
            this.player = Player.PlayerRed;
            eventCommunicator = new EventCommunicator(Integer.parseInt(configuration.getPreference(Configuration.PREF_SERVERPORT)));
            eventCommunicator.addEventListener(controlListener);
            gameStateObject.setState(GameStates.STATE_CONNECTING);
            progressBar.startRolling();
            this.lblStatus.setText("Waiting for connection");
            super.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            menuConnectTo.setEnabled(false);
            menuItemNewGame.setEnabled(false);
            menuItemDisconnect.setEnabled(true);
        } catch (Exception ex) {
            logger.warn("Could not start new game", ex);
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not start new game", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The method is invoked from PiecesOnDisplayPanel
     * 
     * @param e
     */
    void btnSave_actionPerformed(ActionEvent e) {
        // FIXME : implement
        // JFileChooser fc = new JFileChooser();
        // fc.setCurrentDirectory(new File(Configuration.CONF_PATH, "setups"));
        // fc.setFileFilter(new FileExtensionFilter("stp", "Saved setups (*.stp)"));
        // fc.setMultiSelectionEnabled(false);
        //
        // int returnVal = fc.showSaveDialog(this);
        //
        // if (returnVal != JFileChooser.APPROVE_OPTION)
        // return;
        //
        // File f = fc.getSelectedFile();
        // if (log.isLoggable(Level.FINE))
        // log.log(Level.FINE, "Saving piece setup to " + f.getAbsolutePath());
        //
        // XMLDocument doc = new XMLDocument();
        // Element root = doc.setRootElement("setup");
        // Piece piece;
        // for (int y = 6; y < 10; y++) {
        // for (int x = 0; x < 10; x++) {
        // piece = boardPanel.getPieceAtPoint(x, y);
        // if (piece == null)
        // continue;
        //
        // Element el = doc.appendChildElement(root, "piece");
        // el.setAttribute("x", "" + x);
        // el.setAttribute("y", "" + y);
        // el.setAttribute("type", "" + piece.getType());
        // }
        // }
        // try {
        // doc.toStream(new CipherOutputStreamPBE(new FileOutputStream(f), "srC42T#mbT6&tY7".toCharArray()), true);
        // } catch (Exception ex) {
        // log.log(Level.WARNING, "Failed to save piece setup to file\n" + f.getAbsolutePath(), ex);
        // JOptionPane.showMessageDialog(this, "Could not save!\n" + ex.getMessage(), "Failed to save piece setup", JOptionPane.ERROR_MESSAGE);
        // }
    }

    /**
     * The method is invoked from PiecesOnDisplayPanel
     * 
     * @param e
     */
    void btnLoad_actionPerformed(ActionEvent e) {
        // TODO implement in the future
        // JFileChooser fc = new JFileChooser();
        // fc.setCurrentDirectory(new File(Configuration.CONF_PATH, "setups"));
        // fc.setFileFilter(new FileExtensionFilter("stp", "Saved setups (*.stp)"));
        // fc.setMultiSelectionEnabled(false);
        //
        // int returnVal = fc.showOpenDialog(this);
        //
        // if (returnVal != JFileChooser.APPROVE_OPTION)
        // return;
        //
        // File f = fc.getSelectedFile();
        // logger.debug("Loading piece setup from [{}]", f.getAbsolutePath());
        //
        // XMLDocument doc = null;
        // try {
        // doc = new XMLDocument(new CipherInputStreamPBE(new FileInputStream(f), "srC42T#mbT6&tY7".toCharArray()));
        // } catch (Exception ex) {
        // logger.warn("Failed to load piece setup from [" + f.getAbsolutePath() + "]", ex);
        // JOptionPane.showMessageDialog(this, "The file is not a valid piece setup file\n" + f.getAbsolutePath(), "Failed to load piece setup",
        // JOptionPane.ERROR_MESSAGE);
        // return;
        // }
        //
        // boardPanel.clearAllPlayerPieces();
        //
        // XMLElementList pieceList = doc.getElementsByTagName("piece");
        // String type;
        // for (XMLElement piece : pieceList) {
        // type = piece.getAttribute("type");
        // this.subtractPiece(player, type);
        // Point point = new Point(Integer.parseInt(piece.getAttribute("x")), Integer.parseInt(piece.getAttribute("y")));
        // this.boardPanel.setPieceAtPoint(point, type);
        // }
    }

    void menuItemAbout_actionPerformed(ActionEvent e) {
        new SplashPanel("BattleX", majorVersion, minorVersion, true);
    }

    void menuItemPreferences_actionPerformed(ActionEvent e) {
        PreferencesOptionPane pane = new PreferencesOptionPane(this, this.configuration);
        pane.setVisible(true);
    }

    void menuItemDisconnect_actionPerformed(ActionEvent e) {
        if (eventCommunicator != null && eventCommunicator.isConnected())
            eventCommunicator.sendEvent(ControlEvents.EVENT_DISCONNECT);

        super.setCursor(Cursor.getDefaultCursor());
        this.setBoardCursor(Cursor.getDefaultCursor());
        this.progressBar.stopRolling();
        this.lblStatus.setText("Disconnected");
        this.piecesOnDisplayPanelPlayerRed.setVisible(false);
        this.piecesOnDisplayPanelPlayerBlue.setVisible(false);

        menuConnectTo.setEnabled(true);
        menuItemNewGame.setEnabled(true);
        menuItemDisconnect.setEnabled(false);
        eventCommunicator.disconnect();
        eventCommunicator = null;
    }

    /**
     * Internal class for managing control events
     * <p>
     * Copyright: Copyright (c) 2004-2005
     * </p>
     * <p>
     * Company: dmonix.org
     * </p>
     * 
     * @author Peter Nerg
     * @version 1.0
     */
    private class ControlListener implements ControlEventListener {
        /**
         * Receive a control event
         * 
         * @param ceo
         */
        public void controlEvent(ControlEventObject ceo) {
            try {
                logger.debug("Received control event [{}]", ceo);

                /**
                 * CONNECT RECEIVED
                 */
                if (ControlEvents.EVENT_CONNECT.equals(ceo)) {
                    gameStateObject.setState(GameStates.STATE_GAME_SETUP);
                    eventCommunicator.sendEvent(ControlEvents.EVENT_ACK_CONNECT);
                }

                /**
                 * ACK CONNECT RECEIVED
                 */
                else if (ControlEvents.EVENT_ACK_CONNECT.equals(ceo)) {
                    gameStateObject.setState(GameStates.STATE_GAME_SETUP);
                }

                /**
                 * SETUP RECEIVED
                 */
                else if (ControlEvents.EVENT_SETUP_SENT.equals(ceo)) {
                    boardPanel.repaint();
                    if (gameStateObject.inState(GameStates.STATE_GAME_SETUP))
                        gameStateObject.setState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP);
                    else if (gameStateObject.inState(GameStates.STATE_SETUP_WAIT_OPPONENT_SETUP))
                        gameStateObject.setState(GameStates.STATE_IN_GAME);
                    else
                        throw new IllegalStateException("The state " + gameStateObject.getState() + " is not allowed here");
                }

                /**
                 * DISCONNECT RECEIVED
                 */
                else if (ControlEvents.EVENT_DISCONNECT.equals(ceo)) {
                    setBoardCursor(Cursor.getDefaultCursor());
                    progressBar.stopRolling();
                    lblStatus.setText("Disconnected");
                    piecesOnDisplayPanelPlayerRed.setVisible(false);
                    piecesOnDisplayPanelPlayerBlue.setVisible(false);

                    menuConnectTo.setEnabled(true);
                    menuItemNewGame.setEnabled(true);
                    menuItemDisconnect.setEnabled(false);
                    eventCommunicator = null;
                }

                else {
                    logger.warn("Unrecognized command [{}]", ceo);
                }

            } catch (Exception ex) {
                logger.warn("Failed create a new game", ex);
            }
        }
    }

    /**
     * Internal class for managing game events
     * <p>
     * Copyright: Copyright (c) 2004-2005
     * </p>
     * <p>
     * Company: dmonix.org
     * </p>
     * 
     * @author Peter Nerg
     * @version 1.0
     */
    private class GameEventListener implements GameStateChangeListener {
        /**
         * Handles the state changed event
         * 
         * @param oldState
         * @param newState
         */
        public void stateChanged(int oldState, int newState) {
            logger.debug("Changed state from [{}] to [{}]", oldState, newState);
            /**
             * STATE_GAME_SETUP
             */
            if (gameStateObject.inState(GameStates.STATE_GAME_SETUP)) {
                progressBar.stopRolling();
                lblStatus.setText("Connected");
                setBoardCursor(Cursor.getDefaultCursor());
                boardPanel.newGame(player, eventCommunicator);

                if (player.isPlayerRed()) {
                    piecesOnDisplayPanelPlayerRed.setVisible(true);
                    piecesOnDisplayPanelPlayerRed.showButtons();
                } else {
                    piecesOnDisplayPanelPlayerBlue.setVisible(true);
                    piecesOnDisplayPanelPlayerBlue.showButtons();
                }
                setSize(WIDTH_BOARD + WIDTH_PIECESONDISPLAY, 704);
                validate();
            }

            /**
             * STATE_GAME_SETUP_RECEIVED_SETUP
             */
            else if (gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP)) {
                lblStatus.setText("Received opponent setup");
            }

            /**
             * STATE_SETUP_WAIT_OPPONENT_SETUP
             */
            else if (gameStateObject.inState(GameStates.STATE_SETUP_WAIT_OPPONENT_SETUP)) {
                progressBar.startRolling();
                lblStatus.setText("Waiting for opponent");
                setBoardCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            /**
             * STATE_IN_GAME
             */
            else if (gameStateObject.inState(GameStates.STATE_IN_GAME)) {
                progressBar.stopRolling();
                lblStatus.setText("Connected");
                setBoardCursor(Cursor.getDefaultCursor());

                piecesOnDisplayPanelPlayerRed.resetLabels();
                piecesOnDisplayPanelPlayerBlue.resetLabels();
                piecesOnDisplayPanelPlayerRed.setVisible(true);
                piecesOnDisplayPanelPlayerBlue.setVisible(true);
                setSize(WIDTH_BOARD + WIDTH_PIECESONDISPLAY * 2, 704);
                validate();

                if (player.isPlayerRed())
                    gameStateObject.setState(GameStates.STATE_IN_GAME_PLAYER_TURN);
                else
                    gameStateObject.setState(GameStates.STATE_IN_GAME_OPPONENT_TURN);
            }

            /**
             * STATE_IN_GAME_PLAYER_TURN
             */
            else if (gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN)) {
                progressBar.stopRolling();
                lblStatus.setText("Your turn");
                setBoardCursor(Cursor.getDefaultCursor());
            }

            /**
             * STATE_IN_GAME_OPPONENT_TURN
             */
            else if (gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN)) {
                progressBar.startRolling();
                lblStatus.setText("Waiting for opponent move");
                setBoardCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            /**
             * Unhandled event type
             */
            else {
                logger.warn("Unhandled state [{}]", newState);
            }
        }
    }

    // /////////////////////////////
    //
    // Internal event adapters
    //
    // /////////////////////////////

    class MainFrame_this_windowAdapter extends java.awt.event.WindowAdapter {
        public void windowClosing(WindowEvent e) {
            setVisible(false);
            dispose();
            System.exit(0);
        }
    }

    class MainFrame_menuItemNewOpponent_actionAdapter implements java.awt.event.ActionListener {
        public void actionPerformed(ActionEvent e) {
            OpponentOptionPane pane = new OpponentOptionPane(MainFrame.this, configuration);
            pane.setVisible(true);
        }
    }

    class MainFrame_menuItemNewGame_actionAdapter implements java.awt.event.ActionListener {
        public void actionPerformed(ActionEvent e) {
            menuItemNewGame_actionPerformed(e);
        }
    }

    class MainFrame_menuItemAbout_actionAdapter implements java.awt.event.ActionListener {
        public void actionPerformed(ActionEvent e) {
            menuItemAbout_actionPerformed(e);
        }
    }

    class MainFrame_menuItemPreferences_actionAdapter implements java.awt.event.ActionListener {
        public void actionPerformed(ActionEvent e) {
            menuItemPreferences_actionPerformed(e);
        }
    }

    class MainFrame_menuItemDisconnect_actionAdapter implements java.awt.event.ActionListener {
        public void actionPerformed(ActionEvent e) {
            menuItemDisconnect_actionPerformed(e);
        }
    }
}
