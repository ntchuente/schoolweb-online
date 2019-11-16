
package com.core.discussions;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.securites.UtilisateurDAOLocal;
import com.megatim.common.annotations.OrderType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "SMessageManager")
public class SMessageManagerImpl
    extends AbstractGenericManager<SMessage, Long>
    implements SMessageManagerLocal, SMessageManagerRemote
{

    @EJB(name = "SMessageDAO")
    protected SMessageDAOLocal dao;
    
    @EJB(name = "UtilisateurDAO")
    protected UtilisateurDAOLocal userdao;
    
     @EJB(name = "RMessageDAO")
    protected RMessageDAOLocal rmessagedao;
     

    public SMessageManagerImpl() {
    }

    @Override
    public GenericDAO<SMessage, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }
    
     @Override
    public List<SMessage> findAll() {       
        List<SMessage> datas = super.findAll(); //To change body of generated methods, choose Tools | Templates.
        List<SMessage> output = new ArrayList<SMessage>();
        for(SMessage msg:datas){
            SMessage rmsg = new SMessage(msg);
            output.add(rmsg);
        }
        return output;
    }

    @Override
    public SMessage find(String propertyName, Long id) {
        SMessage data = super.find(propertyName, id); //To change body of generated methods, choose Tools | Templates.
        return new SMessage(data);
    }

    @Override
    public List<SMessage> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        //To change body of generated methods, choose Tools | Templates.
        List<SMessage> datas = super.filter(predicats, orders, properties, firstResult, maxResult);
        List<SMessage> output = new ArrayList<SMessage>();
        for(SMessage msg:datas){
            SMessage rmsg = new SMessage(msg);
            output.add(rmsg);
        }
        return output;
    }

    

    @Override
    public SMessage delete(Long id) {
        SMessage data = super.delete(id); //To change body of generated methods, choose Tools | Templates.
        return new SMessage(data);
    }

    @Override
    public List<RMessage> send(long userid, SMessage message) {
        //To change body of generated methods, choose Tools | Templates.
        return dao.send(userid, message);
    }


}
