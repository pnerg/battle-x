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
package org.dmonix.battlex.datamodel;

import static org.dmonix.battlex.datamodel.Player.PlayerBlue;

/**
 * @author Peter Nerg
 * 
 */
class PlayerRedRelativeSquare extends AbstractSquare {
    private static final long serialVersionUID = -762946745863895275L;

    PlayerRedRelativeSquare(int x, int y) {
        super(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Square#getAbsolute()
     */
    @Override
    public Square absolute() {
        return new AbsoluteSquare(x(), 9 - y());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Square#getRelative(int)
     */
    @Override
    public Square relative(Player player) {
        return player.isPlayerRed() ? this : absolute().relative(PlayerBlue);
    }

}
