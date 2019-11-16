/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Module contenant tous les outils
 * @param {type} param1
 * @param {type} param2
 */
angular.module('keren.core.commons',['ngResource']);

angular.module('keren.core.commons')
        .directive('ace',['$timeout',function($timeout){
         var resizeEditor = function(editor,elem){
                  var lineHeight = editor.renderer.lineHeight;
                  var rows = editor.getSession().getLength();

                  $(elem).height(rows * lineHeight);
                  editor.resize();
                };
                return {
                  restrict:'A',
                  require:'?ngModel',
                  scope:true,
                  link:function(scope,elem,attrs,ngModel){
                    var node = elem[0];

                    var editor = ace.edit(node);

                    editor.setTheme('ace/theme/monokai');

                    ///var MarkdownMode = require('ace/mode/markdown').Mode;
                    editor.getSession().setMode('ace/mode/xml'); //new MarkdownMode()

                    // set editor options
                    editor.setShowPrintMargin(false);

                    // editor.setAutoScrollEditorIntoView(true);

                    // data binding to ngModel
                    ngModel.$render = function () {
                        editor.setValue(ngModel.$viewValue);
                        resizeEditor(editor, elem);
                    };

                    editor.on('change', function () {
                        $timeout(function () {
                            scope.$apply(function () {
                                var value = editor.getValue();
                                ngModel.$setViewValue(value);
                            });
                        });

                        resizeEditor(editor, elem);
                    });
                  }
                };
}]);
angular.module('keren.core.commons')
        .factory('commonsTools',function($filter,$compile,$http,$location){
            //Liste des contraintes
            var uniqueContraints = new Array();
            var stopTimer = 0;
            
            /**
             * Momento for navigation
             * @param {type} action: name of the action
             * @param {type} item:current data
             * @returns {undefined}
             */
            var NavigationItem = function(action, item){
                this.action = action;
                this.item = item;
            };
            NavigationItem.prototype={
                hydrate:function(){
                    var memento = angular.fromJson(this);
                    return memento;
                },
                dehydrate:function(memento){
                    var nav = angular.toJson(memento);
                    this.action = nav.action;
                    this.item = nav.item;
                },
                equals : function(memento){
                    return memento.action===this.action && memento.item===this.item;                      
                }
            };
            var NavigationContainer=function(){
                this.mementos = new Array();
                /**
                 * 
                 * @param {type} action
                 * @returns {undefined}
                 */
                this.addRule = function(action,item){
                    var memento = new NavigationItem(action,item);
                    var index = this.getKey(memento);
                    if(index<0){
                        this.mementos[this.mementos.length] = memento;
                    }else{
                        this.mementos[index] = memento;
                    }//end if(index<0){
                },
                this.getRule = function(key){
                    return this.mementos[key];
                },
                this.getKey = function(memento){
                    for(var i=0;i<this.mementos.length;i++){
                        var data = this.mementos[i];
                        if(memento.equals(data)===true){
                            return i;
                        }//end if(memento.equals(data)===true){
                    }//end for(var i=0;i<this.mementos.length;i++){
                    return -1 ;
                },
                this.getMementos = function(){
                    return this.mementos;
                },
                this.reset = function(){
                    this.mementos = new Array();
                },
                /**
                 * Back to one step
                 * @returns {undefined}
                 */
                this.gotoPreview = function(){
                    this.mementos.pop();
                    return this.mementos[this.mementos.length-1];
                },
                this.gotoPreviewIFExist = function(){
                    var memento = this.mementos[this.mementos.length-1];
                    if(memento.item!==null){
                        this.gotoPreview();
                    }//end if(memento.item!==null){
                    return memento;
                },
                this.lastPage = function(){
                    if(this.mementos.length===0) return null;
                    return this.mementos[this.mementos.length-1]
                },
                this.setMementos = function(mementos){
                    if(!angular.isDefined(mementos)
                            ||!angular.isArray(mementos)){
                        this.mementos = new Array();
                    }else{
                        this.mementos = mementos;
                    }
                }
            };
            var NotifyStatutPanel=(function(){
               var instance;
                function createInstance(){
                    var object = new Object();
                    object.active=false;
                    return object;
                }
                return{
                    getInstance: function(){
                        if(!instance){
                            instance = createInstance();
                        }
                        return instance;
                    }
                };
            })();
            /**
             * 
             * @param {type} idElement
             * @param {type} logo
             * @param {type} color
             * @param {type} opacity
             * @returns {undefined}
             */
            var showDialogLoadingFull = function(idElement, logo, color, opacity){
                    
                    $('#'+idElement).remove();
                
                    $('body').append("<div id="+idElement+" style='width:100%;height:100%;position:absolute;z-index:5000;text-align:center;background-color:black'></div>");
                    $('#'+idElement).append("<div id='dialogFullWindow' style='width:100%;margin:auto;margin-top:22%;color:white;text-align:center'>"+logo+"</div>");

                    //Changer le proprietes css
                    $('#'+idElement).css("opacity",opacity);
                    $('#dialogFullWindow').css("color",color);

                    //Afficher le dialog
                    $('#'+idElement).hide();
                    $('#'+idElement).fadeIn();
               };

            return {
                /**
                 * Return le container de navigation
                 * @returns {commons_L61.NavigationContainer}
                 */
                getNavigatorContainer:function(){
                    var container = new NavigationContainer();
                    return container;
                },
                /**
                 * 
                 * @param {type} texte
                 * @param {type} color
                 * @param {type} colorContent
                 * @param {type} topPos
                 * @param {type} leftPos
                 * @returns {undefined}
                 */
               showDialogLoading :function(texte, color, colorContent, topPos,leftPos) {
//                   console.log("commons.showDialogLoading ================== intree ")
                   var instance = NotifyStatutPanel.getInstance();
                   if(instance.active===false){
                       instance.active = true;
                        var idElement = "dialogContent";
                        $('#'+idElement).remove();

                         $('body').append("<div id="+idElement+" style='width:100%;height:100%;position:absolute;z-index:2000;text-align:center;'></div>");
                         $('#'+idElement).append("<div id='dialogWindow'></div>");
                         $('#dialogWindow').append("<span id='dialogWindowText' style='text-align:center;padding:8px;padding-right:16px;padding-left:16px;display:inline-block;color:white;border-radius:3px;font-size:80%;'>"+texte+"</span>");

                         //Changer le proprietes css
                         $('#'+idElement).css("top",topPos);
                         $('#'+idElement).css("left",leftPos);

                         $('#dialogWindowText').css("color","#b3b3b3");
						$('#dialogWindowText').css("background-color","white");

                         //Afficher le dialog
                         $('#'+idElement).fadeIn();

                         stopTimer = setTimeout(function(){

                                 $('#'+idElement).fadeOut(function(){
                                         showDialogLoadingFull(idElement+"_Full","<i class='fa fa-cog fa-spin fa-3x fa-fw'></i>","white","0.2");
                                         $('#'+idElement).remove();

                                         /*setTimeout(function(){
                                                 hideDialogLoading("open01");
                                         },6000);*/

                                 });

                                 //hideDialogLoading(idElement+"_Full");
                         },6000);
                     }
	
               },//end
               
               hideDialogLoading :function() {
//                   console.log("commons.hideDialogLoading ================== sortie ");
                    //On stoppe le moteur
                    var instance = NotifyStatutPanel.getInstance();
                    if(instance.active===true){
                        instance.active = false;
                        this.stopMoteur(stopTimer);
                        var idElement = "dialogContent";
                        //On cache
                        $('#'+idElement).fadeOut(function(){
                                $('#'+idElement).remove();
                        });

                        //On cache
                        $('#'+idElement+"_Full").fadeOut(function(){
                                $('#'+idElement+"_Full").remove();
                        });
                    }//end if(instance.active===true){
//                    console.log("commons.hideDialogLoading ================== sortie 2");
               },
               /**
                * 
                * @param {type} IdMoteur
                * @returns {undefined}
                */
               stopMoteur : function(IdMoteur){
                   clearInterval(IdMoteur);
               },
               /**
                * Affiche la notification en cas d'erreur
                * @param {type} title
                * @param {type} message
                * @param {type} type
                * @returns {undefined}
                */
               notifyWindow : function(title , message ,type){
                   $.notify(
                     {
                       title: "<strong>"+title+":</strong> ",
                       message: message

                     },{
                        type:type,
                        z_index: 5800,
                         animate: {
                           enter: 'animated fadeInRight',
                           exit: 'animated fadeOutRight'
                         }

                     }
                   );
               },
               /**
                * Affiche fenetre alerte
                * @param {type} title
                * @param {type} message
                * @param {type} type
                * @returns {undefined}
                */
               alertWindow : function(title , message ,type){
                   
               },
               //Fileds Validations
                 /**
                    Validate all the fields of the forms to chack constraint validation

                  **/
                 validateFields : function(metaData,currentObject){
                       var champs = new Array();
                       uniqueContraints =  new Array();
                       if(metaData && currentObject){
                           if(metaData.columns){
                              for(var i=0 ; i< metaData.columns.length;i++){
                                  if(!metaData.columns[i].optional || metaData.columns[i].min){
                                      if(!currentObject[metaData.columns[i].fieldName]){
                                          champs.push(metaData.columns[i].fieldLabel);
                                      }//end if(!currentObject[metaData.columns[i].fieldName]){
                                  }//end if(!metaData.columns[i].optional || metaData.columns[i].min){
                                  //Construction des champs pour unicite
                                  if(metaData.columns[i].unique){
                                      var pred = new Object();
                                      pred.fieldLabel = metaData.columns[i].fieldLabel;
                                      pred.fieldName =  metaData.columns[i].fieldName;
                                      pred.fieldValue = currentObject[metaData.columns[i].fieldName];
                                      uniqueContraints.push(pred);
                                  }//end if(metaData.columns[i].unique)
                              }//end for(var i=0 ; i< metaData.columns.length;i++){
                            }//end if(metaData.columns){
                            //Cas des groups
                            if(metaData.groups){
                               for(var i=0;i<metaData.groups.length;i++){
           //                        if($scope.metaData.groups[i].metaArray){
           //                            for(var j=0 ; j<$scope.metaData.groups[i].metaArray.metaData.columns.length ; j++){
           //                                if(!$scope.metaData.groups[i].metaArray.metaData.columns[j].optional || $scope.metaData.groups[i].metaArray.metaData.columns[j].min){
           //                                      champs.push($scope.metaData.groups[i].metaArray.metaData.columns[j].fieldLabel);
           //                                }
           //                            }
           //                        }
                                   //Cas des données normales
                                   if(metaData.groups[i].columns){
                                      for(var j=0 ; j< metaData.groups[i].columns.length;j++){
                                         if(!metaData.groups[i].columns[j].optional || metaData.groups[i].columns[j].min){
                                              var pred = new Object();
                                               pred.fieldLabel = metaData.groups[i].columns[j].fieldLabel;
                                               pred.fieldName =  metaData.groups[i].columns[j].fieldName;
                                               pred.value =  currentObject[metaData.groups[i].columns[j].fieldName];
                                               uniqueContraints.push(pred);
                                               // champs.push($scope.metaData.groups[i].columns[j].fieldLabel);
                                         }//end if(!metaData.groups[i].columns[j].optional || metaData.groups[i].columns[j].
                                         //Construction des champs pour unicite
                                        if(metaData.groups[i].columns[j].unique){
                                            uniqueContraints.push(metaData.groups[i].columns[j].fieldLabel);
                                        }//end if(metaData.groups[i].columns[j].unique){
                                      }//end for(var j=0 ; j< metaData.groups[i].columns.length;j++){
                                   }//end if(metaData.groups[i].columns){
                               }//end for(var i=0;i<metaData.groups.length;i++){
                            }//end if(metaData.groups){
                       }//end if(metaData && currentObject){
                       return champs;

                  },
                  /**
                   * Contriante unicite (appeler a pres l'apel de validateFields 
                   * @returns {commons_L14.commonsAnonym$1.uniqueContraints}
                   */
                  uniqueContraints:function(){
                      return  uniqueContraints;
                  },
                  /**
                   * Construit les composants 
                   * @param {type} columns
                   * @param {type} currentObject
                   * @returns {undefined}
                   */
                 createFromFields: function(columns,currentObject){
                       for(var i=0 ; i< columns.length;i++){
                            if(columns[i].type=='object'){
                              if(!currentObject[""+columns[i].fieldName+""]){
                                currentObject[""+columns[i].fieldName+""] = null;
                              }
                            }else if(columns[i].type=='array'){
                               if(!currentObject[""+columns[i].fieldName+""]) {
                                  currentObject[columns[i].fieldName] = new Array();
                                }
                                //console.log("createFromFields =====  "+$scope.currentObject[columns[i].fieldName]);                       

                                //object["'"+metaData.columns[i].fieldName+"'"].push($scope.createEmptyObject(metaData.columns[i].metaData));
                            }else{
                                if(!currentObject[""+columns[i].fieldName+""]) {
                                  currentObject[""+columns[i].fieldName+""] = "";
                                }
                            }
                        }
                 },
                 /**
                  * 
                  * @param {type} array
                  * @param {type} value
                  * @returns {Boolean}
                  */
                 containsLiteral:function(array , value){
//                     console.log("commonTool.containsLiteral:function(array , value) =============== tab : "+angular.toJson(array)+" ===== state : "+value);
                     if(!angular.isDefined(value)||value==null
                             || !angular.isDefined(array)||array==null){
                         return false ;
                     }//end if(!angular.isDefined(value)){  
                     for(var i=0 ; i<array.length;i++){
                         if(array[i]==value){
                             return true;
                         }
                     }//end for(var i=0 ; i<array.length;i++){
                     return false;
                 },
                /**
                 Create a empty object base of the metaData
                 @metaData : the description of the object
              **/
              createEmptyObject: function(metaData){            
                    var currentObject = new Object();
                     if(metaData){
                        //Cas des colonnes
                        if(metaData.columns){
                              this.createFromFields(metaData.columns,currentObject);                     
                        }
                        //Traitement des champs groups
                        if(metaData.groups){
                             //Traitement des groups
                             for(var i=0 ; i< metaData.groups.length;i++){
                                 //Cas des colonnes
                                 if(metaData.groups[i].columns){
                                    this.createFromFields(metaData.groups[i].columns,currentObject);
                                 }
                                 //cas des metaArray
                                 if(metaData.groups[i].metaArray){
                                     currentObject[metaData.groups[i].metaArray.fieldName] = new Array();
                                 }
                             }
                        }

                     } 
                    return currentObject;
                 },
                 /**
                  * Compute compute field
                  * @param {type} obj
                  * @param {type} currentObject
                  * @param {type} currentUser
                  * @param {type} metadata
                  * @returns {undefined}
                  */
             compteField:function(obj,currentObject,currentUser , metadata){
//                 console.log("commonsTools.expeval  ====== "+angular.toJson(obj)+" ======  "+angular.toJson(metadata));
                 if(obj && metadata){
                     //Traitement des columns
                     for(var i=0 ; i<metadata.columns.length;i++){
                         if(metadata.columns[i].compute==true&&metadata.columns[i].type=='number'){
                             obj[metadata.columns[i].fieldName] = this.expeval(obj,currentObject,currentUser,metadata.columns[i].value);
                         }//end if(metadata[i].compute==true)
                     }//end for(var i=0 ; i<metadata.length;i++)
                     //Traitement des groups
                     for(var i=0;i<metadata.groups.length;i++){
                         if(metadata.groups[i].columns){
                             for(var j=0;j<metadata.groups[i].columns.length;j++){
                                if(metadata.groups[i].columns[j].compute==true&&metadata.groups[i].columns[j].type=='number'){                                
                                    obj[metadata.groups[i].columns[j].fieldName]=this.expeval(obj,currentObject,currentUser,metadata.groups[i].columns[j].value);
                                }//end if(metadata.columns[i].compute==true&&metadata.columns[i].type=='number'){  
                             }//end for(var j=0;j<metadata.groups[i].columns.length;j++)
                         }//end if(metadata.groups[i].columns){
                     }//end for(var i=0;i<metadata.groups.length;i++)
                 }//end if(obj && metadata)
             },
            
            /**
             * Calcul la date end fonction de la date et heure
             * @param {type} date
             * @param {type} hours
             * @returns {undefined}
             * @date : date de debut
             * @hours: duree du au format hh:mm:ss
             */
            computeDate:function(date , hours){
                if(angular.isDate(date)){

                    var heu = 0;
                    var min = 0;
                    var sec = 0 ;
                    if(angular.isString(hours)){
                        var parts = hours.split(":");               
                        for(var i=0 ;i<parts.length;i++){
                            if(i==0){
                                heu = Number(parts[i]);
                            }else if(i==1){
                                min = Number(parts[i]);
                            }else if(i==2){
                                sec = Number(parts[i]);
                            }
                        }
                    }//end if(angular.isString(hours))
                    var result = date.getTime()+ (heu*60*60+min*60+sec)*1000;
                    return new Date(result);
                }

            },
         /**
          * 
          * @param {type} date
          * @returns {unresolved}
          */
         convertToLocalDate:function(date){
                 //var date = new Date();  
                var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                var h = date.getHours()<9 ? "0"+date.getHours():date.getHours();
                var m2 = date.getMinutes()<9 ? "0"+date.getMinutes():date.getMinutes();
                var s = date.getSeconds();
               var dateString = new String();
               dateString = y+"-"+m+"-"+d+"T"+h+":"+m2;
               return dateString;
         },
         /**
          * Attache keyup event on many-to-one and many-to-many 
          * components
          * @param {type} metaData
          * @returns {undefined}
          */
         selectpickerKeyup : function(metaData,model,scope){
             if(!angular.isDefined(metaData)||metaData==null){
                 return ;
             }//end if(!angular.isDefined(metaData)||metaData==null){
//             console.log("commons.selectpickerKeyup ================  ::: "+angular.toJson(metaData));
             for(var i=0;i<metaData.columns.length;i++){
                 var field = metaData.columns[i];
                 if(field.type=='object'){
//                     console.log("0 - commons.selectpickerKeyup ================  ::: "+field.fieldName+" === ");
                     this.keyupevent('manytoone',model,field.fieldName,scope);
                 }else if(field.target=='many-to-many'){
                     this.keyupevent('manytomany',model,field.fieldName,scope);
                 }//end if(field.type=='object'
             }//end for(var i=0;i<metaData.columns.length;i++){
             for(var i=0;i<metaData.groups.length;i++){
                 var group = metaData.groups[i];
                 for(var j=0;j<group.columns.length;j++){
                     var field = group.columns[j];
                     if(field.type=='object'){
                         this.keyupevent('manytoone',model,field.fieldName,scope);
                     }else if(field.target=='many-to-many'){
                         this.keyupevent('manytomany',model,field.fieldName,scope);
                     }//end if(field.type=='object'
                 }//end for(var j=0;j<group.columns.length;j++){
                 //Cas des MetaArray
             }//end for(var i=0;i<metaData.groups.length;i++){
         },      
         /**
          * 
          * @param {type} model
          * @param {type} fieldname
          * @returns {undefined}
          */
         keyupevent : function(target,model, fieldname,scope){
             $('#'+target+'-'+fieldname+' .form-control').on('keyup',
                       function(event){
                           var value =$('#'+target+'-'+fieldname+' input[class="form-control"]').val();
//                           console.log("commons.selectpickerKeyup ================  :::  === "+fieldname+" === "+value);
                           scope.keyupDataLoarder(model+'.'+fieldname,value);
                     });
             $('#'+target+'-'+fieldname+' .selectpicker').on('show.bs.select',
                       function(event){
                           var value =$('#'+target+'-'+fieldname+' input[class="form-control"]').val();
//                           console.log("commons.selectpickerKeyup ================  :::  === "+fieldname+" === "+value);
                           scope.keyupDataLoarder(model+'.'+fieldname,value);
                     });
         },
         /**
          * 
          * @param {type} template
          * @param {type} meta
          * @returns {undefined}
          */
         xmlListParser : function(template,meta){
             if(!angular.isDefined(template)
                     || template==null){
                 return null;
             }//end if(!angular.isDefined(template)
//             console.log("commons.xmlListParser ================================= "+template);
             var container = document.createElement('div');
             container.innerHTML = template;
             return container;
         },
         xmlViewParser : function(data,tree,meta){
              if(meta==null || !angular.isDefined(meta)){
                   return null;
              }
              var map = this.metaDataMapBuilder(meta);                 
              var metaData = meta;
              if(data){
                  metaData = new Object();
                  metaData['entityName'] = meta['entityName'];
                  metaData['moduleName'] = meta['moduleName'];
                  metaData['editTitle'] = meta['editTitle'];
                  metaData['listTitle'] = meta['listTitle'];
                  metaData['createonfield'] = meta['createonfield'];
                  metaData['desablecreate'] = meta['desablecreate'];
                  metaData['header'] = new Array();
                  metaData['columns'] = new Array();
                  metaData['groups'] = new Array();
                  var container = document.createElement('div');
                  container.innerHTML = data;
                  var form = $(container).find('formRecord');
                  //parametres descriptif de la vue
                  var label = $(form).attr('label');
                   if(label){
                       metaData['editTitle'] = label;
                   }
                  //Traitement des headers
                  var header = $(container).find('header');
                  if(header){
                       //traitement des buttons
                       var buttons = header.find('button');
                       if(buttons){
                           for(var i=0 ; i<buttons.length;i++){
                             var button = buttons.eq(i);
                              var column = new Object();
                               column['type'] = button.attr('type');
                               column['search'] = false;
                               column['target'] = button.attr('class');;
                               column['fieldName'] = button.attr('name');
                               column['fieldLabel'] = button.attr('label');
                               column['value'] = null;
                               column['optional'] = null;
                               column['unique']=null;
                               column['updatable'] = null;
                               column['min']=null;
                               column['max']=null;
                               column['pattern']=null;
                               column['sequence']=null;
                               column['metaData']=null;
                               column['colsequence']=null;
                           }//end for(var i=0 ; i<buttons.length;i++) 
                       }//end if(buttons)
                    }//end if(header){

                  //Traitement du sheet
                  var sheet = $(container).find('sheet');
                  //traitement des fields descendant du sheet
                  var fields = $(sheet).find('field');
                  var sequence = 1;
                  for(var i=0;i<fields.length;i++){
                     var name = fields.eq(i).attr('name');
                     var target = fields.eq(i).attr('target');
                     var columns = metaData['columns'];
                     //Si le champs name est definie
                     if(name){
                         columns[i] = map[name];
                         var column = columns[i];
                         column['sequence'] = sequence;
                         column['search'] = false;
                         if(target) {                            
                             column['target']=target;
                         }
                      }
                      sequence = sequence+1 ;
                  }
                  //Traitement des groups 
                  var groups = $(sheet).find('group'); 
                  for(var i=0;i<groups.length;i++){
                      var group = groups.eq(i);
                      var name = group['name'];
                      var label = group['label']
                      if(name){
                          var groupe = new Object();
                          groupe['name'] = name;
                          groupe['label'] = label;
                          groupe['sequence'] = i;
                          groupe['columns'] = new Array();
                          groupe['metaArray']=null;
                          //Traitement des champs
                          var fields = $(group).find('field');
                          for(var j=0 ; j<fields.length;j++){
                              var field = fields.eq(j);
                              var name = fields.eq(i).attr('name');
                              var target = fields.eq(i).attr('target');
                              if(name){
                                 var elem = map[name];
                                 elem['sequence'] = sequence;
                                 elem['search'] = false;
                                 if(target) {                            
                                     elem['target']=target;
                                 }//end if(target)
                                 if(elem['type']=='array'){
                                     groupe['metaArray'] = elem;
                                 }else{
                                      groupe['columns'].push(elem);
                                 }//end if(elem['type']=='array')                                
                              }//end if(name)
                              
                          }//end for(var j=0 ; j<fields.length;j++)
                      }//end if(name)
                  }//end for(var i=0;i<groups.length;i++)
               }//end if(data)
              //Traitement de la fenetre liste
              //console.log(tree);
              if(tree){
                  container = document.createElement('div');
                  container.innerHTML = tree;
                  var form = $(container).find('treeRecord');
                  //parametres descriptif de la vue
                  var label = $(form).attr('label');
                   if(label){
                       metaData['listTitle'] = label;
                   }
                  var fields = $(container).find('field');
                  for(var i=0;i<fields.length;i++){
                     var name = fields.eq(i).attr('name');
                     var target = fields.eq(i).attr('target');
                     var columns = metaData['columns'];
                     //Si le champs name est definie
                     if(name){
                         //columns[i] = map[name];
                         var column = map[name];
                         column['colsequence'] = i;
                         column['search'] = true;                            
                      }                         
                  }//end for(var i=0;i<fields.length;i++)
                  //console.log(fields.innerHTML);
              }//end if(tree)
              //console.log(angular.toJson(metaData));
              return metaData;
         },
         convertDateToString:function(date){
               var d = date.getDate();
                var m = date.getMonth();
                var y = date.getFullYear();
                var h = date.getHours()<9 ? "0"+date.getHours():date.getHours();
                var m2 = date.getMinutes()<9 ? "0"+date.getMinutes():date.getMinutes();
                var s = date.getSeconds();
               var dateString = new String();
               dateString = d+"-"+m+"-"+y+" "+h+":"+m2;
         },
         /**
             Verifie que le tableau contient un element
             @array : tableau de données
             @item : element
          **/
          contains: function(array , item){
               if(!angular.isDefined(array)){
                  return false;
               }

               for(var i= 0 ; i<array.length;i++){

                   if(array[i].id == item.id){
                      return true;
                   }
               }

               return false;
          },
          /**
           * 
           * @param {type} array
           * @param {type} item
           * @returns {undefined}
           */
          getIndex: function(array,item){
              if(!angular.isDefined(array)){
                  return false;
               }
//               console.log("commonsTool.getIndex ::::::::::::::: "+angular.toJson(array)+" === item :: "+angular.toJson(item));
               for(var i= 0 ; i<array.length;i++){
                   if(array[i].id == item.id){
                      return i;
                   }
               }
               
          },
          /**
            * 
            * @param {type} array
            * @param {type} item
            * @returns {undefined}
            */
          removeFromArray: function(array , item){
               if(angular.isDefined(array)){
                  
                  for(var i=0 ; i<array.length;i++){
                     if(array[i].id == item.id){
                        array.splice(i , 1);
                     }
                  }
                  //console.log(array+" ====== "+item);                             
              }
          },
          /**
           * 
           * @param {type} metaData
           * @returns {metaData.groups.metaArray|metaData.columns|Object}
           */
          metaDataMapBuilder : function(metaData){
              var map = new Object();
               if(metaData==null||!angular.isDefined(metaData)){
                   return map;
               }
               //Traitement de la metaData
               if(metaData.columns){
                 for(var i=0 ; i<metaData.columns.length;i++){
                      map[metaData.columns[i].fieldName] = metaData.columns[i];
                 }
               }//end if(metaData.columns){
               //Cas des groups
               if(metaData.groups){
                 for(var i=0 ; i<metaData.groups.length;i++){
                     //Cas des columns
                     if(metaData.groups[i].columns){
                         var columns = metaData.groups[i].columns;
                         for(var j=0 ; j<columns.length;j++){
                             map[columns[j].fieldName] = columns[j];
                         }
                     }
                     //Cas des metaArray
                     if(metaData.groups[i].metaArray){
                         map[metaData.groups[i].metaArray.fieldName] = metaData.groups[i].metaArray;
                     }
                 }
               }//end if(metaData.groups){
                return map;
            },

            xmlViewParser : function(data,tree,meta){
                 if(meta==null || !angular.isDefined(meta)){
                      return null;
                 }
                 var map = this.metaDataMapBuilder(meta);                 
                 var metaData = meta;
                 if(data){
                     metaData = new Object();
                     metaData['entityName'] = meta['entityName'];
                     metaData['moduleName'] = meta['moduleName'];
                     metaData['editTitle'] = meta['editTitle'];
                     metaData['listTitle'] = meta['listTitle'];
                     metaData['createonfield'] = meta['createonfield'];
                     metaData['desablecreate'] = meta['desablecreate'];
                     metaData['header'] = new Array();
                     metaData['columns'] = new Array();
                     metaData['groups'] = new Array();
                     var container = document.createElement('div');
                     container.innerHTML = data;
                     var form = $(container).find('formRecord');
                     //parametres descriptif de la vue
                     var label = $(form).attr('label');
                      if(label){
                          metaData['editTitle'] = label;
                      }
                     //Traitement des headers
                     var header = $(container).find('header');
                     if(header){
                          //traitement des buttons
                          var buttons = header.find('button');
                          if(buttons){
                              for(var i=0 ; i<buttons.length;i++){
                                var button = buttons.eq(i);
                                 var column = new Object();
                                  column['type'] = button.attr('type');
                                  column['search'] = false;
                                  column['target'] = button.attr('class');;
                                  column['fieldName'] = button.attr('name');
                                  column['fieldLabel'] = button.attr('label');
                                  column['value'] = null;
                                  column['optional'] = null;
                                  column['unique']=null;
                                  column['updatable'] = null;
                                  column['min']=null;
                                  column['max']=null;
                                  column['pattern']=null;
                                  column['sequence']=null;
                                  column['metaData']=null;
                                  column['colsequence']=null;
                              }//end for(var i=0 ; i<buttons.length;i++) 
                          }//end if(buttons)
                       }//end if(header){

                     //Traitement du sheet
                     var sheet = $(container).find('sheet');
                     //traitement des fields descendant du sheet
                     var fields = $(sheet).find('field');
                     var sequence = 1;
                     for(var i=0;i<fields.length;i++){
                        var name = fields.eq(i).attr('name');
                        var target = fields.eq(i).attr('target');
                        var columns = metaData['columns'];
                        //Si le champs name est definie
                        if(name){
                            columns[i] = map[name];
                            var column = columns[i];
                            column['sequence'] = sequence;
                            column['search'] = false;
                            if(target) {                            
                                column['target']=target;
                            }
                         }
                         sequence = sequence+1 ;
                     }
                     //Traitement des groups 
                     var groups = $(sheet).find('group'); 
                     for(var i=0;i<groups.length;i++){
                         var group = groups.eq(i);
                         var name = group['name'];
                         var label = group['label']
                         if(name){
                             var groupe = new Object();
                             groupe['name'] = name;
                             groupe['label'] = label;
                             groupe['sequence'] = i;
                             groupe['columns'] = new Array();
                             groupe['metaArray']=null;
                             //Traitement des champs
                             var fields = $(group).find('field');
                             for(var j=0 ; j<fields.length;j++){
                                 var field = fields.eq(j);
                                 var name = fields.eq(i).attr('name');
                                 var target = fields.eq(i).attr('target');
                                 if(name){
                                    var elem = map[name];
                                    elem['sequence'] = sequence;
                                    elem['search'] = false;
                                    if(target) {                            
                                        elem['target']=target;
                                    }//end if(target)
                                    if(elem['type']=='array'){
                                        groupe['metaArray'] = elem;
                                    }else{
                                         groupe['columns'].push(elem);
                                    }//end if(elem['type']=='array')                                
                                 }//end if(name)
                                 
                             }//end for(var j=0 ; j<fields.length;j++)
                         }//end if(name)
                     }//end for(var i=0;i<groups.length;i++)
                  }//end if(data)
                 //Traitement de la fenetre liste
                 //console.log(tree);
                 if(tree){
                     container = document.createElement('div');
                     container.innerHTML = tree;
                     var form = $(container).find('treeRecord');
                     //parametres descriptif de la vue
                     var label = $(form).attr('label');
                      if(label){
                          metaData['listTitle'] = label;
                      }
                     var fields = $(container).find('field');
                     for(var i=0;i<fields.length;i++){
                        var name = fields.eq(i).attr('name');
                        var target = fields.eq(i).attr('target');
                        var columns = metaData['columns'];
                        //Si le champs name est definie
                        if(name){
                            //columns[i] = map[name];
                            var column = map[name];
                            column['colsequence'] = i;
                            column['search'] = true;                            
                         }                         
                     }//end for(var i=0;i<fields.length;i++)
                     //console.log(fields.innerHTML);
                 }//end if(tree)
                 //console.log(angular.toJson(metaData));
                 return metaData;
            },
            /**
             * 
             * @param {type} error
             * @returns {undefined}
             */
            showMessageDialog:function(error){
//                    var errorobj = angular.fromJson(angular.toJson(error));
//                    console.log(angular.toJson(error));
                    if(error.status==412){
                        this.notifyWindow("Une erreur est servenu pendant le traitement" ,"<br/>"+error.data,"danger");
                    }else{
                        var viewElem =  document.createElement('div'); //;
                        viewElem.setAttribute('id' , 'gmodalbody');
                        var content =  document.createElement('div');
                        content.setAttribute('style','height:300px;overflow:auto;');
                        //console.log(angular.toJson(error.data));
                        if(error.data){
                            content.innerHTML = error.data;
                        }else{
                            content.innerHTML = error;
                        }
                        viewElem.appendChild(content);
                        //Construction du footer
                        var footerDiv = document.createElement('div');
                        footerDiv.setAttribute('class' , 'modal-footer');
                        footerDiv.setAttribute('id' , 'gmodalfooter');
                        //Button annuler
                        var buttonElem = document.createElement('button');
                        footerDiv.appendChild(buttonElem);
                        buttonElem.setAttribute('class' , 'btn btn-default');
                        buttonElem.setAttribute('data-dismiss' , "modal");
                        buttonElem.setAttribute('type' , 'button');
                        buttonElem.appendChild(document.createTextNode('Annuler'));
                        //Entete modal
                        var titleheader = document.createElement('h4');
                        titleheader.setAttribute('class','modal-title');
                        titleheader.setAttribute('id','gmodaltitle');
                        titleheader.appendChild(document.createTextNode("Erreur Serveur : "+error.status));                    
                        var items = $(document).find("div");
                        for(var i=0; i<items.length;i++){               
                            if(items.eq(i).attr("id")=="gmodalbody"){
                                  items.eq(i).replaceWith(viewElem);                              

                            } else if(items.eq(i).attr("id")=="gmodalfooter"){
                                items.eq(i).replaceWith(footerDiv);
                            } 
                        } 


                        items = $(document).find("h4");
                        for(var i=0; i<items.length;i++){               
                            if(items.eq(i).attr("id")=="gmodaltitle"){
                                  items.eq(i).replaceWith(titleheader);                                     
                            } 
                        } 
                        //Appele de la fenetre modale
                        $("#globalModal").modal("toggle");
                        $("#globalModal").modal("show");
                    }//end if(error.status==412)
                   
            },
    
            /**
             * 
             * @param {type} idFileElement
             * @param {type} idDivImageContent
             * @param {type} idApercuImageContent
             * @returns {undefined}
             */
            gererChangementImage:function(idFileElement ,idDivImageContent ,idApercuImageContent){
                //Initiallisation du tableau des images
                var files = new Array();
                document.querySelector('#'+idFileElement).onchange = function() {
//		console.log("Images Arrays ::::::::::::::::::::: ");	
		var fileInput = document.querySelector('#'+idFileElement);
		var allowedTypes = ['png', 'jpg', 'jpeg', 'gif'];
				
		files = this.files;      
		var filesLen = files.length;      
		var imgType;       

		for (var i = 0 ; i < filesLen ; i++) {              

			imgType = files[i].name.split('.');       

			imgType = imgType[imgType.length - 1].toLowerCase(); // On utilise toLowerCase() pour éviter les extensions en majuscules             

			if(allowedTypes.indexOf(imgType) != -1) { // Le fichier est bien une image supportée, il ne reste plus qu'à l'afficher                  
				
				//On affiche l'appercu				
				this.createThumbnail(files[i],idDivImageContent,idApercuImageContent);
				
			}else{
				
				//Code
			}           
		}
              };
              
              return files;
            },
            objectToNumber:function(data){
                if(data){
                    var value = new String();
                    for(var key in data){
                        value +=data[key];
                    }
                   return new Number('"'+value.trim()+'"');
                }
            },
            /**
             * 
             * @param {type} meta
             * @param {type} fieldName
             * @returns {undefined}
             */
            getMetaField:function(meta,fieldName){
//                console.log("getMetaField === "+fieldName+" == "+angular.toJson(metav));
                if(meta && fieldName){
                    for(var i=0 ; i<meta.columns.length;i++){
                        if(meta.columns[i].fieldName==fieldName){
                            return meta.columns[i];
                        }
                    }//end for(var i=0 ; i<meta.columns.length;i++)
                    for(var i=0;i<meta.groups.length;i++){
                        for(var j=0;j<meta.groups[i].columns.length;j++){
                            if(meta.groups[i].columns[j].fieldName==fieldName){
                                return meta.groups[i].columns[j];
                            }
                        }
                        if(meta.groups[i].metaArray&&meta.groups[i].metaArray.fieldName==fieldName){
                           return meta.groups[i].metaArray;
                        }//end if(meta.groups[i].columns[j].fieldName==fieldName)
                    }//end for(var i=0;i<meta.groups.length;i++)
                }
            },
            /**
             * 
             * @param {type} file
             * @param {type} idDivImageContent
             * @param {type} idApercuImageContent
             * @returns {undefined}
             */
            createThumbnail:function(file,idDivImageContent,idApercuImageContent){
                var reader = new FileReader();       
		reader.onload = function() {                
					
			var imgElement = document.querySelector('#'+idApercuImageContent);      
			 imgElement.src = this.result;          

		};        
		reader.readAsDataURL(file);
            },
            /**
             * Somme la valeur d'une columns
             * @param {type} fieldName:fields name type :number
             * @param {type} datas:
             * @returns {undefined}
             */
            sumTableField:function(fieldName,datas){
                if(datas && datas.length>0){                    
                    var data = datas[0];                    
                    if(data[fieldName]!=null && angular.isNumber(data[fieldName]) ){                        
                        var total = 0;
                        for(var i=0;i<datas.length;i++){           
                            data = datas[i];
                            total += data[fieldName];
                        }//end for(var i=0;i<datas.length;i++)
//                        console.log("$scope.tableListComponent === "+fieldName+" === "+total);
                        return $filter('number')(total,0);
                    }//end if(angular.isNumber(data[fieldName]))
                    
                }//else{return 0;}end if(datas && datas.length>0)
                
            },
            /**
             * Sum the table datas with rhe express
             * @param {type} expr:['tva','*','quantite']
             * @param {type} datas
             * @returns {undefined}
             */
            sumListExpr:function(expr, datas,object,user){
                var exprA = expr.split(','); 
                if(datas && datas.length>0){
                    /* if(exprA.length==1){
                        var data = datas[0];
                        var value=this.expeval(datas[0],null,null,exprA);
                        if(angular.isNumber(value)){
                            var total = 0;
                            for(var i=0;i<datas.length;i++){
                                var data = datas[i];
                                value = this.expeval(data,null,null,exprA);
                                 total += value;
                            }//end for(var i=0;i<datas.length;i++)   
                            return total;
                        }else{
                            return expr;
                        }
                    }else{*/
                        if(exprA.length==1){
                            exprA = expr.split(".");
                            if(exprA.length==1&&!angular.isNumber(expr)){
                                return expr;
                            }//end if(exprA.length==1&&!angular.isNumber(expr)){
                        }//end if(exprA.length==1&&!angular.isNumber(exprA))
//                        console.log(" expeval:function(obj,currentObject,currentUser  , expr)=="+expr+" ===== "+exprA+" ===== ");                
                        var total = 0;
                        for(var i=0;i<datas.length;i++){
                            var value=this.expeval(datas[i],object,user,expr);
                            if(value && angular.isNumber(value)){
                                total += value;
                            }
                        }//end for(var i=0;i<datas.length;i++)
                   // }//end if(exprA.length==1)
                    
                    
                    return total;
                }else{return 0;}//end if(datas && datas.length>0)
            }, 
            funcEval:function(obj,currentObject,currentUser  , expr){
//                console.log("funcEval:function(obj,currentObject,currentUser  , expr) ========== "+expr);
                var entity = angular.fromJson(expr);
                var data = obj;
                if(entity.source=='this'){
                    data = obj;
                }else if(entity.source=='object'){
                    data = currentObject;
                }else if(entity.source=='user'){
                    data = currentUser;
                }
                data = data[entity.data];
                if(entity.op=="sum"){
                    return this.sum(data,entity.field);
                }
                return "0";
            },
            /**
             * 
             * @param {type} datas
             * @param {type} field
             * @returns {Number}
             */
            sum:function(datas , field){
                var result = 0 ;
                for(var i=0;i<datas.length;i++){
                    var data =datas[i];
                    result +=data[field];
                }
                return result;
            },
            /**
              * Expression evaluation
              * @param {type} obj
              * @param {type} currentObject
              * @param {type} currentUser
              * @param {type} expr
              * @returns {undefined}
              */
             expeval:function(obj,currentObject,currentUser  , expr){ 
//                 console.log("commonsTools.expeval:function(obj,currentObject,currentUser  , expr) =============== "+obj+" ::::  === "+currentObject+" === "+expr);
                
                 expr = ""+expr;
                 var parts = expr.split(';');
                 var exp = new String();
                 for(var i=0;i<parts.length;i++){
                     if(parts[i]=='('||parts[i]==')'||parts[i]=='*'
                             ||parts[i]=='+'||parts[i]=='-'||parts[i]=='/'||parts[i]=="%"){
                         exp+=parts[i];
                     }else{//value
                         var ops = parts[i].split('.');
                         if(ops.length==1){
                             if(!isNaN((parts[i]))){
                                 exp+=parts[i];
                             }else{
                                 exp+=this.funcEval(obj,currentObject,currentUser,parts[i]);
                             }
                         }else if(ops[0]=='this'){
                             var value = obj;
                             for(var j=1;j<ops.length;j++){
                                 if(value[ops[j]]){
                                    value =value[ops[j]];
                                 }else{
                                    value= "0";
                                 }//end if(obj[ops[1]]){    
                             }//end for(var i=1;i<ops.length;i++)
                             exp+=value;        
                         }else if(ops[0]=='object'){
                             var value = currentObject;
                             for(var j=1;j<ops.length;j++){
                                 if(value[ops[j]]){
                                    value =value[ops[j]];
                                 }else{
                                    value= "0";
                                 }//end if(obj[ops[1]]){    
                             }//end for(var i=1;i<ops.length;i++)
                             exp+=value;        
                         }else if(ops[0]=='user'){
                             var value = currentUser;
                             for(var j=1;j<ops.length;j++){
                                 if(value[ops[j]]){
                                    value =value[ops[j]];
                                 }else{
                                    value= "0";
                                 }//end if(obj[ops[1]]){    
                             }//end for(var i=1;i<ops.length;i++)
                             exp+=value;         
                         }                         
                     }//end if(parts[i]=='('||parts[i]==')'||parts[i]=='*'
                 }//end for(var i=0;i<parts.length;i++)
//                 console.log("commonsTools.expeval  ====== "+expr+" ************ "+exp+" == "+ops[1]);
               return eval(exp);               
             },
             /**
              * 
              * @param {type} expr
              * @param {type} data
              * @param {type} currentObject
              * @param {type} currentUser
              * @returns {commons_L61.commonsAnonym$3.getValue.data|String}
              */
            getValue:function(expr,data,currentObject,currentUser){
               if(expr){
                   if(angular.isNumber(expr)){
                       return expr;
                   }//end if(angular.isNumber(expr))
//                   console.log("getValue:function(expr,data) === "+expr);
                   var part = expr.split(".");
                   if(part.length==1){
                       if(angular.isNumber(data[expr])){
                        return data[expr];
                       }
                   }else if(part.length>1){
                       var obj = data[part[0]];
                       for(var i=1;i<part.length;i++){
                           obj = obj[part[i]];
                       }//end for(var i=1;i<part.length;i++){
                       if(angular.isNumber(obj)){
                        return obj;
                       }//end if(angular.isNumber(obj))
                   }//end if(part.length==1)                   
               }//end if(exp) 
               return "0" ;
            },
            sumExpr:function(expr,data){
                if(data && expr.length>0){
                    var expEval = new String();
                    for(var i=0;i<expr.length;i++){
                        if(expr[i]=='*'||expr[i]=='-'||expr[i]=='+'||expr[i]=='/'||expr[i]=='%'||expr[i]=='('||expr[i]==')'){
                            expEval += expr[i];
                        }else{
                            expEval +=this.getValue(expr[i],data);
                        }
                    }//end for(var i+0;i<expr.length;i++){
//                    console.log("sumListExpr:function(expr, datas)) === "+expEval+" === "+result);
                    var result = eval(expEval.toString());
                    return result;
                }//end if(data && expr.length>0)
            },
            /**
             * Construit le pied de table
             * @param {type} script
             * @param {type} datas
             * @returns {undefined}
             */
            tableFooterBuilder:function(script , datas,id,object,user){
                var container = document.createElement('tfoot');
                container.setAttribute("id",id);
                container.innerHTML = script;
               //parcours des tr lignes
//               console.log("tableFooterBuilder ===  === "+container.childNodes.length+" ********* script : "+script);
                for(var i=0;i<container.childNodes.length;i++){
                    //traitement des colonnes
                    var rowNode = container.childNodes[i];
                     if(rowNode.tagName=='TR'){
                        for(var j=0;j<rowNode.childNodes.length;j++){
                            var colNode = rowNode.childNodes[j];
                            if(colNode.tagName=='TD'){
                                var value = colNode.textContent;                        
                                if(value!=null&&value!=""){
//                                    console.log("tableFooterBuilder ===  ===  ********* "+colNode.tagName+" === "+colNode.textContent+" ===== Value : "+value);
                                    var data = this.sumListExpr(value,datas,object,user);
                                    if(!isNaN(data)){
                                        colNode.textContent = $filter('number')(data,0);
                                    }else{ 
                                       colNode.textContent = data; 
                                    }//end if(!isNaN(data))
                                }//end if(value!=null&&value!="")
                            }//end if(colNode.tagName=='TD')
                        }//end for(var j=0;j<rowNode.childNodes.length;j++){
                      }//end if(rowNode.tagName=='TR'){
               }//end for(var i=0;i<container.childNodes.length;i++)
                return container;
            },
            /**
             * 
             * @param {type} metaData
             * @param {type} data
             * @param {type} model
             * @returns {undefined}
             */
            sumFooterTableBuilder:function(metaData , data,model,id){
//                console.log("commonsTool. sumFooterTableBuilder === "+model+"========"+angular.toJson(data));
                    var fieldnames = new Array();
                    for(var i = 0 ; i < metaData.columns.length;i++){
                        if(angular.isDefined(metaData.columns[i].search) && metaData.columns[i].search){
                          fieldnames.push(metaData.columns[i].fieldName);                          
                        }
                    }
                    //Traitement  des champs des groupes
                    if(metaData.groups&&metaData.groups.length>0){
                        for(var i=0;i<metaData.groups.length;i++){
                            //Cas des columns
                            if(metaData.groups[i].columns&&metaData.groups[i].columns.length>0){
                                 for(var j = 0 ; j < metaData.groups[i].columns.length;j++){
                                   if(angular.isDefined(metaData.groups[i].columns[j].search) && metaData.groups[i].columns[j].search){
                                     fieldnames.push(metaData.groups[i].columns[j].fieldName);                                     
                                   }//end  if(angular.isDefined(metaData.groups[i].columns[j].search) && metaData.groups[i].columns[j].search)
                               }//end for(var j = 0 ; j < metaData.groups[i].columns.length;j++)
                            }//end if(metaData.groups[i].columns&&metaData.groups[i].columns.length>0)                     
                        }//end for(var i=0;i<metaData.groups.length;i++)
                    }
                    //Construction du pied
                    if(fieldnames.length>0){
                         var sources = model.split(".");                        
                        var footerElem = document.createElement('tfoot');
                        footerElem.setAttribute("id",id);
                        if(fieldnames.length>0){
                        for(var i=0 ;  i<fieldnames.length ; i++){
                                var thelem = document.createElement('th');
                                footerElem.appendChild(thelem);                            
//                                console.log("commonsTool. sumFooterTableBuilder === "+model+"===="+sources[0]+"===="+angular.toJson(data));
                                var total = this.sumTableField(fieldnames[i],data[sources[sources.length-1]]);
                                if(angular.isDefined(total)&&angular.isNumber(total)){
                                    thelem.appendChild(document.createTextNode(total));
                                    thelem.setAttribute('class','text-right');
                                }//end if(angular.isNumber(total))
                        }//end for(var i=0 ;  i<fieldnames.length ; i++)
//                        tableElem.appendChild(footerElem);
                    }//end if(fieldnames.length>0)
               }//end if(fieldnames.length>0)
               return footerElem;
            },
            
            /**
             * Build for from dashboard entry data
             * @param {type} data
             * @returns {undefined}
             */
            dashboardEntryFormBuilder:function(parentID,data){
                var formElem = document.createElement('form');
//                container.appendChild(formElem);
                formElem.setAttribute('class','form-inline');
                for(var i=0 ; i<data.fields.length;i++){
                    var field = data.fields[i];
                    var divElem = document.createElement("div");
                    divElem.setAttribute("class","form-group  col-sm-12  col-md-12");
                    divElem.setAttribute("style","margin-top: 10px;");
                    formElem.appendChild(divElem);
                    var labelElem_1 = document.createElement("label");
                    divElem.appendChild(labelElem_1);
                    labelElem_1.setAttribute("for",field.fieldName);
                    labelElem_1.setAttribute("class","col-sm-6  col-md-6");
                    labelElem_1.appendChild(document.createTextNode(field.fieldLabel));
                    var labelElem_2 = document.createElement("label");
                    divElem.appendChild(labelElem_2);
                    labelElem_2.setAttribute("for",field.fieldName);
                    labelElem_2.setAttribute("class","col-sm-4  col-md-4");
                    labelElem_2.appendChild(document.createTextNode(field.fieldValue));
                    var butElem = document.createElement("button");
                    divElem.appendChild(butElem);
                    butElem.setAttribute("class","col-md-2 col-sm-2 btn btn-default btn-sm");
                    butElem.appendChild(document.createTextNode("Détail"));
                    butElem.setAttribute("ng-show",field.activalink);
                    butElem.setAttribute("ng-click" , "dashboardEntryBtn('"+field.model+"','"+field.entity+"' , '"+field.method+"')");
                }//end for(var i=0 ; i<data.length;i++)
                $("#"+parentID).html("");
                $("#"+parentID).append(formElem);
//                return formElem;
            },
            /**
             * 
             * @param {type} parentID
             * @param {type} data
             * @returns {undefined}
             */
            dashboardEntryCustomBuilder:function(parentID,data,scope){
                var url = 'http://'+$location.host()+':'+$location.port()+'/'+angular.lowercase(data.model)+'/'+angular.lowercase(data.entity)+'/'+angular.lowercase(data.method);
                $http.get(url).then(
                    /**
                     * 
                     * @param {type} datas
                     * @returns {undefined}
                     */
                        function(response){
                             //Notification du changement du module
                                scope.temporalData = response.data;
                                var container = document.createElement("div");
                //              var obj = angular.fromJson(data);
                                console.log("commons.dashboardEntryCustomBuilder ====== "+angular.toJson(scope.temporalData)+" ==== template : ");                
                                container.innerHTML = data.tempate;
                                var compileFn = $compile(container);
                                 compileFn(scope);    
                                $("#"+parentID).html("");
                                $("#"+parentID).append(container);
                        },
                        function(error){
//                             this.hideDialogLoading();
                             this.showMessageDialog(error);
                        }
                     );
                
//                return formElem;
            },
            /**
             * 
             * @param {type} parentID
             * @param {type} data
             * @returns {undefined}
             */
            dashboardEntryBarBuilder:function(parentID , data){
                var bardata = new Object();
                bardata.type = "column";
                bardata.dataPoints = new Array();                
                for(var i=0 ; i<data.fields.length;i++){
                    bardata.dataPoints.push({label:data.fields[i].fieldLabel,y:data.fields[i].fieldValue});
                }//end for(var i=0 ; i<data.fields.length;i++)
                var bararray = new Array();
                bararray.push(bardata);
                // Construct options first and then pass it as a parameter
                var options = {
                        animationEnabled: true,
                        title: {
                                text: ""
                        },
                        data:bararray
                };
                $("#"+parentID).html("");
                $("#"+parentID).CanvasJSChart(options);
//                return divElem;
            },
            /**
             * 
             * @param {type} parentID
             * @param {type} data
             * @returns {undefined}
             */
            dashboardEntryPieBuilder:function(parentID , data){
                var bardata = new Object();
                bardata.type = "pie";
                bardata.startAngle= 45;
                bardata.indexLabel = "{label} ({y})";
                bardata.yValueFormatString = "#,##0.#"%"";
                bardata.dataPoints = new Array();                
                for(var i=0 ; i<data.fields.length;i++){
                    bardata.dataPoints.push({label:data.fields[i].fieldLabel,y:data.fields[i].fieldValue});
                }//end for(var i=0 ; i<data.fields.length;i++)
                var bararray = new Array();
                bararray.push(bardata);
                // Construct options first and then pass it as a parameter
                var options = {
                        animationEnabled: true,
                        title: {
                                text: ""
                        },
                        data:bararray
                };
                $("#"+parentID).html("");
                $("#"+parentID).CanvasJSChart(options);
            },
            /**
             * 
             * @param {type} parentID
             * @param {type} data
             * @returns {undefined}
             */
            dashboardEntryLineBuilder:function(parentID , data){
                var bardata = new Object();
                bardata.type = "spline";
                bardata.dataPoints = new Array();                
                for(var i=0 ; i<data.fields.length;i++){
                    bardata.dataPoints.push({label:data.fields[i].fieldLabel,y:data.fields[i].fieldValue});
                }//end for(var i=0 ; i<data.fields.length;i++)
                var bararray = new Array();
                bararray.push(bardata);
                // Construct options first and then pass it as a parameter
                var options = {
                        animationEnabled: true,
                        title: {
                                text: ""
                        },
                        data:bararray
                };
                $("#"+parentID).html("");
                $("#"+parentID).CanvasJSChart(options);
            },
            /**
             * 
             * @param {type} parentID
             * @param {type} data
             * @returns {unresolved}
             */
            dashboardEntryUnkownBuilder:function(parentID , data){
                var divElem = document.createElement("div");
                divElem.appendChild(document.createTextNode("Unkown options ..."));
                return divElem;
            },
            /**
             * 
             * @param {type} data
             * @returns {undefined}
             */
            dashboardEntryBuilder:function(parentID,data,scope){
                if(data.type=='data'){
                    return this.dashboardEntryFormBuilder(parentID,data);
                }else if(data.type=='bar'){
                    return this.dashboardEntryBarBuilder(parentID,data);
                }else if(data.type=='pie'){
                    return this.dashboardEntryPieBuilder(parentID,data);
                }else if(data.type=='line'){
                    return this.dashboardEntryLineBuilder(parentID,data);
                }else if(data.type=='template'){
                    return this.dashboardEntryCustomBuilder(parentID,data,scope);
                }else{
                    return this.dashboardEntryUnkownBuilder(parentID,data);
                }
            },
            /**
             * 
             * @param {type} data:dash bord datat
             * @returns {undefined}
             */
        dashboardBuilder:function(data){
                if(data){                                                            
                    var divElem = document.createElement('div');
                    divElem.setAttribute("class","panel dashBoardStyle");
                    //divElem.setAttribute("style","margin-bottom:7px;padding-left: 0px;margin-right: 10px;width: 49%;");
                    var headElem = document.createElement("div");
                    divElem.appendChild(headElem);
                    headElem.setAttribute("class","panel-heading col-sm-12 col-md-12");
					headElem.setAttribute("style","background-color:white;border:solid 1px white;color:"+couleurModuleCourantResponsive+";");
                    headElem.appendChild(document.createTextNode(data.label));
                    var iElem = document.createElement("i");
                    iElem.setAttribute("class" ,"fa fa-2x fa-plus-circle pull-right");
                    headElem.appendChild(iElem);
                    var actionElem = document.createElement("div");
                    headElem.appendChild(actionElem);
                    actionElem.setAttribute("class","btn-group  dropdown pull-right");
                    actionElem.setAttribute("role","group");
                    actionElem.setAttribute("aria-label","group 2");
                    var buttonElem = document.createElement("button");
                    actionElem.appendChild(buttonElem);
                    buttonElem.setAttribute("class","btn dropdown dropdown-toggle btn-sm");
                    buttonElem.setAttribute("type","button");
                    buttonElem.setAttribute("data-toggle","dropdown");
                    buttonElem.setAttribute("aria-haspopup","false");
                    buttonElem.setAttribute("aria-aria-expanded","true");
                    buttonElem.appendChild(document.createElement("Plus"));
                    var spanElem = document.createElement("span");
                    buttonElem.appendChild(spanElem);
                    spanElem.setAttribute("class","caret");
                    var ulElem = document.createElement("ul");
                    actionElem.appendChild(ulElem);
                    ulElem.setAttribute("class","dropdown-menu");
                    ulElem.setAttribute("role","menu");
                    ulElem.setAttribute("aria-labelledby","dashboardbtn");
                    //Liste des menus
                    for(var i=0 ;i<data.entries.length;i++){
                        var entry = data.entries[i];
                        var liElem = document.createElement("li");
                        ulElem.appendChild(liElem);
                        liElem.setAttribute("role","presentation");
                        var aElem = document.createElement("a");
                        liElem.appendChild(aElem);
                        aElem.setAttribute("role","menuitem");
                        aElem.setAttribute("tabindex","1");
                        aElem.setAttribute("href","#");
                        aElem.setAttribute("ng-click","showEntrypanel('"+data.code+"','"+entry.code+"')");
                        aElem.appendChild(document.createTextNode(entry.label));
                    }//end entries
                    //Body of the dashboard
                    var bodyElem = document.createElement("div");
                    divElem.appendChild(bodyElem);
                    bodyElem.setAttribute("class","panel-body col-sm-12 col-md-12");
                    bodyElem.setAttribute("style","padding: 0px;");
                    var divElem2 = document.createElement("div");
                    bodyElem.appendChild(divElem2);
                    divElem2.setAttribute("class","kanban-centered");
                    divElem2.setAttribute("style","padding:0px;");
                    var artElem = document.createElement("article");
                    divElem2.appendChild(artElem);
                    artElem.setAttribute("draggable","true");
                    artElem.setAttribute("style","height: 150px;");
                    var container = document.createElement("div");                    
                    container.setAttribute("id",data.code);
                    container.setAttribute("style","height: 100%; width: 100%;");                    
//                    var dashentry =this.dashboardEntryBuilder(data.code,data.entries[0]);
//                    if(dashentry){
//                        container.appendChild(dashentry);
//                    }//end if(dashentry){
                    artElem.appendChild(container);
//                    console.log(" $scope.initAction ===== "+divElem.innerHTML);
//                    this.dashboardEntryBuilder(data.code,data.entries[0]);
                    return divElem;
                }//end if(data)
            },
            /**
             * Construction du panel tableau de bord
             * @param {type} data
             * @returns {undefined}
             */
            dashboardContainerBuilder:function(data,scope){
                var divElem = document.createElement("div");
                divElem.setAttribute("class","row dashBoardContentStyle");
                divElem.setAttribute("style","padding-left:  10px;padding-right:  10px;");
                for(var i=0;i<data.dashboards.length;i++){
                    if(data.dashboards[i]){                        
                        divElem.appendChild(this.dashboardBuilder(data.dashboards[i]));                        
                    }//end if(data.dashboards[i])
                }//end for(var i=0;i<data.dashboards.length;i++)
                return divElem;
            },
            /**
             * 
             * @param {type} date
             * @returns {undefined}
             */    
             formatDat:function(date){
                if(date){
                    var today = new Date();
                    if(today.getDate()==date.getDate()&&today.getMonth()==date.getMonth()&&today.getYear()==date.getYear()){
                        return date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
                    }else{
                        return date.toDateString();
                    }
                }
            },
            /**
             * 
             * @param {type} module
             * @param {type} action
             * @param {type} entity
             * @param {type} user
             * @returns {commons_L61.commonsAnonym$3.createsession.session|Object}
             */
            createsession: function(module,memento,user){
                 var session = new Object();
                 session.module = module ;
                 session.navigator = memento ;
//                 session.entity = entity;
                 session.user = user;
                 this.createCookie("session_"+session.user,angular.toJson(session),30);
//           console.log("principal.createsession ===== cookie read : "+commonsTools.readCookie("session_"+session.user));
               return session ;
            },
            /**
             * 
             * @param {type} name
             * @param {type} value
             * @param {type} minute
             * @returns {undefined}
             */
            createCookie: function(name,value,days){
                if(days){
                    var date = new Date();
                    date.setTime(date.getTime()+(days*60*1000));
                    var expires ="; expires="+date.toGMTString();
                }else{
                    var expires ="";
                }//end if(days){
                document.cookie = name+"="+value+expires+"; path=/";
            },
            /**
             * 
             * @param {type} name
             * @returns {undefined}
             */
            readCookie: function(name){
                var nameEQ = name + "=";
                var ca = document.cookie.split(';');
                for(var i=0;i < ca.length;i++) {
                        var c = ca[i];
                        while (c.charAt(0)==' ') c = c.substring(1,c.length);
                        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
                }
                return null;
            },
            /**
             * 
             * @param {type} id
             * @returns {undefined}
             */
            getDashoardEntryDataFromID:function(data , dashid,entryId){
//                console.log("getDashoardEntryDataFromID ::: "+angular.toJson(data));
                if(data){
                    if(data.dashboards && data.dashboards.length>0){
                        for(var i=0 ; i<data.dashboards.length;i++){
                            var dash = data.dashboards[i];
                            if(dash.code && dash.code==dashid){
                                for(var j=0 ;j<dash.entries.length;j++){
                                    var entry = dash.entries[j];
                                    if(entry.code==entryId){
                                        return entry;
                                    }
                                }
                            }//end if(dash.code && dash.code==dashid){
                        }//end for(var i=0 ; i<data.dashboards.length;i++)
                    }//end if(data.dashboards && data.dashboards.length>0)
                    return null;
                }//end if(data)
                return null ;
            }  ,
            /**
             * 
             * @param {type} model
             * @returns {undefined}
             */
            keygenerator:function(model){
                var parts= model.split(".");
                var key = "";
                for(var i=1 ; i<parts.length;i++){
                    var part = parts[i].split("[");
                    if(i==1){                 
                        key = part[0];                        
                    }else{
                        key+=part[0];
                    }
                }
                return key;
            },
            /**
             * 
             * @param {type} model
             * @returns {String}
             */
             keyparentgenerator:function(model){
                var parts= model.split(".");
                var key = "";
                var endIndex = parts.length-1;
                for(var i=1 ; i<endIndex;i++){
                    if(i==1){
                        key = parts[i];
                    }else{
                        key+=parts[i];
                    }
                }
                return key;
            },
            /**
             * 
             * @param {type} errors
             * @returns {Array}
             */
            converErrorsMap : function(errors){
                var array = new Array();
                for(var key in errors){
                    var data = errors[key];
                    for(var key2 in data){
                        var error = data[key2];
                        var obj = new Object();
                        obj['line'] = key;
                        obj['field'] = key2;
                        for(var key3 in error){
//                            console.log("commons.converErrorsMap ========== key : "+key3+" ======= value : "+angular.toJson(error[key3]));
                            var detail = error[key3];
                            obj['value'] = detail['value'];
                            obj['message'] = detail['message'];
                        }//end for(var key3 in error){
                        array.push(obj);
                    }//end for(var key2 in data){
                }//end for(var key in errors){
                return array;
            },
            /**
             * 
             * @param {type} data
             * @param {type} expressions
             * @returns {undefined}
             */
            evaluateExpression:function(data,expressions){
                var result = true;
                for(var i=0 ; i<expressions.length;i++){
                    var expr = expressions[i];
                    if(expr.function=='=='){
                        result &=(data[expr.fieldname]==expr.value);
                    }//end if(expr.function=='==')
//                    console.log("commons.evaluateExpression ============ expr = "+expr+" === field : "+data[expr.fieldname]+" == oper : "+expr.function+" === value : "+expr.value);
                }//end for(var i=0 ; i<expressions.length;i++){
                return result;
            },
            /**
            * 
            * @param {type} metaData
            * @returns {undefined}
            */
           createImportEntity:function(metaData){
               var entity = new Object();
               entity.fichier = null;
               entity.fields = new Array();
               entity.className = metaData.className;
               entity.format ='cvs';
               entity.separator = ',';
               entity.typeexport = '0';
               /**
                * Traitement des champs columns
                */
               for(var i=0 ; i<metaData.columns.length;i++){
                   var ele = metaData.columns[i];
                   /**if(ele.search==true)**/{
                        var field = new Object();
                        field.id = -1 ;
                        field.selected = false ;
                        field.code = ele.fieldName;                        
                        if(ele.type=='object'){
                            if(angular.isDefined(ele.importfield) && 
                                    ele.importfield!=""){
                                field.code = field.code+"."+ele.importfield; 
                                field.className = ele.metaData.className;
//                                console.log("commonsTools.createImportEntity ========================= type : "+angular.toJson(ele));                        
                            }//end if(angular.isDefined(field.importfield) &&
                        }//end if(field.type=='object'){
                        field.description = ele.fieldLabel;
                        field.optional = !ele.optional;
                        field.selected = field.optional;
                        field.pattern = ele.pattern;
                        field.length = ele.length;
                        field.min = ele.min;
                        field.max = ele.max ;
                        field.unique = ele.unique;
                        field.nullable = ele.nullable;
                        entity.fields.push(field);
                   }//end if(ele.search==true){
               }//end for(var i=0 ; i<metaData.columns.length;i++){
               /**
                * Traitement des groups
                */
               for(var i=0 ; i<metaData.groups.length;i++){
                   for(var j=0 ;j<metaData.groups[i].columns.length;j++){
                       var ele = metaData.groups[i].columns[j];
                       /**if(ele.search==true)**/{
                            var field = new Object();
                            field.id = -1 ;
                            field.selected = false ;
                            field.code = ele.fieldName;
//                             console.log("commonsTools.createImportEntity ========================= type : "+ele.type+" ===== importfield : "+ele.importfield+" filed code : "+field.code);
                            if(ele.type=='object'){
                                if(angular.isDefined(ele.importfield) && 
                                        ele.importfield!=""){
                                    field.code = field.code+"."+ele.importfield;  
                                    field.className = ele.metaData.className;
//                                    console.log("commonsTools.createImportEntity ========================= type : "+angular.toJson(ele));
                                }//end if(angular.isDefined(field.importfield) &&
                            }//end if(field.type=='object'){
                            field.description = ele.fieldLabel;
                            field.optional = !ele.optional;
                            field.selected = field.optional;
                            field.pattern = ele.pattern;
                            field.length = ele.length;
                            field.min = ele.min;
                            field.max = ele.max ;
                            field.unique = ele.unique;
                            field.nullable = ele.nullable;
                            entity.fields.push(field);
                       }//end if(ele.search==true){
                   }//end for(var j=0 ;j<metaData.groups[i].columns.length;j++){
               }//end for(var i=0 ; i<metaData.groups.length;i++){
               return entity;
           }

       };
            
 });
