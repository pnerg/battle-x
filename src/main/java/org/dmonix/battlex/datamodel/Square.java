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

import java.io.Serializable;

/**
 * Represents a square on the game board.<br>
 * The square can either be absolute or relative.<br>
 * An absolute square maps to the board in such way that the coordinate system is as follows. <br>
 * 
 * <pre>
 * <i>Absolute: x/y</i>
 * 0/9 1/9 2/9 3/9 4/9 5/9 6/9 7/9 8/9 9/9
 * 0/8 1/8 2/8 3/8 4/8 5/8 6/8 7/8 8/8 9/8
 * 0/7 1/7 2/7 3/7 4/7 5/7 6/7 7/7 8/7 9/7
 * 0/6 1/6 2/6 3/6 4/6 5/6 6/6 7/6 8/6 9/6
 * 0/5 1/5 2/5 3/5 4/5 5/5 6/5 7/5 8/5 9/5
 * 0/4 1/4 2/4 3/4 4/4 5/4 6/4 7/4 8/4 9/4
 * 0/3 1/3 2/3 3/3 4/3 5/3 6/3 7/3 8/3 9/3
 * 0/2 1/2 2/2 3/2 4/2 5/2 6/2 7/2 8/2 9/2
 * 0/1 1/1 2/1 3/1 4/1 5/1 6/1 7/1 8/1 9/1
 * 0/0 1/0 2/0 3/0 4/0 5/0 6/0 7/0 8/0 9/0
 * </pre>
 * 
 * @author Peter Nerg
 * 
 */
public interface Square extends Serializable {
    Square getAbsolute();

    Square getRelative(int player);

    int getX();

    int getY();
}
