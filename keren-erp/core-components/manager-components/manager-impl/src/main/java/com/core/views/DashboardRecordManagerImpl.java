
package com.core.views;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.menus.MenuAction;
import com.megatim.common.annotations.OrderType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "DashboardRecordManager")
public class DashboardRecordManagerImpl
    extends AbstractGenericManager<DashboardRecord, Long>
    implements DashboardRecordManagerLocal, DashboardRecordManagerRemote
{

    @EJB(name = "DashboardRecordDAO")
    protected DashboardRecordDAOLocal dao;

    public DashboardRecordManagerImpl() {
    }

    @Override
    public GenericDAO<DashboardRecord, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<DashboardRecord> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        List<DashboardRecord> datas = super.filter(predicats, orders, properties, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
        List<DashboardRecord> result = new ArrayList<DashboardRecord>();
        for(DashboardRecord record:datas){
            result.add(new DashboardRecord(record));
        }
        return result;
    }

    @Override
    public List<DashboardRecord> findAll() {
        List<DashboardRecord> datas  = super.findAll(); //To change body of generated methods, choose Tools | Templates.
        List<DashboardRecord> result = new ArrayList<DashboardRecord>();
        for(DashboardRecord record:datas){
            result.add(new DashboardRecord(record));
        }
        return result;
    }

    @Override
    public DashboardRecord find(String propertyName, Long entityID) {
        DashboardRecord data = super.find(propertyName, entityID); 
        DashboardRecord result = new DashboardRecord(data);
        result.setAction(new MenuAction(data.getAction()));
        return result;
    }
    
    

}
