
package com.core.discussions;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.securites.Utilisateur;
import com.core.securites.UtilisateurDAOLocal;
import com.megatim.common.annotations.OrderType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@TransactionAttribute
@Stateless(mappedName = "FollowerManager")
public class FollowerManagerImpl
    extends AbstractGenericManager<Follower, Long>
    implements FollowerManagerLocal, FollowerManagerRemote
{

    @EJB(name = "FollowerDAO")
    protected FollowerDAOLocal dao;
    
    @EJB(name = "UtilisateurDAO")
    protected UtilisateurDAOLocal userdao;
    
    @EJB(name = "SMessageDAO")
    protected SMessageDAOLocal smsgedao;
      

    public FollowerManagerImpl() {
    }

    @Override
    public GenericDAO<Follower, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<Follower> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        List<Follower> datas = super.filter(predicats, orders, properties, firstResult, maxResult); 
        List<Follower> result = new ArrayList<Follower>();
        for(Follower data:datas){
            result.add(new Follower(data));
        }
        return result;
    }

    @Override
    public List<Follower> findAll() {
        //To change body of generated methods, choose Tools | Templates.
        List<Follower> datas = super.findAll(); 
        List<Follower> result = new ArrayList<Follower>();
        for(Follower data:datas){
            result.add(new Follower(data));
        }
        return result;
    }

    @Override
    public Follower find(String propertyName, Long entityID) {
        Follower data = super.find(propertyName, entityID); //To change body of generated methods, choose Tools | Templates.
        return new Follower(data);
    }

    @Override
    public Follower computeFollower(long userid, Follower follower) {
        //To change body of generated methods, choose Tools | Templates.
        Utilisateur user = userdao.findByPrimaryKey("id", userid);
        if(user==null){
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED).header("error_key", "Impossible de trouvé l'utilisateur avec le ID:"+userid).build());
        }
        if(follower.getEntityid()>0 && follower.getEntityserial()!=null&&!follower.getEntityserial().trim().isEmpty()){
            for(SMessage msge:follower.getMessages()){
                msge.setSender(new Utilisateur(user));
                if(follower.isNoteinterne()){
                    msge.setReciever(new Utilisateur(user));
                    msge.setDate(new Date());
                }else{
                    if(msge.getId()<0){
                        msge.setCanaux(follower.getCanaux());
                        msge.setRecievers(follower.getAbonnes());
                         msge.setDate(new Date());
                        smsgedao.send(userid, msge);
                    }//end if(msge.getId()<0)
                }//end if(msge.getSender()!=null&&msge.getReciever()!=null&&msge.getSender().equals(msge.getReciever())){
            }//end for(SMessage msge:follower.getMessages()){
            if(follower.getId()<=0){
                dao.save(follower);
            }else{
                dao.update(follower.getId(), follower);
            }//end if(follower.getId()<=0){
            dao.getEntityManager().flush();
            //Chargement du follower
           RestrictionsContainer container = RestrictionsContainer.newInstance();
           container.addEq("entityid", follower.getEntityid());
           container.addEq("entityserial", follower.getEntityserial());
           List<Follower> datas =  dao.filter(container.getPredicats(), null, null, 0, -1);
//           System.out.println("Hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh ===== "+datas.size()+" === "+datas);
           return new Follower(datas.get(0));
        }else{
            throw new WebApplicationException(Response.status(Response.Status.PRECONDITION_FAILED).header("error_key", "Numéro de serie ou id de l'entité introuvable").build());
        }//end if(follower.getEntityid()>0 && follower.getEntityserial()!=null&&!follower.getEntityserial().trim().isEmpty())
    }

    
}
