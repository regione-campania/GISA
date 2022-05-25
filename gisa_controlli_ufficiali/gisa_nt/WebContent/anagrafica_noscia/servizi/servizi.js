/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 * 
 * 
 * 
 ***************************************************************** SERVIZI *****************************************************************
 * 
 * 
 * 
 * */

	var main_module = angular.module("main_module"); /*ottengo il modulo */
	/*servizio comuni */
	main_module.service
	(
			"comuniService", /*nome servizio */
			function($http)
			{
			
				this.getComuniCampani = function(callbackSuccess, callbackError) /*la callback successo deve prendere in input data */
				{
					$http({
						
						method : 'POST'
						,params : {soloCampani : 'true'}
						,url : 'ServiziCentralizzabili.do?command=GetComuni'
						
					}).success(callbackSuccess).error(callbackError);
				}
				
				this.getComuniItaliani = function(callbackSuccess, callbackError)
				{
					$http({
						
						method : 'POST'
						,url : 'ServiziCentralizzabili.do?command=GetComuni'
						
					}).success(callbackSuccess).error(callbackError);
				}
				
			}
	);
	
	
	/*servizio lookup generiche*/
	main_module.service
	(
		"fetchLookupService",
		function($http)
		{
			this.getValuesFromLookup = function(nomeLookup,nomeCampoCode, nomeCampoDescription,callbackSuccess,callbackError)
			{
				$http(
				{
					method: 'POST'
					,params : {nomeLookup : nomeLookup, nomeCampoCode : nomeCampoCode, nomeCampoDescription : nomeCampoDescription}
					,url : 'ServiziCentralizzabili.do?command=GetValuesFromLookup'
				}		
				).success(callbackSuccess).error(callbackError);
			}
		}
	);
	
	
	
	/*servizio per il calcolo di un comune client side
	 * in data ci sara' cf
	 *  */
	main_module.service
	(
		"calcolaCFService"
		,function($http)
		{
			this.calcolaCF = function(sex,nome,cognome,desccomune,data_nascita,callbackSuccess, callbackError)
			{
				if(nome.trim().length == 0 || cognome.trim().length == 0  || data_nascita.trim().length == 0 || desccomune == '')
					return '';
				

				desccomune = 'Napoli';
				nome =nome.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
				cognome = cognome.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
				
				$http(
						{
							method : 'POST'
							,params : {
							  nome : nome,
							  cognome : cognome,
							  sex : sex,
							  desccomune : desccomune,
							  data_nascita : data_nascita 
							}
							,url : 'ServiziCentralizzabili.do?command=CalcolaCf'
						}).success(callbackSuccess
						  ).error(callbackError);
				 
				
			}
		}
	);
	
	
	
	/*in data ci sarà esito geo, lng,lat */
	main_module.service(
		"calcolaCoordinateService"
		,function($http)
		{
			this.calcolaCoordinate = function(indirizzo,desccomune,descprovincia,callbackSuccess,callbackError)
			{
				if(indirizzo.trim().length == 0 || desccomune.trim().length== 0 || descprovincia.trim().length == 0)
				{
					return;
				}
				
				$http(
						{
							method : 'POST'
							,url : 'ServiziCentralizzabili.do?command=CalcolaCoordinate'
							,params : {indirizzo : indirizzo, desccomune : desccomune, descprovincia : descprovincia}
						}
				).success(callbackSuccess).error(callbackError);
			}
		}
	);
	
	
	
	
	/*servizio per l'ottenimento della struttura della master list*/
	main_module.service(
			"ottieniMasterListService"
			,function($http)
			{
				
				this.scaricaMasterListFiltrata = function(descflussoorigine,callbacksuccess,callbackerror)
				{
					$http
					({
							method: 'POST',
							url : 'ServiziCentralizzabili.do?command=OttieniMasterList',
							params : { descflussoorigine : descflussoorigine}
					}
					).success(callbacksuccess).error(callbackerror);
				}
				this.scaricaMasterListTutta = function(callbacksuccess,callbackerror)
				{
					$http
					({
							method: 'POST',
							url : 'ServiziCentralizzabili.do?command=OttieniMasterList',
							 
					}
					).success(callbacksuccess).error(callbackerror);
				}
				
			}
	
	);
	
	
	/*servizio per ottenimento campi estesi */
	main_module.service(
			"ottieniCampiEstesiLineaService"
			,function($http)
			{
				/*se si passa anche id rel si vogliono anche i valori eventualmente associati ai campi */
				this.getCampiEstesiLinea = function(idLineaML,idRel,callbacksuccess,callbackerror)
				{
					$http({
						method : 'POST',
						url : 'ServiziCentralizzabili.do?command=OttieniCampiEstesi',
						params : { idLineaML : idLineaML , idRel : idRel }
						
					}).success(callbacksuccess).error(callbackerror);
					
					
				}
				
			}
	);
	
	
	
	/*questo servizio e' reso ancora piu 'generico, in quanto prende in input url della action, in quanto non necessariamente
	 * viene usato per l'anagrafica no scia 
	 * 
	 * 
	 * */
	main_module.service(
			"inviaDatiAlServerAsJSONService"
			,function($http)
			{
				this.inviaDatiAsJSON = function(urlDest,datiAsJSON,callbacksuccess,callbackerror){
					
					$http({
						method : 'POST'
						,url : urlDest
						,params : {dataAsJSON : datiAsJSON}
					}).success(callbacksuccess).error(callbackerror);
					
					
				};
			}
			
	);
	
	
	
 
