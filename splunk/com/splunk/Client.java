/*
 * Copyright 2011 Splunk, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"): you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.splunk;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class that all other endpoints classes will derive from
 */

public class Client {

    // UNDONE -- make inner class to support more than one iterator per client.
    public Service service = null;

    public Client() {
        // default constructor
    }
    public Client(Service serv) {
        service = serv;
    }

    public List<String> getList(Document doc, String path) {
        List<String> outlist = new ArrayList<String>();
        NodeList nl = doc.getElementsByTagName("id");

        // get everything in ID after base path
        for (int idx=1; idx < nl.getLength(); idx++) {
            String [] parts = nl.item(idx).getTextContent().split(path);
            if (parts.length >= 2) {
                // UNDONE: recode to non-url-safe?
                outlist.add(parts[1]);
            }
        }

        return outlist;
    }

    public Entity get(String path) throws Exception {
        Convert converter = new Convert();
        return converter.convertXMLData(service.get(path).getContent());
    }

    public Entity delete(String path) throws Exception {
        Convert converter = new Convert();
        // place holder -- service needs a delete method
        return converter.convertXMLData(service.get/*deletegoeshere*/(path).getContent());
    }

    public List<String> nameList(String path) throws Exception {
        Document doc = service.parseXml(service.get(path));
        return getList(doc, path);
    }
}