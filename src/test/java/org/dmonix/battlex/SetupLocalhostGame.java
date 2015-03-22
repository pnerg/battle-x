package org.dmonix.battlex;

import java.awt.Point;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dmonix.battlex.gui.MainFrame;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
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
public class SetupLocalhostGame {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        MainFrame player1 = new MainFrame();
        MainFrame player2 = new MainFrame();

        Point p = player2.getLocation();
        player2.setLocation(p.x + 500, p.y);

        player1.menuItemNewGame_actionPerformed(null);
        player2.connectToOpponent("localhost", 6969, false, "", -1);
    }
}