/*
 * Copyright 2002-2004 the original author or authors.
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


import org.springframework.richclient.application.PageLayoutBuilder;
import org.springframework.richclient.application.support.DesktopPageDescriptor;
import org.springframework.util.Assert;

/**
 * 
 * @author Claudio Romano - cro
 *
 */
public class FlexPageDescriptor extends DesktopPageDescriptor {
    
    private FlexConfigurationManager configurationManager;

	public void buildInitialLayout(PageLayoutBuilder layout) {  
	    Assert.notNull( configurationManager);
        setViewDescriptors(configurationManager.getViewList());
	    
	    super.buildInitialLayout( layout);
    }
    /**
     * @return Returns the manager.
     */
    public FlexConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
    /**
     * @param manager The manager to set.
     */
    public void setConfigurationManager(FlexConfigurationManager manager) {
        this.configurationManager = manager;
    }
}
