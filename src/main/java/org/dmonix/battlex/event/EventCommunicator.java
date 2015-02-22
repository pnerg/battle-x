package org.dmonix.battlex.event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class EventCommunicator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(EventCommunicator.class);

    private Thread thread = null;

    private ServerSocket serverSocket;

    private Socket socket = null;

    private ObjectOutputStream ostream = null;

    /** The control event listeners */
    private final List<ControlEventListener> ctrlEventListeners = new ArrayList<>();

    /** The game event listeners */
    private final List<GameEventListener> gameEventListeners = new ArrayList<>();

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
        for (ControlEventListener listener : ctrlEventListeners) {
            listener.controlEvent(event);
        }
    }

    /**
     * Fires an event to all registered game event listeners
     * 
     * @param event
     *            the event
     */
    public void fireEvent(GameEventObject event) {
        for (GameEventListener listener : gameEventListeners) {
            listener.gameEvent(event);
        }
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
    public void sendEvent(ControlEventObject ceo)// throws IOException
    {
        try {
            this.send(ceo);
        } catch (IOException ex) {
            logger.warn("Failed to send control event", ex);
        }
    }

    /**
     * Sends a game event object
     * 
     * @param geo
     * @throws IOException
     */
    public void sendEvent(GameEventObject geo) {
        try {
            this.send(geo);
        } catch (IOException ex) {
            logger.warn("Failed to send game event", ex);
        }
    }

    /**
     * General send method.
     * 
     * @param obj
     * @param url
     * @param port
     * @throws IOException
     */
    // private void send(Object obj, String url, int port) throws IOException
    // {
    // ObjectOutputStream ostream = new
    // ObjectOutputStream(socket.getOutputStream());
    // ostream.writeObject(obj);
    // ostream.flush();
    // ostream.close();
    // }
    /**
     * General send method.
     * 
     * @param obj
     * @throws IOException
     */
    private void send(Object obj) throws IOException {
        logger.debug("Sending object [{}]", obj);
        this.ostream.writeObject(obj);
        this.ostream.flush();
    }
}