//VARIABLES
/*var idElementMenuActif = "";
var idElementMenuSousMenusActif = "";
var idElementMenuSousMenusPetitsActif = "";
var siZoneMenugaucheDejaAfficher = false;
var largeurEcran = 1000;
var hauteurEcran = 0;
var defaultResolutionSmallScreen = 1280;
var defaultBackgroundColorEnteteBar = "#2278b2";*/

//APPELS DES FONCTIONS
inialiserLaPagePanelModules();


function inialiserLaPagePanelModules(){
    $('#pageModule').hide();
	//$('body').append("<div id='principalePanel'></div>");
	//$('body').append("<div id='entetePrincipalPanel'></div>");
	/*$('body').append("<div id='pageModule'></div>");*/
	/*$('body').append("<div id='menuGauche'></div>");*/
	//$('body').append("<div id='piedDePage'></div>");
	
	/*$('#principalePanel').append("<div id='bodyPrincipalPanel'></div>");
	$('#principalePanel').append("<div id='footerPrincipalPanel'></div>");*/
	
	//Page module
        /*$('#pageModule').hide();
	$('#pageModule').append("<span id='menuGauche'></span>");
	$('#pageModule').append("<span id='moduleContentData'></span>");
	
	$('#moduleContentData').append("<div id='moduleContentDataBody'></div>");
	$('#moduleContentData').append("<div id='moduleContentDataFooter'></div>");
	
	//Pied de page
	$('#moduleContentDataFooter').append("<span id='moduleContentDataFooterDescription'>Fourni par MEGATIM</span>");
	
	//Menu de gauche
	$('#menuGauche').append("<div id='logoSocieteContent'><img id='logoSociete' src='images/bunec.png'></div>");
	$('#menuGauche').append("<div id='menusContent'></div>");
	$('#menuGauche').append("<div id='ligneContent'></div>");*/
	
	//entetePrincipalPanel
	/*$('#entetePrincipalPanel').append("<span id='entetePrincipalPanelPart01'></span>");
	$('#entetePrincipalPanel').append("<span id='entetePrincipalPanelPart02'></span>");
	
	$('#entetePrincipalPanelPart01').append("<span id='entetePrincipalPanelPart01LogoAndDescription'></span>");
	$('#entetePrincipalPanelPart01').append("<span id='entetePrincipalPanelPart01ElementsModule'></span>");
	
	$('#entetePrincipalPanelPart01LogoAndDescription').append("<span id='entetePrincipalPanelPart01Logo'></span>");
	$('#entetePrincipalPanelPart01LogoAndDescription').append("<span id='entetePrincipalPanelPart01Description'></span>");
	
	$('#entetePrincipalPanelPart01Description').append("<span id='footerPrincipalPanelTitre01'>Cauris </span>");
	$('#entetePrincipalPanelPart01Description').append("<span id='footerPrincipalPanelTitre02'>ERP</span>");
	
	$('#entetePrincipalPanelPart02').append("<span id='messageLogo' class='enteteItem'><i class='fa fa-envelope'></i></span>");
	$('#entetePrincipalPanelPart02').append("<span id='messageLogo2' class='enteteItem'><i class='fa fa-comments'></i></span>");
	$('#entetePrincipalPanelPart02').append("<span id='notificationLogo' class='enteteItem2'></span>");
	
	$('#notificationLogo').append("<span id='loginLogo' class='enteteItemLight'><i class='fa fa-user'></i></span>");
	$('#notificationLogo').append("<span id='userLogin' class='enteteItemLight2'>Admin - ALbert Fanga</span>");*/
	
	//bodyPrincipalPanel
	/*$('#bodyPrincipalPanel').append("<div id='bodyPrincipalPanelPart01'></div>");
	$('#bodyPrincipalPanel').append("<div id='bodyPrincipalPanelPart02'></div>");
	
	$('#bodyPrincipalPanelPart02').append("<div id='bodyPrincipalPanelPart02Special'></div>");*/
	
        //On charge le logo de la sociÃ©tÃ©
        //chargerLogoSociete("logoSocieteContent", "logo.png");
        
	//On manage l'evenement de changement de resolution
	managerEvenementDeResolutionEcran();
	
	$(window).resize(function() {
		
	  //On manage l'evenement de changement de resolution
	  managerEvenementDeResolutionEcran();
	  
	});
	
	gererEvenements();
	
	function gererEvenements(){
		
		/*$("#entetePrincipalPanelPart01Logo").click(function(e) {
			
                        //on reaffiche le panel principal
                        afficherPanelHide();
		});
		
		$("#entetePrincipalPanelPart01Description").click(function(e) {
			
			//On decide s'il faut afficher ou cacher la barre de menus
			afficherOuCacherMenuGaucheResponsive(defaultResolutionSmallScreen);
			
		});*/
	}
}

