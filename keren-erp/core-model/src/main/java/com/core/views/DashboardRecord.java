/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.views;

import com.core.menus.MenuAction;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Commercial_2
 */
@Entity
@DiscriminatorValue("Dashboard")
public class DashboardRecord extends Record implements Serializable{  
    
    @OneToOne
    @JoinColumn(name="F_ITEM_ID")
    @Predicate(label = "Menu Item Parent" ,type = MenuAction.class,group = true,groupName = "group2",groupLabel = "MENUS ACTIONS",search = true)
    private MenuAction action ;

    /**
     * 
     */
    public DashboardRecord() {
    }

    /**
     * 
     * @param action
     * @param code
     * @param titre
     * @param modele
     * @param sequence
     * @param script
     * @param id
     * @param designation
     * @param moduleName 
     */
    public DashboardRecord(MenuAction action, String code, String titre, String modele, short sequence, String script, long id, String designation, String moduleName) {
        super(code, titre, modele, sequence, script, id, designation, moduleName);
        this.action = action;
    }
    
    
    /**
     * 
     * @param record 
     */
    public DashboardRecord(DashboardRecord record) {
        super(record.code, record.titre, record.modele, record.sequence, record.script, record.id, record.designation, record.moduleName);
        //this.action = record.action;
    }

    public MenuAction getAction() {
        return action;
    }

    public void setAction(MenuAction action) {
        this.action = action;
    }

    

    @Override
    public String getListTitle() {
         //To change body of generated methods, choose Tools | Templates.
        return "TABLEAUX DE BORD";
    }

    @Override
    public String getEditTitle() {
        return "TABLEAU BORD"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
