
package com.core.menus;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Tue Nov 21 22:36:56 WAT 2017
 * 
 */
public interface MenuGroupActionsDAO
    extends GenericDAO<MenuGroupActions, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "MenuGroupActionsDAO";

}
