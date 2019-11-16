
package com.core.discussions;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.securites.Utilisateur;
import com.megatim.common.annotations.OrderType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Query;

@TransactionAttribute
@Stateless(mappedName = "KMessageManager")
public class KMessageManagerImpl
    extends AbstractGenericManager<KMessage, Long>
    implements KMessageManagerLocal, KMessageManagerRemote
{

    @EJB(name = "KMessageDAO")
    protected KMessageDAOLocal dao;

    public KMessageManagerImpl() {
    }

    @Override
    public GenericDAO<KMessage, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

     
    @Override
    public List<KMessage> findAll() {       
        List<KMessage> datas = super.findAll(); //To change body of generated methods, choose Tools | Templates.
        List<KMessage> output = new ArrayList<KMessage>();
        for(KMessage msg:datas){
            KMessage rmsg = new KMessage(msg);
            output.add(rmsg);
        }
        return output;
    }

    @Override
    public KMessage find(String propertyName, Long id) {
        KMessage data = super.find(propertyName, id); //To change body of generated methods, choose Tools | Templates.
        return new KMessage(data);
    }

    @Override
    public List<KMessage> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        //To change body of generated methods, choose Tools | Templates.
        List<KMessage> datas = super.filter(predicats, orders, properties, firstResult, maxResult);
        List<KMessage> output = new ArrayList<KMessage>();
        for(KMessage msg:datas){
            KMessage rmsg = new KMessage(msg);
            output.add(rmsg);
        }
        return output;
    }

    

    @Override
    public KMessage delete(Long id) {
        KMessage data = super.delete(id); //To change body of generated methods, choose Tools | Templates.
        return new KMessage(data);
    }

    @Override
    public List<KMessage> getmessages(long userid, long canalid, int firstResult, int maxResult) {
        String requete = "SELECT  DISTINCT  c FROM KMessage c WHERE  ((c.sender.id="+userid+" AND c.canal.id="+canalid+" AND c.typeMessage=0) OR (c.reciever.id="+userid+" AND "+"c.canal.id = "+canalid+"  AND c.typeMessage=1)) ORDER BY c.date DESC";
         List<KMessage> results = new ArrayList<KMessage>();// = 
        Query query = dao.getEntityManager().createQuery(requete);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        results = query.getResultList();
        List<KMessage> output = new ArrayList<KMessage>();
        for(KMessage msge:results){
            output.add(new KMessage(msge));
        }
        return output;
    }

    @Override
    public List<KMessage> getUsermessages(long userid, long canalid, int firstResult, int maxResult) {    
        String requete = "SELECT DISTINCT c FROM KMessage c WHERE ((c.reciever.id="+canalid+" AND c.sender.id="+userid+" AND c.typeMessage=0) OR (c.sender.id="+canalid+" AND c.reciever.id="+userid+" AND c.typeMessage=1))AND c.canal IS NULL ORDER BY c.date DESC ";
        List<KMessage> results = new ArrayList<KMessage>();// = 
        Query query = dao.getEntityManager().createQuery(requete);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        results = query.getResultList();
        List<KMessage> output = new ArrayList<KMessage>();
        for(KMessage msge:results){
            output.add(new KMessage(msge));
        }
        return output;  
    }

    @Override
    public long countcanal(long userid, long canalid) {
        //To change body of generated methods, choose Tools | Templates.
         String requete = "SELECT COUNT(c) FROM KMessage c WHERE  ((c.sender.id="+userid+" AND c.canal.id="+canalid+" AND c.typeMessage=0) OR (c.reciever.id="+userid+" AND "+"c.canal.id = "+canalid+"  AND c.typeMessage=1))";
        Query query = dao.getEntityManager().createQuery(requete);
        return (long) query.getSingleResult();        
    }

    @Override
    public long countdirect(long userid, long canalid) {
        //To change body of generated methods, choose Tools | Templates.       
        String requete = "SELECT COUNT(c) FROM KMessage c WHERE ((c.reciever.id="+canalid+" AND c.sender.id="+userid+" AND c.typeMessage=0) OR (c.sender.id="+canalid+" AND c.reciever.id="+userid+" AND c.typeMessage=1))AND c.canal IS NULL ";
        Query query = dao.getEntityManager().createQuery(requete);        
        return (long) query.getSingleResult();  
    }

}
