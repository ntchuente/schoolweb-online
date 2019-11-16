
package com.core.discussions;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.securites.Utilisateur;
import com.core.securites.UtilisateurManagerRemote;
import com.kerem.core.MetaDataUtil;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.HttpHeaders;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Sat Jan 27 08:28:43 GMT+01:00 2018
 * 
 */
@Path("/canal")
public class CanalRSImpl
    extends AbstractGenericService<Canal, Long>
    implements CanalRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "CanalManagerImpl", interf = CanalManagerRemote.class)
    protected CanalManagerRemote manager;
    
    @Manager(application = "kerencore", name = "UtilisateurManagerImpl", interf = UtilisateurManagerRemote.class)
    protected UtilisateurManagerRemote usermanager;
    

    public CanalRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Canal, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(HttpHeaders headers) {
        try {
            return MetaDataUtil.getMetaData(new Canal(), new HashMap<String, MetaData>(),new ArrayList<String>()); //To change body of generated methods, choose Tools | Templates.
        } catch (InstantiationException ex) {
            Logger.getLogger(CanalRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CanalRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }  

    @Override
    public List<Canal> getCanaux(String username) {
        //To change body of generated methods, choose Tools | Templates.
        return manager.getCanaux(username);
    }

    @Override
    public List<Utilisateur> getConnectUsers(String username) {
         //To change body of generated methods, choose Tools | Templates.        
        return manager.getConnectUsers(username);
    }

}
