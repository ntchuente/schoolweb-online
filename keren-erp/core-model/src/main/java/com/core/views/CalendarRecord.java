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
import javax.persistence.OneToOne;

/**
 *
 * @author BEKO
 */
@Entity
@DiscriminatorValue(value = "CALENDAR")
public class CalendarRecord extends Record{ 
    
    private String titlefield ;
    
    private String startfield ;
    
    private String endfield ;
    
    private Boolean allday = false ;
    
     @OneToOne
    @JoinColumn(name="T_ITEM_ID")
    @Predicate(label = "Menu Item Parent" ,type = MenuAction.class,group = true,groupName = "group2",groupLabel = "MENUS ACTIONS",search = true)
    private MenuAction action ;

     /**
      * 
      * @param titlefield
      * @param startfield
      * @param endfield
      * @param action 
      */
    public CalendarRecord(String titlefield, String startfield, String endfield, MenuAction action) {
        this.titlefield = titlefield;
        this.startfield = startfield;
        this.endfield = endfield;
        this.action = action;
    }

    /**
     * 
     * @param titlefield
     * @param startfield
     * @param endfield
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
    
    public CalendarRecord(String titlefield, String startfield, String endfield, MenuAction action, String code, String titre, String modele, short sequence, String script, long id, String designation, String moduleName) {
        super(code, titre, modele, sequence, script, id, designation, moduleName);
        this.titlefield = titlefield;
        this.startfield = startfield;
        this.endfield = endfield;
        this.action = action;
    }
    
     public CalendarRecord(CalendarRecord rec) {
        super(rec.code, rec.titre, rec.modele, rec.sequence, rec.script, rec.id, rec.designation, rec.moduleName);
        this.titlefield = rec.titlefield;
        this.startfield = rec.startfield;
        this.endfield = rec.endfield;
//        this.action = action;
    }

    public CalendarRecord() {
    }

    public String getTitlefield() {
        return titlefield;
    }

    public void setTitlefield(String titlefield) {
        this.titlefield = titlefield;
    }

    public String getStartfield() {
        return startfield;
    }

    public void setStartfield(String startfield) {
        this.startfield = startfield;
    }

    public String getEndfield() {
        return endfield;
    }

    public void setEndfield(String endfield) {
        this.endfield = endfield;
    }

    public Boolean getAllday() {
        return allday;
    }

    public void setAllday(Boolean allday) {
        this.allday = allday;
    }

    public MenuAction getAction() {
        return action;
    }

    public void setAction(MenuAction action) {
        this.action = action;
    }

    @Override
    public String getDesignation() {
        return titre; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "CALANDRIER"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "CALANDRIER"; //To change body of generated methods, choose Tools | Templates.
    }
     
     
     
}
