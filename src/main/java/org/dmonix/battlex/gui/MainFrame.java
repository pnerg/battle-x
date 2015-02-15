package org.dmonix.battlex.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.net.ConnectException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.dmonix.battlex.Battlex;
import org.dmonix.battlex.event.ControlEventListener;
import org.dmonix.battlex.event.ControlEventObject;
import org.dmonix.battlex.event.EventCommunicator;
import org.dmonix.battlex.event.GameStateChangeListener;
import org.dmonix.battlex.event.GameStateController;
import org.dmonix.battlex.resources.Configuration;
import org.dmonix.battlex.resources.OpponentConfigurationObject;
import org.dmonix.cipher.CipherInputStreamPBE;
import org.dmonix.gui.DMoniXLogoLabel;
import org.dmonix.gui.RollingProgressBar;
import org.dmonix.gui.SplashPanel;
import org.dmonix.io.filters.FileExtensionFilter;
import org.dmonix.net.NetUtil;
import org.dmonix.xml.XMLDocument;
import org.dmonix.xml.XMLElement;
import org.dmonix.xml.XMLElementList;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003-2005
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @version 1.0
 */
public class MainFrame extends JFrame {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(MainFrame.class.getName());
    private static final int majorVersion = 1;
    private static final int minorVersion = 2;

    private static final int WIDTH_BOARD = 706;
    private static final int WIDTH_PIECESONDISPLAY = 70;

    private ControlListener controlListener = new ControlListener();
    private GameEventListener gameEventListener = new GameEventListener();
    private EventCommunicator eventCommunicator;
    private GameStateController gameStateObject = GameStateController.getInstance();
    private int player;

    private GridBagLayout gridBagLayoutFrame = new GridBagLayout();
    private GridBagLayout gridBagLayoutStatus = new GridBagLayout();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenu menuGame = new JMenu();
    private JMenuItem menuItemNewOpponent = new JMenuItem();
    private BoardPanel boardPanel = new BoardPanel();
    private JPanel panelStatus = new JPanel();
    private JLabel lblStatus = new JLabel();
    private PiecesOnDisplayPanel piecesOnDisplayPanelPlayer1 = new PiecesOnDisplayPanel(1);
    private PiecesOnDisplayPanel piecesOnDisplayPanelPlayer2 = new PiecesOnDisplayPanel(2);

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
            SplashPanel.showSplash("BattleX", majorVersion, minorVersion);

            jbInit();

