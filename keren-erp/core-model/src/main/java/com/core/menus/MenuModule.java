/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.menus;

import com.core.application.Manifest;
import com.core.base.BaseElement;
import com.core.views.FormRecord;
import com.core.views.TreeRecord;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "M_MODULE")
@XmlRootElement
public class MenuModule extends BaseElement implements Serializable,Comparable<MenuModule>{
    
     @Predicate(label = "IMAGE MODULE",search = false,target = "image")
     private String icon ;
     
     @Column(nullable = false,unique = true)
     @Predicate(label = "IDENTIFIANT",optional = false,unique = true,updatable = false,search = false)
     private String name ;
     
     @Predicate(label = "NOM DU MODULE",optional = false,unique = false,search = true)
     private String label ;    
     
     //@Predicate(label = "ENTITE MANAGE",optional = false,search = false,unique = false)
     private String entityName ;
     
     //@Predicate(label = "MODELE",optional = false,search = false,unique = false)
     private String model ;
     
     @Predicate(label = "BREF DESCRIPTION DU MODULE",search = false,unique = false,updatable = false)
     private String shortDescription ;
     
     @Predicate(label = "AUTEUR",unique = false,updatable = false,search = true)
     private String autor ;
     
     @Predicate(label = "VERSION",unique = false,updatable = false,search = true)
     private String version ;     
     
     //@Predicate(label = "SEQUENCE" ,type = Short.class,updatable = false,unique = false)
     @Column(name = "ORDRE")
     private Short sequence =0;
     
     private String categorie ; 
     
     //@Predicate(label = "SITE WEB" ,target = "url",updatable = false,unique = false)
     private String website ;
     
     @Predicate(label = "DESCRIPTION DETAILLEE DU MODULE",group = true,groupName = "group_1",groupLabel = "DESCRIPTION DETAILLEE",target = "richeditor",search = false,unique = false)
     private String longDescription ;
     
     private boolean installable = Boolean.TRUE;
     
     private boolean application = Boolean.TRUE;
     
     private boolean auto_install = Boolean.FALSE;
     
     private boolean hasmenu = true ;
     
     @Predicate(label = "INSTALLE" ,updatable = false,unique = false,type = Boolean.class,search = true)
     private Boolean active = false ;
     
     @OneToOne(fetch = FetchType.LAZY,mappedBy ="module" )
     private MenuAction action ;
     
     @OneToMany(mappedBy = "module" , fetch = FetchType.LAZY)
     //@Predicate(label = "menus" ,target = "one-to-many" ,updatable = false ,group = true,groupName = "group1",groupLabel = "MENUS",type = MenuGroupActions.class)
     private List<MenuGroupActions> groups = new ArrayList<MenuGroupActions>();

     private String color;
     
     /**
      * Constructeur par defaut
      */
    public MenuModule() {
    }

    /**
     * 
     * @param icon
     * @param name
     * @param label
     * @param entityName
     * @param model
     * @param shortDescription
     * @param autor
     * @param version
     * @param longDescription
     * @param formView
     * @param treeView
     * @param id
     * @param designation
     * @param moduleName 
     */
    public MenuModule(String icon, String name, String label, String entityName, String model, String shortDescription, String autor, String version, String longDescription, FormRecord formView, TreeRecord treeView, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.icon = icon;
        this.name = name;
        this.label = label;
        this.entityName = entityName;
        this.model = model;
        this.shortDescription = shortDescription;
        this.autor = autor;
        this.version = version;
        this.longDescription = longDescription;
        
    }

/**
 * 
 * @param module 
 */  
public MenuModule(MenuModule module) {
        super(module.id, module.designation, module.moduleName,module.compareid);
        this.icon = module.icon;
        this.name = module.name;
        this.label = module.label;
        this.color = module.color;
        this.entityName = module.entityName;
        this.model = module.model;
        this.shortDescription = module.shortDescription;
        this.autor = module.autor;
        this.version = module.version;
        this.longDescription = module.longDescription;
        this.sequence = module.getSequence();
        this.website = module.getWebsite();
        this.installable = module.installable;
        this.application = module.application;
        this.auto_install = module.auto_install;
        this.active = module.active;
        this.hasmenu = module.hasmenu;
        this.categorie = module.categorie;
    }

/**
 * 
     * @param manifest 
 */  
public MenuModule(Manifest manifest) {
        //this.icon = manifest.icon;
        this.name = manifest.getFilename();
        this.label = manifest.getName();
        this.entityName = null;
        this.model = null;
        this.shortDescription = manifest.getSummary();
        this.autor = manifest.getAuthor();
        this.version = manifest.getVersion();
        this.longDescription = manifest.getDescription();
        this.sequence = manifest.getSequence();
        this.website = manifest.getWebsite();
        this.installable = manifest.isInstallable();
        this.application = manifest.isApplication();
        this.auto_install = manifest.isAuto_install();
        this.categorie = manifest.getCategory();
    }

    /**
     * 
     * @param manifest 
     */
    public void update(Manifest manifest){
        this.name = manifest.getFilename();
        this.label = manifest.getName();
        this.entityName = null;
        this.model = null;
        this.shortDescription = manifest.getSummary();
        this.autor = manifest.getAuthor();
        this.version = manifest.getVersion();
        this.longDescription = manifest.getDescription();
        this.sequence = manifest.getSequence();
        this.website = manifest.getWebsite();
        this.installable = manifest.isInstallable();
        this.application = manifest.isApplication();
        this.auto_install = manifest.isAuto_install();
        this.categorie = manifest.getCategory();
    }

    @Override
    public boolean isDesablecreate() {
         //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    public Short getSequence() {
        return sequence;
    }

    public void setSequence(Short sequence) {
        this.sequence = sequence;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }    

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<MenuGroupActions> getGroups() {
        return groups;
    }

    public void setGroups(List<MenuGroupActions> groups) {
        this.groups = groups;
    }

    public MenuAction getAction() {
        return action;
    }

    public void setAction(MenuAction action) {
        this.action = action;
    }

    public boolean isInstallable() {
        return installable;
    }

    public void setInstallable(boolean installable) {
        this.installable = installable;
    }

    public boolean isApplication() {
        return application;
    }

    public void setApplication(boolean application) {
        this.application = application;
    }

    public boolean isAuto_install() {
        return auto_install;
    }

    public void setAuto_install(boolean auto_install) {
        this.auto_install = auto_install;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    

    @Override
    public String getModuleName() {
        return "kerencore"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return "APPLICATIONS"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "APPLICATION"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDesignation() {
        return label; //To change body of generated methods, choose Tools | Templates.
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isHasmenu() {
        return hasmenu;
    }

    public void setHasmenu(boolean hasmenu) {
        this.hasmenu = hasmenu;
    }

   
     
    @Override
    public int compareTo(MenuModule o) {
        //To change body of generated methods, choose Tools | Templates.
        return name.compareTo(o.name);
    }
    
}
