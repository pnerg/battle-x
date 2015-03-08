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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.dmonix.battlex.resources.Configuration;
import org.dmonix.battlex.resources.OpponentConfigurationObject;
import org.dmonix.battlex.resources.OpponentsConfigurer;
import org.dmonix.gui.models.SortableComboBoxModel;

/**
 * @author Peter Nerg
 */

public class OpponentOptionPane extends JDialog {
    private static final long serialVersionUID = -527788114327118747L;

    private final OpponentConfigurationObject NEW_OPPONENT = new OpponentConfigurationObject("_new opponent", "", "", 6969);

    private MainFrame owner;
    private Configuration configuration = null;

    private GridBagLayout gridBagLayoutPane = new GridBagLayout();
    private JLabel lblName = new JLabel();
    private JTextField txtFieldName = new JTextField();
    private JLabel lblURL = new JLabel();
    private JTextField txtFieldHost = new JTextField();
    private JLabel lblPort = new JLabel();
    private JTextField txtFieldPort = new JTextField();
    private JLabel lblDescription = new JLabel();
    private JScrollPane scrollPane = new JScrollPane();
    private JTextArea txtAreaDescription = new JTextArea();
    private JButton btnOk = new JButton();
    private JButton btnClose = new JButton();
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JLabel lblOpponent = new JLabel();
    private JComboBox<OpponentConfigurationObject> comboBoxOpponents = new JComboBox<>();
    private TitledBorder titledBorder1;
    private JPanel panel3 = new JPanel();
    private JButton btnDelete = new JButton();
    private JCheckBox chkBoxProxy = new JCheckBox();
    private JTextField txtFieldProxyHost = new JTextField();
    private JLabel lblProxyPort = new JLabel("Port");
    private JTextField txtFieldProxyPort = new JTextField();
    private SortableComboBoxModel comboModel = new SortableComboBoxModel();

    // TODO Get rid of the reference to Mainframe, ugly entanglement
    public OpponentOptionPane(MainFrame owner, Configuration configuration) throws HeadlessException {
        super(owner);
        this.owner = owner;
        this.configuration = configuration;
        super.setTitle("Opponent configuration");
        try {
            jbInit();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setModal(true);
        this.setResizable(false);
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "Opponent");
        lblName.setText("Name");
        comboBoxOpponents.setModel(comboModel);
        this.getContentPane().setLayout(gridBagLayoutPane);
        txtFieldName.setMinimumSize(new Dimension(100, 21));
        txtFieldName.setPreferredSize(new Dimension(215, 21));
        txtFieldName.setRequestFocusEnabled(true);
        txtFieldName.setText("");
        lblURL.setText("Host");
        lblPort.setText("Port");
        txtFieldPort.setMinimumSize(new Dimension(50, 21));
        txtFieldPort.setPreferredSize(new Dimension(50, 21));
        txtFieldPort.setText("6969");
        txtFieldHost.setText("");
        lblDescription.setText("Description");
        txtAreaDescription.setText("");
        scrollPane.setPreferredSize(new Dimension(4, 100));
        btnOk.setText("Ok");
        btnOk.addActionListener(new OpponentOptionPane_btnOk_actionAdapter(this));
        btnClose.setText("Close");
        btnClose.addActionListener(new OpponentOptionPane_btnClose_actionAdapter(this));
        panel1.setLayout(new GridBagLayout());
        panel2.setLayout(new GridBagLayout());
        panel3.setLayout(new GridBagLayout());
        lblOpponent.setText("Opponent");

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new OpponentOptionPane_btnDelete_actionAdapter(this));

        comboBoxOpponents.addActionListener(new OpponentOptionPane_comboBoxOpponents_actionAdapter(this));
        chkBoxProxy.setText("Proxy");
        chkBoxProxy.addActionListener(new OpponentOptionPane_chkBoxProxy_actionAdapter(this));
        txtFieldProxyHost.setEditable(false);
        txtFieldProxyPort.setEnabled(true);
        txtFieldProxyPort.setMinimumSize(new Dimension(50, 21));
        txtFieldProxyPort.setPreferredSize(new Dimension(50, 21));
        txtFieldProxyPort.setEditable(false);
        scrollPane.getViewport().add(txtAreaDescription, null);

