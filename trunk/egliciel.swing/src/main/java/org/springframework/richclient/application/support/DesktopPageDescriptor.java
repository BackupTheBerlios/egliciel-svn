package org.springframework.richclient.application.support;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;

import org.springframework.richclient.application.PageDescriptor;
import org.springframework.richclient.application.PageLayoutBuilder;

public class DesktopPageDescriptor implements PageDescriptor {

    private List viewDescriptors = new ArrayList();

    private String id;

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.application.PageDescriptor#getId()
     */
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.application.PageDescriptor#buildInitialLayout(org.springframework.richclient.application.PageLayoutBuilder)
     */
    public void buildInitialLayout(PageLayoutBuilder pageLayout) {
        for (Iterator iter = viewDescriptors.iterator(); iter.hasNext();) {
            String viewDescriptorId = (String) iter.next();
            pageLayout.addView(viewDescriptorId);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.core.DescribedElement#getDisplayName()
     */
    public String getDisplayName() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.core.DescribedElement#getCaption()
     */
    public String getCaption() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.core.DescribedElement#getDescription()
     */
    public String getDescription() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.core.VisualizedElement#getImage()
     */
    public Image getImage() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.core.VisualizedElement#getIcon()
     */
    public Icon getIcon() {
        return null;
    }

    public List getViewDescriptors() {
        return viewDescriptors;
    }

    public void setViewDescriptors(List viewDescriptors) {
        this.viewDescriptors = viewDescriptors;
    }

    public void setId(String id) {
        this.id = id;
    }
}