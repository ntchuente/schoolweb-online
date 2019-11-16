/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module("keren.core",['keren.core.login','mainApp','keren.core.commons']);

angular.module("keren.core")
        .controller("kerenCtrl" ,function($scope,$rootScope,$http,$location,$interval,commonsTools){
              $scope.level = "login";
              //Login de l'utilisateur
              $scope.username = null ;
              
                /**
                * Evenement de login
                */
               $scope.$on("authenticate" , function(event ,args){
                   //Recuperation du login$
                   $scope.username = args.username ;
                   // console.log('$scope.$on("authenticate" , function(event ,args) :::::::::::::::: '+$scope.username);
                    $scope.level = "authenticate";
                    //Chargement des données de l'utilisateur authentifié   
//                    commonsTools.showDialogLoading("Chargement ...","white","#9370db","0%","0%");
                    var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/utilisateur/bystringproperty/courriel/"+$scope.username;
                    $http.get(url)
                            .then(function(response){
//                                console.log("Kerencontroller :::::::  "+angular.toJson(response.data));
                                if(response.data && response.data.length>0){
                                    $rootScope.globals.userinfo = response.data[0];
                                }//end if(response.data && response.data.length>0)
//                                commonsTools.hideDialogLoading();
                            },function(error){
                                commonsTools.hideDialogLoading();
//                                commonsTools.showMessageDialog(error);
                            });
               });

                /**
                * Echec de login de login
                */
               $scope.$on("login" , function(event ,args){
//                   console.log('$scope.$on("dataLoad" , function(event ,args) :::::::::::::::: '+angular.toJson(args));
                   //$location.path("/failed");
                   $scope.level = "login";
                   $scope.stopdiscussionworker();
                   
               });

               $scope.stopdiscussionworker = function(){
                   if(angular.isDefined($rootScope.globals.discussionworker)){
                       $interval.cancel($rootScope.globals.discussionworker);
                       $rootScope.globals.discussionworker = undefined;
                   }  
                };
        });
