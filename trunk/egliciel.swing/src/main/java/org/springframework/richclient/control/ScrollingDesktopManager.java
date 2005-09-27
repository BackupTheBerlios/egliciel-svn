/*
 * Created on 1-dec-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.springframework.richclient.control;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;


/**
 * Private class used to replace the standard DesktopManager for JDesktopPane. Used to provide scrollbar
 * functionality.
 */
public class ScrollingDesktopManager extends DefaultDesktopManager {
    private ScrollingDesktopPane desktopPane;

    public ScrollingDesktopManager(ScrollingDesktopPane pane) {
        this.desktopPane = pane;
    }

    public void endResizingFrame(JComponent f) {
        super.endResizingFrame(f);
        resizeDesktop();
    }

    public void endDraggingFrame(JComponent f) {
        super.endDraggingFrame(f);
        resizeDesktop();
    }

    public void setNormalSize() {
        JScrollPane scrollPane = getScrollPane();

        if (scrollPane != null) {
            int x = 0;
            int y = 0;
            Insets scrollInsets = getInsets(scrollPane);
            Dimension d = scrollPane.getVisibleRect().getSize();
            if (scrollPane.getBorder() != null) {
                d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top
                        - scrollInsets.bottom);
            }

            d.setSize(d.getWidth() - 20, d.getHeight() - 20);
            this.desktopPane.setAllSize(x, y);
            scrollPane.invalidate();
            scrollPane.validate();
        }
    }

    private Insets getInsets(JScrollPane scrollPane) {
        if (scrollPane == null)
            return new Insets(0, 0, 0, 0);
        else
            return scrollPane.getBorder().getBorderInsets(scrollPane);
    }

    private JScrollPane getScrollPane() {
        if (desktopPane.getParent() instanceof JViewport) {
            JViewport viewPort = (JViewport) desktopPane.getParent();
            if (viewPort.getParent() instanceof JScrollPane)
                return (JScrollPane) viewPort.getParent();
        }
        return null;
    }

    void resizeDesktop() {
        JScrollPane scrollPane = getScrollPane();

        if (scrollPane != null) {
            int x = 0;
            int y = 0;
            Insets scrollInsets = getInsets(scrollPane);
            JInternalFrame allFrames[] = desktopPane.getAllFrames();
            for (int i = 0; i < allFrames.length; i++) {
                if (allFrames[i].getX() + allFrames[i].getWidth() > x) {
                    x = allFrames[i].getX() + allFrames[i].getWidth();
                }
                if (allFrames[i].getY() + allFrames[i].getHeight() > y) {
                    y = allFrames[i].getY() + allFrames[i].getHeight();
                }
            }
            Dimension d = scrollPane.getVisibleRect().getSize();
            if (scrollPane.getBorder() != null) {
                d.setSize(d.getWidth() - scrollInsets.left - scrollInsets.right, d.getHeight() - scrollInsets.top
                        - scrollInsets.bottom);
            }

            if (x <= d.getWidth())
                x = ((int) d.getWidth()) - 20;
            if (y <= d.getHeight())
                y = ((int) d.getHeight()) - 20;
            this.desktopPane.setAllSize(x, y);
            scrollPane.invalidate();
            scrollPane.validate();
        }
    }
}