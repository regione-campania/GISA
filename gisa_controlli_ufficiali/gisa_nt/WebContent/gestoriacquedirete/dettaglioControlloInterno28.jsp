<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.gestoriacquenew.base.*"%>
<%@page import="java.net.URLEncoder" %>
<%@page import="java.sql.Date" %>
<%@ include file="../initPage.jsp" %>
<script src="gestoriacquedirete/script.js"></script>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="GestoreAcque" class="org.aspcfs.modules.gestoriacquenew.base.GestoreAcque" scope="session" /> <!-- vuoto se non è stato trovato per l'user id quel gestore -->
<jsp:useBean id="PuntoPrelievoRichiesto" class="org.aspcfs.modules.gestoriacquenew.base.PuntoPrelievo" scope="request" />
<jsp:useBean scope="request" class="org.aspcfs.modules.gestoriacquenew.base.ControlloInterno" id="ControlloRichiesto" />

<head>

</head>
<body>

 	 <%int idPuntoPrelievo = PuntoPrelievoRichiesto.getId();
 	   String paramPerContainer = "idPuntoPrelievo="+idPuntoPrelievo;
 	   %>
	 <%if(GestoreAcque.getId() <= 0 && GestoreAcque.getId()!=-999) {%>
		<!-- non e' stato trovato gestore acque per l'user id dell'utente loggato -->
		<br>
		
		<br>
		<font color="red"> Attenzione, per l'utente <%=User.getUsername() %> non e' stato trovato un Gestore Acque Di Rete Associato</font>
	
	<%}
	  else 
	  {
	  
		  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		  String etichettaStatoControllo = ControlloRichiesto.getStatusId() == 1 ? "<font size=\"4\" color=\"green\">&#x2713;</font> &nbsp;Aperto" : "<font size=\"4\" color=\"red\">&#x2715;</font> &nbsp;Chiuso";
	  %>
		
		<br>
		<br>
		<!-- e' stato trovato gestore acque per l'user id dell'utente loggato -->
		<center>
		<div style="max-width:1500px;">
		 	 <dhv:container name="puntodiprelievodetail_container" selected="Scheda Controlli Interni" object=""  param="<%=paramPerContainer %>">
			<table class="details" width="100%;">
				<tr><th colspan="2"><center>SCHEDA CONTROLLO GESTORE <%=PuntoPrelievoRichiesto.getNomeGestore()%></center></th></tr>
			    <tr><th style=" width:10%;"   align="left">PUNTO DI PRELIEVO</th><td align="left">&nbsp;&nbsp;<%= nullablePrint(PuntoPrelievoRichiesto.getDenominazione()) %></td></tr>
				<tr><th style=" width:10%;"   align="left">INDIRIZZO PUNTO DI PRELIEVO</th><td align="left">&nbsp;&nbsp;<%= nullablePrint(PuntoPrelievoRichiesto.getIndirizzo().getVia())%></td></tr>
				 <tr><th style=" width:10%;"   align="left">STATO CONTROLLO</th><td align="left">&nbsp;&nbsp;<%= etichettaStatoControllo %></td> </tr>
				 <tr><th style=" width:10%;"   align="left">A.S.L.</th><td align="left">&nbsp;&nbsp;<%=nullablePrint(ControlloRichiesto.getDescAsl()) %></td></tr>
			     <tr><th style=" width:10%;"   align="left">IDENTIFICATIVO</th><td align="left">&nbsp;&nbsp;<%=nullablePrint(ControlloRichiesto.getIdControlloUfficiale()+"") %></td></tr> <!-- uso la versione padded di id del ticket, nella proprieta del bean id controllo ufficiale -->
				 <tr><th style=" width:10%;"   align="left">TECNICA DEL CONTROLLO</th><td align="left">&nbsp;&nbsp;<%="Controllo Interno" %></td></tr>
				 <tr><th style=" width:10%;"   align="left">DATA PRELIEVO</th><td align="left">&nbsp;&nbsp;<%=ControlloRichiesto.getDataInizioControllo() != null ? sdf.format(new Date(ControlloRichiesto.getDataInizioControllo().getTime() )) : ""%></td></tr>		
		</table>		
				
		<% if (ControlloRichiesto.getTrizio()!=null) { %>		
		<table class="details" width="100%;">
		<tr><th style=" width:10%;"   align="left">ESITO</th><td align="left">&nbsp;&nbsp;<%=ControlloRichiesto.getEsito().equalsIgnoreCase("conforme") ? "<font color=\"green\">"+ControlloRichiesto.getEsito()+"</font>" : "<font color=\"red\">"+ControlloRichiesto.getEsito()+"</font>"   %></td></tr>
		<tr><th style=" width:10%;"   align="left">PARAMETRI NON CONFORMI</th><td align="left">&nbsp;&nbsp;<%=ControlloRichiesto.getNonConformita() != null ? ControlloRichiesto.getNonConformita() : ""   %></td></tr>
			</table>
			<br><br>
			<table class="details" width="100%;">
			
				<tr>
				 
				<th>ORA</th>
				<th>TRIZIO</th>
				<th>RADON</th>
				<th>DOSE INDICATIVA</th>
				<th>ALFA</th>
				<th>BETA</th>
				<th>NOTE</th>
				</tr>
				
				<tr >
				
					<%
						String checkTrue = "<font size=\"4\" color=\"green\">&#x2713;</font>" ;
					%>
					<td align="center"><%=printSafe(ControlloRichiesto.getOra())%></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getTrizio())%></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getRadon()) %></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getDose()) %></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getAlfa()) %></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getBeta()) %></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getNote()) %></td>
				</tr>
				
			</table>
	<% } else if (ControlloRichiesto.getCampione_finalitaMisura()!=null) {%>
	
		<br/><br/>
		<table class="details" width="100%;">
		<tr><th style=" width:10%;"   align="left" colspan="4">Identificativi del campione prelevato</th></tr>
		<tr>
		<th style=" width:10%;"   align="left">FINALITA' DELLA MISURA</th>
		<th style=" width:10%;"   align="left">NOTA SU FINALITA' DELLA MISURA</th>
		<th style=" width:10%;"   align="left">MOTIVO DEL PRELIEVO</th>
		<th style=" width:10%;"   align="left">NOTA SUL MOTIVO DEL PRELIEVO</th>
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getCampione_finalitaMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getCampione_notaFinalitaMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getCampione_motivoPrelievo() )%></td>
		<td><%=printSafe(ControlloRichiesto.getCampione_notaMotivoPrelievo() )%></td>
		</tr>
		</table>
		
		<br/>
		
		<table class="details" width="100%;">
		<tr><th style=" width:10%;"   align="left" colspan="2">Informazioni sulla zona di fornitura (ZdF)</th></tr>
		<tr>
		<th style=" width:10%;"   align="left">DENOMINAZIONE DELLA ZONA DI FORNITURA</th>
		<th style=" width:10%;"   align="left">GESTORE ZONA DI FORNITURA</th>
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getFornitura_denominazioneZona() )%></td>
		<td><%=printSafe(ControlloRichiesto.getFornitura_denominazioneGestore() )%></td>
		</tr>
		</table>
		
		<br/>
		
		<table class="details" width="100%;">
		<tr><th style=" width:10%;"   align="left" colspan="2">Informazioni sul campionamento</th></tr>
		<tr>
		<th style=" width:10%;"   align="left">NUMERO DI PRELIEVI PROGRAMMATI ALL'ANNO</th>
		<th style=" width:10%;"   align="left">CHI HA EFFETTUATO IL PRELIEVO</th>
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getCampionamento_numeroPrelievi() )%></td>
		<td><%=printSafe(ControlloRichiesto.getCampionamento_chi() )%></td>
		</tr>
		</table>
		
		<br/>
		
		<table class="details" width="100%;">
		<tr><th style=" width:10%;"   align="left" colspan="6">Controllo Dose Indicativa (DI) screening basato sulla misura dell'attivita' alfa e beta totale (o, se necessario, beta residua)</th></tr>
		
		<tr>
		<th style=" width:10%;"   align="left">ATTIVITA' ALFA TOTALE (Bq/kg) MAR</th>
		<th style=" width:10%;"   align="left">ATTIVITA' ALFA TOTALE (Bq/kg) Risultato della misura</th>
		<th style=" width:10%;"   align="left">INCERTEZZA SULL'ATTIVITA' ALFA TOTALE (Bq/kg)</th>
		<th style=" width:10%;"   align="left">DATA DELLA MISURA</th>
		<th style=" width:10%;"   align="left">LABORATORIO CHE HA EFFETTUATO LA MISURA</th>
		<th style=" width:10%;"   align="left">METODO DI PROVA UTILIZZATO</th>
		
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getDI_alfaTotaleMar() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_alfaTotaleMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_alfaTotaleIncertezza() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_alfaTotaleDataMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_alfaTotaleLaboratorio() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_alfaTotaleMetodoProva() )%></td>
		</tr>
		
		<tr>
		<th style=" width:10%;"   align="left">ATTIVITA' BETA TOTALE (Bq/kg) MAR</th>
		<th style=" width:10%;"   align="left">ATTIVITA' BETA TOTALE (Bq/kg) Risultato della misura</th>
		<th style=" width:10%;"   align="left">INCERTEZZA SULL'ATTIVITA' BETA TOTALE (Bq/kg)</th>
		<th style=" width:10%;"   align="left">DATA DELLA MISURA</th>
		<th style=" width:10%;"   align="left">LABORATORIO CHE HA EFFETTUATO LA MISURA</th>
		<th style=" width:10%;"   align="left">METODO DI PROVA UTILIZZATO</th>
		
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getDI_betaTotaleMar() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaTotaleMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaTotaleIncertezza() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaTotaleDataMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaTotaleLaboratorio() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaTotaleMetodoProva() )%></td>
		</tr>
		
		<tr>
		<th style=" width:10%;"   align="left">ATTIVITA' BETA RESIDUA (Bq/kg) MAR</th>
		<th style=" width:10%;"   align="left">ATTIVITA' BETA RESIDUA (Bq/kg) Risultato della misura</th>
		<th style=" width:10%;"   align="left">INCERTEZZA SULL'ATTIVITA' BETA RESIDUA (Bq/kg)</th>
		<th style=" width:10%;"   align="left">DATA DELLA MISURA</th>
		<th style=" width:10%;"   align="left">LABORATORIO CHE HA EFFETTUATO LA MISURA</th>
		<th style=" width:10%;"   align="left">METODO DI PROVA UTILIZZATO</th>
		
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getDI_betaResiduaMar() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaResiduaMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaResiduaIncertezza() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaResiduaDataMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaResiduaLaboratorio() )%></td>
		<td><%=printSafe(ControlloRichiesto.getDI_betaResiduaMetodoProva() )%></td>
		</tr>
		
		</table>
		
		<br/>
		
		<table class="details" width="100%;">
		<tr><th style=" width:10%;"   align="left" colspan="6">Controllo della concentrazione di attivita' di radon</th></tr>
		
		<tr>
		<th style=" width:10%;"   align="left">CONCENTRAZIONE di 222RN (Bq/l) MAR</th>
		<th style=" width:10%;"   align="left">CONCENTRAZIONE di 222RN (Bq/l) Risultato</th>
		<th style=" width:10%;"   align="left">INCERTEZZA SULLA CONCENTRAZIONE di 222RN (Bq/l)</th>
		<th style=" width:10%;"   align="left">DATA DELLA MISURA</th>
		<th style=" width:10%;"   align="left">LABORATORIO CHE HA EFFETTUATO LA MISURA</th>
		<th style=" width:10%;"   align="left">METODO DI PROVA UTILIZZATO</th>
		
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getRadon_concentrazioneMar() )%></td>
		<td><%=printSafe(ControlloRichiesto.getRadon_concentrazioneMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getRadon_concentrazioneIncertezza() )%></td>
		<td><%=printSafe(ControlloRichiesto.getRadon_concentrazioneDataMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getRadon_concentrazioneLaboratorio() )%></td>
		<td><%=printSafe(ControlloRichiesto.getRadon_concentrazioneMetodoProva() )%></td>
		</tr>
		</table>
		
		<br/>
		
		<table class="details" width="100%;">
		<tr><th style=" width:10%;"   align="left" colspan="6">Controllo della concentrazione di attivita' di radon</th></tr>
		
		<tr>
		<th style=" width:10%;"   align="left">CONCENTRAZIONE di 3H (Bq/l) MAR</th>
		<th style=" width:10%;"   align="left">CONCENTRAZIONE di 3H (Bq/l) Risultato</th>
		<th style=" width:10%;"   align="left">INCERTEZZA SULLA CONCENTRAZIONE di 3H (Bq/l)</th>
		<th style=" width:10%;"   align="left">DATA DELLA MISURA</th>
		<th style=" width:10%;"   align="left">LABORATORIO CHE HA EFFETTUATO LA MISURA</th>
		<th style=" width:10%;"   align="left">METODO DI PROVA UTILIZZATO</th>
		
		</tr>
		<tr>
		<td><%=printSafe(ControlloRichiesto.getTrizio_concentrazioneMar() )%></td>
		<td><%=printSafe(ControlloRichiesto.getTrizio_concentrazioneMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getTrizio_concentrazioneIncertezza() )%></td>
		<td><%=printSafe(ControlloRichiesto.getTrizio_concentrazioneDataMisura() )%></td>
		<td><%=printSafe(ControlloRichiesto.getTrizio_concentrazioneLaboratorio() )%></td>
		<td><%=printSafe(ControlloRichiesto.getTrizio_concentrazioneMetodoProva() )%></td>
		</tr>
		</table>
		
		<% } %>
	 		</dhv:container>
		</div>
		</center>
	
	<%} %>
	 

   <%! public String printSafe(String toPrint)
      {
      	if(toPrint == null )
      	{
      		toPrint = "";
      	}
      	return toPrint;
      	
      }%>

		 

</body>
</html>