/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dmonix.battlex.event;

import java.io.Serializable;

import org.dmonix.battlex.resources.Resources;

/**
 * @author Peter Nerg
 * @version 1.0
 */
public final class GameEventObject implements Serializable {
    private static final long serialVersionUID = 3562476784622776147L;

    private final int x_coord_new;
    private final int y_coord_new;
    private final int x_coord_old;
    private final int y_coord_old;

    private final String type;

    public GameEventObject(int x_coord_old, int y_coord_old, int x_coord_new, int y_coord_new) {
        // TODO is this used? null as type is bad.
        this(Resources.PIECE_NO_PIECE, 9 - x_coord_old, 9 - y_coord_old, 9 - x_coord_new, 9 - y_coord_new);
    }

    public GameEventObject(String type, int x_coord_new, int y_coord_new) {
        this(type, -1, -1, 9 - x_coord_new, 9 - y_coord_new);
    }

    private GameEventObject(String type, int x_coord_old, int y_coord_old, int x_coord_new, int y_coord_new) {
        this.x_coord_new = 9 - x_coord_new;
        this.y_coord_new = 9 - y_coord_new;

        this.x_coord_old = 9 - x_coord_old;
        this.y_coord_old = 9 - y_coord_old;

        this.type = type;
    }

    public String getType() {
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

        // TODO when does this happen
        if (Resources.PIECE_NO_PIECE.equals(type)) {
            sb.append("x-coord-old=");
            sb.append(x_coord_old);
            sb.append("\n");

            sb.append("y-coord-old=");
            sb.append(y_coord_old);
            sb.append("\n");
        } else {
            sb.append("type=");
            sb.append(type);
            sb.append("\n");
            sb.append("name=");
            sb.append(type);
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