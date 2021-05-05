/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//variables de connexion
var USER_LOGIN = "";
var USER_PASSWORD = "";
var USER_ANNEE = "";
var USER_ANNEE_END = "";
var HOST_IP = "";
var HOST_PORT = "";
var annee=" ";
var api_key="";
var api_secret=

angular.module('keren.core.login' , ['ngResource','ngCookies','keren.core.commons']);

angular.module('keren.core.login')
        .controller('loginCtrl' , function($rootScope,$scope,$location,$http,authenticationService,commonsTools){
            $scope.dataLoading = true ;
            $scope.username = null;
            $scope.password = null;
            $scope.anneescolaire = null;
            $scope.remember = false;
            
            /**
             * 
             * @returns {undefined}
             */
            (function initController(){
                authenticationService.clearCredentials();
            })();
            
            /**
             * 
             * @returns {undefined}
             */
            $scope.login = function(){
                //console.log("Authentication Login methode === "+$scope.username+" === "+$scope.password);
                authenticationService.login($scope.username,$scope.password,$scope.anneescolaire)
                        .then(function(response){
                        	 // USER_ANNEE =document.getElementById("selectInput").value;//$scope.anneescolaire;
                            var urlPath = "http://"+$location.host()+":"+$location.port()+"/keren/auth/login/crypto"; 
                            $http.post(urlPath ,{username:$scope.username,password:$scope.password,anneescolaire:$scope.anneescolaire})
                                    .then(function(response){
                                        //console.log("$scope.login = function() remember == encrypt pwd : "+response.data);   
                                        
                                         //On recupere les parametres de connexion
                                        USER_LOGIN = $scope.username;
                                        USER_PASSWORD = response.data;
                                      //  console.log("afficher le mot de passe :::: "+USER_PASSWORD);
                                     //   console.log("login :::: "+USER_LOGIN);
                                        HOST_IP = $location.host();
                                        HOST_PORT = $location.port();
                                        //annee= parseLong($scope.anneescolaire)+1;
                                        if ($scope.anneescolaire!=null) {
                                        	USER_ANNEE_END= $scope.anneescolaire*1 + 1  ;
                                            USER_ANNEE= $scope.anneescolaire +" / "+USER_ANNEE_END  ; 
                                        	}
                                        
                                        
                                        //console.log("$scope.login = function() remember == encrypt pwd : "+USER_ANNEE); 
                                        
                                        authenticationService.setCredentials($scope.username,response.data,$scope.remember,$scope.anneescolaire);
                                    },function(error){
                                        commonsTools.notifyWindow("Echec authentification" ,"<br/>"+"Echec de recupération des paramètres ","danger");
                                        $rootScope.$broadcast("login" , {username:$scope.username , password:$scope.password,anneescolaire:$scope.anneescolaire});
                                    });
                           
                            //$location.path('/authenticate');
//                            console.log("Authentication Success === "+response);
                        },function(error){
                            //commonsTools.notifyWindow();
                            //$location.path('/login');
//                            console.log("Authentication Success === "+error);
                            commonsTools.notifyWindow("Echec authentification" ,"<br/>"+"Le login ou le mot de passe  ou l'annee scolaire est incorrect","danger");
                            $rootScope.$broadcast("login" , {username:$scope.username , password:$scope.password,anneescolaire:$scope.anneescolaire});
                        });
            };
            
            // get annee scolaire value from database
          
          $scope.anneescolaires = [
                                   { 'id': 2018, 'code' : '2018','designation' : '2018 / 2019' },
                                   { 'id': 2019, 'code' : '2019','designation' : '2019 / 2020' },
                                   { 'id': 2021, 'code' : '2020','designation' : '2020 / 2021' },
                                   { 'id': 2022, 'code' : '2020','designation' : '2021 / 2022' },
                                   { 'id': 2023, 'code' : '2020','designation' : '2022 / 2023' },
                                   { 'id': 2024, 'code' : '2020','designation' : '2023 / 2024' },
                                   { 'id': 2025, 'code' : '2020','designation' : '2024 / 2025' },
                                   { 'id': 2026, 'code' : '2020','designation' : '2026 / 2027' },
                                   { 'id': 2027, 'code' : '2020','designation' : '2027 / 2028' },
                                   { 'id': 2028, 'code' : '2020','designation' : '2028 / 2029' }
                                   ];
        $scope.anneescilaire = $scope.anneescolaires[1].code;
        
//        $scope.sendMessage = function(){
//        	//url send message
//        	 var urlPath = "https://api.camoo.cm/v1/sms.json"; 
//        	 api_kel="5cfcb9826595a";
//        	 api_secret="bd1d930a45d378580a1b948355d79e8054ed941dea91804ea0cfd339fc8af8f4"
//        	 
//        }
//            $http({
//                method : 'GET',
//                url:"http://"+$location.host()+":"+$location.port()+"/kerencore/anneglobal?username=admin@yahoo.fr&password=ISMvKXpXpadDiUoOSoAfww==",
//            }).then(function successCallback(response) {
//                $scope.anneescolaires = response.data.anneescolaires;
//            }, function errorCallback(response) {
//                console.log(response.statusText);
//            });      
          //  console.log("response.data=============="+response.data)
           // alert("response.data======="+response.data.anneescolaires);
            

            
        });

