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

package org.dmonix.battlex.datamodel;

/**
 * @author Peter Nerg
 * 
 */
class PlayerTwoRelativeSquare extends AbstractSquare {
    private static final long serialVersionUID = 332513439159455944L;

    PlayerTwoRelativeSquare(int x, int y) {
        super(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Square#getAbsolute()
     */
    @Override
    public Square getAbsolute() {
        return new AbsoluteSquare(9 - getX(), getY());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Square#getRelative(int)
     */
    @Override
    public Square getRelative(int player) {
        return player == 2 ? this : getAbsolute().getRelative(1);
    }

}
