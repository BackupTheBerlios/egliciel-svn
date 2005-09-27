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

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.flexdock.demos.elegant.ChildComponentDelegate;
import org.flexdock.docking.DockingPort;

import com.jgoodies.looks.Options;

/**
 * 
 * @author Claudio Romano - cro
 *
 */
public class FlexComponentDelegate extends ChildComponentDelegate {
    
    /**
     * Creates a FlexDockingPort instead of the default
     * 
     */
    public DockingPort createChildPort() {
		FlexDockingPort port = new FlexDockingPort();
		port.setComponentProvider(this);
		return port;
	}
    
    /**
     * Embedded tabs
     * 
     */
    public JTabbedPane createTabbedPane() {
        JTabbedPane tpane = new JTabbedPane(JTabbedPane.BOTTOM);
        tpane.putClientProperty(Options.EMBEDDED_TABS_KEY, Boolean.TRUE);
        tpane.updateUI();
        
        return tpane;
	}
    
    /**
     * Bigger Divider
     * 
     */
    public JSplitPane createSplitPane() {
        JSplitPane sp = super.createSplitPane();
        sp.setDividerSize( 5);
        return sp;
    }
}
