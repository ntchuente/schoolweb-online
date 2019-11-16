/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.importexport;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;

/**
 *
 * @author BEKO
 */
public class LigneImportModel extends BaseElement  implements Serializable,Comparable<LigneImportModel>{

    @Predicate(label = "Nom du champs")
    private String fieldLabel ;
    
    @Predicate(label = "Chemin ")
    private String fieldName ;
    
    @Predicate(label = "Concerné",type = Boolean.class)
    private Boolean statut = Boolean.FALSE;
    
    
    private Short ordre = 0 ;

    public LigneImportModel(String fieldLabel, String fieldName) {
        this.fieldLabel = fieldLabel;
        this.fieldName = fieldName;
    }

    public LigneImportModel(String fieldLabel, String fieldName, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.fieldLabel = fieldLabel;
        this.fieldName = fieldName;
    }

    public LigneImportModel() {
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public Short getOrdre() {
        return ordre;
    }

    public void setOrdre(Short ordre) {
        this.ordre = ordre;
    }
    
    
    
    @Override
    public int compareTo(LigneImportModel o) {
        return fieldName.compareTo(o.fieldName); //To change body of generated methods, choose Tools | Templates.
    }
    
}
