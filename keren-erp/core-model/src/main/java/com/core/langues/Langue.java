/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.langues;

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
@Table(name = "T_LANGUE")
@XmlRootElement
public class Langue extends BaseElement implements Serializable,Comparable<Langue>{

    @Predicate(label = "Nom",search = true)
    private String intitule ;
    
    @Predicate(label = "Actif" ,type = Boolean.class,search = false)
    private Boolean actif = Boolean.FALSE;
    
    @Predicate(label = "Code local",search = true)
    private String code ;
    
    @Predicate(label = "Direction" ,target = "combobox",values ="De droite à gauche ;De gauche à droite")
    private String direction;
    
    @Predicate(label = "Code ISO",search = true)
    private String codeISO;
    
    @Predicate(label = "Séparateur décimal",search = true)
    private String formatDecimal ;
    
    @Predicate(label = "Séparateur des milliers",search = true)
    private String formatMillier ;
    
    @Predicate(label = "Format de date",search = true)
    private String formatDate ;
    
    @Predicate(label = "Format de l'heure",search = true)
    private String formatHeure;

    /**
     * 
     */
    public Langue() {
    }

    /**
     * 
     * @param designation
     * @param code
     * @param direction
     * @param codeISO
     * @param formatDecimal
     * @param formatMillier
     * @param formatDate
     * @param formatHeure 
     */
    public Langue(String designation, String code, String direction, String codeISO, String formatDecimal, String formatMillier, String formatDate, String formatHeure) {
        this.intitule = designation;
        this.code = code;
        this.direction = direction;
        this.codeISO = codeISO;
        this.formatDecimal = formatDecimal;
        this.formatMillier = formatMillier;
        this.formatDate = formatDate;
        this.formatHeure = formatHeure;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCodeISO() {
        return codeISO;
    }

    public void setCodeISO(String codeISO) {
        this.codeISO = codeISO;
    }

    public String getFormatDecimal() {
        return formatDecimal;
    }

    public void setFormatDecimal(String formatDecimal) {
        this.formatDecimal = formatDecimal;
    }

    public String getFormatMillier() {
        return formatMillier;
    }

    public void setFormatMillier(String formatMillier) {
        this.formatMillier = formatMillier;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public String getFormatHeure() {
        return formatHeure;
    }

    public void setFormatHeure(String formatHeure) {
        this.formatHeure = formatHeure;
    }

    @Override
    public String getDesignation() {
        return intitule; //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public String getListTitle() {
         //To change body of generated methods, choose Tools | Templates.
        return "LANGUES";
    }

    @Override
    public String getEditTitle() {
        //To change body of generated methods, choose Tools | Templates.
        return "LANGUE";
    }
    
    @Override
    public String getModuleName() {
         //To change body of generated methods, choose Tools | Templates.
        return "kerencore";
    }
    
    
    @Override
    public int compareTo(Langue o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }
    
}
