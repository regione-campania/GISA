<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
  <jsp:useBean id="AnagraficaDetails" class="org.aspcfs.modules.gestioneanagrafica.base.Anagrafica" scope="request"/>
  
  
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
  
  <%@ include file="../initPage.jsp" %>
  
  <%
String nomeContainer = "operatoriprivati";
String param = "altId="+AnagraficaDetails.getAltId();

%>

 <script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
  		<div align="right">
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<input type="button" title="Stampa Scheda" value="Stampa Scheda" onClick="openRichiestaPDFGestioneAnagrafica('<%= AnagraficaDetails.getAltId() %>', '35');">
</div>
  		
  		<br/><br/>
  		

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href=""><dhv:label name="">Anagrafica stabilimenti</dhv:label></a> >
<a href="OpuStab.do?command=SearchForm"><dhv:label name="">Ricerca</dhv:label></a> >
<a href="RicercaUnica.do?command=Search"><dhv:label name="">Risultato ricerca</dhv:label></a> >
Scheda Anagrafica Impresa
</td>
</tr>
</table>

  <dhv:container name="<%=nomeContainer %>" selected="Scheda" object="AnagraficaDetails" param="<%=param%>">
  

  <br/>
  
  <dhv:permission name="imbarcazioni-imbarcazioni-delete">
	<input type="button" value="Elimina" onClick="javascript:popURLReturn('Operatoriprivati.do?command=ConfirmDelete&id=<%=AnagraficaDetails.getAltId()%>&popup=true','Operatoriprivati.do?command=Search', 'Delete_account','320','200','yes','no');">
	<br/>
	</dhv:permission>
	  
  
  <dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%= (AnagraficaDetails.getAltId() - 140000000)  %>" />
<jsp:param name="riferimentoIdNomeTab" value="anagrafica.stabilimenti" />
</jsp:include> <br><br>
</dhv:permission>
  
  
  <jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=AnagraficaDetails.getAltId() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="altId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="stabilimenti" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include>
  
  <jsp:include page="../schede_centralizzate/iframe.jsp">
    <jsp:param name="objectId" value="<%=AnagraficaDetails.getAltId() %>" />
    <jsp:param name="objectIdName" value="alt_id" />
    <jsp:param name="tipo_dettaglio" value="35" />
     </jsp:include>
     
     <BR/>
     
     <dhv:permission name="operatoriprivati-edit">
      <center>
  	  <input type="button" value="MODIFICA STATO LINEA" onClick="loadModalWindow(); window.location.href='Operatoriprivati.do?command=Modify&altId=<%=AnagraficaDetails.getAltId() %>'"/>
      </center>
      </dhv:permission>
  
  
     </dhv:container>