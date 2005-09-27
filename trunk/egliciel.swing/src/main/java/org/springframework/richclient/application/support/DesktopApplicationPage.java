package org.springframework.richclient.application.support;

import java.beans.PropertyVetoException;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.PageComponentDescriptor;
import org.springframework.richclient.application.PageComponentPane;
import org.springframework.richclient.application.PageDescriptor;
import org.springframework.richclient.application.PageLayoutBuilder;
import org.springframework.richclient.control.ScrollingDesktopPane;

/**
 * @author Peter De Bruycker
 */
public class DesktopApplicationPage extends AbstractApplicationPage implements PageLayoutBuilder {

    private ScrollingDesktopPane control;

    public DesktopApplicationPage(ApplicationWindow window, PageDescriptor pageDescriptor) {
        super(window, pageDescriptor);
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.application.support.AbstractApplicationPage#giveFocusTo(org.springframework.richclient.application.PageComponent)
     */
    protected boolean giveFocusTo(PageComponent pageComponent) {
        PageComponentPane pane = pageComponent.getContext().getPane();
        JInternalFrame internalFrame = (JInternalFrame) pane.getControl();
        
//        control.moveToFront(internalFrame);
        try {
            internalFrame.setSelected(true);
        }
        catch (PropertyVetoException e) {
            // ignore
        }
        
        pane.requestFocusInWindow();
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.application.support.AbstractApplicationPage#createPageComponent(org.springframework.richclient.application.PageComponentDescriptor)
     */
    protected PageComponent createPageComponent(PageComponentDescriptor descriptor) {
        final PageComponent pageComponent = descriptor.createPageComponent();
        pageComponent.setContext(new DefaultViewContext(this, new DesktopPageComponentPane(this, pageComponent)));
        
        JInternalFrame internalFrame = (JInternalFrame) pageComponent.getContext().getPane().getControl();
        internalFrame.setVisible(true);
        control.add(internalFrame);
        
        return pageComponent;        
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.factory.ControlFactory#getControl()
     */
    public JComponent getControl() {
        if(control == null) {
            control = new ScrollingDesktopPane();
            scrollPane = new JScrollPane(control);
            
            this.getPageDescriptor().buildInitialLayout(this);
            setActiveComponent();
        }
        return scrollPane;
    }
    
    private JScrollPane scrollPane;

    /* (non-Javadoc)
     * @see org.springframework.richclient.application.PageLayoutBuilder#addView(java.lang.String)
     */
    public void addView(String viewDescriptorId) {
        showView(viewDescriptorId);
    }
}
