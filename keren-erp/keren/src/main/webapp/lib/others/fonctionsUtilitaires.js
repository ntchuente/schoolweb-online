		function generateurIdAvance() {
		   return  getTimeFormatNumerique()+getDateFormatNumerique()+randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomNumber(0, 9999999999999999999, true);
		}
		
		function obtenirNombreAleatoire( min, max) {
			
			return Math. floor( Math. random( ) * ( max - min + 1) + min) ;
		}
		
		function generateurId() {
		   return randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomLetter()+randomNumber(0, 9999999999999999999, true);
		}
		
		function randomNumber(min, max, integer) {
		    if (!integer) {
		        return Math.random() * (max - min) + min;
		    } else {
		        return Math.floor(Math.random() * (max - min + 1) + min);
		    }
		}
		
		function randomLetter() {
		    var alphabet = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
			return alphabet[randomNumber(0, 25, true)];
		}
		
		function getSizeTab(tab){
			
			var nb = 0;
			
			for (var id in tab) {
				nb++;
			}
			
			return nb;
		}
		
		function getDateFormat() {
			var date = new Date();
		    var jour = date.getDate()+'';
			var mois = (date.getMonth()+1)+'';
			var annee = date.getFullYear()+'';
			
			if(jour.length == 1){
				jour = '0'+jour;
			}else if(mois.length == 1){
				mois = '0'+mois;
			}
			return jour+'/'+mois+'/'+annee;
		}
		
		function getDateFormatNumerique() {
			var date = new Date();
		    var jour = date.getDate()+'';
			var mois = (date.getMonth()+1)+'';
			var annee = date.getFullYear()+'';
			
			if(jour.length == 1){
				jour = '0'+jour;
			}else if(mois.length == 1){
				mois = '0'+mois;
			}
			return jour+mois+annee;
		}
		
		function getTimeFormatNumerique() {
			var date = new Date();
		    var heure = date.getHours()+'';
			var minute = date.getMinutes()+'';
			var seconde = date.getSeconds()+'';
			
			if(heure.length == 1){
				heure = '0'+heure;
			}else if(minute.length == 1){
				minute = '0'+minute;
			}else if(seconde.length == 1){
				seconde = '0'+seconde;
			}
			return heure+minute+seconde;
		}
		
		function getTimeFormat() {
			var date = new Date();
		    var heure = date.getHours()+'';
			var minute = date.getMinutes()+'';
			var seconde = date.getSeconds()+'';
			
			if(heure.length == 1){
				heure = '0'+heure;
			}else if(minute.length == 1){
				minute = '0'+minute;
			}else if(seconde.length == 1){
				seconde = '0'+seconde;
			}
			return heure+':'+minute+':'+seconde;
		}
		
		function reduireTailleTexteSiLong(texteAreduire){
			if(texteAreduire.length <= 25){
				return texteAreduire;
			}else{
				texteAreduire = texteAreduire.substring(0,25);
				return texteAreduire+' ...';
			}
		}
		
		function reduireTailleTexteSiLongSpecialPourRechercheAmis(texteAreduire){
			if(texteAreduire.length <= 16){
				return texteAreduire;
			}else{
				texteAreduire = texteAreduire.substring(0,16);
				return texteAreduire+' ...';
			}
		}
		
		function reduireTailleTexteSiLongGeneral(texteAreduire,taille){
			if(texteAreduire.length <= taille){
				return texteAreduire;
			}else{
				texteAreduire = texteAreduire.substring(0,taille);
				return texteAreduire+' ...';
			}
		}
		
		function reduireTailleTexteSiLongSpecialPourAmisConnectes(texteAreduire){
			if(texteAreduire.length <= 16){
				return texteAreduire;
			}else{
				texteAreduire = texteAreduire.substring(0,16);
				return texteAreduire+' ...';
			}
		}
		
		function jouerUneMusique(cheminMusique){
			var sound = new Howl({
				  urls: [cheminMusique],
				});
				sound.volume(0.90);
				sound.play();
		}
		
		function getSizeInKo(tailleEnOctet){
			return Math.round(tailleEnOctet/(1024));
		}
		
		function getSizeInMo(tailleEnOctet){
			return Math.round(tailleEnOctet/(1024*1024));
		}
		
		function getSizeInGo(tailleEnOctet){
			return Math.round(tailleEnOctet/(1024*1024*1024));
		}
		
		function formatterDateTexte(date){
	
		var jour = date.split('-')[2];
		var mois = date.split('-')[1];;
		var moisEnLettre = '';
		var annee = date.split('-')[0];;
		
		if(parseInt(mois) == 1){
			
			moisEnLettre = 'Janvier';
		}else if(parseInt(mois) == 2){
			
			moisEnLettre = 'Fevrier';
		}else if(parseInt(mois) == 3){
			
			moisEnLettre = 'Mars';
		}else if(parseInt(mois) == 4){
			
			moisEnLettre = 'Avril';
		}else if(parseInt(mois) == 5){
			
			moisEnLettre = 'Mai';
		}else if(parseInt(mois) == 6){
			
			moisEnLettre = 'Juin';
		}else if(parseInt(mois) == 7){
			
			moisEnLettre = 'Juillet';
		}else if(parseInt(mois) == 8){
			
			moisEnLettre = 'Aout';
		}else if(parseInt(mois) == 9){
			
			moisEnLettre = 'Septembre';
		}else if(parseInt(mois) == 10){
			
			moisEnLettre = 'Octobre';
		}else if(parseInt(mois) == 11){
			
			moisEnLettre = 'Novembre';
		}else if(parseInt(mois) == 12){
			
			moisEnLettre = 'Decembre';
		}
		
		return  jour+' '+moisEnLettre+' '+annee;
	}
	
	function genererDateOptionSelect(dateDebut,dateFin){
		
		var data = "";
		
		for(var i=dateDebut;i<=dateFin;i++){
			data = data + "<option id="+i+">"+i+"</otion>";
		}
		
		return data;
	}
	
	function formaterDate(heureDiff,dateDiff){
		
		var nombreDeJour = parseInt(dateDiff);
		var nombreheures = parseInt(heureDiff.split(':')[0]);
		var nombreDeminutes = parseInt(heureDiff.split(':')[1]);
		var nombreSecondes = parseInt(heureDiff.split(':')[2]);
		var texteDate = '';
					
		if(nombreDeJour == 0){
						
			if(nombreheures == 0 && nombreDeminutes == 0){
							
				texteDate = "a l'instant";
							
			}else if(nombreheures == 0 && nombreDeminutes == 1){
							
				texteDate = "il y a 1 minute";
			}else if(nombreheures == 0){
							
				texteDate = "il y a "+nombreDeminutes+" minutes";
			}else if(nombreheures == 1){
							
				texteDate = "il y a une 1 heure";
			}else{
							
					texteDate = "il y a "+nombreheures+" heures";
			}
						
						
		}else if(nombreDeJour == 1){
						
			texteDate = "hier";
		}else  if(nombreDeJour <=6){
						
			texteDate = "il y a "+nombreDeJour+" jours";
		}else  if(nombreDeJour == 7 ){
						
			texteDate = "il y a 1 semaine";
		}else  if(nombreDeJour > 7 && nombreDeJour < 30){
						
			texteDate = "il y a quelques semaines";
		}else  if(nombreDeJour == 30){
						
			texteDate = "il y a 1 mois";
		}else  if(nombreDeJour > 30 && nombreDeJour < 365){
						
			texteDate = "il y a quelques mois";
		}else  if(nombreDeJour  ==  365){
						
			texteDate = "il y a 1 an";
		}else  if(nombreDeJour  >  365){
						
			texteDate = "il y a quelques annees";
		}
		
		return texteDate;
	}
	
	function formaterDateLight(heureDiff,dateDiff){
		
		var nombreDeJour = parseInt(dateDiff);
		var nombreheures = parseInt(heureDiff.split(':')[0]);
		var nombreDeminutes = parseInt(heureDiff.split(':')[1]);
		var nombreSecondes = parseInt(heureDiff.split(':')[2]);
		var texteDate = '';
					
		if(nombreDeJour == 0){
						
			if(nombreheures == 0 && nombreDeminutes == 0){
							
				texteDate = "2s";
							
			}else if(nombreheures == 0 && nombreDeminutes == 1){
							
				texteDate = "1 mn";
			}else if(nombreheures == 0){
							
				texteDate = nombreDeminutes+" mn";
			}else if(nombreheures == 1){
							
				texteDate = "1 h";
			}else{
							
				texteDate = nombreheures+" h";
			}
						
						
		}else if(nombreDeJour == 1){
						
			texteDate = "hier";
		}else  if(nombreDeJour > 1){
						
			texteDate = nombreDeJour+" j";
		}
		
		return texteDate;
	}
	
	function retirerAccent (my_string) { 
			
		var r=my_string.toLowerCase();
		r = r.replace(new RegExp(/\s/g),"");
		r = r.replace(new RegExp(/[������]/g),"a");
		r = r.replace(new RegExp(/�/g),"ae");
		r = r.replace(new RegExp(/�/g),"c");
		r = r.replace(new RegExp(/[����]/g),"e");
		r = r.replace(new RegExp(/[����]/g),"i");
		r = r.replace(new RegExp(/�/g),"n");                
		r = r.replace(new RegExp(/[�����]/g),"o");
		r = r.replace(new RegExp(/�/g),"oe");
		r = r.replace(new RegExp(/[����]/g),"u");
		r = r.replace(new RegExp(/[��]/g),"y");
		//r = r.replace(new RegExp(/\W/g),"");
			
		return r;
	}
	
	//Recupere les donnees depuis le serveur
	function getDataToServer(url, method, login, motDePasse,functionCall){
		
		$.ajax({ 
		xhrFields: {
				withCredentials: true
		},
			beforeSend: function (xhr) {
				xhr.setRequestHeader('Authorization', 'Basic ' + btoa(login+':'+motDePasse));
		},
			type: method,
			dataType: "json",
			url: url,
			success: function(data){ 
					
				//On appelle la fonction
				functionCall(data);
			}
		});
	}
        
        //Recupere les donnees depuis le serveur
	function getDataToServerImage(url, method, login, motDePasse,functionCall){
		
		$.ajax({ 
		xhrFields: {
				withCredentials: true
		},
			beforeSend: function (xhr) {
				xhr.setRequestHeader('Authorization', 'Basic ' + btoa(login+':'+motDePasse));
		},
			type: method,
			dataType: "binary",
			url: url,
			success: function(data){ 
					
				//On appelle la fonction
				functionCall(data);
			},
			error: function(data){ 
					
				//On appelle la fonction
				functionCall(data);
			}
		});
	}
	