/**
 * Tools for Rest
 */
angular.module('keren.core.commons')
.factory("backendService" , function($http ,$resource , $location){
    var urlPath = "";
    //Resource rest pour l'interaction avec le back end
    var restResource = null;
     return{
            /**
             Build the restName base of the entityName
            **/
            url:function(entityName,moduleName){
                 urlPath = "http://"+$location.host()+":"+$location.port()+"/"+moduleName+"/"+entityName+"/";
                 restResource = $resource(urlPath+":path/:first/:max/:propertyname/:id"
                       ,{path:'@path',first:'@first',max:'@max',id:'@id'}
                       ,{search:{
                                    method:'GET',
                                     isArray:true,
                                    params:{ first:'@first',
                                        max:'@max',
                                        path:'filter'
                                      }                              
                                 },
                          update:{
                               method:'PUT'                               
                          }
                   });
                 //console.log("restService == "+urlPath);
                 return urlPath;
            },
            /**
               Return the metaData of the entity
            **/
            getMetaData:function(){
                  if(angular.isDefined(restResource)){
                     return  restResource.get({path:'meta'});
                  }
            },
            /**  
               Cancel etity
            **/
            cancel:function(entity){

            },
            /**
              Save entity in the back data store
              @entity: entity to save
            **/
            save: function(entity){
                //alert("Super vous avez appele le service restService avec :"+entity+" :::: "+urlPath);  
                 return  restResource.save(entity);
            },

            /**
              Save an array of entity in the back data store
              @entity: entities : array of entity to save
            **/
            saveAll: function(entities){

            },
            /**
              update entity in the back data store
              @entity: entity : entity to update
            **/
            update: function(entity){
                 //console.log("=========================== "+angular.toJson(entity));
                 return restResource.update(entity);
            },
           /**
              delete entity in the back data store
              @entity: entity : entity to delete
            **/
           delete:function(entity){
              return restResource.delete({id:entity.id});
           },
           /**
             Supprime la liste des entites
             @entities: array of objects
           **/
           deleteAll:function(entities){
               var ids = new Array();
                for(var i=0;i<entities.length;i++){
                    ids.push(entities[i].id);
                }
               //console.log()
               $http.defaults.headers.common['ids']=angular.toJson(ids);
                return restResource.delete();
           },
           /**
              find entity in the back data store with a specific ID
              @entity: id : id of the entity to find
            **/
           findById:function(id){
                 return restResource.get({id:id,path:'byid',propertyname:'id'});
           },
           /**
            * Retourn un boolean 
            * true : la contrainte d'unicite est verifier
            * false : la contrainte d'unicite est viol�e
            */
           uniqueProperty:function(propertyName , vale){
               
           },
           /**
            * Retourn un boolean 
            * true : la contrainte d'unicite est verifier
            * false : la contrainte d'unicite est viol�e
            */
           uniqueProperties:function(properties){
               $http.defaults.headers.common['properties']=angular.toJson(properties);
               //console.log(" uniqueProperties == "+angular.toJson(properties));
                if(angular.isDefined(restResource)){
                    return  restResource.query({path:'unique'});
                 }
           },
           /**
              find all entities in the back data store 
              
            **/
           findAll:function(){
               return restResource.query();
           },
           /**
              find entity in the back data store with a specific ID
              @entity: id : id of the entity to find
            **/
           findByUniqueProperty:function(propertyName , value){

           },
           /**
              find entities which match a specific criteria in the back data store 
              @predicats: array of criteria({fieldName:name,fieldValue:value ,criteria:EQUAL}
              @firstResult:the index of the first result
              @maxResult : the max number of Items of the result
            **/
           filter:function(predicats ,firstResult , maxResult){
               $http.defaults.headers.common['predicats']= angular.toJson(predicats);               
                if(angular.isDefined(restResource)){
                    return  restResource.search({path:'filter',first:firstResult,max:maxResult});
                 }
           },
           /**
             return the number of items which match the specific criteria
            @predicats: array of criteria({fieldName:name,fieldValue:value ,criteria:EQUAL}
           **/
           count:function(predicats){
               $http.defaults.headers.common['predicats']= angular.toJson(predicats);               
                if(angular.isDefined(restResource)){
                    return  restResource.get({path:'count'});
                 }
           },
           /**
            * Upload ressource to the server
            * @param {type} files
            * @returns {undefined}
            */
           uploadFile:function(files){
               //URL de la resource responsable de transfert du fichier
               var url = "http://"+$location.host()+":"+$location.port()+"/kerencore/resource/upload";
               var fd = new FormData();
               //Take the first select 
               for(var i=0;i<files.length;i++){
                   fd.append("file_"+(i+1),files[i]);               
               }
               return $http.post(url,fd
                    ,{withCredentials:true,headers:{'Content-Type':undefined},
                       transformRequest: angular.identity});
               
           }
     };
});
//Javascript observer design pattern implementations
/**
 * Observable class
 * declaration de l'observable
 */
