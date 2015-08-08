//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.22 at 10:21:27 AM CET 
//

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
package org.dmonix.battlex.resources;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="opponent" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="host" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="proxy">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="proxyHost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="proxyPort" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="useProxy" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "opponent" })
@XmlRootElement(name = "opponents")
public class Opponents {

    protected List<Opponents.Opponent> opponent;

    /**
     * Gets the value of the opponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present
     * inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the opponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getOpponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Opponents.Opponent }
     * 
     * 
     */
    public List<Opponents.Opponent> getOpponent() {
        if (opponent == null) {
            opponent = new ArrayList<Opponents.Opponent>();
        }
        return this.opponent;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="host" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="port" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="proxy">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="proxyHost" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="proxyPort" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="useProxy" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = { "description", "host", "port", "proxy" })
    public static class Opponent {

        @XmlElement(required = true, defaultValue = "")
        protected String description;
        @XmlElement(required = true)
        protected String host;
        @XmlElement(required = true)
        protected BigInteger port;
        @XmlElement(required = true)
        protected Opponents.Opponent.Proxy proxy;
        @XmlAttribute(name = "name", required = true)
        protected String name;

        /**
         * Gets the value of the description property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the host property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getHost() {
            return host;
        }

        /**
         * Sets the value of the host property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setHost(String value) {
            this.host = value;
        }

        /**
         * Gets the value of the port property.
         * 
         * @return possible object is {@link BigInteger }
         * 
         */
        public BigInteger getPort() {
            return port;
        }

        /**
         * Sets the value of the port property.
         * 
         * @param value
         *            allowed object is {@link BigInteger }
         * 
         */
        public void setPort(BigInteger value) {
            this.port = value;
        }

        /**
         * Gets the value of the proxy property.
         * 
         * @return possible object is {@link Opponents.Opponent.Proxy }
         * 
         */
        public Opponents.Opponent.Proxy getProxy() {
            return proxy;
        }

        /**
         * Sets the value of the proxy property.
         * 
         * @param value
         *            allowed object is {@link Opponents.Opponent.Proxy }
         * 
         */
        public void setProxy(Opponents.Opponent.Proxy value) {
            this.proxy = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return possible object is {@link String }
         * 
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *            allowed object is {@link String }
         * 
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * <p>
         * Java class for anonymous complex type.
         * 
         * <p>
         * The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="proxyHost" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="proxyPort" type="{http://www.w3.org/2001/XMLSchema}integer"/>
         *       &lt;/sequence>
         *       &lt;attribute name="useProxy" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = { "proxyHost", "proxyPort" })
        public static class Proxy {

            @XmlElement(required = true)
            protected String proxyHost;
            @XmlElement(required = true)
            protected BigInteger proxyPort;
            @XmlAttribute(name = "useProxy")
            protected Boolean useProxy;

            /**
             * Gets the value of the proxyHost property.
             * 
             * @return possible object is {@link String }
             * 
             */
            public String getProxyHost() {
                return proxyHost;
            }

            /**
             * Sets the value of the proxyHost property.
             * 
             * @param value
             *            allowed object is {@link String }
             * 
             */
            public void setProxyHost(String value) {
                this.proxyHost = value;
            }

            /**
             * Gets the value of the proxyPort property.
             * 
             * @return possible object is {@link BigInteger }
             * 
             */
            public BigInteger getProxyPort() {
                return proxyPort;
            }

            /**
             * Sets the value of the proxyPort property.
             * 
             * @param value
             *            allowed object is {@link BigInteger }
             * 
             */
            public void setProxyPort(BigInteger value) {
                this.proxyPort = value;
            }

            /**
             * Gets the value of the useProxy property.
             * 
             * @return possible object is {@link Boolean }
             * 
             */
            public boolean isUseProxy() {
                if (useProxy == null) {
                    return false;
                } else {
                    return useProxy;
                }
            }

            /**
             * Sets the value of the useProxy property.
             * 
             * @param value
             *            allowed object is {@link Boolean }
             * 
             */
            public void setUseProxy(Boolean value) {
                this.useProxy = value;
            }

        }

    }

}
