<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@page import="com.anagrafica_noscia.prototype.base_beans.*" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@page import="java.sql.Timestamp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
	.td_destro > * /*qualunque cosa sta nel td destro */
	{
		position: relative;
		left: 10px;
	}
	
	table.table_linee td
	{
		max-width: 220px;
	}
	
	.table_ind_interno th
	{
		background-color: white;
	}
	
	.table_ind_interno td, .table_ind_interno td
	{
		border-color:white;
		border-bottom-color: white;
		border-top-color: white;
	}
</style>



 <!-- QUESTA E' UNA PAGINA DI DETTAGLIO (PARTE STATICA) GENERATA CON JSP SCRIPTLET
 CHE PERO' DIVENTA DINAMICA QUANDO SI CLICCA SU MODIFICA (USANDO ANGULAR)
 PER FARE QUESTO QUANDO LA PAGINA VIENE GENERATA, SALVO I VALORI DEI BEAN JAVA (PARTE STATICA) IN OGGETTI JAVASCRIPT (..._originale) TRAMITE LA NG-INIT DI ANGULAR
 CHE RAPPRESENTANO I VALORI ORIGINALI DEL MODELLO, 
 QUANDO ABILITO MODALITA MODIFICA, QUESTI VALORI ORIGINALI VENGONO PRESI DA QUESTI OGGETTI JAVASCRIPT E VENGONO COPIATI
 NEI BEAN ANGULAR JAVASCRIPT CHE SERVONO PER DARE IL COMPORTAMENTO DINAMICO DELLA PAGINA
 SE L'UTENTE CONFERMA IL SALVATAGGIO, QUESTI VALORI VENGONO INVIATI AL SERVER E TUTTA LA PAGINA VIENE RICARICATA
 SE NON LO CONFERMA SI ESCE SEMPLICEMENTE, QUINDI I VALORI MODIFICATI (E MEMORIZZATI NEI BEAN ANGULAR) VERRANNO PERSI 
 LA SUCCESSIVA VOLTA CHE SI RIENTRA IN MODALITA MODIFICA -->
 <!-- NB : LO SPLIT DEL BEAN TRA VERSIONE _ORIGINALE E VERSIONE DOVE SALVO I VALORI TEMPORANEI DI ANGULAR E' SOLO A LIVELLO
 DELLE UNITA' ATOMICHE (AD ESEMPIO NON DUPLICO LA STRUTTURA INDIRIZZI, MA SOLO TOPONIMO,VIA etc...) -->


<!-- INCLUDO ANGULAR E JQUERY-->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="anagrafica_noscia/utils/datepicker-it.js"></script> <!-- per rendere italiano il widget del datepicker -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>

<!-- CREO MODULO PRINCIPALE ANGULAR -->
<script>
	angular.module("main_module",[]);
</script>

<!-- INCLUDO ALCUNE ROUTINE DI UTILITY --> 
<!--E SOLO DOPO AVER CREATO IL MODULO PRINCIPALE, INCLUDO I SERVIZI E I CONTROLLER ASSOCIATIGLI -->
<script src="anagrafica_noscia/servizi/servizi.js"></script>
<script src="anagrafica_noscia/controllers/root_controller.js"></script>
<script src="anagrafica_noscia/controllers/controller_dettaglio_modifica.js"></script>


</head>
<body>
 
  

<%
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Impresa imp = (Impresa)request.getAttribute("impresa");  
		String paramPerContainer="idImpresa="+imp.getId();
		
		String ragioneSociale = imp.getRagioneSociale();
		String pIvaImp = imp.getPiva();
		String cfImp = imp.getCodFiscale();
		String tipoImpresaSocieta = imp.getLookupPairTipoImpresaSocieta().getDesc() != null ? imp.getLookupPairTipoImpresaSocieta().getDesc() : "";
		int tipoImpresaSocI = imp.getLookupPairTipoImpresaSocieta().getCode();
		
		String dataInserimentoImpresa = "";
		try{dataInserimentoImpresa = sdf.format(new Date(imp.getDataInserimento().getTime())); }catch(Exception ex){}
		
		String dataArrivoPecS = "";
		try{dataArrivoPecS = sdf.format(new Date(imp.getDataArrivoPec().getTime()));} catch(Exception ex){}
		 
		
