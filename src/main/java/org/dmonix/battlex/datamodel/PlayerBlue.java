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
public class PlayerBlue implements Player {

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Player#other()
     */
    @Override
    public Player other() {
        return PlayerRed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Player#isPlayerRed()
     */
    @Override
    public boolean isPlayerRed() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.dmonix.battlex.datamodel.Player#asInt()
     */
    @Override
    public int asInt() {
        return 2;
    }

    /**
     * Returns <code>true</code> if other is a {@link PlayerBlue} as {@link PlayerBlue} is stateless comparing it to some other None is therefore always the
     * same, <code>false</code> otherwise.
     * 
     * @param other
     *            The other object to compare to
     * @return <code>true</code> if other is {@link PlayerBlue}, <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof PlayerBlue;
    }

    /**
     * Always returns <code>1</code> as PlayerBlue is stateless and has no value.
     * 
     * @return 2
     */
    @Override
    public int hashCode() {
        return asInt();
    }

    /**
     * Returns a String representation of the instance.
     */
    @Override
    public String toString() {
        return "PlayerBlue";
    }

}
