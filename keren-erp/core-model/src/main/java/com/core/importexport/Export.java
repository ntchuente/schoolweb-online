/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.importexport;

import com.core.base.BaseElement;
import com.megatim.common.annotations.Predicate;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author BEKO
 */
@Entity
@Table(name = "T_EXPORT")
public class Export extends BaseElement implements Serializable,Comparable<Export>{

    @Predicate(label = "Repertoire de sauvegarde",optional = false,nullable = false,search = true)
    private String code ;

    @Predicate(label = "Tous les (en jours)",type = Short.class,optional = false,nullable = false,search = true)
    private Short cycle ;
    
    @Predicate(label = "Heure",target = "time",optional = false,nullable = false,search = true)
    private String heure ;
    
    @Predicate(label = "Prochaine Execution",type = Date.class,target = "date",updatable = false,editable = false,search = true)
    @Temporal(TemporalType.DATE)
    private Date execDay ;
    
    public Export() {
    }

    public Export(long id, String designation, String moduleName, long comparedid) {
        super(id, designation, moduleName, comparedid);
    }
    
    /**
     * 
     * @param export 
     */
    public Export(Export export) {
        super(export.id, export.designation, export.moduleName, export.compareid);
        this.code = export.code;
        this.cycle = export.cycle;
        this.heure = export.heure;
        this.execDay = export.execDay;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getCycle() {
        return cycle;
    }

    public void setCycle(Short cycle) {
        this.cycle = cycle;  }

    

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public Date getExecDay() {
        return execDay;
    }

    public void setExecDay(Date execDay) {
        this.execDay = execDay;
    }
    
    
    
    
    @Override
    public int compareTo(Export o) {
        //To change body of generated methods, choose Tools | Templates.
        return code.compareTo(o.code);
    }

    @Override
    public String getEditTitle() {
        return "Exporter la Base de données"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
