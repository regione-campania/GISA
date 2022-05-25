/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*SORGENTE DEL CONTROLLER USATO PER CREARE NUOVA ANAGRAFICA */


/*
 * 
 * 
 * 
 ***************************************************************** CONTROLLERS *****************************************************************
 * 
 * 
 * 
 * */
	
	/*ottengo il main module */
	var main_module = angular.module("main_module");
	/*creo controller */
	var controller_nuova_anagrafica = main_module.controller(
			"controller_nuova_anagrafica",
			["$scope","$http","comuniService","fetchLookupService","calcolaCFService","calcolaCoordinateService","ottieniMasterListService","ottieniCampiEstesiLineaService","$log","$compile","inviaDatiAlServerAsJSONService"
			,function($scope,$http,comuniService,fetchLookupService,servizioCalcoloCF, servizioCalcoloCoordinate,ottieniMasterListService ,ottieniCampiEstesiLineaService ,$log,$compile, inviaDatiAlServerAsJSONService){
				 	
				
				
				 $scope.dataarrivopec_scelta = new Date();
				 
				 $scope.prendiSoloCampani = function(items)
				 {
					 var soloCampani = {};
					 angular.forEach(
							 items,
							 function(val,key) /*il foreach di angular va pure sulle mappe */	
							 { 
								 if(val.cod_regione == '15')
								 {
									 soloCampani[key]=val;
								 }
							 }
					 );
					 return soloCampani;
				 }
				 
				 
				 $scope.comuni = null;
				 
				 
				 
				 /*
				 $scope.comuniItaliani = null;
				 $scope.comuniCampani = null; 
				 */
				 
				 /*ottengo comuni italiani */
				 comuniService.getComuniItaliani(
						 function(data) /*callback se successo */
						 {
							 /*serve per iterare piu' semplicemente con ng-repeat...*/
							 $scope.comuni = data.comuni;
							 /*utili per risolvere il reverse */
							 $scope.idComuniToNomi = data.idComuniToNomi; 
							 
							 /*TODO: PERCHE' NON AGGANCIA SU NAPOLI NELLA SELECT ? */
							 //$scope.id_comune_scelto = +$scope.comuni['NAPOLI'].id;
							 $scope.id_comune_stabilimento_scelto =  +$scope.comuni['NAPOLI'].id;
							 
							 
 
						 }
						 , 
						 function() /* se errore */
						 {
							 alert("errore");
						 }
				 );
			   
				 
			     
				 /*DATI IMPRESA **********************************************************/
			     
			     $scope.tipi_imprese = null;
			     fetchLookupService.getValuesFromLookup("LOOKUP_TIPO_IMPRESA_SOC","code","description",
			    		 function(data) /*callback se successo */
			    		 {
			    	 		$scope.tipi_imprese = data.entries;
			    	 		$scope.id_tipo_impresa_scelto = $scope.tipi_imprese[0].code; /*questo grazie all'ng-model sulla select fa si che venga selezionato */
			    		 }
			     		,function() /*callback se errore */
			     		{
			     			alert("errore");
			     		}
			     );
			     
			     
			     $scope.cf_scelto = '';
			     
			     $scope.toponimi = null;
			     fetchLookupService.getValuesFromLookup("lookup_toponimi","code","description",
			    		 function(data) /*callback se successo */
			    		 {
				    	 		$scope.toponimi = data.entries;
				    	 		$scope.idtoponimoindirizzolegale_scelto = $scope.toponimi[0].code; /*questo grazie all'ng-model sulla select fa si che venga selezionato */
							    $scope.idtoponimoindirizzostabilimento_scelto = $scope.toponimi[0].code;
							    $scope.idtoponimosedelegalelegale_scelto = $scope.toponimi[0].code;
							    $scope.idtoponimosedelegale_scelto = $scope.toponimi[0].code;
			    		 }
				     	 ,function() /*callback se errore */
				     	 {
				     		alert("errore");
				     	 }
				 );
			     
			     
			     $scope.idsessolegale_scelto = 0;
			     
			     
			     /* TIPO ATTIVITA NON DEVE ESSERE PIU' USATO PER NO SCIA
			     $scope.tipiattivita = null;
			     fetchLookupService.getValuesFromLookup("LOOKUP_TIPO_ATTIVITA","code","description",
			    		 function(data)  
			    		 {
				    	 		$scope.tipiattivita = data.entries;
				    	 		$scope.idtipoattivita_scelto = $scope.tipiattivita[0].code; 
				    	  }
				     	 ,function()  
				     	 {
				     		alert("errore");
				     	 }
				 );
			     */
			     
			     
			     $scope.tipicarattere = null;
			     fetchLookupService.getValuesFromLookup("LOOKUP_TIPO_IMPRESA_SOC","code","description",
			    		 function(data) /*callback se successo */
			    		 {
				    	 		$scope.tipicarattere = data.entries;
				    	 		$scope.idtipocarattere_scelto = $scope.tipicarattere[0].id; /*questo grazie all'ng-model sulla select fa si che venga selezionato */
				    	  }
				     	 ,function() /*callback se errore */
				     	 {
				     		alert("errore");
				     	 }
				 );
			     
			     
			     

				 /*DATI RAPP LEGALE **********************************************************/
			     
			     /*funzione usata per valutare se attivare o meno il bottone di genera cf per legale*/
			      
			     
			     $scope.flag_generacf_attivabile = false;
			     
			     $scope.valutaSePossoGenerareCf = function()
			     {
			    	 $scope.flag_generacf_attivabile = 
			    		 $scope.form_nuova_anagrafica.cognomelegale_scelto.$valid && 
			    		 $scope.cognomelegale_scelto.trim().length != 0 && 
						 $scope.form_nuova_anagrafica.nomelegale_scelto.$valid && 
						 $scope.nomelegale_scelto.trim().length != 0 &&
 						 $scope.datanascitalegale_scelto != undefined &&
						 $scope.form_nuova_anagrafica.datanascitalegale_scelto.$valid && 
 						 $scope.datanascitalegale_scelto.trim().length != 0 &&
 						 
 						 (
 								 $scope.desccomunenascitalegale_scelto != undefined && $scope.desccomunenascitalegale_scelto.trim().length > 0
 								 && ((''+$scope.desccomunenascitalegale_scelto.toUpperCase() ) in $scope.comuni)/*la descrizione del comune nascita legale deve esistere tra i comuni italiani */
 						 );	
			    	 
			    	 	$log.info((
 								 $scope.desccomunenascitalegale_scelto+","+$scope.desccomunenascitalegale_scelto != undefined && $scope.desccomunenascitalegale_scelto.trim().length > 0
 								// && ((''+$scope.desccomunenascitalegale_scelto.toUpperCase() ) in $scope.comuni)/*la descrizione del comune nascita legale deve esistere tra i comuni italiani */
 						 ));
			    	 
			    	 return $scope.flag_generacf_attivabile;
 								
			     }
			     
			     
			     $scope.$watch("flag_generacf_attivabile",function(){}); /*metto nel digest loop questa variabile in maniera tale che quando aggiornata ...
			     
			     /*questa funzione parte quando si cambia uno dei campi agganciato al cf scelto del legale */
			     $scope.generaCfLegale = function()
			     {
			    	 /*non aggiorno se i campi messi non sono validi o non aggiornati */
			    	 
			    	 var formt = $scope.form_nuova_anagrafica;
			    	 
			    	 if(formt.nomelegale_scelto.$valid 
			    		&& formt.cognomelegale_scelto.$valid  
			    		&& formt.datanascitalegale_scelto.$valid 
			    	 	)
			    	   {
			    		 
			    		 	 var descComune = $scope.idComuniToNomi[$scope.comunenascitalegale_scelto]; /*ottengo desc comune da id */
			    		 	 var descSesso = $scope.idsessolegale_scelto == 0 ? 'M' : 'F';
			    		 	 
			    		     servizioCalcoloCF.calcolaCF( 
			    				  descSesso,
			    				  $scope.nomelegale_scelto, 
			    				  $scope.cognomelegale_scelto, 
			    				  descComune,
			    				  $scope.datanascitalegale_scelto,
			    				  function(data){ /*success callback */
			    					  $scope.cflegale_scelto = data.cf;
			    				  },
			    				  function(data){ /*errore callback */
			    					  alert("errore");
			    				  });
			    	   }
			     }
			     
			     
			     

				 /*DATI SEDE LEGALE **********************************************************/
			     $scope.sedelegale_corrisponde_stabilimento = false;
			     
			     
			     
			     
			     

				 /*DATI STABILIMENTO **********************************************************/
			     
			     $scope.isIndirizzoStabValido = false;
			   
			 			
			     $scope.aggiornaCorrettezzaIndirizzoStab = function()
			     {
			    	 $scope.isIndirizzoStabValido =  $scope.form_nuova_anagrafica.viastabilimento_scelto.$valid &&
			    	 		$scope.form_nuova_anagrafica.civicostabilimento_scelto.$valid &&
			    	 		$scope.form_nuova_anagrafica.capstabilimento_scelto.$valid;
			    	 		
			     }
			     
			     $scope.$watch("isIndirizzoStabValido",function(){}); /*metto il watch come pe ril flag di genera cf legale */
			     
			     
			     $scope.calcolaCoordinate = function()
			     {
			    	 var indirizzo =
			    		 $scope.viastabilimento_scelto +' ' + $scope.civicostabilimento_scelto +  ' '+ $scope.capstabilimento_scelto;
			    	 console.log(indirizzo);
			    	 var idcomune = $scope.id_comune_stabilimento_scelto;
	    		 	 var descComune = $scope.idComuniToNomi[idcomune]; /*ottengo desc comune da id */
	    		 	 
			    	 var descprovincia = $scope.provinciastabilimento_scelto;
			    	 
			    	 servizioCalcoloCoordinate.calcolaCoordinate(
			    			 indirizzo,
			    			 descComune,
			    			 descprovincia,
			    			 function(data) /*callback success */
			    			 {
			    				 
			    				 if(data.esito_geo.toLowerCase() == 'a' || data.esito_geo.toLowerCase() == 'v') /*il geocoder server side risponde in questo modo*/
			    				 {
			    					 $scope.latitudine_scelto = data.lat;
			    					 $scope.longitudine_scelto = data.lng;
			    				 }
			    				 
			    			 }
			    	 		,function(data) /*callback errore */
			    	 		{
			    	 			alert("errore");
			    	 		}
			    	 );
			     }
			     
			     
			     

				 /*MASTER LIST **********************************************************/
			     
			     $scope.pathsAggiunte = [];
			     
			     
			     /*per i campi estesi -------------------------------------------------------------------*/
			     /*indice entry serve per indicare, sulla struttura delle entry, a quale si faceva riferimento per i campi */
			     $scope.aggiornaCampiEstesiLinea = function(indpath,idLineaML,indiceEntry)
			     {
			    	 $log.info("chiedo campi estesi per linea "+idLineaML+" path "+indpath);
			    	 /*cambio cursor */
			    	 $(document.body).css({"cursor" : "wait"});
			    	 
			    	 ottieniCampiEstesiLineaService.getCampiEstesiLinea(
			    		idLineaML
			    		,-1 /*perche' non ci interessa istanza valore visto che siamo in inserimento */
			    		,function (data) /*callback success */
			    		{
			    			
			    			 
			    			/*aggiungo i campi estesi nella struttura della linea nel path
			    			 */
			    			
			    			/*i campi estesi arrivano ordinati per id istanza dei valori, ma noi abbiamo passato -1 come id istanza
			    			 * quindi prendiamo la prima chiave che arriva
			    			 */
			    			if(Object.keys(data.campiEstesi).length > 0) /*altrimenti non ha trovato proprio campi estesi */
			    			{
			    				var dummyidistanza = Object.keys(data.campiEstesi)[0];
			    				$scope.pathsAggiunte[indpath].entries_precedenti_aggiunte[indiceEntry].campi_estesi = data.campiEstesi[dummyidistanza];
			    				
			    				
			    				for(var ind = 0; ind < $scope.pathsAggiunte[indpath].entries_precedenti_aggiunte[indiceEntry].campi_estesi.length; ind++)
			    				{
			    					$log.info(indpath+","+indiceEntry);
			    					$scope.pathsAggiunte[indpath].entries_precedenti_aggiunte[indiceEntry].campi_estesi[ind].campo_value = undefined;
			    				}
			    			
			    				$log.info("arrivati campi estesi per linea "+idLineaML+" path "+indpath);
				    			
				    			
			    			}
			    			/*il cursore torna normale */
			    			$(document.body).css({"cursor" : "default"});
			    			
			    		}
			    		,function (data) /*callback errore */
			    		{
			    			alert("errore");

			    			/*il cursore torna normale */
			    			$(document.body).css({"cursor" : "default"});
			    		}
			    	 );
			     } 
			     
			    
			    
			    /*metodo per inizializzare una nuova struttura per una nuova path */
			    $scope.aggiungipath = function()
			    {
			    	/*faccio append del dummy alla master list , ne faccio clone per non sporcarle*/
			    	
			    	var mlt = angular.copy($scope.masterList);//$.extend({},$scope.masterList);//new Map($scope.masterList);
			    	var dummy = {id : -1, desc : 'Scegli livello', figli : [], campi_estesi : []};
			    	mlt[-1] = dummy;
			    	
			    	var nuovapath = 
			    	{
			    			last_selected_id : -1,
			    			entries_selezionabili : mlt, /*mappa */
			    			entries_precedenti_aggiunte : [], /*lista */
			    			trashed : false,
			    			path_esaurito : false
			    			,data_inizio : undefined
			    			,data_fine : undefined
			    			 
			    			
			    	};
			    	
			    	$scope.pathsAggiunte.push(nuovapath);
			    	
			    	/*ovviamente aggiorno flag che indica che non tutte le paths sono completate */
			    	$scope.flag_tutte_paths_completate = false;
			    	$scope.flag_almeno_unalinea_completamente_vuota = true;
			    	
			    	/*Devo mettere (con jquery widget e non con angular il datepicker sopra, quindi aspetto un po che angular generi il dom
			    	 * 
			    	 */
			    	var lastIndex =$scope.pathsAggiunte.length -1 ;
			    	var regex0 = new RegExp("^data_fine_path_"+lastIndex+"$");
			    	var regex1 = new RegExp("^data_inizio_path_"+lastIndex+"$");
			    	setTimeout(function(){
			    		$("input[type='text'][id]").filter(function(){
			    			return $(this).attr("id").match(regex0);
			    		}).datepicker();
			    		
			    		$("input[type='text'][id]").filter(function(){
			    			return $(this).attr("id").match(regex1);
			    		}).datepicker();
			    	},1000);
			    	
			    }
			     
			    
			    
			    $scope.cancellapath = function(indpath)
			    {
			    	
			    	var totNonTrashed = 0;
			    	 $scope.pathsAggiunte.forEach(function(path,indexKey)
			    	{
			    		totNonTrashed =  !path.trashed ? totNonTrashed +1 : totNonTrashed;
			    	});
			    	
			    	if(totNonTrashed < 2) /*una deve sempre esserci */
			    	{
			    		return;
			    	}
			    	/*per pulizia gli svuoto anche le strutture */
			    	$scope.pathsAggiunte[indpath].last_selected_id = -1;
			    	$scope.pathsAggiunte[indpath].entries_selezionabili = [];
			    	$scope.pathsAggiunte[indpath].entries_precedenti_aggiunte = [];
			    	$scope.pathsAggiunte[indpath].trashed = true; /*questo non la fara' piu' apparire */
			    	$scope.pathsAggiunte[indpath].data_inizio = undefined;
			    	$scope.pathsAggiunte[indpath].data_fine = undefined;
			    	
			    	$scope.aggiornaStatoPaths();
			    }
			     
			    $scope.resetpath = function(indpath)
			    {
			    	var mlt = angular.copy($scope.masterList);//$.extend({},$scope.masterList);//new Map($scope.masterList);
			    	var dummy = {id : -1, desc : 'Scegli livello', figli : [] , campi_estesi : []};
			    	 
			    	mlt[-1] = dummy;
			    	
			    	var nuovapath = 
			    	{
			    			last_selected_id : -1,
			    			entries_selezionabili : mlt, /*mappa */
			    			entries_precedenti_aggiunte : [], /*lista */
			    			trashed : false,
			    			path_esaurito : false
			    			,data_inizio : undefined
			    			,data_fine : undefined
			    			
			    	};
			    	
			    	$scope.pathsAggiunte[indpath] = (nuovapath);
			    	
			    	/*ovviamente aggiorno flag che indica che non tutte le paths sono completate */
			    	$scope.flag_tutte_paths_completate = false;
			    	 $scope.flag_almeno_unalinea_completamente_vuota = true;
			    	
			    	
			    	/*per qualche motivo si cancellano i datepicker widget, quindi li rimetto*/
			    	var lastIndex =$scope.pathsAggiunte.length -1 ;
			    	var regex0 = new RegExp("^data_fine_path_"+indpath+"$");
			    	var regex1 = new RegExp("^data_inizio_path_"+indpath+"$");
			    	setTimeout(function(){
			    		$("input[type='text'][id]").filter(function(){
			    			return $(this).attr("id").match(regex0);
			    		}).datepicker();
			    		
			    		$("input[type='text'][id]").filter(function(){
			    			return $(this).attr("id").match(regex1);
			    		}).datepicker();
			    	},1000);
			    	
			    }
			    
			    
			    
			    
			    /*creo funzione che aggiorna la variabile di stato per
			      indicare che tutti i path sono stati esauriti (ATTENZIONE
			      A CONSIDERARE SOLO LE NON CANCELLATE)
			      */
			     $scope.flag_tutte_paths_completate = false;
			     
			     /*e lo stesso per flag che mi indica se esiste almeno una linea che ha il dummy selezionato anche al primo nodo
			      * (COMPLETAMENTE VUOTA, -> IN TAL CASO DEVO BLOCCARE L'INVIO DATI )
			      */
			     $scope.flag_almeno_unalinea_completamente_vuota = false;
			     
			     
			     $scope.aggiornaStatoPaths = function()
			     {
			    	 var tflag = true;
			    	 
			    	 /*controllo se tutti i path sono esauriti fino all'ultimo */
			    	 $scope.pathsAggiunte.forEach(function(path,index){
			    		  tflag = tflag && (path.trashed || path.path_esaurito);
			    	 });
			    	 
			    	 $scope.flag_tutte_paths_completate = tflag;
			    	 
			    	 
			    	 /*controllo se esiste almeno un path che ha selezionato il dummy al primissimo nodo (LINEA COMPLETAMENTE
			    	  * VUOTA QUINDI )
			    	  */
			    	 if(!$scope.flag_tutte_paths_completate )
			    	 {
			    		 tflag = false;
			    		 
			    		 $scope.pathsAggiunte.forEach(function(path,idx){
			    			 tflag = 
			    				 tflag || 
			    			 (
			    					 !path.trashed &&  (path.entries_precedenti_aggiunte.length == 0) && (path.last_selected_id == -1)
			    			 );
			    		 });
			    		 $scope.flag_almeno_unalinea_completamente_vuota = tflag;
			    	 }
			    	 else
			    	 { 	/*se tutti i path sono esauriti, sicuramente non esiste nessuna con un dummy al primo livello*/
			    		 $scope.flag_almeno_unalinea_completamente_vuota = false; 
			    	 }
			    	 
			    	 $log.info( $scope.flag_tutte_paths_completate+"\n"+$scope.flag_almeno_unalinea_completamente_vuota);
			     
			     }
			     
			     /*metto il watch */
			     $scope.$watch("flag_almeno_unalinea_completamente_vuota",function(){});
			     
			    
			     
			     
			     
			     
			    /*parte quando scegliamo un valore non dummy nella select
			     * ultimo livello non vuol dire che sia realmente ultimo nel path della master list (questo viene valutato)
			     
			     */
			    $scope.selezionataEntryUltimoLivello = function(indpath)
			    {
			    	var selIdEntry = $scope.pathsAggiunte[indpath].last_selected_id ; /*l'id selezionato sta mappato nel model con angular */
			    	if(selIdEntry == -1) /*ho selezionato il dummy */
			    	{
			    		return;
			    	}
			    	else
			    	{
			    		/*estraggo, usando l'id selezionato, l'oggetto */
			    		var entrySelezionata = $scope.pathsAggiunte[indpath].entries_selezionabili[selIdEntry];
			    		/*faccio il push dell'ultima selezione */
			    		$scope.pathsAggiunte[indpath].entries_precedenti_aggiunte.push(entrySelezionata);
			    		
			    		/*se i figli e' vuota vuol dire che siamo arrivati fino alla fine , quindi setto il flag che mi dice che per questa
			    		 * path abbiamo esaurito path
			    		 */
			    		 
			    		
			    		
			    		if(Object.keys(entrySelezionata.figli).length == 0)
			    		{
			    			$scope.pathsAggiunte[indpath].path_esaurito = true;
			    		
			    		}
			    		else
			    		{
			    			/*metto i figli del selezionato nella struttura usata per il mapping della select */
				    		/*prima pero' ci aggiungo il dummy dopo averle clonate */
				    		var figlit = $.extend({},entrySelezionata.figli);//new Map(entrySelezionata.figli);
			    			var dummy = {id : -1, desc : 'Scegli livello', figli : [], campi_estesi : []};
				    		figlit[-1] = dummy; /*metto il dummy nella clonata */
				    		$scope.pathsAggiunte[indpath].entries_selezionabili = figlit; /*aggancio la clonata */
				    		/*metto a -1 il model dell'id selezionato (questo farà selezionare il dummy*/
				    		$scope.pathsAggiunte[indpath].last_selected_id = -1;
			    		}
			    		
			    		/*I CAMPI ESTESI SONO ASSOCIABILI ORA A QUALUNQUE LIVELLO */
			    		/*passo anche indice entry per indicare su quale entry vanno aggiornati i campi estesi */
			    		var indiceEntry = $scope.pathsAggiunte[indpath].entries_precedenti_aggiunte.length -1;
			    		$log.info("aggiorno ce "+indpath);
		    			$scope.aggiornaCampiEstesiLinea(indpath,selIdEntry, indiceEntry  );
		    			
			    		/*lancio aggiornamento stato per vedere se li ho completate tutte */
			    		$scope.aggiornaStatoPaths();
			    		
			    	}
			    	
			    	
			    	
			    }
			     
			     /*otteniamo la struttura master list */
			     /*per il flusso dei no scia */
			     ottieniMasterListService.scaricaMasterListFiltrata(
			    		 'NO-SCIA',  
			    		function(data){ /*callbacksuccess */
			    	 		$scope.masterList = data.masterList;
			    	 		/*ora che e' arrivata la master list aggiungo almeno una nuova path */
			    	 		$scope.aggiungipath();
			    	 		 
			    	 		
			     		}
			     		,function(data) /*callbackerrore*/
			     		{
			     			alert("errore");
			     		}
			     );
			     
			     
			     
			     
			     
			     /*funzione di validazione di tutti gli stati valid del form, dei campi estesi etc, usata
			      * per attivare il bottone di submit
			      *
			      * Lo stato del form e' direttamente ottenibile con form.$valid, poi bisogna 
			      * vedere se anche tutti i path sono stati esauriti
			      */
			     
			     $scope.inviaDati = function()
			     {
			    	 
			    	 /*ora e' possibile comunque inserire anche se non si sono scelti i livelli fino alla foglia per le
			    	  * linee di attività. Tuttavia avvisiamo l'utente
			    	  */
			    	 if($scope.flag_tutte_paths_completate == false)
			    	 {
			    		 var resp = confirm("Una o piu' linee di attività hanno livelli aggiuntivi non scelti. Continuare comunque ?");
			    		 if(resp == false)
			    			 return;
			    	 }
			    	 dataToSend = {};
			    	 /*recupero tutti i dati----------- */
			    	 /*IMPRESA-----*/
			    	 dataToSend.dataarrivopec_scelto = $scope.dataarrivopec_scelto;
			    	 //dataToSend.id_comune_scelto = $scope.id_comune_scelto;
			    	 dataToSend.id_tipo_impresa_scelto = $scope.id_tipo_impresa_scelto;
			    	 dataToSend.ditta_scelto = $scope.ditta_scelto;
			    	 dataToSend.piva_scelto = $scope.piva_scelto;
			    	 dataToSend.cf_scelto = $scope.cf_scelto;
			    	 dataToSend.cf_uguale_piva = $scope. cf_uguale_piva;
			    	 dataToSend.pec_scelto = $scope. pec_scelto;
			    	 dataToSend.note_scelto = $scope.note_scelto;
			    	 /*RAPP LEGALE ------*/
			    	 dataToSend.nomelegale_scelto = $scope.nomelegale_scelto;
			    	 dataToSend.cognomelegale_scelto = $scope.cognomelegale_scelto;
			    	 dataToSend.idsessolegale_scelto = $scope.idsessolegale_scelto;
			    	 dataToSend.datanascitalegale_scelto = $scope.datanascitalegale_scelto;
			    	 dataToSend.nazionenascitalegale_scelto = $scope.nazionenascitalegale_scelto;
			    	 dataToSend.desccomunenascitalegale_scelto = $scope.desccomunenascitalegale_scelto;
			    	 dataToSend.cflegale_scelto = $scope.cflegale_scelto;
			    	 dataToSend.nazioneresidenzalegale_scelto = $scope. nazioneresidenzalegale_scelto;
			    	 dataToSend.provinciaresidenzalegale_scelto = $scope. provinciaresidenzalegale_scelto;
			    	 dataToSend.comuneresidenzalegale_scelto = $scope.comuneresidenzalegale_scelto;
			    	 dataToSend.idtoponimoindirizzolegale_scelto = $scope.idtoponimoindirizzolegale_scelto;
			    	 dataToSend.vialegale_scelto = $scope.vialegale_scelto;
			    	 dataToSend.civicolegale_scelto = $scope.civicolegale_scelto;
			    	 dataToSend.caplegale_scelto = $scope.caplegale_scelto;
			    	 dataToSend.telefonolegale_scelto = $scope.telefonolegale_scelto;
			    	 dataToSend.peclegale_scelto = $scope.peclegale_scelto ;
			    	 /*DATI SEDE LEGALE */
			    	 dataToSend.sedelegale_corrisponde_stabilimento = $scope.sedelegale_corrisponde_stabilimento;
			    	 dataToSend.nazionesedelegale_scelto = $scope.nazionesedelegale_scelto;
			    	 dataToSend.provinciasedelegale_scelto = $scope.provinciasedelegale_scelto;
			    	 dataToSend.comunesedelegale_scelto = $scope.comunesedelegale_scelto;
			    	 dataToSend.idtoponimosedelegale_scelto = $scope.idtoponimosedelegale_scelto;
			    	 dataToSend.viasedelegale_scelto = $scope.viasedelegale_scelto;
			    	 dataToSend.civicosedelegale_scelto = $scope.civicosedelegale_scelto;
			    	 dataToSend.capsedelegale_scelto = $scope.capsedelegale_scelto;
			    	 /*STAB--------*/
			    	 //dataToSend.idtipoattivita_scelto = $scope.idtipoattivita_scelto;
			    	 dataToSend.denominazionestab_scelto = $scope.denominazionestab_scelto;
			    	 dataToSend.idtipocarattere_scelto = $scope.idtipocarattere_scelto;
			    	 dataToSend.provinciastabilimento_scelto = $scope.provinciastabilimento_scelto;
			    	 dataToSend.id_comune_stabilimento_scelto = $scope.id_comune_stabilimento_scelto;
			    	 dataToSend.idtoponimoindirizzostabilimento_scelto = $scope.idtoponimoindirizzostabilimento_scelto;
			    	 dataToSend.viastabilimento_scelto = $scope.viastabilimento_scelto;
			    	 dataToSend.civicostabilimento_scelto = $scope.civicostabilimento_scelto;
			    	 dataToSend.capstabilimento_scelto = $scope.capstabilimento_scelto;
			    	 dataToSend.latitudine_scelto = $scope.latitudine_scelto;
			    	 dataToSend.longitudine_scelto = $scope.longitudine_scelto;
			    	 /*LINEE */
			    	 dataToSend.paths = [];
			    	 /*per ciascun path, metto nell'array proprio l'array entries_precedenti_aggiunte di ciascuno di essi, che e' un array
			    	  * di oggetti, ciascuno corrispondente ad una linea di un dato livello, con al suo interno , tra le varie chiavi, un array
			    	  * associato alla chiave 'campi_estesi' contenente oggetti (uno per campo esteso ) del tipo {nome_campo : , html : , campo_value : }
			    	  * su campo_value e' stato bindato (con angular) il valore dell'input generato dall'html.
			    	  * Prima di allegarli pero', creo una copia dei campi estesi da inviare prendendo solo quelli che non sono undefined (cioe' quelli per cui non e' stato scelto
			    	  * un valore )
			    	  * E ovviamente devo anche saltare i paths che sono stati cancellati da maschera (trashed = true)
			    	  * NB TUTTE LE COPIE SONO FATTE IN CLONAZIONE, IN MODO TALE CHE SE L'INSERIMENTO FALLISC ENON VENGONO
			    	  * ALTERATE LE STRUTTURE DEI BEAN MAPPATI ORIGINALI
			    	  */
			    	 for(var i = 0; i< $scope.pathsAggiunte.length;i++)
			    	 {
			    		 /*salto i paths trashed (cancellati da maschera ) */
			    		 if($scope.pathsAggiunte[i].trashed)
			    		 {
			    			 continue;
			    		 }
			    		 
			    		 var entriesDaPrendere = []; /*la lista degli id delle linee per ciascun livello associate al path*/
			    		 for(var j = 0; j< $scope.pathsAggiunte[i].entries_precedenti_aggiunte.length; j++)
			    		 {
			    			 var entryOriginale = $scope.pathsAggiunte[i].entries_precedenti_aggiunte[j];
			    			 var entryToAdd = {};
			    			 entryToAdd.id = entryOriginale.id;
			    			 entryToAdd.desc = entryOriginale.desc;
			    			 entryToAdd.data_fine = entryOriginale.data_fine;
			    			 entryToAdd.data_inizio = entryOriginale.data_inizio;
			    			 //i figli non mi serve copiarli
			    			 /*per i campi estesi prendo (in copia) solo quelli che non hanno campo_value = undefined */
			    			 entryToAdd.campi_estesi = [];
			    			 for(var k = 0; k< entryOriginale.campi_estesi.length; k++)
			    			 {
			    				 if(entryOriginale.campi_estesi[k].campo_value != undefined && entryOriginale.campi_estesi[k].campo_value != '')
			    				 {
			    					 entryToAdd.campi_estesi.push({
			    						 nome_campo : entryOriginale.campi_estesi[k].nome_campo, 
//			    						 html : entryOriginale.campi_estesi[k].html, // NON MANDO L'HTML PER NON APPESANTIRE TRAFFICO TANTO NON MI SERVE MANDARLo
			    						 campo_value : entryOriginale.campi_estesi[k].campo_value
			    					 });
			    				 }
			    			 }
			    			 entriesDaPrendere.push(entryToAdd);
			    			  
			    		 }
			    		 
			    		 dataToSend.paths.push(entriesDaPrendere);
			    		  
			    	 }
			    	 
			    	 /*i dati al server sono mandati come stringone json come paramaetro di richiesta post per semplicita */
			    	 
			    	 var dataAsJSON = JSON.stringify(dataToSend);
			    	 
			    	 inviaDatiAlServerAsJSONService.inviaDatiAsJSON(
			    			 'MainAnagraficaNoScia.do?command=SalvaAnagrafica'
			    			 ,dataAsJSON
			    			 ,function(risp)
			    			 {
			    				 if(risp.statoOp == '-1')
			    				 {
			    					 alert("INSERIMENTO FALLITO");
			    				 }
			    				 else
			    				 {	
			    					 var messaggio = $scope.MSG_STATI_OPS_DBI_INSERIMENTO[+risp.statoOp];
			    					 alert(messaggio);
			    					 window.document.location.href = "MainAnagraficaNoScia.do?command=Default"
			    				 }
			    			 },
			    			 function(risp)
			    			 {
		    					 alert("INSERIMENTO FALLITO");
			    			 }
			    	 );
			    	 
			     }
			    
			     
			     
			     
			     
			     
			     
			     $scope.popolaFormMockup = function()
			     {
			    	 
			    	  
			    	 /*IMPRESA-----*/
			    	  $scope.dataarrivopec_scelto = '28/04/1988';
			    	 //$scope.id_comune_scelto = 1 ;
			    	   $scope.id_tipo_impresa_scelto  = 1 ;
			    	 $scope.ditta_scelto = 1;
			    	  $scope.piva_scelto = 12312312312+'';
			    	  $scope.cf_scelto =12312312312+'';
			    	    $scope. cf_uguale_piva = true;
			    	  $scope. pec_scelto = 'w.amante@gmail.com';
			    	  $scope.note_scelto = 'ciao come stai ?';
			    	 /*RAPP LEGALE ------*/
			    	 $scope.nomelegale_scelto = 'walter';
			    	  $scope.cognomelegale_scelto = 'amante';
			    	   $scope.idsessolegale_scelto = '1';
			    	 $scope.datanascitalegale_scelto = '28/04/2099';
			    	   $scope.nazionenascitalegale_scelto = 'bangladesh';
			    	  $scope.desccomunenascitalegale_scelto = 'NAPOLI';
			    	  $scope.cflegale_scelto = '123412321211111f';
			    	 $scope. nazioneresidenzalegale_scelto = 'italia';
			    	  $scope. provinciaresidenzalegale_scelto ='napoli';
			    	  $scope.comuneresidenzalegale_scelto = 'napoli';
			    	  $scope.idtoponimoindirizzolegale_scelto = 22;
			    	  $scope.vialegale_scelto = 'II trav s.anna';
			    	  $scope.civicolegale_scelto = '5';
			    	  $scope.caplegale_scelto = '80142';
			    	   $scope.telefonolegale_scelto = 342381231;
			    	  $scope.peclegale_scelto  = 'test@test.it';
			    	  /*dati sede legale */
			    	  $scope.sedelegale_corrisponde_stabilimento = false;
			    	  $scope.nazionesedelegale_scelto ="Italia";
			    	  $scope.provinciasedelegale_scelto = "Napoli";
			    	  $scope.comunesedelegale_scelto = "Napoli";
			    	  $scope.idtoponimosedelegale_scelto = 1;
			    	  $scope.viasedelegale_scelto = "Prova Cellole 14";
			    	  $scope.civicosedelegale_scelto = 54;
			    	  $scope.capsedelegale_scelto = 8022;
			    	  $scope.denominazionestab_scelto = "stabciccio";
			    	 /*STAB--------*/
			    	  //$scope.idtipoattivita_scelto = 1;
			    	   $scope.idtipocarattere_scelto = 2;
			    	   $scope.provinciastabilimento_scelto = 'napoli';
			    	 $scope.id_comune_stabilimento_scelto = 1;
			    	  $scope.idtoponimoindirizzostabilimento_scelto = 22;
			    	 $scope.viastabilimento_scelto = 'via test';
			    	  $scope.civicostabilimento_scelto = 5;
			    	  $scope.capstabilimento_scelto = 80110;
			    	  $scope.latitudine_scelto = 3333;
			    	 $scope.longitudine_scelto = 3333;
			    	 
			    	 $scope.pathsAggiunte[0].entries_precedenti_aggiunte.push( {id : 9, desc : 'linea prova', figli : [], campi_estesi : [], data_inizio : '28/04/1988'} );
			    	 $scope.pathsAggiunte[0].entries_precedenti_aggiunte[0].campi_estesi.push({nome_campo : 'test campo', campo_value : 'ciao'});
			    	 $scope.pathsAggiunte[0].entries_precedenti_aggiunte.push( {id : 20033, desc : 'linea prova2', figli : [], campi_estesi : [], data_inizio : '28/04/1988'} );
			    	 $scope.pathsAggiunte[0].entries_precedenti_aggiunte[1].campi_estesi.push({nome_campo : 'test campo', campo_value : 'ciao'});
			    	 $scope.pathsAggiunte[0].entries_precedenti_aggiunte.push( {id : 40090, desc : 'linea prova3', figli : [], campi_estesi : [], data_inizio : '28/04/1988'} );
			    	 $scope.pathsAggiunte[0].entries_precedenti_aggiunte[2].campi_estesi.push({nome_campo : 'test campo', campo_value : 'ciao'});
			    	 
			    	 
			    	 $scope.pathsAggiunte[0].path_esaurito = true;
			    	 $scope.flag_tutte_paths_completate = true;
			    	 $scope.flag_almeno_unalinea_completamente_vuota = false;
			    	 $scope.$apply();
			     }
			    
			     
			  
			     
			     
			     
			     
			}
		]

	);
	
	
	
	
	/*
	 * 
	 * 
	 * 
	 ***************************************************************** DIRETTIVE CUSTOM *****************************************************************
	 * 
	 * 
	 * 
	 * */

	main_module.directive('campoEsteso', function ($compile,$log) {
	    return {
	        
	    	restrict: 'E',
	    	scope : {
	    		campo : '='
	    	}
	    	//,template: '<div ng-bind-html="campo.html | astrustedhtml" ></div>' 
	        ,template : '<div>dummy</div>'
	    	,link : {   post: function(scope,elm,attrs)
				        {
				        		//$log.info($(elm).html());
				        		//$(elm).find("select").prop("disabled",true);//attr("ng-model","campo.campo_value");
				        		var html = scope.campo.html;
				        		var wrapper = $("<div></div>").html(html);
				        		
				        		/*lo vado a bindare sul modello della struttura */
				        		wrapper.find("select").attr("ng-model","campo.campo_value");
				        		/*selezione del primo valore per ognuno */
				        		 
				        		wrapper.find("input").attr("ng-model","campo.campo_value");
				        		
				        		wrapper  = angular.element(wrapper);
				        		wrapper = $compile(wrapper)(scope);
				        		elm.replaceWith(wrapper);
				        	 
				        	 
				        }
	        		}
	      };
	    });

	

	
	
	/*
	 * 
	 * 
	 * 
	 ***************************************************************** FILTRI *****************************************************************
	 * 
	 * 
	 * 
	 * */


	
	 
