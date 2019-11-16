
package com.core.menus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import com.bekosoftware.genericdaolayer.dao.ifaces.GenericDAO;
import com.bekosoftware.genericdaolayer.dao.tools.RestrictionsContainer;
import com.bekosoftware.genericmanagerlayer.core.impl.AbstractGenericManager;
import com.core.application.Manifest;
import com.core.securites.Groupe;
import com.core.securites.GroupeDAOLocal;
import com.core.views.CalendarRecordDAOLocal;
import com.core.views.DashboardRecord;
import com.core.views.DashboardRecordDAOLocal;
import com.core.views.FormRecordDAOLocal;
import com.core.views.ReportRecordDAOLocal;
import com.core.views.TreeRecordDAOLocal;
import com.kerem.core.CommonTools;
import com.kerem.core.FileHelper;
import com.kerem.genarated.CalendarRecord;
import com.kerem.genarated.FormRecord;
import com.kerem.genarated.Keren;
import com.kerem.genarated.Menu;
import com.kerem.genarated.Menuitem;
import com.kerem.genarated.ReportRecord;
import com.kerem.genarated.TreeRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

@TransactionAttribute
@Stateless(mappedName = "MenuModuleManager")
public class MenuModuleManagerImpl
    extends AbstractGenericManager<MenuModule, Long>
    implements MenuModuleManagerLocal, MenuModuleManagerRemote
{

    @EJB(name = "MenuModuleDAO")
    protected MenuModuleDAOLocal dao;
    
    @EJB(name = "MenuGroupActionsDAO")
    protected MenuGroupActionsDAOLocal menudao;
    
    @EJB(name = "MenuActionDAO")
    protected MenuActionDAOLocal menuitemdao;
    
    @EJB(name = "GroupeDAO")
    protected GroupeDAOLocal groupedao;    
    
    @EJB(name = "FormRecordDAO")
    protected FormRecordDAOLocal formdao;
    
    @EJB(name = "TreeRecordDAO")
    protected TreeRecordDAOLocal treedao;
    
    @EJB(name = "ReportRecordDAO")
    protected ReportRecordDAOLocal reportdao;
    
    @EJB(name = "DashboardRecordDAO")
    protected DashboardRecordDAOLocal dashboarddao;
    
     @EJB(name = "CalendarRecordDAO")
    protected CalendarRecordDAOLocal calendardao;
    
    public MenuModuleManagerImpl() {
    }

    @Override
    public GenericDAO<MenuModule, Long> getDao() {
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
    public MenuModule find(String propertyName, Long id) {
        MenuModule mod =  super.find(propertyName, id); //To change body of generated methods, choose Tools | Templates.
        if(mod!=null){
            return new MenuModule(mod);
        }
        return null ;
    }

    /**
     * 
     * @return 
     */
    @Override
    public List<MenuModule> findAll() {
         //To change body of generated methods, choose Tools | Templates.
        List<MenuModule> datas= super.findAll(); //To change body of generated methods, choose Tools | Templates.
        List<MenuModule> results = new ArrayList<MenuModule>();
        if(datas!=null){
            for(MenuModule mod : datas){
                if(mod.isApplication()){
                    results.add(new MenuModule(mod));
                }
            }
        }
        return results;
    }

    @Override
    public MenuModule delete(Long id) {
        MenuModule module = dao.findByPrimaryKey("id", id);
        if(module==null) return null;
        deleteGroupes(module);
        deleteMenuGroupAction(module);
        module = super.delete(id); //To change body of generated methods, choose Tools | Templates.        
        MenuModule mod = new MenuModule(module);
        return mod;
    }

    @Override
    public void installApplication(MenuModule module) throws Exception{
        //To change body of generated methods, choose Tools | Templates.             
            //To change body of generated methods, choose Tools | Templates.
            if(module==null) return ;   
            //Verifier si le module a deja fait l'object d'une installation
             if(module.isActive()) return ;
            //Lecture du fichier manifest liée
             Manifest manifest = FileHelper.getManifest(module);
             //On arrete si le fichier manifest est introuvable
             if(manifest==null) return ;
             //Traitement des dependances
             if(manifest.getDepends().length>0){
                 for(String name : manifest.getDepends()){
                     if(name.trim().isEmpty()) continue;
                     MenuModule mod = dao.findByPrimaryKey("name", name);
                     if(mod==null){
                         //Emettre une exception pour notifier l'utilisateur
                         return ;
                     }//end if(mod==null)
                     //Si le module n'est pas installer alors install
                     if(!mod.isActive()){
                         installApplication(mod);
                     }//end if(!mod.isActive())
                 }//end for(String name : manifest.getDepends())
             }//end if(manifest.getDepends().length>0)
            //Chargement des Vues
            List<Keren> views = FileHelper.getViews(module);
            //Creation ou mise ajour de la vue
            applicationMenusBulder(module, views);
            //Mise a jour du module
            module.setActive(true);
            dao.update(module.getId(), module);
            //Deplacement des artefacts javaee
            FileHelper.processCore(manifest);
            //Copies des templates reports
            FileHelper.processReporting(manifest);
            //Copies des images 
            FileHelper.processImages(manifest);
    }    

    /**
     * 
     * @param module 
     */
    @Override
    public void uninstallApplication(MenuModule module) {
        //To change body of generated methods, choose Tools | Templates.
        if(module==null||!module.isActive()) return ;
        //Le module est installer
        if(!module.isHasmenu()){
            RestrictionsContainer container = RestrictionsContainer.newInstance();
            container.addEq("module.id", module.getId());
            List<MenuAction> menus = menuitemdao.filter(container.getPredicats(), null, new HashSet<String>(), 0, -1);
            if(menus==null) return ;
            deleteMenuAction(menus);
        }else{//Module avec menu
            deleteGroupes(module);
            deleteMenuGroupAction(module);
        }
        //Mise a jour du status du module
        module.setActive(false);
        dao.update(module.getId(), module);
    }
    
    /**
     * Suppression des groupes
     * @param module 
     */
    private void deleteGroupes(MenuModule module){
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("module.id", module.getId());
        List<Groupe> datas = groupedao.filter(container.getPredicats(), null, new HashSet<String>(), 0, -1);
        
        if(datas==null) return ;
        
        //Suppresion des groupes chez les utilisateur liées
        
        for(Groupe data:datas){
            groupedao.delete(data.getId());
        }
    }
    
    /**
     * Suppression du mene
     * @param module 
     */
    private void deleteMenuGroupAction(MenuModule module){
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("module.id", module.getId());
        List<MenuGroupActions> datas = menudao.filter(container.getPredicats(), null,new HashSet<String>(), 0, -1);
        if(datas==null) return ;
        for(MenuGroupActions data : datas){
            container = RestrictionsContainer.newInstance();
            container.addEq("menu.id", data.getId());
            List<MenuAction> menus = menuitemdao.filter(container.getPredicats(), null,new HashSet<String>(), 0, -1);
            if(menus==null) continue;
            deleteMenuAction(menus);
            menudao.delete(data.getId());
        }//end for(MenuGroupActions data : datas){
    }
    /**
     * Suppression des actions
     * @param actions 
     */
    private void deleteMenuAction(List<MenuAction> actions){
        if(actions==null) return ;        
        for(MenuAction act:actions){
            act = menuitemdao.findByPrimaryKey("id", act.getId());
            if(act.getFormView()!=null){
               formdao.delete(act.getFormView().getId());
            }//end if(act.getFormView()!=null)
            if(act.getTreeView()!=null){
                treedao.delete(act.getTreeView().getId());
            }//end if(act.getTreeView()!=null)
            if(act.getRecords()!=null){
                for(com.core.views.ReportRecord rep:act.getRecords()){
                    reportdao.delete(rep.getId());
                }//end for(com.core.views.ReportRecord rep:act.getRecords())
            }//end if(act.getRecords()!=null)
            if(act.getDashboard()!=null){
                dashboarddao.delete(act.getDashboard().getId());               
            }//end if(act.getDashboard()!=null){*
            if(act.getCalendar()!=null){
                calendardao.delete(act.getCalendar().getId());               
            }//end if(act.getDashboard()!=null){
            menuitemdao.delete(act.getId());
        }
    }
    
     /**
      * 
      * @param module
      * @param views
      * @throws JAXBException
      * @throws SAXException
      * @throws IOException 
      */
    private void applicationMenusBulder(MenuModule module , List<Keren> views) throws JAXBException, IOException, ParserConfigurationException, SAXException, TransformerException {        
       
        for(Keren data : views){
            //Mise a jour de l'action Parente
            if(data.getAction()==null) continue ;
            //Initialisation du module
            module.setHasmenu(data.getAction().isHasmenu());
            if(!data.getAction().isHasmenu()){//Traitement des modules sans menu vertical
                //Module sans menu dans ce cas 
            }else{//cas des modules avec menu verticale
                if(data.getMenu()!=null){
                    //Traitement des Menu
                    for(Menu item : data.getMenu()){
                        MenuGroupActions menu = CommonTools.getMenuGroupActions(item);
                        menu.setModule(module);
                        MenuGroupActions menu_old = menudao.findByPrimaryKey("name", menu.getName());
                        if(menu_old!=null){
                            menu.setId(menu_old.getId());
                            menudao.update(menu_old.getId(), menu);
                        }else{
                            menudao.save(menu);
                        }
                    }//end for(Menu item : data.getMenu()){
                }//end  if(data.getMenu()!=null){
                //Traitement des actions
                if(data.getMenuitem()!=null){
                    for(Menuitem item : data.getMenuitem()){
                        MenuAction menu = CommonTools.getMenuAction(item);
                        Map<String,Integer> map = new HashMap<>();
                        for(ActionItem it : menu.getActions()){
                            map.put(it.getName(), menu.getActions().indexOf(it));
                        }//end for(ActionItem it : menu.getActions())
                        MenuAction menu_old = menuitemdao.findByPrimaryKey("name", menu.getName());
                        if(!item.getParentRef().trim().isEmpty()){
                            MenuGroupActions parent = menudao.findByPrimaryKey("name", item.getParentRef());
                            //Verifier que le menu a un parent
                            if(parent==null) continue ;
                             menu.setMenu(parent);
                        }//end if(!item.getParentRef().trim().isEmpty())
                        if(menu_old!=null){
                            menu.setId(menu_old.getId());
                            for(ActionItem it:menu_old.getActions()){
                                if(map.containsKey(it.getName())){
                                    menu.getActions().set(map.get(it.getName()), it);
                                }//end if(map.containsKey(it.getName())){
                            }//end for(ActionItem it:menu_old.getActions())
                            menuitemdao.update(menu.getId(), menu);
                        }else{
                            menuitemdao.save(menu);
                        }//end if(menu_old!=null)
                        //Chargement du menu parent
                    }//end for(Menuitem item : data.getMenuitem()){
                }//end if(data.getMenuitem()!=null){
                //Traitement Views
                if(data.getFormRecord()!=null){
                    for(FormRecord view:data.getFormRecord()){
                        com.core.views.FormRecord record = CommonTools.getFormView(view);    
                        //System.out.println(MenuModuleManagerImpl.class.toString()+" == Form \n"+record.getScript());
                        MenuAction action = menuitemdao.findByPrimaryKey("name", view.getActionRef());
                        if(action==null) continue;
                        record.setAction(action);
                        //Verification de l'existancerec**
                        com.core.views.FormRecord  old_record = formdao.findByPrimaryKey("code", record.getCode());
                        if(old_record!=null){
                            record.setId(old_record.getId());
                            formdao.update(record.getId(), record);
                        }else{
                            formdao.save(record);
                        }
                     }
                }//end if(data.getFormRecord()!=null){
                //Traitement des Dashboard
                if(data.getDashboardRecord()!=null){
                    for(com.kerem.genarated.DashboardRecord view:data.getDashboardRecord()){
                        DashboardRecord record = CommonTools.getDashboard(view);
                        MenuAction action = menuitemdao.findByPrimaryKey("name", view.getActionRef());
                        if(action==null) continue;
                        record.setAction(action);
                        //Verification de l'existance d'un dashbord
                        DashboardRecord old = dashboarddao.findByPrimaryKey("code", record.getCode());
                        if(old!=null){
                            record.setId(old.getId());
                            dashboarddao.update(record.getId(), record);
                        }else{
                            dashboarddao.save(record);
                        }
                    }//for(DashboardRecord view:data.getDashboardRecord()){
                    
                }//end if(data.getDashboardRecord()!=null)
                if(data.getTreeRecord()!=null){
                    for(TreeRecord view:data.getTreeRecord()){
                        com.core.views.TreeRecord record = CommonTools.getFormView(view);
                        //System.out.println(MenuModuleManagerImpl.class.toString()+" == Tree \n"+record.getScript());
                        MenuAction action = menuitemdao.findByPrimaryKey("name", view.getActionRef());
                        if(action==null) continue;
                        record.setAction(action);
                        //Verification Existance de la vue
                        com.core.views.TreeRecord old_record = treedao.findByPrimaryKey("code", record.getCode());
                        if(old_record!=null){
                            record.setId(old_record.getId());
                            treedao.update(record.getId(), record);
                        }else{
                            treedao.save(record);
                        }
                    }
                }//end if(data.getTreeRecord()!=null)
                if(data.getCalendarRecord()!=null){
                    for(CalendarRecord view:data.getCalendarRecord()){
                        com.core.views.CalendarRecord record = CommonTools.getFormView(view);
                        //System.out.println(MenuModuleManagerImpl.class.toString()+" == Tree \n"+record.getScript());
                        MenuAction action = menuitemdao.findByPrimaryKey("name", view.getActionRef());
                        if(action==null) continue;
                        record.setAction(action);
                        //Verification Existance de la vue
                        com.core.views.CalendarRecord old_record = treedao.findByPrimaryKey("code", record.getCode());
                        if(old_record!=null){
                            record.setId(old_record.getId());
                            calendardao.update(record.getId(), record);
                        }else{
                            calendardao.save(record);
                        }
                    }
                }//end if(data.getCalendarRecord()!=null)
                if(data.getReportRecord()!=null){
                    for(ReportRecord view:data.getReportRecord()){
                        com.core.views.ReportRecord record = CommonTools.getReportView(view);
                        //System.out.println(MenuModuleManagerImpl.class.toString()+" ====================== "+view.getTemplate());
                        MenuAction action = menuitemdao.findByPrimaryKey("name", view.getActionRef());
                        if(action==null) continue;
                        record.setAction(action);
                        //Verification Existance de la vue
                        com.core.views.ReportRecord old_record = reportdao.findByPrimaryKey("code", record.getCode());
                        if(old_record!=null){
                            record.setId(old_record.getId());
                            reportdao.update(record.getId(), record);
                        }else{
                            reportdao.save(record);
                        }
                    }
                }//end if(data.getReportRecord()!=null)
            } //end  if(!data.getAction().isHasmenu()){
            
        }
    }

}
