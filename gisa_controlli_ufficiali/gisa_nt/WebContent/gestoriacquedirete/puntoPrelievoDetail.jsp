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
<%@ include file="../initPage.jsp" %>
<script src="gestoriacquedirete/script.js"></script>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="GestoreAcque" class="org.aspcfs.modules.gestoriacquenew.base.GestoreAcque" scope="session" /> <!-- vuoto se non è stato trovato per l'user id quel gestore -->
<jsp:useBean id="PuntoPrelievoRichiesto" class="org.aspcfs.modules.gestoriacquenew.base.PuntoPrelievo" scope="request" />

<head>

</head>
<body>

 	 <%int idPuntoPrelievo = PuntoPrelievoRichiesto.getId();
 	   String paramPerContainer = "idPuntoPrelievo="+idPuntoPrelievo;
 	   %>
	 <%if(GestoreAcque.getId() <= 0 && GestoreAcque.getId() != -999 ) {%>
		<!-- non e' stato trovato gestore acque per l'user id dell'utente loggato -->
		<br>
		<br>
		
	
	<%}
	  else 
	  {
	  
	  
	  %>
		
		<!-- Questo messaggio esce se il dettaglio viene chiamato dopo la modifica di stato -->
		<%=(request.getAttribute("esitoModifyStato")!=null ?  (String)request.getAttribute("esitoModifyStato") : "")%>
		
		<br>
		<br>
		<!-- e' stato trovato gestore acque per l'user id dell'utente loggato -->
		<center>
		<div style="max-width:1500px;">
		 <dhv:container name="puntodiprelievodetail_container" selected="Scheda Punto Di Prelievo" object=""  param="<%=paramPerContainer %>">
			<table class="details" width="100%;">
				<tr><th colspan="2"  ><center>PUNTO DI PRELIEVO</center></th></tr>
				 <tr><th style=" width:10%;"   align="left">ASL</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getDescrizioneAsl()) %></td> 
				 <tr><th style=" width:10%;"   align="left">GESTORE</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getNomeGestore()) %></td> 
				 <tr><th    align="left">COMUNE</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getIndirizzo().getDescrizioneComune()) %></td> 
				 <tr><th   align="left">INDIRIZZO</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getIndirizzo().getVia()) %></td> 
				 <tr><th    align="left">DENOMINAZIONE</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getDenominazione()) %></td> 
				 <tr><th    align="left">TIPOLOGIA</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getDescrizioneTipologia()) %></td> 
				 <tr>
				 	<th align="left">
				 		STATO
				 	</td>
				 	<td align="left">
				 		&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getStato()) %>
				 		<dhv:permission name="gestoriacquenew-edit">
				 		<form action="StabilimentoGestoriAcqueReteNewAction.do?command=PuntoPrelievoModifyStato" method="post">
				 			<input type="hidden" id="idGestore" name="idGestore" value="<%=PuntoPrelievoRichiesto.getIdGestore()%>">
							<input type="hidden" id="idPuntoPrelievo" name="idPuntoPrelievo" value="<%=PuntoPrelievoRichiesto.getId()%>">
							<input type="submit" value='Modifica stato PDP' />
						</form>
						</dhv:permission>
				 	</td>
				 <tr><th    align="left">CODICE GISA</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getCodiceGisa()) %></td>
				 <tr><th    align="left">CODICE</td><td align="left">&nbsp;&nbsp;<%=nullablePrint(PuntoPrelievoRichiesto.getCodice()) %></td>
				 <tr><th    align="left">LATITUDINE</td><td align="left">&nbsp;&nbsp;<%= PuntoPrelievoRichiesto.getIndirizzo().getLatitudine() %></td>
				 <tr><th    align="left">LONGITUDINE</td><td align="left">&nbsp;&nbsp;<%= Double.toString(PuntoPrelievoRichiesto.getIndirizzo().getLongitudine()) %></td>
				 
				 <tr><th    align="left">DATA INSERIMENTO</td><td align="left">&nbsp;&nbsp;<%=toDateWithTimeasString(PuntoPrelievoRichiesto.getDataInserimento()) %></td>
			</table>
		</dhv:container>
		</div>
		</center>
	
	<%} %>
	 

 

		 

</body>
</html>