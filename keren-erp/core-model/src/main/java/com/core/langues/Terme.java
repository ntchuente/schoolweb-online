/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.langues;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Filter;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *Terme du langue 
 * NB le Francais est le language par defaut 
 * Donc la traduction se fera du francais vers le langague en question
 * @author BEKO
 */
@Entity
@Table(name = "K_TERME")
public class Terme extends BaseElement implements Serializable,Comparable<Terme>{

    @Predicate(label = "Terme source",target = "textarea",search = true)
    private String orign ;
    
    @Predicate(label = "Traduction",target = "textarea",search = true)
    private String traduc ;
    
    @ManyToOne
    @JoinColumn(name = "LANG_ID")
    @Predicate(label = "Langue",type = Langue.class,target = "many-to-one",search = true)
    @Filter(value = "[{\"fieldName\":\"codeISO\",\"value\":\"fr\",\"operator\":\"!=\"}]")
    private Langue  langue ;

    /**
     * 
     */
    public Terme() {
    }

    /**
     * 
     * @param orign
     * @param traduc
     * @param langue 
     */
    public Terme(String orign, String traduc, Langue langue) {
        this.orign = orign;
        this.traduc = traduc;
        this.langue = langue;
    }

    /**
     * 
     * @param orign
     * @param traduc
     * @param langue
     * @param id
     * @param designation
     * @param moduleName 
     */
    public Terme(String orign, String traduc, Langue langue, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.orign = orign;
        this.traduc = traduc;
        this.langue = langue;
    }

    public String getOrign() {
        return orign;
    }

    public void setOrign(String orign) {
        this.orign = orign;
    }

    public String getTraduc() {
        return traduc;
    }

    public void setTraduc(String traduc) {
        this.traduc = traduc;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    @Override
    public String getDesignation() {
        return orign; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getModuleName() {
        return "kerencore"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "Termes traduits"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "Terme traduit"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    @Override
    public int compareTo(Terme o) {
        //To change body of generated methods, choose Tools | Templates.
        return orign.compareTo(o.orign);
    }
    
}
