
package com.core.langues;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Sat Nov 11 21:58:35 WAT 2017
 * 
 */
public interface LangueManager
    extends GenericManager<Langue, Long>
{

    public final static String SERVICE_NAME = "LangueManager";

}
