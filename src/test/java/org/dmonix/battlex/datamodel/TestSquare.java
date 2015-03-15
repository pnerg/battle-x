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

import org.dmonix.battlex.BaseAssert;
import org.junit.Test;

/**
 * @author Peter Nerg
 * 
 */
public class TestSquare extends BaseAssert {

    @Test
    public void absoluteSquare_getAbsolute() {
        Square square = SquareFactory.createAbsolute(0, 0);
        Square absolute = square.getAbsolute();
        assertEquals(square, absolute);
        assertEquals(square.getX(), absolute.getX());
        assertEquals(square.getY(), absolute.getY());
    }

    @Test
    public void absoluteSquare_getRelative1_0_0() {
        Square square = SquareFactory.createAbsolute(0, 0);
        Square relative = square.getRelative(1);
        assertNotSame(square, relative);
        assertEquals(0, relative.getX());
        assertEquals(9, relative.getY());
    }

    @Test
    public void absoluteSquare_getRelative1_2_3() {
        Square square = SquareFactory.createAbsolute(2, 3);
        Square relative = square.getRelative(1);
        assertNotSame(square, relative);
        assertEquals(2, relative.getX());
        assertEquals(6, relative.getY());
    }

    @Test
    public void absoluteSquare_getRelative1_all() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Square square = SquareFactory.createAbsolute(x, y);
                Square relative = square.getRelative(1);
                assertNotSame(square, relative);
                assertEquals(x, relative.getX());
                assertEquals(9 - y, relative.getY());
            }
        }
    }

    @Test
    public void absoluteSquare_getRelative2_0_0() {
        Square square = SquareFactory.createAbsolute(0, 0);
        Square relative = square.getRelative(2);
        assertNotSame(square, relative);
        assertEquals(9, relative.getX());
        assertEquals(0, relative.getY());
    }

    @Test
    public void absoluteSquare_getRelative2_2_3() {
        Square square = SquareFactory.createAbsolute(2, 3);
        Square relative = square.getRelative(2);
        assertNotSame(square, relative);
        assertEquals(7, relative.getX());
        assertEquals(3, relative.getY());
    }

    @Test
    public void absoluteSquare_getRelative2_all() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Square square = SquareFactory.createAbsolute(x, y);
                Square relative = square.getRelative(2);
                assertNotSame(square, relative);
                assertEquals(9 - x, relative.getX());
                assertEquals(y, relative.getY());
            }
        }
    }

    @Test
    public void playerOneRelativeSquare_getAbsolute() {
        Square square = SquareFactory.createRelative(1, 0, 9);
        Square absolute = square.getAbsolute();
        assertNotSame(square, absolute);
        assertEquals(0, absolute.getX());
        assertEquals(0, absolute.getY());
    }

    @Test
    public void playerOneRelativeSquare_getRelative1() {
        Square square = SquareFactory.createRelative(1, 0, 0);
        Square relative = square.getRelative(1);
        assertEquals(square, relative);
        assertEquals(square.getX(), relative.getX());
        assertEquals(square.getY(), relative.getY());
    }

    @Test
    public void playerOneRelativeSquare_getRelative2() {
        Square square = SquareFactory.createRelative(1, 0, 0);
        Square relative = square.getRelative(2);
        assertNotSame(square, relative);
        assertEquals(9, relative.getX());
        assertEquals(9, relative.getY());
    }

    @Test
    public void playerTwoRelativeSquare_getAbsolute() {
        Square square = SquareFactory.createRelative(2, 9, 0);
        Square absolute = square.getAbsolute();
        assertNotSame(square, absolute);
        assertEquals(0, absolute.getX());
        assertEquals(0, absolute.getY());
    }

    @Test
    public void playerTwoRelativeSquare_getRelative2() {
        Square square = SquareFactory.createRelative(2, 0, 0);
        Square relative = square.getRelative(2);
        assertEquals(square, relative);
        assertEquals(square.getX(), relative.getX());
        assertEquals(square.getY(), relative.getY());
    }

    @Test
    public void playerTwoRelativeSquare_getRelative1() {
        Square square = SquareFactory.createRelative(2, 0, 0);
        Square relative = square.getRelative(1);
        assertNotSame(square, relative);
        assertEquals(9, relative.getX());
        assertEquals(9, relative.getY());
    }
}
