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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dmonix.battlex.resources.Opponents.Opponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Nerg
 * 
 */
public class OpponentsConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(OpponentsConfigurer.class);

    public static void addOpponent(OpponentConfigurationObject oc) {
        List<Opponent> opponents = read();
        opponents.add(toOpponent(oc));
        store(opponents);
    }

    public static void removeOpponent(String name) {
        List<Opponent> newOpponentList = new ArrayList<>();
        for (Opponent opponent : read()) {
            if (!name.equals(opponent.getName())) {
                newOpponentList.add(opponent);
            }
        }
        store(newOpponentList);
    }

    public static List<OpponentConfigurationObject> getOpponents() {
        List<OpponentConfigurationObject> opponents = new ArrayList<>();
        for (Opponent opponent : read()) {
            opponents.add(toOpponentConfigurationObject(opponent));
        }
        Collections.sort(opponents);
        return opponents;
    }

    /**
     * @param oc
     * @return
     */
    private static Opponent toOpponent(OpponentConfigurationObject oco) {
        Opponent opponent = new ObjectFactory().createOpponentsOpponent();
        opponent.setName(oco.getName());
        opponent.setHost(oco.getUrl());
        opponent.setDescription(oco.getDescription());
        opponent.setPort(BigInteger.valueOf(oco.getPort()));
        // TODO add proxy settings
        return opponent;
    }

    private static OpponentConfigurationObject toOpponentConfigurationObject(Opponent opponent) {
        String name = opponent.getName();
        String description = opponent.getDescription();
        String url = opponent.getHost();
        int port = opponent.getPort().intValue();
        OpponentConfigurationObject oco = new OpponentConfigurationObject(name, description, url, port);
        // TODO add proxy settings
        return oco;
    }

    private static void store(List<Opponent> opponentList) {
        ObjectFactory objectFactory = new ObjectFactory();
        Opponents opponents = objectFactory.createOpponents();
        opponents.getOpponent().addAll(opponentList);
        File cfgFile = getOpponentsConfigurationFile();

        try {
            JAXBContext ctx = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(opponents, cfgFile);
            logger.debug("Stored opponents [{}] to [{}]", opponentList.size(), cfgFile);
        } catch (JAXBException ex) {
            logger.error("Failed to store data to [" + cfgFile + "]", ex);
        }
    }

    private static List<Opponent> read() {
        File cfgFile = getOpponentsConfigurationFile();
        try {
            JAXBContext ctx = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            Opponents opponents = (Opponents) unmarshaller.unmarshal(cfgFile);
            List<Opponent> opponentList = opponents.getOpponent();
            logger.debug("Found [{}] opponents in [{}]", opponentList.size(), cfgFile);
            return opponentList;
        } catch (JAXBException ex) {
            logger.error("Failed to read contents of [" + cfgFile + "]", ex);
            return new ArrayList<>();
        }
    }

    private static File getOpponentsConfigurationFile() {
        return new File(System.getProperty("battlex.opponents.file", "../config/opponents.xml"));
    }
}
