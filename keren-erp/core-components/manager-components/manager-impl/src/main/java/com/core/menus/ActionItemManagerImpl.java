
package com.core.menus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.views.ReportRecord;
import com.megatim.common.annotations.OrderType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "ActionItemManager")
public class ActionItemManagerImpl
    extends AbstractGenericManager<ActionItem, Long>
    implements ActionItemManagerLocal, ActionItemManagerRemote
{

    @EJB(name = "ActionItemDAO")
    protected ActionItemDAOLocal dao;

    public ActionItemManagerImpl() {
    }

    @Override
    public GenericDAO<ActionItem, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<ActionItem> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        List<ActionItem> datas = super.filter(predicats, orders, properties, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
        List<ActionItem> result = new ArrayList<ActionItem>();
        for(ActionItem item : datas){
            result.add(new ActionItem(item));
        }            
        return result;
    }

    @Override
    public List<ActionItem> findAll() {
        //To change body of generated methods, choose Tools | Templates.
        List<ActionItem> datas = super.findAll(); 
        List<ActionItem> result = new ArrayList<ActionItem>();
        for(ActionItem item : datas){
            result.add(new ActionItem(item));
        }            
        return result;
    }

    @Override
    public ActionItem find(String propertyName, Long entityID) {
        //To change body of generated methods, choose Tools | Templates.
        ActionItem item = super.find(propertyName, entityID);
        ActionItem result = new ActionItem(item);
        if(item.getFormView()!=null){
            result.setFormView(item.getFormView());
        }
        if(item.getTreeView()!=null){
            result.setTreeView(item.getTreeView());
        }
        if(item.getDashboard()!=null){
            result.setDashboard(item.getDashboard());
        }
        if(item.getCalendar()!=null){
            result.setCalendar(item.getCalendar());
        }
        if(item.getRecords()!=null){
            for(ReportRecord rec:item.getRecords()){
                result.getRecords().add(new ReportRecord(rec));
            }
        }//end if(item.getRecords()!=null)
        return result;
    }
    
    

}
