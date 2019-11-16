
package com.core.menus;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Sat Nov 11 06:50:36 WAT 2017
 * 
 */
public interface MenuActionManager
    extends GenericManager<MenuAction, Long>
{

    public final static String SERVICE_NAME = "MenuActionManager";

}
