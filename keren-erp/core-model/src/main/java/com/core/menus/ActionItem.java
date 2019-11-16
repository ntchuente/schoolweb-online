/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.menus;

import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Commercial_2
 */
@Entity
@DiscriminatorValue("ITEM_ACT")
public class ActionItem extends  MenuAction implements Serializable{
  
   @Predicate(label = "ACTION TYPE",optional = false,search = true,type = String.class)
   private String type ;
    
//   private String parentRef ;
   
   @Column(name = "requete")
   @Predicate(label = "Value",optional = false,target = "textarea",group = true,groupName = "group1",groupLabel = "Requête")
   private String value ;
   
   
   /**
    * 
    * @param name
     * @param type
    * @param label
    * @param value 
    */
    public ActionItem(String name, String type, String label, String value) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.value = value;
        this.setActions(null);
    }

    /**
     * 
     * @param item 
     */
    public ActionItem(ActionItem item) {        
        this.id = item.getId();
        this.designation = item.getDesignation();
        this.name = item.name;
        this.type = item.type;
        this.label = item.label;
        this.value = item.value;
        this.setActions(null);
    }
    /**
     * 
     * @param name
     * @param parentRef
     * @param label
     * @param value
     * @param id
     * @param designation
     * @param moduleName 
     */
    public ActionItem(String name, String parentRef, String label, String value, long id, String designation, String moduleName) {
        //super(id, designation, moduleName);
        this.name = name;
//        this.parentRef = parentRef;
        this.label = label;
        this.value = value;
    }

    public ActionItem() {
    }

   
//    public String getParentRef() {
//        return parentRef;
//    }
//
//    public void setParentRef(String parentRef) {
//        this.parentRef = parentRef;
//    }

   
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getEditTitle() {
        return "ACTION"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "ACTIONS"; //To change body of generated methods, choose Tools | Templates.
    }
   
       
}
