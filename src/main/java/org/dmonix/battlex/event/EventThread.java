package org.dmonix.battlex.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread that awaits for objects on a socket.
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
public final class EventThread implements Runnable {
    /** The logger instance for this class */
    private static final Logger log = Logger.getLogger(EventThread.class.getName());

    private Thread thread;
    private String connectedTo;
    private ObjectInputStream istream;
    private EventCommunicator eventCommunicator;

    public EventThread(EventCommunicator eventCommunicator, Socket socket) throws IOException {
        connectedTo = socket.toString();
        if (log.isLoggable(Level.FINER))
            log.log(Level.FINER, "Initiated connection to/from: " + connectedTo);

        this.eventCommunicator = eventCommunicator;
        this.istream = new ObjectInputStream(socket.getInputStream());
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object event = istream.readObject();
                if (log.isLoggable(Level.FINE))
                    log.log(Level.FINE, "Received event from " + connectedTo + "\n" + event.toString());

                if (event instanceof GameEventObject) {
                    // fire event to all listeners
                    eventCommunicator.fireEvent((GameEventObject) event);
                } else if (event instanceof ControlEventObject) {
                    // fire event to all listeners
                    eventCommunicator.fireEvent((ControlEventObject) event);
                } else {
                    log.log(Level.WARNING, "Class type not recognized : " + event.getClass().getName() + "\n" + event.toString());
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SocketException ex) {
            if (log.isLoggable(Level.FINE))
                log.log(Level.FINE, "Disconnected from " + connectedTo);

            eventCommunicator.fireEvent(ControlEventObject.EVENT_CONNECTION_LOST);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
