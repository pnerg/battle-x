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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Peter Nerg
 * @version 1.0
 */

public class ChatPanel extends JPanel {
    private GridBagLayout gridBagLayoutPanel = new GridBagLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JEditorPane editorPane = new JEditorPane();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JTextArea jTextArea1 = new JTextArea();
    private JButton btnSubmit = new JButton();
    private JButton btnClear = new JButton();

    public ChatPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayoutPanel);
        jPanel1.setLayout(gridBagLayout1);
        editorPane.setFont(new java.awt.Font("Monospaced", 0, 12));
        editorPane.setMaximumSize(new Dimension(32767, 32767));
        editorPane.setMinimumSize(new Dimension(21, 21));
        editorPane.setPreferredSize(new Dimension(21, 21));
        editorPane.setEditable(false);
        editorPane.setText("");
        jPanel2.setLayout(gridBagLayout2);
        btnSubmit.setText("Submit");
        btnClear.setText("Clear");
        jTextArea1.setText("");
        jScrollPane1.setMaximumSize(new Dimension(32767, 32767));
        jScrollPane1.setPreferredSize(new Dimension(25, 25));
        this.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(jScrollPane1,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0));
        jScrollPane1.getViewport().add(editorPane, null);
        this.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(jTextArea1, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0));
        jPanel2.add(btnSubmit,
                new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(6, 0, 6, 6), 0, 0));
        jPanel2.add(btnClear, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(6, 0, 0, 6), 0, 0));
    }

}