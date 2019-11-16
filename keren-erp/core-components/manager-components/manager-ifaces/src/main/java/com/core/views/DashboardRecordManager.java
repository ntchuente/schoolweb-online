
package com.core.views;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Wed Jan 24 08:06:02 WAT 2018
 * 
 */
public interface DashboardRecordManager
    extends GenericManager<DashboardRecord, Long>
{

    public final static String SERVICE_NAME = "DashboardRecordManager";

}
