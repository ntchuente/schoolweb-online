/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kerem.core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.core.menus.ActionItem;
import com.core.menus.MenuAction;
import com.core.menus.MenuGroupActions;
import com.core.views.CalendarRecord;
import com.core.views.DashboardRecord;
import com.core.views.FormRecord;
import com.core.views.ReportRecord;
import com.core.views.TreeRecord;
import com.kerem.genarated.Action;
import com.kerem.genarated.Button;
import com.kerem.genarated.Field;
import com.kerem.genarated.Group;
import com.kerem.genarated.Menu;
import com.kerem.genarated.Menuitem;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaArray;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaColumn;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaData;
import com.megatimgroup.generic.jax.rs.layer.impl.MetaGroup;

/**
 *
 * @author Commercial_2
 */
public class CommonTools {
    
    /**
     * 
     * @param item
     * @return 
     */
    public static MenuAction getMenuAction(Menuitem item){
        MenuAction action = new MenuAction();
        action.setName(item.getId());
        action.setLabel(item.getLabel());
        action.setEntityName(item.getEntityRef());
        action.setModel(item.getModelRef());        
        action.setLink(item.getLink());
        action.setReport(item.getReport());
        action.setHide(item.isHide());
        action.setModal(item.isModal());
        action.setIcon(item.getGyphycon());
        action.setViewMode(item.getViewType());
        action.setMethod(item.getMethodRef());
        List<ActionItem> actions = new ArrayList<ActionItem>();      
        //Creation des actions
        if(item.getAction()!=null){
            for(Action act:item.getAction()){
                ActionItem data = new ActionItem(act.getId(),act.getType(), act.getLabel(), act.getValue());
//                data.set
                actions.add(data);
            }
        }
        action.setActions(actions);
        //this.treeView = treeView;
        return action;
    }
    
    /**
     * 
     * @param item
     * @return 
     */
    public static MenuGroupActions getMenuGroupActions(Menu item){
        MenuGroupActions menu = new MenuGroupActions();
        menu.setIcon(item.getGyphycon());
        menu.setName(item.getId());
        menu.setLabel(item.getLabel());
        //menu.getM = module;
        return menu;
    }
    
    /**
     * 
     * @param view
     * @return 
     * @throws javax.xml.bind.JAXBException 
     */
    public static FormRecord getFormView(com.kerem.genarated.FormRecord view) throws JAXBException{
        FormRecord record = new FormRecord();
        record.setCode(view.getId());
        record.setScript(FileHelper.transformJaxBToScript(view));
        record.setTitre(view.getLabel());
        return record;
    }
    
    /**
     * Construction de la vue dashbord
     * @param view
     * @param record
     * @return 
     * @throws javax.xml.bind.JAXBException 
     */
    public static DashboardRecord getDashboard(com.kerem.genarated.DashboardRecord view) throws JAXBException{
        DashboardRecord record = new DashboardRecord();
        record.setCode(view.getId());
        record.setScript(FileHelper.transformJaxBToScript(view));
        record.setTitre(view.getLabel());
        return record;
    }
    
