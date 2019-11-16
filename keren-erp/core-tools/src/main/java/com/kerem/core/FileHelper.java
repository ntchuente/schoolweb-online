/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kerem.core;

import com.core.application.Manifest;
import com.core.menus.MenuModule;
import com.google.gson.Gson;
import com.kerem.genarated.CalendarRecord;
import com.kerem.genarated.DashboardRecord;
import com.kerem.genarated.FormRecord;
import com.kerem.genarated.Keren;
import com.kerem.genarated.Search;
import com.kerem.genarated.TreeRecord;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Commercial_2
 */
public class FileHelper {
    public static String ADDONS_PATH = "addons";
    public static String MANIFEST_FILE ="manifest.json";
    public static String DEPLOY_DIR = "standalone"+File.separator+"deployments";
    public static String TEMP_REPORT_DIR = "standalone"+File.separator+"data"+File.separator+"keren"+File.separator+"resources"+File.separator+"templates";
    public static String TEMP_STATIC_DIR = "standalone"+File.separator+"data"+File.separator+"keren"+File.separator+"resources"+File.separator+"static";
    public static String TEMP_CONFIG_DIR = "standalone"+File.separator+"data"+File.separator+"keren"+File.separator+"resources"+File.separator+"config";
    public static String TEMP_DIR = "standalone"+File.separator+"tmp"+File.separator+"keren";
    /**
     * Retourn le chemin courant
     * @return 
     */
    public static File getCurrentDirectory(){
        String fileName = (String) System.getProperties().get("user.dir") ;
        return new File(fileName);
    }
    
    /**
     * Deplacement de fichiers et laisse une copie du fichier
     * @param from
     * @param to 
     * @throws java.io.IOException 
     */
    public static void moveFile(File from , File to) throws IOException{
        Files.move(Paths.get(from.toURI()), Paths.get(to.toURI()),StandardCopyOption.REPLACE_EXISTING);
    }
    
    /**
     * Retourne le repertoires contenant ù
     * les modules
     * @return 
     */
    public static File getAddonsDirectory(){
        File binDirectory = FileHelper.getCurrentDirectory();        
        return new File(binDirectory.getParent()+File.separator+ADDONS_PATH);
    }
    
    /**
     * Chemin du repertoire des etats
     * @return 
     */
    public static File getReportsDirectory(){
         File binDirectory = FileHelper.getCurrentDirectory();        
         return new File(binDirectory.getParent()+File.separator+TEMP_REPORT_DIR);
    }
    
    /**
     * Chemin du repertoire des etats
     * @return 
     */
    public static File getStaticDirectory(){
         File binDirectory = FileHelper.getCurrentDirectory();        
         return new File(binDirectory.getParent()+File.separator+TEMP_STATIC_DIR);
    }
    
    public static File getConfigDirectory(){
         File binDirectory = FileHelper.getCurrentDirectory();        
         return new File(binDirectory.getParent()+File.separator+TEMP_CONFIG_DIR);
    }
    
    public static File getTemporalDirectory(){
         File binDirectory = FileHelper.getCurrentDirectory();     
         File file = new File(binDirectory.getParent()+File.separator+TEMP_DIR);
         if(!file.exists()){
             file.mkdir();
         }//end if(!file.exists())
         return file;
    }
    
    /**
     * 
     * @return 
     */
    public static File getDeployDirectory(){
        File binDirectory = FileHelper.getCurrentDirectory();        
        return new File(binDirectory.getParent()+File.separator+DEPLOY_DIR);
    }
    
    /**
     * Liste des Repertoires du repertoires des modules
     * @return 
     */
    public static List<File> getAddonsSubDirectories(){
        List<File> results = new ArrayList<File>();
        File[] directories =  FileHelper.getAddonsDirectory().listFiles();
        for(File fich : directories){
            if(fich.isDirectory()){
                results.add(fich);
            }
        }
        return results;
    }
    
    /**
     * 
     * @param directory
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public static Manifest getManifestFromDirectory(File directory) throws FileNotFoundException, IOException{
        String ligne ;
        Gson gson = new Gson();
        if(directory==null || !directory.isDirectory()) return null;
       for(File fich : directory.listFiles()){
           if(fich.isFile()&&fich.getName().compareToIgnoreCase(MANIFEST_FILE)==0){
               BufferedReader reader = new BufferedReader(new FileReader(fich));
               StringBuilder builder = new StringBuilder();
               ligne = reader.readLine();
               while(ligne!=null){
                   builder.append(ligne);
                   ligne = reader.readLine();
               }
               Manifest manifest = gson.fromJson(builder.toString(), Manifest.class);
               manifest.setFilename(fich.getParentFile().getName());
               //System.out.println("FileHelper.public static Manifest getManifestFromDirectory(File directory) throws FileNotFoundException, IOException == \n"+manifest);               
               return manifest;
           }
       }
       return null;
    }
    
    /**
     * Retourn la listes des modules disponibles
     * dans le repertoires 
     * @return 
     * @throws java.io.IOException 
     */
    public static List<Manifest> getAvailableModules() throws IOException{
        List<File> modules = FileHelper.getAddonsSubDirectories();
        List<Manifest> manifests = new ArrayList<Manifest>();
        for(File rep : modules){
            manifests.add(getManifestFromDirectory(rep));
        }
        return manifests;
    }
    
