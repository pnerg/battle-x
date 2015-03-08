package org.dmonix.battlex;

import org.dmonix.battlex.event.ControlEvents;
import org.dmonix.battlex.event.EventCommunicator;
import org.dmonix.battlex.event.GameEventObject;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
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
public class TestCommunication {
    EventCommunicator sender;
    EventCommunicator sender2;

    public TestCommunication() throws Exception {
        // sender = new EventSender("136.225.5.218", 6969);
        sender = new EventCommunicator("localhost", 6969);
        // sender2 = new EventCommunicator("localhost", 6969);

        sender.sendEvent(ControlEvents.EVENT_CONNECT);
        // System.out.println(sender.requestStatus("136.225.5.218", 6969));
        // System.out.println(sender.requestStatus("136.434.534.3243", 6969));
        sendGameEvent(2, 1, 2, 2);
        sendGameEvent(3, 1, 3, 2);
        // sendGameEvent();
        // sendGameEvent();
    }

    private void sendGameEvent(int x_coord_old, int y_coord_old, int x_coord_new, int y_coord_new) throws Exception {
        GameEventObject geo = new GameEventObject(x_coord_old, y_coord_old, x_coord_new, y_coord_new);
        sender.sendEvent(geo);
    }

    public static void main(String[] args) {
        try {
            TestCommunication testCommunication1 = new TestCommunication();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}