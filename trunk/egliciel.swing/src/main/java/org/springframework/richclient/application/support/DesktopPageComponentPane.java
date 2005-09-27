package org.springframework.richclient.application.support;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.PageComponentPane;

public class DesktopPageComponentPane extends PageComponentPane {

    private final PageComponent pageComponent;
    private final DesktopApplicationPage applicationPage;
    private JInternalFrame internalFrame;

    public DesktopPageComponentPane(DesktopApplicationPage applicationPage, PageComponent component) {
        super(component);
        this.applicationPage = applicationPage;
        pageComponent = component;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.richclient.application.PageComponentPane#createControl()
     */
    protected JComponent createControl() {
        internalFrame = new JInternalFrame(pageComponent.getDisplayName());
        if (pageComponent.getIcon() != null) {
            internalFrame.setFrameIcon(pageComponent.getIcon());
        }
        internalFrame.setResizable(true);
        internalFrame.setMaximizable(true);
        internalFrame.setIconifiable(true);
        internalFrame.setClosable(true);
        internalFrame.addInternalFrameListener(new InternalFrameAdapter() {
            /* (non-Javadoc)
             * @see javax.swing.event.InternalFrameAdapter#internalFrameActivated(javax.swing.event.InternalFrameEvent)
             */
            public void internalFrameActivated(InternalFrameEvent arg0) {
                applicationPage.fireFocusGained(pageComponent);
            }
            
            /* (non-Javadoc)
             * @see javax.swing.event.InternalFrameAdapter#internalFrameDeactivated(javax.swing.event.InternalFrameEvent)
             */
            public void internalFrameDeactivated(InternalFrameEvent arg0) {
                applicationPage.fireFocusLost(pageComponent);
            }
            public void internalFrameClosed(InternalFrameEvent arg0) {
                applicationPage.close(pageComponent);
            }
        });
        
        internalFrame.getContentPane().add(pageComponent.getControl());
        internalFrame.pack();
        return internalFrame;
    }
}
