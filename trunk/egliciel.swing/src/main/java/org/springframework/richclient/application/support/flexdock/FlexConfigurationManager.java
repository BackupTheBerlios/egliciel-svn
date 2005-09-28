/*
 * Copyright 2002-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.richclient.application.support.flexdock;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.flexdock.docking.config.ConfigurationManager;
import org.flexdock.docking.config.DockingConfiguration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * @author Claudio Romano - cro
 * 
 */
public class FlexConfigurationManager {

    public String defaultLayoutLocation;

    public String persistentFileName;

    private DockingConfiguration config;

    private Resource configResource;

    private List viewList;

    private static final Log logger = LogFactory.getLog(FlexConfigurationManager.class);

    public FlexConfigurationManager() {
    }

    public void ensureInizialized() {
        if (config == null) {
            
            Assert.hasLength(persistentFileName);
            Assert.hasLength(defaultLayoutLocation);
            

            //create resource loader
            File persistentFile = new File(persistentFileName);
            if( persistentFile.exists()) {
                configResource = new FileSystemResource(persistentFile);
            } else {
                configResource = new DefaultResourceLoader().getResource(defaultLayoutLocation);
            }
            
            try {
                Assert.notNull(configResource, "Default layout location " + defaultLayoutLocation + " not found");
                
                //load view list
                viewList = new ArrayList();
                viewList = new ViewConfiguration().parse(new InputSource(configResource.getInputStream()));
                
                //load conifuration
                config = ConfigurationManager.load(configResource.getURL());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return Returns the defaultLayoutLocation.
     */
    public String getDefaultLayoutLocation() {
        return defaultLayoutLocation;
    }

    /**
     * @param defaultLayoutLocation
     *            The defaultLayoutLocation to set.
     */
    public void setDefaultLayoutLocation(String defaultLayoutLocation) {
        this.defaultLayoutLocation = defaultLayoutLocation;
    }

    /**
     * @return Returns the persistentFileName.
     */
    public String getPersistentFileName() {
        return persistentFileName;
    }

    /**
     * @param persistentFileName
     *            The persistentFileName to set.
     */
    public void setPersistentFileName(String persistentFileName) {
        this.persistentFileName = persistentFileName;
    }


    public List getViewList() {
        ensureInizialized();
        return viewList;
    }

    public DockingConfiguration getDockingConfiguration() {
        return config;
    }

    public void applyConfiguration() {
        ensureInizialized();
        ConfigurationManager.applyConfiguration(config);
    }

    public void saveConfiguration() {
        ensureInizialized();
        DockingConfiguration config = ConfigurationManager.createDockingConfiguration();

        File outFile = new File(persistentFileName);

        if (!outFile.exists()) {
            try {
                outFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ConfigurationManager.store(config, outFile);
    }
    
    
    private class ViewConfiguration extends DefaultHandler {
        StringBuffer contentBuffer;
        List list;
        
        private List parse(InputSource inputSource) {
            list = new ArrayList();
            contentBuffer = new StringBuffer();
            
            try {
    	        SAXParser p = SAXParserFactory.newInstance ().newSAXParser ();
    	
    	        XMLReader reader = p.getXMLReader ();
    	        reader.setContentHandler (this);
    	        reader.parse (inputSource);
            }catch( Exception e) {
                e.printStackTrace();
            }
            return list;
        }
        
        public void characters (char[] chars, int start, int length) {
            contentBuffer.append (chars, start, length);
        }
        
        public void startElement (String uri, String lname, String qname, Attributes attributes) {
            logger.info("contentBuffer = "+ contentBuffer.toString());
            logger.info("lname = "+ lname);
            logger.info("qname = "+ qname);
            contentBuffer.setLength (0);
            if (qname.equals ("Dockable")) {
                list.add(attributes.getValue("id"));
            }
        }
        
        
    }
}