package org.dmonix.battlex.event;

import java.util.EventListener;

/**
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: dmonix.org</p>
 * @author Peter Nerg
 * @version 1.0
 */
public interface ControlEventListener extends EventListener
{

  public void controlEvent(ControlEventObject ceo);

}