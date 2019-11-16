/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.email;

import com.core.base.BaseElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author BEKO
 */
@Entity
@Table(name = "K_EMAIL")
public class Email extends BaseElement implements Serializable,Comparable<Email>{

    @Column(name = "SUB",nullable = false)
    private String subject ;
    
    @Column(name = "MESS")
    @Lob
    private String text ;
    
    @Column(name = "SRC",nullable = false)
    private String source;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SENT_D")
    private Date sentDate;
    
    @Column(name = "CIB",nullable = false)
    private String cible ;
    
    @ElementCollection
    private List<String> copies = new ArrayList<String>();
    
    @ElementCollection
    private List<String> piecesjointes = new ArrayList<String>();

    /**
     * 
     * @param subject
     * @param to
     * @param text
     * @param from 
     */
    public Email(String from , String to,String subject, String text) {
        super(-1, null, null,0L);
        this.subject = subject;
        this.text = text;
        this.source = from;
        this.cible = to ;
//        this.sentDate = sentDate;
    }

    /**
     * 
     * @param subject
     * @param text
     * @param from
     * @param sentDate
     * @param id
     * @param designation
     * @param moduleName 
     */
    public Email(String subject, String text, String from, Date sentDate, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.subject = subject;
        this.text = text;
        this.source = from;
        this.sentDate = sentDate;
    }

    /**
     * 
     */
    public Email() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
   

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCible() {
        return cible;
    }

    public void setCible(String cible) {
        this.cible = cible;
    }

   

    public List<String> getCopies() {
        return copies;
    }

    public void setCopies(List<String> copies) {
        this.copies = copies;
    }

    public List<String> getPiecesjointes() {
        return piecesjointes;
    }

    public void setPiecesjointes(List<String> piecesjointes) {
        this.piecesjointes = piecesjointes;
    }
    
    

    @Override
    public String getDesignation() {
        return super.getDesignation(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getModuleName() {
        return super.getModuleName(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getListTitle() {
        return super.getListTitle(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return super.getEditTitle(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    @Override
    public int compareTo(Email o) {
        //To change body of generated methods, choose Tools | Templates.
        return source.compareTo(o.source);
    }

    @Override
    public String toString() {
        return "Email{" + "subject=" + subject + ", text=" + text + ", source=" + source + ", sentDate=" + sentDate + ", cible=" + cible + ", copies=" + copies + '}';
    }
    
    
    
}
