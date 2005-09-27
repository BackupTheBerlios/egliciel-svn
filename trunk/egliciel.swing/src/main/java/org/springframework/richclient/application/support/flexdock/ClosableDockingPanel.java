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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;

import org.flexdock.docking.Dockable;
import org.flexdock.docking.DockableAdapter;
import org.flexdock.docking.DockingManager;
import org.flexdock.docking.DockingPort;
import org.flexdock.docking.defaults.DefaultDockingPort;
import org.flexdock.docking.windows.DockingWindow;

/**
 * 
 * @author Claudio Romano - cro
 *
 */
public class ClosableDockingPanel extends DockingWindow {
    // constants


    private Dockable dockable;

    private DockingPort dockingPort;

    private String id;

    // constructor
    public ClosableDockingPanel(String id, String title, Icon icon, Component comp) {
        super(title, icon, comp);
        this.id = id;
        dockable = new DockableImpl();
    }


    public void dockPanel() {
        super.dockPanel();

        dockingPort.dock(getDockable(), DockingPort.CENTER_REGION);

        ((DefaultDockingPort) dockingPort).validate();
        ((DefaultDockingPort) dockingPort).repaint();

    }

    public void closePanel() {
        this.setDocked(false);
        dockingPort.undock(this);
        
        ((DefaultDockingPort) dockingPort).validate();
        ((DefaultDockingPort) dockingPort).repaint();
        
    }

    // private
    private ClosableDockingPanel getThis() {
        return this;
    }

    public Dockable getDockable() {
        return dockable;
    }

    private class DockableImpl extends DockableAdapter {

        public DockableImpl() {
            DockingManager.registerDockable(this);
        }

        public String getPersistentId() {
            return id;
        }

        public Component getDockable() {
            return getThis();
        }

        public String getDockableDesc() {
            return getTitle().trim();
        }

        public Icon getIcon() {
            return mHeader.getIcon();
        }

        public Component getInitiator() {
            return mHeader; // the titlebar will the the 'hot' component that
            // initiates dragging
        }

        public boolean isDockingEnabled() {
            return !isFloating();
        }

        public void dockingCompleted(DockingPort port) {
            dockingPort = port;
        }
    }

    /**
     * @return Returns the dockingPort.
     */
    public DockingPort getDockingPort() {
        return dockingPort;
    }

    /**
     * @param dockingPort
     *            The dockingPort to set.
     */
    public void setDockingPort(DockingPort dockingPort) {
        this.dockingPort = dockingPort;
    }

    protected void setSelected(boolean selected) {
        boolean oldValue = mSelected;
        super.setSelected( selected);
        
        
        this.firePropertyChange("selected", oldValue, selected);
        super.setSelected( selected);
    }

    protected void setupHeader() {
        this.mHeader.addButton("close", CLOSE_ICON, "close").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closePanel();
            }
        });
    }
}