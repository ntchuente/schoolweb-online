/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.referentiels;

import com.core.base.BaseElement;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_PJ")
public class PieceJointe extends BaseElement implements Serializable,Comparable<PieceJointe>{

    private String filename ;
    
    private String attachename ;
    
    //Numero de serie de l'entite
    private String entityserial ;
    
    //Identifiant de l'entite
    private long entityid ;

    /**
     * 
     * @param filename
     * @param attachename 
     */
    public PieceJointe(String filename, String attachename) {
        this.filename = filename;
        this.attachename = attachename;
    }

    /**
     * 
     * @param filename
     * @param attachename
     * @param serial
     * @param entityid 
     */
    public PieceJointe(String filename, String attachename, String serial, long entityid) {
        this.filename = filename;
        this.attachename = attachename;
        this.entityserial = serial;
        this.entityid = entityid;
    }

    /**
     * 
     * @param filename
     * @param attachename
     * @param serial
     * @param entityid
     * @param id
     * @param designation
     * @param moduleName 
     */
    public PieceJointe(String filename, String attachename, String serial, long entityid, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.filename = filename;
        this.attachename = attachename;
        this.entityserial = serial;
        this.entityid = entityid;
    }
    
    /**
     * 
     * @param pj 
     */
    public PieceJointe(PieceJointe pj) {
        super(pj.id, pj.designation, pj.moduleName,pj.compareid);
        this.filename = pj.filename;
        this.attachename = pj.attachename;
        this.entityserial = pj.serial;
        this.entityid = pj.entityid;
    }

    /**
     * 
     * @param filename
     * @param attachename
     * @param id
     * @param designation
     * @param moduleName 
     */
    public PieceJointe(String filename, String attachename, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.filename = filename;
        this.attachename = attachename;
    }

    /**
     * 
     */
    public PieceJointe() {
    }

    
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getAttachename() {
        return attachename;
    }

    public void setAttachename(String attachename) {
        this.attachename = attachename;
    }

    public long getEntityid() {
        return entityid;
    }

    public void setEntityid(long entityid) {
        this.entityid = entityid;
    }

    public String getEntityserial() {
        return entityserial;
    }

    public void setEntityserial(String entityserial) {
        this.entityserial = entityserial;
    }
       
    @Override
    public int compareTo(PieceJointe o) {
         //To change body of generated methods, choose Tools | Templates.
        return filename.compareTo(o.filename);
    }
    
}
