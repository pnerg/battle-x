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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.dmonix.battlex.Battlex;
import org.dmonix.battlex.datamodel.Player;
import org.dmonix.battlex.resources.Resources;

/**
 * @author Peter Nerg
 */
public class ResolveStrikeDialog extends JDialog {
    private static final long serialVersionUID = -6216921027576667223L;
    private JLabel lblText = new JLabel();
    private JLabel lblAttacker = new JLabel(new ImageIcon(ResolveStrikeDialog.class.getResource("/images/player1/battlex-piece-100.gif")));
    private JLabel lblVs = new JLabel();
    private JButton btnOk = new JButton();
    private JLabel lblDefender = new JLabel(new ImageIcon(ResolveStrikeDialog.class.getResource("/images/player2/battlex-piece-100.gif")));
    private static ResolveStrikeDialog dialog;

    private ResolveStrikeDialog() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.setSize(200, 150);
        setVisible(false);
    }

    public static void showStrikeResult(JFrame owner, String text, Player attacker, String attackerType, Player defender, String defenderType) {
        if (dialog == null)
            dialog = new ResolveStrikeDialog();

        dialog.setModal(true);
        dialog.setLocationRelativeTo(owner);
        dialog.lblAttacker.setIcon(Resources.getIcon(attacker, attackerType, 40));
        dialog.lblDefender.setIcon(Resources.getIcon(defender, defenderType, 40));
        dialog.lblText.setText(text);
        dialog.setTitle("Resolve strike");
        dialog.setVisible(true);
    }

    private void jbInit() throws Exception {
        lblText.setText("Text.....");
        this.getContentPane().setLayout(new GridBagLayout());
        lblVs.setText("vs");
        btnOk.setText("Ok");
        btnOk.addActionListener(new ResolveStrikeDialog_btnOk_actionAdapter(this));
        lblAttacker.setText("");
        lblDefender.setText("");
        this.getContentPane().setBackground(Battlex.BACKGOUND_COLOR);
        this.getContentPane().add(lblText,
                new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(lblAttacker,
                new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(lblVs,
                new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(lblDefender,
                new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(btnOk,
                new GridBagConstraints(2, 2, 1, 1, 0.0, 1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }

    void btnOk_actionPerformed(ActionEvent e) {
        dialog.setVisible(false);
    }

}

class ResolveStrikeDialog_btnOk_actionAdapter implements java.awt.event.ActionListener {
    private ResolveStrikeDialog adaptee;

    ResolveStrikeDialog_btnOk_actionAdapter(ResolveStrikeDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnOk_actionPerformed(e);
    }
}