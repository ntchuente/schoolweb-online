/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
angular.module('keren.core.calendar' ,['ui.calendar','pascalprecht.translate','ngRoute','keren.core.commons'])
        .config(function($routeProvider ,$locationProvider,$translateProvider){
            //$locationProvider.html5Mode(true);
    $translateProvider.translations('en',{
            Creer:'Create',
            Importer:'Import',
            Imprimer:'Print',
            Filtres:'Filter',
            Quitter:'Exit',
            PJ:'Attachement(s)',
            Actions:'Actions',
            Enregistrer:'Save',
            Ajouter:'Add',
            Exporter:'Export',
            Modifier:'Update',
            Supprimer:'Delete',
            Administration:'Administration',
            Discussions:'Discussions',
            Calendrier:'Calendar',
            Applications:'Applications',
            Configuration:'Configuration',
            Documentation:'Documentation',
            Assistance:'Assistance',
            Préference:'Preference',
            UPPWD:'Change the Password',
            Déconnexion:'Log out',
            MAIL:'Mailbox',
            Conversation:'Tchat',
            Administrateur:'Administrator',
            EVENTTITLE:'Event Title',
            Participants:'Participants',
            EVENTDATE:'Event date',
            Options:'Options',
            Nom:'Name',
            EMAIL:'Mail adrdress',
            Socièté:'Partner',
            Description:'Description',
            BEGINDATETIME:'Start date and time',
            Rappels:'Reminders',
            Confidentialite:'Privacy',
            ALLPEOPLE:'Everybody',
            ONLYME:'Only me',
            INNERONLY:'Internal user only',
            SHOWHOURS:'Show time as',
            Libre:'Free',
            Occupée:'Occupied',
            Duree:'Duration',
            CALENDAROPTIONS:'Calendar options',
            ADDELEMENT:'Add an element',
            Lieu:'Place',
            ALLDAY:'All day'
        });
        $translateProvider.translations('fr',{
            PJ:'Pièce(s) Jointe(s)',
            UPPWD:'Modifier le Mot de passe',
            MAIL:'Boîte de réception',
            EVENTTITLE:'Titre evenement',
            EVENTDATE:'Date evenement',
            EMAIL:'Adresse mail',
            BEGINDATETIME:'Date et heure de debut',
            ALLPEOPLE:'Tout le monde',
            ONLYME:'Moi seulement',
            INNERONLY:'Utilisateur interne seulement',
            SHOWHOURS:'Afficher heure comme',
            CALENDAROPTIONS:'Options du calendrier',
            ADDELEMENT:'Ajouter un element',
            ALLDAY:'Toute la journee'
        });
        $translateProvider.preferredLanguage('fr');
            $routeProvider.when("/edit" , {
                templateUrl:"modules/calendrier/views/editView.html" ,
                controller:function($scope,$timeout){
                               $timeout(function(){
                                    $('.selectpicker').selectpicker('refresh');
                                });
                }
            });
            $routeProvider.when("/list" , {
                templateUrl:"modules/calendrier/views/calendarListPanel.html" 
            });
            $routeProvider.when("/message" , {
                templateUrl:"modules/calendrier/views/editView.html" 
            });
            $routeProvider.when("/profile" , {
                templateUrl:"modules/calendrier/views/editView.html" 
            });
            $routeProvider.otherwise({
                templateUrl:"modules/calendrier/views/calendarPanel.html" 
            });
        });
