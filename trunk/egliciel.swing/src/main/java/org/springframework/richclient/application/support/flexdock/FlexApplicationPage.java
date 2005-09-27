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


import javax.swing.JComponent;

import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.PageComponentDescriptor;
import org.springframework.richclient.application.PageComponentPane;
import org.springframework.richclient.application.PageDescriptor;
import org.springframework.richclient.application.support.DefaultApplicationPage;
import org.springframework.richclient.application.support.DefaultViewContext;
import org.springframework.richclient.application.support.FlexComponentPane;

import com.jgoodies.forms.factories.Borders;

/**
 * @author Claudio Romano - cro
 * 
 */
public class FlexApplicationPage extends DefaultApplicationPage {

    private FlexDockingPort mainDockingPort;

    public FlexApplicationPage(ApplicationWindow window, PageDescriptor pageDescriptor) {
        super(window, pageDescriptor);
    }

    protected PageComponent createPageComponent(PageComponentDescriptor descriptor) {
        PageComponent pageComponent = descriptor.createPageComponent();
        pageComponent.setContext(new DefaultViewContext(this, new FlexComponentPane(pageComponent, this)));
        return pageComponent;
    }

    public JComponent getControl() {
        if (mainDockingPort == null) {
            mainDockingPort = new FlexDockingPort("mainDockingPort");

            super.getControl().add(mainDockingPort);
            super.getControl().setBorder(Borders.DLU4_BORDER);
        }

        return super.getControl();
    }

    protected boolean giveFocusTo(PageComponent pageComponent) {
        PageComponentPane pane = pageComponent.getContext().getPane();

        mainDockingPort.validate();
        mainDockingPort.repaint();
        pane.requestFocusInWindow();

        fireFocusGained(pageComponent);

        return true;
    }

    //  Initial Application Page Layout Builder methods
    public void addView(String viewDescriptorId) {
        showView(viewDescriptorId);
    }


    protected void setActiveComponent(PageComponent pageComponent) {
        //dock if was the pageComponent was never added or closed
        if ((pageComponent.getControl().getParent() == null) ||
                (pageComponent.getContext().getPane().getControl() instanceof ClosableDockingPanel &&
                pageComponent.getContext().getPane().getControl().getParent() == null)) {
            mainDockingPort.dock(pageComponent.getContext().getPane().getControl());
        }
 
        //check if it's alredy the active component
        if (this.getActiveComponent() != pageComponent) {
            super.setActiveComponent(pageComponent);
        }
    }
}