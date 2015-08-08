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
package org.dmonix.battlex.resources;

/**
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: dmonix.org
 * </p>
 * 
 * @author Peter Nerg
 * @version 1.0
 */
public class OpponentConfigurationObject implements Comparable<OpponentConfigurationObject> {
    private final String name;
    private String description;
    private int port, proxyport;
    private String url;
    private String proxy;
    private boolean useProxy = false;

    public OpponentConfigurationObject(String name, String description, String url, int port, boolean useProxy, String proxy, int proxyport) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.port = port;
        this.proxy = proxy;
        this.proxyport = proxyport;
        this.useProxy = useProxy;
    }

    public OpponentConfigurationObject(String name, String description, String url, int port) {
        this(name, description, url, port, false, null, -1);
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }

    public String getProxy() {
        return this.proxy;
    }

    public int getProxyPort() {
        return this.proxyport;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean useProxy() {
        return this.useProxy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProxy(boolean useProxy, String proxy, int proxyport) {
        this.useProxy = useProxy;
        this.proxy = proxy;
        this.proxyport = proxyport;
    }

    public int compareTo(OpponentConfigurationObject o) {
        return this.name.compareTo(o.name);
    }

    public String toString() {
        return this.name;
    }

    public String toString2() {
        StringBuffer sb = new StringBuffer();
        sb.append("OpponentConfigurationObject\n");
        sb.append("name=" + name + "\n");
        sb.append("description=" + description + "\n");
        sb.append("url=" + url + "\n");
        sb.append("port=" + port + "\n");
        sb.append("useProxy=" + useProxy + "\n");
        sb.append("proxyhost=" + proxy + "\n");
        sb.append("proxyport=" + proxyport + "\n");
        return sb.toString();
    }
}