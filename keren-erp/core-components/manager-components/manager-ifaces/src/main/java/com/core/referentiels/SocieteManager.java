
package com.core.referentiels;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Sat Nov 11 07:16:13 WAT 2017
 * 
 */
public interface SocieteManager
    extends GenericManager<Societe, Long>
{

    public final static String SERVICE_NAME = "SocieteManager";

}
