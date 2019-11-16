/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.views;

import com.core.menus.MenuAction;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 *
 * @author Commercial_2
 */
@Entity
@DiscriminatorValue("REPORT")
public class ReportRecord extends Record implements Serializable{
    
    /**
     *
     */
    @Predicate(label = "Search" ,group = true,groupName = "group2",groupLabel = "Search",target = "aceeditor",search = false)
    @Lob
    @Column(name = "script_s")
    @Basic(fetch = FetchType.LAZY)
    protected String search ;

    @ManyToOne
    @JoinColumn(name="R_ITEM_ID")
    @Predicate(label = "Menu Item Parent" ,type = MenuAction.class,group = true,groupName = "group3",groupLabel = "MENUS ACTIONS",search = true)
    private MenuAction action ;
    
    private String method ;    
    
    private String entity ;
    
    private String model ;
    
    private String clazz ;
    
    private boolean extern = false ;
    
   
     /**
      * 
      */
    public ReportRecord() {
    }

     
     
     /**
      * 
      * @param action 
      */
    public ReportRecord(MenuAction action) {
        this.action = action;
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
    public ReportRecord(MenuAction action, String code, String titre, String modele, short sequence, String script, long id, String designation, String moduleName) {
        super(code, titre, modele, sequence, script, id, designation, moduleName);
        this.action = action;
    }
    public ReportRecord(ReportRecord report) {
        super(report.code, report.titre, report.modele, report.sequence, null, report.id, report.designation, report.moduleName);
        super.setIgnore(report.isIgnore());
        model = report.model;
        entity = report.entity;
        search = report.search;
        //this.action = action;
    }

    public MenuAction getAction() {
        return action;
    }

    public void setAction(MenuAction action) {
        this.action = action;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }    

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    
    
    
    
    @Override
    public String getListTitle() {
        //To change body of generated methods, choose Tools | Templates.
        return "RAPPORTS";
    }

    @Override
    public String getEditTitle() {
        //To change body of generated methods, choose Tools | Templates.
        return "RAPPORT";
    }

    @Override
    public String getDesignation() {
        return getTitre(); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isExtern() {
        return extern;
    }

    public void setExtern(boolean extern) {
        this.extern = extern;
    }

    
     
     
}