function afficherPanelHide(){
    
    //On vide
    nomModuleCourantResponsive = "";
    iDModuleCourantResponsive = "";
    siModuleDejaClique = false;
    
    //On remet la couleur principal
    $("#entetePrincipalPanel").css('background-color',defaultBackgroundColorEnteteBar);
    
    //On vide les menus
    $("#entetePrincipalPanelPart01ElementsModule").html("");
    
    //On remet les textes d'origines
    $("#entetePrincipalPanelPart01Logo").html("");
    $("#entetePrincipalPanelPart01Description").html("");
    
    //On remet les textes d'origines
    $('#entetePrincipalPanelPart01Description').append("<span id='footerPrincipalPanelTitre01'>Cauris </span>");
    $('#entetePrincipalPanelPart01Description').append("<span id='footerPrincipalPanelTitre02'>ERP</span>");

    //On cache la page module courantea afficher
    $('#principalePanel').fadeIn(200);
    $('#pageModule').fadeOut(200);
    
   // $("#barMenuHorizontal").css('display','none');
    //$("#menuVertical").css('display','none');
}

function managerEvenementDeResolutionEcran(){
	
	//On recupere les dimensions
	largeurEcran = $(window).width();
	hauteurEcran = $(window).height();
	
	if(iDModuleCourantResponsive.length != 0){
	
		//On gere le responsive de la barre de gauche
		afficherOuCacherTexteMenuEntete(defaultResolutionSmallScreen);
		afficherOuCacherMenuGaucheResponsive(defaultResolutionSmallScreen);
		afficherOuCacherMenuGaucheResponsive2(defaultResolutionSmallScreen);
	
	}
}

