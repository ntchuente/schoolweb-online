/**

 **/
function showDialogLoading(texte, color, colorContent, topPos,leftPos) {
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
}

function hideDialogLoading() {
	$('#dialogContent').fadeOut(function(){
		$('#dialogContent').remove();
	});
}

function hellordWordDialog() {
	
	//On affiche le dialog
	showDialogLoading("Chargement ...","white","#9370db","60%","0%");

	setTimeout(function(){
		hideDialogLoading();
	},3000);
}