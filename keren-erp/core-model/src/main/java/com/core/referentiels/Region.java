/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.referentiels;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_REGION")
@XmlRootElement
public class Region extends BaseElement implements Serializable,Comparable<Region>{

    @Predicate(label = "Code de la Region" ,optional = false,unique = true,search = true)
     private String code ;
    
    @Predicate(label = "Nom de la Region" ,optional = false,unique = true,search = true)
     private String intitule ;

    /**
     * 
     */
    public Region() {
    }

    /**
     * 
     * @param code
     * @param designation 
     */
    public Region(String code, String designation) {
        this.code = code;
        this.intitule = designation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDesignation() {
        return intitule;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    
    @Override
    public String getListTitle() {
        return "ETATS";//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "ETAT"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public String getModuleName() {
         //To change body of generated methods, choose Tools | Templates.
        return "kerencore";
    }
    
    
    
    @Override
    public int compareTo(Region o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }

    @Override
    public String toString() {
        return "Region{" + "code=" + code + ", designation=" + designation + '}';
    }
    
    
}
