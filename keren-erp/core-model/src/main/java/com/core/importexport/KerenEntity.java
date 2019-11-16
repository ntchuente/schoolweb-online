/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.importexport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;

/**
 *Class 
 * @author BEKO
 */
public class KerenEntity extends BaseElement implements Serializable,Comparable<KerenEntity>{

    @Predicate(label = "Entité Concerné",editable = false)
    private String code ;
    
    @Predicate(label = "Fichier",target = "file")
    private String file ;

    @Predicate(label = ".",target = "one-to-many",edittable = true,type = KerenElement.class,group = true,groupName = "group",groupLabel = "Colonnes")
    private List<KerenElement> fields = new ArrayList<KerenElement>();
    /**
     * 
     */
    public KerenEntity() {
    }

    /**
     * 
     * @param code
     * @param intitule
     * @param id
     * @param designation
     * @param moduleName 
     */
    public KerenEntity(String code, String intitule, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.code = code;
        this.file = intitule;
    }
    
    /**
     * 
     * @param code
     * @param intitule 
     */
    public KerenEntity(String code, String intitule) {
        super(-1, null, null,0L);
        this.code = code;
        this.file = intitule;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }   

    public List<KerenElement> getFields() {
        return fields;
    }

    public void setFields(List<KerenElement> fields) {
        this.fields = fields;
    }
    
    
    
    @Override
    public int compareTo(KerenEntity o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }
    
}