function ajouterElementModule(idParent, idItem, description, color, actions){
	$('#'+idParent).append("<span id="+idItem+"_elementModule class='elementModule'></span>");
        $('#'+idItem+"_elementModule").append("<div id="+idItem+"_elementModuleTitre class='elementModuleTitre'>"+description+"</div>");
        $('#'+idItem+"_elementModule").append("<div id="+idItem+"_elementModuleActions class='elementModuleActions'></div>");
        
        for(var i=0;i<actions.length;i++){
             
             //Si le champ est visible
             if(!actions[i].hide){
                 
                //On affiche les actions
                $('#'+idItem+"_elementModuleActions").append("<div id="+actions[i].id+"_elementModuleActionItem class='elementModuleActionItem'></div>");
                //$('#'+actions[i].id+"_elementModuleActionItem").append("<span id="+actions[i].id+"_elementModuleLogo class='elementModuleLogo'><i class='fa fa-circle'></i></span>");
                //$('#'+actions[i].id+"_elementModuleActionItem").append("<span id="+actions[i].id+"_elementModuleLogo class='elementModuleLogo'></span>");
                $('#'+actions[i].id+"_elementModuleActionItem").append("<span id="+actions[i].id+"_elementModuleTitle class='elementModuleTitle'>"+actions[i].label+"</span>");
            
                //On gere les evenements
                managerEvenements(actions[i].id, idItem);
                
                function managerEvenements(idAction, idItem){
                    
                    var menuDeroulantItem = document.getElementById(idAction+"_elementModuleActionItem");
                
                    menuDeroulantItem.onmouseover = function(e) {

                        e = e || window.event; // Compatibilité IE
                        var relatedTarget = e.relatedTarget || e.fromElement; // Idem
                        while (relatedTarget != menuDeroulantItem && relatedTarget.nodeName != 'BODY' && relatedTarget != document)
                        {
                                relatedTarget = relatedTarget.parentNode;
                        }
                        if (relatedTarget != menuDeroulantItem) {
                            
                            if(idMenuDeroulantActif != idAction){
                            
                                //
                                $('#'+idAction+"_elementModuleActionItem").css('background-color','#e4e4e4');
                            }

                        }
                    };

                    menuDeroulantItem.onmouseout = function(e) {

                        e = e || window.event; // Compatibilité IE
                        var relatedTarget = e.relatedTarget || e.toElement; // Idem
                        while (relatedTarget != menuDeroulantItem && relatedTarget.nodeName != 'BODY' && relatedTarget != document)
                        {
                                relatedTarget = relatedTarget.parentNode;
                        }
                        if (relatedTarget != menuDeroulantItem) {
                            
                            if(idMenuDeroulantActif != idAction){
                                //
                                $('#'+idAction+"_elementModuleActionItem").css('background-color','transparent');
                            }
                        }
                    };
                    
                    $('#'+idAction+"_elementModuleActionItem").click(function(e) {
                    
                        //On simule click sur la barre verticale qui est cache
                        $('#'+idAction+"_elementModuleMenusPetit").trigger('click');
                        
                        //On cache l'element
                         $('#'+idItem+"_elementModuleActions").fadeOut(100,function(){
                            
                            //On marque comme actif
                            if(idMenuDeroulantActif != idAction){
                                
                                //On retire le focus de l'element precedent 
				$('#'+idMenuDeroulantActif+"_elementModuleLogo").css('color','white');
                                $('#'+idMenuDeroulantActif+"_elementModuleActionItem").css('background-color','transparent');
                                $('#'+idMenuDeroulantActif+"_elementModuleActionItem").css('color',"black");
                                
				//On affecte le nouveau
				idMenuDeroulantActif = idAction;
				
                                //On applique le focus de l'element precedent 
				$('#'+idMenuDeroulantActif+"_elementModuleLogo").css('color',color);
                                $('#'+idMenuDeroulantActif+"_elementModuleActionItem").css('background-color',color);
                                $('#'+idMenuDeroulantActif+"_elementModuleActionItem").css('color',"white");
                                
                            }
                            
                            
                        });
                   
                    });
                }
            }
        }
        
        //In cache le detail
        $('#'+idItem+"_elementModuleActions").hide();
	
	//variables
	var idElementModuleMenus = idItem+"_"+generateurIdAvance();
	
	//On affiches les sous menus
	ajouterElementModuleMenus("menusContent", idElementModuleMenus, description, color, idItem);
	
	gererEvenements();
	
	function gererEvenements(){
		
                var elementModule = document.getElementById(idItem+"_elementModule");
                
                elementModule.onmouseover = function(e) {
                    
                    e = e || window.event; // Compatibilité IE
                    var relatedTarget = e.relatedTarget || e.fromElement; // Idem
                    while (relatedTarget != elementModule && relatedTarget.nodeName != 'BODY' && relatedTarget != document)
                    {
                            relatedTarget = relatedTarget.parentNode;
                    }
                    if (relatedTarget != elementModule) {

                        //On cache le simple
                        $('#'+idItem+"_elementModuleActions").fadeIn(100,function(){

                               //Code

                        });

                    }
                };

                elementModule.onmouseout = function(e) {
                    
                    e = e || window.event; // Compatibilité IE
                    var relatedTarget = e.relatedTarget || e.toElement; // Idem
                    while (relatedTarget != elementModule && relatedTarget.nodeName != 'BODY' && relatedTarget != document)
                    {
                            relatedTarget = relatedTarget.parentNode;
                    }
                    if (relatedTarget != elementModule) {

                        //On cache le simple
                        $('#'+idItem+"_elementModuleActions").fadeOut(100,function(){

                                //Code

                        });
                    }
                };
                
		$('#'+idItem+"_elementModule").click(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
				
                                //On retire le focus de l'element precedent (barre horizontale)
				$('#'+idElementMenuSousMenusActif+"_elementModule").css('background-color','transparent');
                                
				//On retire le focus de l'element precedent (barre verticale)
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','#4c4c4c');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','500');
				
				//On remonte
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideUp();
				
				//On affecte le nouveau
				idElementMenuSousMenusActif = idItem;
				
                                //On applique le focus de l'element precedent (barre horizontale)
				$('#'+idElementMenuSousMenusActif+"_elementModule").css('background-color','rgba(0, 0, 0, 0.60)');
                                
				//On applique le focus de l'element precedent (barre verticale)
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color',color);
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','white');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','bold');
                                
                                //on place l'element courant en tete (barre horizontale)
                                /*entetePrincipalPanelPart01ElementsModule = document.getElementById("entetePrincipalPanelPart01ElementsModule");
                                elementModuleCourantId = document.getElementById(idElementMenuSousMenusActif+"_elementModule");                    
                                entetePrincipalPanelPart01ElementsModule.insertBefore(elementModuleCourantId, entetePrincipalPanelPart01ElementsModule.firstChild);
                                */
                               
				//On dÃ©roule
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideDown(300);
			}else{
				
				// Au niveau de la barre horizontal 
				$('#'+idElementMenuSousMenusActif+"_elementModule").css('background-color','transparent');
				
				//On retire le focus de l'element precedent
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','#4c4c4c');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','500');
				
				//On remonte
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideUp(250);
				
				//On affecte le nouveau
				idElementMenuSousMenusActif = "";
			}
		});
		
		$('#'+idItem+"_elementModule").mouseover(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModule").css('background-color','rgba(0, 0, 0, 0.30)');
			}
		});
		
		$('#'+idItem+"_elementModule").mouseout(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModule").css('background-color','transparent');
			}
			
		});
	}	
}

