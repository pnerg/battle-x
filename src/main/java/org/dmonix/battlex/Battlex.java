package org.dmonix.battlex;

import java.util.logging.*;

import java.awt.*;
import javax.swing.*;

import org.dmonix.battlex.gui.*;

/**
 * <p>
 * Title: battlex
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: DMoniX
 * </p>
 * 
 * @author Tobias Jönsson
 * @version 1.0
 */

public class Battlex {
    public static final Color BACKGOUND_COLOR = new Color(141, 167, 118);

    boolean packFrame = false;

    // Construct the application
    public Battlex() {
        // StrategoFrame frame = new StrategoFrame();
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
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        frame.repaint();
        frame.setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    MainFrame.class.getResourceAsStream("/log.properties"));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Battlex();
    }
}