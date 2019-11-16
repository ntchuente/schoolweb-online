
package com.core.importexport;

import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;


/**
 * Interface etendue par les interfaces locale et remote de la DAO
 * @since Wed Jul 04 15:20:25 GMT+01:00 2018
 * 
 */
public interface ExportDAO
    extends GenericDAO<Export, Long>
{

    /**
     * Nom du service
     * 
     */
    public final static String SERVICE_NAME = "ExportDAO";

}