function ajouterElementModuleMenus(idParent, idItem, description, color, idParentBarreHorizontale){
	$('#'+idParent).append("<div id="+idItem+"_elementModuleMenus class='elementModuleMenus'></div>");
	$('#'+idItem+"_elementModuleMenus").append("<div id="+idItem+"_elementModuleMenusEntete class='elementModuleMenusEntete'></div>");
	$('#'+idItem+"_elementModuleMenus").append("<div id="+idItem+"_elementModuleMenusCorps class='elementModuleMenusCorps'></div>");
	
	$('#'+idItem+"_elementModuleMenusEntete").append("<span id="+idItem+"_elementModuleMenus class='elementModuleMenusLogo'><i class='fa fa-angle-down'></i></span>");
	$('#'+idItem+"_elementModuleMenusEntete").append("<span id="+idItem+"_elementModuleMenus class='elementModuleMenusDescription'>"+description+"</span>");
	
	//On remonte
	$('#'+idItem+"_elementModuleMenusCorps").slideUp(100);
	
	//On affiche les sous menus
        //chargerActionsMenu(idItem+"_elementModuleMenusCorps", idItem, color);
	
	gererEvenements();
	
	function gererEvenements(){
		
		$('#'+idItem+"_elementModuleMenusEntete").click(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
				
				/****** Au niveau de la barre horizontal *********/
				//On retire le focus de l'element precedent
				$('#'+idElementMenuActif+"_elementModule").css('background-color','transparent');
				
				//On affecte le nouveau
				idElementMenuActif = idParentBarreHorizontale;//
				
				//On applique le focus de l'element precedent
				$('#'+idElementMenuActif+"_elementModule").css('background-color','rgba(0, 0, 0, 0.60)');
				/*****************************************************/
				
				//On retire le focus de l'element precedent
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','#4c4c4c');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','500');
				
				//On remonte
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideUp();
				
				//On affecte le nouveau
				idElementMenuSousMenusActif = idItem;
				
				//On applique le focus de l'element precedent
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color',color);
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','white');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','bold');
				
				//On dÃ©roule
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideDown(300);
			}else{
				
				/****** Au niveau de la barre horizontal *********/
				$('#'+idElementMenuActif+"_elementModule").css('background-color','transparent');
				/***************************************************/
				
				//On retire le focus de l'element precedent
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','#4c4c4c');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','500');
				
				//On remonte
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideUp(250);
				
				//On affecte le nouveau
				idElementMenuSousMenusActif = "";
			}
			
		});
		
		$('#'+idItem+"_elementModuleMenusEntete").mouseover(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusEntete").css('background-color',"#e4e4e4");
				//$('#'+idItem+"_elementModuleMenus").css('color','white');
			}
		});
		
		$('#'+idItem+"_elementModuleMenusEntete").mouseout(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idItem+"_elementModuleMenusEntete").css('color','#4c4c4c');
			}
		});
	}	
}

