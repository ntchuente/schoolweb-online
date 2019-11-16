
package com.core.calendar;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Sun Nov 19 14:21:00 WAT 2017
 * 
 */
public interface RappelManager
    extends GenericManager<Rappel, Long>
{

    public final static String SERVICE_NAME = "RappelManager";

}
