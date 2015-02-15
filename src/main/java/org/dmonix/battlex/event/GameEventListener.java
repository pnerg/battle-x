package org.dmonix.battlex.event;

import java.util.EventListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: dmonix.org</p>
 * @author Peter Nerg
 * @version 1.0
 */

public interface GameEventListener extends EventListener
{

  public void gameEvent(GameEventObject geo);

}