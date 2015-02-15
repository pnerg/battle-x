package org.dmonix.battlex.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

import org.dmonix.battlex.resources.OpponentConfigurationObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: dmonix.org</p>
 * @author Peter Nerg
 * @version 1.0
 */
public class OpponentMenuItem extends JMenuItem implements ActionListener, Comparable
{
  private MainFrame mainFrame = null;
  private OpponentConfigurationObject oc;

  public OpponentMenuItem(MainFrame mainFrame, OpponentConfigurationObject oc)
  {
    super(oc.getName());
    this.mainFrame = mainFrame;
    this.oc = oc;
    this.addActionListener(this);
  }

  public OpponentConfigurationObject getConfiguration()
  {
    return this.oc;
  }

  public void actionPerformed(ActionEvent e)
  {
    mainFrame.connectToOpponent(this.oc.getUrl(), this.oc.getPort(),
                                this.oc.useProxy(), this.oc.getProxy(),
                                this.oc.getProxyPort());
  }

  public String toString()
  {
    return this.oc.getName();
  }

  public int compareTo(Object o)
  {
    return this.toString().compareTo(o.toString());
  }

}