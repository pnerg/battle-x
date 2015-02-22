package org.dmonix.battlex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dmonix.battlex.gui.MainFrame;

/**
 * Copyright: Copyright (c)<br>
 * 2003 Company: DMoniX
 * 
 * @author Peter Nerg
 * @version 1.0
 */
public class Battlex {
    public static final Color BACKGOUND_COLOR = new Color(141, 167, 118);

    boolean packFrame = false;

    // Construct the application
    public Battlex() {
        MainFrame frame = new MainFrame();
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their
        // layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.repaint();
        frame.setVisible(true);
    }

    // Main method
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // UIManager.setLookAndFeel(MetalLookAndFeel.class.getName());
        new Battlex();
    }
}