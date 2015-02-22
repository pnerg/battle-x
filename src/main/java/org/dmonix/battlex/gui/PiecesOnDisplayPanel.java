package org.dmonix.battlex.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.dmonix.battlex.Battlex;
import org.dmonix.battlex.event.ControlEventObject;
import org.dmonix.battlex.event.GameStateController;
import org.dmonix.battlex.event.GameStates;
import org.dmonix.battlex.resources.Resources;
import org.dmonix.util.ImageLoaderUtil;

/**
 * The panel for displaying one players available/captured pieces.
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @version 1.0
 */
public class PiecesOnDisplayPanel extends JPanel {
    private MainFrame owner;
    private LabelTypeIcon currentLabel;
    private int pieceCount = Resources.TOTAL_PIECES;
    private GameStateController gameStateObject = GameStateController.getInstance();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private TitledBorder titledBorder;
    private LabelTypeIcon lblType0Icon;
    private JLabel lblType0Counter = new JLabel();
    private LabelTypeIcon lblType1Icon;
    private JLabel lblType1Counter = new JLabel();
    private LabelTypeIcon lblType2Icon;
    private JLabel lblType2Counter = new JLabel();
    private LabelTypeIcon lblType3Icon;
    private JLabel lblType3Counter = new JLabel();
    private LabelTypeIcon lblType4Icon;
    private JLabel lblType4Counter = new JLabel();
    private LabelTypeIcon lblType5Icon;
    private JLabel lblType5Counter = new JLabel();
    private LabelTypeIcon lblType6Icon;
    private JLabel lblType6Counter = new JLabel();
    private LabelTypeIcon lblType7Icon;
    private JLabel lblType7Counter = new JLabel();
    private LabelTypeIcon lblType8Icon;
    private JLabel lblType8Counter = new JLabel();
    private LabelTypeIcon lblType9Icon;
    private JLabel lblType9Counter = new JLabel();
    private LabelTypeIcon lblType10Icon;
    private JLabel lblType10Counter = new JLabel();
    private LabelTypeIcon lblType11Icon;
    private JLabel lblType11Counter = new JLabel();

    private Hashtable<String, JLabel> pieces = new Hashtable<>();
    private JButton btnOk = new JButton(ImageLoaderUtil.getImageIcon(ImageLoaderUtil.PATH_TOOLBARBUTTONGRAPHICS_GENERAL, "SendMail24.gif"));
    private PiecesOnDisplayPanel_lblTypeIcon_mouseAdapter mouseAdapter;
    private JButton btnSave = new JButton(ImageLoaderUtil.getImageIcon(ImageLoaderUtil.PATH_TOOLBARBUTTONGRAPHICS_GENERAL, "Save24.gif"));
    private JButton btnLoad = new JButton(ImageLoaderUtil.getImageIcon(ImageLoaderUtil.PATH_TOOLBARBUTTONGRAPHICS_GENERAL, "Open24.gif"));

