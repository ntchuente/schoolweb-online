
package com.core.securites;

import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.menus.MenuModule;
import java.util.List;


/**
 * Interface etendue par les interfaces locale et remote du manager

 * @since Tue Nov 14 21:34:39 WAT 2017
 * 
 */
public interface UtilisateurManager
    extends GenericManager<Utilisateur, Long>
{

    public final static String SERVICE_NAME = "UtilisateurManager";
    
    /**
     * Chargement des modules pour cette utilisateur
     * @param user
     * @return 
     */
    public List<MenuModule> loadUserApplications(Utilisateur user);
}
