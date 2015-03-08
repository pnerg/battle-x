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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.dmonix.battlex.resources.OpponentConfigurationObject;

/**
 * @author Peter Nerg
 */
public class OpponentMenuItem extends JMenuItem implements ActionListener, Comparable<OpponentConfigurationObject> {
    private static final long serialVersionUID = -2929589867007454937L;
    private MainFrame mainFrame = null;
    private OpponentConfigurationObject oc;

    public OpponentMenuItem(MainFrame mainFrame, OpponentConfigurationObject oc) {
        super(oc.getName());
        this.mainFrame = mainFrame;
        this.oc = oc;
        this.addActionListener(this);
    }

    public OpponentConfigurationObject getConfiguration() {
        return this.oc;
    }

    public void actionPerformed(ActionEvent e) {
        mainFrame.connectToOpponent(this.oc.getUrl(), this.oc.getPort(), this.oc.useProxy(), this.oc.getProxy(), this.oc.getProxyPort());
    }

    public String toString() {
        return this.oc.getName();
    }

    public int compareTo(OpponentConfigurationObject o) {
        return this.toString().compareTo(o.toString());
    }

}