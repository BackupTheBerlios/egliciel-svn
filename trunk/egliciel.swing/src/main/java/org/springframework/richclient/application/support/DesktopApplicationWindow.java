package org.springframework.richclient.application.support;

import org.springframework.richclient.application.ApplicationPage;
import org.springframework.richclient.application.PageDescriptor;

public class DesktopApplicationWindow extends DefaultApplicationWindow {

    /*
     * (non-Javadoc)
     * @see org.springframework.richclient.application.support.DefaultApplicationWindow#createPage(org.springframework.richclient.application.PageDescriptor)
     */
    protected ApplicationPage createPage(PageDescriptor descriptor) {
        return new DesktopApplicationPage(this, descriptor);
    }
}