/**
 * 
 */
package malitech.egliciel.ui;

import org.springframework.richclient.application.support.AbstractView;

import javax.swing.*;
import java.awt.*;

public class MainNavigatorView extends AbstractView
{
    protected JComponent createControl()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Ici, on mettra l'arbre de naviguation"));
        return panel;
    }
}
