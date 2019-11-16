
package com.core.discussions;

import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.securites.UtilisateurManagerRemote;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import java.util.List;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Tue Jan 30 08:25:11 GMT+01:00 2018
 * 
 */
@Path("/kmessage")
public class KMessageRSImpl
    extends AbstractGenericService<KMessage, Long>
    implements KMessageRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "KMessageManagerImpl", interf = KMessageManagerRemote.class)
    protected KMessageManagerRemote manager;
    
     @Manager(application = "kerencore", name = "UtilisateurManagerImpl", interf = UtilisateurManagerRemote.class)
    protected UtilisateurManagerRemote usermanager;
     
     @Manager(application = "kerencore", name = "CanalManagerImpl", interf = CanalManagerRemote.class)
    protected CanalManagerRemote canalmanager;
     

    public KMessageRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<KMessage, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    /**
     * 
     * @param userid
     * @param canalid
     * @param firstResult
     * @param maxResult
     * @return 
     */
    @Override
    public List<KMessage> getmessages(long userid,long canalid, int firstResult, int maxResult) {
       //To change body of generated methods, choose Tools | Templates.
       return manager.getmessages(userid, canalid, firstResult, maxResult);
    }

    @Override
    public List<KMessage> getUsermessages(long userid, long canalid, int firstResult, int maxResult) {
        //To change body of generated methods, choose Tools | Templates.
       return manager.getUsermessages(userid, canalid, firstResult, maxResult);
    }

    @Override
    public long countCanalMsge(long userid, long canalid) {
        //To change body of generated methods, choose Tools | Templates.
        return manager.countcanal(userid, canalid);
    }

    @Override
    public long countDirectMsge(long userid, long connectuserid) {
        //To change body of generated methods, choose Tools | Templates.
        return manager.countdirect(userid, connectuserid);
    }

   
   

}