function ajouterElementModuleMenusPetits(idParent, idItem, description, logo, color){
	$('#'+idParent).append("<div id="+idItem+"_elementModuleMenusPetit class='elementModuleMenusPetit'></div>");
	$('#'+idItem+"_elementModuleMenusPetit").append("<span id="+idItem+"_elementModuleMenusPetitLogo class='elementModuleMenusPetitLogo'>"+logo+"</span>");
	$('#'+idItem+"_elementModuleMenusPetit").append("<span id="+idItem+"_elementModuleMenusPetitDescription class='elementModuleMenusPetitDescription'>"+description+"</span>");
	
	gererEvenements();
	
	function gererEvenements(){
		
		$('#'+idItem+"_elementModuleMenusPetit").click(function(e) {
			
			if(idElementMenuSousMenusPetitsActif != idItem){
				
				//On retire le focus de l'element precedent
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetit").css('background-color','transparent');
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetitLogo").css('color','#4c4c4c');
				
				//On affecte le nouveau
				idElementMenuSousMenusPetitsActif = idItem;
				
				//On applique le focus de l'element precedent
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetit").css('background-color',"#d6d6d6");
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetitLogo").css('color',color);
				
			}
			
			//On decide s'il faut afficher ou cacher la barre de menus
			afficherOuCacherMenuGaucheResponsive(defaultResolutionSmallScreen);
                     
		});
		
		$('#'+idItem+"_elementModuleMenusPetit").mouseover(function(e) {
			
			if(idElementMenuSousMenusPetitsActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusPetit").css('background-color',"#e4e4e4");
			}
		});
		
		$('#'+idItem+"_elementModuleMenusPetit").mouseout(function(e) {
			
			if(idElementMenuSousMenusPetitsActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusPetit").css('background-color','transparent');
			}
			
			
		});
	}
}

function ajouterElementModuleMenusPetitsLight(idParent, idItem, description, logo, color){
	/*$('#'+idParent).append("<div id="+idItem+"_elementModuleMenusPetit class='elementModuleMenusPetit'></div>");
	$('#'+idItem+"_elementModuleMenusPetit").append("<span id="+idItem+"_elementModuleMenusPetitLogo class='elementModuleMenusPetitLogo'>"+logo+"</span>");
	$('#'+idItem+"_elementModuleMenusPetit").append("<span id="+idItem+"_elementModuleMenusPetitDescription class='elementModuleMenusPetitDescription'>"+description+"</span>");
	*/
       
       //On reduit le texte
       var texteAvant = $('#'+idItem+"_elementModuleMenusPetitDescription").text();
       var texteAapres = reduireTailleTexteSiLongGeneral($('#'+idItem+"_elementModuleMenusPetitDescription").text(),18);
       
        $('#'+idItem+"_elementModuleMenusPetitDescription").text(texteAapres);
       
       //Gerer les infosbulles
       if(texteAvant != texteAapres){
           
            $(function(){
                $('#'+idItem+"_elementModuleMenusPetit").tooltip();
            });
        }
       
	gererEvenements();
	
	function gererEvenements(){
		
		$('#'+idItem+"_elementModuleMenusPetit").click(function(e) {
			
			if(idElementMenuSousMenusPetitsActif != idItem){
				
				//On retire le focus de l'element precedent
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetit").css('background-color','transparent');
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetitLogo").css('color','#4c4c4c');
				
				//On affecte le nouveau
				idElementMenuSousMenusPetitsActif = idItem;
				
				//On applique le focus de l'element precedent
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetit").css('background-color',"#d6d6d6");
				$('#'+idElementMenuSousMenusPetitsActif+"_elementModuleMenusPetitLogo").css('color',color);
				
			}
			
			//On decide s'il faut afficher ou cacher la barre de menus
			afficherOuCacherMenuGaucheResponsive(defaultResolutionSmallScreen);
			
		});
		
		$('#'+idItem+"_elementModuleMenusPetit").mouseover(function(e) {
			
			if(idElementMenuSousMenusPetitsActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusPetit").css('background-color',"#e4e4e4");
			}
		});
		
		$('#'+idItem+"_elementModuleMenusPetit").mouseout(function(e) {
			
			if(idElementMenuSousMenusPetitsActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusPetit").css('background-color','transparent');
			}
			
			
		});
	}
}

