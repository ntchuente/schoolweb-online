
package com.core.importexport;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Wed Jul 04 15:20:26 GMT+01:00 2018
 * 
 */
public interface ExportManager
    extends GenericManager<Export, Long>
{

    public final static String SERVICE_NAME = "ExportManager";
    
    /**
     * 
     * @param entity
     * @param day
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InterruptedException 
     */
    public void exportFileManager(Export entity,Date day) throws FileNotFoundException, IOException, InterruptedException;
    
     /**
     * Permet de definir la frequence de traitement des 
     * evenements 
     * @param initialExpiration
     * @param duration
     */
    public void scheduleDBExporterManager(Date initialExpiration , long duration);

}