    public PiecesOnDisplayPanel(int player) {
        try {
            mouseAdapter = new PiecesOnDisplayPanel_lblTypeIcon_mouseAdapter();
            btnOk.setEnabled(false);
            lblType0Icon = new LabelTypeIcon(player, Resources.PIECE_BOMB_TYPE);
            lblType1Icon = new LabelTypeIcon(player, Resources.PIECE_MARSHAL_TYPE);
            lblType2Icon = new LabelTypeIcon(player, Resources.PIECE_GENERAL_TYPE);
            lblType3Icon = new LabelTypeIcon(player, Resources.PIECE_COLONEL_TYPE);
            lblType4Icon = new LabelTypeIcon(player, Resources.PIECE_MAJOR_TYPE);
            lblType5Icon = new LabelTypeIcon(player, Resources.PIECE_CAPTAIN_TYPE);
            lblType6Icon = new LabelTypeIcon(player, Resources.PIECE_LIEUTENANT_TYPE);
            lblType7Icon = new LabelTypeIcon(player, Resources.PIECE_SERGEANT_TYPE);
            lblType8Icon = new LabelTypeIcon(player, Resources.PIECE_MINER_TYPE);
            lblType9Icon = new LabelTypeIcon(player, Resources.PIECE_SCOUT_TYPE);
            lblType10Icon = new LabelTypeIcon(player, Resources.PIECE_SPY_TYPE);
            lblType11Icon = new LabelTypeIcon(player, Resources.PIECE_FLAG_TYPE);

            jbInit();

            titledBorder.setTitle("Player " + player);
            pieces.put("lblType0Counter", lblType0Counter);
            pieces.put("lblType1Counter", lblType1Counter);
            pieces.put("lblType2Counter", lblType2Counter);
            pieces.put("lblType3Counter", lblType3Counter);
            pieces.put("lblType4Counter", lblType4Counter);
            pieces.put("lblType5Counter", lblType5Counter);
            pieces.put("lblType6Counter", lblType6Counter);
            pieces.put("lblType7Counter", lblType7Counter);
            pieces.put("lblType8Counter", lblType8Counter);
            pieces.put("lblType9Counter", lblType9Counter);
            pieces.put("lblType10Counter", lblType10Counter);
            pieces.put("lblType11Counter", lblType11Counter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOwner(MainFrame owner) {
        this.owner = owner;
        btnSave.addActionListener(new PiecesOnDisplayPanel_btnSave_actionAdapter(owner));
        btnLoad.addActionListener(new PiecesOnDisplayPanel_btnLoad_actionAdapter(owner));
    }

    public void showButtons() {
        btnSave.setVisible(true);
        btnLoad.setVisible(true);
    }

    private void jbInit() throws Exception {
        btnSave.setVisible(false);
        btnLoad.setVisible(false);
        titledBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "Player 1");
        this.setLayout(gridBagLayout1);
        lblType0Counter.setText("" + Resources.PIECE_BOMB_COUNT);
        lblType1Counter.setText("" + Resources.PIECE_MARSHAL_COUNT);
        lblType2Counter.setText("" + Resources.PIECE_GENERAL_COUNT);
        lblType3Counter.setText("" + Resources.PIECE_COLONEL_COUNT);
        lblType4Counter.setText("" + Resources.PIECE_MAJOR_COUNT);
        lblType5Counter.setText("" + Resources.PIECE_CAPTAIN_COUNT);
        lblType6Counter.setText("" + Resources.PIECE_LIEUTENANT_COUNT);
        lblType7Counter.setText("" + Resources.PIECE_SERGEANT_COUNT);
        lblType8Counter.setText("" + Resources.PIECE_MINER_COUNT);
        lblType9Counter.setText("" + Resources.PIECE_SCOUT_COUNT);
        lblType10Counter.setText("" + Resources.PIECE_SPY_COUNT);
        lblType11Counter.setText("" + Resources.PIECE_FLAG_COUNT);
        this.setBackground(Battlex.BACKGOUND_COLOR);
        this.setBorder(titledBorder);

        btnOk.addActionListener(new PiecesOnDisplayPanel_btnOk_actionAdapter(this));
        btnOk.setMargin(new Insets(2, 14, 2, 14));
        btnOk.setMnemonic('0');
        btnOk.setBackground(new Color(141, 167, 118));
        btnOk.setBorder(null);
        btnLoad.setBorder(null);
        btnSave.setBorder(null);
        btnOk.setMaximumSize(new Dimension(47, 25));
        btnOk.setMinimumSize(new Dimension(47, 30));
        btnOk.setPreferredSize(new Dimension(47, 30));
        btnOk.setToolTipText("Confirm setup");
        btnSave.setBackground(new Color(141, 167, 118));
        btnSave.setMinimumSize(new Dimension(47, 30));
        btnSave.setPreferredSize(new Dimension(47, 30));
        btnSave.setToolTipText("Save piece setup");
        btnSave.setActionCommand("btnSave");
        btnSave.setText("");
        btnLoad.setBackground(new Color(141, 167, 118));
        btnLoad.setMinimumSize(new Dimension(47, 30));
        btnLoad.setPreferredSize(new Dimension(47, 30));
        btnLoad.setToolTipText("Load piece setup");
        btnLoad.setActionCommand("btnLoad");
        btnLoad.setText("");
        this.add(lblType11Icon, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0,
                0));
        this.add(lblType11Counter, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0,
                0));
        this.add(lblType0Icon,
                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType0Counter,
                new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType1Icon,
                new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType1Counter,
                new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType2Icon,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType2Counter,
                new GridBagConstraints(0, 3, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType3Icon,
                new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType3Counter,
                new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType4Icon,
                new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType4Counter,
                new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType5Icon,
                new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType5Counter,
                new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType6Icon,
                new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType6Counter,
                new GridBagConstraints(0, 7, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType7Icon,
                new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType7Counter,
                new GridBagConstraints(0, 8, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType8Icon,
                new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType8Counter,
                new GridBagConstraints(0, 9, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        this.add(lblType9Icon, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0,
                0));
        this.add(lblType9Counter, new GridBagConstraints(0, 10, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0,
                0));
        this.add(lblType10Icon, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0,
                0));
        this.add(lblType10Counter, new GridBagConstraints(0, 11, 3, 1, 1.0, 1.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 0,
                0));
        this.add(btnOk, new GridBagConstraints(2, 12, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 6, 6), 0, 0));
        this.add(btnSave, new GridBagConstraints(1, 12, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 6, 6), 0, 0));
        this.add(btnLoad, new GridBagConstraints(0, 12, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 10, 6, 6), 0, 0));
    }