angular.module('keren.core.calendar')
        .controller('calendarCtrl',['$rootScope','$scope','$translate','$timeout','$location','$http','uiCalendarConfig','commonsTools','backendService'
        , function($rootScope,$scope,$translate,$timeout,$location,$http , uiCalendarConfig,commonsTools,backendService){
                $scope.tableheaderselected = false;
                $scope.userslist = new Array();
                $scope.onCheckboxClick = function(){        
                            $scope.tableheaderselected = !$scope.tableheaderselected;

                            for(var i=0 ; i<$scope.events.length;i++){
                               var event = $scope.events[i];	     	 	 		  
                                   event.selected = $scope.tableheaderselected;
                            }

                        for(var i=0 ; i<$scope.events.length;i++){
                             var event = $scope.events[i];	     
                             //console.log("Vous avez cliquez sur "+module.name+" === "+module.shortDescription+"==="+module.selected); 
                        }  
               };
                /**
             Selectionne ou desselectionne une ligne
           **/
          $scope.onRowCheckboxClick = function(item){
              //console.log("onRowCheckboxClick  ============== "+item);
              item.selected = !item.selected;

              if(item.selected){
                if(!commonsTools.contains($scope.selectedObjects , item)){
                     $scope.selectedObjects.unshift(item);
                }
              }else{
                commonsTools.removeFromArray($scope.selectedObjects , item);
                if($scope.selectedObjects.length===0){
                  $scope.tableheaderselected = false;
                }
              }
              $scope.currentObject = $scope.selectedObjects[0];

          };
                /**
                 * Declaration des champs
                 */
                var date = new Date();  
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                
                /**
                 * 
                 */
                $scope.dataCache = {
                     rappels : [{id:'load' ,designation:'Charger les donn�es'}],
                     users : [{id:'load' ,designation:'Charger les donn�es'}],
                     crrentRappel :{}
                };
                
                 $scope.listViewType = 'calendar';
                 
                 $scope.previousType = 'calendar';
                 
                 $scope.windowType = 'new';
    
                $scope.eventSources = [];
                
                $scope.metaData = {};
                
                $scope.selectedObjects = [];
                
                //Element selectionn�
                $scope.selectedEvent = null ;
                //Verifie que c'est la premiere fois
                var isFirstTime = true ;
                //Liste des events en provenance de la BD
                $scope.events = [
//                    {title: 'All Day Event',start: new Date(y, m, 1),selected:false},
//                    {title: 'Long Event',start: new Date(y, m, d - 5),end: new Date(y, m, d - 2),selected:false},
//                    {id: 999,title: 'Repeating Event',start: new Date(y, m, d - 3, 16, 0),allDay: false,selected:false},
//                    {id: 999,title: 'Repeating Event',start: new Date(y, m, d + 4, 16, 0),allDay: false,selected:false},
//                    {title: 'Birthday Party',start: new Date(y, m, d + 1, 19, 0),end: new Date(y, m, d + 1, 22, 30),allDay: false,selected:false},
//                    {title: 'Click for Google',start: new Date(y, m, 28),end: new Date(y, m, 29),url: 'http://google.com/',selected:false}
                ];
                //Event source
                $scope.eventSources=[$scope.events];
                //Configuration du calendrier
                $scope.uiConfig ={
                    calendar:{
                        height:480,
                        editable:true,
                        displayEventTime:false,
                        header:{
                            left:'prev,today,next,month,agendaWeek,agendaDay',
                            center:'title',
                            right:' '
                        },
                        selectable:true,
                        selectHelper:true,
                        select:function(start,end){
                              $scope.dataCache.crrentRappel={titre:null,quantite:null,unite:0};
                              $scope.listViewType ='edit';
                              $scope.windowType = 'new';
                              $scope.previousType = 'calendar';                              
                              $scope.selectedEvent = commonsTools.createEmptyObject($scope.metaData);
                              $scope.selectedEvent.start = new Date(start);;
                              $scope.selectedEvent.end = new Date(end); ;
                              $location.path("/edit");
                              $('.selectpicker').selectpicker('refresh');
                             //console.log("Vous avez cliquez sur selectionner");
                        },
                        eventClick:function(event){
                            $scope.selectedEvent=event;
                            $scope.listViewType ='edit';
                            $scope.windowType = 'update';
                            $scope.previousType = 'calendar';//Notification du changement du module
                            $rootScope.$broadcast("calendarEventClick" , {event:event});                    
                        },
                        eventAfterAllRender:function(){
                            if($scope.events.length>0 && isFirstTime){
                                //Focus first event
                            //      console.log("=============== "+angular.toJson(uiCalendarConfig));
                                  if(uiCalendarConfig.calendars && uiCalendarConfig.calendars.myCalendar){
                                    uiCalendarConfig.calendars.myCalendar.fullCalendar('gotoDate',$scope.events[0].start);
                                  }
                                  isFirstTime = false;
                           }
                        }
                    }
                };
                /**
                 * 
                 * @param {type} item
                 * @returns {undefined}
                 */
                $scope.removeUser = function(item){
                    var participants = $scope.selectedEvent.participants;
                    if(participants){
                        for(var i=0 ; i<participants.length;i++){
                            var user = participants[i];
                            if(user.compareid===item.compareid){
                                participants.splice(i , 1);
                            }//end if(user.id==item.id)
                        }//end for(var i=0 ; i<participants.length;i++)
                    }//end if(participants)
                };
                /**
                 * Affiche la liste des evenements
                 * Liste evenements
                 * @returns {undefined}
                 */
                $scope.switchToList = function(){
                    $scope.selectedObjects = [];
                    $scope.listViewType = 'list';
                    $location.path("/list");
                    $('.selectpicker').selectpicker('refresh');
                    //console.log("Vous avez cliqu� sur switchToCalendar");
                };
                
                /**
                 * Affiche la liste des evenements
                 * sous forme de calendrier
                 * @returns {undefined}
                 */
                $scope.switchToCalendar = function(){
//                    var data = $rootScope.globals.user;
//                    if(data.langue.codeISO=="en"){
//                        $translate.use('en');
//                    }//end if(data[0].langue.codeISO=="fr_FR"){
                    $scope.selectedObjects = [];
                    $scope.listViewType = 'calendar';
                    $scope.selectedEvent= null;
                    $scope.listViewType ='calendar';
                    $scope.windowType = 'new';
                    $scope.previousType = 'calendar';
                    $location.path("/calendar");
                    $('.selectpicker').selectpicker('refresh');
                    //console.log("Vous avez cliqu� sur switchToCalendar");
                };
                /**
                 * Affiche la fenetre Edition
                 * @param {type} type
                 * @returns {undefined}
                 */
                $scope.switchToEdit = function(event){
                    $scope.selectedEvent=event;
                    $scope.selectedObjects = [];
                    $scope.selectedObjects.push(event);
                    $scope.listViewType ='edit';
                    $scope.windowType = 'update';
                    $scope.previousType = 'list';//Notification du changement du module
                    $rootScope.$broadcast("calendarEventClick" , {event:event});  
                    //console.log("Vous avez cliqu� sur switchToCalendar  : "+event);
                };
                //Show modal for edit event
                /**
                 * 
                 * @param {type} type
                 * @returns {undefined}
                 */
                $scope.showEditDialog = function(){
                    var url="http://"+$location.host()+":"+$location.port()+"/kerencore/utilisateur";
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    $http.get(url)
                            .then(function(response){
                                $scope.dataCache.utilisateur = response.data;
                                commonsTools.hideDialogLoading();
                                $("#myModal00").modal("toggle");
                                $("#myModal00").modal("show");    
                                $('.selectpicker').selectpicker('refresh');
                            },function(error){
                                commonsTools.hideDialogLoading();
                                commonsTools.showMessageDialog(error);
                            });
                    
                };
                
                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.annulerAction = function(){
                    $scope.listViewType = $scope.previousType;
                    if($scope.listViewType=='list'){
                        $location.path('/list');
                    }else if($scope.listViewType=='calendar'){
                        $location.path('/calendar');
                    }
                };
                /**
                 * Load user Data
                 * @returns {undefined}
                 */
                $scope.getUserData = function(item){
//                  console.log(angular.toJson(item));
                  if($scope.dataCache.users.length<=0 || $scope.dataCache.users[0].id=='load'){  
                    $scope.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/utilisateur";
                    $http.get(url).then(
                            function(response){
                                $scope.dataCache.users = response.data;
                                $timeout(function(){
                                    $('.selectpicker').selectpicker('refresh');
                                });    
                                //$location.path("/edit");
                                commonsTools.hideDialogLoading();
                            },
                            function(error){
                                commonsTools.hideDialogLoading();
                                commonsTools.showMessageDialog(error);
                            });
                  }
                };
                
                /**
                 * 
                 * @param {type} item
                 * @returns {undefined}
                 */
                $scope.getRappelData = function(item){
                    //console.log(angular.toJson($scope.dataCache.rappels));
                    if($scope.dataCache.rappels.length<=0 || $scope.dataCache.rappels[0].id=='load'){  
                        commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                        var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/rappel";
                        $http.get(url).then(
                                function(response){
                                    $scope.dataCache.rappels = response.data;
                                    $('.selectpicker').selectpicker('refresh');
                                    $timeout(function(){
                                     $('.selectpicker').selectpicker('refresh');
                                   });//console.log(angular.toJson($scope.dataCache.users));
                                    commonsTools.hideDialogLoading();
                                },
                                function(error){
                                    commonsTools.hideDialogLoading();
                                    commonsTools.showMessageDialog(error);
                                });
                  }
                };
                
                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.addDialogAction = function(){
                      var datas = new Array();
                      var participants = $scope.dataCache.utilisateur;
                      if(participants){
                          for(var i=0 ;i<participants.length;i++){
                              var user = participants[i];
                              if(user.selected==true){
                                  datas.push(user);
                              }//end if(user.selected)
                          }//end for(var i=0 ;i<participants.length;i++)
                      }//end if(participants)
                      $scope.selectedEvent.participants = datas;
                      $('#myModal00').modal('hide');
                };
                $scope.addRappel = function(){
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/rappel";
                    if($scope.dataCache.crrentRappel.titre==null||$scope.dataCache.crrentRappel.quantite==null
                            ||$scope.dataCache.crrentRappel.unite==null){
                            //console.log(angular.toJson($scope.dataCache.crrentRappel));
                            commonsTools.hideDialogLoading();
                    }else{
                        $http.post(url,$scope.dataCache.crrentRappel).then(
                                function(response){
                                    $http.get(url).then(
                                        function(response){
                                            $scope.dataCache.rappels = response.data;
                                            $('.selectpicker').selectpicker('refresh');
                                            //console.log(angular.toJson($scope.dataCache.users));
                                            commonsTools.hideDialogLoading();
                                             $('#myModal002').modal('hide');
                                        },
                                        function(error){
                                            commonsTools.hideDialogLoading();
                                            commonsTools.showMessageDialog(error);
                                        });
                                },
                                function(error){
                                    commonsTools.hideDialogLoading();
                                    commonsTools.showMessageDialog(error);
                                }
                             );
                  }
                };
                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.addElementAction = function(){
                     $scope.dataCache.crrentRappel={titre:null,quantite:null,unite:0};
                    $scope.listViewType ='edit';
                    $scope.windowType = 'new';
                    $scope.previousType = 'list';
                    $scope.selectedEvent = commonsTools.createEmptyObject($scope.metaData);
//                    $scope.selectedEvent.start = start;
//                    $scope.selectedEvent.end = end ;
                    $location.path("/edit");
                };
                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.deleteAction = function(){
                    //Traitement
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    if($scope.selectedObjects.length>0){
                        backendService.url('event','kerencore');
                        backendService.deleteAll($scope.selectedObjects).$promise
                                .then(function(){
                                    for(var i=0 ;i<$scope.selectedObjects.length;i++){
                                        commonsTools.removeFromArray($scope.events,$scope.selectedObjects[i]);
                                    }
                                    $scope.listViewType = 'list';
                                    $location.path("/list");
                                    commonsTools.hideDialogLoading();
                                    commonsTools.notifyWindow("Status Operation" ,"L'opération s'est déroulée avec sucess","success");   
                                },function(error){
                                    commonsTools.hideDialogLoading();
                                    commonsTools.showMessageDialog(error);
                                });
                    }else{
                        commonsTools.hideDialogLoading();
                        commonsTools.notifyWindow("Aucun enregistrement n'est selectionn�" ,message,"warning");
                    }
                };
                /**
                 * 
                 * @returns {undefined}
                 */
                $scope.saveorupdateAction = function(){
                    
                    //$scope.selectedEvent.duree = 
                    //console.log(angular.toJson($scope.selectedEvent)+"  ====  "+angular.isString($scope.selectedEvent.duree)+" === "+$scope.selectedEvent.duree);
                    $http.defaults.headers.common['userid']= $rootScope.globals.userinfo.id;  
                    
                    //Traitement
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    var validate = commonsTools.validateFields($scope.metaData,$scope.selectedEvent);
                    var uniqueContraints = commonsTools.uniqueContraints();
                    if(validate.length>0){
                        var message = "";
                        for(var i=0; i<validate.length;i++){
                            message = message+"<br/>"+validate[i];
                        }
                        commonsTools.hideDialogLoading();
                        commonsTools.notifyWindow("Les champs suivants sont incorrects" ,message,"danger");     
                    }else{
                        $scope.selectedEvent.start = new Date($scope.selectedEvent.start);
                        $scope.selectedEvent.end = null;
                        if(!$scope.selectedEvent.allDay){                       
                            $scope.selectedEvent.end = commonsTools.computeDate($scope.selectedEvent.start,$scope.selectedEvent.duree);
                        }
                        //Ajout de l'utilisateur courant
                        $scope.selectedEvent.owner = $rootScope.globals.userinfo;
                        backendService.url('event','kerencore');
                        if($scope.windowType=='update'){
                           backendService.update($scope.selectedEvent).$promise.then(
                                   function(entity){
                                       //$scope.events.push(entity);
//                                       $scope.selectedEvent = null;
//                                       $scope.listViewType = $scope.previousType;
//                                        if($scope.listViewType=='list'){
//                                            $location.path('/list');
//                                        }else if($scope.listViewType=='calendar'){
//                                            $location.path('/calendar');
//                                        }
//                                        $scope.hideDialogLoading();
                                         $rootScope.$broadcast("refreshList",{event:entity});
                                        commonsTools.notifyWindow("Status Operation" ,"L'opération s'est déroulée avec sucess","success");   
                                   },function(error){
                                       $scope.hideDialogLoading();
                                       commonsTools.showMessageDialog(error);
                                   });
                        }else if($scope.windowType=='new'){
                              backendService.uniqueProperties(uniqueContraints).$promise.then(
                                      function(response){
                                          if(response&&response.length>0){
                                                var message ="";
                                                for(var i=0; i<response.length;i++){
                                                    message = message+"<br/>"+response[i].fieldLabel;
                                                }
                                                commonsTools.notifyWindow("Les champs suivants violent la contrainte d'unicit�" ,message,"danger");
                                                commonsTools.hideDialogLoading();
                                          }else{
                                              backendService.save($scope.selectedEvent).$promise.then(
                                                 function(entity){                                             
                                                     $rootScope.$broadcast("refreshList",{event:entity});
                                                 },function(error){
                                                     commonsTools.hideDialogLoading();
                                                     commonsTools.showMessageDialog(error);
                                                 });
                                          }
                                      },function(error){
                                          commonsTools.hideDialogLoading();
                                          commonsTools.showMessageDialog(error);
                                      });
                        }
                    }
                };
                $scope.$on("refreshList",function(event,args){
                    var usersid = new Array();
                    for(var i=0;i<$scope.userslist.length;i++){
                        if($scope.userslist[i].selected){
                            usersid.push($scope.userslist[i].id);
                        }//end if($scope.userslist[i].selected)
                    }//end for(var i=0;i<$scope.userslist.length;i++)
                    $http.defaults.headers.common['usersid']= angular.toJson(usersid);  
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/event/event/"+$rootScope.globals.userinfo.id; 
                     $http.get(url).then(
                            function(response){
                                $scope.events = response.data;
                                 for(var i=0;i<$scope.events.length;i++){
                                    $scope.events[i].start = new Date($scope.events[i].start);
                                    if(!$scope.events[i].allDay){
                                        $scope.events[i].end = new Date($scope.events[i].end);
                                    }else{
                                        $scope.events[i].end = null;
                                    }
                                }//end for(var i=0;i<$scope.events.length;i++)
                                $scope.eventSources=[$scope.events];
                               $scope.selectedEvent = null;
                               $scope.listViewType = $scope.previousType;
                                if($scope.listViewType=='list'){
                                    $location.path('/list');
                                }else if($scope.listViewType=='calendar'){
                                    $location.path('/calendar');
                                    $scope.uiConfig.calendar.eventAfterAllRender();
                                }
                                commonsTools.hideDialogLoading();
//                                commonsTools.notifyWindow("Status Operation" ,"L'opération s'est déroulée avec sucess","success");   
                            },function(error){
                                commonsTools.hideDialogLoading();
                                commonsTools.showMessageDialog(error);
                            });           
                });
                /**
                 * 
                 */
                $scope.$on("calendarModule" , function(event , args){
                    //commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    $scope.listViewType = 'calendar';
                    $scope.selectedEvent= null;
                    $scope.listViewType ='calendar';
                    $scope.windowType = 'new';
                    $scope.previousType = 'calendar';
                    $scope.events = args.events;
                    for(var i=0;i<$scope.events.length;i++){
                        $scope.events[i].start = new Date($scope.events[i].start);
                        if(!$scope.events[i].allDay){
                            $scope.events[i].end = new Date($scope.events[i].end);
                        }else{
                            $scope.events[i].end = null;
                        }
                    }
                    $scope.metaData = args.metaData;
                    //Event source
                    $scope.eventSources=[$scope.events];
                    $location.path("/calendar");
                    //console.log("calendarModule action detect ... === "+angular.toJson($scope.events));
                });
                
                /**
                 * 
                 */
                $scope.$on("calendarEventClick" , function(event , args){
                            //console.log(angular.toJson($scope.selectedEvent.id));
                            commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                            backendService.url('event','kerencore');
                            backendService.findById($scope.selectedEvent.id).$promise.then(
                                    function(entity){                                  
                                        $scope.dataCache.crrentRappel = entity.rappel;
                                        $scope.dataCache.rappels = new Array();
                                        $scope.dataCache.users = new Array();
                                        $scope.dataCache.rappels.push({id:'load' ,designation:'Charger les données'});
                                        $scope.dataCache.rappels.push(entity.rappel);
                                        $scope.dataCache.users.push({id:'load' ,designation:'Charger les données'});
                                        for(var i=0 ; i<entity.participants.length;i++){
                                            $scope.dataCache.users.push(entity.participants[i]);
                                        }
                                        $scope.selectedEvent= entity;
                                        $scope.selectedEvent.start = commonsTools.convertToLocalDate(new Date(entity.start));
                                        $scope.listViewType ='edit';
                                        $scope.windowType = 'update';
                                        //$scope.previousType = 'calendar';
                                        $location.path("/edit");
                                        commonsTools.hideDialogLoading();
                                        $timeout(function(){
                                          $('.selectpicker').selectpicker('refresh');    
                                        });
                                        
                                    },function(error){
                                        commonsTools.hideDialogLoading();
                                        commonsTools.showMessageDialog(error);
                                    });                            
                            
                });
                $scope.$on("calendarModule" , function(event , args){
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/utilisateur";
                    $http.get(url)
                            .then(function(response){
                                $scope.userslist = response.data;
                            },function(error){
                                 commonsTools.showMessageDialog(error);
                            });
                });
                /**
                 * 
                 * @param {type} item
                 * @returns {undefined}
                 */
                $scope.userselect = function(user){                    
                   $rootScope.$broadcast("refreshList",{event:null});
                };
                /**
                 * Charger par defaut calendrier
                 */
                $scope.switchToCalendar();
                
                
                /**

           **/
          $scope.showDialogLoading =function(texte, color, colorContent, topPos,leftPos) {
            $('body').append("<div id='dialogContent' style='width:100%;height:100%;position:absolute;z-index:2000;text-align:center;'></div>");
            $('#dialogContent').append("<div id='dialogWindow'></div>");
            $('#dialogWindow').append("<span id='dialogWindowText' style='text-align:center;padding:8px;padding-right:16px;padding-left:16px;display:inline-block;color:white;border-radius:3px;font-size:80%;'>"+texte+"</span>");
            
            
            //Changer le proprietes css
            $('#dialogContent').css("top",topPos);
            $('#dialogContent').css("left",leftPos);
            $('#dialogWindowText').css("color",color);
            $('#dialogWindowText').css("background-color",colorContent);
            
            //Afficher le dialog
            $('#dialogContent').fadeIn();
          };

          $scope.hideDialogLoading =function() {
            $('#dialogContent').fadeOut(function(){
              $('#dialogContent').remove();
            });
          };
          
        }]);

