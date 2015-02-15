package org.dmonix.battlex.event;

import java.io.Serializable;

/**
 * The class is used to send/receive status information to/from an other player.
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: dmonix.org</p>
 * @author Peter Nerg
 * @version 1.0
 */
public final class StatusObject implements Serializable {
    private static final long serialVersionUID = 2576476784622776147L;

    public static final int STATUS_FREE = 1;
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_NOCON = 4;
    public static final int STATUS_UNKNOWN_HOST = 8;
    private int response = -1;

    public StatusObject() {
    }

    public int getResponse() {
        return this.response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}