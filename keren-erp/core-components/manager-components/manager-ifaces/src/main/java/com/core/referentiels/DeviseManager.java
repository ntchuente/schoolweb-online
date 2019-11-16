
package com.core.referentiels;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Tue Nov 14 13:20:52 WAT 2017
 * 
 */
public interface DeviseManager
    extends GenericManager<Devise, Long>
{

    public final static String SERVICE_NAME = "DeviseManager";

}
