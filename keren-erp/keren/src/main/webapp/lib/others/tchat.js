var nbreTchatBox = 0;
var listeTchatBox = {};


function generateTchat(){
//    alert("TCHAT");
    //On affiche le dialog
    createBodyChatContent();

    addChatZone("zone01","idUser1", "idFriend1", "images/2.jpg", "images/1.jpg", "#Ange");
    addChatZone("zone02","idUser1", "idFriend1", "images/2.jpg", "images/1.jpg", "#Carole");
    addChatZone("zone03","idUser1", "idFriend1", "images/2.jpg", "images/1.jpg", "#Carole");
    addChatZone("zone04","idUser1", "idFriend1", "images/2.jpg", "images/1.jpg", "#Carole");

    example("zone01");
    example("zone02");
    example("zone03");
    example("zone04");
}

function example(idParent){
	
	addMessage(idParent,generateurIdAvance(),"idFriend", 0, "images/1.jpg", "Bon jour ma cooo, je t'ai appellé sans succes, sinon comment tu vas","Il y a 2mn");
	addMessage(idParent,generateurIdAvance(),"idUser", 1, "images/2.jpg", "Laisse slmnt, n'est ce pas c'est ton bebe qui ne voulais pas dormir","Il y a 20mn");
	addMessage(idParent,generateurIdAvance(),"idUser", 1, "images/2.jpg", "Ah ok, tu as recu mon msg ?","Il y a 15mn");
	addMessage(idParent,generateurIdAvance(),"idFriend", 0, "images/1.jpg", "Ton message tu m'as filmé là ? loool","Il y a 2mn");
	addMessage(idParent,generateurIdAvance(),"idFriend", 0, "images/1.jpg", "Bon jour ma cooo, je t'ai appellé sans succes, sinon comment tu vas","Il y a 1h");
	addMessage(idParent,generateurIdAvance(),"idUser", 1, "images/2.jpg", "Laisse slmnt, n'est ce pas c'est ton bebe qui ne voulais pas dormir","Il y a 1mois");
	addMessage(idParent,generateurIdAvance(),"idFriend", 0, "images/1.jpg", "Ton message tu m'as filmé là ? loool","Il y a 2mn");
}

/**
*Retourne le nombre de nbreTchatBox ouvertes
**/
function getNbreTChatBox(){
	return nbreTchatBox;
}

function incrementeNbreTChatBox(){
	nbreTchatBox = nbreTchatBox + 1;
}

function decrementeNbreTChatBox(){
	nbreTchatBox = nbreTchatBox - 1;
}

function createBodyChatContent(){
	$('body').append("<div id='tchatBodyContent' style='border:solid 1px green;position:fixed;z-index:9000; width:100%;top:100%;text-align:right;background-color:white'></div>");
}

