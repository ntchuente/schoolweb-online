
package com.core.langues;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Tue May 08 12:34:37 GMT+01:00 2018
 * 
 */
public interface TermeManager
    extends GenericManager<Terme, Long>
{

    public final static String SERVICE_NAME = "TermeManager";

}
