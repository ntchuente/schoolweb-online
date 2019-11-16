
package com.core.menus;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Sun Dec 17 10:38:04 WAT 2017
 * 
 */
public interface ActionItemManager
    extends GenericManager<ActionItem, Long>
{

    public final static String SERVICE_NAME = "ActionItemManager";

}
