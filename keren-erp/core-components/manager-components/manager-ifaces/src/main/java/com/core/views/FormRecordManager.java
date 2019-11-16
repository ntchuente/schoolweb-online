
package com.core.views;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;


/**
 * Interface etendue par les interfaces locale et remote du manager
 * @since Tue Nov 21 21:56:28 WAT 2017
 * 
 */
public interface FormRecordManager
    extends GenericManager<FormRecord, Long>
{

    public final static String SERVICE_NAME = "FormRecordManager";

}
