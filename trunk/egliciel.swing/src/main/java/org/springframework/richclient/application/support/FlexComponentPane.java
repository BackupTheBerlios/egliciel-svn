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


//package org.springframework.richclient.command.support;
package org.springframework.richclient.application.support;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.PageComponentPane;
import org.springframework.richclient.application.support.AbstractApplicationPage;
import org.springframework.richclient.application.support.flexdock.ClosableDockingPanel;

/**
 * @author Claudio Romano - cro
 * 
 */
public class FlexComponentPane extends PageComponentPane {

    private AbstractApplicationPage applicationPage;

    public FlexComponentPane(PageComponent component, AbstractApplicationPage applicationPage) {
        super(component);
        this.applicationPage = applicationPage;
    }

    
    protected JComponent createControl() {
        ClosableDockingPanel closableDockPanel = new ClosableDockingPanel(getPageComponent().getId(), getPageComponent().getDisplayName(), getPageComponent().getIcon(), getPageComponent().getControl());
        closableDockPanel.addPropertyChangeListener("selected", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if( evt.getNewValue().equals(new Boolean(true)))
                    applicationPage.fireFocusGained(getPageComponent());
                else 
                    applicationPage.fireFocusLost(getPageComponent());
            }
        });
        
        return closableDockPanel;
    }

    
    /**
     * ?
     * 
     */
    protected void handleViewPropertyChange() {
        ClosableDockingPanel frame = (ClosableDockingPanel) getControl();
        frame.setTitle(getPageComponent().getDisplayName());
        frame.setIcon(getPageComponent().getIcon());
        frame.setToolTipText(getPageComponent().getCaption());
    }
}