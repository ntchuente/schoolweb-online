
package com.core.discussions;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Mon Jan 29 12:08:24 GMT+01:00 2018
 * 
 */
public interface RMessageManager
    extends GenericManager<RMessage, Long>
{

    public final static String SERVICE_NAME = "RMessageManager";

}
