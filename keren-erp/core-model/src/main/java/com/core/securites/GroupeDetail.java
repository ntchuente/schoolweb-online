/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.securites;

import com.core.base.BaseElement;
import com.core.menus.MenuAction;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_GOUPD")
public class GroupeDetail extends BaseElement implements Serializable,Comparable<GroupeDetail>{

    @ManyToOne
    @JoinColumn(name = "ACT_ID")
    @Predicate(label = "groupe.detail.action",optional = false,type = MenuAction.class,target = "many-to-one",editable = false,search = true)
    private MenuAction menuAction ;
    
    @Predicate(label = "groupe.detail.niveau.habilitation" , target = "combobox" ,values = "Suppression;Ecriture;Lecture;Aucune",search = true)
    private String habilitation;

    /**
     * 
     */
    public GroupeDetail() {
    }

    /**
     * 
     * @param menuAction
     * @param level
     * @param id
     * @param designation
     * @param moduleName 
     */
    public GroupeDetail(MenuAction menuAction, String level, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.menuAction = menuAction;
        this.habilitation = level;
    }
    
    public GroupeDetail(GroupeDetail groupe) {
        super(groupe.id, groupe.designation, groupe.moduleName,groupe.compareid);
        //this.menuAction = groupe.menuAction;
        this.habilitation = groupe.habilitation;
    }

    /**
     * 
     * @param menuAction
     * @param level 
     */
    public GroupeDetail(MenuAction menuAction, String level) {
        this.menuAction = menuAction;
        this.habilitation = level;
    }
    public MenuAction getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(MenuAction menuAction) {
        this.menuAction = menuAction;
    }

    public String getHabilitation() {
        return habilitation;
    }

    public void setHabilitation(String habilitation) {
        this.habilitation = habilitation;
    }

    @Override
    public String getDesignation() {
        return menuAction.getDesignation(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public boolean isCreateonfield() {
        return false; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "NIVEAUX HABILITATIONS"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "NIVEAU HABILITATION"; //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    @Override
    public int compareTo(GroupeDetail o) {
        //To change body of generated methods, choose Tools | Templates.
        return menuAction.compareTo(o.menuAction);
    }
    
}