    public int getSelectedPieceType() {
        if (currentLabel == null)
            return -1;

        int pieceType = currentLabel.type;
        subtractPiece(pieceType);
        currentLabel.setBorder(null);
        currentLabel = null;
        owner.setBoardCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        btnSave.setVisible(true);
        btnLoad.setVisible(true);

        return pieceType;
    }

    public void resetLabels() {
        btnOk.setVisible(false);
        lblType0Icon.setEnabled(true);
        lblType1Icon.setEnabled(true);
        lblType2Icon.setEnabled(true);
        lblType3Icon.setEnabled(true);
        lblType4Icon.setEnabled(true);
        lblType5Icon.setEnabled(true);
        lblType6Icon.setEnabled(true);
        lblType7Icon.setEnabled(true);
        lblType8Icon.setEnabled(true);
        lblType9Icon.setEnabled(true);
        lblType10Icon.setEnabled(true);
        lblType11Icon.setEnabled(true);

        lblType0Counter.setEnabled(true);
        lblType1Counter.setEnabled(true);
        lblType2Counter.setEnabled(true);
        lblType3Counter.setEnabled(true);
        lblType4Counter.setEnabled(true);
        lblType5Counter.setEnabled(true);
        lblType6Counter.setEnabled(true);
        lblType7Counter.setEnabled(true);
        lblType8Counter.setEnabled(true);
        lblType9Counter.setEnabled(true);
        lblType10Counter.setEnabled(true);
        lblType11Counter.setEnabled(true);

        lblType0Counter.setText("" + Resources.PIECE_BOMB_COUNT);
        lblType1Counter.setText("" + Resources.PIECE_MARSHAL_COUNT);
        lblType2Counter.setText("" + Resources.PIECE_GENERAL_COUNT);
        lblType3Counter.setText("" + Resources.PIECE_COLONEL_COUNT);
        lblType4Counter.setText("" + Resources.PIECE_MAJOR_COUNT);
        lblType5Counter.setText("" + Resources.PIECE_CAPTAIN_COUNT);
        lblType6Counter.setText("" + Resources.PIECE_LIEUTENANT_COUNT);
        lblType7Counter.setText("" + Resources.PIECE_SERGEANT_COUNT);
        lblType8Counter.setText("" + Resources.PIECE_MINER_COUNT);
        lblType9Counter.setText("" + Resources.PIECE_SCOUT_COUNT);
        lblType10Counter.setText("" + Resources.PIECE_SPY_COUNT);
        lblType11Counter.setText("" + Resources.PIECE_FLAG_COUNT);
    }

