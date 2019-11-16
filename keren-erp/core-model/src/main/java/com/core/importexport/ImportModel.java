/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.importexport;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;

/**
 *
 * @author BEKO
 */
public class ImportModel extends BaseElement implements Serializable,Comparable<ImportModel>{

    @Predicate(label = "Fichier",target = "file")
    private String file ;
    
    @Predicate(label = "Extension",target = "combobox",values = "EXCEL;CVS")
    private String extension ="0";
    
    private String className ;
    
    @OneToMany
    @Predicate(label = ".",type = LigneImportModel.class,target = "one-to-many",edittable = true,group = true,groupName = "group1",groupLabel = "Champs disponibles")
    private List<LigneImportModel> lignes = new ArrayList<LigneImportModel>();

    /**
     * 
     * @param file 
     */
    public ImportModel(String file) {
        this.file = file;
    }

    /**
     * 
     * @param file
     * @param id
     * @param designation
     * @param moduleName 
     */
    public ImportModel(String file, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.file = file;
    }

    public ImportModel() {
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<LigneImportModel> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneImportModel> lignes) {
        this.lignes = lignes;
    }
    
    
    
    @Override
    public int compareTo(ImportModel o) {
        return className.compareTo(o.className); //To change body of generated methods, choose Tools | Templates.
    }
    
}