var Observable = function(){
    this.observers = new Array();
};

/**
 * Fonction de l'object observable
 */
Observable.prototype = {
    
    //enregistrement un observer a recevoir des notifications
    register:function(observer){
        if(angular.isDefined(observer)){
            this.observers.push(observer);           
        }//end if(angular.isDefined(observer))
        return this;
    },
    //envoie une notification a tous les observers enregistres
    notifyObservers:function(event , parameters){
        for(var i=0;i<this.observers.length;i++){
            var observer = this.observers[i];
            observer.notify(event,parameters);
        }//end for(var i=0;i<this.observers.length;i++)
    }
};
// gestion de l'héritage
function extend(C, P) {
  var F = function () {};
  F = P;
  F.prototype = $.extend(P.prototype, C.prototype);
  C.prototype = new F();
  C.uber = P.prototype;
  C.prototype.constructor = C;
}
/**
 * Declaration de l'observable
 */
var Observer = function(){
   this.observers = new Array(); 
} ;

/**
 * fonctions de l'object observable
 */
Observer.prototype = {
    register:function(observable){
       this.observable = observable;
       this.notifyMe();
       return this;
    },
    notifyMe:function(){
        this.observable.register(this);
    },
    /**
     * 
     * @param {type} event
     * @param {type} parameters
     * @returns {undefined}
     */
    notifyObservers:function(event , parameters){
        this.observable.notifyObservers(event,parameters);
    },
    /**
     * 
     * @param {type} event
     * @param {type} parameters
     * @returns {Observer.prototype}
     */
    notify:function(event , parameters){
        return this;
    }   
};