
//hellordWordFileChooser();

function hellordWordFileChooser() {
	
	$('body').append("<div id='exampleLoadingImageContent' style='border:solid 1px red;width:50%;height:50%;position:absolute;top:25%;left:25%;z-index:10000;background-color:red;'></div>");
	$('#exampleLoadingImageContent').append("<div id='imageChooserContent' style='border:solid 1px red;width:100%;'><input id='imageChooserInput' type='file'  accept='image/x-png,image/gif,image/jpeg'></div>");
	$('#exampleLoadingImageContent').append("<div id='imageContent' style='border:solid 1px red;width:30%;'></div>");
	
	gererChangementImage("imageChooserInput" ,"imageContent" ,"apercuImageContent");
}

/**
 * 
 * @param {type} idFileElement
 * @param {type} idDivImageContent
 * @param {type} idApercuImageContent
 * @returns {undefined}
 */
function gererChangementImage(idFileElement ,idDivImageContent ,idApercuImageContent){
	//console.log(idFileElement+" ==== "+document.querySelector('#'+idFileElement));	
	document.querySelector('#'+idFileElement).onchange = function() {
			
		var fileInput = document.querySelector('#'+idFileElement);
		var allowedTypes = ['png', 'jpg', 'jpeg', 'gif'];
				
		var files = this.files,       
		filesLen = files.length,      
		imgType;       

		for (var i = 0 ; i < filesLen ; i++) {              

			imgType = files[i].name.split('.');       

			imgType = imgType[imgType.length - 1].toLowerCase(); // On utilise toLowerCase() pour éviter les extensions en majuscules             

			if(allowedTypes.indexOf(imgType) != -1) { // Le fichier est bien une image supportée, il ne reste plus qu'à l'afficher                  
				
				//On affiche l'appercu				
				createThumbnail(files[i],idDivImageContent,idApercuImageContent);
				
			}else{
				
				//Code
			}           
		}	
	};
	
        /**
         * 
         * @param {type} file
         * @param {type} idDivImageContent
         * @param {type} idApercuImageContent
         * @returns {undefined}
         */
	function createThumbnail(file,idDivImageContent,idApercuImageContent) {
		var reader = new FileReader();       
		reader.onload = function() {                
					
			var imgElement = document.querySelector('#'+idApercuImageContent);      
			 imgElement.src = this.result;          

		};        
		reader.readAsDataURL(file);
	}		
}