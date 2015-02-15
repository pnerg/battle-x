package org.dmonix.battlex.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.dmonix.battlex.resources.*;
import org.dmonix.battlex.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: dmonix.org</p>
 * @author Peter Nerg
 * @version 1.0
 */

public class ResolveStrikeDialog extends JDialog
{
  private JLabel lblText = new JLabel();
  private JLabel lblAttacker = new JLabel(new ImageIcon(ResolveStrikeDialog.class.getResource("/org/dmonix/battlex/images/player1/battlex-piece-100.gif")));
  private JLabel lblVs = new JLabel();
  private JButton btnOk = new JButton();
  private JLabel lblDefender = new JLabel(new ImageIcon(ResolveStrikeDialog.class.getResource("/org/dmonix/battlex/images/player2/battlex-piece-100.gif")));
  private static ResolveStrikeDialog dialog;

  private ResolveStrikeDialog()
  {
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    super.setSize(200, 150);
    hide();
  }

  public static void showStrikeResult(JFrame owner, String text, int attacker, int attackerType, int defender, int defenderType)
  {
    if(dialog == null)
      dialog = new ResolveStrikeDialog();

    dialog.setModal(true);
    dialog.setLocationRelativeTo(owner);
    dialog.lblAttacker.setIcon(Resources.getIcon(attacker, attackerType, 40));
    dialog.lblDefender.setIcon(Resources.getIcon(defender, defenderType, 40));
    dialog.lblText.setText(text);
    dialog.setTitle("Resolve strike");
    dialog.show();
  }

  private void jbInit() throws Exception
  {
    lblText.setText("Text.....");
    this.getContentPane().setLayout(new GridBagLayout());
    lblVs.setText("vs");
    btnOk.setText("Ok");
    btnOk.addActionListener(new ResolveStrikeDialog_btnOk_actionAdapter(this));
    lblAttacker.setText("");
    lblDefender.setText("");
    this.getContentPane().setBackground(Battlex.BACKGOUND_COLOR);
    this.getContentPane().add(lblText,   new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(lblAttacker,    new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(lblVs,   new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(lblDefender,  new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0
           ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.getContentPane().add(btnOk,      new GridBagConstraints(2, 2, 1, 1, 0.0, 1.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
  }

  void btnOk_actionPerformed(ActionEvent e)
  {
    dialog.hide();
  }

}

class ResolveStrikeDialog_btnOk_actionAdapter implements java.awt.event.ActionListener
{
  private ResolveStrikeDialog adaptee;

  ResolveStrikeDialog_btnOk_actionAdapter(ResolveStrikeDialog adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.btnOk_actionPerformed(e);
  }
}