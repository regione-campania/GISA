/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

/*SORGENTE DEL CONTROLLER USATO PER MODIFICARE ANAGRAFICA DALLA PAGINA DI DETTAGLIO*/
/*
 * 
 * 
 * 
 ***************************************************************** CONTROLLERS *****************************************************************
 * 
 * 
 * 
 * */



var controller_modifica_detail = angular.module("main_module").controller(
		"controller_dettaglio_modifica",
		["$scope","$http","comuniService","fetchLookupService","calcolaCFService","calcolaCoordinateService","ottieniMasterListService","ottieniCampiEstesiLineaService","$log","$compile","inviaDatiAlServerAsJSONService"
			 ,function($scope,$http,comuniService,fetchLookupService,servizioCalcoloCF, servizioCalcoloCoordinate,ottieniMasterListService ,ottieniCampiEstesiLineaService ,$log,$compile, inviaDatiAlServerAsJSONService){
					
					 
				
					$scope.modalitaModifica = false;
					
					$scope.abilitaModifica = function()
					{
						
						$scope.modalitaModifica = true;
						/*prendo i valori dai bean js che mantengono i valori originali e li copio
						 * in quelli in cui mantenere i valori dell'utente che interagisce
						 */
						/*--dati impresa*/
						$scope.ragioneSocialeImpresa = $scope.ragioneSocialeImpresa_originale;
						$scope.pIvaImpresa = $scope.pIvaImpresa_originale;
						$scope.cfImpresa = $scope.cfImpresa_originale;
						$scope.dataArrivoPecS = $scope.dataArrivoPecS_originale;
						$scope.dataInserimentoPecS = $scope.dataInserimentoPecS_originale;
						$scope.tipoImpresaSocieta = 2;//$scope.tipoImpresaSocieta_originale;
						/*--dati sede (i) legale*/
						angular.forEach($scope.indirizziSedeLegale,function(el_indirizzo,idind){
							el_indirizzo["via"]=el_indirizzo["via_originale"];
							el_indirizzo["cap"]=el_indirizzo["cap_originale"];
							el_indirizzo["civico"]=el_indirizzo["civico_originale"];
							el_indirizzo["idComune"]=el_indirizzo["idComune_originale"];
							el_indirizzo["idToponimo"]="1";//el_indirizzo["idToponimo_originale"];
						});
						/*--dati rappresentante(i) legale */
						angular.forEach($scope.rappresentantiLegali,function(el_rapprs,idrappr){ /*ognuno di questi e' un differente rappresentante stessa impresa */
							el_rapprs["nome"] = el_rapprs["nome_originale"];
							el_rapprs["cognome"] = el_rapprs["cognome_originale"];
							el_rapprs["cf"] = el_rapprs["cf_originale"];
							el_rapprs["descSex"] = el_rapprs["descSex_originale"];
							el_rapprs["descComuneNascita"] = el_rapprs["descComuneNascita_originale"];
							el_rapprs["pec"] = el_rapprs["pec_originale"];
							el_rapprs["telefono"] = el_rapprs["telefono_originale"];
								/*--indirizzi singolo rappresentante*/
							angular.forEach(el_rapprs["indirizzi"],function(el_indrappr,idind){ /*ognuno di questi e' indirizzo diverso stesso rappresentante*/
								el_indrappr["via"]=el_indrappr["via_originale"];
								el_indrappr["cap"]=el_indrappr["cap_originale"];
								el_indrappr["civico"]=el_indrappr["civico_originale"];
								el_indrappr["idComune"]=el_indrappr["idComune_originale"];
								el_indrappr["idToponimo"]=6;//el_indrappr["idToponimo_originale"];
							});
						});
						/*--dati stabilimento(i) **/
						angular.forEach($scope.stabilimenti,function(el_stab,idstab){ /*ciascuno di questi e' un differente stab della stessa impresa */
							el_stab["denominazione"] = el_stab["denominazione_originale"];
							el_stab["idStato"] = el_stab["idStatoOriginale_originale"];
								/*indirizzi singolo stabilimento */
							angular.forEach(el_stab["indirizzi"],function(el_indstab,idind){
								el_indstab["via"]=el_indstab["via_originale"];
								el_indstab["cap"]=el_indstab["cap_originale"];
								el_indstab["civico"]=el_indstab["civico_originale"];
								el_indstab["idComune"]=el_indstab["idComune_originale"];
								el_indstab["idToponimo"]=6;//el_indstab["idToponimo_originale"];
								
							});
						});
						
						
					 
//						setTimeout(function(){
//							
//							$scope.$apply();
//							
//						},3000);
						
						
					}
					
					$scope.salvaModifiche = function()
					{
						var risp = confirm("Sicuro di voler salvare le modifiche ?");
						 
						if(risp) //l'utente sceglie di salvare
						{
							/*todo salva e solo all'arrivo risposta riswitcha stato e ricarica */
							
							var dataToSend = {};
							dataToSend.idImpresa = $scope.idImpresa;
							dataToSend.ragioneSocialeImpresa = $scope.ragioneSocialeImpresa;
							dataToSend.pIvaImpresa = $scope.pIvaImpresa;
							dataToSend.cfImpresa = $scope.cfImpresa;
							dataToSend.dataArrivoPecS = $scope.dataArrivoPecS;
							dataToSend.dataInserimentoPecS = $scope.dataInserimentoPecS;
							dataToSend.tipoImpresaSocieta = $scope.tipoImpresaSocieta;
							dataToSend.indirizziSedeLegale = $scope.indirizziSedeLegale;
							dataToSend.rappresentantiLegali = $scope.rappresentantiLegali;
							dataToSend.stabilimenti = $scope.stabilimenti;
							var dataToSendJ = JSON.stringify(dataToSend);
							
							inviaDatiAlServerAsJSONService.inviaDatiAsJSON(
									"MainAnagraficaNoScia.do?command=ModifyAnagrafica"
									,dataToSendJ
									,function(risp){
										
										if(risp.statoOp == '0')
										{
											alert("Operazione completata con successo")
										}
										else
										{
											alert("Qualcosa e' andato storto");
										}
										/*e ricarico la pagina*/
										document.location.reload(true);
										/*e ricambio modalita'*/
										$scope.modalitaModifica = false;
										
									}
									,function(risp){
										alert("Qualcosa e' andato storto");
										/*e ricarico la pagina*/
										document.location.reload(true);
										/*e ricambio modalita'*/
										$scope.modalitaModifica = false;
									}
							);
							
						}
						else //sceglie di non salvare
						{
							$scope.modalitaModifica = false;
						}
						
					}
			
					
					 $scope.toponimi = null;
				     fetchLookupService.getValuesFromLookup("lookup_toponimi","code","description",
				    		 function(data) /*callback se successo */
				    		 {
					    	 		$scope.toponimi = data.entries;
					    	 		 
				    		 }
					     	 ,function() /*callback se errore */
					     	 {
					     		alert("errore");
					     	 }
					 );
					
				     
				     $scope.comuni = null;
					 
					 
					 
				     /*ottengo lista lookup stati */
				     $scope.statiStab = null;
				     fetchLookupService.getValuesFromLookup(
				    		 				"lookup_stati"
				    		 				,"code"
				    		 				,"description"
				    		 				,function(data)
				    		 				{
				    		 					$scope.statiStab = data.entries;
				    		 				}
				    		 				,function()
				    		 				{
				    		 					alert("errore");
				    		 				}
				    		 
				     );
				     
				     
					 
					 /*ottengo comuni campani */
					 comuniService.getComuniCampani(
							 function(data) /*callback se successo */
							 {
								 /*serve per iterare piu' semplicemente con ng-repeat...*/
								 $scope.comuniCampani = data.comuni;
	 
							 }
							 , 
							 function() /* se errore */
							 {
								 alert("errore");
							 }
					 );
					 
					 
					 $scope.tipi_imprese = null;
				     fetchLookupService.getValuesFromLookup("LOOKUP_TIPO_IMPRESA_SOC","code","description",
				    		 function(data) /*callback se successo */
				    		 {
				    	 		$scope.tipi_imprese = data.entries;
				    	 		
				    		 }
				     		,function() /*callback se errore */
				     		{
				     			alert("errore");
				     		}
				     );
			
			
			
			
			  }
		]


);