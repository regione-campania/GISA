/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

/*SORGENTE DEL CONTROLLER USATO PER GESTIRE IL FORM DI RICERCA ANAGRAFICA */
/*
 * 
 * 
 * 
 ***************************************************************** CONTROLLERS *****************************************************************
 * 
 * 
 * 
 * */

var controller_nuova_anagrafica = angular.module("main_module").controller(
		"controller_ricerca_anagrafica",
		["$scope","$http","comuniService","fetchLookupService","calcolaCFService","calcolaCoordinateService","ottieniMasterListService","ottieniCampiEstesiLineaService","$log","$compile","inviaDatiAlServerAsJSONService"
		 ,function($scope,$http,comuniService,fetchLookupService,servizioCalcoloCF, servizioCalcoloCoordinate,ottieniMasterListService ,ottieniCampiEstesiLineaService ,$log,$compile, inviaDatiAlServerAsJSONService){
	
			
			/*init delle variabili di controllo tipo di ricerca */
			$scope.ricercaper_datistab = false;
			$scope.ricercaper_datilegale = false;
	
			$scope.tipiattivita = null;
		    fetchLookupService.getValuesFromLookup("LOOKUP_TIPO_ATTIVITA","code","description",
		    		 function(data) /*callback se successo */
		    		 {
			    	 		$scope.tipiattivita = data.entries;
			    	 		$scope.idtipoattivita_scelto = -1;
			    	  }
			     	 ,function() /*callback se errore */
			     	 {
			     		alert("errore");
			     	 }
			 );
		    
		    $scope.searchFunction = function()
		    {
		    	
		    	/*riempio con i dati dal form di ricerca */
		    	var datiAsJSON = {};
		    	/*parte per i dati impresa*/
		    	datiAsJSON.ricercaper_datiimpresa = $scope.ricercaper_datiimpresa;
		    	datiAsJSON.ragione_sociale = $scope.ragione_sociale;
		    	datiAsJSON.partita_iva = $scope.partita_iva;
		    	datiAsJSON.codice_fiscale = $scope.codice_fiscale;
		    	datiAsJSON.comune_impresa = $scope.comune_impresa;
		    	
		    	
		    	/*parte per dati legale*/
		    	datiAsJSON.ricercaper_datilegale = $scope.ricercaper_datilegale;
		    	datiAsJSON.nome_rappresentante = $scope.nome_rappresentante;
		    	datiAsJSON.cognome_rappresentante = $scope.cognome_rappresentante;
		    	datiAsJSON.codice_fiscale_rappresentante = $scope.codice_fiscale_rappresentante;
		    	
		    	
		    	/*parte per dati stabilimento*/
		    	datiAsJSON.ricercaper_datistab = $scope.ricercaper_datistab;
		    	datiAsJSON.idtipoattivita_scelto = $scope.idtipoattivita_scelto;
		    	datiAsJSON.comune_stab = $scope.comune_stab; 
		    	
		    	
		    	/* 
		    	 * non vado in richiesta asincrona ma dirigo proprio la pagina creando un form apposito di cui faccio submit
		    	 */
		    	
		    	/*  --> vecchia versione asincrona
		    	inviaDatiAlServerAsJSONService.inviaDatiAsJSON(
		    			'MainAnagraficaNoScia.do?command=CercaAnagrafica'
		    			,JSON.stringify(datiAsJSON)
		    			,function(risp){
		    				if(risp.statoOp == '0')
		    				{
		    					window.s = risp;
		    				}
		    				else
		    				{
		    					alert("RICERCA FALLITA");
		    				}
		    			}  
		    			,function(risp){alert("RICERCA FALLITA");}  
		    	);
				*/
		    	
		    	/* --> versione sincrona */
		    	var form0 = document.createElement("form");
		    	form0.action = "MainAnagraficaNoScia.do?command=CercaAnagrafica";
		    	form0.method = "POST";
		    	
		    	/*creo input type dove metto lo stringone json, e lo metto in append al form */
		    	var mappazzoneJSON = document.createElement("input");
		    	mappazzoneJSON.type ="hidden";
		    	mappazzoneJSON.name="dataAsJSON";
		    	mappazzoneJSON.value = JSON.stringify(datiAsJSON);
		    	form0.appendChild(mappazzoneJSON);
		    	document.body.appendChild(form0);
		    	
		    	form0.submit();
		    	
		    }
			
	
	
	
	
	
	
		}]);

