
package com.core.views;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Mon Dec 18 20:36:03 WAT 2017
 * 
 */
public interface ReportRecordManager
    extends GenericManager<ReportRecord, Long>
{

    public final static String SERVICE_NAME = "ReportRecordManager";

}
