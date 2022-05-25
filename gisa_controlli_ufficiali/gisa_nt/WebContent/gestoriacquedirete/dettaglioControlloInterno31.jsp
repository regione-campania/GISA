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
			    <tr><th style=" width:10%;"   align="left">PUNTO DI PRELIEVO</th><td align="left">&nbsp;&nbsp;<%= nullablePrint(PuntoPrelievoRichiesto.getDenominazione()) %></td>
				<tr><th style=" width:10%;"   align="left">INDIRIZZO PUNTO DI PRELIEVO</th><td align="left">&nbsp;&nbsp;<%= nullablePrint(PuntoPrelievoRichiesto.getIndirizzo().getVia())%></td>
			 
				 <tr><th style=" width:10%;"   align="left">STATO CONTROLLO</th><td align="left">&nbsp;&nbsp;<%= etichettaStatoControllo %></td> 
				 <tr><th style=" width:10%;"   align="left">A.S.L.</th><td align="left">&nbsp;&nbsp;<%=nullablePrint(ControlloRichiesto.getDescAsl()) %></td>
			     <tr><th style=" width:10%;"   align="left">IDENTIFICATIVO</th><td align="left">&nbsp;&nbsp;<%=nullablePrint(ControlloRichiesto.getIdControlloUfficiale()+"") %></td> <!-- uso la versione padded di id del ticket, nella proprieta del bean id controllo ufficiale -->
				 <tr><th style=" width:10%;"   align="left">TECNICA DEL CONTROLLO</th><td align="left">&nbsp;&nbsp;<%="Controllo Interno" %></td>
				 <tr><th style=" width:10%;"   align="left">DATA PRELIEVO</th><td align="left">&nbsp;&nbsp;<%=ControlloRichiesto.getDataInizioControllo() != null ? sdf.format(new Date(ControlloRichiesto.getDataInizioControllo().getTime() )) : ""%></td>
				<tr><th style=" width:10%;"   align="left">ESITO</th><td align="left">&nbsp;&nbsp;<%=ControlloRichiesto.getEsito().equalsIgnoreCase("conforme") ? "<font color=\"green\">"+ControlloRichiesto.getEsito()+"</font>" : "<font color=\"red\">"+ControlloRichiesto.getEsito()+"</font>"   %></td>
				<tr><th style=" width:10%;"   align="left">PARAMETRI NON CONFORMI</th><td align="left">&nbsp;&nbsp;<%=ControlloRichiesto.getNonConformita() != null ? ControlloRichiesto.getNonConformita() : ""   %></td>
			</table>
			<br><br>
			<table class="details" width="100%;">
			
				<tr>
				<th>ORA</th>
				<th>CONTROLLO DI ROUTINE(PARTE A)</th>
				<th>CONTROLLO DI VERIFICA(PARTE B)</th>
				<th>CONTROLLO DI REPLICA</th>
				<th>CONTROLLO DI RICERCA FITOSANITARI</th>
				<th>CLORO</th>
				<th>TEMPERATURA</th>
				<th>NOTE</th>
				</tr>
				
				<tr >
				
					<%
						String checkTrue = "<font size=\"4\" color=\"green\">&#x2713;</font>" ;
					%>
					<td align="center"><%=printSafe(ControlloRichiesto.getOra())%></td>
					<td align="center"><b><%=  ControlloRichiesto.isProtocolloRoutine() == true ? checkTrue : "" %></b></td>
					<td align="center"><b><%=  ControlloRichiesto.isProtocollVerifica() == true ? checkTrue: "" %></b></td>
					<td align="center"><b><%=  ControlloRichiesto.isProtocolloReplicaMicro() == true ? checkTrue : "" %></b></td>
					<td align="center"><b><%=  ControlloRichiesto.isProtocolloRicercaFitosanitari() == true ? checkTrue : "" %></b></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getCloro())%></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getTemperatura())%></td>
					<td align="center"><%=printSafe(ControlloRichiesto.getNote())%></td>
				</tr>
				
			</table>
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