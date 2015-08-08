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

import org.dmonix.battlex.datamodel.PieceData;
import org.dmonix.battlex.datamodel.Square;

/**
 * @author Peter Nerg
 * @version 1.0
 */
public final class GameEventObject implements Serializable {
    private static final long serialVersionUID = 3562476784622776147L;

    private final String type;

    private final Square oldCoord;
    private final Square newCoord;

    public GameEventObject(Square oldCoord, Square newCoord) {
        // TODO is this used? null as type is bad.
        this(PieceData.PIECE_NO_PIECE, oldCoord, newCoord);
    }

    public GameEventObject(String type, Square newCoord) {
        // TODO is this used? Kind of ugly with -1 coord values? Create non-valid square type?
        this(type, Square.apply(-1, -1), newCoord);
    }

    private GameEventObject(String type, Square oldCoord, Square newCoord) {
        this.type = type;
        this.oldCoord = oldCoord.getAbsolute();
        this.newCoord = newCoord.getAbsolute();
    }

    public String getType() {
        return type;
    }

    public Square getNewCoord() {
        return newCoord;
    }

    public Square getOldCoord() {
        return oldCoord;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append("\n");

        // TODO when does this happen
        if (PieceData.PIECE_NO_PIECE.equals(type)) {
            sb.append("oldCoord=").append(oldCoord).append("\n");
        } else {
            sb.append("type=").append(type).append("\n");
        }

        sb.append("newCoord=").append(newCoord).append("\n");

        return sb.toString();
    }

}