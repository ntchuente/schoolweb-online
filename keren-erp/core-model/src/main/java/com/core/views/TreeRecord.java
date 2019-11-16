/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.views;

import com.core.menus.MenuAction;
import com.megatim.common.annotations.Predicate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 *
 * @author Commercial_2
 */
@Entity
@DiscriminatorValue(value = "TREE")
public class TreeRecord extends Record{   
    
    @OneToOne
    @JoinColumn(name="T_ITEM_ID")
    @Predicate(label = "Menu Item Parent" ,type = MenuAction.class,group = true,groupName = "group2",groupLabel = "MENUS ACTIONS",search = true)
    private MenuAction action ;
    
    @Lob
    private String template ;

    /**
     * 
     */
    public TreeRecord() {
    }

    /**
     * 
     * @param record
     */
    public TreeRecord(TreeRecord record) {
        super(record.code, record.titre, record.modele, record.sequence, record.script, record.id, record.designation, record.moduleName);
        this.template = record.template;
    }
    
    

    public MenuAction getAction() {
        return action;
    }

    public void setAction(MenuAction action) {
        this.action = action;
    }  

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
    
    
   

    @Override
    public String getListTitle() {
        return "ARBORESCENCES"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "ARBORESCENCE"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDesignation() {
        return titre; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
