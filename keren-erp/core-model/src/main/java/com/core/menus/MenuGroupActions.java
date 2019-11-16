/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.menus;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "M_AGROUP")
@XmlRootElement
public class MenuGroupActions extends BaseElement implements Serializable,Comparable<MenuGroupActions>{   
   
     
     
    @Predicate(label = "IDENTIFIANT DU MENU" ,optional = false,unique = true,updatable = false,search = true)
    @Column(nullable = false,unique = true)
     private String name ;
     
     @Predicate(label = "NOM DU MENU" ,optional = false,search = true)
     private String label ;
     
     @ManyToOne
     @JoinColumn(name = "AG_ID")
     @Predicate(label = "MODULE PARENT" ,type = MenuModule.class,search = true)
     private MenuModule module ;
     
     @Predicate(label = "ICON")
     private String icon ;
      
     /**
      * Affiche le menu si true sinon desactive le menu
      */
     private boolean showmenu = true;
     //@Predicate(label = "menuitems",target = "one-to-many" ,group = true,groupName = "group1",groupLabel = "MENU ITEMS",updatable = false,type = MenuAction.class)
     @OneToMany(mappedBy ="menu" ,fetch = FetchType.LAZY)
     private List<MenuAction> actions = new ArrayList<MenuAction>();
 
     /**
      * NIveau de securite
      * 0 = afficher
      * 1=Masquer      
      */
     private short securitylevel = 0;
     
     /**
      * 
      */
    public MenuGroupActions() {
    }

    /**
     * 
     * @param icon
     * @param name
     * @param label
     * @param module 
     */
    public MenuGroupActions(String icon, String name, String label, MenuModule module) {
        this.icon = icon;
        this.name = name;
        this.label = label;
        this.module = module;
    }

    /**
     * 
     * @param group 
     */
    public MenuGroupActions(MenuGroupActions group) {
        super(group.getId(), group.getDesignation(), group.getModuleName(),group.compareid);
        this.icon = group.icon;
        this.name = group.name;
        this.label = group.label;  
        this.securitylevel = group.securitylevel;
    
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

    public List<MenuAction> getActions() {
        return actions;
    }

    public void setActions(List<MenuAction> actions) {
        this.actions = actions;
    }

    public MenuModule getModule() {
        return module;
    }

    public void setModule(MenuModule module) {
        this.module = module;
    }

    public short getSecuritylevel() {
        return securitylevel;
    }

    public void setSecuritylevel(short securitylevel) {
        this.securitylevel = securitylevel;
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

    @Override
    public String getListTitle() {
        return "MENUS"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "MENU"; //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isShowmenu() {
        return showmenu;
    }

    public void setShowmenu(boolean showmenu) {
        this.showmenu = showmenu;
    }

     
     
    @Override
    public int compareTo(MenuGroupActions o) {
        //To change body of generated methods, choose Tools | Templates.
        return name.compareTo(o.name);
    }
}
