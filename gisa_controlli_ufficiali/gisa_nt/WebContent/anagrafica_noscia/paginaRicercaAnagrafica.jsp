<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<!-- stili per i campi non validi etc... -->
<link rel="stylesheet" href="anagrafica_noscia/css/stili_nuova_anag.css" />

<!-- INCLUDO ANGULAR -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>


<!-- CREO MODULO PRINCIPALE ANGULAR -->
<script>
	/*creo il module principale (l'app) */
	 angular.module("main_module", []);  
</script>

<!-- INCLUDO ALCUNE ROUTINE DI UTILITY --> 
<!--E SOLO DOPO AVER CREATO IL MODULO PRINCIPALE, INCLUDO I SERVIZI E I CONTROLLER ASSOCIATIGLI -->
<script src="anagrafica_noscia/servizi/servizi.js"></script>
<script src="anagrafica_noscia/controllers/root_controller.js"></script>
<script src="anagrafica_noscia/controllers/controller_ricercaanag.js"></script>
 

	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ricerca anagrafica</title>
</head>
<body>

	
	 
	
	
	<div ng-app="main_module" ng-controller="root_controller" ng-cloak>
		<center>
		<div ng-controller="controller_ricerca_anagrafica" style="max-width:1300px;">
			
			
		 
			<dhv:container name="anagnoscia_ricercaanagrafica_container" selected="Ricerca Anagrafica" object=""  param="" >
				 <div>
				 <b>Ricerca per:</b><br>
				 	<center>
					 <input type="checkbox" ng-model="ricercaper_datiimpresa" ng-init="ricercaper_datiimpresa = true" />&nbsp; Dati Impresa 
					 <input type="checkbox" ng-model="ricercaper_datilegale"  />&nbsp; Dati Legale Rappresentante 
					 <input type="checkbox" ng-model="ricercaper_datistab"   /> &nbsp; Dati Stabilimento  
					 </center>
				 </div>
				 <br><br>				 
				 <div>				 
					 <fieldset ng-show="ricercaper_datiimpresa == true">
					 	<legend><b>Dati Impresa</b></legend>
					 	<table class="details" width="100%">
					 		<tr>
						 		<th style="width:200px;" align="left">Ragione Sociale</th>
						 		<td align="left" class="td_destro">
						 			<input type="text" ng-model="ragione_sociale" placeholder="Ragione Sociale" />
						 		</td>
					 		</tr>
					 		<tr>
					 			<th style="width:200px;" align="left">Partita Iva</th>
					 			<td align="left" class="td_destro">
					 				<input type="text" ng-model="partita_iva" placeholder="Partita IVA" />
					 			</td>
					 		</tr>
					 		<tr>
					 			<th style="width:200px;" align="left">Codice Fiscale</th>
					 			<td align="left" class="td_destro">
					 				<input type="text" ng-model="codice_fiscale" placeholder="Codice Fiscale" />
					 			</td>
					 		</tr>
					 		<tr>
					 		<th style="width:200px;" align="left">Comune</th>
					 		<td align="left" class="td_destro">
					 			<input type="text" ng-model="comune_impresa" placeholder="Comune" />
					 		</td>
					 		</tr>
					 	</table>
					 </fieldset>	
					  
					 <fieldset ng-show="ricercaper_datilegale == true">
					 	<legend><b>Dati Legale Rappresentante</b></legend>
					 	<table class="details" width="100%">
					 		<tr>
						 		<th style="width:200px;" align="left">Nome</th>
						 		<td align="left" class="td_destro">
						 			<input type="text" ng-model="nome_rappresentante" placeholder="Nome" />
						 		</td>
					 		</tr>
					 		<tr>
					 			<th style="width:200px;" align="left">Cognome</th>
					 			<td align="left" class="td_destro">
					 				<input type="text" ng-model="cognome_rappresentante" placeholder="Cognome" />
					 			</td>
					 		</tr>
					 		<tr>
					 			<th style="width:200px;" align="left">Codice Fiscale</th>
					 			<td align="left" class="td_destro">
					 				<input type="text" ng-model="codice_fiscale_rappresentante" placeholder="Rappresentante" />
					 			</td>
					 		</tr>
					 	</table>
					 	
					 </fieldset>	 
					 <fieldset ng-show="ricercaper_datistab == true">
					 	<legend><b>Dati Sede</b></legend>
					 	<table class="details" width="100%">
					 		<tr>
						 		<th style="width:200px;" align="left">Tipo Attivita'</th>
						 		<td align="left" class="td_destro">
						 			<select name="idtipoattivita_scelto" ng-model="idtipoattivita_scelto" >
						 				<option ng-repeat="tipoattivita in tipiattivita" value="{{tipoattivita.code}}">{{tipoattivita.description}}</option>
						 			</select>
						 		</td>
					 		</tr>
					 		 
					 		<tr>
					 			<th style="width:200px;" align="left">Comune</th>
					 			<td align="left" class="td_destro">
					 				<input type="text" ng-model="comune_stab" placeholder="Comune" />
					 			</td>
					 		</tr>
					 	</table>
					 </fieldset>	 
					 <!-- se tutti sono disattivati -->
					 <fieldset ng-show="(ricercaper_datistab == false && ricercaper_datiimpresa == false && ricercaper_datilegale == false)">
					 	 <center><b>Cerca tutti NO-SCIA</b></center> 
					 </fieldset>	
					 
				 	 <div align="center">
				 	 	<input type="button" ng-click="searchFunction()" value="search"/>
				 	 </div>
				 </div>
			</dhv:container>
	 
			
		</div>
		</center>
	</div>
	
	<br>
	<br>
	
	
	
	
	 
	
	
	
	
</body>
</html>