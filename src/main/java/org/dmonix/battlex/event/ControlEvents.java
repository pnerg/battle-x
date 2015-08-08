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
package org.dmonix.battlex.event;

/**
 * Events to be used for sending game updates.
 * 
 * @author Peter Nerg
 */
public final class ControlEvents {
    private ControlEvents() {
    }

    /** Request a connection. */
    private static final int CMD_CONNECT = 1;

    /** Acknowledge a connection. */
    private static final int CMD_ACK_CONNECT = 2;

    /** Disconnect a connection. */
    private static final int CMD_DISCONNECT = 10;

    /** Sends a message. */
    private static final int CMD_MESSAGE = 100;

    private static final int CMD_SETUP_SENT = 200;
    private static final int CONNECTION_LOST = 300;

    public static final ControlEventObject EVENT_CONNECT = new ControlEventObject(CMD_CONNECT);
    public static final ControlEventObject EVENT_ACK_CONNECT = new ControlEventObject(CMD_ACK_CONNECT);
    public static final ControlEventObject EVENT_DISCONNECT = new ControlEventObject(CMD_DISCONNECT);
    public static final ControlEventObject EVENT_SETUP_SENT = new ControlEventObject(CMD_SETUP_SENT);
    public static final ControlEventObject EVENT_CONNECTION_LOST = new ControlEventObject(CONNECTION_LOST);

}