%>
		
	  
	<center>
	<div  style="max-width:1300px;" ng-app="main_module" ng-controller="root_controller">
		<div ng-controller=" controller_dettaglio_modifica">
		<dhv:container name="anagnoscia_risultatiricercaanagrafica_container" selected="Dettaglio Anagrafica" object=""  param="<%=paramPerContainer %>" >
			
			 
				<div align="right">
					<input ng-show="modalitaModifica == false"  type="button" value="ABILITA MODIFICHE"  ng-click="abilitaModifica()"/>
					<input ng-show="modalitaModifica == true" style="background-color:red;"  type="button" value="SALVA MODIFICHE"  ng-click="salvaModifiche()"/> 
				</div>
			</center>
			
			<fieldset
				ng-init="
						idImpresa ='<%=imp.getId() %>';
						ragioneSocialeImpresa_originale = '<%=ragioneSociale %>';
						pIvaImpresa_originale = '<%=pIvaImp %>';
						cfImpresa_originale = '<%=cfImp %>';
						dataArrivoPecS_originale = '<%=dataArrivoPecS %>';
						dataInserimentoPecS_originale = '<%=dataInserimentoImpresa %>';
						tipoImpresaSocieta_originale = <%=tipoImpresaSocI %>;
						"
			> <!-- DATI IMPRESA -->
				<legend>IMPRESA</legend>
				
				<table class="details" width="100%">
					<tr>
						<th style="width:200px;" align="left">TIPO IMPRESA/SOCIETA</th>
						<td class="td_destro" align="left" >
							<font ng-show="!modalitaModifica" ><%=tipoImpresaSocieta %></font>
							 
							<select ng-show="modalitaModifica" ng-model="tipoImpresaSocieta" >
								<option ng-repeat="tipoImpresa in tipi_imprese" value="{{tipoImpresa.code}}">{{tipoImpresa.description}}</option>
							</select>
						 	
						
						</td>
					</tr>
					<tr>
						<th style="width:200px;" align="left">RAGIONE SOCIALE IMPRESA</th>
						<td class="td_destro" align="left" >
							<font ng-show="!modalitaModifica"><%=ragioneSociale %></font>
							<input type ="text" ng-show="modalitaModifica" ng-model="ragioneSocialeImpresa" />
						
						</td>
					</tr>
					<tr>
						<th style="width:200px;" align="left">P.IVA IMPRESA</th>
						<td class="td_destro" align="left">
							<font ng-show="!modalitaModifica"><%=pIvaImp %></font>
							<input type="text" ng-model="pIvaImpresa" ng-show="modalitaModifica" />
						</td>
					</tr>
					<tr>
						<th style="width:200px;" align="left">CODICE FISCALE IMPRESA</th>
						<td class="td_destro"  align="left">
							<font ng-show="!modalitaModifica"><%=cfImp %></font>
							<input type="text" ng-show="modalitaModifica" ng-model="cfImpresa" />
						</td>
					</tr>
					<tr>
						<!-- QUESTI DUE CAMPI DATE NON LI RENDO MODIFICABILI -->
						<th style="width:200px;" align="left">DATA ARRIVO PEC</th>
						<td class="td_destro"  align="left"><font><%=dataArrivoPecS %></font></td>
					</tr>
					<tr>
						<th style="width:200px;" align="left">DATA INSERIMENTO IMPRESA</th>
						<td class="td_destro"  align="left"><font><%=dataInserimentoImpresa %></font></td>
					</tr>
				</table>
		 	</fieldset>
		 	
		 	<br>
		 	
		 	<div ng-init="indirizziSedeLegale = {}"> <!-- memorizzo gli indirizzi stabilimento, mappandoli in hash map indirizzo[idindirizzo] = indirizzo -->
		 	<!-- DATI SEDE LEGALE -->
		 		<!-- teoricamente da modello dati potrebbe esserci piu' di una sede legale (indirizzo impresa) ma 
		 		cio' non capita mai in realta' -->
		 		<%
					List<Indirizzo> sedi = imp.getIndirizzi();
		 			for(int i = 0; i< sedi.size(); i++)
		 			{
		 				Indirizzo sede = sedi.get(i);
		 				String descToponimo = sede.getDescToponimo( )!= null ? sede.getDescToponimo() : "";
		 				String via = sede.getDescVia() != null ? sede.getDescVia() : "";
						String cap = sede.getCap() != null ? sede.getCap() : "";
						String civico = sede.getCivico() != null ? sede.getCivico() : "";
						String descComuneInd = sede.getDescComune() != null ? sede.getDescComune() : "";
		 				%>
		 					
		 					<fieldset>
		 						<legend>SEDE LEGALE (<%=i+1 %>)</legend>
		 						<!-- dummy div per inizializzar eil bean dell'indirizzo -->
		 						<div ng-init="indirizziSedeLegale[<%=sede.getId()%>] = {};
											  indirizziSedeLegale[<%=sede.getId()%>].via_originale = '<%=via %>';
											  indirizziSedeLegale[<%=sede.getId()%>].cap_originale = '<%=cap %>';
											  indirizziSedeLegale[<%=sede.getId()%>].civico_originale = '<%=civico %>';
											  indirizziSedeLegale[<%=sede.getId()%>].idComune_originale = <%=sede.getIdComune() %>;
											  indirizziSedeLegale[<%=sede.getId()%>].idToponimo_originale = <%=sede.getIdToponimo() %>;
		 						"
		 						> <!--  /* in realta' il toponimo va settato quando il servizio riceve la lista */ -->
		 						
		 						</div>
		 						<table class="details" width="100%">
		 							<!-- TR SE NON SIAMO IN MODALITA MODIFICA -->
		 							<tr ng-show="!modalitaModifica">
										<th style="width:200px;" align="left">INDIRIZZO</th>
										<td class="td_destro"  align="left" valign="middle"> <!-- teoricamente da modello piu' di un indirizzo, ma in realta' al max 1 -->
												 
													 <font><%=descToponimo +" "+via+" "+civico+" "+descComuneInd+" "+cap %></font> <br>
												 
										</td>
									</tr>
									<!-- TRs SE SIAMO IN MODALITA MODIFICA -->
									<tr ng-show="modalitaModifica">
										<th style="width:200px;" align="left">TOPONIMO</th>
										<td class="td_destro">
											<select ng-model="indirizziSedeLegale[<%=sede.getId()%>].idToponimo" >
												<option ng-repeat="toponimo in toponimi" value="{{toponimo.code}}">{{toponimo.description}}</option>
											</select> 
										</td>
									</tr>
									<tr ng-show="modalitaModifica">
										<th style="width:200px;" align="left">INDIRIZZO</th>
										<td class="td_destro"><input type="text" ng-model="indirizziSedeLegale[<%=sede.getId()%>].via" /></td>
									</tr>
									<tr ng-show="modalitaModifica">
										<th style="width:200px;" align="left">CIVICO</th>
										<td class="td_destro"><input type="text" ng-model="indirizziSedeLegale[<%=sede.getId()%>].civico" /></td>
									</tr>
									<tr ng-show="modalitaModifica">
										<th style="width:200px;" align="left">CAP</th>
										<td class="td_destro"><input type="text" ng-model="indirizziSedeLegale[<%=sede.getId()%>].cap" /></td>
									</tr>
									<tr ng-show="modalitaModifica">
										<th style="width:200px;" align="left">COMUNE</th>
											<td class="td_destro">
												 <select ng-model="indirizziSedeLegale[<%=sede.getId()%>].idComune" >
												 	<option ng-repeat="comune in comuniCampani" value="{{comune.id}}">{{comune.nome}}</option>
												 </select>
											</td>
												 
									</tr>
		 						</table>
		 						
		 						
		 					</fieldset>
		 				
		 				<%
		 			}
		 		%>
			</div>		 	
		 	
		 	
		 	<div ng-init="rappresentantiLegali = {}"> <!-- memorizzo ciascuno dei soggetti fisici, per id -->
		 	 <!-- DATI LEGALE -->
		 		<!-- TEORICAMENTE DA MODELLO DATI
		 		 POTREBBE ESSERCI PIU' DI UN LEGALE PER IMPRESA, MA ALL'ATTO PRATICO NON SI PUO' VERIFICARE -->
		 		
		 		 
		 		 <% 
		 		 	List<SoggettoFisico> legaliRapps = imp.getLegaliRappresentanti();
		 		 
		 		 
		 		 
		 		 	for(int i = 0; i< legaliRapps.size(); i++)
		 		 	{
		 		 		SoggettoFisico legale = legaliRapps.get(i);
		 		 		String nomeRappLe = legale.getNome() != null ? legale.getNome() : "";
		 		 		String cognomeRappLe = legale.getCognome() != null ? legale.getCognome() : "";
		 		 		String cfRappLe = legale.getCodiceFiscale() != null ? legale.getCodiceFiscale() : "";
		 		 		String comuneNascitaRappLe = legale.getDescComuneNascita() != null ? legale.getDescComuneNascita() : "";
		 		 		String descSexRappLe = legale.getDescSex() != null ? legale.getDescSex() : "";
		 		 		String telRappLe = legale.getTelefono() != null ? legale.getTelefono() : "";
		 		 		String pecRappLe = legale.getPec() != null ? legale.getPec() : "";
		 		 		List<Indirizzo> indirizziRappLe = legale.getIndirizzi();
		 		 		 
		 		 		
		 				 
		 		 %>
		 		 		<fieldset>
		 		 		<legend ng-init="
		 		 			rappresentantiLegali[<%=legale.getId() %>] = {};
		 		 			rappresentantiLegali[<%=legale.getId() %>].nome_originale = '<%=nomeRappLe%>';
		 		 			rappresentantiLegali[<%=legale.getId() %>].cognome_originale = '<%=cognomeRappLe%>';
		 		 			rappresentantiLegali[<%=legale.getId() %>].cf_originale = '<%=cfRappLe%>';
		 		 			rappresentantiLegali[<%=legale.getId() %>].descSex_originale = '<%=descSexRappLe%>';
		 		 			rappresentantiLegali[<%=legale.getId() %>].descComuneNascita_originale = '<%=comuneNascitaRappLe%>';
		 		 			rappresentantiLegali[<%=legale.getId() %>].pec_originale = '<%=pecRappLe%>';
		 		 			rappresentantiLegali[<%=legale.getId() %>].telefono_originale = '<%=telRappLe%>';
		 		 			rappresentantiLegali[<%=legale.getId() %>].indirizzi = {}; 
		 		 		">
		 		 			RAPPRESENTANTE LEGALE (<%=i+1 %>)
		 		 		</legend>
		 		 		
		 		 		<table class="details" width="100%"> <!-- una table per legale, anche se e' al max 1 in genere -->
		 		 			<tr>
								<th style="width:200px;" align="left">NOME</th>
								<td class="td_destro"  align="left">
									<font ng-show="!modalitaModifica"><%=nomeRappLe %></font>
								    <input ng-show="modalitaModifica" type="text" ng-model="rappresentantiLegali[<%=legale.getId() %>].nome" />
								</td>
							</tr>
							<tr>
								<th style="width:200px;" align="left">COGNOME</th>
								<td class="td_destro"  align="left">
									<font ng-show="!modalitaModifica"><%=cognomeRappLe %></font>
									<input type="text" ng-model="rappresentantiLegali[<%=legale.getId() %>].cognome" ng-show="modalitaModifica" />
								</td>
							</tr>
							<tr>
								<th style="width:200px;" align="left">CODICE FISCALE</th>
								<td class="td_destro"  align="left">
									<font ng-show="!modalitaModifica"><%=cfRappLe %></font>
									<input type="text" ng-show="modalitaModifica" ng-model="rappresentantiLegali[<%=legale.getId() %>].cf" />
								</td>
							</tr>
							<tr>
								<th style="width:200px;" align="left">SESSO</th>
								<td class="td_destro"  align="left">
									<font ng-show="!modalitaModifica"><%=descSexRappLe %></font>
									<select ng-show="modalitaModifica" ng-model="rappresentantiLegali[<%=legale.getId() %>].descSex">
										<option value="M" >M</option>
										<option value="F">F</option>
									</select>
								</td>
							</tr>
							<tr>
								<th style="width:200px;" align="left">COMUNE NASCITA</th>
								<td class="td_destro"  align="left">
									<font ng-show="!modalitaModifica"><%=comuneNascitaRappLe %></font>
									<input type="text" ng-model="rappresentantiLegali[<%=legale.getId() %>].descComuneNascita" ng-show="modalitaModifica"/>
								</td>
							</tr>	
							<tr>
								<th style="width:200px;" align="left">INDIRIZZI DEL RAPPRESENTANTE</th>
								<td class="td_destro"  align="left" valign="middle"> <!-- teoricamente da modello piu' di un indirizzo, ma in realta' al max 1 -->
										<%for(int j = 0; j< indirizziRappLe.size(); j++){ 
											Indirizzo ind = indirizziRappLe.get(j);
											String descToponimo = ind.getDescToponimo() != null ? ind.getDescToponimo() : "";
											String via = ind.getDescVia() != null ? ind.getDescVia() : "";
											String cap = ind.getCap() != null ? ind.getCap() : "";
											String civico = ind.getCivico() != null ? ind.getCivico() : "";
											String descComuneInd = ind.getDescComune() != null ? ind.getDescComune() : "";
											%>
											 <font ng-show="!modalitaModifica"><%=descToponimo +" "+via+" "+civico+" "+descComuneInd+" "+cap %></font>   <!-- li concateno tutti s enon sono in modifica -->
											 <div ng-show="modalitaModifica">
													<div ng-init="
												 	rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>] = {};
												 	rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].via_originale = '<%=via %>';
												 	rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].cap_originale = '<%=cap %>';
												 	rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].civico_originale = '<%=civico %>';
												 	rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].idComune_originale = <%=ind.getIdComune() %>;
												 	rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].idToponimo_originale = <%=ind.getIdToponimo()%>;
												 ">
												
												 </div>
											
												 <table class="table_ind_interno" > <!-- altrimenti se sono in modalita' modifica.. -->
												 	<tr>
												 		<th  >TOPONIMO</th>
												 		<td  >
															<select ng-model="rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId() %>].idToponimo" >
																<option ng-repeat="toponimo in toponimi" value="{{toponimo.code}}">{{toponimo.description}}</option>
															</select>
														</td> 
												 	</tr>
												 	<tr><th  >VIA</th><td  ><input type="text" ng-model="rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].via" ></td></tr>
												 	<tr><th  >CIVICO</th><td ><input type="text" ng-model="rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].civico" /></td></tr>
												 	<tr><th  >CAP</th><td ><input type="text" ng-model="rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].cap" /></td></tr>
												 	<tr>
												 		<th  >COMUNE</th>
												 		<td  >
												 			<select ng-model="rappresentantiLegali[<%=legale.getId() %>].indirizzi[<%=ind.getId()%>].idComune" >
												 				<option ng-repeat="comune in comuniCampani" value="{{comune.id}}">{{comune.nome}}</option>
												 			</select>
												 		</td>
												 	</tr>
												 </table>
												 
											 </div>
											 
										<%} %>
								</td>
							</tr>
							<tr>
								<th style="width:200px;" align="left">PEC</th>
								<td class="td_destro"  align="left">
									<font ng-show="!modalitaModifica"><%=pecRappLe %></font>
									<input ng-show="modalitaModifica" ng-model="rappresentantiLegali[<%=legale.getId() %>].pec" ng-show="modalitaModifica" />
									
								</td>
							</tr>	
							<tr>
								<th style="width:200px;" align="left">TELEFONO</th>
								<td class="td_destro"  align="left">
									<font ng-show="!modalitaModifica"><%=telRappLe %></font>
									<input ng-show="modalitaModifica" ng-model="rappresentantiLegali[<%=legale.getId() %>].telefono" ng-show="modalitaModifica" />
								</td>
							</tr>	
		 		 		</table>
		 		 		
		 		 		
		 		 		 
		 		 		
		 		 		</fieldset>
		 		 		<br>
		 		 <%} %>
				
			</div>
		 	
		 	
		 	 
		 	
		 	<!-- DATI STAB -->
		 		<!-- TEORICAMENTE DA MODELLO DATI
		 		 POTREBBE ESSERCI PIU' DI UNO STAB PER IMPRESA, MA ALL'ATTO PRATICO NON SI PUO' VERIFICARE -->
		 		 
		 		 <div ng-init="stabilimenti = {};"> <!-- metto gli stabilimenti dell'impresa in un'hashmap dove chiave e' id stab -->
		 		 <%
		 		     List<Stabilimento> stabs = imp.getStabilimenti();
		 			 
		 		 	  for(int i = 0; i< stabs.size(); i ++) { 
		 			 	 Stabilimento stab = stabs.get(i);
		 			 	 String denominazioneStab = stab.getDenominazione() != null ? stab.getDenominazione() : "";
		 			 	 String descStato = stab.getStato( )!= null ? stab.getStato() : "";
		 			 	 Timestamp dataInserimento = stab.getDataInserimento();
		 			 	 String dataInserimentoS = dataInserimento != null ? sdf.format(new Date(dataInserimento.getTime())) : "";
		 			 	 List<Indirizzo> indirizziStab = stab.getIndirizzi(); /*sedi operative */
		 			   %>
		 			    <fieldset
		 			    	ng-init="
		 			    		stabilimenti[<%=stab.getId() %>] = {};
		 			    		stabilimenti[<%=stab.getId() %>].denominazione_originale = '<%=denominazioneStab %>';
		 			    		stabilimenti[<%=stab.getId() %>].idStato_originale = '<%=stab.getIdStato() %>';
		 			    		stabilimenti[<%=stab.getId() %>].indirizzi_originale = {};
		 			    	"
		 			    
		 			    >
		 			    <legend>STABILIMENTO (<%=i+1 %>)</legend>
		 			   	<table class="details" width="100%">
		 					<tr>
		 						<th align="left" style="width:200px;">DENOMINAZIONE</th>
		 						<td align="left" class="td_destro">
		 							<font ng-show="!modalitaModifica"><%=denominazioneStab %></font>
		 							<input type="text" ng-show="modalitaModifica" ng-model="stabilimenti[<%=stab.getId() %>].denominazione" />
		 						</td>
		 					</tr>
		 					<tr>
		 						<th align="left" style="width:200px;">STATO</th>
		 						<td align="left" class="td_destro">
		 							<font ng-show="!modalitaModifica"><%=descStato %></font>
									<select ng-show="modalitaModifica" ng-model="stabilimenti[<%=stab.getId() %>].idStato" >
										<option ng-repeat="stato in statiStab" value="{{stato.code}}">{{stato.description}}</option>
									</select>
		 						</td>
		 					</tr>
		 					<tr>
		 						<th align="left" style="width:200px;">DATA INSERIMENTO</th>
		 						<td align="left" class="td_destro"><font><%=dataInserimentoS %></font></td>
		 					</tr>
		 					<tr>
								<th style="width:200px;" align="left">INDIRIZZI STABILIMENTO</th>
								<td class="td_destro"  align="left" valign="middle"> <!-- teoricamente da modello piu' di un indirizzo, ma in realta' al max 1 -->
										<%for(int j = 0; j< indirizziStab.size(); j++){ 
											Indirizzo ind = indirizziStab.get(j);
											String descToponimo = ind.getDescToponimo() != null ? ind.getDescToponimo() : "";
											String via = ind.getDescVia() != null ? ind.getDescVia() : "";
											String cap = ind.getCap() != null ? ind.getCap() : "";
											String civico = ind.getCivico() != null ? ind.getCivico() : "";
											String descComuneInd = ind.getDescComune() != null ? ind.getDescComune() : "";
											%>
											 <font ng-show="!modalitaModifica"><%=descToponimo+" "+via+" "+civico+" "+descComuneInd+" "+cap %></font>   <!-- li concateno tutti s enon sono in modifica -->
											 <div ng-show="modalitaModifica">
											 	 <div ng-init="
												 	stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>] = {};
												 	stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].via_originale = '<%=via %>';
												 	stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].cap_originale = '<%=cap %>';
												 	stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].civico_originale = '<%=civico %>';
												 	stabilimentie[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].idComune_originale = <%=ind.getIdComune() %>;
												 	stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].idToponimo_originale = <%=ind.getIdToponimo() %>;
												 "> <!-- dummy div per l'init -->
												 
												 </div>
												 <table class="table_ind_interno"> <!-- altrimenti se sono in modalita' modifica.. -->
												 	<tr><th  >TOPONIMO</th>
												 		<td  >
															<select ng-model="stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].idToponimo" >
																<option ng-repeat="toponimo in toponimi" value="{{toponimo.code}}">{{toponimo.description}}</option>
															</select>
														</td> 
												 	</tr>
												 	<tr><th  >VIA</th><td  ><input type="text" ng-model="stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].via" ></td></tr>
												 	<tr><th  >CIVICO</th><td  ><input type="text" ng-model="stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].civico" /></td></tr>
												 	<tr><th  > CAP</th><td  ><input type="text" ng-model="stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].cap" /></td></tr>
												 	<tr><th  >COMUNE</th>
												 		<td  >
												 			<select ng-model="stabilimenti[<%=stab.getId() %>].indirizzi[<%=ind.getId()%>].idComune" >
												 				<option ng-repeat="comune in comuniCampani" value="{{comune.id}}">{{comune.nome}}</option>
												 			</select>
												 		</td>
												 	
												 	</tr>
												 </table>
												 
											 </div>
											 
											 
											 
										<%} %>
								</td>
							</tr>
		 				</table>
		 				
		 				<!-- MOSTRO TUTTE LE LINEE PER LO STABILIMENTO -->
		 				<br>
		 				<fieldset>
		 					<legend >LINEE ATTIVITA</legend>
		 					<table class="details table_linee" width="100%">
		 						<tr><th>MACROAREA</th><th>AGGREGAZIONE</th><th>ATTIVITA</th><th>N.REGISTRAZIONE</th><th>STATO</th><!-- <th>INIZIO</th><th>fine</th></tr>-->
		 						<%
		 							List<RelazioneStabilimentoLineaProduttiva> lineeLP = stab.getLineeProds();
		 							for(int k = 0; k<lineeLP.size(); k++)
		 							{
		 								RelazioneStabilimentoLineaProduttiva lp = lineeLP.get(k);
		 								
		 								String descStatoLinea = lp.getStato();
		 								String numeroRegistrazione = lp.getNumeroRegistrazione() != null ? lp.getNumeroRegistrazione() : "";
		 								String macroarea = lp.getMacroarea().getMacroarea() != null ? lp.getMacroarea().getMacroarea() : "";
		 								String aggregazione = lp.getAggregazione().getAggregazione() != null ? lp.getAggregazione().getAggregazione() : "";
		 								String attivita = lp.getLineaAttivita().getLineaAttivita() != null ? lp.getLineaAttivita().getLineaAttivita() : "";
		 								/*TODO AGGIUNGI DATA INIZIO E DATA FINE ATTIVITA */
		 								%>
		 									<tr>
		 									<td><%=macroarea %></td><td><%=aggregazione %></td><td><%=attivita %></td><td><%=numeroRegistrazione %></td><td><%=descStato %></td>
		 									</tr>
		 								<%
		 							}
		 						%>
		 					</table>
		 				</fieldset>
		 				
		 				
		 			   </fieldset>
		 			 <%} %>
		 		 
		 		</div>
		 	
		 	 
		</dhv:container>
		</div>
		
		
	</div>
	
	
		 		
	</center>
	
</body>
</html>