            menuItemDisconnect.setEnabled(false);
            this.setTitle("BattleX");
            super.setSize(WIDTH_BOARD, 704);
            // super.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            super.validate();
            super.repaint();
            super.setResizable(false);
            super.setIconImage(ImageIO.read(MainFrame.class.getResource("/org/dmonix/battlex/images/battlex-bullet.gif")));
            gameStateObject.addStateChangeListener(gameEventListener);
            boardPanel.setOwner(this);
            piecesOnDisplayPanelPlayer1.setOwner(this);
            piecesOnDisplayPanelPlayer2.setOwner(this);
            piecesOnDisplayPanelPlayer1.setVisible(false);
            piecesOnDisplayPanelPlayer2.setVisible(false);
            setOpponentMenuItems();
            this.setVisible(true);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error in initialization", e);
        }
    }

    /**
     * Get which player (1,2) the current player is
     * 
     * @return
     */
    public int getCurrentPlayer() {
        return this.player;
    }

    public void setOpponentMenuItems() {
        this.menuConnectTo.removeAll();
        Enumeration enumeration = configuration.getOpponents();
        while (enumeration.hasMoreElements()) {
            this.menuConnectTo.add(new OpponentMenuItem(this, (OpponentConfigurationObject) enumeration.nextElement()));
        }

    }

    public void setBoardCursor(Cursor cursor) {
        this.boardPanel.setCursor(cursor);
        this.panelStatus.setCursor(cursor);
        this.piecesOnDisplayPanelPlayer1.setCursor(cursor);
        this.piecesOnDisplayPanelPlayer2.setCursor(cursor);
    }

    public int getSelectedSetupPiece(int player) {
        if (player == 1)
            return piecesOnDisplayPanelPlayer1.getSelectedPieceType();
        else
            return piecesOnDisplayPanelPlayer2.getSelectedPieceType();
    }

    public void sendEvent(ControlEventObject ceo) {
        this.eventCommunicator.sendEvent(ceo);
    }

    public void addPiece(int player, int type) {
        if (player == 1)
            piecesOnDisplayPanelPlayer1.addPiece(type);
        else
            piecesOnDisplayPanelPlayer2.addPiece(type);
    }

    public void subtractPiece(int player, int type) {
        if (player == 1)
            piecesOnDisplayPanelPlayer1.subtractPiece(type);
        else
            piecesOnDisplayPanelPlayer2.subtractPiece(type);
    }

    public void connectToOpponent(String host, int port, boolean useProxy, String proxy, int proxyPort) {
        try {
            this.player = 2;
            if (useProxy)
                NetUtil.setProxy(proxy, "" + proxyPort);

            this.eventCommunicator = new EventCommunicator(host, port);
            this.eventCommunicator.addEventListener(controlListener);
            this.eventCommunicator.sendEvent(ControlEventObject.EVENT_CONNECT);
            this.lblStatus.setText("Connecting");
            this.progressBar.startRolling();
            super.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            menuConnectTo.setEnabled(false);
            menuItemNewGame.setEnabled(false);
            menuItemDisconnect.setEnabled(true);
        } catch (ConnectException cex) {
            JOptionPane.showMessageDialog(this, cex.getMessage(), "Failed to connect", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed create a new game", ex);
        }
    }

    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().readConfiguration(MainFrame.class.getResourceAsStream("/log.properties"));
        // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        new MainFrame();
    }

    private void jbInit() throws Exception {
        setBackground(Battlex.BACKGOUND_COLOR);
        this.getContentPane().setBackground(SystemColor.control);
        this.setJMenuBar(menuBar);
        this.addWindowListener(new MainFrame_this_windowAdapter(this));
        this.getContentPane().setLayout(gridBagLayoutFrame);
        menuFile.setEnabled(true);
        menuFile.setText("File");
        menuGame.setText("Game");
        menuItemNewOpponent.setText("Manage opponents");
        menuItemNewOpponent.addActionListener(new MainFrame_menuItemNewOpponent_actionAdapter(this));
        panelStatus.setBackground(Color.white);
        panelStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        panelStatus.setLayout(gridBagLayoutStatus);
        lblStatus.setFont(new java.awt.Font("Dialog", 0, 9));
        lblStatus.setText("Disconnected");
        menuHelp.setText("Help");
        menuItemHelp.setEnabled(false);
        menuItemHelp.setText("Help");
        menuItemAbout.setText("About");
        menuItemAbout.addActionListener(new MainFrame_menuItemAbout_actionAdapter(this));
        menuItemNewGame.setText("New game");
        menuItemNewGame.addActionListener(new MainFrame_menuItemNewGame_actionAdapter(this));
        menuConnectTo.setText("Connect to");
        piecesOnDisplayPanelPlayer1.setMinimumSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayer1.setPreferredSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayer2.setMinimumSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayer2.setPreferredSize(new Dimension(90, 75));
        piecesOnDisplayPanelPlayer1.setRequestFocusEnabled(true);
        menuItemPreferences.setText("Preferences");
        menuItemPreferences.addActionListener(new MainFrame_menuItemPreferences_actionAdapter(this));
        menuItemDisconnect.setText("Disconnect");
        menuItemDisconnect.addActionListener(new MainFrame_menuItemDisconnect_actionAdapter(this));
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

        this.getContentPane().add(piecesOnDisplayPanelPlayer1,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(6, 6, 0, 0), 0, 0));
        this.getContentPane().add(piecesOnDisplayPanelPlayer2,
                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(6, 5, 0, 5), 0, 0));
        this.getContentPane().add(boardPanel,
                new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(panelStatus,
                new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 6, 0, 0), 0, 0));
        menuFile.add(menuItemPreferences);

    }

    void this_windowClosing(WindowEvent e) {
        super.hide();
        super.dispose();
        System.gc();
        System.exit(0);
    }

    void newOpponent_actionPerformed(ActionEvent e) {
        OpponentOptionPane pane = new OpponentOptionPane(this, this.configuration);
        pane.show();
    }

    public void menuItemNewGame_actionPerformed(ActionEvent e) {
        try {
            this.player = 1;
            eventCommunicator = new EventCommunicator(Integer.parseInt(configuration.getPreference(Configuration.PREF_SERVERPORT)));
            eventCommunicator.addEventListener(controlListener);
            gameStateObject.setState(GameStateController.STATE_CONNECTING);
            progressBar.startRolling();
            this.lblStatus.setText("Waiting for connection");
            super.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            menuConnectTo.setEnabled(false);
            menuItemNewGame.setEnabled(false);
            menuItemDisconnect.setEnabled(true);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Could not start new game", ex);
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
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(Configuration.CONF_PATH, "setups"));
        fc.setFileFilter(new FileExtensionFilter("stp", "Saved setups (*.stp)"));
        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(this);

        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;

        File f = fc.getSelectedFile();
        if (log.isLoggable(Level.FINE))
            log.log(Level.FINE, "Loading piece setup from " + f.getAbsolutePath());

        XMLDocument doc = null;
        try {
            doc = new XMLDocument(new CipherInputStreamPBE(new FileInputStream(f), "srC42T#mbT6&tY7".toCharArray()));
        } catch (Exception ex) {
            log.log(Level.WARNING, "Failed to load piece setup from\n" + f.getAbsolutePath(), ex);
            JOptionPane.showMessageDialog(this, "The file is not a valid piece setup file\n" + f.getAbsolutePath(), "Failed to load piece setup",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boardPanel.clearAllPlayerPieces();

        XMLElementList pieceList = doc.getElementsByTagName("piece");
        int type;
        for (XMLElement piece : pieceList) {
            type = Integer.parseInt(piece.getAttribute("type"));
            this.subtractPiece(player, type);
            Point point = new Point(Integer.parseInt(piece.getAttribute("x")), Integer.parseInt(piece.getAttribute("y")));
            this.boardPanel.setPieceAtPoint(point, type);
        }
    }

    void menuItemAbout_actionPerformed(ActionEvent e) {
        new SplashPanel("BattleX", majorVersion, minorVersion, true);
    }

    void menuItemPreferences_actionPerformed(ActionEvent e) {
        PreferencesOptionPane pane = new PreferencesOptionPane(this, this.configuration);
        pane.show();
    }

    void menuItemDisconnect_actionPerformed(ActionEvent e) {
        if (eventCommunicator != null && eventCommunicator.isConnected())
            eventCommunicator.sendEvent(ControlEventObject.EVENT_DISCONNECT);

        super.setCursor(Cursor.getDefaultCursor());
        this.setBoardCursor(Cursor.getDefaultCursor());
        this.progressBar.stopRolling();
        this.lblStatus.setText("Disconnected");
        this.piecesOnDisplayPanelPlayer1.setVisible(false);
        this.piecesOnDisplayPanelPlayer2.setVisible(false);

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
                if (log.isLoggable(Level.FINER))
                    log.log(Level.FINER, "Received control event\n" + ceo.toString());

                /**
                 * CONNECT RECEIVED
                 */
                if (ceo.getCommand() == ControlEventObject.CMD_CONNECT) {
                    gameStateObject.setState(GameStateController.STATE_GAME_SETUP);
                    eventCommunicator.sendEvent(ControlEventObject.EVENT_ACK_CONNECT);
                }

                /**
                 * ACK CONNECT RECEIVED
                 */
                else if (ceo.getCommand() == ControlEventObject.CMD_ACK_CONNECT) {
                    gameStateObject.setState(GameStateController.STATE_GAME_SETUP);
                }

                /**
                 * SETUP RECEIVED
                 */
                else if (ceo.getCommand() == ControlEventObject.CMD_SETUP_SENT) {
                    boardPanel.repaint();
                    if (gameStateObject.inState(GameStateController.STATE_GAME_SETUP))
                        gameStateObject.setState(GameStateController.STATE_GAME_SETUP_RECEIVED_SETUP);
                    else if (gameStateObject.inState(GameStateController.STATE_SETUP_WAIT_OPPONENT_SETUP))
                        gameStateObject.setState(GameStateController.STATE_IN_GAME);
                    else
                        throw new IllegalStateException("The state " + gameStateObject.getState() + " is not allowed here");
                }

                /**
                 * DISCONNECT RECEIVED
                 */
                else if (ceo.getCommand() == ControlEventObject.CMD_DISCONNECT) {
                    setBoardCursor(Cursor.getDefaultCursor());
                    progressBar.stopRolling();
                    lblStatus.setText("Disconnected");
                    piecesOnDisplayPanelPlayer1.setVisible(false);
                    piecesOnDisplayPanelPlayer2.setVisible(false);

                    menuConnectTo.setEnabled(true);
                    menuItemNewGame.setEnabled(true);
                    menuItemDisconnect.setEnabled(false);
                    eventCommunicator = null;
                }

                else
                    log.log(Level.WARNING, "Unrecognized command " + ceo);

            } catch (Exception ex) {
                log.log(Level.WARNING, "Failed create a new game", ex);
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
            if (log.isLoggable(Level.FINER))
                log.log(Level.FINER, "Changed state from " + oldState + " to " + newState);

            /**
             * STATE_GAME_SETUP
             */
            if (gameStateObject.inState(GameStateController.STATE_GAME_SETUP)) {
                progressBar.stopRolling();
                lblStatus.setText("Connected");
                setBoardCursor(Cursor.getDefaultCursor());
                boardPanel.newGame(player, eventCommunicator);

                if (player == 1) {
                    piecesOnDisplayPanelPlayer1.setVisible(true);
                    piecesOnDisplayPanelPlayer1.showButtons();
                } else {
                    piecesOnDisplayPanelPlayer2.setVisible(true);
                    piecesOnDisplayPanelPlayer2.showButtons();
                }
                setSize(WIDTH_BOARD + WIDTH_PIECESONDISPLAY, 704);
                validate();
            }

            /**
             * STATE_GAME_SETUP_RECEIVED_SETUP
             */
            else if (gameStateObject.inState(GameStateController.STATE_GAME_SETUP_RECEIVED_SETUP)) {
                lblStatus.setText("Received opponent setup");
            }

            /**
             * STATE_SETUP_WAIT_OPPONENT_SETUP
             */
            else if (gameStateObject.inState(GameStateController.STATE_SETUP_WAIT_OPPONENT_SETUP)) {
                progressBar.startRolling();
                lblStatus.setText("Waiting for opponent");
                setBoardCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            /**
             * STATE_IN_GAME
             */
            else if (gameStateObject.inState(GameStateController.STATE_IN_GAME)) {
                progressBar.stopRolling();
                lblStatus.setText("Connected");
                setBoardCursor(Cursor.getDefaultCursor());

                piecesOnDisplayPanelPlayer1.resetLabels();
                piecesOnDisplayPanelPlayer2.resetLabels();
                piecesOnDisplayPanelPlayer1.setVisible(true);
                piecesOnDisplayPanelPlayer2.setVisible(true);
                setSize(WIDTH_BOARD + WIDTH_PIECESONDISPLAY * 2, 704);
                validate();

                if (player == 1)
                    gameStateObject.setState(GameStateController.STATE_IN_GAME_PLAYER_TURN);
                else
                    gameStateObject.setState(GameStateController.STATE_IN_GAME_OPPONENT_TURN);
            }

            /**
             * STATE_IN_GAME_PLAYER_TURN
             */
            else if (gameStateObject.inState(GameStateController.STATE_IN_GAME_PLAYER_TURN)) {
                progressBar.stopRolling();
                lblStatus.setText("Your turn");
                setBoardCursor(Cursor.getDefaultCursor());
            }

            /**
             * STATE_IN_GAME_OPPONENT_TURN
             */
            else if (gameStateObject.inState(GameStateController.STATE_IN_GAME_OPPONENT_TURN)) {
                progressBar.startRolling();
                lblStatus.setText("Waiting for opponent move");
                setBoardCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            /**
             * Unhandled event type
             */
            else
                log.log(Level.WARNING, "Unhandled state : " + newState);
        }
    }

}

// /////////////////////////////
//
// Internal event adapters
//
// /////////////////////////////

class MainFrame_this_windowAdapter extends java.awt.event.WindowAdapter {
    MainFrame adaptee;

    MainFrame_this_windowAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }
}

class MainFrame_menuItemNewOpponent_actionAdapter implements java.awt.event.ActionListener {
    MainFrame adaptee;

    MainFrame_menuItemNewOpponent_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.newOpponent_actionPerformed(e);
    }
}

class MainFrame_menuItemNewGame_actionAdapter implements java.awt.event.ActionListener {
    private MainFrame adaptee;

    MainFrame_menuItemNewGame_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.menuItemNewGame_actionPerformed(e);
    }
}

class MainFrame_menuItemAbout_actionAdapter implements java.awt.event.ActionListener {
    private MainFrame adaptee;

    MainFrame_menuItemAbout_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.menuItemAbout_actionPerformed(e);
    }
}

class MainFrame_menuItemPreferences_actionAdapter implements java.awt.event.ActionListener {
    private MainFrame adaptee;

    MainFrame_menuItemPreferences_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.menuItemPreferences_actionPerformed(e);
    }
}

class MainFrame_menuItemDisconnect_actionAdapter implements java.awt.event.ActionListener {
    private MainFrame adaptee;

    MainFrame_menuItemDisconnect_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.menuItemDisconnect_actionPerformed(e);
    }
}
