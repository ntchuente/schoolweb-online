/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.langues;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@XmlRootElement
public class ChargerLangue extends BaseElement implements Serializable,Comparable<ChargerLangue>{

    @Predicate(label = "Langue",target = "many-to-one",type = Langue.class,search = false)
    @ManyToOne
    private Langue langue;

    /**
     * 
     */
    public ChargerLangue() {
    }

    /**
     * 
     * @param langue 
     */
    public ChargerLangue(Langue langue) {
        this.langue = langue;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    @Override
    public String getListTitle() {
        return "CHARGER UNE LANGUE"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "CHARGER UNE LANGUE"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getModuleName() {
         //To change body of generated methods, choose Tools | Templates.
        return "kerencore";
    }
    
    
    
    @Override
    public int compareTo(ChargerLangue o) {
        //To change body of generated methods, choose Tools | Templates.
        return langue.compareTo(o.langue);
    }
    
}
