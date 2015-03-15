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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.dmonix.battlex.Battlex;
import org.dmonix.battlex.datamodel.PieceData;
import org.dmonix.battlex.event.ControlEvents;
import org.dmonix.battlex.event.GameStateController;
import org.dmonix.battlex.event.GameStates;
import org.dmonix.battlex.resources.Resources;
import org.dmonix.util.ImageLoaderUtil;

/**
 * The panel for displaying one players available/captured pieces.
 * 
 * @author Peter Nerg
 */
public class PiecesOnDisplayPanel extends JPanel {
    private static final long serialVersionUID = -1615792225365427489L;
    private MainFrame owner;
    private LabelTypeIcon currentLabel;
    private int pieceCount = PieceData.getTotalPieceCount();
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

    private Map<String, LabelTypeIcon> pieceImageLabels = new Hashtable<>();
    private Map<String, JLabel> pieceCounterLabels = new Hashtable<>();

    private JButton btnOk = new JButton(ImageLoaderUtil.getImageIcon(ImageLoaderUtil.PATH_TOOLBARBUTTONGRAPHICS_GENERAL, "SendMail24.gif"));
    private final PiecesOnDisplayPanel_lblTypeIcon_mouseAdapter mouseAdapter = new PiecesOnDisplayPanel_lblTypeIcon_mouseAdapter();
    private JButton btnSave = new JButton(ImageLoaderUtil.getImageIcon(ImageLoaderUtil.PATH_TOOLBARBUTTONGRAPHICS_GENERAL, "Save24.gif"));
    private JButton btnLoad = new JButton(ImageLoaderUtil.getImageIcon(ImageLoaderUtil.PATH_TOOLBARBUTTONGRAPHICS_GENERAL, "Open24.gif"));

