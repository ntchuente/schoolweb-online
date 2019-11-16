
package com.core.views;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Sat Feb 17 07:50:36 GMT+01:00 2018
 * 
 */
public interface CalendarRecordManager
    extends GenericManager<CalendarRecord, Long>
{

    public final static String SERVICE_NAME = "CalendarRecordManager";

}
