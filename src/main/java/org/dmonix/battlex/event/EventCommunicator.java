package org.dmonix.battlex.event;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final Logger log = Logger.getLogger(EventCommunicator.class.getName());

    private Thread thread = null;

    private ServerSocket serverSocket;

    private Socket socket = null;

    private ObjectOutputStream ostream = null;

    /** The control event listeners */
    private Vector ctrlEventListeners = new Vector();

    /** The game event listeners */
    private Vector gameEventListeners = new Vector();

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
                if (log.isLoggable(Level.FINE))
                    log.log(Level.FINE, "Closed event thread");
            }

            if (serverSocket != null) {
                this.serverSocket.close();
                this.serverSocket = null;
                if (log.isLoggable(Level.FINE))
                    log.log(Level.FINE, "Closed serversocket");
            }

            if (socket != null) {
                this.socket.close();
                this.socket = null;
                if (log.isLoggable(Level.FINE))
                    log.log(Level.FINE, "Closed socket");
            }

            this.ctrlEventListeners.clear();
            this.gameEventListeners.clear();
        } catch (IOException ex) {
            log.log(Level.WARNING, "Problems with closing listener", ex);
        }
    }

    /**
     * Fires an event to all registered control event listeners
     * 
     * @param event
     *            the event
     */
    public void fireEvent(ControlEventObject event) {
        for (int i = 0; i < this.ctrlEventListeners.size(); i++) {
            ((ControlEventListener) this.ctrlEventListeners.elementAt(i)).controlEvent(event);
        }
    }

    /**
     * Fires an event to all registered game event listeners
     * 
     * @param event
     *            the event
     */
    public void fireEvent(GameEventObject event) {
        for (int i = 0; i < this.gameEventListeners.size(); i++) {
            ((GameEventListener) this.gameEventListeners.elementAt(i)).gameEvent(event);
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
        if (log.isLoggable(Level.FINE))
            log.log(Level.FINE, "Started event communicator listener on port: " + serverSocket.getLocalPort());

        while (thread != null && !thread.isInterrupted()) {
            try {
                Socket s = serverSocket.accept();
                this.ostream = new ObjectOutputStream(s.getOutputStream());
                new EventThread(this, s);
            } catch (SocketException se) {
                if (se.getMessage().indexOf("socket closed") < 0)
                    log.log(Level.WARNING, "Exception occurred", se);
            } catch (Exception ex) {
                log.log(Level.WARNING, "Exception occurred", ex);
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
            log.log(Level.WARNING, "Failed to control event", ex);
        }
    }

    /**
     * Sends a game event object
     * 
     * @param geo
     * @throws IOException
     */
    public void sendEvent(GameEventObject geo)// throws IOException
    {
        try {
            this.send(geo);
        } catch (IOException ex) {
            log.log(Level.WARNING, "Failed to send game event", ex);
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
        if (log.isLoggable(Level.FINE))
            log.log(Level.FINE, "Sending object:\n" + obj.toString());

        this.ostream.writeObject(obj);
        this.ostream.flush();
    }
}