    /**
     *  
     * @param view
     * @return 
     * @throws javax.xml.parsers.ParserConfigurationException 
     * @throws org.xml.sax.SAXException 
     * @throws java.io.IOException 
     */
    public static ReportRecord getReportView(com.kerem.genarated.ReportRecord view) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException, JAXBException {
        ReportRecord record = new ReportRecord();
        record.setCode(view.getId());
        record.setTitre(view.getLabel());
        record.setExtern(view.isExtern());
        if(view.getSearch()!=null){
            record.setEntity(view.getSearch().getEntity());
            record.setModel(view.getSearch().getModule());
            record.setMethod(view.getSearch().getMethod());
            record.setSearch(FileHelper.transformJaxBToScript(view.getSearch()));
            record.setClazz(view.getSearch().getClazz());
            record.setIgnore(view.getSearch().isIgnore());
        }//end if(view.getSearch()!=null)        
//        System.out.println(CommonTools.class.toString()+" ========================================= "+view.getTemplate());
        if(view.getTemplate()!=null&&!view.getTemplate().trim().isEmpty()){
            DocumentBuilderFactory odbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder odb = odbf.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(view.getTemplate().getBytes(StandardCharsets.UTF_8.name()));
            Document odoc = odb.parse(stream);
    //        DOMParser parser = new DOMParser();
    //        parser.parse(view.getTemplate());
    //        Document document = parser.getDocument();
//            StringWriter xmlWriter = new StringWriter() ;
//            XMLSerializer ser = new XMLSerializer(xmlWriter,
//              new OutputFormat("xml", "UTF-8", true));
//            ser.serialize(odoc);
            DOMSource domSource = new DOMSource(odoc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            record.setScript(writer.toString());
        }//end if(view.getTemplate()!=null&&!view.getTemplate().trim().isEmpty()){
        return record;
    }
    
    /**
     * 
     * @param view
     * @return
     * @throws JAXBException 
     */
    public static CalendarRecord getFormView(com.kerem.genarated.CalendarRecord view) throws JAXBException{
        CalendarRecord record = new CalendarRecord();
        record.setTitlefield(view.getTitlefield());record.setStartfield(view.getStartfield());
        record.setEndfield(view.getEndfield());record.setAllday(view.isAllday());
        record.setCode(view.getId());
        record.setScript(FileHelper.transformJaxBToScript(view));
        record.setTitre(view.getLabel());
        return record;
    }
    
    /**
     * 
     * @param view
     * @return
     * @throws JAXBException 
     */
    public static TreeRecord getFormView(com.kerem.genarated.TreeRecord view) throws JAXBException{
        TreeRecord record = new TreeRecord();
        record.setCode(view.getId());
        record.setScript(FileHelper.transformJaxBToScript(view));
        record.setTemplate(view.getTemplate());
        record.setTitre(view.getLabel());
        return record;
    }
    
    /**
     * 
     * @param metaData
     * @return 
     */
    private static Map<String , Object> metaDataMapBuilder(MetaData metaData){
        Map<String , Object> map = new HashMap<String , Object>();
        //Traitement des colonnes
        if(metaData.getColumns()!=null){
            for(MetaColumn col : metaData.getColumns()){
                map.put(col.getFieldName(), col);
            }
        }//end  if(metaData.getColumns()!=null)
        //Cas des groups
        if(metaData.getGroups()!=null){
            for(MetaGroup group:metaData.getGroups()){
                if(group.getColumns()!=null){
                    for(MetaColumn col : group.getColumns()){
                        map.put(col.getFieldName(), col);
                    }//end for(MetaColumn col : group.getColumns())
                }//end if(group.getColumns()!=null) 
                if(group.getMetaArray()!=null&&group.getMetaArray().size()>0){
                    for(MetaArray metaArray:group.getMetaArray()){
                        map.put(metaArray.getFieldName(), metaArray);
                    }//end for(MetaArray metaArray:group.getMetaArray())
                }
            }
        }//end if(metaData.getGroups()!=null){
        return map;
    }
    
   
    /**
     * 
     * @param entity
     * @param form
     * @param tree
     * @return 
     * @throws java.lang.InstantiationException 
     * @throws java.lang.IllegalAccessException 
     * @throws javax.xml.bind.JAXBException 
     */
    public static MetaData xmlViewParser( Class<?> entity , FormRecord form , TreeRecord tree) throws InstantiationException, IllegalAccessException, JAXBException{
        MetaData metadata = MetaDataUtil.getMetaData(entity.newInstance(), new HashMap<String, MetaData>(),new ArrayList<String>());
        
        if((form==null)&&(tree==null)){
            return metadata;
        }
        MetaData result = new MetaData();
        Map map = metaDataMapBuilder(metadata);
        //Traitement Form 
       if(form!=null&&form.getScript()!=null&&!form.getScript().trim().isEmpty()){
           result.setEntityName(metadata.getEntityName());
           result.setModuleName(metadata.getModuleName());
           result.setEditTitle(metadata.getEditTitle());
           result.setListTitle(metadata.getListTitle());
           result.setCreateonfield(metadata.isCreateonfield());
           result.setDesablecreate(metadata.isDesablecreate());
           result.setStates(metadata.getStates());
//           result.setCustomfooter(metadata.isCustomfooter());
           result.setFooterScript(metadata.getFooterScript());
           com.kerem.genarated.FormRecord record = FileHelper.transformScriptToForm(form.getScript());
           
           if(record.getLabel()!=null&&!record.getLabel().trim().isEmpty()){
               result.setEditTitle(record.getLabel());
           }//end if(record.getLabel()!=null&&!record.getLabel().trim().isEmpty())
           //Traitement des entetes du formulaire
           if(record.getHeader()!=null){
               //Traitements de buttons
               if(record.getHeader().getButton()!=null){
                   short seq = 1 ;
                   for(Button button : record.getHeader().getButton()){
                       MetaColumn column = new MetaColumn("button", button.getName(), button.getLabel(), false, button.getType(), null);
//                       column.setType("button");
//                       column.setSearch(false);
                       column.setPattern(button.getClazz());
//                       column.setTarget(button.getType());
//                       column.setFieldName(button.getName());
//                       column.setFieldLabel(button.getLabel());
                       column.setValue(button.getValue());
//                       column.setMetaData(null);
                       column.setSequence(seq);
                       String[] states = null;
                       if(button.getStates()!=null){
                           states = button.getStates().split(",");
                       }//end if(button.getStates()!=null){
                       column.setStates(states);
                       seq++;
//                       System.out.println(CommonTools.class.toString()+" ======================= "+button.getStates()+" === "+states);
                       result.getHeader().add(column);
                   }//end for(Button button : record.getHeader().getButton())
               }//end if(record.getHeader().getButton()!=null)
               if(record.getHeader().getField()!=null){
                    MetaColumn column = new MetaColumn("workflow", record.getHeader().getField().getName(), "State", false, record.getHeader().getField().getTarget(), null);
//                    column.setType();
//                    column.setTarget(record.getHeader().getField().getTarget());
//                    column.setSearch(false);
//                    column.setTarget(record.getHeader().getField().getTarget());
                    result.getHeader().add(column);
                    //column.setFieldName(button.getName());
                    //column.setFieldLabel(button.getLabel());
               }//end if(record.getHeader().getField()!=null)
           }//end if(record.getHeader()!=null)
           //Cas du body
           if(record.getSheet()!=null){
               if(record.getSheet().getField()!=null){
                   short sequence = 1;
                   for(Field field : record.getSheet().getField()){
                       if(field.getName()!=null&&!field.getName().trim().isEmpty()){
                           MetaColumn column = (MetaColumn) map.get(field.getName());
                           column.setSequence(sequence);
                           column.setSearch(false);
                           if(field.getTarget()!=null&&!field.getTarget().trim().isEmpty()){
                               column.setTarget(field.getTarget());
                           }
                           result.getColumns().add(column);
                           sequence ++ ;
                       }//end if(field.getName()!=null&&!field.getName().trim().isEmpty())
                   }//end for(Field field : record.getSheet().getField())
                   if(record.getSheet().getGroup()!=null){
                       for(Group group : record.getSheet().getGroup()){
                           short i = 1 ;
                           if(group.getName()!=null&&!group.getName().trim().isEmpty()){
                               MetaGroup groupe = new MetaGroup();
                               groupe.setGroupName(group.getName());
                               groupe.setGroupLabel(group.getLabel());
                               groupe.setSequence(i);
                               //Traitement des champs
                               for(Field field : group.getField()){
                                  Object data = (MetaColumn) map.get(field.getName());
                                  if(data instanceof MetaColumn){
                                        MetaColumn column = (MetaColumn) data ;
                                        column.setSequence(sequence);
                                        column.setSearch(false);
                                        if(field.getTarget()!=null&&!field.getTarget().trim().isEmpty()){
                                            column.setTarget(field.getTarget());
                                        }
                                        groupe.getColumns().add(column);
                                  }else if(data instanceof MetaArray){
                                      MetaArray column = (MetaArray) data;
                                      //column.setSequence(sequence);
                                      column.setSearch(false);
                                      if(field.getTarget()!=null&&!field.getTarget().trim().isEmpty()){
                                          column.setTarget(field.getTarget());
                                      }
                                      groupe.getMetaArray().add(column);
                                  }
                                    result.getGroups().add(groupe);
                                    sequence++;
                               }
                               i++;
                           }//end if(group.getName()!=null&&!group.getName().trim().isEmpty())
                       }
                   }//end if(record.getSheet().getGroup()!=null)
               }//end if(record.getSheet().getField()!=null)
           }//end if(record.getSheet()!=null)
       }else{
              result = metadata;
       }//end if(form!=null&&!form.trim().isEmpty())
        //Traitement des tree
       if(tree!=null&&tree.getScript()!=null&&!tree.getScript().trim().isEmpty()){
           com.kerem.genarated.TreeRecord record = FileHelper.transformScriptToTree(tree.getScript());
           if(record.getLabel()!=null&&!record.getLabel().trim().isEmpty()){
               result.setListTitle(record.getLabel());
           }
           short colsequence = 1 ;
           for(Field field : record.getField()){
              if(field.getName()!=null&&!field.getName().trim().isEmpty()){
                  Object data = map.get(field.getName());
                  if(data instanceof MetaColumn){
                      MetaColumn column = (MetaColumn) data ;
                      column.setColsequence(colsequence);
                      column.setSearch(true);
                  }
                  colsequence++;
              }
           }//end for(Field field : record.getField())
       }//end if(tree!=null&&!tree.trim().isEmpty())
       return result;
    }
    /**
     * Return  PDF file
     * @param file
     * @param filename
     * @return
     * @throws FileNotFoundException 
     */
    public static Response getPdf(File file,String filename) throws FileNotFoundException{
        FileInputStream fileInputStream = new FileInputStream(file);
        Response.ResponseBuilder responseBuilder = Response.ok((Object)fileInputStream);
        responseBuilder.type("application/pdf");
        responseBuilder.header("Content-Disposition", "attachment; filename="+filename);
        return responseBuilder.build();
    }
    
    /**
     * Return  PDF file
     * @param file
     * @param filename
     * @return
     * @throws FileNotFoundException 
     */
    public static Response getText(File file,String filename) throws FileNotFoundException{
        String[] names = file.getName().split(".");
        FileInputStream fileInputStream = new FileInputStream(file);
        Response.ResponseBuilder responseBuilder = Response.ok((Object)fileInputStream);
        responseBuilder.type("text/plain");
        responseBuilder.header("Content-Disposition", "attachment; filename="+filename);
        return responseBuilder.build();
    }
    
    /**
     * 
     * @param file
     * @param filename
     * @return
     * @throws FileNotFoundException 
     */
     public static Response getStream(File file,String filename) throws FileNotFoundException{
        String[] names = file.getName().split(".");
        FileInputStream fileInputStream = new FileInputStream(file);
        Response.ResponseBuilder responseBuilder = Response.ok((Object)fileInputStream);
//        responseBuilder.type("text/plain");
        responseBuilder.header("Content-Disposition", "attachment; filename="+filename);
        return responseBuilder.build();
    }
    /**
     * 
     * @param file
     * @param filename
     * @return
     * @throws IOException 
     */
    public static Response getImage(File file,String filename) throws IOException{
        String[] names = file.getName().split(".");
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageData = baos.toByteArray();
        Response.ResponseBuilder responseBuilder = Response.ok(imageData);
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        responseBuilder.cacheControl(cc);
        responseBuilder.type("image/png");
        responseBuilder.header("Content-Disposition", "attachment; filename="+filename);
        return responseBuilder.build();
    }
    
    /**
     * 
     * @param file
     * @param filename
     * @return
     * @throws IOException 
     */
    public static Response getImage(File file) throws IOException{
        String[] names = file.getName().split(".");
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageData = baos.toByteArray();
        Response.ResponseBuilder responseBuilder = Response.ok(imageData);
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        responseBuilder.cacheControl(cc);        
        return responseBuilder.build();
    }
    
    /**
     * 
     * @param data
     * @return 
     */
    public static java.lang.reflect.Field[] getClassFields(Class clazz){
        List<java.lang.reflect.Field> fields = new ArrayList<java.lang.reflect.Field>();        
        if(!clazz.getSuperclass().equals(Object.class)){
            java.lang.reflect.Field[] champs = getClassFields(clazz.getSuperclass());
            if(champs!=null&&champs.length>0){
                fields.addAll(Arrays.asList(champs));
            } //end for(Field field:champs){
        }//end if(!clazz.getSuperclass().equals(Object.class))
        java.lang.reflect.Field[] champs = clazz.getDeclaredFields();
        fields.addAll(Arrays.asList(champs)); //end for(Field field:champs){
        if(fields.size()>0){
            java.lang.reflect.Field[] result = new java.lang.reflect.Field[fields.size()];
            int i=0;
            for(java.lang.reflect.Field field:fields){
                result[i] = field;
                i++;
            }//end for(Field field:fields)
            return result;
        }//end if(fields.size()>0){
        return null;
    }
    
    
}
