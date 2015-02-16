package org.dmonix.battlex.event;

import java.io.Serializable;

import org.dmonix.battlex.resources.Resources;

/**
 * An event object used with the GameEventListener.
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
public final class GameEventObject implements Serializable {
    private static final long serialVersionUID = 3562476784622776147L;

    private final int x_coord_new;
    private final int y_coord_new;
    private final int x_coord_old;
    private final int y_coord_old;

    private final int type;

    public GameEventObject(int x_coord_old, int y_coord_old, int x_coord_new, int y_coord_new) {
        this(-1, 9 - x_coord_old, 9 - y_coord_old, 9 - x_coord_new, 9 - y_coord_new);
    }

    public GameEventObject(int type, int x_coord_new, int y_coord_new) {
        this(type, -1, -1, 9 - x_coord_new, 9 - y_coord_new);
    }

    private GameEventObject(int type, int x_coord_old, int y_coord_old, int x_coord_new, int y_coord_new) {
        this.x_coord_new = 9 - x_coord_new;
        this.y_coord_new = 9 - y_coord_new;

        this.x_coord_old = 9 - x_coord_old;
        this.y_coord_old = 9 - y_coord_old;

        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getNewXCoord() {
        return this.x_coord_new;
    }

    public int getOldXCoord() {
        return this.x_coord_old;
    }

    public int getNewYCoord() {
        return this.y_coord_new;
    }

    public int getOldYCoord() {
        return this.y_coord_old;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append("\n");

        if (type != -1) {
            sb.append("type=");
            sb.append(type);
            sb.append("\n");
            sb.append("name=");
            sb.append(Resources.getPieceName(type));
            sb.append("\n");
        } else {
            sb.append("x-coord-old=");
            sb.append(x_coord_old);
            sb.append("\n");

            sb.append("y-coord-old=");
            sb.append(y_coord_old);
            sb.append("\n");
        }

        sb.append("x-coord-new=");
        sb.append(x_coord_new);
        sb.append("\n");

        sb.append("y-coord-new=");
        sb.append(y_coord_new);
        sb.append("\n");

        return sb.toString();
    }

}