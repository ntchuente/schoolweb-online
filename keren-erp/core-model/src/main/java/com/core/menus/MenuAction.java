/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.menus;

import com.core.base.BaseElement;
import com.core.views.CalendarRecord;
import com.core.views.DashboardRecord;
import com.core.views.FormRecord;
import com.core.views.ReportRecord;
import com.core.views.TreeRecord;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "M_ACTION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ACTION",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("MENU_ACT")
@XmlRootElement
public class MenuAction extends BaseElement implements Serializable,Comparable<MenuAction>{
    
     @Column(nullable = false,unique = true)
     @Predicate(label = "IDENTIFIANT",optional = false,updatable = false,unique = true,search = true)
     protected String name ;
     
     @Predicate(label = "MODAL",search = true)
     protected Boolean modal = Boolean.FALSE;
     
     @Predicate(label = "NOM MENU ITEM",optional = false,search = true)
     protected String label ;     
     
     @Predicate(label = "ENTITE MANAGE",optional = false,search = true)
     protected String entityName ;
     
     @Predicate(label = "MODELE",optional = false,search = true)
     protected String model ;
     
      @Predicate(label = "METHOD",optional = true,search = true)
     protected String method ;
     
     @ManyToOne
     @JoinColumn(name = "AC_ID")
     @Predicate(label = "MENU PARENT" ,type = MenuGroupActions.class,search = true)
     protected MenuGroupActions menu ;     
     
     @Predicate(label = "ICON ACTION")
     protected String icon ;
     
     @Predicate(label = "TYPE DE VUE",search = true)
     protected String viewMode ="tree,form";
     
     @Predicate(label = "Action liée(uniquement si modal = true)")
     protected String link = null;
     
     @Predicate(label = "Rapport liée")
     private String report = null;
     
     private Boolean hide = Boolean.FALSE;
     
     @OneToOne
     @JoinColumn(name = "MOD_ID")
     protected MenuModule module;
     
     @OneToOne(mappedBy = "action",fetch = FetchType.LAZY)
     protected FormRecord formView ;
     
     @OneToOne(mappedBy = "action" ,fetch = FetchType.LAZY)
     protected TreeRecord treeView ;
     
      @OneToOne(mappedBy = "action",fetch = FetchType.LAZY)
     protected DashboardRecord dashboard ;
     
     @OneToOne(mappedBy = "action",fetch = FetchType.LAZY)
     protected CalendarRecord calendar;
     
     @OneToMany(fetch = FetchType.LAZY,mappedBy = "action")
     protected List<ReportRecord> records = new ArrayList<ReportRecord>();
     
     //@Predicate(label = "ACTIONS",group = true,groupName = "group2",groupLabel = "ACTIONS",target = "one-to-many",type = ActionItem.class)
     @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
     @JoinColumn(name = "ACT_ID")
     private List<ActionItem> actions = new ArrayList<ActionItem>();
     /**
      * NIveau de securite
      * 0 = Tous les droits
      * 1=Lecture/Ecriture
      * 2=Lecture Seulement
      * 3= Aucnu
      */
     protected short securitylevel = 0;
     /**
      * 
      */
    public MenuAction() {
    }
    

    /**
     * 
     * @param menu 
     */
    public MenuAction(MenuAction menu) {
        super(menu.id, menu.designation, menu.moduleName,menu.compareid);
        this.name = menu.name;
        this.label = menu.label;
        this.entityName = menu.entityName;
        this.model = menu.model;
        //this.menu = menu;
        this.modal = menu.modal;
        this.icon = menu.icon;
        this.viewMode = menu.viewMode;
        this.method = menu.method;
        this.link = menu.link;
        this.report = menu.report;
        this.hide = menu.hide;
        if(menu.treeView!=null){
            this.treeView = new TreeRecord(menu.treeView);
        }
        if(menu.formView!=null){
            this.formView = new FormRecord(menu.formView);
        }
        if(menu.getRecords()!=null){
            for(ReportRecord rec:menu.getRecords()){
                records.add(new ReportRecord(rec));
            }
        }
        if(menu.getDashboard()!=null){
            this.dashboard = new DashboardRecord(menu.dashboard);
        }
        if(menu.getCalendar()!=null){
            this.calendar = new CalendarRecord(menu.calendar);
        }
    }
    /**
     * 
     * @param name
     * @param label
     * @param entityName
     * @param model
     * @param menu
     * @param icon
     * @param formView
     * @param treeView
     * @param id
     * @param designation
     * @param moduleName 
     */
    public MenuAction(String name, String label, String entityName, String model, MenuGroupActions menu, String icon, FormRecord formView, TreeRecord treeView, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.name = name;
        this.label = label;
        this.entityName = entityName;
        this.model = model;
        this.menu = menu;
        this.icon = icon;
        this.formView = formView;
        this.treeView = treeView;        
    }

    public List<ActionItem> getActions() {
        return actions;
    }

    public void setActions(List<ActionItem> actions) {
        this.actions = actions;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public CalendarRecord getCalendar() {
        return calendar;
    }

    public void setCalendar(CalendarRecord calendar) {
        this.calendar = calendar;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }
    
    

    @Override
    public String getListTitle() {
        return "ACTIONS"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "ACTION"; //To change body of generated methods, choose Tools | Templates.
    }

    public MenuGroupActions getMenu() {
        return menu;
    }

    public void setMenu(MenuGroupActions menu) {
        this.menu = menu;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }    
    

    public FormRecord getFormView() {
        return formView;
    }

    public void setFormView(FormRecord formView) {
        this.formView = formView;
    }

    public TreeRecord getTreeView() {
        return treeView;
    }

    public void setTreeView(TreeRecord treeView) {
        this.treeView = treeView;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
    
    

    @Override
    public boolean isCreateonfield() {
        return false; //To change body of generated methods, choose Tools | Templates.
    }

   @Override
    public String getModuleName() {
        return "kerencore"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDesignation() {
        return label; //To change body of generated methods, choose Tools | Templates.
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getModal() {
        return modal;
    }

    public void setModal(Boolean modal) {
        this.modal = modal;
    }

    public short getSecuritylevel() {
        return securitylevel;
    }

    public void setSecuritylevel(short securitylevel) {
        this.securitylevel = securitylevel;
    }

    public MenuModule getModule() {
        return module;
    }

    public void setModule(MenuModule module) {
        this.module = module;
    }   

    public List<ReportRecord> getRecords() {
        return records;
    }

    public void setRecords(List<ReportRecord> records) {
        this.records = records;
    }

    public DashboardRecord getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardRecord dashboard) {
        this.dashboard = dashboard;
    }
    
    
     
    @Override
    public int compareTo(MenuAction o) {
         //To change body of generated methods, choose Tools | Templates.
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "MenuAction{" + "name=" + name + ", id=" + id + ", label=" + label + ", entityName=" + entityName + ", model=" + model + ", menu=" + menu + ", icon=" + icon + ", viewMode=" + viewMode + ", module=" + module + ", formView=" + formView + ", treeView=" + treeView + ", securitylevel=" + securitylevel + '}';
    }
    
    
}
