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

package org.dmonix.battlex.resources;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.dmonix.battlex.BaseAssert;
import org.dmonix.battlex.resources.Opponents.Opponent;
import org.dmonix.io.IOUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the class {@link OpponentsConfigurer}.
 * 
 * @author Peter Nerg
 */
public class TestOpponentsConfigurer extends BaseAssert {

    private final File configFile = new File("target/config/opponents-test.xml");
    private final ObjectFactory objectFactory = new ObjectFactory();

    @Before
    public void before() throws IOException {
        File file = new File(TestOpponentsConfigurer.class.getResource("/opponents-test.xml").getFile());
        IOUtil.copyFile(file, configFile);
        System.setProperty("battlex.opponents.file", configFile.getAbsolutePath());
    }

    @After
    public void after() {
        IOUtil.deleteFile(configFile);
        System.clearProperty("battlex.opponents.file");
    }

    @Test
    public void getOpponents() throws JAXBException {
        List<OpponentConfigurationObject> opponents = OpponentsConfigurer.getOpponents();
        assertNotNull(opponents);
        assertEquals(1, opponents.size());
        OpponentConfigurationObject oco = opponents.get(0);
        assertEquals("peter", oco.getName());
        assertEquals("localhost", oco.getUrl());
        assertEquals(6969, oco.getPort());
    }

    @Test
    public void addOpponent() {
        OpponentConfigurationObject oco = new OpponentConfigurationObject("sockerconny", "elak fan", "127.0.0.1", 666);
        OpponentsConfigurer.addOpponent(oco);

        List<OpponentConfigurationObject> opponents = OpponentsConfigurer.getOpponents();
        assertNotNull(opponents);
        assertEquals(2, opponents.size());
    }

    @Test
    public void removeOpponent() {
        OpponentsConfigurer.removeOpponent("peter");
        List<OpponentConfigurationObject> opponents = OpponentsConfigurer.getOpponents();
        assertNotNull(opponents);
        assertEquals(0, opponents.size());
    }

    @Test
    public void store() throws JAXBException {
        Opponents opponents = objectFactory.createOpponents();
        Opponent opponent = objectFactory.createOpponentsOpponent();
        opponent.setName("Test");
        opponent.setHost("127.0.0.1");
        opponent.setDescription("used for test");
        opponent.setPort(BigInteger.valueOf(6969));
        opponents.getOpponent().add(opponent);

        // OpponentsConfigurer.store(opponents);
    }
}
