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

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Nerg
 */
public class EventCommunicator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(EventCommunicator.class);

    private Thread thread = null;

    private ServerSocket serverSocket;

    private Socket socket = null;

    private ObjectOutputStream ostream = null;

    /** The control event listeners */
    private final List<ControlEventListener> ctrlEventListeners = new ArrayList<>();

    /** The game event listeners */
    private final Set<GameEventListener> gameEventListeners = new HashSet<>();

    /**
     * Sets up the communicator as a server
     * 
     * @param port
     * @throws IOException
     */
    public EventCommunicator(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Sets up the communicator as a client attempting to connect to a server
     * 
     * @param host
     * @param port
     * @throws IOException
     */
    public EventCommunicator(String host, int port) throws IOException {
        socket = new Socket(host, port);
        this.ostream = new ObjectOutputStream(socket.getOutputStream());
        new EventThread(this, socket);
    }

    /**
     * Add a control event listener
     * 
     * @param listener
     *            the listener
     */
    public void addEventListener(ControlEventListener listener) {
        this.ctrlEventListeners.add(listener);
    }

    /**
     * Add a game event listener
     * 
     * @param listener
     *            the listener
     */
    public void addEventListener(GameEventListener listener) {
        this.gameEventListeners.add(listener);
    }

    /**
     * Remove a control event listener
     * 
     * @param listener
     *            the listener
     */
    public void removeEventListener(ControlEventListener listener) {
        this.ctrlEventListeners.remove(listener);
    }

    /**
     * Remove a game event listener
     * 
     * @param listener
     *            the listener
     */
    public void removeEventListener(GameEventListener listener) {
        this.gameEventListeners.remove(listener);
    }

    /**
     * Close the socket that this class listens to
     */
    public void disconnect() {
        try {
            if (this.thread != null) {
                this.thread.interrupt();
                this.thread = null;
            }

            if (serverSocket != null) {
                this.serverSocket.close();
                this.serverSocket = null;
            }

            if (socket != null) {
                this.socket.close();
                this.socket = null;
            }

            this.ctrlEventListeners.clear();
            this.gameEventListeners.clear();
        } catch (IOException ex) {
            logger.warn("Problems with closing listener", ex);
        }
    }

    /**
     * Fires an event to all registered control event listeners
     * 
     * @param event
     *            the event
     */
    public void fireEvent(ControlEventObject event) {
        ctrlEventListeners.stream().forEach(l -> l.controlEvent(event));
    }

    /**
     * Fires an event to all registered game event listeners
     * 
     * @param event
     *            the event
     */
    public void fireEvent(GameEventObject event) {
        gameEventListeners.stream().forEach(l -> l.gameEvent(event));
    }

    /**
     * Is the communicator connected towards any other player.
     * 
     * @return
     */
    public boolean isConnected() {
        return this.ostream != null;
    }

    public void run() {
        logger.debug("Started event communicator listener on port [{}]", serverSocket.getLocalPort());

        while (thread != null && !thread.isInterrupted()) {
            try {
                Socket s = serverSocket.accept();
                this.ostream = new ObjectOutputStream(s.getOutputStream());
                new EventThread(this, s);
            } catch (SocketException se) {
                if (se.getMessage().indexOf("socket closed") < 0)
                    logger.warn("Exception occurred", se);
            } catch (Exception ex) {
                logger.warn("Exception occurred", ex);
            }
        }
    }

    /**
     * Sends a control event object.
     * 
     * @param ceo
     * @throws IOException
     */
    public void sendEvent(Serializable event)// throws IOException
    {
        try {
            logger.debug("Sending event [{}]", event);
            this.ostream.writeUnshared(event);
            this.ostream.flush();
        } catch (IOException ex) {
            logger.warn("Failed to send event object", ex);
        }
    }
}