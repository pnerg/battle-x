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
import static org.dmonix.battlex.datamodel.Player.PlayerRed;

import org.dmonix.battlex.BaseAssert;
import org.junit.Test;

/**
 * @author Peter Nerg
 * 
 */
public class TestSquare extends BaseAssert {

    @Test
    public void absoluteSquare_getAbsolute() {
        Square square = Square.apply(0, 0);
        Square absolute = square.absolute();
        assertEquals(square, absolute);
        assertEquals(square.x(), absolute.x());
        assertEquals(square.y(), absolute.y());
    }

    @Test
    public void absoluteSquare_getRelative1_0_0() {
        Square square = Square.apply(0, 0);
        Square relative = square.relative(PlayerRed);
        assertNotSame(square, relative);
        assertEquals(0, relative.x());
        assertEquals(9, relative.y());
    }

    @Test
    public void absoluteSquare_getRelative1_2_3() {
        Square square = Square.apply(2, 3);
        Square relative = square.relative(PlayerRed);
        assertNotSame(square, relative);
        assertEquals(2, relative.x());
        assertEquals(6, relative.y());
    }

    @Test
    public void absoluteSquare_getRelative1_all() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Square square = Square.apply(x, y);
                Square relative = square.relative(PlayerRed);
                assertNotSame(square, relative);
                assertEquals(x, relative.x());
                assertEquals(9 - y, relative.y());
            }
        }
    }

    @Test
    public void absoluteSquare_getRelative2_0_0() {
        Square square = Square.apply(0, 0);
        Square relative = square.relative(PlayerBlue);
        assertNotSame(square, relative);
        assertEquals(9, relative.x());
        assertEquals(0, relative.y());
    }

    @Test
    public void absoluteSquare_getRelative2_2_3() {
        Square square = Square.apply(2, 3);
        Square relative = square.relative(PlayerBlue);
        assertNotSame(square, relative);
        assertEquals(7, relative.x());
        assertEquals(3, relative.y());
    }

    @Test
    public void absoluteSquare_getRelative2_all() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Square square = Square.apply(x, y);
                Square relative = square.relative(PlayerBlue);
                assertNotSame(square, relative);
                assertEquals(9 - x, relative.x());
                assertEquals(y, relative.y());
            }
        }
    }

    @Test
    public void playerOneRelativeSquare_getAbsolute() {
        Square square = Square.apply(PlayerRed, 0, 9);
        Square absolute = square.absolute();
        assertNotSame(square, absolute);
        assertEquals(0, absolute.x());
        assertEquals(0, absolute.y());
    }

    @Test
    public void playerOneRelativeSquare_getRelative1() {
        Square square = Square.apply(PlayerRed, 0, 0);
        Square relative = square.relative(PlayerRed);
        assertEquals(square, relative);
        assertEquals(square.x(), relative.x());
        assertEquals(square.y(), relative.y());
    }

    @Test
    public void playerOneRelativeSquare_getRelative2() {
        Square square = Square.apply(PlayerRed, 0, 0);
        Square relative = square.relative(PlayerBlue);
        assertNotSame(square, relative);
        assertEquals(9, relative.x());
        assertEquals(9, relative.y());
    }

    @Test
    public void playerTwoRelativeSquare_getAbsolute() {
        Square square = Square.apply(PlayerBlue, 9, 0);
        Square absolute = square.absolute();
        assertNotSame(square, absolute);
        assertEquals(0, absolute.x());
        assertEquals(0, absolute.y());
    }

    @Test
    public void playerTwoRelativeSquare_getRelative2() {
        Square square = Square.apply(PlayerBlue, 0, 0);
        Square relative = square.relative(PlayerBlue);
        assertEquals(square, relative);
        assertEquals(square.x(), relative.x());
        assertEquals(square.y(), relative.y());
    }

    @Test
    public void playerTwoRelativeSquare_getRelative1() {
        Square square = Square.apply(PlayerBlue, 0, 0);
        Square relative = square.relative(PlayerRed);
        assertNotSame(square, relative);
        assertEquals(9, relative.x());
        assertEquals(9, relative.y());
    }
}
