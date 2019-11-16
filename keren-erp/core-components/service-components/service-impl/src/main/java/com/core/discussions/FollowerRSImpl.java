
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
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

 * @since Wed Jan 31 16:04:28 GMT+01:00 2018
 * 
 */
@Path("/follower")
public class FollowerRSImpl
    extends AbstractGenericService<Follower, Long>
    implements FollowerRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "FollowerManagerImpl", interf = FollowerManagerRemote.class)
    protected FollowerManagerRemote manager;

    public FollowerRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<Follower, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public MetaData getMetaData(HttpHeaders headers) {
        try {
            return MetaDataUtil.getMetaData(new Follower(), new HashMap<String, MetaData>(),new ArrayList<String>());
        } catch (InstantiationException ex) {
            Logger.getLogger(FollowerRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FollowerRSImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Follower getFollower(String serial, long entityid) {
        //To change body of generated methods, choose Tools | Templates.
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("entityserial", serial);
        container.addEq("entityid", entityid);
        List<Follower> datas = manager.filter(container.getPredicats(), null, null, 0, -1);
        if(datas.isEmpty()){
            return null;
        }
        return datas.get(0);
    }

    /**
     * Traitement du follower
     * @param userid
     * @param follower
     * @return 
     */
    @Override
    public Follower computeFollower(long userid , Follower follower) {
        //To change body of generated methods, choose Tools | Templates.
        //traitement des messages
        return manager.computeFollower(userid, follower);
    }
    
    

}
