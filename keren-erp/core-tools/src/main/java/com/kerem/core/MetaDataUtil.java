/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kerem.core;

import com.core.base.BaseElement;
import com.core.base.State;
import com.kerem.commons.KerenSession;
import com.megatim.common.annotations.Filter;
import com.megatim.common.annotations.Observer;
import com.megatim.common.annotations.Predicate;
import com.megatim.common.annotations.TableFooter;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaArray;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaColumn;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaGroup;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Commercial_2
 */
public class MetaDataUtil {
    
    private static  Map<String,MetaData>  sharedCache = new HashMap<String,MetaData>();
    /**
     * 
     * @return 
     */
    public static Map<String,MetaData> shareCache(){
          return sharedCache;   
    }
    
    /**
     * 
     */
    public static void resetShareCache(){
        sharedCache.clear();
    }
    
    /**
     * 
     * @param key
     * @param meta 
     */
    public static void put(String key , MetaData meta){
        sharedCache.put(key, meta);
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    public static boolean containKey(String key){
        return sharedCache.containsKey(key);
    }
    
    
    
    /**
     * 
     * @param meta
     * @param fields
     * @return 
     */
    public static MetaData getMetaData(MetaData meta , List<com.kerem.genarated.Field> fields){
        if(meta==null) return null;
        MetaData metaData = new MetaData();
        metaData.setEditTitle(meta.getEditTitle());
        metaData.setListTitle(meta.getListTitle());
        metaData.setEntityName(meta.getEntityName());
        metaData.setModuleName(meta.getModuleName());
        metaData.setCreateonfield(meta.isCreateonfield()); 
        metaData.setDesablecreate(meta.isDesablecreate());
        metaData.setDesabledelete(meta.isDesabledelete());
        metaData.setDesableupdate(meta.isDesableupdate());
        metaData.setActivefilelink(meta.isActivefilelink());
        metaData.setStates(meta.getStates());
        metaData.setActivatefollower(meta.isActivatefollower());
        metaData.setSearchfields(meta.getSearchfields());
        metaData.setClassName(meta.getClassName());
//        metaData.setCustomfooter(meta.isCustomfooter());
        metaData.setFooterScript(meta.getFooterScript());
        HashMap<String,com.kerem.genarated.Field> map = new HashMap<String,com.kerem.genarated.Field>();
        for(com.kerem.genarated.Field field : fields){
            map.put(field.getName(), field);
        }
        //Copie des columns
        if(meta.getColumns()!=null){
            for(MetaColumn col:meta.getColumns()){
                if(map.containsKey(col.getFieldName())){
                    metaData.getColumns().add(col);
                }
            }//end for(MetaColumn col:meta.getColumns())
        }//end if(meta.getColumns()!=null)
        //Traitement des groupes
        if(meta.getGroups()!=null){
            for(MetaGroup grp : meta.getGroups()){
                if(grp.getColumns()!=null){
                     for(MetaColumn col:grp.getColumns()){
                        if(map.containsKey(col.getFieldName())){
                            metaData.getColumns().add(col);
                        }
                    }//end for(MetaColumn col:meta.getColumns())
                }
            }//end for(MetaGroup grp : meta.getGroups())
        }//end if(meta.getGroups()!=null){
        return metaData;
    }
    
    
    /**
     * 
     * @param obj
     * @param shareCache
     * @param exclures
     * @return 
     * @throws java.lang.InstantiationException 
     * @throws java.lang.IllegalAccessException 
     */
    public static MetaData getMetaData(Object obj,Map<String , MetaData> shareCache,List<String> exclures) throws InstantiationException, IllegalAccessException{
        List metaDoublons = new ArrayList();
        //Si on a deja traite cete entite
//        if(shareCache.containsKey(obj.getClass().toString())){
////            System.out.println("public static MetaData getMetaData(Object obj,Map<Class<?> , MetaData> shareCache) ========= "+obj.getClass().toString());
//            return shareCache.get(obj.getClass().toString());
//        }
        MetaData metaData = new MetaData();
        metaData.setEditTitle(((BaseElement)obj).getEditTitle());
        if(KerenSession.containKey(((BaseElement)obj).getEditTitle())){
            metaData.setEditTitle(KerenSession.getEntry(((BaseElement)obj).getEditTitle()));
        }//end if(KerenSession.containKey(((BaseElement)obj).getEditTitle()))
        metaData.setListTitle(((BaseElement)obj).getListTitle());
        if(KerenSession.containKey(((BaseElement)obj).getListTitle())){
            metaData.setListTitle(KerenSession.getEntry(((BaseElement)obj).getListTitle()));
        }//end if(KerenSession.containKey(((BaseElement)obj).getListTitle())){
        metaData.setEntityName(obj.getClass().getSimpleName());
        metaData.setModuleName(((BaseElement)obj).getModuleName());
        metaData.setCreateonfield(((BaseElement)obj).isCreateonfield()); 
        metaData.setDesablecreate(((BaseElement)obj).isDesablecreate());
        metaData.setDesabledelete(((BaseElement)obj).isDesabledelete());
        metaData.setDesableupdate(((BaseElement)obj).isDesableupdate());
        metaData.setActivefilelink(((BaseElement)obj).isActivefilelien());
        metaData.setFooterScript(((BaseElement)obj).getFooterScript());
        metaData.setActivatefollower(((BaseElement)obj).isActivatefollower());
        metaData.setSearchfields(((BaseElement)obj).searchFields());
        metaData.setClassName(obj.getClass().getName());
        //Creation des etates
        List<com.megatimgroup.generic.jax.rs.layer.impl.State> states = new ArrayList<>();
        for(State state : ((BaseElement)obj).getStates()){
            states.add(new com.megatimgroup.generic.jax.rs.layer.impl.State(state.getCode(), state.getIntitule()));
        }//end for(State state : ((BaseElement)obj).getStates())
        metaData.setStates(states);        
        //Mise a jour ShareClass
//        shareCache.put(obj.getClass().toString(), metaData);
         //System.out.println(MetaDataUtil.class.toString()+" ===================== "+shareCache.keySet().size()+" ==== "+obj.getClass().toString());
        
        //Liste des champs disponible
        List<Field> fields = new ArrayList<Field>();
        Field[] fields_0 = obj.getClass().getSuperclass().getDeclaredFields();
        fields.addAll(Arrays.asList(fields_0)); //end for(Field f : fields_0){
        Field[] fields_1 =  obj.getClass().getDeclaredFields();
        fields.addAll(Arrays.asList(fields_1)); //end for(Field f : fields_1)
        //Traitement des donnees
        Map<String , List<Field>> groups = new HashMap<String , List<Field>>();
        List<Field> columns = new ArrayList<Field>();
        
        for(Field field : fields){
            //On verifie que le champs est annote
            Predicate annot = field.getAnnotation(Predicate.class);      
            if(annot!=null){                
                if(!annot.group()){
                    columns.add(field);
                }else if(!annot.groupName().trim().isEmpty()){
                    if(groups.get(annot.groupName())==null){
                        groups.put(annot.groupName(), new ArrayList<Field>());
                    }//end if(groups.get(annot.groupName())==null)
                    groups.get(annot.groupName()).add(field);
                }//end if(!annot.group()){        
            }//end if(annot!=null){
        } //end for(Field field : fields){       
                       
        //Costruction des champs
        if(!columns.isEmpty()){
            for(Field field : columns){
                Predicate annot = field.getAnnotation(Predicate.class); 
                TableFooter annot2 = field.getAnnotation(TableFooter.class);
                Observer annot3 = field.getAnnotation(Observer.class);
                if(field.getType().equals(String.class)){   
                       if(annot.target().equals("combobox")){
                          String label = annot.label();
                          if(KerenSession.containKey(annot.label())){
                              label = KerenSession.getEntry(label);
                          }//end if(KerenSession.containKey(annot.label())){
                          MetaColumn column = new MetaColumn(annot.target(), field.getName(), label,annot.search(), null, null);
                          column.setValue(annot.values());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());column.setEditable(annot.editable());
                          column.setOptional(annot.optional());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());//column.setPattern(annot.pattern());
                          column.setHide(annot.hide());column.setCustomfooter(annot.customfooter());column.setCompute(annot.compute());
                          column.setHidden(annot.hidden());column.setObservable(annot.observable());column.setFrozen(annot.frozen());
                          column.setImportfield(annot.importfield());
                          if(annot3!=null){
                              column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                          }//end if(annot3!=null)
                          metaData.getColumns().add(column);        
                       }else{
                            String label = annot.label();
                           if(KerenSession.containKey(annot.label())){
                               label = KerenSession.getEntry(label);
                           }//end if(KerenSession.containKey(annot.label())){
                           MetaColumn column = new MetaColumn(annot.target(), field.getName(), label,annot.search(), null, null);
                          column.setOptional(annot.optional());column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                          column.setUnique(annot.unique());column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());
                          column.setColsequence(annot.colsequence());column.setImportfield(annot.importfield());
                          column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                          column.setCompute(annot.compute());column.setHidden(annot.hidden());column.setFrozen(annot.frozen());
                          if(annot3!=null){
                              column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                          }//end if(annot3!=null)
                          metaData.getColumns().add(column);        
                       }                               
                }else if(field.getType().equals(Date.class)){
                          String label = annot.label();
                          if(KerenSession.containKey(annot.label())){
                              label = KerenSession.getEntry(label);
                          }//end if(KerenSession.containKey(annot.label())){
                          MetaColumn column = new MetaColumn(annot.target(), field.getName(), label,annot.search(), null, null);
                          column.setValue(annot.values());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());
                          column.setOptional(annot.optional());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                          column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                          column.setCompute(annot.compute());column.setHidden(annot.hidden());column.setFrozen(annot.frozen());
                          column.setImportfield(annot.importfield());
                          if(annot3!=null){
                              column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                          }//end if(annot3!=null)
                          metaData.getColumns().add(column);
                }else if(field.getType().equals(Double.class)||annot.type().equals(Float.class)||annot.type().equals(Short.class)
                        ||annot.type().equals(BigDecimal.class)||annot.type().equals(Integer.class)||
                        annot.type().equals(Long.class)){
                    String label = annot.label();
                    if(KerenSession.containKey(annot.label())){
                        label = KerenSession.getEntry(label);
                    }//end if(KerenSession.containKey(annot.label())){
                    MetaColumn column = new MetaColumn("number", field.getName(),label,annot.search(), null, null);
                    column.setOptional(annot.optional());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                    column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                    column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());column.setHidden(annot.hidden());
                    column.setCompute(annot.compute());column.setValue(annot.values());column.setFrozen(annot.frozen());
                    column.setImportfield(annot.importfield());
                    if(annot3!=null){
                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                    }//end if(annot3!=null)
                    metaData.getColumns().add(column);
                }else if(field.getType().equals(Boolean.class)){
                    String label = annot.label();
                    if(KerenSession.containKey(annot.label())){
                        label = KerenSession.getEntry(label);
                    }//end if(KerenSession.containKey(annot.label())){
                    MetaColumn column = new MetaColumn("boolean", field.getName(), label,annot.search(), null, null);
                    column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                    column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                    column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());column.setImportfield(annot.importfield());
                    column.setCompute(annot.compute());column.setHidden(annot.hidden());column.setFrozen(annot.frozen());
                    if(annot3!=null){
                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                    }//end if(annot3!=null)
                    metaData.getColumns().add(column);
                }else if(field.getType().equals(List.class)){
                    MetaData meta = null;
//                    boolean doulons = false ;
//                    if(shareCache.containsKey(annot.type().getClass().toString())){
//                        meta = shareCache.get(annot.type().getClass().toString());
//                        exclures.add(annot.type().getClass().toString());
//                        doulons = true;
//                    }//end //end 
////                    meta = getMetaData(annot.type().newInstance(),shareCache,exclures);
                    
                    /**if(!shareCache.containsKey(annot.type().getClass().toString()))**/{
                    if(field.isAnnotationPresent(ManyToMany.class)){
                        String label = annot.label();
                        if(KerenSession.containKey(annot.label())){
                            label = KerenSession.getEntry(label);
                        }//end if(KerenSession.containKey(annot.label())){
                        MetaColumn column = new MetaColumn("array", field.getName(), label ,annot.search(),"many-to-many", meta);
                        if(annot.target()=="many-to-many-list"){
                            column = new MetaColumn("array", field.getName(), label,annot.search(),"many-to-many-list", meta);
                        }//end if(annot.target()=="many-to-many-list")                        
                        column.setHide(annot.hide());column.setEditable(annot.editable());column.setUpdatable(annot.updatable()); 
                        String[] searchfields = annot.searchfields().split(",");
                        column.setSearchfields(searchfields);column.setCustomfooter(annot.customfooter());
                        if(column.isCustomfooter()&&annot2!=null){
                            column.setFooterScript(annot2.value());
                        }//end if(column.isCustomfooter()&&annot2!=null)
                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName()); 
                        //Traitement des filter
                        if(field.isAnnotationPresent(Filter.class)){
                            Filter filter = field.getAnnotation(Filter.class);
                            column.setFilter(filter.value());
                        }//end if(field.isAnnotationPresent(Filter.class)){
                        /*if(!exclures.contains(keybuilder.toString())){  */                         
                            metaData.getColumns().add(column);
                        /*}else{
                            continue;
                        }*/
                        /*if(shareCache.containsKey(annot.type().toString()))*/{
                            //exclures.add(keybuilder.toString());
                            //doulons = true;
                        }//end //end                         
                        column.setMetaData(getMetaDataInner(annot.type().newInstance(),new HashMap<String, MetaData>(),new ArrayList<String>()));
                        column.setSequence(annot.sequence());column.setHidden(annot.hidden());
                        column.setColsequence(annot.colsequence()); column.setFrozen(annot.frozen());
                        if(annot3!=null){
                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                        }//end if(annot3!=null)
                    }else if(field.isAnnotationPresent(OneToMany.class)){
                        String label = annot.label();
                        if(KerenSession.containKey(annot.label())){
                            label = KerenSession.getEntry(label);
                        }//end if(KerenSession.containKey(annot.label())){
                        MetaColumn column = new MetaColumn("array", field.getName(), label,annot.search(),"one-to-many", null);
                       
                        if(annot.target()=="many-to-many-list"){
                            column = new MetaColumn("array", field.getName(), label,annot.search(),"many-to-many-list", meta);
                        }else if(annot.target().trim().equalsIgnoreCase("piece-jointe")){
                            column = new MetaColumn("array", field.getName(), label,annot.search(),"piece-jointe", meta);
                            
                        }//end if(annot.target()=="many-to-many-list")                        
                        column.setHide(annot.hide());column.setEditable(annot.editable());column.setUpdatable(annot.updatable());
                        column.setCustomfooter(annot.customfooter());column.setSequence(annot.sequence());column.setHidden(annot.hidden());
                        String[] searchfields = annot.searchfields().split(",");
                        column.setEdittable(annot.edittable());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                        column.setSearchfields(searchfields);column.setColsequence(annot.colsequence());
                        
                        if(column.isCustomfooter()&&annot2!=null){
                            column.setFooterScript(annot2.value());
                        }//end if(column.isCustomfooter()&&annot2!=null)
                        if(annot3!=null){
                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                        }//end if(annot3!=null)
                        //Traitement des filter
                        if(field.isAnnotationPresent(Filter.class)){
                            Filter filter = field.getAnnotation(Filter.class);
                            column.setFilter(filter.value());
                        }//end if(field.isAnnotationPresent(Filter.class)){
                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName());                            
                        /*if(!exclures.contains(keybuilder.toString())){*/                           
                            metaData.getColumns().add(column);
                        /*}else{
                            continue;
                        }*/
                        /*if(shareCache.containsKey(annot.type().toString()))*/{
                             //exclures.add(keybuilder.toString());
                            //doulons = true;
                        }//end //end                         
                       column.setMetaData(getMetaDataInner(annot.type().newInstance(),new HashMap<String, MetaData>(),new ArrayList<String>()));
                    }
                    }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                }else{
                    /*if(field.isAnnotationPresent(ManyToOne.class))*/{
                        MetaData meta = null;
                        boolean doublons = false ;
//                        if(shareCache.containsKey(annot.type().getClass().toString())){
//                            meta = shareCache.get(annot.type().getClass().toString());
//                            doublons = true;
//                        }else{
//                           meta = getMetaData(annot.type().newInstance(),shareCache);
//                        }//end 
                     /**if(!shareCache.containsKey(field.getType().getClass().toString()))**/{
                        String label = annot.label();
                        if(KerenSession.containKey(annot.label())){
                            label = KerenSession.getEntry(label);
                        }//end if(KerenSession.containKey(annot.label())){
                        MetaColumn column = new MetaColumn("object", field.getName(), label ,annot.search(), "many-to-one", meta);
                        column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                        column.setHide(annot.hide());column.setEditable(annot.editable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                        column.setImportfield(annot.importfield());                        
                        //metaData.getColumns().add(column);
                        String[] searchfields = annot.searchfields().split(",");
                        column.setSearchfields(searchfields);column.setCustomfooter(annot.customfooter());column.setHidden(annot.hidden());
//                        System.out.println(MetaDataUtil.class.toString()+" ==== "+annot.type().toString()+" ==== "+field.getName());
                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName());  
                        if(field.isAnnotationPresent(Filter.class)){
                            Filter filter = field.getAnnotation(Filter.class);
                            column.setFilter(filter.value());
                        }//end if(field.isAnnotationPresent(Filter.class)){
                        column.setObservable(annot.observable());column.setFrozen(annot.frozen());
                        if(annot3!=null){
                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                        }//end if(annot3!=null)
                        /*if(!exclures.contains(keybuilder.toString()))*/{                           
                            metaData.getColumns().add(column);
                            /*if(shareCache.containsKey(annot.type().toString()))*/{
                                 //exclures.add(keybuilder.toString());
                                //doulons = true;
                            }//end //end                       
                            column.setMetaData(getMetaDataInner(annot.type().newInstance(),new HashMap<String, MetaData>(),new ArrayList<String>()));
                        }//end if(!exclures.contains(keybuilder.toString()))                      
                     }//end if(!shareCache.containsKey(annot.type().getClass().toString())){ 
                  }
                }//end if(field.isAnnotationPresent(ManyToOne.class)){
            }
        }//End if(columns
        //Traitement des groups
        if(!groups.isEmpty()){
            //Pour chaque groupe faire
            for(String key : groups.keySet()){
                //Traitement des columns
                if(!groups.get(key).isEmpty()){
                      Predicate annot = groups.get(key).get(0).getAnnotation(Predicate.class);
                      MetaGroup metaGroup = new MetaGroup(annot.groupName(),annot.groupLabel(), null);
                      metaData.getGroups().add(metaGroup);
                      for(Field field : groups.get(key)){
                        annot = field.getAnnotation(Predicate.class);
                        TableFooter annot2 = field.getAnnotation(TableFooter.class);                    
                        Observer annot3 = field.getAnnotation(Observer.class);
                        metaGroup.setSequence(annot.sequence());
                        if(field.getType().equals(String.class)){   
                               if(annot.target().equals("combobox")){
                                   String label = annot.label();
                                    if(KerenSession.containKey(annot.label())){
                                        label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                  MetaColumn column = new MetaColumn(annot.target(), field.getName(), label,annot.search(), annot.target(), null);
                                  column.setValue(annot.values());column.setUpdatable(annot.updatable());column.setFrozen(annot.frozen());
                                  column.setEditable(annot.editable());column.setOptional(annot.optional());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                  column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                  column.setCustomfooter(annot.customfooter());column.setHidden(annot.hidden());
                                  column.setObservable(annot.observable());column.setImportfield(annot.importfield());
                                  if(annot3!=null){
                                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                  }//end if(annot3!=null)
                                  metaGroup.getColumns().add(column);        
                               }else{
                                   String label = annot.label();
                                    if(KerenSession.containKey(annot.label())){
                                        label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                   MetaColumn column = new MetaColumn(annot.target(), field.getName(), label,annot.search(), null, null);
                                   column.setOptional(annot.optional());column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                   column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                   column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                                   column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                                   if(annot3!=null){
                                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                    }//end if(annot3!=null)
                                   metaGroup.getColumns().add(column);        
                               }                               
                        }else if(field.getType().equals(Date.class)){
                                String label = annot.label();
                                if(KerenSession.containKey(annot.label())){
                                    label = KerenSession.getEntry(label);
                                }//end if(KerenSession.containKey(annot.label())){
                                MetaColumn column = new MetaColumn(annot.target(), field.getName(), label,annot.search(), null, null);
                                column.setValue(annot.values());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());
                                column.setOptional(annot.optional());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                                column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                                 if(annot3!=null){
                                    column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                }//end if(annot3!=null)
                                metaGroup.getColumns().add(column); 
                      }else if(field.getType().equals(Double.class)||annot.type().equals(Float.class)||annot.type().equals(Short.class)
                                ||annot.type().equals(BigDecimal.class)||annot.type().equals(Integer.class)||
                                annot.type().equals(Long.class)){
                            String label = annot.label();
                            if(KerenSession.containKey(annot.label())){
                                label = KerenSession.getEntry(label);
                            }//end if(KerenSession.containKey(annot.label())){
                            MetaColumn column = new MetaColumn("number", field.getName(), label ,annot.search(), null, null);
                            column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                            column.setHide(annot.hide());column.setEditable(annot.editable());column.setSequence(annot.sequence());
                            column.setCustomfooter(annot.customfooter());column.setColsequence(annot.colsequence());
                            column.setCompute(annot.compute());column.setValue(annot.values());column.setFrozen(annot.frozen());
                            column.setHidden(annot.hidden());column.setImportfield(annot.importfield());
                            if(annot3!=null){
                                column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                            }//end if(annot3!=null)
                            metaGroup.getColumns().add(column);
                        }else if(field.getType().equals(Boolean.class)){
                            String label = annot.label();
                            if(KerenSession.containKey(annot.label())){
                                label = KerenSession.getEntry(label);
                            }//end if(KerenSession.containKey(annot.label())){
                            MetaColumn column = new MetaColumn("boolean", field.getName(), label ,annot.search(), null, null);
                            column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                            column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                            column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                            if(annot3!=null){
                                column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                            }//end if(annot3!=null)
                            metaGroup.getColumns().add(column);
                        }else if(field.getType().equals(List.class)){
                                MetaData meta = null;
//                                boolean doublons = false;
//                                if(shareCache.containsKey(annot.type().getClass().toString())){
//                                    meta = shareCache.get(annot.type().getClass().toString());
//                                    doublons = true;
//                                }else{
//                                   meta = getMetaData(annot.type().newInstance(),shareCache);
//                                }//end 
                            /**if(!shareCache.containsKey(annot.type().getClass().toString()))**/{
                                if(field.isAnnotationPresent(ManyToMany.class)){
                                     if(annot.target().equalsIgnoreCase("many-to-many-list")){//many-to-many-list
                                         String label = annot.label();
                                         if(KerenSession.containKey(annot.groupLabel())){
                                            label = KerenSession.getEntry(label);
                                         }//end if(KerenSession.containKey(annot.label())){
                                         MetaArray metaArray = new MetaArray("array", field.getName(), label ,annot.search(),annot.target(), meta);
                                         metaArray.setUpdatable(annot.updatable());metaArray.setCustomfooter(annot.customfooter());
                                         String[] searchfields = annot.searchfields().split(",");
                                         metaArray.setSearchfields(searchfields);metaArray.setHidden(annot.hidden());
                                         metaArray.setFrozen(annot.frozen());
                                         if(metaArray.isCustomfooter()&&annot2!=null){
                                            metaArray.setFooterScript(annot2.value());
                                         }//end if(column.isCustomfooter()&&annot2!=null)
                                         if(annot3!=null){
                                            metaArray.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                        }//end if(annot3!=null)
                                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName());                            
                                        /*if(!exclures.contains(keybuilder.toString()))*/{                           
                                            metaGroup.getMetaArray().add(metaArray);
                                            /*if(shareCache.containsKey(annot.type().toString()))*/{
                                                 //exclures.add(keybuilder.toString());
                                                //doulons = true;
                                            }//end //end                                         
//                                            metaArray.setMetaData(getMetaData(annot.type().newInstance(),shareCache,exclures));
                                            metaArray.setMetaData(getMetaDataInner(annot.type().newInstance(),new HashMap<String, MetaData>(),new ArrayList<String>()));
                                        }//end if(!exclures.contains(annot.type().toString())){ 
                                        if(field.isAnnotationPresent(Filter.class)){
                                            Filter filter = field.getAnnotation(Filter.class);
                                            metaArray.setFilter(filter.value());
                                        }//end if(field.isAnnotationPresent(Filter.class)){
                                        //metaGroup.setMetaArray(metaArray);
                                     }else{
                                         String label = annot.label();
                                         if(KerenSession.containKey(annot.label())){
                                            label = KerenSession.getEntry(label);
                                         }//end if(KerenSession.containKey(annot.label())){
                                         MetaColumn column = new MetaColumn("array", field.getName(), label,annot.search(),"many-to-many", meta);                           
                                         column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                         column.setHide(annot.hide());column.setEditable(annot.editable());column.setImportfield(annot.importfield());
                                         column.setCustomfooter(annot.customfooter());column.setFrozen(annot.frozen());
                                         String[] searchfields = annot.searchfields().split(",");
                                         column.setSearchfields(searchfields);column.setHidden(annot.hidden());
                                         if(column.isCustomfooter()&&annot2!=null){
                                            column.setFooterScript(annot2.value());
                                         }//end if(column.isCustomfooter()&&annot2!=null)
                                         if(annot3!=null){
                                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                        }//end if(annot3!=null)
                                         StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                                         keybuilder.append(annot.type().toString());keybuilder.append(field.getName());   
                                         /*if(!exclures.contains(keybuilder.toString()))*/{                           
                                            metaGroup.getColumns().add(column); 
                                            /*if(shareCache.containsKey(annot.type().toString()))*/{                                                                          
                                                 //exclures.add(keybuilder.toString());
                                               //doulons = true;
                                             }//end //end                       
                                             column.setMetaData(getMetaDataInner(annot.type().newInstance(),new HashMap<String, MetaData>(),new ArrayList<String>()));
                                          }//end if(!exclures.contains(annot.type().toString())){         
                                         if(field.isAnnotationPresent(Filter.class)){
                                                Filter filter = field.getAnnotation(Filter.class);
                                            column.setFilter(filter.value());
                                          }//end if(field.isAnnotationPresent(Filter.class)){
                                    }
                                }else if(field.isAnnotationPresent(OneToMany.class)){ 
                                    String label = annot.label();
                                    if(KerenSession.containKey(annot.groupLabel())){
                                        label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                    MetaArray metaArray = new MetaArray("array", field.getName(), label,annot.search(),annot.target(),meta);                                                  
                                    metaArray.setUpdatable(annot.updatable());metaArray.setCustomfooter(annot.customfooter());
                                    metaArray.setEdittable(annot.edittable());metaArray.setFrozen(annot.frozen());
                                    String[] searchfields = annot.searchfields().split(",");
                                    metaArray.setSearchfields(searchfields);metaArray.setHidden(annot.hidden());
                                    if(metaArray.isCustomfooter()&&annot2!=null){
                                        metaArray.setFooterScript(annot2.value());
                                    }//end if(column.isCustomfooter()&&annot2!=null)
                                    if(annot3!=null){
                                        metaArray.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                    }//end if(annot3!=null)
                                    //Traitement des filter
                                    if(field.isAnnotationPresent(Filter.class)){
                                        Filter filter = field.getAnnotation(Filter.class);
                                        metaArray.setFilter(filter.value());
                                    }//end if(field.isAnnotationPresent(Filter.class)){
                                    StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                                    keybuilder.append(annot.type().toString());keybuilder.append(field.getName());  
                                    /*if(!exclures.contains(keybuilder.toString()))*/{                           
                                            metaGroup.getMetaArray().add(metaArray);
                                            /*if(shareCache.containsKey(annot.type().toString()))*/{                                                                           
                                                 //exclures.add(keybuilder.toString());
                                                //doulons = true;
                                            }//end //end 
//                                            System.out.println(MetaDataUtil.class.toString()+" ==== "+annot.type().toString()+" ==== "+field.getName());
//                                            metaArray.setMetaData(getMetaData(annot.type().newInstance(),shareCache,exclures));
                                            metaArray.setMetaData(getMetaDataInner(annot.type().newInstance(),new HashMap<String, MetaData>(),new ArrayList<String>()));
                                    }//end if(!exclures.contains(annot.type().toString())){
                                    //metaGroup.setMetaArray(metaArray); 
                                }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                            }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                        }else{
                            /*if(field.isAnnotationPresent(ManyToOne.class))*/{
//                            System.out.println(MetaDataUtil.class.toString()+" ==== "+annot.type().toString()+" ==== "+field.getName());
                                    MetaData meta = null;                                
                                /*if(!shareCache.containsKey(field.getType().getClass().toString()))*/{
                                    String label = annot.label();
                                    if(KerenSession.containKey(annot.label())){
                                        label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                    MetaColumn column = new MetaColumn("object", field.getName(), label ,annot.search(), "many-to-one", meta);
                                    column.setEditable(annot.editable());column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                    column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                    column.setCustomfooter(annot.customfooter());column.setFrozen(annot.frozen());
                                    column.setImportfield(annot.importfield());
                                    String[] searchfields = annot.searchfields().split(",");
                                    column.setSearchfields(searchfields);column.setHidden(annot.hidden());
                                    StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());    
                                    keybuilder.append(annot.type().toString());keybuilder.append(field.getName());                                    
                                    /*if(!exclures.contains(keybuilder.toString())){ */                          
                                       column.setObservable(annot.observable()); metaGroup.getColumns().add(column); 
                                        if(field.isAnnotationPresent(Filter.class)){
                                            Filter filter = field.getAnnotation(Filter.class);
                                            column.setFilter(filter.value());
                                        }//end if(field.isAnnotationPresent(Filter.class)){
                                        if(annot3!=null){
                                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                        }//end if(annot3!=null)
                                        /*if(shareCache.containsKey(annot.type().toString()))*/{
                                             //exclures.add(keybuilder.toString());
                                           //doulons = true;
                                        }//end //end                       
                                        column.setMetaData(getMetaDataInner(annot.type().newInstance(),new HashMap<String, MetaData>(),new ArrayList<String>()));
                                    /*}else{
                                    }//end if(shareCache.containsKey(annot.type().toString())){*/
                                }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                          }//end if(field.isAnnotationPresent(ManyToOne.class)){
                        }
                      }//end for(Field field : groups.get(key))
                }
            }
        }
        
        return metaData;
    }
    
    
   private  static MetaData getMetaDataInner(Object obj,Map<String , MetaData> shareCache,List<String> exclures) throws InstantiationException, IllegalAccessException{
        List metaDoublons = new ArrayList();
        //Si on a deja traite cete entite
//        if(shareCache.containsKey(obj.getClass().toString())){
////            System.out.println("public static MetaData getMetaData(Object obj,Map<Class<?> , MetaData> shareCache) ========= "+obj.getClass().toString());
//            return shareCache.get(obj.getClass().toString());
//        }
        MetaData metaData = new MetaData();
        metaData.setEditTitle(((BaseElement)obj).getEditTitle());
        if(KerenSession.containKey(((BaseElement)obj).getEditTitle())){
            metaData.setEditTitle(KerenSession.getEntry(((BaseElement)obj).getEditTitle()));
        }//end if(KerenSession.containKey(((BaseElement)obj).getEditTitle()))
        metaData.setListTitle(((BaseElement)obj).getListTitle());
        if(KerenSession.containKey(((BaseElement)obj).getListTitle())){
            metaData.setListTitle(KerenSession.getEntry(((BaseElement)obj).getListTitle()));
        }//end if(KerenSession.containKey(((BaseElement)obj).getListTitle())){
        metaData.setEntityName(obj.getClass().getSimpleName());
        metaData.setModuleName(((BaseElement)obj).getModuleName());
        metaData.setCreateonfield(((BaseElement)obj).isCreateonfield()); 
        metaData.setDesablecreate(((BaseElement)obj).isDesablecreate());
        metaData.setDesabledelete(((BaseElement)obj).isDesabledelete());
        metaData.setDesableupdate(((BaseElement)obj).isDesableupdate());        
        metaData.setActivefilelink(((BaseElement)obj).isActivefilelien());
        metaData.setFooterScript(((BaseElement)obj).getFooterScript());
        metaData.setActivatefollower(((BaseElement)obj).isActivatefollower());
        metaData.setSearchfields(((BaseElement)obj).searchFields());
        metaData.setClassName(obj.getClass().getName());
        //Creation des etates
        List<com.megatimgroup.generic.jax.rs.layer.impl.State> states = new ArrayList<>();
        for(State state : ((BaseElement)obj).getStates()){
            states.add(new com.megatimgroup.generic.jax.rs.layer.impl.State(state.getCode(), state.getIntitule()));
        }//end for(State state : ((BaseElement)obj).getStates())
        metaData.setStates(states);
        
        //Mise a jour ShareClass
//        shareCache.put(obj.getClass().toString(), metaData);
         //System.out.println(MetaDataUtil.class.toString()+" ===================== "+shareCache.keySet().size()+" ==== "+obj.getClass().toString());
        
        //Liste des champs disponible
        List<Field> fields = new ArrayList<Field>();
        Field[] fields_0 = obj.getClass().getSuperclass().getDeclaredFields();
        for(Field f : fields_0){
            fields.add(f);
        }//end for(Field f : fields_0){
        Field[] fields_1 =  obj.getClass().getDeclaredFields();
        for(Field f : fields_1){
            fields.add(f);
        }//end for(Field f : fields_1)
        //Traitement des donnees
        Map<String , List<Field>> groups = new HashMap<String , List<Field>>();
        List<Field> columns = new ArrayList<Field>();
        
        for(Field field : fields){
            //On verifie que le champs est annote
            Predicate annot = field.getAnnotation(Predicate.class);      
            if(annot!=null){                
                if(!annot.group()){
                    columns.add(field);
                }else if(!annot.groupName().trim().isEmpty()){
                    if(groups.get(annot.groupName())==null){
                        groups.put(annot.groupName(), new ArrayList<Field>());
                    }
                    groups.get(annot.groupName()).add(field);
                }//end if(!annot.group()){        
            }//end if(annot!=null){
        } //end for(Field field : fields){       
                       
        //Costruction des champs
        if(!columns.isEmpty()){
            for(Field field : columns){
                Predicate annot = field.getAnnotation(Predicate.class); 
                TableFooter annot2 = field.getAnnotation(TableFooter.class);
                Observer annot3 = field.getAnnotation(Observer.class);
                if(field.getType().equals(String.class)){   
                       if(annot.target().equals("combobox")){
                          String label = annot.label();
                          if(KerenSession.containKey(annot.label())){
                               label = KerenSession.getEntry(label);
                          }//end if(KerenSession.containKey(annot.label())){
                          MetaColumn column = new MetaColumn(annot.target(), field.getName(), label ,annot.search(), null, null);
                          column.setValue(annot.values());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());column.setEditable(annot.editable());
                          column.setOptional(annot.optional());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());//column.setPattern(annot.pattern());
                          column.setHide(annot.hide());column.setCustomfooter(annot.customfooter());column.setHidden(annot.hidden());
                          column.setObservable(annot.observable());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                          if(annot3!=null){
                              column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                          }//end if(annot3!=null)
                          metaData.getColumns().add(column);        
                       }else{
                           String label = annot.label();
                          if(KerenSession.containKey(annot.label())){
                               label = KerenSession.getEntry(label);
                          }//end if(KerenSession.containKey(annot.label())){
                           MetaColumn column = new MetaColumn(annot.target(), field.getName(), label ,annot.search(), null, null);
                          column.setOptional(annot.optional());column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                          column.setUnique(annot.unique());column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                          column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                          column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                          if(annot3!=null){
                              column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                          }//end if(annot3!=null)
                          metaData.getColumns().add(column);        
                       }                               
                }else if(field.getType().equals(Date.class)){
                          String label = annot.label();
                          if(KerenSession.containKey(annot.label())){
                               label = KerenSession.getEntry(label);
                          }//end if(KerenSession.containKey(annot.label())){
                          MetaColumn column = new MetaColumn(annot.target(), field.getName(), label ,annot.search(), null, null);
                          column.setValue(annot.values());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());
                          column.setOptional(annot.optional());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                          column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                          column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                          if(annot3!=null){
                              column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                          }//end if(annot3!=null)
                          metaData.getColumns().add(column);
                }else if(field.getType().equals(Double.class)||annot.type().equals(Float.class)||annot.type().equals(Short.class)
                        ||annot.type().equals(BigDecimal.class)||annot.type().equals(Integer.class)||
                        annot.type().equals(Long.class)){
                    String label = annot.label();
                    if(KerenSession.containKey(annot.label())){
                         label = KerenSession.getEntry(label);
                    }//end if(KerenSession.containKey(annot.label())){
                    MetaColumn column = new MetaColumn("number", field.getName(), label ,annot.search(), null, null);
                    column.setOptional(annot.optional());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                    column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                    column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());column.setCompute(annot.compute());
                    column.setValue(annot.values());column.setHidden(annot.hidden());column.setFrozen(annot.frozen());
                    column.setImportfield(annot.importfield());
                    if(annot3!=null){
                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                    }//end if(annot3!=null)
                    metaData.getColumns().add(column);
                }else if(field.getType().equals(Boolean.class)){
                    String label = annot.label();
                    if(KerenSession.containKey(annot.label())){
                         label = KerenSession.getEntry(label);
                    }//end if(KerenSession.containKey(annot.label())){
                    MetaColumn column = new MetaColumn("boolean", field.getName(), label ,annot.search(), null, null);
                    column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                    column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                    column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                    column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                    if(annot3!=null){
                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                    }//end if(annot3!=null)
                    metaData.getColumns().add(column);
                }else if(field.getType().equals(List.class)){
                    MetaData meta = null;
//                    boolean doulons = false ;
//                    if(shareCache.containsKey(annot.type().getClass().toString())){
//                        meta = shareCache.get(annot.type().getClass().toString());
//                        exclures.add(annot.type().getClass().toString());
//                        doulons = true;
//                    }//end //end 
////                    meta = getMetaData(annot.type().newInstance(),shareCache,exclures);
                    
                    /**if(!shareCache.containsKey(annot.type().getClass().toString()))**/{
                    if(field.isAnnotationPresent(ManyToMany.class)){
                        String label = annot.label();
                        if(KerenSession.containKey(annot.label())){
                             label = KerenSession.getEntry(label);
                        }//end if(KerenSession.containKey(annot.label())){
                        MetaColumn column = new MetaColumn("array", field.getName(), label ,annot.search(),"many-to-many", meta);
                        if(annot.target()=="many-to-many-list"){
                            column = new MetaColumn("array", field.getName(), annot.label(),annot.search(),"many-to-many-list", meta);
                        }//end if(annot.target()=="many-to-many-list"){                        
                        column.setHide(annot.hide());column.setEditable(annot.editable());
                        column.setUpdatable(annot.updatable()); column.setFrozen(annot.frozen());
                        column.setImportfield(annot.importfield());
                        String[] searchfields = annot.searchfields().split(",");
                        column.setSearchfields(searchfields);column.setCustomfooter(annot.customfooter());
                        if(column.isCustomfooter()&&annot2!=null){
                            column.setFooterScript(annot2.value());
                        }//end if(column.isCustomfooter()&&annot2!=null)
                        if(annot3!=null){
                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                        }//end if(annot3!=null)
                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName());                      
                        if(!exclures.contains(keybuilder.toString())){                           
                            metaData.getColumns().add(column);
                        }else{
                            continue;
                        }
                        /*if(shareCache.containsKey(annot.type().toString()))*/{
                            exclures.add(keybuilder.toString());
                            //doulons = true;
                        }//end //end       
                        if(field.isAnnotationPresent(Filter.class)){
                            Filter filter = field.getAnnotation(Filter.class);
                            column.setFilter(filter.value());
                        }//end if(field.isAnnotationPresent(Filter.class)){
                        column.setMetaData(getMetaDataInner(annot.type().newInstance(),shareCache,exclures));
                        column.setSequence(annot.sequence());
                        column.setColsequence(annot.colsequence()); column.setHidden(annot.hidden());
                    }else if(field.isAnnotationPresent(OneToMany.class)){
                        //System.out.println("===================================================="+annot.type().newInstance());
                        String label = annot.label();
                        if(KerenSession.containKey(annot.label())){
                             label = KerenSession.getEntry(label);
                        }//end if(KerenSession.containKey(annot.label())){
                        MetaColumn column = new MetaColumn("array", field.getName(), label ,annot.search(),"one-to-many", null);
                        column.setHide(annot.hide());column.setEditable(annot.editable());column.setUpdatable(annot.updatable());
                        column.setCustomfooter(annot.customfooter());column.setSequence(annot.sequence());column.setHidden(annot.hidden());
                        column.setEdittable(annot.edittable());column.setFrozen(annot.frozen());
                        column.setImportfield(annot.importfield());
                        String[] searchfields = annot.searchfields().split(",");
                        column.setSearchfields(searchfields);column.setColsequence(annot.colsequence());
                        if(column.isCustomfooter()&&annot2!=null){
                            column.setFooterScript(annot2.value());
                        }//end if(column.isCustomfooter()&&annot2!=null)
                        if(annot3!=null){
                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                        }//end if(annot3!=null)
                        if(field.isAnnotationPresent(Filter.class)){
                            Filter filter = field.getAnnotation(Filter.class);
                            column.setFilter(filter.value());
                        }//end if(field.isAnnotationPresent(Filter.class)){
                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName());                            
                        if(!exclures.contains(keybuilder.toString())){                           
                            metaData.getColumns().add(column);
                        }else{
                            continue;
                        }
                        /*if(shareCache.containsKey(annot.type().toString()))*/{
                             exclures.add(keybuilder.toString());
                            //doulons = true;
                        }//end //end                         
                        column.setMetaData(getMetaDataInner(annot.type().newInstance(),shareCache,exclures));
                    }
                    }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                }else{
                    /*if(field.isAnnotationPresent(ManyToOne.class))*/{
                        MetaData meta = null;
                        boolean doublons = false ;
//                        if(shareCache.containsKey(annot.type().getClass().toString())){
//                            meta = shareCache.get(annot.type().getClass().toString());
//                            doublons = true;
//                        }else{
//                           meta = getMetaData(annot.type().newInstance(),shareCache);
//                        }//end 
                     /**if(!shareCache.containsKey(field.getType().getClass().toString()))**/{
                        String label = annot.label();
                        if(KerenSession.containKey(annot.label())){
                             label = KerenSession.getEntry(label);
                        }//end if(KerenSession.containKey(annot.label())){
                        MetaColumn column = new MetaColumn("object", field.getName(), label,annot.search(), "many-to-one", meta);
                        column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                        column.setHide(annot.hide());column.setEditable(annot.editable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                        column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                        String[] searchfields = annot.searchfields().split(",");
                        column.setSearchfields(searchfields);column.setCustomfooter(annot.customfooter());
                        column.setHidden(annot.hidden());column.setObservable(annot.observable());
//                        System.out.println(MetaDataUtil.class.toString()+" ==== "+annot.type().toString()+" ==== "+field.getName());
                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName());
                        if(field.isAnnotationPresent(Filter.class)){
                            Filter filter = field.getAnnotation(Filter.class);
                            column.setFilter(filter.value());
                        }//end if(field.isAnnotationPresent(Filter.class)){
                        if(annot3!=null){
                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                        }//end if(annot3!=null)
                        if(!exclures.contains(keybuilder.toString())){                           
                            metaData.getColumns().add(column);
                            /*if(shareCache.containsKey(annot.type().toString()))*/{
                                 exclures.add(keybuilder.toString());
                                //doulons = true;
                            }//end //end                       
                            column.setMetaData(getMetaDataInner(annot.type().newInstance(),shareCache,exclures));
                        }//end if(!exclures.contains(keybuilder.toString()))                      
                     }//end if(!shareCache.containsKey(annot.type().getClass().toString())){ 
                  }
                }//end if(field.isAnnotationPresent(ManyToOne.class)){
            }
        }//End if(columns
        //Traitement des groups
        if(!groups.isEmpty()){
            //Pour chaque groupe faire
            for(String key : groups.keySet()){
                //Traitement des columns
                if(!groups.get(key).isEmpty()){
                      Predicate annot = groups.get(key).get(0).getAnnotation(Predicate.class);
                      MetaGroup metaGroup = new MetaGroup(annot.groupName(),annot.groupLabel(), null);
                      metaData.getGroups().add(metaGroup);
                      for(Field field : groups.get(key)){
                        annot = field.getAnnotation(Predicate.class);
                        TableFooter annot2 = field.getAnnotation(TableFooter.class); 
                        Observer annot3 = field.getAnnotation(Observer.class);
                        metaGroup.setSequence(annot.sequence());
                        if(field.getType().equals(String.class)){   
                               if(annot.target().equals("combobox")){
                                   String label = annot.label();
                                    if(KerenSession.containKey(annot.label())){
                                         label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                  MetaColumn column = new MetaColumn(annot.target(), field.getName(), label,annot.search(), annot.target(), null);
                                  column.setValue(annot.values());column.setUpdatable(annot.updatable());
                                  column.setEditable(annot.editable());column.setOptional(annot.optional());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                  column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                  column.setCustomfooter(annot.customfooter());column.setHidden(annot.hidden());
                                  column.setObservable(annot.observable());column.setFrozen(annot.frozen());
                                  column.setImportfield(annot.importfield());
                                  if(annot3!=null){
                                    column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                  }//end if(annot3!=null)
                                  metaGroup.getColumns().add(column);        
                               }else{
                                   String label = annot.label();
                                    if(KerenSession.containKey(annot.label())){
                                         label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                   MetaColumn column = new MetaColumn(annot.target(), field.getName(), label ,annot.search(), null, null);
                                   column.setOptional(annot.optional());column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                   column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                   column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                                   column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                                   if(annot3!=null){
                                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                    }//end if(annot3!=null)
                                   metaGroup.getColumns().add(column);        
                               }                               
                        }else if(field.getType().equals(Date.class)){
                                String label = annot.label();
                                if(KerenSession.containKey(annot.label())){
                                   label = KerenSession.getEntry(label);
                                }//end if(KerenSession.containKey(annot.label())){
                                MetaColumn column = new MetaColumn(annot.target(), field.getName(), label ,annot.search(), null, null);
                                column.setValue(annot.values());column.setUnique(annot.unique());column.setUpdatable(annot.updatable());
                                column.setOptional(annot.optional());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                                column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                                if(annot3!=null){
                                    column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                }//end if(annot3!=null)
                                metaGroup.getColumns().add(column); 
                      }else if(field.getType().equals(Double.class)||annot.type().equals(Float.class)||annot.type().equals(Short.class)
                                ||annot.type().equals(BigDecimal.class)||annot.type().equals(Integer.class)||
                                annot.type().equals(Long.class)){
                            String label = annot.label();
                            if(KerenSession.containKey(annot.label())){
                                 label = KerenSession.getEntry(label);
                            }//end if(KerenSession.containKey(annot.label())){
                            MetaColumn column = new MetaColumn("number", field.getName(), label,annot.search(), null, null);
                            column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                            column.setHide(annot.hide());column.setEditable(annot.editable());column.setSequence(annot.sequence());
                            column.setCustomfooter(annot.customfooter());column.setColsequence(annot.colsequence());
                            column.setCompute(annot.compute());column.setValue(annot.values());column.setImportfield(annot.importfield());
                            column.setHidden(annot.hidden());column.setFrozen(annot.frozen());
                            if(annot3!=null){
                                column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                            }//end if(annot3!=null)
                            metaGroup.getColumns().add(column);
                        }else if(field.getType().equals(Boolean.class)){
                            String label = annot.label();
                            if(KerenSession.containKey(annot.label())){
                                 label = KerenSession.getEntry(label);
                            }//end if(KerenSession.containKey(annot.label())){
                            MetaColumn column = new MetaColumn("boolean", field.getName(), label ,annot.search(), null, null);
                            column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                            column.setHide(annot.hide());column.setEditable(annot.editable());column.setCustomfooter(annot.customfooter());
                            column.setHidden(annot.hidden());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                            if(annot3!=null){
                                column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                            }//end if(annot3!=null)
                            metaGroup.getColumns().add(column);
                        }else if(field.getType().equals(List.class)){
                                MetaData meta = null;
//                                boolean doublons = false;
//                                if(shareCache.containsKey(annot.type().getClass().toString())){
//                                    meta = shareCache.get(annot.type().getClass().toString());
//                                    doublons = true;
//                                }else{
//                                   meta = getMetaData(annot.type().newInstance(),shareCache);
//                                }//end 
                            /**if(!shareCache.containsKey(annot.type().getClass().toString()))**/{
                                if(field.isAnnotationPresent(ManyToMany.class)){
                                     if(annot.target().equalsIgnoreCase("many-to-many-list")){//many-to-many-list
                                         String label = annot.groupLabel();
                                        if(KerenSession.containKey(annot.groupLabel())){
                                             label = KerenSession.getEntry(label);
                                        }//end if(KerenSession.containKey(annot.label())){
                                         MetaArray metaArray = new MetaArray("array", field.getName(), label ,annot.search(),annot.target(), meta);
                                         metaArray.setUpdatable(annot.updatable());metaArray.setCustomfooter(annot.customfooter());
                                         String[] searchfields = annot.searchfields().split(",");
                                         metaArray.setSearchfields(searchfields);metaArray.setHidden(annot.hidden());
                                         metaArray.setFrozen(annot.frozen());
                                         if(metaArray.isCustomfooter()&&annot2!=null){
                                            metaArray.setFooterScript(annot2.value());
                                         }//end if(column.isCustomfooter()&&annot2!=null)
                                        if(annot3!=null){
                                            metaArray.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                        }//end if(annot3!=null)
                                        StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                                        keybuilder.append(annot.type().toString());keybuilder.append(field.getName());                            
                                        if(!exclures.contains(keybuilder.toString())){                           
                                            metaGroup.getMetaArray().add(metaArray);
                                            /*if(shareCache.containsKey(annot.type().toString()))*/{
                                                 exclures.add(keybuilder.toString());
                                                //doulons = true;
                                            }//end //end                                         
                                            metaArray.setMetaData(getMetaDataInner(annot.type().newInstance(),shareCache,exclures));
                                        }//end if(!exclures.contains(annot.type().toString())){ 
                                        if(field.isAnnotationPresent(Filter.class)){
                                            Filter filter = field.getAnnotation(Filter.class);
                                            metaArray.setFilter(filter.value());
                                        }//end if(field.isAnnotationPresent(Filter.class)){
                                        //metaGroup.setMetaArray(metaArray);
                                     }else{
                                         String label = annot.label();
                                         if(KerenSession.containKey(annot.label())){
                                             label = KerenSession.getEntry(label);
                                         }//end if(KerenSession.containKey(annot.label())){
                                         MetaColumn column = new MetaColumn("array", field.getName(), label ,annot.search(),"many-to-many", meta);                           
                                         column.setUpdatable(annot.updatable());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                         column.setHide(annot.hide());column.setEditable(annot.editable());column.setFrozen(annot.frozen());
                                         column.setCustomfooter(annot.customfooter());column.setHidden(annot.hidden());column.setImportfield(annot.importfield());
                                         String[] searchfields = annot.searchfields().split(",");
                                         column.setSearchfields(searchfields);
                                         if(column.isCustomfooter()&&annot2!=null){
                                            column.setFooterScript(annot2.value());
                                         }//end if(column.isCustomfooter()&&annot2!=null)
                                         StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                                         keybuilder.append(annot.type().toString());keybuilder.append(field.getName());   
                                         if(!exclures.contains(keybuilder.toString())){                           
                                            metaGroup.getColumns().add(column); 
                                            /*if(shareCache.containsKey(annot.type().toString()))*/{                                                                          
                                                 exclures.add(keybuilder.toString());
                                               //doulons = true;
                                             }//end //end                       
                                             column.setMetaData(getMetaDataInner(annot.type().newInstance(),shareCache,exclures));
                                          }//end if(!exclures.contains(annot.type().toString())){ 
                                         if(field.isAnnotationPresent(Filter.class)){
                                            Filter filter = field.getAnnotation(Filter.class);
                                            column.setFilter(filter.value());
                                        }//end if(field.isAnnotationPresent(Filter.class)){
                                        if(annot3!=null){
                                            column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                        }//end if(annot3!=null)
                                    }
                                }else if(field.isAnnotationPresent(OneToMany.class)){    
                                    String label = annot.groupLabel();
                                    if(KerenSession.containKey(annot.groupLabel())){
                                         label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                    MetaArray metaArray = new MetaArray("array", field.getName(), label ,annot.search(),annot.target(),meta);
                                    metaArray.setUpdatable(annot.updatable());metaArray.setCustomfooter(annot.customfooter());
                                    String[] searchfields = annot.searchfields().split(",");
                                    metaArray.setEdittable(annot.edittable());metaArray.setFrozen(annot.frozen());
                                    metaArray.setSearchfields(searchfields);metaArray.setHidden(annot.hidden());
                                    if(metaArray.isCustomfooter()&&annot2!=null){
                                        metaArray.setFooterScript(annot2.value());
                                    }//end if(column.isCustomfooter()&&annot2!=null)
                                    if(annot3!=null){
                                        metaArray.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                    }//end if(annot3!=null)
                                    if(field.isAnnotationPresent(Filter.class)){
                                        Filter filter = field.getAnnotation(Filter.class);
                                        metaArray.setFilter(filter.value());
                                    }//end if(field.isAnnotationPresent(Filter.class)){
                                    StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());
                                    keybuilder.append(annot.type().toString());keybuilder.append(field.getName());  
                                    if(!exclures.contains(keybuilder.toString())){                           
                                            metaGroup.getMetaArray().add(metaArray);
                                            /*if(shareCache.containsKey(annot.type().toString()))*/{                                                                           
                                                 exclures.add(keybuilder.toString());
                                                //doulons = true;
                                            }//end //end 
//                                            System.out.println(MetaDataUtil.class.toString()+" ==== "+annot.type().toString()+" ==== "+field.getName());
                                            metaArray.setMetaData(getMetaDataInner(annot.type().newInstance(),shareCache,exclures));
                                    }//end if(!exclures.contains(annot.type().toString())){
                                    //metaGroup.setMetaArray(metaArray); 
                                }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                            }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                        }else{
                            /*if(field.isAnnotationPresent(ManyToOne.class))*/{
//                            System.out.println(MetaDataUtil.class.toString()+" ==== "+annot.type().toString()+" ==== "+field.getName());
                                    MetaData meta = null;                                
                                /*if(!shareCache.containsKey(field.getType().getClass().toString()))*/{
                                     String label = annot.label();
                                    if(KerenSession.containKey(annot.label())){
                                         label = KerenSession.getEntry(label);
                                    }//end if(KerenSession.containKey(annot.label())){
                                    MetaColumn column = new MetaColumn("object", field.getName(), label ,annot.search(), "many-to-one", meta);
                                    column.setEditable(annot.editable());column.setOptional(annot.optional());column.setUpdatable(annot.updatable());//column.setMin(annot.min());column.setMax(annot.max());column.setPattern(annot.pattern());
                                    column.setHide(annot.hide());column.setSequence(annot.sequence());column.setColsequence(annot.colsequence());
                                    column.setCustomfooter(annot.customfooter());column.setFrozen(annot.frozen());column.setImportfield(annot.importfield());
                                    String[] searchfields = annot.searchfields().split(",");
                                    column.setSearchfields(searchfields);column.setHidden(annot.hidden());
                                    StringBuilder keybuilder = new StringBuilder(obj.getClass().toString());    
                                    keybuilder.append(annot.type().toString());keybuilder.append(field.getName());                                    
                                    if(!exclures.contains(keybuilder.toString())){                           
                                        metaGroup.getColumns().add(column); 
                                        /*if(shareCache.containsKey(annot.type().toString()))*/{
                                             exclures.add(keybuilder.toString());
                                           //doulons = true;
                                        }//end //end                       
                                        column.setMetaData(getMetaDataInner(annot.type().newInstance(),shareCache,exclures));
                                    }else{
                                    }//end if(shareCache.containsKey(annot.type().toString())){
                                    if(field.isAnnotationPresent(Filter.class)){
                                        Filter filter = field.getAnnotation(Filter.class);
                                        column.setFilter(filter.value());
                                    }//end if(field.isAnnotationPresent(Filter.class)){
                                    column.setObservable(annot.observable());
                                    if(annot3!=null){
                                        column.setObserver(new com.megatimgroup.generic.jax.rs.layer.impl.Observer(annot3.observable(), annot3.source(),annot3.parameters()));
                                    }//end if(annot3!=null)
                                }//end if(!shareCache.containsKey(annot.type().getClass().toString()))
                          }//end if(field.isAnnotationPresent(ManyToOne.class)){
                        }
                      }//end for(Field field : groups.get(key))
                }
            }
        }
        
        return metaData;
    }
    
}
