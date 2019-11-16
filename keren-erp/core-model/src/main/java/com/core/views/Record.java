/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.views;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_VIEW")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE_VIEW",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("VIEW")
public class Record extends BaseElement implements Serializable,Comparable<Record>{
    /**
     * Identifiant de la vue
     */
    @Predicate(label = "Identifiant" , unique = true,optional = false,updatable = false,search = true)
    protected String code ;
    
     
    @Predicate(label = "Actif " , type = Boolean.class)
    protected Boolean actif = Boolean.TRUE;
    
    
    @Predicate(label = "Nom de la Vue",search = true)
    protected String titre ;  
    
//    @Predicate(label = "Type de Vue" ,target = "combobox" ,values = "Formulaire;Arborescence")
//    protected String type ;
    
    @Predicate(label = "Modele",search = true)
    protected String modele ;
    
    @Predicate(label = "Sequence" , type = Short.class,search = true)
    protected short sequence =0;
    
    @Column(name = "ignor")
    private boolean ignore = false;
    
   
    @Predicate(label = "Architecture" ,group = true,groupName = "architecture",groupLabel = "Architecture",target = "aceeditor",search = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    protected String script ;
    
    
    
    /**
     * 
     */
    public Record() {
    }

    /**
     * 
     * @param code
     * @param titre
     * @param modele
     * @param sequence
     * @param script
     * @param id
     * @param designation
     * @param moduleName 
     */
    public Record(String code, String titre, String modele, short sequence, String script, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.code = code;
        this.titre = titre;
        this.modele = modele;
        this.sequence = sequence;
        this.script = script;
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

   
    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public short getSequence() {
        return sequence;
    }

    public void setSequence(short sequence) {
        this.sequence = sequence;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
    
    

    
    @Override
    public String getModuleName() {
        return "kerencore"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDesignation() {
        return titre; //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public int compareTo(Record o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }
}