function afficherOuCacherTexteMenuEntete(limite){
	
	//alert(largeurEcran);
	if(largeurEcran < limite){
		
		//On change le texte 
		$("#entetePrincipalPanelPart01Description").text("Menu");
		
	}else{
		
		//On change le texte 
		$("#entetePrincipalPanelPart01Description").text(nomModuleCourantResponsive);
	}
	
}

function afficherOuCacherMenuGaucheResponsive(limite){
	
	if(largeurEcran < limite){
		
		if(siZoneMenugaucheDejaAfficher){
		
			$('#menuGauche').animate({'margin-left':'-88%'}, 400 ,function(){
						
				//O, notifie que c'est pas afficher	
				siZoneMenugaucheDejaAfficher = false;
			});
				
		}else{
				
			$('#menuGauche').animate({'margin-left':'0%'}, 400 ,function(){
						
				//On, notifie que c'est dÃ©jÃ  afficher	
				siZoneMenugaucheDejaAfficher = true;
			});
		}
	}
}

function afficherOuCacherMenuGaucheResponsive2(limite){
	
	if(largeurEcran > limite){
		
		$('#menuGauche').animate({'margin-left':'0%'}, 400 ,function(){
						
			//On, notifie que c'est dÃ©jÃ  afficher	
			siZoneMenugaucheDejaAfficher = false;
		});
	}
}

function ajouterUnItem(idParent, idItem, logo, description, color){
	$('#'+idParent).append("<div id="+idItem+"_moduleItem   class='moduleItem' ></div>");
	$('#'+idItem+"_moduleItem").append("<div id="+idItem+"_moduleItemLogo class='moduleItemLogo'>"+logo+"</div>");
	$('#'+idItem+"_moduleItem").append("<div id="+idItem+"_moduleItemDescription class='moduleItemDescription'>"+description+"</div>");
	
	//On applique la couleur
	$('#'+idItem+"_moduleItemLogo").css("color",color);
	$('#'+idItem+"_moduleItemDescription").css("color",color);
	
	gererEvenements();
	
	function gererEvenements(){
		
		$('#'+idItem+"_moduleItem").click(function(e) {
			
			$("#entetePrincipalPanelPart01Logo").html("<i class='fa fa-th'></i>");
			$("#entetePrincipalPanelPart01Description").html(description);
			$("#entetePrincipalPanel").css('background-color',color);
			
			
			//On ajoute les elements du module
			$("#entetePrincipalPanelPart01ElementsModule").html("");
			$("#menusContent").html("");
			
                        //On charge les menus du modules
                        chargerMenusModule("entetePrincipalPanelPart01ElementsModule", idItem, color);                 
                        
			//On save le Id du module
			iDModuleCourantResponsive = idItem;
			
			//On save le nom du module
			nomModuleCourantResponsive = description;
			
			//On decide s'il faut afficher ou cacher la barre de menus
			managerEvenementDeResolutionEcran();
                        
                        //On teste (on attive la barre et le menu gauche de beko)
                        $('#principalePanel').fadeOut(200);
                        //$('#entetePrincipalPanel').fadeOut(200);
                        $("#barMenuHorizontal").css('display','block');
                        $("#menuVertical").css('display','block');
                        
		});
		
		$('#'+idItem+"_moduleItem").mouseover(function(e) {
			
			//On change la couleur d'arriere plan
			$('#'+idItem+"_moduleItem").css("background-color",color);
			$('#'+idItem+"_moduleItemLogo").css("color","white");
			$('#'+idItem+"_moduleItemDescription").css("color","white");
		});
		
		$('#'+idItem+"_moduleItem").mouseout(function(e) {
			
			//On change la couleur d'arriere plan
			$('#'+idItem+"_moduleItem").css("background-color","white");
			$('#'+idItem+"_moduleItemLogo").css("color",color);
			$('#'+idItem+"_moduleItemDescription").css("color",color);
			
		});
	}
}

