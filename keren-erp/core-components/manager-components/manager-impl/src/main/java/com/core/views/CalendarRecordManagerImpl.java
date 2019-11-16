
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
@Stateless(mappedName = "CalendarRecordManager")
public class CalendarRecordManagerImpl
    extends AbstractGenericManager<CalendarRecord, Long>
    implements CalendarRecordManagerLocal, CalendarRecordManagerRemote
{

    @EJB(name = "CalendarRecordDAO")
    protected CalendarRecordDAOLocal dao;

    public CalendarRecordManagerImpl() {
    }

    @Override
    public GenericDAO<CalendarRecord, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<CalendarRecord> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        List<CalendarRecord> datas = super.filter(predicats, orders, properties, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
        List<CalendarRecord> result = new ArrayList<CalendarRecord>();
        for(CalendarRecord rec:datas){
            result.add(new CalendarRecord(rec));
        }
        return result;
    }

    @Override
    public List<CalendarRecord> findAll() {        
        List<CalendarRecord> datas = super.findAll(); //To change body of generated methods, choose Tools | Templates.
        List<CalendarRecord> result = new ArrayList<CalendarRecord>();
        for(CalendarRecord rec:datas){
            result.add(new CalendarRecord(rec));
        }
        return result;
    }

    @Override
    public CalendarRecord find(String propertyName, Long entityID) {
        CalendarRecord data = super.find(propertyName, entityID); //To change body of generated methods, choose Tools | Templates.
       if(data.getScript()!=null){
            data.getScript().length();
        }
        if(data.getAction()!=null){
            data.setAction(new MenuAction(data.getAction()));            
        }
        return data;
    }
    
    

    @Override
    public CalendarRecord delete(Long id) {
        CalendarRecord data = super.delete(id); //To change body of generated methods, choose Tools | Templates.
        return new CalendarRecord(data);
    }
    
    

}