    public void addPiece(int type) {
        JLabel label = pieces.get("lblType" + type + "Counter");
        int newValue = Integer.parseInt(label.getText()) + 1;
        label.setText("" + newValue);
        pieceCount++;
        btnOk.setEnabled(false);
        enableLabel(type, true);
    }

    public void subtractPiece(int type) {
        pieceCount--;
        if (pieceCount == 0)
            btnOk.setEnabled(true);

        JLabel label = pieces.get("lblType" + type + "Counter");
        int newValue = Integer.parseInt(label.getText()) - 1;
        if (newValue <= 0) {
            enableLabel(type, false);
            newValue = 0;
        }

        label.setText("" + newValue);
    }

    private void enableLabel(int type, boolean enabled) {
        try {
            ((JLabel) PiecesOnDisplayPanel.class.getDeclaredField("lblType" + type + "Counter").get(this)).setEnabled(enabled);
            ((JLabel) PiecesOnDisplayPanel.class.getDeclaredField("lblType" + type + "Icon").get(this)).setEnabled(enabled);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
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
    private class LabelTypeIcon extends JLabel {
        private int type;

        private LabelTypeIcon(int player, int type) {
            super(Resources.getIcon(player, type, 35));
            this.type = type;
            super.addMouseListener(mouseAdapter);
        }
    }

    private class PiecesOnDisplayPanel_lblTypeIcon_mouseAdapter extends java.awt.event.MouseAdapter {
        private boolean itemSelected = false;
        private Border border = BorderFactory.createRaisedBevelBorder();

        public void mouseClicked(MouseEvent e) {
            if (gameStateObject.inState(GameStates.STATE_IN_GAME) || gameStateObject.inState(GameStates.STATE_IN_GAME_PLAYER_TURN)
                    || gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN))
                return;

            if (e.getButton() != 1) {
                currentLabel.setBorder(null);
                currentLabel = null;

                owner.setBoardCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                itemSelected = false;
            } else {
                if (!((LabelTypeIcon) e.getComponent()).isEnabled())
                    return;

                if (currentLabel != null)
                    currentLabel.setBorder(null);

                currentLabel = (LabelTypeIcon) e.getComponent();
                currentLabel.setBorder(border);

                owner.setBoardCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                itemSelected = true;
            }
        }

        public void mouseEntered(MouseEvent e) {
            if (itemSelected || gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN)
                    || gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN))
                return;

            owner.setBoardCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        public void mouseExited(MouseEvent e) {
            if (itemSelected || gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN)
                    || gameStateObject.inState(GameStates.STATE_IN_GAME_OPPONENT_TURN))
                return;

            owner.setBoardCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }

    void btnOk_actionPerformed(ActionEvent e) {
        btnOk.setVisible(false);
        btnSave.setVisible(false);
        btnLoad.setVisible(false);

        owner.sendEvent(ControlEventObject.EVENT_SETUP_SENT);

        if (gameStateObject.inState(GameStates.STATE_GAME_SETUP))
            gameStateObject.setState(GameStates.STATE_SETUP_WAIT_OPPONENT_SETUP);
        else if (gameStateObject.inState(GameStates.STATE_GAME_SETUP_RECEIVED_SETUP))
            gameStateObject.setState(GameStates.STATE_IN_GAME);
        else
            throw new IllegalStateException("The state " + gameStateObject.getState() + " is not allowed here");

    }
}

class PiecesOnDisplayPanel_btnOk_actionAdapter implements java.awt.event.ActionListener {
    private PiecesOnDisplayPanel adaptee;

    PiecesOnDisplayPanel_btnOk_actionAdapter(PiecesOnDisplayPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnOk_actionPerformed(e);
    }
}

class PiecesOnDisplayPanel_btnSave_actionAdapter implements java.awt.event.ActionListener {
    private MainFrame adaptee;

    PiecesOnDisplayPanel_btnSave_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSave_actionPerformed(e);
    }
}

class PiecesOnDisplayPanel_btnLoad_actionAdapter implements java.awt.event.ActionListener {
    private MainFrame adaptee;

    PiecesOnDisplayPanel_btnLoad_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnLoad_actionPerformed(e);
    }
}
