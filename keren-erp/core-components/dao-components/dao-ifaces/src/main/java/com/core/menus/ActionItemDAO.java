
package com.core.menus;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Sun Dec 17 10:38:04 WAT 2017
 * 
 */
public interface ActionItemDAO
    extends GenericDAO<ActionItem, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "ActionItemDAO";

}