function ajouterUnItemLight(idItem, logo, description, color){ 
	
	//On applique la couleur
	$('#'+idItem+"_moduleItemLogo").css("color",color);
	$('#'+idItem+"_moduleItemDescription").css("color",color);
        
         //On reduit le texte
       var texteAvant = $('#'+idItem+"_moduleItemDescription").text();
       var texteAapres = reduireTailleTexteSiLongGeneral($('#'+idItem+"_moduleItemDescription").text(),9);
       
        $('#'+idItem+"_moduleItemDescription").text(texteAapres);
       
       //Gerer les infosbulles
       if(texteAvant != texteAapres){
           
            $(function(){
                $('#'+idItem+"_moduleItem").tooltip();
            });
        }
        
	gererEvenements();
	
	function gererEvenements(){
		
		$('#'+idItem+"_moduleItem").click(function(e) {
			
                    if(!siModuleDejaClique){
                        
                        $("#entetePrincipalPanelPart01Logo").html("<i class='fa fa-th'></i>");
                        $("#entetePrincipalPanelPart01Description").html(description);
                        $("#entetePrincipalPanel").css('background-color',color);

                        //On ajoute les elements du module
                        $("#entetePrincipalPanelPart01ElementsModule").html("");
                        $("#menusContent").html("");
                        
                        
                        if( idItem == "configurationModule" ){
                        
                            //On charge les menus du menu configuration                     
                            chargerMetatdaModuleConfiguration("entetePrincipalPanelPart01ElementsModule", idItem, color)  
                            
                        }else if( idItem == "applicationModule" ){
                        
                            //On charge les menus du menu applications                     
                            chargerMetatdaModuleApplications("entetePrincipalPanelPart01ElementsModule", idItem, color)  
                            
                        }else{
                            
                            //On charge les menus du modules                     
                            chargerMenusModule("entetePrincipalPanelPart01ElementsModule", idItem, color);
                            
                        }

                        //On save le Id du module
                        iDModuleCourantResponsive = idItem;

                        //On save le coleur du module
                        couleurModuleCourantResponsive = color;

                        //On save le nom du module
                        nomModuleCourantResponsive = description;

                        //On decide s'il faut afficher ou cacher la barre de menus
                        managerEvenementDeResolutionEcran();

                        //On teste (on attive la barre et le menu gauche de beko)
                        $('#principalePanel').fadeOut(200);
                        $('#pageModule').fadeIn(200);
                        //$("#barMenuHorizontal").css('display','block');
                        //$("#menuVertical").css('display','block');
                        
                        //On change la couleur de tous les boutons save des dialogs
                        $('#boutonSAveDialog01').css('background-color',couleurModuleCourantResponsive);
                        $('#boutonSAveDialog02').css('background-color',couleurModuleCourantResponsive);
                        $('#boutonSAveDialog03').css('background-color',couleurModuleCourantResponsive);
                        $('#boutonSAveDialog04').css('background-color',couleurModuleCourantResponsive);

                        siModuleDejaClique = true;
                    }
		});
		
		$('#'+idItem+"_moduleItem").mouseover(function(e) {
			
			//On change la couleur d'arriere plan
			$('#'+idItem+"_moduleItem").css("background-color",color);
			$('#'+idItem+"_moduleItemLogo").css("color","white");
			$('#'+idItem+"_moduleItemDescription").css("color","white");
		});
		
		$('#'+idItem+"_moduleItem").mouseout(function(e) {
			
			//On change la couleur d'arriere plan
			$('#'+idItem+"_moduleItem").css("background-color","white");
			$('#'+idItem+"_moduleItemLogo").css("color",color);
			$('#'+idItem+"_moduleItemDescription").css("color",color);
			
		});
	}
}

