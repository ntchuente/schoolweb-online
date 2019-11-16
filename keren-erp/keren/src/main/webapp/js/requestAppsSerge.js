//VARIABLES
var nombreModuleActifs = 0;
var defaultLogo = "fa fa-coffee";
var defaultColor = "#ff9e67";
var listeInfosModules = {};

function getMetaDataConfigurations(){
    
    var configurationModule = [{ id:-1 , name:"configuration",label:"Configuration",selected:false,hasmenu:true,
                 groups:[
                      {id:-1 , name:"utilisateurs",label:"Utilisateurs",icon:"glyphicon glyphicon-user" ,showmenu:true,
                       actions:[
                          {id:-1,name:"utilisateur" , label:"Utilisateurs",icon:"glyphicon glyphicon-user",entityName:"Utilisateur",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore',hide:false},
                          {id:-2,name:"groupes" , label:"Groupes",icon:"glyphicon glyphicon-list-alt",entityName:"Groupe",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore',hide:false},
                          {id:-3,name:"societe" , label:"Societes",icon:"glyphicon glyphicon-home",entityName:"Societe",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore',hide:false},
                          {id:-4,name:"pays" , label:"Pays",icon:"glyphicon glyphicon-flag",entityName:"pays",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore',hide:false}
                       ]},
                       { id:-1000 , name:"traduction" , label:"Traductions",icon:"glyphicon glyphicon-book" ,showmenu:true,
                         actions:[
                             {id:-5,name:"langues" , label:"Langues",icon:"glyphicon glyphicon-bullhorn",entityName:"Langue",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore',hide:false},
                             {id:-7,name:"traduction" , label:"TERME",icon:"glyphicon glyphicon-globe",entityName:"Terme",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore',hide:false}
                         ]},
                         {id:-4 , name:"discussion_conf",label:"Discussion",icon:"glyphicon glyphicon-th",showmenu:true,
                                actions:[
                                   {id:-8,name:"show_canal_discussion" , label:"CANAUX",icon:"glyphicon glyphicon-th",entityName:"Canal",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore',hide:false}                          
                           ]},
                       {id:-3 , name:"techniques",label:"Technique",icon:"glyphicon glyphicon-cog", showmenu:true,
                       actions:[
                          {id:-9,name:"actionsdb" ,hide:false, label:"Actions",icon:"glyphicon glyphicon-th",entityName:"ActionItem",moduleName:"kerencore",modal:false,securitylevel:2,model:'kerencore'},
                          {id:-10,name:"menu" ,hide:false, label:"MENUGROUP",icon:"glyphicon glyphicon-th",entityName:"MenuGroupActions",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                          {id:-11,name:"actions" ,hide:false, label:"MENUACTION",icon:"glyphicon glyphicon-align-center",entityName:"MenuAction",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                          {id:-12,name:"formview" ,hide:false, label:"FORMVIEW",icon:"glyphicon glyphicon-list-alt",entityName:"FormRecord",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                          {id:-13,name:"treeview" ,hide:false, label:"LISTVIEW",icon:"glyphicon glyphicon-align-justify",entityName:"TreeRecord",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                          {id:-14,name:"calendarview" ,hide:false, label:"CALENDARVIEW",icon:"glyphicon glyphicon-align-justify",entityName:"CalendarRecord",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                          {id:-15,name:"dashboardview" ,hide:false, label:"DASHBORDVIEW",icon:"glyphicon glyphicon-align-justify",entityName:"DashboardRecord",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                          {id:-16,name:"reportview" ,hide:false, label:"Editique",icon:"glyphicon glyphicon-align-justify",entityName:"ReportRecord",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'}
                       ]},
                       {id:-5 , name:"sauvegarde_conf",label:"DBSAVE",icon:"glyphicon glyphicon-hdd",showmenu:true,
                           actions:[
                              {id:-17,name:"progra_save" ,hide:false, label:"Configuration",icon:"glyphicon glyphicon-time",entityName:"Export",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                              {id:-18,name:"export_bd" ,hide:false, label:"EXPORTBD",icon:"glyphicon glyphicon-save-file",entityName:"Export",moduleName:"kerencore",modal:true,securitylevel:0,model:'kerencore'}                          
                       ]}
                 ]

      }];
    
    return configurationModule;
}

function getMetaDataApplications(){
    
    var applicationsModule = [{ id:-1 , name:"application",label:"Applications",selected:false,hasmenu:true,
            
         groups:[
              {id:-1 , name:"application",label:"Applications",icon:"glyphicon glyphicon-th",showmenu:true, color:"#d1a9a9",
               actions:[
                  {id:-1,name:"applications" ,hide:false, label:"Applications",icon:"glyphicon glyphicon-th-list",entityName:"MenuModule",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'},
                  {id:-2,name:"application_update" ,hide:false, label:"MISEAJOUR",icon:"glyphicon glyphicon-refresh",entityName:"MenuModule",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'}                          
               ]}                       
         ]

      }];
    
    return applicationsModule;
}

function chargerLesModules(){ 
    
    //On recupere les modules
    getDataToServer("http://"+HOST_IP+":"+HOST_PORT+"/kerencore/utilisateur/application/"+USER_LOGIN, "GET", USER_LOGIN, USER_PASSWORD, afficherModules);
    
    /** On charge et affcihe les modules installes **/
    function afficherModules(data){

        for(var i=0;i<data.length;i++){ 
            
             //On recuper le module
             listeInfosModules[data[i].id] = data[i];

            //On charge les modules actifs
            if(data[i].active){

                //On compte
                nombreModuleActifs = nombreModuleActifs + 1;

                //On affiche le module
               ajouterUnItemLight(data[i].id, "<i class='"+data[i].icon+"'></i>", data[i].label, data[i].color);
            }
        }

        //On harmonise l'affiche au cas oÃ¹ il ya peut de module afficher
        if(nombreModuleActifs < 5){
            $('#bodyPrincipalPanelPart02Special').css("text-align","center");
            $('#bodyPrincipalPanelPart02Special').css("padding-left","40px");
        }
    }
}

function chargerConfigurationModule(){ 
    
    //On compte
    nombreModuleActifs = nombreModuleActifs + 1;

    //On affiche le module
    ajouterUnItemLight("configurationModule", "<i class='fa fa-cog'></i>", "Configurations", "#ccb9b4");

    //On harmonise l'affiche au cas oÃ¹ il ya peut de module afficher
    if(nombreModuleActifs < 5){
        $('#bodyPrincipalPanelPart02Special').css("text-align","center");
        $('#bodyPrincipalPanelPart02Special').css("padding-left","40px");
    }
    
}

function chargerMetatdaModuleConfiguration(idParent, idItem, color){
    
    //On ajoute les menus au module concernee
    var data = getMetaDataConfigurations();
    
    for(var i=0;i<data.length;i++){
       
        //On ajoute les menus au module concernee
        var groups = data[i].groups;

        for(var j=0;j<groups.length;j++){ 

            var actions = groups[j].actions;

            //On recuper le module groups barre horizontale
            ajouterElementModule("entetePrincipalPanelPart01ElementsModule", groups[j].id, groups[j].label, color, actions, "<i class='fa fa-circle'></i>");

            //On recuper le module groups barre verticale
            ajouterElementModuleMenusLight(groups[j].id, color);  

            for(var k=0;k<actions.length;k++){

                //On affiche les actions
                ajouterElementModuleMenusPetitsLight(idItem, actions[k].id, actions[k].label, "<i class='fa fa-circle'></i>",color);
            }
        }

    }

    //On affiche le pane l principale
    $('#pageModule').fadeIn(200);
    
}

function chargerMetatdaModuleApplications(idParent, idItem, color){
    
    //On ajoute les menus au module concernee
    var data = getMetaDataApplications();
    
    for(var i=0;i<data.length;i++){
       
        //On ajoute les menus au module concernee
        var groups = data[i].groups;

        for(var j=0;j<groups.length;j++){ 

            var actions = groups[j].actions;

            //On recuper le module groups barre horizontale
            ajouterElementModule("entetePrincipalPanelPart01ElementsModule", groups[j].id, groups[j].label, color, actions, "<i class='fa fa-circle'></i>");

            //On recuper le module groups barre verticale
            ajouterElementModuleMenusLight(groups[j].id, color);  

            for(var k=0;k<actions.length;k++){

                //On affiche les actions
                ajouterElementModuleMenusPetitsLight(idItem, actions[k].id, actions[k].label, "<i class='fa fa-circle'></i>",color);
            }
        }

    }

    //On affiche le pane l principale
    $('#pageModule').fadeIn(200);
    
}

function chargerApplicationModule(){ 
    
    //On compte
    nombreModuleActifs = nombreModuleActifs + 1;

    //On affiche le module
    ajouterUnItemLight("applicationModule", "<i class='fa fa-list'></i>", "Applications", "#d1a9a9");

    //On harmonise l'affiche au cas oÃ¹ il ya peut de module afficher
    if(nombreModuleActifs < 5){
        $('#bodyPrincipalPanelPart02Special').css("text-align","center");
        $('#bodyPrincipalPanelPart02Special').css("padding-left","40px");
    }
   
}

function chargerMenusModule(idParent, idItem, color){
	
    //listeInfosModules[data[i].id] = data[i];
    getDataToServer("http://"+HOST_IP+":"+HOST_PORT+"/kerencore/utilisateur/application/"+USER_LOGIN, "GET", USER_LOGIN, USER_PASSWORD,chargerMenusModuleData);
    
    function chargerMenusModuleData(data){ 
        
        for(var i=0;i<data.length;i++){
           
           if(data[i].id == idItem){
               
                //On ajoute les menus au module concernee
                var groups = data[i].groups;
                
                for(var j=0;j<groups.length;j++){ 
                    
                    var actions = groups[j].actions;
                        
                    //On recuper le module groups barre horizontale
                    ajouterElementModule("entetePrincipalPanelPart01ElementsModule", groups[j].id, groups[j].label, color, actions, "<i class='fa fa-circle'></i>");

                    //On recuper le module groups barre verticale
                    ajouterElementModuleMenusLight(groups[j].id, color);  
                    
                    for(var k=0;k<actions.length;k++){
                        
                        //On affiche les actions
                        ajouterElementModuleMenusPetitsLight(idItem, actions[k].id, actions[k].label, "<i class='fa fa-circle'></i>",color);
                    }
                }

                break;
           }
        }
        
        //On affiche le pane l principale
        $('#pageModule').fadeIn(200);
        
    }
}

function chargerActionsMenu(idParent, idMenu, color){

    //On recupere le id reeelle du menu
    //var idSimplemMenu = idMenu.split('_')[0];
    
    //On charge 
    getDataToServer("http://"+HOST_IP+":"+HOST_PORT+"/kerencore/utilisateur/application/"+USER_LOGIN, "GET", USER_LOGIN, USER_PASSWORD, chargerActionsMenuData);
    
    function chargerActionsMenuData(data){ 
       
        for(var i=0;i<data.length;i++){ 

           if(data[i].id == idMenu){

                //On ajoute les menus au module concernee
                var groups = data[i].groups;

                for(var j=0;j<groups.length;j++){ 

                    //On recuper le module groups barre horizontale
                    ajouterElementModule("entetePrincipalPanelPart01ElementsModule", groups[j].id, groups[j].label, color);

                    //On recuper le module groups barre verticale
                    ajouterElementModuleMenusLight(groups[j].id, color);  
                }

                break;
           }
        }
        
        
        for(var i=0;i<data.length;i++){ 
              
            //On recuper l'action du menu
            ajouterElementModuleMenusPetitsLight(idParent, data[i].id, data[i].label, "<i class='fa fa-circle'></i>",color);
           
        }
    }
}

function chargerLogoSociete(idParent, imageName){
	
    //charge le logo de l'entreprise
    //getDataToServerImage("http://"+HOST_IP+":"+HOST_PORT+"/kerencore/resource/png/"+imageName, "GET", USER_LOGIN, USER_PASSWORD, chargerLogoSocieteData);
    //sergeooo ();
    
    function chargerLogoSocieteData(data){  
        
       /*var blob = new Blob([data], { type: 'image/png' });
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        
        alert("imageName===========================" +link.href);
       
       //On affiche le pane l principale
       $('#'+idParent).html("<img id='logoSociete' src="+link.href+">");*/
       
        
    }
}


