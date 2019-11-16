/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.calendar;

import com.core.base.BaseElement;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_RAPPEL")
public class Rappel extends BaseElement implements Serializable,Comparable<Rappel>{

    private String titre ;
    
    /**
     * Unite de temps pour le rappel
     * 0 = aucun , 1 = heure , 2 = minutes , 3 = Jour(s)
     */
    private short unite ;
    
    private short quantite  ;

    /**
     * 
     */
    public Rappel() {
    }

    /**
     * 
     * @param titre
     * @param unite
     * @param quantite 
     */
    public Rappel(String titre, short unite, short quantite) {
        this.titre = titre;
        this.unite = unite;
        this.quantite = quantite;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public short getUnite() {
        return unite;
    }

    public void setUnite(short unite) {
        this.unite = unite;
    }

    public short getQuantite() {
        return quantite;
    }

    public void setQuantite(short quantite) {
        this.quantite = quantite;
    }

    @Override
    public String getDesignation() {
        return titre; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    @Override
    public int compareTo(Rappel o) {
        //To change body of generated methods, choose Tools | Templates.
        return titre.compareTo(o.titre);
    }
    
}
