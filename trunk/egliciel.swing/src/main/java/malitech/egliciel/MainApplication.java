/**
 * 
 */
package malitech.egliciel;

import org.springframework.richclient.application.ApplicationLauncher;

/**
 * @author Ech
 * 
 */
public class MainApplication
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new ApplicationLauncher("/malitech/egliciel/context-splash.xml",
                "/malitech/egliciel/context-main.xml");
    }

}
