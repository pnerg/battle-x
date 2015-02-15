package org.dmonix.battlex.gui;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import org.dmonix.battlex.resources.Configuration;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004-2005</p>
 * <p>Company: dmonix.org</p>
 * @author Peter Nerg
 * @version 1.0
 */

public class PreferencesOptionPane extends JDialog
{
  private Configuration configuration = null;

  private MainFrame owner;
  private GridBagLayout gridBagLayoutDialog = new GridBagLayout();
  private JPanel panel1 = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private JLabel lblPort = new JLabel();
  private JTextField txtFieldServerport = new JTextField();
  private JButton btnOk = new JButton();
  private JButton btnCancel = new JButton();

  public PreferencesOptionPane(MainFrame owner, Configuration configuration) throws HeadlessException
  {
    super(owner);
    this.owner = owner;
    this.configuration = configuration;

    try
    {
      jbInit();
      init();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    this.setTitle("Preferences");
    this.setModal(true);
    this.setResizable(false);
    this.getContentPane().setLayout(gridBagLayoutDialog);
    panel1.setLayout(gridBagLayout1);
    panel1.setAlignmentY((float) 0.5);
    panel1.setDebugGraphicsOptions(0);
    lblPort.setText("Server port");
    txtFieldServerport.setPreferredSize(new Dimension(30, 21));
    txtFieldServerport.setText("6969");
    btnOk.setText("Ok");
    btnOk.addActionListener(new PreferencesOptionPane_btnOk_actionAdapter(this));
    btnCancel.setText("Cancel");
    btnCancel.addActionListener(new PreferencesOptionPane_btnCancel_actionAdapter(this));
    this.getContentPane().add(panel1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0));
    panel1.add(lblPort,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    panel1.add(txtFieldServerport,        new GridBagConstraints(1, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(btnOk,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    panel1.add(btnCancel,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }

  private void init()
  {
    this.txtFieldServerport.setText(this.configuration.getPreference(Configuration.PREF_SERVERPORT));
    this.setSize(300, 150);
    this.setResizable(true);
    this.setLocationRelativeTo(owner);
  }

  void btnOk_actionPerformed(ActionEvent e)
  {
    try
    {
      this.configuration.setPreference(Configuration.PREF_SERVERPORT,"" +Integer.parseInt(this.txtFieldServerport.getText()));
      this.configuration.save();
      super.setVisible(false);
    }
    catch (NumberFormatException ex)
    {
      JOptionPane.showMessageDialog(owner, "Not a valid port", "Invalid input", JOptionPane.ERROR_MESSAGE);
    }

  }

  void btnCancel_actionPerformed(ActionEvent e)
  {
    super.setVisible(false);
  }

}

class PreferencesOptionPane_btnOk_actionAdapter implements java.awt.event.ActionListener
{
  private PreferencesOptionPane adaptee;

  PreferencesOptionPane_btnOk_actionAdapter(PreferencesOptionPane adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.btnOk_actionPerformed(e);
  }
}

class PreferencesOptionPane_btnCancel_actionAdapter implements java.awt.event.ActionListener
{
  private PreferencesOptionPane adaptee;

  PreferencesOptionPane_btnCancel_actionAdapter(PreferencesOptionPane adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.btnCancel_actionPerformed(e);
  }
}