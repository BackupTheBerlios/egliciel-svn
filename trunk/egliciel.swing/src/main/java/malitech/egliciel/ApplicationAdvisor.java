/**
 * 
 */
package malitech.egliciel;

import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.config.DefaultApplicationLifecycleAdvisor;
import org.springframework.richclient.application.support.flexdock.FlexConfigurationManager;

public class ApplicationAdvisor extends DefaultApplicationLifecycleAdvisor
{
    public void onWindowCreated(ApplicationWindow window) {
        inizializeFlexConfiguration();
        super.onWindowCreated(window);
      }
         
      public boolean onPreWindowClose(ApplicationWindow window) {
        saveFlexConfiguration();
        return super.onPreWindowClose( window);
      }
         

      private void saveFlexConfiguration() {
        if (getApplicationServices().containsBean("flexdockConfigurationManager")) {
          FlexConfigurationManager manager = (FlexConfigurationManager)getApplicationServices().getBean("flexdockConfigurationManager");
          manager.saveConfiguration();
        }
      }

      private void inizializeFlexConfiguration() {
        if (getApplicationServices().containsBean("flexdockConfigurationManager")) {
          FlexConfigurationManager manager = (FlexConfigurationManager) getApplicationServices().getBean("flexdockConfigurationManager");
          manager.applyConfiguration();
        }       
      } 
}
