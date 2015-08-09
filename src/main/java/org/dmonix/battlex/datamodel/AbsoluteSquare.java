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

/**
 * @author Peter Nerg
 * 
 */
class AbsoluteSquare extends AbstractSquare {
    private static final long serialVersionUID = -9144650732136634392L;

    AbsoluteSquare(int x, int y) {
        super(x, y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Square#getAbsolute()
     */
    @Override
    public Square absolute() {
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Square#getRelative(int)
     */
    @Override
    public Square relative(int player) {
        return player == 1 ? new PlayerOneRelativeSquare(x(), 9 - y()) : new PlayerTwoRelativeSquare(9 - x(), y());
    }
}
