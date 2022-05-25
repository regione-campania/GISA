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

<!-- STILI CSS PER JQUERY UI (USATI PER IL DATEPICKER) -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">

<!-- stili per i campi non validi etc... -->
<link rel="stylesheet" href="anagrafica_noscia/css/stili_nuova_anag.css" />

<!-- INCLUDO ANGULAR E JQUERY-->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="anagrafica_noscia/utils/datepicker-it.js"></script> <!-- per rendere italiano il widget del datepicker -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-sanitize.min.js"></script> -->


<!-- CREO MODULO PRINCIPALE ANGULAR -->
<script>
	/*creo il module principale (l'app) */
	angular.module("main_module", []); 
</script>

<!-- INCLUDO ALCUNE ROUTINE DI UTILITY --> 
<!--E SOLO DOPO AVER CREATO IL MODULO PRINCIPALE, INCLUDO I SERVIZI E I CONTROLLER ASSOCIATIGLI -->
<script src="anagrafica_noscia/servizi/servizi.js"></script>
<script src="anagrafica_noscia/controllers/root_controller.js"></script>
<script src="anagrafica_noscia/controllers/controller_nuovaanag.js"></script>
 


<!-- CREO I WIDGETS PER I DATE PICKERS (SOLO QUANDO TUTTI I DOMS SONO STATI GENERATI )-->
<script>
$(function(){
	$("#dataarrivopec").datepicker();	
	$("#datanascitalegale").datepicker();
		
	
});
  
