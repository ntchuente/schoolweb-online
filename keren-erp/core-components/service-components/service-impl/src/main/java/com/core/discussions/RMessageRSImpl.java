
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.securites.Utilisateur;
import com.core.securites.UtilisateurManagerRemote;
import com.megatim.common.annotations.OrderType;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Mon Jan 29 12:08:25 GMT+01:00 2018
 * 
 */
@Path("/rmessage")
public class RMessageRSImpl
    extends AbstractGenericService<RMessage, Long>
    implements RMessageRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "RMessageManagerImpl", interf = RMessageManagerRemote.class)
    protected RMessageManagerRemote manager;
    
     @Manager(application = "kerencore", name = "UtilisateurManagerImpl", interf = UtilisateurManagerRemote.class)
    protected UtilisateurManagerRemote usermanager;
     
     @Manager(application = "kerencore", name = "CanalManagerImpl", interf = CanalManagerRemote.class)
    protected CanalManagerRemote canalmanager;

    public RMessageRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<RMessage, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

   
    
    
    @Override
    public MessageState getUnReadMessages(long userid) {
        //To change body of generated methods, choose Tools | Templates.
        MessageState msge = new MessageState();
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        Utilisateur user = usermanager.find("id", userid);
        container.addIsFalse("status");
        container.addIsNull("sender", null);
        container.addEq("reciever", user);
        msge.setUnreadMessages(manager.count(container.getPredicats()));
        user.setLastconfirme(new Date());
        usermanager.update(userid, user);
        return msge;
    }

     @Override
    public MessageState getUnReadMessages(String username) {
        //To change body of generated methods, choose Tools | Templates.
        MessageState msge = new MessageState();
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        Utilisateur user = usermanager.findByUniqueProperty("courriel", username,new HashSet<String>()).get(0);
        container.addIsFalse("status");
        container.addIsNull("sender", null);
        container.addEq("reciever", user);
        msge.setUnreadMessages(manager.count(container.getPredicats()));
        user.setLastconfirme(new Date());
        usermanager.update(user.getId(), user);
        return msge;
    }

   
    @Override
    public List<RMessage> getmessages(long userid, int firstResult, int maxResult) {
       //To change body of generated methods, choose Tools | Templates.
        Utilisateur user = usermanager.find("id", userid);
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("reciever", user);
        container.addIsNull("sender", null);
        Map<String , OrderType> parametres = new HashMap<String, OrderType>();
        parametres.put("date", OrderType.DESC);
        List<RMessage> messages = manager.filter(container.getPredicats(), parametres, new HashSet<String>(), firstResult, maxResult);        
        return messages;
    }

    @Override
    public List<RMessage> marqueMessage(long messageid, long userid, int index, int max) {
        //To change body of generated methods, choose Tools | Templates.
        RMessage message = manager.find("id", messageid);        
        if(message!=null){
            message.setStatus(true);
            manager.update(message.getId(), message);
        }//end if(message!=null)
        return getmessages(userid, index, max);
    }    

    @Override
    public long getInboxCount(long userid) {
        //To change body of generated methods, choose Tools | Templates.
         Utilisateur user = usermanager.find("id", userid);
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("reciever", user);
        container.addIsNull("sender", null);
        return manager.count(container.getPredicats());
    }
}
