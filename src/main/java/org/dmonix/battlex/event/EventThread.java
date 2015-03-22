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
package org.dmonix.battlex.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread that awaits for objects on a socket.
 * 
 * @author Peter Nerg
 * @version 1.0
 */
public final class EventThread implements Runnable {
    /** The logger instance for this class */
    private static final Logger logger = LoggerFactory.getLogger(EventThread.class);

    private final Thread thread;
    private final ObjectInputStream istream;
    private final EventCommunicator eventCommunicator;

    private final Socket socket;

    public EventThread(EventCommunicator eventCommunicator, Socket socket) throws IOException {
        this.socket = socket;
        logger.debug("Initiated connection to/from [{}]", socket);

        this.eventCommunicator = eventCommunicator;
        this.istream = new ObjectInputStream(socket.getInputStream());
        this.thread = new Thread(this, "EventListenerThread");
        this.thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object event = istream.readUnshared();
                logger.debug("Received event [{}] from [{}]", event, socket);

                if (event instanceof GameEventObject) {
                    // fire event to all listeners
                    eventCommunicator.fireEvent((GameEventObject) event);
                } else if (event instanceof ControlEventObject) {
                    // fire event to all listeners
                    eventCommunicator.fireEvent((ControlEventObject) event);
                } else {
                    logger.warn("Class type not recognized [{}]", event.getClass());
                }
            }
        } catch (SocketException ex) {
            logger.debug("Disconnected from [{}]", socket);
            eventCommunicator.fireEvent(ControlEvents.EVENT_CONNECTION_LOST);
            ex.printStackTrace();
        } catch (ClassNotFoundException | IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

    }
}
