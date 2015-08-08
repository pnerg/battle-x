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
package org.dmonix.battlex;

import java.awt.Point;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dmonix.battlex.gui.MainFrame;

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