</script>

	
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inserisci nuova anagrafica</title>
</head>
<body>

	
	 
	
	
	<div ng-app="main_module" ng-controller="root_controller" ng-cloak>
		<center>
		<div ng-controller="controller_nuova_anagrafica" style="max-width:1300px;">
			<form name="form_nuova_anagrafica" novalidate> <!-- novalidate leva validazione html5 -->
				
				<dhv:container name="anagnoscia_nuovanagrafica_container" selected="Nuova Anagrafica" object=""  param="" >
				 
				 
				 <!-- DATI IMPRESA------------------------------------------------------------------------------------------------ -->
				 
				 <table class="details" width="100%">
				 	<tr><th colspan="2" ><div class="div_intestazione" align="center">Dati Impresa <b>*</b><br> <font color="red" size="1">* Se la ragione sociale, il codice fiscale e la piva dell'impresa gia' esistono nel sistema, verrà riutilizzata l'impresa esistente <br>(con la sede legale ed il legale rappresentante gia' associatigli)</font></div></th></tr>
				 	<tr>
				 		<th style="width:200px;" align="left">Data Arrivo Pec</th>
				 		<td class="td_destro" align="left">
				 			
				 		 <font color="red">&nbsp;*</font>
				 			<!-- su questo creo un widget usando JqueryUI (non angular) e con angular faccio binding del valore , e lo si puo' editare solo col date picker  -->
				 			<input type="text" name="dataarrivopec_scelto" id="dataarrivopec" ng-model="dataarrivopec_scelto" ng-required="true" readonly placeholder="Data arrivo richiesta" />  
				 			<!-- messaggi di errore   ---------------- -->
					 		<span class="span_errore" ng-show="form_nuova_anagrafica.dataarrivopec_scelto.$touched && !form_nuova_anagrafica.dataarrivopec_scelto.$valid  " 
					 			align="center">
					 			&nbsp;{{msg_errore_data_mancante}}
					 		</span>
					 		<!-- fine messaggi errore ---------------- -->
				 		 </td>
				 	</tr>
				 	
				 	<!--
				 	<tr> per ora levo questo comune poiche' anche se i noscia non sono configurati come tipo attivita fissa,
				 		vengono trattate come tali, e per le fisse il comune all'inizio dell'inserimento pratica suap
				 		va a settarsi sul comune dello stabilimento, quindi a questo punto seleziono direttamente comune stab
				 		e se invece le trattassi come mobili, tale comune andrebbe sul comune sede legale, e allo stesso modo tanto vale 
				 		settarlo li per un fatto di pulizia 
				 		<th style="width:200px;" align="left">Comune</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;*</font>
							<select name="id_comune_scelto" ng-model="id_comune_scelto" >
								<!-- visto che non si puo' fare filter su mappa, in angular, allora uso ng-if  
								<option ng-repeat="(nomecom,comune)  in prendiSoloCampani(comuni)" value="{{comune.id}}" >{{comune.nome}}</option>
							</select>
						</td>
				 	</tr>
				 	-->
				 	<tr>
				 		<th style="width:200px;" align="left">Tipo Impresa</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;*</font>
				 			<select name="id_tipo_impresa_scelto" ng-model="id_tipo_impresa_scelto" >
				 				<option ng-repeat="tipoimpresa in tipi_imprese" value="{{tipoimpresa.code}}">{{tipoimpresa.description}}</option>
				 			</select>
				 		</td>
				 	</tr>
				 	<tr>
				 		<th style="width:200px;" align="left">Ditta</th>
				 		<td align="left" class="td_destro">
				 		<font color="red">&nbsp;*</font>
						<input type="text" name="ditta_scelto" ng-model="ditta_scelto" placeholder="ragione sociale" ng-required="true" maxlength="50" style="width:300px;"/>
						<!-- messaggi di errore   ------------------>
				 		<span class="span_errore" ng-show="form_nuova_anagrafica.ditta_scelto.$touched && !form_nuova_anagrafica.ditta_scelto.$valid  " 
				 			align="center">
				 			&nbsp;{{msg_errore_ragionesociale}}
				 		</span>
				 		<!-- fine messaggi errore ------------------>
						</td>
				 	</tr>
				 	<tr>
				 		<th style="width:200px;" align="left">Partita Iva</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;*</font>
				 			<input type ="text" name="piva_scelto" ng-model="piva_scelto" placeholder="partita iva" ng-required="true" ng-pattern="pattern_piva" style="width:300px;"/>
				 			<!-- messaggi di errore   ------------------>
				 			<span class="span_errore" ng-show="form_nuova_anagrafica.piva_scelto.$touched && !form_nuova_anagrafica.piva_scelto.$valid"
				 				align="center">
				 				&nbsp;{{msg_errore_piva}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 		</td>
				 	</tr>
				 	<tr>
				 		<th style="width:200px;" align="left">Codice Fiscale</th>
				 		<td align="left" class="td_destro" ng-init="cf_uguale_piva = false"  >
				 			  <font color="red">&nbsp;*</font>
				 			  <input ng-show="!cf_uguale_piva"  type ="text" name="cf_scelto" ng-model="cf_scelto"  placeholder="codice fiscale" ng-pattern="pattern_cf_o_iva" ng-required="true" style="width:300px;"/> 
				 			  <input ng-show="cf_uguale_piva" readonly type ="text" name="cf_scelto" value="{{piva_scelto}}" placeholder="codice fiscale" style="width:300px;"/> 
				 			<br> 
				 			 
				 			<!-- metto un checkbox, attivabile solo se la piva e' valida, che quando premuto mette il valore della partita iva scelta sul codice fiscale 
				 			quando ripremuto, svuota il cf-->
				 			 &nbsp; &nbsp; <input type="checkbox" ng-model="cf_uguale_piva" ng-click="(cf_uguale_piva && (cf_scelto = piva_scelto)) || (cf_scelto = '')"  ng-disabled="!form_nuova_anagrafica.piva_scelto.$valid" />
				 			   <span   ng-class="{schiarito : !form_nuova_anagrafica.piva_scelto.$valid}">Uguale alla Partita Iva</span>  
				 			
				 			<!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="form_nuova_anagrafica.cf_scelto.$touched && !form_nuova_anagrafica.cf_scelto.$valid" 
				 				align="center">
				 				&nbsp;{{msg_errore_cf_o_iva}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 		</td>
				 	</tr>
				 	<tr>
				 		<th style="width:200px;" align="left">Pec</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" name="pec_scelto" ng-model="pec_scelto" placeholder="posta elettronica certificata" style="width:300px;" ng-pattern="pattern_mail"/>
				 			<!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="form_nuova_anagrafica.pec_scelto.$touched && !form_nuova_anagrafica.pec_scelto.$valid" 
				 				align="center">
				 				&nbsp;{{msg_errore_mail}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 		</td>
				 	</tr>
				 	 
				 	 <tr>
				 	 	<th>Note</th>
				 	 	<td align="left" class="td_destro">
				 	 		<font color="red">&nbsp;&nbsp;</font>
				 	 		<textarea rows="5" cols="50" ng-model="note_scelto" placeholder="NOTE AGGIUNTIVE" >
				 	 		
				 	 		</textarea>
				 	 	</td>
				 	 </tr>
				 	
				 </table>
				 
				  <!--FINE  DATI IMPRESA------------------------------------------------------------------------------------------------ -->
				 
				 <br><br>
				 
				 <!-- DATI SEDE LEGALE IMPRESA ---------------------------------------------------------------->
				 <table class="details" width="100%">
				 	<tr><th colspan="2" ><div class="div_intestazione" align="center">Dati Sede Legale <b>*</b><br> <font color="red" size="1">* Nel caso di utilizzo di impresa esistente, verra' utilizzata la sede legale gia' associata</font></div></th></tr>
				 	<tr>
				 		<td colspan="2" align="left">
				 			<input type="checkbox" ng-model="sedelegale_corrisponde_stabilimento" /> &nbsp;<b>La sede legale corrisponde con i dati dello stabilimento</b>
				 		</td>
				 	</tr>
					 	 <tr  ng-show="!sedelegale_corrisponde_stabilimento">
					 		<th style="width:200px;" align="left">Nazione</th>
					 		<td align="left" class="td_destro">
					 			 <font color="red">&nbsp;&nbsp;</font>
					 			<input type ="text" name="nazionesedelegale_scelto" ng-model="nazionesedelegale_scelto" placeholder="nazione sede legale"  style="width:300px;"/>
					 		</td>
						 </tr>
						  <tr  ng-show="!sedelegale_corrisponde_stabilimento">
						 		<th style="width:200px;" align="left">Provincia</th>
						 		<td align="left" class="td_destro">
						 			<font color="red">&nbsp;&nbsp;</font>
						 			<input type ="text" name="provinciasedelegale_scelto" ng-model="provinciasedelegale_scelto" placeholder="provincia sede legale" style="width:300px;"/>
						 		</td>
						 </tr>
						 <tr  ng-show="!sedelegale_corrisponde_stabilimento">
						 		<th style="width:200px;" align="left">Comune</th>
						 		<td align="left" class="td_destro">
						 			<font color="red">&nbsp;&nbsp;</font>
						 			<input type ="text" name="comunesedelegale_scelto" ng-model="comunesedelegale_scelto" placeholder="comune sede legale"/>
						 		</td>
						 </tr>
						  <tr  ng-show="!sedelegale_corrisponde_stabilimento">
						 		<th style="width:200px;" align="left">Indirizzo</th>
						 		<td align="left" class="td_destro">
						 			<font color="red">&nbsp;&nbsp;</font>
						 			<select name="idtoponimosedelegalelegale_scelto" ng-model="idtoponimosedelegale_scelto">
						 				<option ng-repeat="toponimo in toponimi" value="{{toponimo.code}}" >{{toponimo.description}}</option>
						 			</select>
						 			&nbsp;
						 			<input name="viasedelegale_scelto" ng-model="viasedelegale_scelto" type="text" placeholder="indirizzo" style="width:300px;"/>
						 			&nbsp;
						 			<!-- cap e numero civico vengono visualizzati solo se viene inserito indirizzo -->
						 			<input type="text" name="civicosedelegale_scelto" ng-model="civicosedelegale_scelto" placeholder="n.civico" ng-show="viasedelegale_scelto.trim().length > 0"/>
						 			&nbsp;
						 			<input type="text" name="capsedelegale_scelto" ng-model="capsedelegale_scelto" placeholder="cap" ng-show="viasedelegale_scelto.trim().length > 0"/>
						 		</td>
						 </tr> 
				</table>	 
				 
				 
				 
				 
				 
				  <br><br>
				  <!-- DATI LEGALE RAPP ------------------------------------------------------------------------------------------------ -->
				  <table class="details" width="100%">
				  
				  	<tr><th colspan="2" ><div class="div_intestazione" align="center">Dati Legale Rappresentante<b>*</b><br> <font color="red" size="1">* Nel caso di utilizzo di impresa esistente, verra' utilizzato il soggetto fisico gia' associato. <br>Viceversa se il Codice Fiscale gia' esiste a sistema, verra' riutilizzato lo stesso soggetto fisico, associandolo all'impresa (se non gia' associato)</font></div></th></tr>
				   
				  
				  <tr>
				 		<th style="width:200px;" align="left">Nome</th>
				 		<td align="left" class="td_destro">
				 			 <font color="red">&nbsp;*</font>
				 			<input type ="text" name="nomelegale_scelto" ng-model="nomelegale_scelto" placeholder="nome" ng-required="true" style="width:300px;" 
				 			 ng-change="valutaSePossoGenerareCf()"
				 			 />
				 			<!-- messaggio di errore se non editato e non valido -->
				 			<span class="span_errore" ng-show="!form_nuova_anagrafica.nomelegale_scelto.$valid && form_nuova_anagrafica.nomelegale_scelto.$touched" 
				 				align="center">
				 				&nbsp;{{msg_errore_campo_mancante_generico}}
				 			</span>
				 		</td>
				 		
				 </tr>
				 <tr>
				 		<th style="width:200px;" align="left">Cognome</th>
				 		<td align="left" class="td_destro">
				 			 <font color="red">&nbsp;*</font>
				 			<input type ="text" name="cognomelegale_scelto" ng-model="cognomelegale_scelto" placeholder="cognome" ng-required="true" style="width:300px;"
				 			 ng-change="valutaSePossoGenerareCf()"
				 			/>
				 			<!-- messaggio di errore se non editato e non valido -->
				 			<span class="span_errore" ng-show="!form_nuova_anagrafica.cognomelegale_scelto.$valid && form_nuova_anagrafica.cognomelegale_scelto.$touched" 
				 				align="center">
				 				&nbsp;{{msg_errore_campo_mancante_generico}}
				 			</span>
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Sesso</th>
				 		<td align="left" class="td_destro">
				 			 <font color="red">&nbsp;&nbsp;</font>
				 			 <select ng-model="idsessolegale_scelto" name="idsessolegale_scelto"
				 			  >
				 			 	<option value="0">M</option>
				 			 	<option value="1">F</option>
				 			 </select>
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Data Nascita</th>
				 		<td align="left" class="td_destro">
				 			<!-- anche per questo creo un widget jquery ui per farlo valorizzare e il modello si binda contro il modelo con angular -->
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" id="datanascitalegale" name="datanascitalegale_scelto" ng-model="datanascitalegale_scelto" ng-pattern="pattern_data" 
				 			 ng-change="valutaSePossoGenerareCf()"  
				 			  />
				 			 <!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="form_nuova_anagrafica.datanascitalegale_scelto.$touched && !form_nuova_anagrafica.datanascitalegale_scelto.$valid" 
				 				align="center">
				 				&nbsp;&nbsp;{{msg_errore_formato_data}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 			 
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Nazione Nascita</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" name="nazionenascitalegale_scelto" ng-model="nazionenascitalegale_scelto" placeholder="nazione nascita" style="width:300px;"/>
				 		</td>
				 </tr>
				 <tr>
				 		<th style="width:200px;" align="left">Comune Nascita</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
							
							 
							 <input type="text" ng-change="valutaSePossoGenerareCf()" name="desccomunenascitalegale_scelto" ng-model="desccomunenascitalegale_scelto" placeholder="comune di nascita" style="width:300px;"/>
							
						</td>
				 	</tr>
				  <tr>
				 		<th style="width:200px;" align="left">Codice Fiscale</th>
				 		<td align="left" class="td_destro">
				 			 <font color="red">&nbsp;*</font>
				 			<input type ="text" name="cflegale_scelto" ng-model="cflegale_scelto" placeholder="codice fiscale" style="width:300px;" ng-required="true" ng-pattern="pattern_cf"
				 			
				 			/>
				 			<input type="button"  ng-click="generaCfLegale()" 
					 			ng-show="flag_generacf_attivabile"
					 			value="GENERA CF"
					 		/>
				 			 
				 			
				 			<!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="(form_nuova_anagrafica.cflegale_scelto.$touched) && !form_nuova_anagrafica.cflegale_scelto.$valid" 
				 				align="center">
				 				&nbsp;{{msg_errore_cf}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Nazione Residenza</th>
				 		<td align="left" class="td_destro">
				 			 <font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" name="nazioneresidenzalegale_scelto" ng-model="nazioneresidenzalegale_scelto" placeholder="nazione residenza legale"  style="width:300px;"/>
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Provincia Residenza</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" name="provinciaresidenzalegale_scelto" ng-model="provinciaresidenzalegale_scelto" placeholder="provincia residenza legale" style="width:300px;"/>
				 		</td>
				 </tr>
				 <tr>
				 		<th style="width:200px;" align="left">Comune Residenza</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" name="comuneresidenzalegale_scelto" ng-model="comuneresidenzalegale_scelto" placeholder="comune residenza legale"/>
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Indirizzo Residenza</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<select name="idtoponimoindirizzolegale_scelto" ng-model="idtoponimoindirizzolegale_scelto">
				 				<option ng-repeat="toponimo in toponimi" value="{{toponimo.code}}" >{{toponimo.description}}</option>
				 			</select>
				 			&nbsp;
				 			<input name="vialegale_scelto" ng-model="vialegale_scelto" type="text" placeholder="indirizzo" style="width:300px;"/>
				 			&nbsp;
				 			<!-- cap e numero civico vengono visualizzati solo se viene inserito indirizzo -->
				 			<input type="text" name="civicolegale_scelto" ng-model="civicolegale_scelto" placeholder="n.civico" ng-show="vialegale_scelto.trim().length > 0"/>
				 			&nbsp;
				 			<input type="text" name="caplegale_scelto" ng-model="caplegale_scelto" placeholder="cap" ng-show="vialegale_scelto.trim().length > 0"/>
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Telefono</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" name="telefonolegale_scelto" ng-model="telefonolegale_scelto" placeholder="contatto telefonico" ng-pattern="pattern_tel"/>
				 			<span class="span_errore" ng-show="form_nuova_anagrafica.telefonolegale_scelto.$touched && !form_nuova_anagrafica.telefonolegale_scelto.$valid"
				 			 	align="center" >
				 				&nbsp; {{msg_errore_telefono}}
				 			</span>
				 		</td>
				 </tr>
				 <tr>
				 		<th style="width:200px;" align="left">Pec</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
				 			<input type ="text" name="peclegale_scelto" ng-model="peclegale_scelto" placeholder="contatto di posta pec" style="width:300px;" ng-pattern="pattern_mail"/>
				 			<!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="form_nuova_anagrafica.peclegale_scelto.$touched && !form_nuova_anagrafica.peclegale_scelto.$valid" 
				 				align="center">
				 				&nbsp;{{msg_errore_mail}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 		</td>
				 </tr>
				 </table>
				 
				 
					 
				  
				 <!-- FINE DATI SEDE LEGALE -------------------- -->
				 
				  <br><br>
				  <!-- STABILIMENTO ------------------------------------------------------------------------------------------------ -->
				
				
				  <table class="details" width="100%">
				
				
			 <!-- IL TIPO ATTIVITA' NON C'E' PER I NO SCIA -->
				   
				  	<tr><th colspan="2" ><div class="div_intestazione" align="center">Dati Sede Operativa <b>*</b><br> <font color="red" size="1">* Lo stabilimento specificato viene sempre inserito a sistema ex-novo</font></div></th></tr>
				   
				 
				  <!-- 
				   <tr>
				  <th style="width:200px;" align="left">Tipo Attivita</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;*</font>
				 			<select name="idtipoattivita_scelto" ng-model="idtipoattivita_scelto" >
				 				<option ng-repeat="tipoattivita in tipiattivita" value="{{tipoattivita.code}}">{{tipoattivita.description}}</option>
				 			</select>
				 		</td>
				  </tr> -->
				   <tr>
				 		<th style="width:200px;" align="left">Denominazione </th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;&nbsp;</font>
							<input type="text" name="denominazionestab_scelto" ng-model="denominazionestab_scelto" placeholder="denominazione"  />
						</td>
				 		</td>
				 </tr>
				  
				   <tr>
				 		<th style="width:200px;" align="left">Provincia </th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;*</font>
				 			<input type ="text" name="provinciastabilimento_scelto" ng-model="provinciastabilimento_scelto" placeholder="provincia sede" style="width:300px;" ng-required="true"/>
				 			<!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="form_nuova_anagrafica.provinciastabilimento_scelto.$touched && !form_nuova_anagrafica.provinciastabilimento_scelto.$valid" 
				 				align="center">
				 				&nbsp;{{msg_errore_campo_mancante_generico}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 			
					
							
					
				 		</td>
				 </tr>
				 <tr>
				 		<th style="width:200px;" align="left">Comune </th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;*</font>
							<select name="id_comune_stabilimento_scelto" ng-model="id_comune_stabilimento_scelto" ng-required="true" >
								<option ng-repeat="(nomecom, comune) in prendiSoloCampani(comuni)" value="{{comune.id}}" >{{comune.nome}}</option>
							</select>
							<!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="form_nuova_anagrafica.id_comune_stabilimento_scelto.$touched && !form_nuova_anagrafica.id_comune_stabilimento_scelto.$valid" 
				 				align="center">
				 				&nbsp;{{msg_errore_campo_mancante_generico}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
						</td>
				 		</td>
				 </tr>
				  <tr>
				 		<th style="width:200px;" align="left">Indirizzo</th>
				 		<td align="left" class="td_destro">
				 			<font color="red">&nbsp;*</font>
				 			<select name="idtoponimoindirizzostabilimento_scelto" ng-model="idtoponimoindirizzostabilimento_scelto">
				 				<option ng-repeat="toponimo in toponimi" value="{{toponimo.code}}" >{{toponimo.description}}</option>
				 			</select>
				 			&nbsp;
				 			<input name="viastabilimento_scelto" ng-model="viastabilimento_scelto" type="text" placeholder="indirizzo sede" style="width:300px;" ng-required="true"
				 			ng-change="aggiornaCorrettezzaIndirizzoStab()"
				 			/>
				 			&nbsp;
				 			<input type="text" name="civicostabilimento_scelto" ng-model="civicostabilimento_scelto" ng-show="viastabilimento_scelto.trim().length > 0" placeholder="n.civico sede" 
				 			ng-required="true"
				 			ng-change="aggiornaCorrettezzaIndirizzoStab()"
				 			/>
				 			&nbsp;
				 			<input type="text" name="capstabilimento_scelto" ng-model="capstabilimento_scelto"  ng-show="viastabilimento_scelto.trim().length > 0" placeholder="cap sede" 
				 			ng-required="true"
				 			ng-change="aggiornaCorrettezzaIndirizzoStab()"
				 			/>
				 			
				 			<!-- messaggi di errore   ------------------>
				 			<span class="span_errore"  ng-show="form_nuova_anagrafica.viastabilimento_scelto.$touched && !isIndirizzoStabValido" 
				 				align="center">
				 				&nbsp;{{msg_errore_indirizzo}}
				 			</span>
				 			<!-- fine messaggi errore ------------------>
				 			
				 		</td>
				 </tr>
				 <tr> 
				 	<th style="width:200px;" align="left">Coordinate</th>
				 	<td align="left" class="td_destro">
				 		<font color="red">&nbsp;*</font>
				 		<input type="text" name="latitudine_scelto" ng-model="latitudine_scelto" placeholder="latitudine sede" ng-required="true" ng-pattern="pattern_coordinate"/><br>
				 		<font color="red">&nbsp;*</font>
				 		<input type="text" name="longitudine_scelto" ng-model="longitudine_scelto" placeholder="longitudine sede" ng-required="true" ng-pattern="pattern_coordinate"/>
				 		<input type="button" value="CALCOLA COORDINATE"
				 			ng-show="isIndirizzoStabValido && form_nuova_anagrafica.provinciastabilimento_scelto.$valid && form_nuova_anagrafica.id_comune_stabilimento_scelto.$valid"
				 			ng-click="calcolaCoordinate()"
				 		/>
				 		<!-- messaggi di errore   ------------------>
				 		<span class="span_errore"  ng-show="(form_nuova_anagrafica.latitudine_scelto.$dirty && !form_nuova_anagrafica.latitudine_scelto.$valid) ||  
				 		(form_nuova_anagrafica.longitudine_scelto.$dirty && !form_nuova_anagrafica.longitudine_scelto.$valid)" 
				 			align="center">
				 			<br>
				 			&nbsp;&nbsp;&nbsp;{{msg_errore_coordinate}}
				 		</span>
				 		<!-- fine messaggi errore ------------------>
				 	</td>
				 </tr>
				 </table>
				 
				 
				 <!-- FINE DATI STABILMENTO --------------------------------------------------------------------------------- -->
				 
				  <br><br>
				  <!-- LINEE MASTER LIST------------------------------------------------------------------------------------------------ -->
				  <table class="details" width="100%">
				  <tr><th colspan="4" ><div class="div_intestazione" align="center">Dati Linee Attivita'<b>*</b><br> <font color="red" size="1">* Le linee specificate vengono sempre inserite e associato allo stabilimento inserito</font></div></th></tr>
				  
				  <tr ng-repeat="path in pathsAggiunte" ng-if="!path.trashed"> <!-- mi serve mantenere consistente l'$index anche per le trashed, quindi non posso usare filtro per non farle vedere altrimenti mi altera cnteggio della $index -->
				  		
				  		 
				  		 <!-- uso quindi ng-if -->
				  		 <td style="width: 50%; " >  
				  		 	<!-- SE IL PATH NON E' COMPLETATO PER LA path IN QUESTIONE, MOSTRO TEXTS PER I LIVELLI SCELTI E SELECT PER QUELLO SELEZIONABILE.... -->
				  		 	<div ng-show="!path.path_esaurito"  >
								
								 
								<!-- per ciascuno dei livello gia' scelti.... -->
								<font ng-repeat ="entryScelta in path.entries_precedenti_aggiunte">
										&nbsp; 	&middot;&nbsp;{{entryScelta.desc}}<br> 
								</font>
								   		 	
				  		 		<font ng-show="!path.path_esaurito"> <!-- non mostro select option scelta lvl successivo se il path e' esaurito -->
				  		 		 
				  		 			&nbsp;
				  		 			<select ng-model="path.last_selected_id" ng-change="selezionataEntryUltimoLivello($index)" style="max-width:500px;">
				  		 				<option ng-repeat="selezionabile in path.entries_selezionabili" value="{{selezionabile.id}}">{{selezionabile.desc}}</option>
				  		 			</select>
				  		 			<br>
				  		 		</font>
				  		 	</div>
				  		 	<!-- ALTRIMENTI MOSTRO SOLO RIEPILOGO ORIZZONTALE DEI LIVELLI SCELTI-->
				  		 	 <div ng-if="path.path_esaurito">
					  		 	<font ng-repeat="lvl in path.entries_precedenti_aggiunte" style="font-weight: bold;" >
					  		 				&nbsp;	&middot;&nbsp;{{lvl.desc}}<br> 
					  		 	</font>
					  		 	<br>
					  		 </div> 
				  		 </td>
				  		 
				  		 
				  		 <!-- NELLA PARTE DESTRA MOSTRO TUTTI I CAMPI ESTESI FINO AD ORA SBLOCCATI -->
				  		 
				  		 <td>
				  		 		<div ng-repeat="entryScelta in path.entries_precedenti_aggiunte">
				  		 			
						  		 	<div align="left" ng-show="entryScelta.campi_estesi.length > 0">
						  		 			<fieldset>
				  		 					<legend>Campi Aggiuntivi per <b>{{entryScelta.desc}}</b></legend>
						  		 				<div ng-repeat="campo_est in entryScelta.campi_estesi"  >
						  		 					 <fieldset>
						  		 						<campo-esteso campo="campo_est" /> <!-- direttiva custom -->
						  		 					</fieldset>
						  		 						 
						  		 				</div>
						  		 				 
						  		 			</fieldset>
						  		 	</div>
						  		 	
					  		 	</div>
				  		 </td>
				  		  
				  		 <!-- per la parte delle date (inizio e fine) -->
				  		 <td style="min-width:250px;">
				  		 	<div valign="top">
					  		 		<center>
					  		 		 <table border="0">
					  		 		 <tr>
					  		 		 <td>
							  		 	<b>DATA INIZIO</b>
							  		 </td>
							  		 <td>&nbsp;<input type="text" readonly id="data_inizio_path_{{$index}}" name="data_inizio_path_{{$index}}" ng-model="path.data_inizio" ng-pattern="pattern_data" placeholder="data inizio" ng-required></td>
							  		 </tr>
							  		 <tr>
							  		 <td><b>DATA FINE</b></td>
							  		 <td>&nbsp;<input type="text"  readonly id="data_fine_path_{{$index}}" name="data_fine_path_{{$index}}" ng-model="path.data_fine"   ng-pattern="pattern_data" placeholder ="data fine"></td>
					  		 		 </tr>
					  		 		</table>
					  		 		<br>
					  		 		 
					  		 		
					  		 		
					  		 		</center>
				  		 	</div>
				  		 </td>
				  		 <td >
				  		 	<span>
					  		 <input type="button" value="SVUOTA" ng-click="resetpath($index)" />
					  		 <input type="button" value="CANCELLA" ng-click="cancellapath($index)" />
					  		 </span>
				  		 </td>
				  </tr>
				  <tr>
				  	<td style="min-width: 100%;" colspan="3">  
				  		<div>
						 	<span ng-if="flag_tutte_paths_completate" style="color:green; position: relative; left: 55px;"><center>
						 	  <div style="position : relative; left: 40px;">
						 	&#10004;&nbsp;TUTTE LE ATTIVITA SELEZIONATE
						 	 </div>
						 	 </center></span>
						 	
						 	<!-- <span ng-if="!flag_tutte_paths_completate  && flag_almeno_unalinea_completamente_vuota" style="color:rgba(255,0,0,0.9); position: relative; left: 55px;"><center> 
						 	 <div style="position : relative; left: 40px;">
						 	ALMENO UNA LINEA COMPLETAMENTE VUOTA, RIMUOVERLA/RIEMPIRLA
						 	</div>
						 	 </center></span> -->
						 	
						 	<span ng-if="!flag_tutte_paths_completate" style="color:red; position: relative; left: 55px;"><center> 
						 	 <div style="position : relative; left: 40px;">
						 	ALCUNE LINEE DI ATTIVITA' PRESENTANO LIVELLI NON SCELTI
						 	</div>
						 	 </center></span>
						 	
						 </div>
				  	</td>
				  	<td  align="right" ><input   ng-click="aggiungipath()" type="button" value="AGGIUNGI ATTIVITA"/></td>
				  </tr>	 
				 
				 </table>
				 
				 <div ng-if="debug_mode">
					
					<b>
					CAMPI ESTESI INSERITI :
					</b>
					 <br>
					<span ng-repeat ="path in pathsAggiunte">
						->PATH N{{$index}}
						<br>
						{{path.data_inizio}}
						<br>
						{{path.data_fine}}
						<br>
						<div ng-repeat="entry in path.entries_precedenti_aggiunte">
							-->CAMPI PER LINEA LIVELLO {{entry.desc}}--------------
								<br>
								 <font ng-repeat="campo_est in entry.campi_estesi  ">---> {{campo_est.nome_campo}} :  <b>{{campo_est.campo_value}}</b> </br></font>
						</div>
						
					</span>
					<ul>
					  <li ng-repeat="(key, errors) in form_nuova_anagrafica.$error track by $index"> <strong>{{ key }}</strong> errors
					    <ul>
					      <li ng-repeat="e in errors">{{ e.$name }} has an error: <strong>{{ key }}</strong>.</li>
					    </ul>
					  </li>
					</ul>
					
					
					
					
					
					
				</div>
				 
				 <br>
			 	<div align="right">
			 		<!-- BOTTONE PER RIEMPIRE IL MOCKUP (DA TENERE NASCOSTO IN PROD) -->
			 		<input type="button" value="mockup insert" ng-click="popolaFormMockup()" ng-if="debug_mode" />
				 	<!-- btn invia se valido -->
					<input ng-if="form_nuova_anagrafica.$valid && flag_tutte_paths_completate"
					 type="button" value="INVIA" ng-click="inviaDati()"
					/>
					<!-- btn invia disabilitato -->
					<button ng-if="!form_nuova_anagrafica.$valid || !flag_tutte_paths_completate "
					ng-class="{schiarito : true}" ng-disabled 
					onclick="javascript: alert('Valorizzare tutti i campi obbligatori');">INVIA</button>	
				</div>
			 
				
				</dhv:container>	
				
				<br>
				<br>
				
				
				
				
				
				
			</form>
		 
			
			
	 
			
		</div>
		</center>
	</div>
	
	<br>
	<br>
	
	
	
	
	 
	
	
	
	
</body>
</html>