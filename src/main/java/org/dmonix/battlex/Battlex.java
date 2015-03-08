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
package org.dmonix.battlex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dmonix.battlex.gui.MainFrame;
import org.dmonix.battlex.resources.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Nerg
 */
public class Battlex {
    public static final Color BACKGOUND_COLOR = new Color(141, 167, 118);

    private static final Logger logger = LoggerFactory.getLogger(Battlex.class);

    private Battlex() {
    }

    private void readAndSetProperties() throws IOException {
        File propertyFile = new File(System.getProperty("battlex.config", "../battlex.properties"));
        PropertiesHelper.loadAndSetProperties(propertyFile);
    }

    private MainFrame createMainFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // UIManager.setLookAndFeel(MetalLookAndFeel.class.getName());

        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their
        // layout
        MainFrame mainFrame = new MainFrame();
        boolean packFrame = false; // TODO why this boolean?
        if (packFrame) {
            mainFrame.pack();
        } else {
            mainFrame.validate();
        }
        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = mainFrame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        mainFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        mainFrame.repaint();
        mainFrame.setVisible(true);
        return mainFrame;
    }

    private void startInDevMode(MainFrame mainFrame) {
        if (System.getProperty("battlex.devmode.startserver") != null) {
            mainFrame.menuItemNewGame_actionPerformed(null);
        }
        if (System.getProperty("battlex.devmode.connectserver") != null) {
            mainFrame.connectToOpponent("localhost", 6969, false, "", -1);
        }
    }

    // Main method
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException,
            IOException {
        Battlex battlex = new Battlex();
        battlex.readAndSetProperties();
        MainFrame mainFrame = battlex.createMainFrame();
        battlex.startInDevMode(mainFrame);
    }
}