    public PiecesOnDisplayPanel(int player) {
        try {
            btnOk.setEnabled(false);
            lblType0Icon = new LabelTypeIcon(player, PieceData.PIECE_BOMB_TYPE);
            lblType1Icon = new LabelTypeIcon(player, PieceData.PIECE_MARSHAL_TYPE);
            lblType2Icon = new LabelTypeIcon(player, PieceData.PIECE_GENERAL_TYPE);
            lblType3Icon = new LabelTypeIcon(player, PieceData.PIECE_COLONEL_TYPE);
            lblType4Icon = new LabelTypeIcon(player, PieceData.PIECE_MAJOR_TYPE);
            lblType5Icon = new LabelTypeIcon(player, PieceData.PIECE_CAPTAIN_TYPE);
            lblType6Icon = new LabelTypeIcon(player, PieceData.PIECE_LIEUTENANT_TYPE);
            lblType7Icon = new LabelTypeIcon(player, PieceData.PIECE_SERGEANT_TYPE);
            lblType8Icon = new LabelTypeIcon(player, PieceData.PIECE_MINER_TYPE);
            lblType9Icon = new LabelTypeIcon(player, PieceData.PIECE_SCOUT_TYPE);
            lblType10Icon = new LabelTypeIcon(player, PieceData.PIECE_SPY_TYPE);
            lblType11Icon = new LabelTypeIcon(player, PieceData.PIECE_FLAG_TYPE);

            jbInit();

            titledBorder.setTitle("Player " + player);
            pieceImageLabels.put(PieceData.PIECE_BOMB_TYPE, lblType0Icon);
            pieceImageLabels.put(PieceData.PIECE_MARSHAL_TYPE, lblType1Icon);
            pieceImageLabels.put(PieceData.PIECE_GENERAL_TYPE, lblType2Icon);
            pieceImageLabels.put(PieceData.PIECE_COLONEL_TYPE, lblType3Icon);
            pieceImageLabels.put(PieceData.PIECE_MAJOR_TYPE, lblType4Icon);
            pieceImageLabels.put(PieceData.PIECE_CAPTAIN_TYPE, lblType5Icon);
            pieceImageLabels.put(PieceData.PIECE_LIEUTENANT_TYPE, lblType6Icon);
            pieceImageLabels.put(PieceData.PIECE_SERGEANT_TYPE, lblType7Icon);
            pieceImageLabels.put(PieceData.PIECE_MINER_TYPE, lblType8Icon);
            pieceImageLabels.put(PieceData.PIECE_SCOUT_TYPE, lblType9Icon);
            pieceImageLabels.put(PieceData.PIECE_SPY_TYPE, lblType10Icon);
            pieceImageLabels.put(PieceData.PIECE_FLAG_TYPE, lblType11Icon);

            pieceCounterLabels.put(PieceData.PIECE_BOMB_TYPE, lblType0Counter);
            pieceCounterLabels.put(PieceData.PIECE_MARSHAL_TYPE, lblType1Counter);
            pieceCounterLabels.put(PieceData.PIECE_GENERAL_TYPE, lblType2Counter);
            pieceCounterLabels.put(PieceData.PIECE_COLONEL_TYPE, lblType3Counter);
            pieceCounterLabels.put(PieceData.PIECE_MAJOR_TYPE, lblType4Counter);
            pieceCounterLabels.put(PieceData.PIECE_CAPTAIN_TYPE, lblType5Counter);
            pieceCounterLabels.put(PieceData.PIECE_LIEUTENANT_TYPE, lblType6Counter);
            pieceCounterLabels.put(PieceData.PIECE_SERGEANT_TYPE, lblType7Counter);
            pieceCounterLabels.put(PieceData.PIECE_MINER_TYPE, lblType8Counter);
            pieceCounterLabels.put(PieceData.PIECE_SCOUT_TYPE, lblType9Counter);
            pieceCounterLabels.put(PieceData.PIECE_SPY_TYPE, lblType10Counter);
            pieceCounterLabels.put(PieceData.PIECE_FLAG_TYPE, lblType11Counter);
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
        lblType0Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_BOMB_TYPE));
        lblType1Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_MARSHAL_TYPE));
        lblType2Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_GENERAL_TYPE));
        lblType3Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_COLONEL_TYPE));
        lblType4Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_MAJOR_TYPE));
        lblType5Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_CAPTAIN_TYPE));
        lblType6Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_LIEUTENANT_TYPE));
        lblType7Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_SERGEANT_TYPE));
        lblType8Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_MINER_TYPE));
        lblType9Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_SCOUT_TYPE));
        lblType10Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_SPY_TYPE));
        lblType11Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_FLAG_TYPE));
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

    public String getSelectedPieceType() {
        if (currentLabel == null)
            return PieceData.PIECE_NO_PIECE;

        String pieceType = currentLabel.type;
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

        lblType0Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_BOMB_TYPE));
        lblType1Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_MARSHAL_TYPE));
        lblType2Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_GENERAL_TYPE));
        lblType3Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_COLONEL_TYPE));
        lblType4Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_MAJOR_TYPE));
        lblType5Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_CAPTAIN_TYPE));
        lblType6Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_LIEUTENANT_TYPE));
        lblType7Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_SERGEANT_TYPE));
        lblType8Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_MINER_TYPE));
        lblType9Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_SCOUT_TYPE));
        lblType10Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_SPY_TYPE));
        lblType11Counter.setText("" + PieceData.getPieceCount(PieceData.PIECE_FLAG_TYPE));
    }

    public void addPiece(String type) {
        JLabel label = pieceCounterLabels.get(type);
        int newValue = Integer.parseInt(label.getText()) + 1;
        label.setText("" + newValue);
        pieceCount++;
        btnOk.setEnabled(false);
        enableLabel(type, true);
    }

    public void subtractPiece(String type) {
        pieceCount--;
        if (pieceCount == 0)
            btnOk.setEnabled(true);

        JLabel label = pieceCounterLabels.get(type);
        int newValue = Integer.parseInt(label.getText()) - 1;
        if (newValue <= 0) {
            enableLabel(type, false);
            newValue = 0;
        }

        label.setText("" + newValue);
    }

    private void enableLabel(String type, boolean enabled) {
        pieceImageLabels.get(type).setEnabled(enabled);
        pieceCounterLabels.get(type).setEnabled(enabled);
        // try {
        // ((JLabel) PiecesOnDisplayPanel.class.getDeclaredField("lblType" + type + "Icon").get(this)).setEnabled(enabled);
        // ((JLabel) PiecesOnDisplayPanel.class.getDeclaredField("lblType" + type + "Counter").get(this)).setEnabled(enabled);
        // } catch (Exception ex) {
        // ex.printStackTrace();
        // }
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
        private static final long serialVersionUID = 1292383031384135798L;
        private String type;

        private LabelTypeIcon(int player, String type) {
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

        owner.sendEvent(ControlEvents.EVENT_SETUP_SENT);

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
