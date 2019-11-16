
package com.core.menus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.Predicat;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@TransactionAttribute
@Stateless(mappedName = "MenuGroupActionsManager")
public class MenuGroupActionsManagerImpl
    extends AbstractGenericManager<MenuGroupActions, Long>
    implements MenuGroupActionsManagerLocal, MenuGroupActionsManagerRemote
{

    @EJB(name = "MenuGroupActionsDAO")
    protected MenuGroupActionsDAOLocal dao;

    public MenuGroupActionsManagerImpl() {
    }

    @Override
    public GenericDAO<MenuGroupActions, Long> getDao() {
        return dao;
    }

    @Override
    public String getEntityIdName() {
        return "id";
    }
    
    /**
     * 
     * @param propertyName
     * @param id
     * @return 
     */
    @Override
    public MenuGroupActions find(String propertyName, Long id) {
        MenuGroupActions grp = super.find(propertyName, id); //To change body of generated methods, choose Tools | Templates.
        if(grp!=null){
            MenuGroupActions result = new MenuGroupActions(grp);
            if(grp.getModule()!=null){
                result.setModule(new MenuModule(grp.getModule()));
            }
            return result;
        }
        return null;
    }

    @Override
    public List<MenuGroupActions> findAll() {
         //To change body of generated methods, choose Tools | Templates.
        List<MenuGroupActions> datas = super.findAll(); //To change body of generated methods, choose Tools | Templates.
        List<MenuGroupActions> results = new ArrayList<MenuGroupActions>();
        if(datas!=null){
            for(MenuGroupActions mod : datas){
               MenuGroupActions grp = new MenuGroupActions(mod);
                 if(mod.getModule()!=null){
                     grp.setModule(new MenuModule(mod.getModule()));
                 }
                results.add(grp);
            }
        }
        return results;
    }

    @Override
    public List<MenuGroupActions> filter(List<Predicat> predicats, Map<String, com.megatim.common.annotations.OrderType> orders, Set<String> properties, int firstResult, int maxResult) {
        List<MenuGroupActions> datas =  super.filter(predicats, orders, properties, firstResult, maxResult); //To change body of generated methods, choose Tools | Templates.
         List<MenuGroupActions> results = new ArrayList<MenuGroupActions>();
        if(datas!=null){
            for(MenuGroupActions mod : datas){
                MenuGroupActions grp = new MenuGroupActions(mod);
                 if(mod.getModule()!=null){
                     grp.setModule(new MenuModule(mod.getModule()));
                 }
                results.add(grp);
            }
        }
        return results;
    }

    @Override
    public MenuGroupActions delete(Long id) {
        MenuGroupActions group = super.delete(id); //To change body of generated methods, choose Tools | Templates.
        
        return new MenuGroupActions(group);
    }
 
    
}
