
package com.core.menus;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Sat Nov 11 06:50:36 WAT 2017
 * 
 */
public interface MenuActionDAO
    extends GenericDAO<MenuAction, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "MenuActionDAO";

}
