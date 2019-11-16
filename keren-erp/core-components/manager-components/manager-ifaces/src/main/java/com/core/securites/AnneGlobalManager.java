
package com.core.securites;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Mon May 27 17:42:51 WAT 2019
 * 
 */
public interface AnneGlobalManager
    extends GenericManager<AnneGlobal, Long>
{

    public final static String SERVICE_NAME = "AnneGlobalManager";

}