        panel1.add(lblOpponent, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0,
                0));
        panel1.add(comboBoxOpponents, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,
                5, 5, 5), 0, 0));

        panel2.setBorder(titledBorder1);
        panel2.add(lblName, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        panel2.add(txtFieldName, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5,
                5), 0, 0));
        panel2.add(lblURL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        panel2.add(txtFieldHost, new GridBagConstraints(1, 1, 1, 2, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5,
                5), 0, 0));
        panel2.add(lblPort, new GridBagConstraints(2, 1, 1, 3, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        panel2.add(txtFieldPort, new GridBagConstraints(3, 1, 1, 3, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5, 10),
                0, 0));
        panel2.add(chkBoxProxy, new GridBagConstraints(0, 2, 1, 2, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0,
                0));
        panel2.add(txtFieldProxyHost, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,
                5, 5, 5), 0, 0));
        panel2.add(lblProxyPort, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0,
                0));
        panel2.add(txtFieldProxyPort, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 0, 5,
                10), 0, 0));
        panel2.add(lblDescription, new GridBagConstraints(0, 3, 1, 2, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5),
                0, 0));
        panel2.add(scrollPane,
                new GridBagConstraints(1, 3, 3, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));

        panel3.add(btnOk, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        panel3.add(btnClose, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
        panel3.add(btnDelete, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        this.getContentPane().add(panel1,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 0), 0, 0));
        this.getContentPane().add(panel2,
                new GridBagConstraints(0, 2, 3, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5, 5, 0, 5), 0, 0));
        this.getContentPane().add(panel3,
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    private void init() {
        for (OpponentConfigurationObject oco : OpponentsConfigurer.getOpponents()) {
            this.comboModel.addElement(oco);
        }

        this.setSize(400, 300);
        this.setResizable(true);
        this.comboModel.addElement(NEW_OPPONENT);
        this.comboModel.sort();
        this.comboBoxOpponents.setSelectedItem(NEW_OPPONENT);
        this.setLocationRelativeTo(owner);
    }

    void btnOk_actionPerformed(ActionEvent e) {
        OpponentConfigurationObject oc;
        int port, proxyport;

        try {
            port = Integer.parseInt(txtFieldPort.getText());
            proxyport = Integer.parseInt(txtFieldProxyPort.getText());
        } catch (NumberFormatException numfex) {
            JOptionPane.showMessageDialog(owner, "Illegal port value", "Input error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.comboBoxOpponents.getSelectedItem() == NEW_OPPONENT) {
            oc = new OpponentConfigurationObject(txtFieldName.getText(), txtAreaDescription.getText(), txtFieldHost.getText(), port, chkBoxProxy.isSelected(),
                    txtFieldProxyHost.getText(), proxyport);

            this.comboModel.addElement(oc);
            this.comboBoxOpponents.setSelectedItem(oc);
            OpponentsConfigurer.addOpponent(oc);
            this.owner.setOpponentMenuItems();
        } else {
            oc = (OpponentConfigurationObject) this.comboBoxOpponents.getSelectedItem();
            oc.setUrl(txtFieldHost.getText());
            oc.setDescription(txtAreaDescription.getText());
            oc.setPort(port);
            oc.setProxy(chkBoxProxy.isSelected(), txtFieldProxyHost.getText(), proxyport);
        }
        this.comboModel.sort();
    }

    void btnClose_actionPerformed(ActionEvent e) {
        super.setVisible(false);
    }

    void btnDelete_actionPerformed(ActionEvent e) {
        Object o = this.comboBoxOpponents.getSelectedItem();
        if (o != null && !o.equals(NEW_OPPONENT))
            return;

        OpponentConfigurationObject oc = (OpponentConfigurationObject) o;
        this.comboModel.removeElement(o);
        OpponentsConfigurer.removeOpponent(oc.getName());
        this.comboBoxOpponents.setSelectedItem(NEW_OPPONENT);
        this.owner.setOpponentMenuItems();
    }

    class OpponentOptionPane_btnOk_actionAdapter implements java.awt.event.ActionListener {
        OpponentOptionPane adaptee;

        OpponentOptionPane_btnOk_actionAdapter(OpponentOptionPane adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnOk_actionPerformed(e);
        }
    }

    class OpponentOptionPane_btnClose_actionAdapter implements java.awt.event.ActionListener {
        OpponentOptionPane adaptee;

        OpponentOptionPane_btnClose_actionAdapter(OpponentOptionPane adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.btnClose_actionPerformed(e);
        }
    }

    void comboBoxOpponents_actionPerformed(ActionEvent e) {
        Object o = comboBoxOpponents.getSelectedItem();
        if (o == null)
            return;

        OpponentConfigurationObject oc = (OpponentConfigurationObject) o;
        if (oc == NEW_OPPONENT) {
            btnDelete.setEnabled(false);
            txtFieldName.setEditable(true);
            this.txtFieldName.setText("");
            this.txtFieldProxyHost.setText("");
        } else {
            btnDelete.setEnabled(true);
            txtFieldName.setEditable(false);
            this.txtFieldName.setText(oc.getName());
        }

        this.txtAreaDescription.setText(oc.getDescription());
        this.txtFieldPort.setText("" + oc.getPort());
        this.txtFieldHost.setText(oc.getUrl());
        this.chkBoxProxy.setSelected(oc.useProxy());
        this.txtFieldProxyHost.setText(oc.getProxy());
        this.txtFieldProxyHost.setEditable(oc.useProxy());
        this.txtFieldProxyPort.setText("" + oc.getProxyPort());
        this.txtFieldProxyPort.setEditable(oc.useProxy());
    }

    void chkBoxProxy_actionPerformed(ActionEvent e) {
        this.txtFieldProxyHost.setEditable(this.chkBoxProxy.isSelected());
        this.txtFieldProxyPort.setEditable(this.chkBoxProxy.isSelected());
    }

}

class OpponentOptionPane_btnDelete_actionAdapter implements java.awt.event.ActionListener {
    private OpponentOptionPane adaptee;

    OpponentOptionPane_btnDelete_actionAdapter(OpponentOptionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnDelete_actionPerformed(e);
    }
}

class OpponentOptionPane_comboBoxOpponents_actionAdapter implements java.awt.event.ActionListener {
    private OpponentOptionPane adaptee;

    OpponentOptionPane_comboBoxOpponents_actionAdapter(OpponentOptionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.comboBoxOpponents_actionPerformed(e);
    }
}

class OpponentOptionPane_chkBoxProxy_actionAdapter implements java.awt.event.ActionListener {
    private OpponentOptionPane adaptee;

    OpponentOptionPane_chkBoxProxy_actionAdapter(OpponentOptionPane adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.chkBoxProxy_actionPerformed(e);
    }
}