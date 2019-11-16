/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.referentiels;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Commercial_2
 */
@Entity
@Table(name = "T_SOCIETE")
@XmlRootElement
public class Societe extends BaseElement implements Serializable,Comparable<Societe>{
    
    @Predicate(label = "Logo de la société",search = false,target = "image")
     private String image ;
    
     @Predicate(label = "Nom de la socièté" ,optional = false,unique = true,search = true)
     private String code ;
     
     @Predicate(label = "Slogan de la socièté",search = true)
     private String intitule;
     
     @Predicate(label = "Adresse",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales",search = true)
     private String adresse;
     
     @Predicate(label = "Téléphone",target = "tel",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales",search = true)
     private String telephone;    
     
     @Predicate(label = "Ville",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales")
     private String ville;
     
     @Predicate(label = "Fax" , target = "tel",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales")
     private String fax ;
     
     @Predicate(label = "Code Postal",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales",search = true)
     private String codePostal;
     
     @Predicate(label = "Courriel",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales",search = true)
     private String courriel ;
     
     @Predicate(label = "Pays" , type = Pays.class,group = true,groupName = "infosgenerales",groupLabel = "Informations Générales")
     @ManyToOne
     @JoinColumn(name = "PAYS_ID")
     private Pays pays ;
     
     @Predicate(label = "Numéro fiscal",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales")
     private String numFiscal ;
     
     @Predicate(label = "Site Web",target = "url",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales")
     private String siteWeb ;
     
     @Predicate(label = "Registre du commerce",group = true,groupName = "infosgenerales",groupLabel = "Informations Générales")
     private String registre ;
     
     @Predicate(label = "Devise" ,type = Devise.class,group = true,groupName = "infosgenerales",groupLabel = "Informations Générales")
     @ManyToOne
     @JoinColumn(name = "DEV_ID")
     private Devise devise ;
     
    @Predicate(label = "Socièté mère" , type = Societe.class,target="many-to-one")
    @ManyToOne
    @JoinColumn(name = "SOCP_ID")
     private Societe societeMere ;

     
     /**
      * 
      */
    public Societe() {
    }

    /**
     * 
     * @param image
     * @param code
     * @param designation
     * @param adresse
     * @param telephone
     * @param ville
     * @param fax
     * @param codePostal
     * @param courriel
     * @param pays
     * @param numFiscal
     * @param siteWeb
     * @param registre
     * @param devise
     * @param societeMere 
     */
    public Societe(String image, String code, String designation, String adresse, String telephone, String ville, String fax, String codePostal, String courriel, Pays pays, String numFiscal, String siteWeb, String registre, Devise devise, Societe societeMere) {
        this.image = image;
        this.code = code;
        this.intitule = designation;
        this.adresse = adresse;
        this.telephone = telephone;
        this.ville = ville;
        this.fax = fax;
        this.codePostal = codePostal;
        this.courriel = courriel;
        this.pays = pays;
        this.numFiscal = numFiscal;
        this.siteWeb = siteWeb;
        this.registre = registre;
        this.devise = devise;
        //this.societeMere = societeMere;
    }

   
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDesignation() {
        return code;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public String getNumFiscal() {
        return numFiscal;
    }

    public void setNumFiscal(String numFiscal) {
        this.numFiscal = numFiscal;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getRegistre() {
        return registre;
    }

    public void setRegistre(String registre) {
        this.registre = registre;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Societe getSocieteMere() {
        return societeMere;
    }

    public void setSocieteMere(Societe societeMere) {
        this.societeMere = societeMere;
    }

   
    
    

//    public Societe getSocieteMere() {
//        return societeMere;
//    }
//
//    public void setSocieteMere(Societe societeMere) {
//        this.societeMere = societeMere;
//    }

    @Override
    public String getListTitle() {
        return "SOCIETES"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEditTitle() {
        return "SOCIETE"; //To change body of generated methods, choose Tools | Templates.
    }
     
     @Override
    public String getModuleName() {
         //To change body of generated methods, choose Tools | Templates.
        return "kerencore";
    }
    
    

    @Override
    public boolean isCreateonfield() {
        return false; //To change body of generated methods, choose Tools | Templates.
    }
    
    
     
    @Override
    public int compareTo(Societe o) {
         //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }
    
}
