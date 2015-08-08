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

/**
 * The class is used to send/receive status information to/from an other player.
 * 
 * @author Peter Nerg
 */
public final class StatusObject implements Serializable {
    private static final long serialVersionUID = 2576476784622776147L;

    public static final int STATUS_FREE = 1;
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_NOCON = 4;
    public static final int STATUS_UNKNOWN_HOST = 8;
    private int response = -1;

    public StatusObject() {
    }

    public int getResponse() {
        return this.response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}