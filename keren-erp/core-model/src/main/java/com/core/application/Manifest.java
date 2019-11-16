/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.application;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@XmlRootElement
public class Manifest implements Serializable,Comparable<Manifest>{

    private String name ;
    
    private String version ;
    
    private String summary ;
    
    private String author ;
    
    private short sequence ;
    
    private String description ;
    
    private String category;
    
    private String website ;
    
    private String[] images  ;
    
    private String[] depends ;
    
    private String[] views ;
    
    private String[] cores ;
    
    private String[] reports ;
    
    private boolean installable = true ;
    
    private boolean application = true ;
    
    private boolean auto_install = false;
    
    private String filename ;
    
    

    /**
     * 
     */
    public Manifest() {
    }
    
    

            /**
             * 
             * @param name
             * @param version
             * @param summary
             * @param sequance
             * @param description
             * @param category
             * @param website
             * @param images
             * @param depends
             * @param views
             * @param cores
             * @param reports 
             */
    public Manifest(String name, String version, String summary, short sequance, String description, String category, String website, String[] images, String[] depends, String[] views, String[] cores, String[] reports) {
        this.name = name;
        this.version = version;
        this.summary = summary;
        this.sequence = sequance;
        this.description = description;
        this.category = category;
        this.website = website;
        this.images = images;
        this.depends = depends;
        this.views = views;
        this.cores = cores;
        this.reports = reports;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public short getSequence() {
        return sequence;
    }

    public void setSequence(short sequance) {
        this.sequence = sequance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getDepends() {
        return depends;
    }

    public void setDepends(String[] depends) {
        this.depends = depends;
    }

    public String[] getViews() {
        return views;
    }

    public void setViews(String[] views) {
        this.views = views;
    }

    public String[] getCores() {
        return cores;
    }

    public void setCores(String[] cores) {
        this.cores = cores;
    }

    public String[] getReports() {
        return reports;
    }

    public void setReports(String[] reports) {
        this.reports = reports;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    
    @Override
    public int compareTo(Manifest o) {
        //To change body of generated methods, choose Tools | Templates.
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "Manifest{" + "name=" + name + ", version=" + version + ", summary=" + summary + ", author=" + author + ", sequence=" + sequence + ", description=" + description + ", category=" + category + ", website=" + website + ", images=" + images + ", depends=" + depends + ", views=" + views + ", cores=" + cores + ", reports=" + reports + ", installable=" + installable + ", application=" + application + ", auto_install=" + auto_install + '}';
    }

   
}
