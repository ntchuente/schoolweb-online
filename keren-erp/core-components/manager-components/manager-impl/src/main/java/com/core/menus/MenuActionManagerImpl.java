
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
@Stateless(mappedName = "MenuActionManager")
public class MenuActionManagerImpl
    extends AbstractGenericManager<MenuAction, Long>
    implements MenuActionManagerLocal, MenuActionManagerRemote
{

    @EJB(name = "MenuActionDAO")
    protected MenuActionDAOLocal dao;

    public MenuActionManagerImpl() {
    }

    @Override
    public GenericDAO<MenuAction, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }

    @Override
    public List<MenuAction> filter(List<Predicat> predicats, Map<String, OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        //To change body of generated methods, choose Tools | Templates.
        List<MenuAction> datas =  super.filter(predicats, orders, properties, firstResult, maxResult); 
        List<MenuAction> result = new ArrayList<MenuAction>();
        if(datas!=null){
            for(MenuAction item: datas){
                MenuAction act = new MenuAction(item);
                if(item.getActions()!=null){
                    for(ActionItem data : item.getActions()){
                        ActionItem it = new ActionItem(data);
                        act.getActions().add(it);
                    }
                }
                if(item.getMenu()!=null){
                    act.setMenu(new MenuGroupActions(item.getMenu()));
                    result.add(act);
                }
            }
        }
        return result;
    }

    @Override
    public List<MenuAction> findAll() {
        //To change body of generated methods, choose Tools | Templates.
        List<MenuAction> datas =  super.findAll(); 
        List<MenuAction> result = new ArrayList<MenuAction>();
        if(datas!=null){
            for(MenuAction item: datas){
                MenuAction act = new MenuAction(item);
                //Cas des actions
                if(item.getActions()!=null){
                    for(ActionItem data : item.getActions()){
                        ActionItem it = new ActionItem(data);
                        act.getActions().add(it);
                    }
                }//end if(item.getActions()!=null){
                //cas des rapports
                if(item.getRecords()!=null){
                    for(ReportRecord rec:item.getRecords()){
                        ReportRecord recor = new ReportRecord(rec);
                        act.getRecords().add(recor);
                    }//end for(ReportRecord rec:item.getRecords())
                }//end if(item.getRecords()!=null){
                if(item.getMenu()!=null){
                    act.setMenu(new MenuGroupActions(item.getMenu()));
                    result.add(act);
                }                
            }
        }
        return result;
    }

    @Override
    public MenuAction find(String propertyName, Long entityID) {
        //To change body of generated methods, choose Tools | Templates.
        MenuAction item = super.find(propertyName, entityID); 
        if(item!=null){
            MenuAction act = new MenuAction(item);
            if(item.getModule()!=null){
                act.setModule(new MenuModule(item.getModule()));
            }
            if(item.getMenu()!=null){
                act.setMenu(new MenuGroupActions(item.getMenu()));
            }
            if(item.getActions()!=null){
                for(ActionItem data : item.getActions()){
                    ActionItem it = new ActionItem(data);
                    act.getActions().add(it);
                }
            }
            //cas des rapports
            if(item.getRecords()!=null){
                for(ReportRecord rec:item.getRecords()){
                    ReportRecord recor = new ReportRecord(rec);
                    act.getRecords().add(recor);
                }//end for(ReportRecord rec:item.getRecords())
            }//end if(item.getRecords()!=null){
           return act ;
        }
        return null;
    }

    @Override
    public MenuAction delete(Long id) {
        MenuAction item =  super.delete(id); //To change body of generated methods, choose Tools | Templates.
        MenuAction act = new MenuAction(item);
        if(item.getMenu()!=null){
            act.setMenu(new MenuGroupActions(item.getMenu()));
        }
        return act ;
    }

    @Override
    public List<MenuAction> findByUniqueProperty(String propertyName, Object propertyValue, Set<String> properties) {
        //To change body of generated methods, choose Tools | Templates.
        List<MenuAction> datas =  super.findByUniqueProperty(propertyName, propertyValue, properties); 
        List<MenuAction> result = new ArrayList<MenuAction>();
        if(datas!=null){
            for(MenuAction item: datas){
                MenuAction act = new MenuAction(item);
                if(item.getMenu()!=null){
                    act.setMenu(new MenuGroupActions(item.getMenu()));
                    result.add(act);
                }
                //cas des rapports
                if(item.getRecords()!=null){
                    for(ReportRecord rec:item.getRecords()){
                        ReportRecord recor = new ReportRecord(rec);
                        act.getRecords().add(recor);
                    }//end for(ReportRecord rec:item.getRecords())
                }//end if(item.getRecords()!=null){
            }
            
        }
        return result;
    }
    
    

}