     /**
     * Retourn Le manifest correspondant a un module
     * dans le repertoires 
     * @param mod
     * @return 
     * @throws java.io.IOException 
     */
    public static Manifest getManifest(MenuModule mod) throws IOException{
        List<File> modules = FileHelper.getAddonsSubDirectories();       
        for(File rep : modules){
            Manifest manif = getManifestFromDirectory(rep);
            manif.setFilename(rep.getName());
            if(manif.getFilename().trim().equalsIgnoreCase(mod.getName().trim())){
                return manif;
            }
        }
        return null;
    }
    
     /**
     * Retourn Le manifest correspondant a un module
     * dans le repertoires 
     * @param moduleName
     * @param mod
     * @return 
     * @throws java.io.IOException 
     */
    public static Manifest getManifest(String moduleName) throws IOException{
        List<File> modules = FileHelper.getAddonsSubDirectories();       
        for(File rep : modules){
            Manifest manif = getManifestFromDirectory(rep);
            manif.setFilename(rep.getParentFile().getName());
            if(manif.getName().trim().equalsIgnoreCase(moduleName)){
                return manif;
            }
        }
        return null;
    }
    /**
     * Retourne un entite keren a partir d'un fichier xml decrivant la vue
     * @param file
     * @return 
     * @throws javax.xml.bind.JAXBException 
     */
    public static Keren getXmlToEntities(File file) throws JAXBException{
        if(file==null) return null;
        JAXBContext jc = JAXBContext.newInstance(Keren.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Keren data = (Keren) unmarshaller.unmarshal(file);
        //System.out.println(FileHelper.class.toString()+".Keren getXmlToEntities(File file) === "+file.exists()+" == "+data);
        return data;
    }
    
    
    
    /**
     * 
     * @param module
     * @return 
     * @throws java.io.IOException 
     * @throws javax.xml.bind.JAXBException 
     */
    public static List<Keren> getViews(MenuModule module) throws IOException, JAXBException{  
        List<Keren> datas = new ArrayList<Keren>();
        //Cas module null
        if(module==null) return datas;
        //Le module est non null
        Manifest manif = getManifest(module);
        if(manif.getViews()!=null){
            for(String view : manif.getViews()){
                String path = getAddonsDirectory().getPath()+File.separator+module.getName()+File.separator+view;
                File file =new File(path);
                if(file.exists()){
                    Keren data = getXmlToEntities(file);
                    datas.add(data);                     
                }//end if(file.exists()){               
            }//end for(String view : manif.getViews())
        }//end if(manif.getViews()!=null){     
        return datas;
    }
    
    /**
     * Traitement des fichieers pour le reporting
     * Deplacement de ces fichiers vers le reportoire de reportings
     * @param manifest
     * @throws IOException 
     */
    public static  void processReporting(Manifest manifest)  throws IOException{
         if(manifest==null) return ;        
        //verification que le manifest reference un fichier
        for(String report : manifest.getReports()){
            String path = getAddonsDirectory().getPath()+File.separator+manifest.getFilename()+File.separator+report;
            File source = new File(path);
            if(source.exists()){
                createDirectory(getReportsDirectory()+File.separator+manifest.getFilename());
                File cible = new File(getReportsDirectory()+File.separator+manifest.getFilename()+File.separator+source.getName());
                moveFile(source, cible);
            }//end if(source.exists()){
        }//end for(String core : manifest.getCores()){
    }
    
    /**
     * 
     * @param directory
     * @throws IOException 
     */
    private static void createDirectory(String directory) throws IOException{
        File fichier = new File(directory);
        if(!fichier.exists()){
            fichier.mkdir();
        }//end if(!fichier.exists()){
    }
    
    /**
     * Transfert des images dans le repertoires en question
     * @param manifest
     * @throws IOException 
     */
    public static void processImages(Manifest manifest)  throws IOException{
        if(manifest==null) return ;        
        //verification que le manifest reference un fichier
        for(String image : manifest.getImages()){
            String path = getAddonsDirectory().getPath()+File.separator+manifest.getFilename()+File.separator+image;
            File source = new File(path);
            if(source.exists()){
                createDirectory(getStaticDirectory()+File.separator+manifest.getFilename());
                File cible = new File(getStaticDirectory()+File.separator+manifest.getFilename()+File.separator+source.getName());
                moveFile(source, cible);
            }//end if(source.exists()){
        }//end for(String core : manifest.getCores()){
    }
    /**
     * 
     * @param manifest 
     * @throws java.io.IOException 
     */
    public static void processCore(Manifest manifest) throws IOException{
        if(manifest==null) return ;        
        //verification que le manifest reference un fichier
        for(String core : manifest.getCores()){
            String path = getAddonsDirectory().getPath()+File.separator+manifest.getFilename()+File.separator+core;
            File source = new File(path);
            if(source.exists()){
                File cible = new File(getDeployDirectory()+File.separator+core);
                moveFile(source, cible);
            }//end if(source.exists()){
        }//end for(String core : manifest.getCores()){
    }
    
    /**
     * 
     * @param view
     * @return
     * @throws JAXBException 
     */
    public static String transformJaxBToScript(FormRecord view) throws JAXBException{
        StringWriter writer = new StringWriter();
        String output = "";
        JAXBContext ctx = JAXBContext.newInstance(FormRecord.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(view,writer);
        output = writer.toString();
        return output;
    }
    
     public static String transformJaxBToScript(DashboardRecord view) throws JAXBException{
        StringWriter writer = new StringWriter();
        String output = "";
        JAXBContext ctx = JAXBContext.newInstance(DashboardRecord.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(view,writer);
        output = writer.toString();
        return output;
    }
     
     public static String transformJaxBToScript(CalendarRecord view) throws JAXBException{
        StringWriter writer = new StringWriter();
        String output = "";
        JAXBContext ctx = JAXBContext.newInstance(CalendarRecord.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(view,writer);
        output = writer.toString();
        return output;
    }
    
    /**
     * 
     * @param view
     * @return
     * @throws JAXBException 
     */
    public static String transformJaxBToScript(Search view) throws JAXBException{
        StringWriter writer = new StringWriter();
        String output = "";
        JAXBContext ctx = JAXBContext.newInstance(Search.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(view,writer);
        output = writer.toString();
        return output;
    }
        
    /**
     * 
     * @param view
     * @return
     * @throws JAXBException 
     */
    public static String transformJaxBToScript(TreeRecord view) throws JAXBException{
        StringWriter writer = new StringWriter();
        String output = "";
        JAXBContext ctx = JAXBContext.newInstance(TreeRecord.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(view,writer);
        output = writer.toString();
        return output;
    }
    
    /**
     * 
     * @param script
     * @return 
     */
    public static TreeRecord transformScriptToTree(String script) throws JAXBException{
        StringReader reader = new StringReader(script);
        JAXBContext ctx = JAXBContext.newInstance(TreeRecord.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        TreeRecord record = (TreeRecord) unmarshaller.unmarshal(reader);
        return record;
    }
    
    /**
     * 
     * @param script
     * @return
     * @throws JAXBException 
     */
     public static CalendarRecord transformScriptToCalendar(String script) throws JAXBException{
        StringReader reader = new StringReader(script);
        JAXBContext ctx = JAXBContext.newInstance(CalendarRecord.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        CalendarRecord record = (CalendarRecord) unmarshaller.unmarshal(reader);
        return record;
    }
    
    /**
     * 
     * @param script
     * @return
     * @throws JAXBException 
     */
    public static FormRecord transformScriptToForm(String script) throws JAXBException{
        StringReader reader = new StringReader(script);
        JAXBContext ctx = JAXBContext.newInstance(FormRecord.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        FormRecord record = (FormRecord) unmarshaller.unmarshal(reader);
        return record;
    }
    /**
     * 
     * @param script
     * @return
     * @throws JAXBException 
     */
    public static DashboardRecord transformScriptToDashboard(String script) throws JAXBException{
        StringReader reader = new StringReader(script);
        JAXBContext ctx = JAXBContext.newInstance(DashboardRecord.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        DashboardRecord record = (DashboardRecord) unmarshaller.unmarshal(reader);
        return record;
    }
    
     /**
     * 
     * @param script
     * @return
     * @throws JAXBException 
     */
    public static Search transformScriptToSearch(String script) throws JAXBException{
        StringReader reader = new StringReader(script);
        JAXBContext ctx = JAXBContext.newInstance(Search.class);
        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        Search record = (Search) unmarshaller.unmarshal(reader);
        return record;
    }
}
