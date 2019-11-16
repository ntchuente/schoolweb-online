/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.dashboard;

import com.core.base.BaseElement;
import java.io.Serializable;

/**
 *
 * @author Commercial_2
 */
public class DashboardField extends BaseElement implements Serializable,Comparable<DashboardField>{

    private String fieldName ;
    
    private double fieldValue ;
    
    private String fieldLabel; 
    
    private String entity ;
    
    private String model ;
    
    private String method ;

    private boolean activalink = false;
    /**
     * 
     * @param fieldName
     * @param fieldValue
     * @param fieldLabel 
     */
    public DashboardField(String fieldName, double fieldValue, String fieldLabel) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.fieldLabel = fieldLabel;
    }

    
    /**
     * 
     * @param fieldName
     * @param fieldValue
     * @param fieldLabel
     * @param id
     * @param designation
     * @param moduleName 
     */
    public DashboardField(String fieldName, double fieldValue, String fieldLabel, long id, String designation, String moduleName) {
        super(id, designation, moduleName,0L);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.fieldLabel = fieldLabel;
    }

    public DashboardField() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public double getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(double fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    
    
    @Override
    public int compareTo(DashboardField o) {
        return fieldName.compareTo(o.fieldName); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isActivalink() {
        return activalink;
    }

    public void setActivalink(boolean activalink) {
        this.activalink = activalink;
    }
    
}
