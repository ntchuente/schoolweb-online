/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kerem.core;

import com.core.base.BaseElement;
import com.core.dashboard.DashboardContainer;
import com.core.dashboard.DashboardEntry;
import com.core.dashboard.DashboardField;
import com.core.views.DashboardRecord;
import com.kerem.genarated.Dashboard;
import com.kerem.genarated.Dashboardentry;
import com.megatim.common.annotations.Predicate;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Commercial_2
 */
public class DashboardUtil {
    
    /**
     * 
     * @param data
     * @param template
     * @return 
     * @throws javax.xml.bind.JAXBException 
     * @throws java.lang.IllegalAccessException 
     */
    public static DashboardContainer  dashboardBuilder(Object data , DashboardRecord template) throws JAXBException, IllegalArgumentException, IllegalAccessException{
        if(data==null || template==null){
            return null;
        }//end if(data==null || template==null)
        DashboardContainer container = new DashboardContainer();
        //Jaxb conversion
        com.kerem.genarated.DashboardRecord record = FileHelper.transformScriptToDashboard(template.getScript());
        //Construction du map contenant les champs de data
        Field[] fields = CommonTools.getClassFields(data.getClass());
//        System.out.println(DashboardUtil.class.toString()+" ====== "+fields);
        //Map de données
        Map<String,Field> dataMap = new HashMap<String, Field>();
        for(Field field:fields){
            field.setAccessible(true);
            dataMap.put(field.getName(), field);
        }//end for(Field field:fields){
        //Construction du dashbord containercontainer.setActivatefollower(true);
        container.setLabel(record.getLabel());container.setModuleName(record.getModelRef());
        container.setMethod(record.getMethodRef());container.setEntity(record.getEntityRef());
        for(Dashboard dash:record.getDashboard()){
            com.core.dashboard.Dashboard dashboard = new com.core.dashboard.Dashboard();
            dashboard.setCode(dash.getId());dashboard.setLabel(dash.getLabel());
            container.getDashboards().add(dashboard);
            //Construction des entrees du dashboard
            for(Dashboardentry ent:dash.getDashboardentry()){
                if(ent.getType()==null||ent.getType().trim().isEmpty()){
                    continue;
                }//end if(ent.getType()==null||ent.getType().trim().isEmpty())
                DashboardEntry entry = new DashboardEntry(ent.getId(), ent.getType(),ent.getLabel());
                entry.setTempate(ent.getTemplate());entry.setModel(ent.getModelRef());
                entry.setEntity(ent.getEntityRef());entry.setMethod(ent.getMethodRef());
               //Traitement des champs
                for(com.kerem.genarated.Field field:ent.getField()){
                    DashboardField champ = new DashboardField();
                    champ.setFieldName(field.getName());champ.setEntity(field.getEntityRef());
                    champ.setActivalink(true);champ.setMethod(field.getMethodRef());
                    champ.setModel(field.getModelRef());
                    if(field.getEntityRef()==null||field.getEntityRef().trim().isEmpty()
                            ||field.getMethodRef()==null||field.getMethodRef().trim().isEmpty()
                            ||field.getModelRef()==null||field.getModelRef().trim().isEmpty()){
                        champ.setActivalink(false);
                    }//end if(field.getEntityRef()==null||field.getEntityRef().trim().isEmpty()
                    Field j_field = dataMap.get(field.getName());                    
                    j_field.setAccessible(true);
                    if(j_field!=null){
                        Predicate annot = j_field.getAnnotation(Predicate.class);
                        if(annot!=null){
                            champ.setFieldLabel(annot.label());
                        }
                        champ.setFieldValue(j_field.getDouble(data));
                    }//end if(j_field!=null)
                    entry.getFields().add(champ);
                }//end for(com.kerem.genarated.Field field:ent.getField())
                
                String[] types = ent.getType().split(",");
                short i =1 ;
                for(String type:types){
                    DashboardEntry entr = new DashboardEntry(entry);
                    entr.setType(type);entr.setCode(entr.getCode()+""+i);
                    entr.setLabel(entr.getLabel());
                    if(entr.getLabel()==null||entr.getLabel().trim().isEmpty()){
                        if(type.equalsIgnoreCase("bar")){
                            entr.setLabel("Histogramme");
                        }else if(type.equalsIgnoreCase("pie")){
                            entr.setLabel("Camenbart");
                        }else if(type.equalsIgnoreCase("line")){
                            entr.setLabel("Courbe");
                        }//end if(type.equalsIgnoreCase("bar")){
                    }//end if(entr.getLabel()==null||entr.getLabel().trim().isEmpty()){
//                    System.out.println(DashboardUtil.class.toString()+" === "+type+" === "+entr.getLabel());
                     dashboard.getEntries().add(entr); 
                     i++;
                }//end for(String type:types)
            }//end for(DashboardEntry entry:dash.getDashboardentry())
            
        }//end for(Dashboard dash:record.getDashboard())
        container.setEditTitle(((BaseElement)data).getEditTitle());
        container.setListTitle(((BaseElement)data).getListTitle());
        container.setDesignation(((BaseElement)data).getDesignation());
        return  container;
    }
}
