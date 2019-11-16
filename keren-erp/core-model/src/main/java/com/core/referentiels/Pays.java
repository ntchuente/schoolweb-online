/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.referentiels;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name = "T_PAYS")
@XmlRootElement
public class Pays extends BaseElement implements Serializable,Comparable<Pays>{

     @Predicate(label = "image",target = "image",search = false)
     private String image ;
     
    @Predicate(label = "Nom du pays" ,optional = false,unique = true,search = true)
    private String intitule ;
    
    @Predicate(label = "Code du pays" ,optional = false,updatable = false,unique = true,search = true)
    private String code;
    
    @ManyToOne
    @JoinColumn(name = "DEVISE_ID")
    @Predicate(label = "Dévise",type = Devise.class,target="many-to-one",search = true)
    private Devise devise ;
    
    @Predicate(label = "Regions / Etats" , type = Region.class,target = "one-to-many" ,group = true,groupName = "regions",groupLabel = "Régions", search = false,edittable = true)
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYS_ID")
    private List<Region> etats = new ArrayList<Region>();

    /**
     * 
     */
    public Pays() {
    }

    /**
     * 
     * @param image
     * @param designation
     * @param code 
     */
    public Pays(String image, String designation, String code) {
        this.image = image;
        this.intitule = designation;
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

   

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }
    
    

    public List<Region> getEtats() {
        return etats;
    }

    public void setEtats(List<Region> etats) {
        this.etats = etats;
    }
    
    @Override
    public String getModuleName() {
         //To change body of generated methods, choose Tools | Templates.
        return "kerencore";
    }

    @Override
    public String getListTitle() {
        return "PAYS"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "PAYS"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public int compareTo(Pays o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }

    @Override
    public String toString() {
        return "Pays{" + "image=" + image + ", designation=" + designation + ", code=" + code + ", devise=" + devise + ", etats=" + etats + '}';
    }
    
    
    
}