function addChatZone(idElement, idUser, idFriend, photoUser, photoFriend, nameFriend){
	
	$('#tchatBodyContent').append("<span id="+idElement+"_tchatContent style='width:300px;height:400px;vertical-align:top;margin-left:6px;display:inline-block;text-align:left;margin-top:-400px;background-color:white;'></span>");
	$('#'+idElement+'_tchatContent').append("<div id="+idElement+"_tchatTitleContent style='width:100%;height:8%;padding:5px;background-color:#0384f7;color:white;border-radius:5px 5px 0px 0px'></div>");
	$('#'+idElement+'_tchatContent').append("<div id="+idElement+"_tchatTexteContent style='border-left:solid 1px #d3d3d3;border-right:solid 1px #d3d3d3;width:100%;height:82%;overflow:auto;padding:5px'></div>");
	$('#'+idElement+'_tchatContent').append("<div id="+idElement+"_tchatSaisieContent style='width:100%;height:10%;'></div>");
	
	
	$('#'+idElement+'_tchatTitleContent').append("<span id="+idElement+"_tchatTitleTexte style='width:90%;display:inline-block;font-weight:bold'>"+nameFriend+"</span>");
	$('#'+idElement+'_tchatTitleContent').append("<span id="+idElement+"_tchatRemove style='width:10%;text-align:center;display:inline-block;cursor:pointer'><i class='fa fa-remove'></i></span>");
	
	$('#'+idElement+'_tchatSaisieContent').append("<input id="+idElement+"_tchatSaisie style='width:100%;height:100%;border:solid 1px #d3d3d3;padding:6px' placeholder='Ajouter un message'>");
	
	//On incremente le nbreTchatBox
	incrementeNbreTChatBox();
	
	//On mentionne qu'on cree et deroule la tchatBox
	listeTchatBox[idElement] = {isOpen:true};
	
	gererEvenements();
	
	function gererEvenements(){
		
		$('#'+idElement+'_tchatSaisie').keyup(function(e) {
				
			if(e.which == 13){
				
				if($('#'+idElement+'_tchatSaisie').val().length != 0){
					addMessage(idElement,generateurIdAvance(), idUser, 1, photoUser, $('#'+idElement+'_tchatSaisie').val(), "A l'instant");
					
					//On vide la zone de saisie
					$('#'+idElement+'_tchatSaisie').val("");
				}
			}
		});
		
		$('#'+idElement+'_tchatRemove').click(function(e) {
			
			$('#'+idElement+'_tchatContent').fadeOut(function(){
							
				decrementeNbreTChatBox();	
				$('#'+idElement+'_tchatContent').remove();
			});	
			
		});
		
		$('#'+idElement+'_tchatTitleTexte').click(function(e) {
			
			if(listeTchatBox[idElement].isOpen){
				
				$('#'+idElement+'_tchatContent').animate({'margin-top':'-30px' }, 500, function(){
								
					listeTchatBox[idElement].isOpen = false;		
				});
				
			}else{
				
				$('#'+idElement+'_tchatContent').animate({'margin-top':'-400px' }, 500, function(){
								
					listeTchatBox[idElement].isOpen = true;		
				});
				
			}
			
		});
	}
}

function addMessage(idParent, idElement, idUser, sens, photo, message, date){
	$('#'+idParent+'_tchatTexteContent').append("<div id="+idParent+idElement+"_messageTchat style='width:100%;padding:8px;'></div>");
	
	
	if(sens == 0){
		
		//Left
		$('#'+idParent+idElement+"_messageTchat").append("<span id="+idParent+idElement+"_messageTchatPhotoContent style='margin-right:5px;width:40px;border:solid 1px #d3d3d3;display:inline-block;'></span>");
		$('#'+idParent+idElement+"_messageTchat").append("<span id="+idParent+idElement+"_messageTchatTexteContent style='width:82%;vertical-align:top;display:inline-block;background-color:#eaeaea;padding:8px;border-radius:15px;word-wrap : break-word ;'></span>");
		
		$('#'+idParent+idElement+"_messageTchatPhotoContent").append("<img id="+idParent+idElement+"_messageTchatPhoto style='width:100%;' src="+photo+">");
		$('#'+idParent+idElement+"_messageTchatTexteContent").append("<div id="+idParent+idElement+"_messageTchatTexte style='font-size:89%'>"+message+"</div>");
		$('#'+idParent+idElement+"_messageTchatTexteContent").append("<div id="+idParent+idElement+"_messageTchatDate style='font-style:italic;font-size:80%;text-align:right;'>"+date+"</div>");
		
	}else{
		
		//Right
		$('#'+idParent+idElement+"_messageTchat").append("<span id="+idParent+idElement+"_messageTchatTexteContent style='margin-right:5px;width:82%;vertical-align:top;display:inline-block;background-color:#0484f7;color:white;padding:8px;border-radius:15px;word-wrap : break-word ;'></span>");
		$('#'+idParent+idElement+"_messageTchat").append("<span id="+idParent+idElement+"_messageTchatPhotoContent style='width:40px;border:solid 1px #d3d3d3;display:inline-block;'></span>");
		
		$('#'+idParent+idElement+"_messageTchatPhotoContent").append("<img id="+idParent+idElement+"_messageTchatPhoto style='width:100%;' src="+photo+">");
		$('#'+idParent+idElement+"_messageTchatTexteContent").append("<div id="+idParent+idElement+"_messageTchatTexte style='font-size:89%'>"+message+"</div>");
		$('#'+idParent+idElement+"_messageTchatTexteContent").append("<div id="+idParent+idElement+"_messageTchatDate style='font-style:italic;font-size:80%;text-align:right;'>"+date+"</div>");
	}
	
	//On scroll vers le bas
	$('#'+idParent+'_tchatTexteContent').scrollTop(1000000000,'bottom');
	
	//Save en bd
	
}