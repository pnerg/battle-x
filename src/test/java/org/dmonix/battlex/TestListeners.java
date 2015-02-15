package org.dmonix.battlex;

import java.util.logging.LogManager;

import org.dmonix.battlex.event.ControlEventListener;
import org.dmonix.battlex.event.ControlEventObject;
import org.dmonix.battlex.event.GameEventListener;
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
public class TestListeners implements ControlEventListener, GameEventListener {
    public TestListeners() throws Exception {
        // EventReceiver receiver = new EventReceiver(6969);
        // receiver.addControlEventListener(this);
        // receiver.addGameEventListener(this);
    }

    public void controlEvent(ControlEventObject ceo) {
        System.out.println(ceo.toString());
    }

    public void gameEvent(GameEventObject geo) {
        System.out.println(geo.toString());
    }

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(TestListeners.class.getClassLoader().getResourceAsStream("log.properties"));
            TestListeners testListeners1 = new TestListeners();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}