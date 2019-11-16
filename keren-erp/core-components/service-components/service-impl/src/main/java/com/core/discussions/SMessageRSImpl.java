
package com.core.discussions;

import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import javax.ws.rs.Path;
import com.bekosoftware.genericmanagerlayer.core.ifaces.GenericManager;
import com.core.securites.Utilisateur;
import com.core.securites.UtilisateurManagerRemote;
import com.megatim.common.annotations.OrderType;
import com.megatimgroup.generic.jax.rs.layer.annot.Manager;
import com.megatimgroup.generic.jax.rs.layer.impl.AbstractGenericService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * Classe d'implementation du Web Service JAX-RS

 * @since Mon Jan 29 12:08:25 GMT+01:00 2018
 * 
 */
@Path("/smessage")
public class SMessageRSImpl
    extends AbstractGenericService<SMessage, Long>
    implements SMessageRS
{

    /**
     * On injecte un Gestionnaire d'entites
     * 
     */
    @Manager(application = "kerencore", name = "SMessageManagerImpl", interf = SMessageManagerRemote.class)
    protected SMessageManagerRemote manager;
    
     @Manager(application = "kerencore", name = "UtilisateurManagerImpl", interf = UtilisateurManagerRemote.class)
    protected UtilisateurManagerRemote usermanager;
     
     @Manager(application = "kerencore", name = "RMessageManagerImpl", interf = RMessageManagerRemote.class)
    protected RMessageManagerRemote rmsgemanager;
     

    public SMessageRSImpl() {
        super();
    }

    /**
     * Methode permettant de retourner le gestionnaire d'entites
     * 
     */
    @Override
    public GenericManager<SMessage, Long> getManager() {
        return manager;
    }

    public String getModuleName() {
        return ("kerencore");
    }

    @Override
    public List<SMessage> getmessages(long userid, int firstResult, int maxResult) {
        //To change body of generated methods, choose Tools | Templates.
        Utilisateur user = usermanager.find("id", userid);
        List<SMessage> results = new ArrayList<SMessage>();// = 
        if(user!=null){
            RestrictionsContainer container = RestrictionsContainer.newInstance();
            container.addEq("sender", user);
            Map<String,OrderType> parametres = new HashMap<String, OrderType>();
            parametres.put("date", OrderType.DESC);
            results = manager.filter(container.getPredicats(), parametres, new HashSet<String>(), firstResult, maxResult);            
        }//end if(user!=null)
        return results;
    }

    @Override
    public List<RMessage> send(long userid, SMessage message) {
        //To change body of generated methods, choose Tools | Templates.
        manager.save(message);
        return manager.send(userid, message);
//        Utilisateur user = usermanager.find("id", userid);
//        List<RMessage> results = new ArrayList<RMessage>();
//        if(user!=null){
//            message.setSender(user);
//            if(message.getCanal()!=null&&message.getCanal().getMembres()!=null){
//                for(Utilisateur mem : message.getCanal().getMembres()){
//                    if(!mem.equals(user)){//exclusion de l'emeteur du message
//                        RMessage rmess = new RMessage(message);
//                        rmess.setSender(user);
//                        rmess.setReciever(mem);
//                        rmsgemanager.save(rmess);
//                        results.add(rmess);
//                    }//end if(!mem.equals(user))
//                }//end for(Utilisateur mem : message.getCanal().getMembres())
//            }//end if(message.getCanal()!=null)
//            if(message.getReciever()!=null){
//                RMessage rmess = new RMessage(message);
//                rmess.setReciever(message.getReciever());
//                rmess.setSender(user);
//                rmsgemanager.save(rmess);
//                results.add(rmess);
//            }//end if(message.getReciever()!=null)
//            manager.save(message);
//           try {
//                //Traitement des pieces jointes
//                if(message.getPiecesjointe()!=null){
//                    for(PieceJointe pj:message.getPiecesjointe()){
//
//                            String sourceName = FileHelper.getTemporalDirectory()+File.separator+pj.getFilename();
//                            String ciblename = FileHelper.getStaticDirectory()+File.separator+pj.getFilename();
//                            FileHelper.moveFile(new File(sourceName), new File(ciblename));                  
//                    }
//                }//end if(message.getPiecesjointe()!=null)
//           } catch (IOException ex) {
//                throw new WebApplicationException(Response.serverError().build());
//            }
//        }
//        return results;
    }

   
    
}
