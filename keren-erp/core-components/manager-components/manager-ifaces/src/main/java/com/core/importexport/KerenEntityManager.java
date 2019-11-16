
package com.core.importexport;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.importexport.KerenEntity;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Mon Apr 30 13:23:43 GMT+01:00 2018
 * 
 */
public interface KerenEntityManager
    extends GenericManager<KerenEntity, Long>
{

    public final static String SERVICE_NAME = "KerenEntityManager";

}
