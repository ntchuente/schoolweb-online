
package com.core.discussions;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.megatim.common.annotations.OrderType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "RMessageManager")
public class RMessageManagerImpl
    extends AbstractGenericManager<RMessage, Long>
    implements RMessageManagerLocal, RMessageManagerRemote
{

    @EJB(name = "RMessageDAO")
    protected RMessageDAOLocal dao;

    public RMessageManagerImpl() {
    }

    @Override
    public GenericDAO<RMessage, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }
    
    
    @Override
    public List<RMessage> findAll() {       
        List<RMessage> datas = super.findAll(); //To change body of generated methods, choose Tools | Templates.
        List<RMessage> output = new ArrayList<RMessage>();
        for(RMessage msg:datas){
            RMessage rmsg = new RMessage(msg);
            output.add(rmsg);
        }
        return output;
    }

    @Override
    public RMessage find(String propertyName, Long id) {
        RMessage data = super.find(propertyName, id); //To change body of generated methods, choose Tools | Templates.
        return new RMessage(data);
    }

    @Override
    public List<RMessage> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        //To change body of generated methods, choose Tools | Templates.
        List<RMessage> datas = super.filter(predicats, orders, properties, firstResult, maxResult);
        List<RMessage> output = new ArrayList<RMessage>();
        for(RMessage msg:datas){
            RMessage rmsg = new RMessage(msg);
            output.add(rmsg);
//            System.out.println(RMessageManagerImpl.class.toString()+" ==== "+msg.getPiecesjointe());
        }
        return output;
    }

    

    @Override
    public RMessage delete(Long id) {
        RMessage data = super.delete(id); //To change body of generated methods, choose Tools | Templates.
        return new RMessage(data);
    }

    
}
