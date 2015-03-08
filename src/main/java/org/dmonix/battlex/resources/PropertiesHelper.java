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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Nerg
 * 
 */
public class PropertiesHelper {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);

    public static void loadAndSetProperties(File propertyFile) throws IOException {
        Properties properties = new Properties();
        if (!propertyFile.isFile()) {
            logger.error("Failed to load configuration file [{}]", propertyFile.getAbsolutePath());
        }
        try (InputStream istream = new FileInputStream(propertyFile)) {
            properties.load(istream);
            for (Object name : properties.keySet()) {
                logger.info("Setting property [{}][{}]", name, properties.get(name));
                System.setProperty(name.toString(), properties.get(name).toString());
            }
        }
    }

    public static void setIfNotSet(String name, int value) {
        setIfNotSet(name, String.valueOf(value));
    }

    public static void setIfNotSet(String name, String value) {
        if (System.getProperty(name) != null) {
            System.setProperty(name, value);
        }
    }

    public static int getIntProperty(String name) {
        return Integer.parseInt(getProperty(name));
    }

    public static String getProperty(String name) {
        if (System.getProperty(name) == null) {
            logger.error("No such property is set [{}]", name);
            throw new IllegalArgumentException("No such property [" + name + "]");
        }

        return System.getProperty(name);
    }
}
