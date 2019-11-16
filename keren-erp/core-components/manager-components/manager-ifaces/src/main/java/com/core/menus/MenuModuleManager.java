
package com.core.menus;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Tue Nov 21 22:36:56 WAT 2017
 * 
 */
public interface MenuModuleManager
    extends GenericManager<MenuModule, Long>
{

    public final static String SERVICE_NAME = "MenuModuleManager";
    
    /**
     * Installation d'un module
     * @param module  
     * @throws java.lang.Exception 
     */
    public void installApplication(MenuModule module) throws Exception;
    
    /**
     * Désinstallation d'un module
     * @param module 
     * @throws java.lang.Exception 
     */
    public void uninstallApplication(MenuModule module) throws Exception;

}