function ajouterElementModuleMenusLight(idItem, color){
	/*$('#'+idParent).append("<div id="+idItem+"_elementModuleMenus class='elementModuleMenus'></div>");
	$('#'+idItem+"_elementModuleMenus").append("<div id="+idItem+"_elementModuleMenusEntete class='elementModuleMenusEntete'></div>");
	$('#'+idItem+"_elementModuleMenus").append("<div id="+idItem+"_elementModuleMenusCorps class='elementModuleMenusCorps'></div>");
	
	$('#'+idItem+"_elementModuleMenusEntete").append("<span id="+idItem+"_elementModuleMenus class='elementModuleMenusLogo'><i class='fa fa-angle-down'></i></span>");
	$('#'+idItem+"_elementModuleMenusEntete").append("<span id="+idItem+"_elementModuleMenus class='elementModuleMenusDescription'>"+description+"</span>");
	*/
       
       //On reduit le texte
       var texteAvant = $('#'+idItem+"_elementModuleMenusDescription").text();
       var texteAapres = reduireTailleTexteSiLongGeneral($('#'+idItem+"_elementModuleMenusDescription").text(),18);
       
       //On met Ã  jour le texte
       $('#'+idItem+"_elementModuleMenusDescription").text(texteAapres);
       
       //Gerer les infosbulles
       if(texteAvant != texteAapres){
           
            $(function(){
                $('#'+idItem+"_elementModuleMenusEntete").tooltip();
            });
        }
       
	//On remonte
	$('#'+idItem+"_elementModuleMenusCorps").slideUp(100);
	
	//On affiche les sous menus
       chargerActionsMenu(idItem+"_elementModuleMenusCorps", idItem, color);
	
	gererEvenements();
	
	function gererEvenements(){
		
		$('#'+idItem+"_elementModuleMenusEntete").click(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
				
				
                                //On retire le focus de l'element precedent (barre horizontale)
				$('#'+idElementMenuSousMenusActif+"_elementModule").css('background-color','transparent');
                                
				//On retire le focus de l'element precedent (barre verticale)
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','#4c4c4c');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','500');
				
				//On remonte
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideUp();
				
				//On affecte le nouveau
				idElementMenuSousMenusActif = idItem;
				
                                //On applique le focus de l'element precedent (barre horizontale)
				$('#'+idElementMenuSousMenusActif+"_elementModule").css('background-color','rgba(0, 0, 0, 0.60)');
                                
				//On applique le focus de l'element precedent (barre verticale)
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color',color);
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','white');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','800');
				
                                //on place l'element courant en tete (barre horizontale)
                                entetePrincipalPanelPart01ElementsModule = document.getElementById("entetePrincipalPanelPart01ElementsModule");
                                elementModuleCourantId = document.getElementById(idElementMenuSousMenusActif+"_elementModule");                    
                                entetePrincipalPanelPart01ElementsModule.insertBefore(elementModuleCourantId, entetePrincipalPanelPart01ElementsModule.firstChild);
                                
				//On dÃ©roule
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideDown(300);
			}else{
				
				// Au niveau de la barre horizontal 
				$('#'+idElementMenuSousMenusActif+"_elementModule").css('background-color','transparent');
				
				//On retire le focus de l'element precedent
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('color','#4c4c4c');
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusEntete").css('font-weight','500');
				
				//On remonte
				$('#'+idElementMenuSousMenusActif+"_elementModuleMenusCorps").slideUp(250);
				
				//On affecte le nouveau
				idElementMenuSousMenusActif = "";
			}
			
		});
		
		$('#'+idItem+"_elementModuleMenusEntete").mouseover(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusEntete").css('background-color',"#e4e4e4");
				//$('#'+idItem+"_elementModuleMenus").css('color','white');
			}
		});
		
		$('#'+idItem+"_elementModuleMenusEntete").mouseout(function(e) {
			
			if(idElementMenuSousMenusActif != idItem){
			
				//On change la couleur d'arriere plan
				$('#'+idItem+"_elementModuleMenusEntete").css('background-color','transparent');
				$('#'+idItem+"_elementModuleMenusEntete").css('color','#4c4c4c');
			}
		});
	}	
}