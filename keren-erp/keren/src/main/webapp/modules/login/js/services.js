/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Service de gestion de l'utilisateur
 * @param {type} param1
 * @param {type} param2
 */
angular.module('keren.core.login')
        .factory('userService' ,function($http,backendService){
            backendService.url("utilisateur" , "kerencore");
            return {
                getAll : function(){
                             return backendService.findAll();
                        },
                getById: function(id){
                         return backendService.findById(id);  
                } ,
                getByUserName:function(username){
                    return backendService.findByUniqueProperty('courriel',username);
                },
                create:function(entity){
                    return  backendService.save(entity);
                },
                update:function(entity){
                    return backendService.update(entity);
                },
                delete:function(entity){
                    return backendService.delete(entity);
                }
            };
        });
          

angular.module('keren.core.login')
        .factory('authenticationService' ,function($http , $cookieStore,$location , $rootScope , $timeout , userService){
            
              // Base64 encoding service used by AuthenticationService
        var Base64 = {

            keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',

            encode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                do {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);

                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;

                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }

                    output = output +
                        this.keyStr.charAt(enc1) +
                        this.keyStr.charAt(enc2) +
                        this.keyStr.charAt(enc3) +
                        this.keyStr.charAt(enc4);
                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";
                } while (i < input.length);

                return output;
            },

            decode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
                var base64test = /[^A-Za-z0-9\+\/\=]/g;
                if (base64test.exec(input)) {
                    window.alert("There were invalid base64 characters in the input text.\n" +
                        "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                        "Expect errors in decoding.");
                }
                input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

                do {
                    enc1 = this.keyStr.indexOf(input.charAt(i++));
                    enc2 = this.keyStr.indexOf(input.charAt(i++));
                    enc3 = this.keyStr.indexOf(input.charAt(i++));
                    enc4 = this.keyStr.indexOf(input.charAt(i++));

                    chr1 = (enc1 << 2) | (enc2 >> 4);
                    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                    chr3 = ((enc3 & 3) << 6) | enc4;

                    output = output + String.fromCharCode(chr1);

                    if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                    }
                    if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                    }

                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";

                } while (i < input.length);

                return output;
            }
         };  
             return {
                 login:function(username,password,anneescolaire){
                     var urlPath = "http://"+$location.host()+":"+$location.port()+"/keren/auth/login/authenticate";           
                     return $http.post(urlPath
                                       ,{username:username,password:password,anneescolaire:anneescolaire});
                 },
                 /**
                  * 
                  * @param {type} username
                  * @param {type} password
                  * @returns {undefined}
                  */
                 setCredentials:function(username , password,remember,anneescolaire){
                     var authdata = Base64.encode(username + ':' + password);                     
                     $rootScope.globals = {
                         currentUser:{
                             username:username,
                             anneescolaire:anneescolaire,
                             authdata:authdata                             
                         }
                     };
                     //Set default auth header for http requests
                     $http.defaults.headers.common['Authorization']='Basic '+authdata;
                     //store user details in globals cookie that keeps user logged in for 1 week(or until they logout)
                     if(remember){
                        var cookieExp = new Date();
                        cookieExp.setDate(cookieExp.getDate(+7));
                        $cookieStore.put('globals',$rootScope.globals,{expires:cookieExp});
                     }
//                     console.log("$scope.login = function() remember == encrypt pwd 000: "+$scope.anneescolaire);
                     //console.log("$scope.login = function() remember == encrypt pwd 1111: "+anneescolaire);
                     $http.defaults.headers.common['anneeid']= anneescolaire; 
                     //Route vers l'application
                     $rootScope.$broadcast("authenticate" , {username:username 
                                               , password:password ,authdata:authdata , anneescolaire:anneescolaire});
                            
                 },
                 clearCredentials : function(){
                    $rootScope.globals={};
                    $cookieStore.remove('globals');
                     $http.defaults.headers.common.Authorization = 'Basic';
                }
                
             };
             
             
            
        });

