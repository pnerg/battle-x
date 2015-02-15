package org.dmonix.battlex.event;

import java.io.Serializable;

/**
 * An event object used with the ControlEventListener<br>
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
public final class ControlEventObject implements Serializable {
 
    private static final long serialVersionUID = 7526476784622776147L;

    /** Request a connection. */
    public static final int CMD_CONNECT = 1;

    /** Acknowledge a connection. */
    public static final int CMD_ACK_CONNECT = 2;

    /** Disconnect a connection. */
    public static final int CMD_DISCONNECT = 10;

    /** Sends a message. */
    public static final int CMD_MESSAGE = 100;

    public static final int CMD_SETUP_SENT = 200;
    public static final int CONNECTION_LOST = 300;
    public static final ControlEventObject EVENT_CONNECT = new ControlEventObject(CMD_CONNECT);
    public static final ControlEventObject EVENT_ACK_CONNECT = new ControlEventObject(CMD_ACK_CONNECT);
    public static final ControlEventObject EVENT_DISCONNECT = new ControlEventObject(CMD_DISCONNECT);
    public static final ControlEventObject EVENT_SETUP_SENT = new ControlEventObject(CMD_SETUP_SENT);
    public static final ControlEventObject EVENT_CONNECTION_LOST = new ControlEventObject(CONNECTION_LOST);

    private final int command;

    public ControlEventObject(int command) {
        this.command = command;
    }

    public int getCommand() {
        return this.command;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append("\n");

        sb.append("command=");
        sb.append(command);
        return sb.toString();
    }
}