/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';
angular.module('keren.core.discussion' ,['pascalprecht.translate']);
angular.module('keren.core.discussion')
        .config(function($translateProvider){
             $translateProvider.translations('en',{
            CANAUX:'CHANNELS',
            INBOX:'INBOX',
            MSGEDIRECT:'DIRECT MESSAGES',
            INPUTMSGE:'Enter your Message',
            Administrateur:'Administrator'
        });
        $translateProvider.translations('fr',{
            MSGEDIRECT:'MESSAGES DIRECT',
            INPUTMSGE:'Saisir votre Message'           
        });
        $translateProvider.preferredLanguage('fr');
        });
angular.module('keren.core.discussion')
        .controller('discussionCtrl' , 
             /**
              * 
              * @param {type} $scope
              * @param {type} $rootScope
              * @param {type} commonsTools
              * @param {type} restService
              * @returns {undefined}
              */
           function($scope,$rootScope,$translate,commonsTools,restService,$http,$filter,$location,$interval,$compile){
               $scope.currentUser = null;
               //Canaux de communications
               $scope.canaux = new Array();
               $scope.messages = new Array();
               $scope.sendmessages = new Array(),
               $scope.message = null;
               $scope.canal = null; 
               $scope.connectedusers = new Array();
               $scope.connecteduser = null;
               $scope.windowTitle = "INBOX";
               $scope.messagestate = {} ;
               $scope.messagebody = "";
               $scope.messageobject = null;
               $scope.dataCache = new Object();
               $scope.windowType = "INBOX";
               
                $scope.pagination = {  
                                 beginIndex: 0,
                                 endIndex : 0,
                                 currentPage :0,
                                 pageSize : 15 ,
                                 totalPages : 0,
                                 enabledNext: true,
                                 enabledPrevious:true,
                                 /** Get the next pages **/
                                 next:function(){
                                     //Notification du chargement
                                     //Verification si on peut avance
                                     if(this.hasnext()){
                                         commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");  
                                         //this.currentPage = (this.currentPage*this.pageSize)%this.totalPages; 
                                         $scope.pagination.beginIndex = $scope.pagination.endIndex+1;                                        
                                         var url="http://"+$location.host()+":"+$location.port()+"/kerencore/";
                                         //Chargement des donnes
                                         if($scope.windowType=="INBOX"){
                                             url+="rmessage/inbox/"+$scope.currentUser.id+"/"+this.beginIndex+"/"+this.pageSize;
                                         }else if($scope.windowType=="CANAL"){
                                             url+="kmessage/canal/"+$scope.currentUser.id+"/"+$scope.canal.id+"/"+this.beginIndex+"/"+this.pageSize;
                                         }else{
                                             url+="kmessage/direct/"+$scope.currentUser.id+"/"+$scope.connecteduser.id+"/"+this.beginIndex+"/"+this.pageSize;
                                         }//end if($scope.windowType=='INBOX')
                                          $http.get(url)
                                                .then(function(response){
//                                                      console.log('$scope.loadData = function() :::::::::::::::: '+data);
                                                     if(response.data){
                                                         $scope.messages = response.data;
                                                         $scope.setImageIds($scope.messages);
                                                         $scope.loadMessagesResources($scope.messages);
                                                         $scope.pagination.currentPage = $scope.pagination.beginIndex;
                                                         $scope.pagination.endIndex = $scope.pagination.endIndex+$scope.pagination.pageSize;
                                                         if($scope.pagination.endIndex>$scope.pagination.totalPages){
                                                             $scope.pagination.endIndex = $scope.pagination.totalPages;
                                                         }
                                                         commonsTools.hideDialogLoading();
//                                                         $rootScope.$broadcast("dataLoad" , {
//                                                             message:"dataLoad"
//                                                         });
                                                     }
                                                } ,function(error){
                                                    commonsTools.hideDialogLoading();
                                                    commonsTools.showMessageDialog(error);
                                                });  
                                     }//end if(this.hasnext()){
                                 },
                                 hasnext:function(){
                                     //Verifie that current pages is less or egal than totalPages
                                     if(this.endIndex>=this.totalPages){
                                         this.enabledNext = false ;                                        
                                     }else{
                                         this.enabledNext = true;
                                     }
                                     return this.enabledNext;
                                 },                        
                                 hasprevious:function(){
                                     if(this.beginIndex<=0){
                                         this.enabledPrevious = false ;
                                     }else{
                                        this.enabledPrevious = true ;
                                    }
                                    return this.enabledPrevious;
                                 },
                                 previous:function(){
                                     if(this.hasprevious()){
                                         commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");  
                                         var interval = this.endIndex - this.beginIndex;
                                         this.beginIndex = this.beginIndex-this.pageSize-1;
                                         this.currentPage = this.beginIndex+1 ;
                                         if(this.beginIndex<0){
                                             this.beginIndex = 0;
                                             this.currentPage = 1;
                                         }
                                         if(interval>=this.pageSize){
                                             this.endIndex = this.endIndex - this.pageSize;
                                         }else{
                                             this.endIndex = this.endIndex - interval-1;
                                         }//end if(interval>=this.pageSize){
                                         if(this.endIndex<=0){
                                            this.endIndex = this.pageSize;                                                             
                                         }//end if(this.endIndex<=0)
                                         var url="http://"+$location.host()+":"+$location.port()+"/kerencore/";
                                         //Chargement des donnes
                                         if($scope.windowType=="INBOX"){
                                             url+="rmessage/inbox/"+$scope.currentUser.id+"/"+this.beginIndex+"/"+this.pageSize;
                                         }else if($scope.windowType=="CANAL"){
                                             url+="kmessage/canal/"+$scope.currentUser.id+"/"+$scope.canal.id+"/"+this.beginIndex+"/"+this.pageSize;
                                         }else{
                                             url+="kmessage/direct/"+$scope.currentUser.id+"/"+$scope.connecteduser.id+"/"+this.beginIndex+"/"+this.pageSize;
                                         }//end if($scope.windowType=='INBOX')
                                         commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");   
                                         $http.get(url)
                                                .then(function(response){
//                                                      console.log('$scope.loadData = function() :::::::::::::::: '+data);
                                                     if(response.data){
                                                         $scope.messages = response.data; 
                                                         $scope.setImageIds($scope.messages);
                                                         $scope.loadMessagesResources($scope.messages);
                                                         commonsTools.hideDialogLoading();
//                                                         $rootScope.$broadcast("dataLoad" , {
//                                                             message:"dataLoad"
//                                                         });
                                                     }
                                                } ,function(error){
                                                    commonsTools.hideDialogLoading();
                                                    commonsTools.showMessageDialog(error);
                                                });  
                                     }//end if(this.hasprevious()){
                                 }
                                 
                           };
            /**
                Structure du module de configuration
            **/
            
             
            
//            $scope.goToCanal= function(){
//                var action = {id:-100,name:"show_canal_discussion" , label:"Elements de Menu",icon:"glyphicon glyphicon-th",entityName:"Canal",moduleName:"kerencore",modal:false,securitylevel:0,model:'kerencore'};
//                          $rootScope.$broadcast("discussionmodule" ,{
//                                   module:$scope.applicationsModule.groups[0] , verticalMenu:true,user:$scope.currentUser});  
//            };
            $scope.reset = function(){
                $scope.canaux = new Array();
                $scope.messages = new Array();
                $scope.message = null;
                $scope.canal = null; 
                $scope.connectedusers = new Array();
                //$scope.connecteduser = null;
            };
            $scope.loadCurrentUser = function(){
                $scope.reset();
                var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/utilisateur/bystringproperty/courriel/"+$rootScope.globals.currentUser.username;
                commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");  
                $http.get(url)
                        .then(function(response){
                            if(response.data.length>0){
                                $scope.currentUser = response.data[0];
//                                $scope.gotoinbox();
                                $scope.loadCanaux();
                                $scope.loadconnectedusers(); 
                                $scope.loadInboxMessages();
                            }
                        },function(error){
                            commonsTools.showDialogLoading(error);
                        });
            };
            /**
             * 
             * @returns {undefined}
             */
            $scope.loadInboxMessages = function(){
//                   var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/rmessage/nonlus/"+$scope.currentUser.id;
//                    $http.get(url)
//                            .then(function(response){
//                                $scope.numberofnewmessages = response.data;                         
//                            },function(error){
//                                commonsTools.showDialogLoading(error);
//                            });
                    if($scope.currentUser==null){
                        return ;
                    }//end if($scope.currentUser==null)
                    $scope.connecteduser=null;
                    $scope.canal = null;
                    $scope.windowType = "INBOX";                
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/rmessage/inboxcount/"+$scope.currentUser.id;
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    $http.get(url)
                            .then(function(response){
                                $scope.pagination.beginIndex = 0 ;
                                $scope.pagination.totalPages = response.data;
                                 var url2 = "http://"+$location.host()+":"+$location.port()+"/kerencore/rmessage/inbox/"+$scope.currentUser.id+"/"+$scope.pagination.beginIndex+"/"+$scope.pagination.pageSize;
                                $http.get(url2)
                                        .then(function(response){
                                            $scope.messages = response.data;
                                            $scope.setImageIds($scope.messages);
                                            $scope.loadMessagesResources($scope.messages);
                                            $scope.pagination.currentPage = 1;
                                            if($scope.messages.length<=0){
                                                $scope.pagination.currentPage = 0;
                                            }//end if($scope.messages.length<=0)
                                            $scope.pagination.endIndex = $scope.messages.length;
//                                            $scope.buildMessageTableView($scope.messages);
                                            commonsTools.hideDialogLoading();
                                        },function(error){
                                            commonsTools.showDialogLoading(error);
                                            commonsTools.hideDialogLoading();
                                        });
                            },function(error){
                                commonsTools.showDialogLoading(error);
                                commonsTools.hideDialogLoading();
                            });
                   
            };
            /**
             * 
             * @returns {undefined}
             */
            $scope.loadCanaux = function(){
                 $scope.currentUser = $rootScope.globals.userinfo;
//                 console.log(angular.toJson($scope.currentUser));
                var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/canal/canaux/"+$scope.currentUser.courriel;
                commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                $http.get(url)
                        .then(function(response){
                            $scope.canaux = response.data;
                            commonsTools.hideDialogLoading();
                        },function(error){
                            commonsTools.hideDialogLoading();
                            commonsTools.showDialogLoading(error);
                        });
                
            };
            /**
             * 
             * @returns {undefined}
             */
            $scope.loadconnectedusers = function(){
                   $scope.currentUser = $rootScope.globals.userinfo;
                   if(!angular.isDefined($scope.currentUser)){
                       return ;
                   }
//                 console.log(angular.toJson($scope.currentUser));
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/canal/directe/"+$scope.currentUser.courriel;
    //                commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    $http.get(url)
                            .then(function(response){
                                $scope.connectedusers = response.data;
                                commonsTools.hideDialogLoading();
                            },function(error){
                                commonsTools.hideDialogLoading();
                                commonsTools.showDialogLoading(error);
                            });
            };
            $scope.viewSelector = function(viewType){
                if(viewType=='inbox'){
                    return "<div id='bodyContent'  style='width:100%;height:75%;overflow: auto; padding:8px'><table class='table table-inbox table-hover' id='t_messages_id'> <tbody> <tr class='unread'> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message  dont-show'>PHPClass</td> <td class='view-message '>Added a new class: Login Class Fast Site</td> <td class='view-message  inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message  text-right'>9:27 AM</td> </tr> <tr class='unread'> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Google Webmaster </td> <td class='view-message'>Improve the search presence of WebSite</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>March 15</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>JW Player</td> <td class='view-message'>Last Chance: Upgrade to Pro for </td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>March 15</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Tim Reid, S P N</td> <td class='view-message'>Boost Your Website Traffic</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>April 01</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star inbox-started'></i></td> <td class='view-message dont-show'>Freelancer.com <span class='label label-danger pull-right'>urgent</span></td> <td class='view-message'>Stop wasting your visitors </td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>May 23</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star inbox-started'></i></td> <td class='view-message dont-show'>WOW Slider </td> <td class='view-message'>New WOW Slider v7.8 - 67% off</td> <td class='view-message inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message text-right'>March 14</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star inbox-started'></i></td> <td class='view-message dont-show'>LinkedIn Pulse</td> <td class='view-message'>The One Sign Your Co-Worker Will Stab</td> <td class='view-message inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message text-right'>Feb 19</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Drupal Community<span class='label label-success pull-right'>megazine</span></td> <td class='view-message view-message'>Welcome to the Drupal Community</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>March 04</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Facebook</td> <td class='view-message view-message'>Somebody requested a new password </td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>June 13</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Skype <span class='label label-info pull-right'>family</span></td> <td class='view-message view-message'>Password successfully changed</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>March 24</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star inbox-started'></i></td> <td class='view-message dont-show'>Google+</td> <td class='view-message'>alireza, do you know</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>March 09</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star inbox-started'></i></td> <td class='dont-show'>Zoosk </td> <td class='view-message'>7 new singles we think you'll like</td> <td class='view-message inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message text-right'>May 14</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>LinkedIn </td> <td class='view-message'>Alireza: Nokia Networks, System Group and </td> <td class='view-message inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message text-right'>February 25</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='dont-show'>Facebook</td> <td class='view-message view-message'>Your account was recently logged into</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>March 14</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Twitter</td> <td class='view-message'>Your Twitter password has been changed</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>April 07</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>InternetSeer Website Monitoring</td> <td class='view-message'>http://golddesigner.org/ Performance Report</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>July 14</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star inbox-started'></i></td> <td class='view-message dont-show'>AddMe.com</td> <td class='view-message'>Submit Your Website to the AddMe Business Directory</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>August 10</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Terri Rexer, S P N</td> <td class='view-message view-message'>Forget Google AdWords: Un-Limited Clicks fo</td> <td class='view-message inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message text-right'>April 14</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Bertina </td> <td class='view-message'>IMPORTANT: Don't lose your domains!</td> <td class='view-message inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message text-right'>June 16</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star inbox-started'></i></td> <td class='view-message dont-show'>Laura Gaffin, S P N </td> <td class='view-message'>Your Website On Google (Higher Rankings Are Better)</td> <td class='view-message inbox-small-cells'></td> <td class='view-message text-right'>August 10</td> </tr> <tr class=''> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message dont-show'>Facebook</td> <td class='view-message view-message'>Alireza Zare Login faild</td> <td class='view-message inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message text-right'>feb 14</td> </tr> </tbody> </table></div> ";
                }else if(viewType=="canal"){
                    return "<div id='bodyContent'  style='width:100%;height:75%;overflow: auto; padding:8px'><div style='height: 45px;display: inline-block;padding-left: 0px;border: solid 5px blue;' class='col-sm-12  col-md-12'> <div class='form-group  col-sm-11  col-md-11' style='padding-left: 0px; padding-right: 0px;'><input type='text' class='form-control'  id='name' placeholder='Saisir votre Message' ng-required='true' ng-minlength='2' style='width: 100%;' ng-model='messagebody'> </div> <div class='col-sm-1  col-md-1' style='padding:0px;height: 100%;'> <span style='display: inline-block;width: 45%;height: 100%;'> <div class='btn-group  dropdown'    role='group'  aria-label='group 2' style='width: 100%;height: 100%;'> <button type='button'  class='btn btn-default dropdown dropdown-toggle btn-sm' data-toggle='dropdown' aria-haspopup='false'  aria-expanded='true' id='pjidbtn' style='width: 100%;height: 100%;'> <span style='display: inline-block'> <span class='glyphicon glyphicon-paperclip'  aria-hidden='true' style='color:blue;'></span> <span class='caret'></span> </span> </button> <ul class='dropdown-menu'  role='menu'  aria-labelledby='imprimerbtn'   id='pjmenuid'> <li role='presentation'> <a role='menuitem' tabindex='-1' href='#' ng-click='imageClick(\"pjfileinput_id\")'> Ajouter </a> </li> </ul><input type='file' id='pjfileinput_id' style='display: none' fileinput='file'   onchange='angular.element(this).scope().gererChangementFichier(event)'></div> </span> <span style='display: inline-block;width: 50%;height: 100%;'> <button type='button'  class='btn btn-default btn-sm' ng-click ='sendAction()' style='width: 100%;height: 100%;'> <span class='glyphicon glyphicon-send' aria-hidden='true' style='color:blue'></span> </button> </span> </div> </div> <div style='height: 87%;width: 100%;overflow: auto;'> <table class='table table-inbox table-hover'  id = 't_messages_id'> <tbody> <tr class='unread'> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message  dont-show'>PHPClass</td> <td class='view-message '>Added a new class: Login Class Fast Site</td> <td class='view-message  inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message  text-right'>9:27 AM</td> </tr> </tbody> </table> </div></div>";
                }else if(viewType=="direct"){
                    return "<div id='bodyContent'  style='width:100%;height:75%;overflow: auto; padding:8px'><div style='height: 45px;display: inline-block;padding-left: 0px;border: solid 5px blue;' class='col-sm-12  col-md-12'> <div class='form-group  col-sm-11  col-md-11' style='padding-left: 0px; padding-right: 0px;'><input type='text' class='form-control'  id='name' placeholder='Saisir votre Message' ng-required='true' ng-minlength='2' style='width: 100%;' ng-model='messagebody'> </div> <div class='col-sm-1  col-md-1' style='padding:0px;height: 100%;'> <span style='display: inline-block;width: 45%;height: 100%;'> <div class='btn-group  dropdown'    role='group'  aria-label='group 2' style='width: 100%;height: 100%;'> <button type='button'  class='btn btn-default dropdown dropdown-toggle btn-sm' data-toggle='dropdown' aria-haspopup='false'  aria-expanded='true' id='pjidbtn' style='width: 100%;height: 100%;'> <span style='display: inline-block'> <span class='glyphicon glyphicon-paperclip'  aria-hidden='true' style='color:blue;'></span> <span class='caret'></span> </span> </button> <ul class='dropdown-menu'  role='menu'  aria-labelledby='imprimerbtn'   id='pjmenuid'> <li role='presentation'> <a role='menuitem' tabindex='-1' href='#' ng-click='imageClick(\"pjfileinput_id\")'> Ajouter </a> </li> </ul><input type='file' id='pjfileinput_id' style='display: none' fileinput='file'   onchange='angular.element(this).scope().gererChangementFichier(event)'></div> </span> <span style='display: inline-block;width: 50%;height: 100%;'> <button type='button'  class='btn btn-default btn-sm' ng-click ='sendAction()' style='width: 100%;height: 100%;'> <span class='glyphicon glyphicon-send' aria-hidden='true' style='color:blue'></span> </button> </span> </div> </div> <div style='height: 87%;width: 100%;overflow: auto;'> <table class='table table-inbox table-hover'  id = 't_messages_id'> <tbody> <tr class='unread'> <td class='inbox-small-cells'> <input type='checkbox' class='mail-checkbox'> </td> <td class='inbox-small-cells'><i class='fa fa-star'></i></td> <td class='view-message  dont-show'>PHPClass</td> <td class='view-message '>Added a new class: Login Class Fast Site</td> <td class='view-message  inbox-small-cells'><i class='fa fa-paperclip'></i></td> <td class='view-message  text-right'>9:27 AM</td> </tr> </tbody> </table> </div></div>";
                }
            };
            
            $scope.gotoselectcanal =  function(canal){
                $scope.canal = canal;
                $scope.windowTitle = "CONVERSATION / "+canal.code;
                $scope.windowType = "CANAL";
//                alert(angular.toJson(canal));
                if($scope.canal){ 
                    $scope.connecteduser = null;
                    $scope.messageobject = $scope.createemptyMessage($scope.canal,$scope.connecteduser,$scope.messagebody);
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    var url0 ="http://"+$location.host()+":"+$location.port()+"/kerencore/kmessage/countcanal/"+$scope.currentUser.id+"/"+$scope.canal.id;
                    $http.get(url0)
                            .then(function(response){
                                $scope.pagination.beginIndex=0;
                                $scope.pagination.totalPages = response.data;
                                var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/kmessage/canal/"+$scope.currentUser.id+"/"+$scope.canal.id+"/"+$scope.pagination.beginIndex+"/"+$scope.pagination.pageSize;
                                $http.get(url)
                                        .then(function(response){
                                            $scope.messages = response.data;
                                            $scope.setImageIds($scope.messages);
                                            $scope.loadMessagesResources($scope.messages);
                                            $scope.pagination.currentPage = $scope.pagination.beginIndex+1;
                                            $scope.pagination.endIndex = $scope.messages.length;
            //                                console.log("$scope.gotoselectcanal ====  "+$scope.messages.length);
//                                            $scope.buildMessageDivView($scope.messages);
                                            $scope.piecejointeMenu($scope.messageobject);
                                            commonsTools.hideDialogLoading();
                                        },function(error){
                                            commonsTools.hideDialogLoading();
                                            commonsTools.showDialogLoading(error);
                                        });
                            },function(error){
                                 commonsTools.hideDialogLoading();
                                 commonsTools.showDialogLoading(error);
                            });
                    
                   
                }//end if($scope.canal)
            };
            /**
             * 
             * @param {type} messages
             * @returns {undefined}
             */
            $scope.loadMessagesResources = function(messages){
                for(var i=0 ;i<messages.length;i++){            
                        $scope.loadMessageResources(messages[i]);             
                }
            };
            $scope.setImageIds = function(messages){
                for(var i=0 ;i<messages.length;i++){                    
                    messages[i].imageid = "sender"+messages[i].id;             
                }
            };
            /**
             * 
             * @param {type} messages
             * @returns {undefined}
             */
            $scope.loadMessageResources = function(message){
                if(message){
                    var image = "avatar.png";
                    if(message.sender!=null){
                        image = message.sender.image ;
                    }
//                    console.log("$scope.loadMessageResources ====================== "+image+"===="+message.imageid);
                    restService.downloadPNG(image,message.imageid);  
                }
            };
//            $scope.getpngurl = function(item){
//                return restService.getPNG_URL(item.image);
//            };
            $scope.buildCanalView = function(messages){
                   var viewElem = $scope.viewSelector("canal");                    
                    var tableElem = document.createElement('table');
                    tableElem.setAttribute('class','table table-inbox table-hover');
                    var tbody = document.createElement('tbody');
                    tableElem.appendChild(tbody);
                    if(messages){
                        for(var i=0;i<messages.length;i++){
                            var msge = messages[i];
                            var trElem = document.createElement('tr');
                            tbody.appendChild(trElem);
                            if(msge.typeMessage=='RECEPTION'){
                                trElem.setAttribute('class', "recievemsge"); 
                            };
                            var tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message  dont-show");
                            tdElem.appendChild(document.createTextNode(msge.sender.intitule));
                         //Marqueur de lecture    
                            tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message");
                            tdElem.appendChild(document.createTextNode(msge.body));                            
                            //piecejointe
                            tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message");
                            if(msge.piecesjointe){
                                var spanElem= document.createElement("span");
                                tdElem.appendChild(spanElem);
                                spanElem.setAttribute("style","display:inline-block;")
                                for(var j=0 ; j<msge.piecesjointe.length;j++){
                                    var pj = msge.piecesjointe[j];
                                    var span = document.createElement("span");
                                    spanElem.appendChild(span);
                                    span.setAttribute("class","glyphicon glyphicon-paperclip");
                                    span.setAttribute("aria-hidden","true");
                                    var aElem = document.createElement("a");
                                    aElem.setAttribute("href","#");
                                    aElem.setAttribute("ng-click","showpiecejointe('"+pj.id+"')");
                                    aElem.appendChild(document.createTextNode(pj.attachename)),
                                    span.appendChild(aElem);
                                }//end for(var j=0 ; j<msge.piecesjointe.lenth;j++)
                            }//end if(msge.piecesjointe)
                            //Heuree
                            tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message");
                            tdElem.appendChild(document.createTextNode(commonsTools.formatDat(new Date(msge.date))));
                        }//end for(var i=0;i<$scope.messages.length;i++)
                    }//end if($scope.messages){
                    viewElem = angular.element(viewElem);
                    var items = viewElem.find("table");
                    for(var i=0; i<items.length;i++){
                        if(items.eq(i).attr("id")=="t_messages_id"){
                              items.eq(i).replaceWith(tableElem);
                        }  
                    }
                    var compileFn = $compile(viewElem);
                    compileFn($scope);
                    
                ///Remplacement dans la vue
                $("#bodyContent").replaceWith(viewElem);
            };
            
            /**
             * 
             * @param {type} messages
             * @returns {undefined}
             */
            $scope.buildMessageTableView = function(messages){
                    if(messages.length<=0){
                        return ;
                    }//end if(messages.length<=0)
                    var viewElem = document.createElement("div");
                    viewElem.setAttribute("style","height: 87%;width: 100%;overflow: auto;");
                    viewElem.setAttribute("id","tablecontainer");
                    var tableElem = document.createElement('table');
                    tableElem.setAttribute('class','table table-inbox table-hover');
                    var tbody = document.createElement('tbody');
                    tableElem.appendChild(tbody);
                    if(messages){
                        for(var i=0;i<messages.length;i++){
                            var msge = messages[i];
                            var trElem = document.createElement('tr');
                            tbody.appendChild(trElem);
                            if($scope.windowType=="INBOX"){
                                if(msge.status==false){
                                    trElem.setAttribute('class', "unread"); 
                                }
                            }else{//end if($scope.windowType=='INBOX')
                                if(msge.typeMessage=="RECEPTION"){
                                    trElem.setAttribute('class', "recievemsge"); 
                                }
                            }//end if($scope.windowType=='INBOX')
                            var tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message  dont-show");
                            if(msge.sender!=null){
                                tdElem.appendChild(document.createTextNode(msge.sender.intitule));
                            }else{
                                tdElem.appendChild(document.createTextNode("System"));
                            }
                              //Marqueur de lecture  
                            if($scope.windowType=="INBOX"){  
                                tdElem = document.createElement('td');
                                trElem.appendChild(tdElem);
                                tdElem.setAttribute('class',"view-message");
                                if(msge.status==false){
                                    var aElem = document.createElement("a");
                                    tdElem.appendChild(aElem);
                                    aElem.setAttribute("href","#");
                                    aElem.setAttribute("ng-click","marquerlu('"+msge.id+"')");
                                    var span = document.createElement("span");
                                    aElem.appendChild(span);
                                    span.setAttribute("class","glyphicon glyphicon-eye-open");
                                    span.setAttribute("aria-hidden","true");
                                }//end if(msge.status==false)
                            }//end if($scope.windowType=='INBOX')
                         //Marqueur de lecture    
                            tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message");
                            tdElem.appendChild(document.createTextNode(msge.body));                            
                            //piecejointe
                            tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message");
                            if(msge.piecesjointe){
                                var spanElem= document.createElement("span");
                                tdElem.appendChild(spanElem);
                                spanElem.setAttribute("style","display:inline-block;")
                                for(var j=0 ; j<msge.piecesjointe.length;j++){
                                    var pj = msge.piecesjointe[j];
                                    var span = document.createElement("span");
                                    spanElem.appendChild(span);
                                    span.setAttribute("class","glyphicon glyphicon-paperclip");
                                    span.setAttribute("aria-hidden","true");
                                    var aElem = document.createElement("a");
                                    aElem.setAttribute("href","#");
                                    aElem.setAttribute("ng-click","showpiecejointe('"+pj.id+"')");
                                    aElem.appendChild(document.createTextNode(pj.attachename)),
                                    span.appendChild(aElem);
                                }//end for(var j=0 ; j<msge.piecesjointe.lenth;j++)
                            }//end if(msge.piecesjointe)
                            //Heuree
                            tdElem = document.createElement('td');
                            trElem.appendChild(tdElem);
                            tdElem.setAttribute('class',"view-message");
                            tdElem.appendChild(document.createTextNode(commonsTools.formatDat(new Date(msge.date))));
                        }//end for(var i=0;i<$scope.messages.length;i++)
                    }//end if($scope.messages){
                    viewElem.appendChild(tableElem);
                    var compileFn = $compile(viewElem);
                    compileFn($scope);
                    var items = $(document).find("div");
                    for(var i=0; i<items.length;i++){
                        if(items.eq(i).attr("id")=="tablecontainer"){
                              items.eq(i).replaceWith(viewElem);
//                              console.log("$scope.buildMessageTableView = function(messages) :::::: trouvé !!!!!!");
                        }  
                    }
                    
                    
                ///Remplacement dans la vue
//                $("#bodyContent").replaceWith(viewElem);
            };
            $scope.buildMessageDivView = function(messages){
                    if(messages.length<=0){
                        return ;
                    }//end if(messages.length<=0)
                    var viewElem = document.createElement("div");
                    viewElem.setAttribute("style","height: 87%;width: 100%;overflow: auto;");
                    viewElem.setAttribute("id","tablecontainer");
                    var ulElem = document.createElement("ul");
                    viewElem.appendChild(ulElem);
                    ulElem.setAttribute("class","media-list");
                    if(messages){
                        for(var i=0;i<messages.length;i++){
                            var msge = messages[i];
                            var liElem = document.createElement('li');
                            liElem.setAttribute("class","media");
                            ulElem.appendChild(liElem);
                            var divElem = document.createElement('div');
                            divElem.setAttribute("class","media-body");
                            liElem.appendChild(divElem);
                            //Deuxieme div
                            var divElem2 = document.createElement("div");
                            divElem2.setAttribute("class","media");
                            divElem.appendChild(divElem2);
                            //Image traitement
                            var aElem = document.createElement("a");
                            divElem2.appendChild(aElem);
                            aElem.setAttribute("href","#");
                            if(msge.typeMessage=="RECEPTION"){
                                aElem.setAttribute("class","pull-left");
                            }else {
                               aElem.setAttribute("class","pull-right");
                            } //end if(msge.typeMessage=="RECEPTION"){                           
                            var imgElem = document.createElement("img");
                            aElem.appendChild(imgElem);
                            imgElem.setAttribute("alt","Avatar");
                            imgElem.setAttribute("width","54");
                            imgElem.setAttribute("hieght","50");
                            imgElem.setAttribute("class","media-object img-circle");
                            var today = new Date();
                            imgElem.setAttribute("id","sender"+i);
                            restService.downloadPNG(msge.sender.image,"sender"+i);  
                            //Body
                            var divElem3 = document.createElement("div");
                            divElem2.appendChild(divElem3);
                            divElem3.setAttribute("class","media-body");
                            divElem3.appendChild(document.createTextNode(msge.body));
                            var brElem =document.createElement("br");
                            divElem3.appendChild(brElem);                           
                            //Traitement des pieces jointes                           
                             //span piece jointe
                            if(msge.piecesjointe && msge.piecesjointe.length>0){
                                 var spanElem = document.createElement("span");
                                 spanElem.setAttribute("style","display:inline-block");
                                 divElem3.appendChild(spanElem);                                 
                                for(var j=0 ; j<msge.piecesjointe.length;j++){
                                    var pj = msge.piecesjointe[j];
                                    var aElem2 = document.createElement("a");
                                    aElem2.setAttribute("style","padding-right:5px;");
                                    spanElem.appendChild(aElem2);
//                                    spanElem.setAttribute("class","glyphicon glyphicon-paperclip");
//                                    spanElem.setAttribute("aria-hidden","true");
                                    aElem2.setAttribute("href","#");
                                    aElem2.setAttribute("ng-click","showpiecejointe('"+pj.id+"')");
                                    aElem2.appendChild(document.createTextNode(pj.attachename));
//                                    span.appendChild(aElem);
                                }//end for(var j=0 ; j<msge.piecesjointe.lenth;j++)
                            }//end if(msge.piecesjointe)
                            divElem3.appendChild(document.createElement("br"));
                            var smallElem = document.createElement("small");
                            divElem3.appendChild(smallElem);
                            if(msge.typeMessage=="RECEPTION"){
                                smallElem.setAttribute("class","text-muted");
                            }else {
                               smallElem.setAttribute("class","text-muted pull-right");
                            } //end if(msge.typeMessage=="RECEPTION"){  
                            smallElem.appendChild(document.createTextNode(msge.sender.intitule+" | "+commonsTools.formatDat(new Date(msge.date)))+"  ");
                              //Marqueur de lecture  
                            if($scope.windowType=="INBOX"){                            
                                if(msge.status==false){
                                    var aElem = document.createElement("a");
                                    divElem3.appendChild(aElem);
                                    aElem.setAttribute("href","#");
                                    aElem.setAttribute("ng-click","marquerlu('"+msge.id+"')");
                                    var span = document.createElement("span");
                                    aElem.appendChild(span);
                                    span.setAttribute("class","glyphicon glyphicon-eye-open");
                                    span.setAttribute("aria-hidden","true");
                                }//end if(msge.status==false)
                            }//end if($scope.windowType=='INBOX')
                            divElem3.appendChild(document.createElement("hr"));
                           
                            //Heuree                            
                        }//end for(var i=0;i<$scope.messages.length;i++)
                    }//end if($scope.messages){
                    var compileFn = $compile(viewElem);
                    compileFn($scope);
                    var items = $(document).find("div");
                    for(var i=0; i<items.length;i++){
                        if(items.eq(i).attr("id")=="tablecontainer"){
                              items.eq(i).replaceWith(viewElem);
//                              console.log("$scope.buildMessageTableView = function(messages) :::::: trouvé !!!!!!");
                        }  
                    }
                    
                    
                ///Remplacement dans la vue
//                $("#bodyContent").replaceWith(viewElem);
            };
            $scope.shownolu = function(item){
                if($scope.windowType=="INBOX" && item.status==false)
                    return true;
                else return false;
            };
            $scope.formatDat = function(date){
                return commonsTools.formatDat(new Date(date));
            };
            /**
             * Chargement de message
             * @returns {undefined}
             */
            $scope.gotoinbox = function(messages){
                $scope.windowTitle = "INBOX";
                 var viewElem = $scope.viewSelector("inbox");
                 var tableElem = document.createElement('table');
                 tableElem.setAttribute('class','table table-inbox table-hover');
                 var tbody = document.createElement('tbody');
                 tableElem.appendChild(tbody);
                 if(messages){
                     for(var i=0;i<messages.length;i++){
                         var msge = messages[i];
                         var trElem = document.createElement('tr');
                         tbody.appendChild(trElem);
                         if(msge.status==false){
                             trElem.setAttribute('class', "unread"); 
                         };
                         var tdElem = document.createElement('td');
                         trElem.appendChild(tdElem);
                         tdElem.setAttribute('class',"view-message  dont-show");
                         if(msge.sender==null){
                             tdElem.appendChild(document.createTextNode("Système"));
                         }else{
                             tdElem.appendChild(document.createTextNode(msge.sender.intitule));
                         }
                         //Marqueur de lecture    
                        tdElem = document.createElement('td');
                        trElem.appendChild(tdElem);
                        tdElem.setAttribute('class',"view-message");
                        if(msge.status==false){
                            var aElem = document.createElement("a");
                            tdElem.appendChild(aElem);
                            aElem.setAttribute("href","#");
                            aElem.setAttribute("ng-click","marquerlu('"+msge.id+"')");
                            var span = document.createElement("span");
                            aElem.appendChild(span);
                            span.setAttribute("class","glyphicon glyphicon-eye-open");
                            span.setAttribute("aria-hidden","true");
                        }//end if(msge.status==false)
                        //Message body
                         tdElem = document.createElement('td');
                         trElem.appendChild(tdElem);
                         tdElem.setAttribute('class',"view-message");
                         tdElem.appendChild(document.createTextNode(msge.body));
                         //piecejointe
                         tdElem = document.createElement('td');
                         trElem.appendChild(tdElem);
                         tdElem.setAttribute('class',"view-message");                         
                         if(msge.piecesjointe){
                             var spanElem= document.createElement("span");
                             tdElem.appendChild(spanElem);
                             spanElem.setAttribute("style","display:inline-block;");
                             for(var j=0 ; j<msge.piecesjointe.length;j++){
                                 var pj = msge.piecesjointe[j];
//                                 console.log("========= "+angular.toJson(pj));
                                 var span = document.createElement("span");                                 
                                 spanElem.appendChild(span);
                                 span.setAttribute("class","glyphicon glyphicon-paperclip");
                                 span.setAttribute("aria-hidden","true");
                                 var aElem = document.createElement("a");
                                 aElem.setAttribute("href","#");
                                 aElem.setAttribute("ng-click","showpiecejointe('"+pj.id+"')");
                                 aElem.appendChild(document.createTextNode(pj.attachename)),
                                 span.appendChild(aElem);
                             }//end for(var j=0 ; j<msge.piecesjointe.lenth;j++)
                         }//end if(msge.piecesjointe)
                         //Heuree
                         tdElem = document.createElement('td');
                         trElem.appendChild(tdElem);
                         tdElem.setAttribute('class',"view-message");
                         tdElem.appendChild(document.createTextNode(commonsTools.formatDat(new Date(msge.date))));
                     }//end for(var i=0;i<$scope.messages.length;i++)
                 }//end if($scope.messages){
                 viewElem = angular.element(viewElem);
                 var items = viewElem.find("table");
                 for(var i=0; i<items.length;i++){
                     if(items.eq(i).attr("id")=="t_messages_id"){
                           items.eq(i).replaceWith(tableElem);
                     }  
                 }
                 var compileFn = $compile(viewElem);
                 compileFn($scope);
                 
                 $("#bodyContent").replaceWith(viewElem);
            };
            /**
             * 
             * @param {type} user
             * @returns {undefined}
             */
            $scope.gotodirectdiscussion = function(user){
                $scope.connecteduser = user;
                $scope.windowType = "DIRECT";
                $scope.windowTitle = "CHAT / "+user.intitule;
//                alert(angular.toJson(canal));
                if($scope.connecteduser){  
                    $scope.canal = null;
                    $scope.messageobject = $scope.createemptyMessage($scope.canal,$scope.connecteduser,$scope.messagebody);
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/kmessage/countdirect/"+$scope.currentUser.id+"/"+$scope.connecteduser.id;
                    $http.get(url)
                            .then(function(response){
                                $scope.pagination.beginIndex = 0;
                                $scope.pagination.totalPages = response.data;
                                 var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/kmessage/direct/"+$scope.currentUser.id+"/"+$scope.connecteduser.id+"/"+$scope.pagination.beginIndex+"/"+$scope.pagination.pageSize;
//                    console.log("$scope.gotodirectdiscussion = =========== "+url);
                                $http.get(url)
                                        .then(function(response){
                                            $scope.messages = response.data;
                                            $scope.setImageIds($scope.messages);
                                            $scope.loadMessagesResources($scope.messages);
                                            $scope.pagination.currentPage=$scope.pagination.beginIndex+1;
                                            $scope.pagination.endIndex =$scope.messages.length; 
            //                                console.log("$scope.gotoselectcanal ====  "+$scope.messages.length);
                                            //$scope.buildMessageDivView($scope.messages);
                                            $scope.piecejointeMenu($scope.messageobject);
                                            commonsTools.hideDialogLoading();
                                        },function(error){
                                            commonsTools.hideDialogLoading();
                                            commonsTools.showDialogLoading(error);
                                        });
                            },function(error){
                                commonsTools.hideDialogLoading();
                                commonsTools.showDialogLoading(error);
                            });
                   
                }//end if($scope.canal)                
//                alert(angular.toJson(canal));                
            };
            
            /**
             * 
             * @param {type} canal
             * @param {type} reciever
             * @param {type} body
             * @returns {controllers_L18.$scope.createemptyMessage.message|type.createemptyMessage.message|type@call;$asArray.createemptyMessage.message|cfg.type.createemptyMessage.message|Object|paramTypes@call;type.createemptyMessage.message}
             */
            $scope.createemptyMessage = function(canal,reciever,body){
                var message = new Object();
                message.id = -1;message.compareid=-1;message.designation=null;
                message.editTitle=null;message.listTitle=null;message.moduleName=null;
                message.selected=false;message.createonfield=true;message.desablecreate=false;
                message.desabledelete=false;message.serial=null;message.activefilelien=false;
                message.footerScript=null;message.activatefollower=false;message.date=new Date();
                message.status=false;message.piecesjointe=new Array();message.sender=$scope.currentUser;
                message.canal=canal;message.reciever=reciever;message.body=body;//message.senders=[];
                return message;
            };
            /**
             * 
             * @returns {undefined}
             */
            $scope.sendAction = function(){
                if($scope.messagebody && $scope.messagebody.trim()!=""){
//                    var message = $scope.createemptyMessage($scope.canal,$scope.connecteduser,$scope.messagebody);
                    $scope.messageobject.body = $scope.messagebody;   
//                    console.log("$scope.sendAction ==== "+$scope.currentUser.id+" ===== "+angular.toJson($scope.messageobject)+" ==== ");
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/smessage/send/"+$scope.currentUser.id;
                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    $http.post(url,$scope.messageobject)
                            .then(function(response){                               
                                        var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/kmessage/";///canal/"+$scope.currentUser.id+"/0/20";
                                        if($scope.windowType=="CANAL"){
                                            url +="canal/"+$scope.currentUser.id+"/"+$scope.messageobject.canal.id+"/0/20";
                                        }else{
                                            url +="direct/"+$scope.currentUser.id+"/"+$scope.messageobject.reciever.id+"/0/20";
                                        }//end if($scope.messageobject.canal!=null){
//                                         console.log("$scope.sendAction ====  ===== "+url+" ==== ");
                                        $http.get(url)
                                                .then(function(response){
                                                    $scope.messages = response.data;
                                                    $scope.setImageIds($scope.messages);
                                                    $scope.loadMessagesResources($scope.messages);
                                                    $scope.messagebody="";
                                                    $scope.messageobject = $scope.createemptyMessage($scope.canal,$scope.connecteduser,$scope.messagebody);
                                                    $scope.piecejointeMenu($scope.messageobject);
                                                    commonsTools.hideDialogLoading();
                                                },function(error){
                                                    commonsTools.hideDialogLoading();
                                                    commonsTools.showDialogLoading(error);
                                                });                                
//                                commonsTools.hideDialogLoading();
                            },function(error){
                                commonsTools.hideDialogLoading();
                                commonsTools.showDialogLoading(error);
                            });
                }else{
                    commonsTools.notifyWindow("Erreur" ,"Le corps du message ne peut être vide","danger"); 
                }//end if($scope.messagebody && $scope.messagebody.trim()!="")
            };
            /**
             * 
             * @param {type} message
             * @returns {undefined}
             */
            $scope.piecejointeMenu = function(message){                 
                  var divElem = document.createElement('div');
                  divElem.setAttribute('class','media');
                  divElem.setAttribute('id','pjmenuid');
                  var divElem1 = document.createElement('div');
                  divElem.appendChild(divElem1);
                  divElem1.setAttribute("class","media-body");
                  
                  var pbjmenu = document.querySelector('#pjmenuid');
                    if(pbjmenu!=null){
                         pbjmenu.replaceWith(divElem);
                    }                
                 commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%"); 
                   var pieces = message.piecesjointe;
                    if(pieces && pieces.length>0){
                        for(var i=0 ;i<pieces.length;i++){
                            var act = pieces[i];                            
                            var spanElem = document.createElement("span");
                            divElem1.appendChild(spanElem);
                            spanElem.setAttribute("style","display: inline-block;");
                            var span_1 = document.createElement("span");
                            span_1.setAttribute("style","inline-block;margin-right: 15px;");
                            spanElem.appendChild(span_1);
                            var span_2 = document.createElement("span");
                            span_2.setAttribute("style","inline-block;");
//                            span_2.setAttribute("ng-hide","desabledelete==true");
                            spanElem.appendChild(span_2);
                            var aElem = document.createElement('a');
                            span_1.appendChild(aElem);
                            aElem.setAttribute('role','menuitem');
                            aElem.setAttribute('tabindex','-1');
                            aElem.setAttribute('href','#');
                            aElem.setAttribute('ng-click',"piecejointeviewAction("+act.id+",'"+act.filename+"')");
                            aElem.setAttribute('style',"text-decoration: none;");
                            aElem.appendChild(document.createTextNode(act.attachename)) ;
                            aElem = document.createElement('a');
                            span_2.appendChild(aElem);
                            aElem.setAttribute('style','margin-right: 50;text-decoration: none;');
                            aElem.setAttribute('tabindex','-1');
                            aElem.setAttribute('href','#');
                            aElem.setAttribute('ng-click',"piecejointedeleteAction('"+act.filename+"')");
                            var span_3 = document.createElement('span');
                            aElem.appendChild(span_3);
                            span_3.setAttribute('class','glyphicon glyphicon-trash');
                            span_3.setAttribute('aria-hidden','true');
                        }//end for(var i=0 ;i<pieces.length;i++)    
                      }//end if(pieces && pieces.length>0) desableupdateedit                                      
                    var compileFn = $compile(divElem);
                    compileFn($scope);
                        commonsTools.hideDialogLoading();
//                            var divElem0 = document.createElement("div");
//                            divElem0.appendChild(divElem);
//                            alert(divElem0.innerHTML); 
//               return viewElem;
        };
         $scope.piecejointeMenuSave = function(message){                 
                  var ulElem = document.createElement('ul');
                  ulElem.setAttribute('class','dropdown-menu');
                  ulElem.setAttribute('role','menu');
                  ulElem.setAttribute('aria-labelledby','piecejointebtn');
                  ulElem.setAttribute("id","pjmenuid");  
                  var pbjmenu = document.querySelector('#pjmenuid');
                    if(pbjmenu!=null){
                         pbjmenu.replaceWith(ulElem);
                    }                
                 commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%"); 
                   var pieces = message.piecesjointe;
                    if(pieces && pieces.length>0){
                        for(var i=0 ;i<pieces.length;i++){
                            var act = pieces[i];
                            var liElem = document.createElement('li');
                            liElem.setAttribute('role','presentation');
                            ulElem.appendChild(liElem);
                            var spanElem = document.createElement("span");
                            spanElem.setAttribute("style","display: inline-block;");
                            liElem.appendChild(spanElem);
                            var span_1 = document.createElement("span");
                            span_1.setAttribute("style","inline-block;margin-right: 15px;");
                            spanElem.appendChild(span_1);
                            var span_2 = document.createElement("span");
                            span_2.setAttribute("style","inline-block;");
//                            span_2.setAttribute("ng-hide","desabledelete==true");
                            spanElem.appendChild(span_2);
                            var aElem = document.createElement('a');
                            span_1.appendChild(aElem);
                            aElem.setAttribute('role','menuitem');
                            aElem.setAttribute('tabindex','-1');
                            aElem.setAttribute('href','#');
                            aElem.setAttribute('ng-click',"piecejointeviewAction("+act.id+",'"+act.filename+"')");
                            aElem.setAttribute('style',"text-decoration: none;");
                            aElem.appendChild(document.createTextNode(act.attachename)) ;
                            aElem = document.createElement('a');
                            span_2.appendChild(aElem);
                            aElem.setAttribute('style','margin-right: 50;text-decoration: none;');
                            aElem.setAttribute('tabindex','-1');
                            aElem.setAttribute('href','#');
                            aElem.setAttribute('ng-click',"piecejointedeleteAction('"+act.filename+"')");
                            var span_3 = document.createElement('span');
                            aElem.appendChild(span_3);
                            span_3.setAttribute('class','glyphicon glyphicon-trash');
                            span_3.setAttribute('aria-hidden','true');
                        }//end for(var i=0 ;i<pieces.length;i++)    
                      }//end if(pieces && pieces.length>0) desableupdateedit
                    var liElem = document.createElement('li');
                    liElem.setAttribute('role','presentation');
                    ulElem.appendChild(liElem);
                    var aElem = document.createElement('a');
                    aElem.setAttribute('role','menuitem');
                    aElem.setAttribute('tabindex','-1');
                    aElem.setAttribute('href','#');
                    aElem.setAttribute('ng-click','imageClick("pjfileinput_id")');
                    aElem.appendChild(document.createTextNode('Ajouter')) ;                            
                    liElem.appendChild(aElem);      
                    ulElem.appendChild(liElem);                       
                    var compileFn = $compile(ulElem);
                    compileFn($scope);
                        commonsTools.hideDialogLoading();
//                            var divElem = document.createElement("div");
//                            divElem.appendChild(ulElem);
//                            alert(divElem.innerHTML); 
//               return viewElem;
        };
        $scope.gererChangementFichier = function(event){
              //Initiallisation du tableau des images
//              var fileInput = document.querySelector('#'+inputID);
//              console.log("$scope.gererChangementFichier = function(event) =============== ");
             var file = event.target.files[0];              
              var imgType = file.name.split(".");
              imgType = imgType[imgType.length - 1].toLowerCase();
              var date = new Date();
              var filename = date.getTime()+"."+imgType;             
              if(!$scope.dataCache['resources']){
                  $scope.dataCache['resources'] = new Array();
              }
              if(!$scope.dataCache['names']){
                  $scope.dataCache['names'] = new Array();
              }
//              var map = new Map();
//              map.set(filename,file);
              $scope.dataCache['resources'].push(file);
              $scope.dataCache['names'].push(filename);
              //transfert des resources et mise a jour du menu
//              var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/piecejointe";
              var data = {id:-1,compareid:-1,designation:"",editTitle:""
                    ,listTitle:"",moduleName:'kerencore',selected:false,createonfield:true,desablecreate:false,
                    serial:"1234",activefilelien:false,desabledelete:false,filename:filename,attachename:file.name,entityserial:null,entityid:null};
             $scope.messageobject.piecesjointe.push(data);
             commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%"); 
//             var url2 = "http://"+$location.host()+":"+$location.port()+"/kerencore/resource/temporalupload";
//             console.log("$scope.gererChangementFichier = function(event) =============== "+url2);
            $http.defaults.headers.common['names']= angular.toJson($scope.dataCache['names']); 
            restService.uploadFile2($scope.dataCache['resources'])
                .then(function(response){//                                                        alert(angular.toJson(response))                                                          
                    $scope.dataCache['resources'] = new Array();
                    $scope.dataCache['names'] = new Array(); 
                    $scope.piecejointeMenu($scope.messageobject);
                    commonsTools.hideDialogLoading();                                                        
                },function(error){
                    $scope.dataCache['resources'] = new Array();
                    $scope.dataCache['names'] = new Array();   
                    commonsTools.hideDialogLoading();
                    commonsTools.showDialogLoading(error);
//                    commonsTools.notifyWindow("ERREUR" ,"Le transfert des ressources a échoué <br> Veuillez consulter les logs pour plus de détails","success");
                }); 
                      
             //commonsTools.gererChangementImage(imageChooserInput,imageContent,apercuImageContent);
         
         
         };
         /**
          * 
          * @param {type} msgeID
          * @param {type} pjID
          * @returns {undefined}
          */
         $scope.showpiecejointe = function(pjID){
             $rootScope.$broadcast("showpiecejointe",{pjID:pjID});
         };
         $scope.marquerlu = function(msgeID){
             //@Path("marquer/{messageid}/{userid}/{index}/{max}")
             commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%"); 
             var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/rmessage/marquer/"+msgeID+"/"+$scope.currentUser.id+"/0/20";
             $http.get(url)
                     .then(function(response){
                         $scope.buildMessageTableView(response.data);
                         commonsTools.hideDialogLoading();
                     },function(error){
                         commonsTools.hideDialogLoading();
                         commonsTools.showDialogLoading(error);
                     });
         };
        $scope.piecejointedeleteAction = function(filename){
                commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%"); 
                var url2 = "http://"+$location.host()+":"+$location.port()+"/kerencore/resource/temporal/"+filename;
                $http({method:"DELETE" , url:url2
                }).then(function(response){
                     for(var i=0 ; i<$scope.messageobject.piecesjointe.length;i++){
                         var pj = $scope.messageobject.piecesjointe[i];
                         if(pj.filename==filename){
                             $scope.messageobject.piecesjointe.splice(i,1);
                         }//end if(pj.filename==filename)
                     }//end for(var i=0 ; i<$scope.messageobject.length;i++)
                     $scope.piecejointeMenu($scope.messageobject);
                     commonsTools.hideDialogLoading();
                },function(error){
                    commonsTools.hideDialogLoading();
                    commonsTools.showDialogLoading(error);
                });          
        };
        /**
         * 
         * @param {type} messages
         * @returns {undefined}
         */
        $scope.getMaxId = function(messages){
            if(messages){
                var max = 0;
                for(var i=0;i<messages.length;i++){
                    if(max<messages[i].id){
                        max = messages[i].id;
                    }
                }
                return max;
            }
            return 0;
        };
        /**
       * 
       * @param {type} key
       * @returns {undefined}
       */
        $scope.imageClick = function(key){
            $("#"+key).trigger('click');
//            console.log('$scope.imageClick ==========================='+key);
         };
        $scope.loadCurrentUser();
//        $scope.loadInboxMessages();
            /**
        * Action sur les canaux
        */
            $scope.$on("discussionmodule" , function(event , args){
        //           alert(args.action);
//                      alert("Vous avez selectionne Discussion ");
                      $scope.loadCurrentUser();
                      $scope.loadInboxMessages();
             });
              $scope.$on("login" , function(event , args){
        //           alert(args.action);
                     $scope.stop();
                   
             });
             /**
              * Refresh controleur
              * @type @call;$interval|@call;$interval
              */
             var DiscussionController=(function(){
                 var instance;
                 
                 function createInstance(){
                     var object = new Object();
                     object.errors = 0 ;
                     object.promise = null;
                     return object;
                 }
                 return {
                     getInstance:function(){
                         if(!instance){
                             instance = createInstance();
                         }//endif(!instance){
                         return instance;
                     }
                 };
             })();
             //Start inbox Inbox worker
//             var promise =   $interval(discussionwatcher,5000);
             /**
              * 
              * @returns {undefined}
              */
             $scope.start = function(){
                 var instance = DiscussionController.getInstance();
                 if(instance.promise===null){
                     instance.promise =   $interval(discussionwatcher,5000);
                 }else{
                     $scope.stop();
                     instance.promise =   $interval(discussionwatcher,5000);
                 }//end if(instance.promise===null){
                 
             };
             /**
              * 
              * @returns {undefined}
              */
             $scope.stop = function(){
                 var instance = DiscussionController.getInstance();
                 if(instance.promise!==null){
                     $interval.cancel(instance.promise);
                 }
             };
             function discussionwatcher(){ 
//                    console.log("$scope.gotoselectcanal ====  "+$scope.windowType+" ===== connectuser : "+$scope.connecteduser+" === user : "+angular.toJson($scope.currentUser));
                    //Mise a jour du nombre de messages
                   var instance = DiscussionController.getInstance();
                   if(instance.errors>0){
                       $scope.stop();
                       sweetAlert("Oops...", "Serveur Indisponible \nVeuillez contacter l'administrateur", "error");
                       instance.errors = 0;
                       $scope.start();
                       return ;
                   }//end if(instance.errors>4){
                   if($scope.currentUser!=null){
                            var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/rmessage/nonlus/"+$scope.currentUser.id;
                            $http.get(url)
                                    .then(function(response){
                                        instance.errors = 0;
                                        $scope.messagestate = response.data;
                                        if($scope.messagestate.unreadMessages>0){
                                            $rootScope.$broadcast("updatemessagesnumber",{value:$scope.messagestate.unreadMessages});
                                        }//end if($scope.numberofnewmessages>0)                                        
                                    },function(error){                                
                                        instance.errors = instance.errors+1;
                                        instance.error = error;
                                    });
                             //Traitement des messages
//                             console.log("$scope.gotoselectcanal ====  "+$scope.windowType+" ===== "+$scope.connecteduser);
                            
                            if($scope.windowType=="INBOX"){
//                                if($scope.canal!=null||$scope.connecteduser!=null){
//                                    return ;
//                                }
//                                var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/rmessage/inbox/"+$scope.currentUser.id+"/"+$scope.pagination.beginIndex+"/"+$scope.pagination.pageSize;
//                                $scope.windowType="INBOX";
//                                $http.get(url)
//                                    .then(function(response){
//                                        $scope.messages = response.data;
//                                        $scope.buildMessageTableView($scope.messages);
//                                    },function(error){
//                                        //console.error(error);
//                                    });
                            }else if($scope.windowType=="CANAL"){
                                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/kmessage/canal/"+$scope.currentUser.id+"/"+$scope.canal.id+"/"+$scope.pagination.beginIndex+"/"+$scope.pagination.pageSize;
                                    $scope.windowType="CANAL";
                                     $http.get(url)
                                            .then(function(response){
                                               // $scope.loadMessagesResources(response.data);
                                                var messages = response.data;
                                                for(var i=0 ; i<messages.length;i++){
                                                    if(!commonsTools.contains($scope.messages,messages[i])){
                                                        messages[i].imageid = "sender"+messages[i].id;
                                                         $scope.messages.unshift(messages[i]);
                                                        $scope.loadMessageResources(messages[i]);
                                                    }
                                                }
//                                                $filter('orderBy')($scope.messages,'date',true); 
                //                                console.log("$scope.gotoselectcanal ====  "+$scope.messages.length);
//                                                $scope.buildMessageDivView($scope.messages);
//                                                            $scope.piecejointeMenu($scope.messageobject);
                                                  commonsTools.hideDialogLoading();
                                            },function(error){
                                                  commonsTools.hideDialogLoading();
                                                  commonsTools.showDialogLoading(error);
                                            });
                            }else {
                                    if($scope.connecteduser==null){
                                        return ;
                                    }
                                    $scope.windowType="DIRECT";
                                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/kmessage/direct/"+$scope.currentUser.id+"/"+$scope.connecteduser.id+"/"+$scope.pagination.beginIndex+"/"+$scope.pagination.pageSize;
//                    console.log("$scope.gotodirectdiscussion = =========== "+url);
                                   $http.get(url)
                                           .then(function(response){
                                               //$scope.loadMessagesResources(response.data);
                                              var messages = response.data;
                                                for(var i=0 ; i<messages.length;i++){
                                                    if(!commonsTools.contains($scope.messages,messages[i])){
                                                        messages[i].imageid = "sender"+messages[i].id;                                                        
                                                        $scope.messages.unshift(messages[i]);
                                                        $scope.loadMessageResources(messages[i]);
                                                    }
                                                }
//                                                $filter('orderBy')($scope.messages,'date',true); 
                                           },function(error){
                                                    commonsTools.hideDialogLoading();
//                                                           commonsTools.showDialogLoading(error);
                                           });
                              }//end if($scope.windowType=="INBOX")                                        
//                                        
                              //Mise a jour de la liste des utilisateurs actuifs
                             $scope.loadconnectedusers();
                   }//end if($scope.currentUser!=null){
                }  ; 
                //Lancement de l'couteur
                $scope.start();
        });



