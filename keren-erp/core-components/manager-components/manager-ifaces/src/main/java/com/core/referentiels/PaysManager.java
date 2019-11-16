
package com.core.referentiels;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Sat Nov 11 18:45:30 WAT 2017
 * 
 */
public interface PaysManager
    extends GenericManager<Pays, Long>
{

    public final static String SERVICE_NAME = "PaysManager";

}
