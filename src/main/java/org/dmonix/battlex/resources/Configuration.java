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

package org.dmonix.battlex.resources;

import java.io.File;
import java.util.logging.Logger;

import org.dmonix.io.IOUtil;
import org.dmonix.xml.XMLDocument;
import org.dmonix.xml.XMLElement;

/**
 * @author Peter Nerg
 * @version 1.0
 */
public class Configuration {
    public static final File CONF_PATH = new File(System.getProperty("user.home") + File.separator + "dmonix" + File.separator + "battlex");

    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(Configuration.class.getName());

    private static final File CONF_FILE = new File(CONF_PATH, "battlex.cfg");

    private XMLDocument configuration = null;

    private XMLElement elementOpponents = null;
    private XMLElement elementPreferences = null;

    public static final String PREF_SERVERPORT = "serverport";

    private static final String OPPONENTS = "opponents";
    private static final String OPPONENT = "opponent";
    private static final String NAME = "name";
    private static final String DESC = "description";
    private static final String PORT = "port";
    private static final String PROXY = "proxy";
    private static final String PROXYHOST = "proxyhost";
    private static final String PROXYPORT = "proxyport";
    private static final String URL = "url";
    private static final String PREFERENCES = "preferences";

    public Configuration() {
        try {
            this.configuration = new XMLDocument(CONF_FILE);
        } catch (Exception ex) {
            this.createNewConfig();
        }

        this.elementOpponents = this.configuration.getElement(OPPONENTS);
        this.elementPreferences = this.configuration.getElement(PREFERENCES);
    }

    public void setPreference(String preference, String value) {
        // Element pref = this.configuration.getElement(elementPreferences.getElementsByTagName(preference));
        // pref.removeChild(pref.getFirstChild());
        // this.configuration.setElementValue(pref, value);
    }

    public String getPreference(String preference) {
        if (preference.equals(PREF_SERVERPORT))
            return "6969";
        // return this.configuration.getElementValue(elementPreferences.getElementsByTagName(preference));
        return "";
    }

    // public void addOpponent(OpponentConfigurationObject oc) {
    // // /*
    // // * <opponent> <name>localhost</name> <description>Local host, act as your own opponent</description> <url>localhost</url> <port>6969</port> <proxy
    // // * useProxy="false"> <proxyHost/> <proxyPort/> </proxy> </opponent>
    // // */
    // //
    // // Element eopponent = this.configuration.appendChildElement(this.elementOpponents, OPPONENT);
    // //
    // // this.configuration.appendChildElement(eopponent, NAME, oc.getName());
    // // this.configuration.appendChildElement(eopponent, DESC, oc.getDescription());
    // // this.configuration.appendChildElement(eopponent, URL, oc.getUrl());
    // // this.configuration.appendChildElement(eopponent, PORT, "" + oc.getPort());
    // //
    // // Element eproxy = this.configuration.appendChildElement(eopponent, PROXY);
    // // eproxy.setAttribute("useProxy", "" + oc.useProxy());
    // //
    // // this.configuration.appendChildElement(eproxy, PROXYHOST, oc.getProxy());
    // // this.configuration.appendChildElement(eproxy, PROXYPORT, "" + oc.getProxyPort());
    // //
    // // save();
    // }
    //
    // public void removeOpponent(String name) {
    // // NodeList list = this.configuration.getElementsByTagName(OPPONENT);
    // // Element e;
    // // for (int i = 0; i < list.getLength(); i++) {
    // // e = (Element) list.item(i);
    // // if (this.configuration.getElementValue(e.getElementsByTagName(NAME)).equals(name)) {
    // // this.elementOpponents.removeChild(e);
    // // break;
    // // }
    // // }
    // // save();
    // }
    //
    // public List<OpponentConfigurationObject> getOpponents() {
    // // Vector data = new Vector();
    // // NodeList list = this.configuration.getElementsByTagName(OPPONENT);
    // //
    // // Element e, eproxy;
    // // OpponentConfigurationObject oc;
    // // for (int i = 0; i < list.getLength(); i++) {
    // // e = (Element) list.item(i);
    // // eproxy = this.configuration.getElement(e.getElementsByTagName(PROXY));
    // // if (eproxy.getAttribute("useProxy").equalsIgnoreCase("true"))
    // // oc = new OpponentConfigurationObject(this.configuration.getElementValue(e.getElementsByTagName(NAME), ""), this.configuration.getElementValue(
    // // e.getElementsByTagName(DESC), ""), this.configuration.getElementValue(e.getElementsByTagName(URL), ""),
    // // Integer.parseInt(this.configuration.getElementValue(e.getElementsByTagName(PORT), "6969")), true, this.configuration.getElementValue(
    // // eproxy.getElementsByTagName(PROXYHOST), ""), Integer.parseInt(this.configuration.getElementValue(
    // // eproxy.getElementsByTagName(PROXYPORT), "-1")));
    // // else
    // // oc = new OpponentConfigurationObject(this.configuration.getElementValue(e.getElementsByTagName(NAME), ""), this.configuration.getElementValue(
    // // e.getElementsByTagName(DESC), ""), this.configuration.getElementValue(e.getElementsByTagName(URL), ""),
    // // Integer.parseInt(this.configuration.getElementValue(e.getElementsByTagName(PORT), "6969")), false, this.configuration.getElementValue(
    // // eproxy.getElementsByTagName(PROXYHOST), ""), Integer.parseInt(this.configuration.getElementValue(
    // // eproxy.getElementsByTagName(PROXYPORT), "-1")));
    // //
    // // if (log.isLoggable(Level.FINE))
    // // log.log(Level.FINE, oc.toString2());
    // //
    // // data.addElement(oc);
    // // }
    // // Collections.sort(data);
    // //
    // // return data.elements();
    // return Collections.emptyList();
    // }

    // public void save() {
    // try {
    // this.configuration.toFile(this.CONF_FILE);
    // } catch (Exception ex) {
    // log.log(Level.WARNING, "Could not save configuration", ex);
    // }
    // }

    private void createNewConfig() {
        if (!this.CONF_FILE.exists()) {
            try {
                this.CONF_FILE.getParentFile().mkdirs();
                IOUtil.copyFile(Configuration.class.getResource("battlex.xml"), this.CONF_FILE);
                this.configuration = new XMLDocument(this.CONF_FILE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}