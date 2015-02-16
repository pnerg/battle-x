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
public class OpponentConfigurationObject implements Comparable {
    private String description;
    private String name;
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

    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
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