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
package org.dmonix.battlex;

import org.dmonix.battlex.event.ControlEventListener;
import org.dmonix.battlex.event.ControlEventObject;
import org.dmonix.battlex.event.GameEventListener;
import org.dmonix.battlex.event.GameEventObject;

public class TestListeners implements ControlEventListener, GameEventListener {
    public TestListeners() throws Exception {
        // EventReceiver receiver = new EventReceiver(6969);
        // receiver.addControlEventListener(this);
        // receiver.addGameEventListener(this);
    }

    public void controlEvent(ControlEventObject ceo) {
        System.out.println(ceo.toString());
    }

    public void gameEvent(GameEventObject geo) {
        System.out.println(geo.toString());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }
    }

}