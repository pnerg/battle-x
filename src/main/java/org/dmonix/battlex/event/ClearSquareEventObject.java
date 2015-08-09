/**
 *  Copyright 2015 Peter Nerg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dmonix.battlex.event;

import java.io.Serializable;

import org.dmonix.battlex.datamodel.Square;

/**
 * Event object to signal that a square should be cleared.<br>
 * Used during setup of the board when players add/remove pieces.
 * 
 * @author Peter Nerg
 * 
 */
public class ClearSquareEventObject implements Serializable {

    private static final long serialVersionUID = 4737898205362176967L;
    private final Square square;

    public ClearSquareEventObject(Square square) {
        this.square = square.absolute();
    }

    /**
     * @return the square
     */
    public Square getSquare() {
        return square;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ClearSquareEventObject:" + square;
    }
}
