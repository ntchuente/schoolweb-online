
package com.core.referentiels;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import java.util.List;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Wed Jan 10 21:34:02 WAT 2018
 * 
 */
public interface PieceJointeManager
    extends GenericManager<PieceJointe, Long>
{

    public final static String SERVICE_NAME = "PieceJointeManager";
    
    public List<PieceJointe> getPiecesJointe(String serial, long identifiant);

}
