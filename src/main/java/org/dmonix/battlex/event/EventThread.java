package org.dmonix.battlex.event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(EventThread.class);

    private Thread thread;
    private ObjectInputStream istream;
    private EventCommunicator eventCommunicator;

    private final Socket socket;

    public EventThread(EventCommunicator eventCommunicator, Socket socket) throws IOException {
        this.socket = socket;
        logger.debug("Initiated connection to/from [{}]", socket);

        this.eventCommunicator = eventCommunicator;
        this.istream = new ObjectInputStream(socket.getInputStream());
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object event = istream.readObject();
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
            eventCommunicator.fireEvent(ControlEventObject.EVENT_CONNECTION_LOST);
            ex.printStackTrace();
        } catch (ClassNotFoundException | IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

    }
}
