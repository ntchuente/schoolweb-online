
package com.core.views;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Tue Nov 21 21:56:28 WAT 2017
 * 
 */
public interface TreeRecordManager
    extends GenericManager<TreeRecord, Long>
{

    public final static String SERVICE_NAME = "TreeRecordManager";

}
