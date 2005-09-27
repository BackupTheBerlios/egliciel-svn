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
import java.awt.Container;
import java.awt.Window;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.flexdock.demos.elegant.ShadowBorder;
import org.flexdock.docking.DockingPort;
import org.flexdock.docking.defaults.DefaultDockingPort;
import org.flexdock.docking.defaults.StandardBorderManager;

/**
 * @author Claudio Romano - cro
 * 
 */

public class FlexDockingPort extends DefaultDockingPort {
    public FlexDockingPort() {
        this(null);
    }

    public FlexDockingPort(String id) {
        super(id);
        setComponentProvider(new FlexComponentDelegate());
        setBorderManager(new StandardBorderManager(new ShadowBorder()) {

            public void managePortTabbedChild(DockingPort port) {
                //owrwrite standart behavior
            }
        });

    }

    public boolean dock(Component view) {
        if (view instanceof ClosableDockingPanel) {
            return dock(((ClosableDockingPanel) view).getDockable(), CENTER_REGION);
        }

        return false;
    }

    public boolean dock(Component comp, String desc, String region, boolean resizable) {
        if (comp instanceof ClosableDockingPanel) {
            ((ClosableDockingPanel) comp).setDockingPort(this);
        }

        if (super.getDockedComponent() instanceof JSplitPane) {
            JSplitPane sp = (JSplitPane) super.getDockedComponent();
            if (sp.getRightComponent() instanceof FlexDockingPort) {
                return ((FlexDockingPort) sp.getRightComponent()).dock(comp, desc, region, resizable);
            }
        }

        return super.dock(comp, desc, region, resizable);
    }

    public void reevaluateContainerTree() {
        //Remember values before reevaluateContainerTree
        Container parent = getParent();
        Window ancestor = getWindowAncestor();

        super.reevaluateContainerTree();

        //this docking port is not more used anymore try to refresh old parent
        if (getDockedComponent() == null) {
            if (parent != null) {
                //try one up the tree
                if (!(parent instanceof DefaultDockingPort)) {
                    if (parent.getParent() instanceof DefaultDockingPort)
                        ((DefaultDockingPort) parent.getParent()).reevaluateContainerTree();
                    //try to refesh the hole frame
                    else if (parent instanceof JSplitPane && ancestor != null) {
                        ancestor.validate();
                        ancestor.repaint();
                    }
                }
            }
        } else {
            //set docking port to Floating panel childs
            if (getDockedComponent() instanceof ClosableDockingPanel) {
                ((ClosableDockingPanel) getDockedComponent()).setDockingPort(this);
            } else if (getDockedComponent() instanceof JTabbedPane && getDockedComponent().getParent() instanceof FlexDockingPort) {
                JTabbedPane tabPane = (JTabbedPane) getDockedComponent();
                int tabCount = tabPane.getTabCount();
                for (int i = 0; i < tabCount; i++) {
                    Component comp = tabPane.getComponentAt(i);
                    if (comp instanceof ClosableDockingPanel)
                        ((ClosableDockingPanel) comp).setDockingPort(((FlexDockingPort) getDockedComponent().getParent()));
                }
            }
        }
    }
}

