<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*" %>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.*"%>


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="partita" class="org.aspcfs.modules.partitecommerciali.base.PartitaCommerciale" scope="request"/>

<%@ include file="../initPage.jsp" %>
<% String param1 = "idPartita=" + partita.getIdPartitaCommerciale() +"&idSpecie=" + partita.getIdTipoPartita();
   %>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0"> 
<tr>
<td width="100%">
  <a href="PartiteCommerciali.do"><dhv:label name="circuito.commerciale">Circuito Commerciale</dhv:label></a> >
  <dhv:label name="circuito.commerciale.dettagli">Dettagli partita</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<body >
<dhv:container name="partita" selected="details" object="partita" param="<%=param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
<form>
          <dhv:permission name="partite-commerciali-edit">
            <input type="button" value="<dhv:label name="">Inserisci Dettagli</dhv:label>" onClick="javascript:window.location.href='PartiteCommerciali.do?command=InserisciDettagliPartita&idPartita=<%= partita.getIdPartitaCommerciale() %>'">
          </dhv:permission>
</br></br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Dettagli partita (Prenotifica)</dhv:label></strong>
	  </th>
  </tr>
<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Asl di riferimento</dhv:label>
    </td>
	<td>
	<%=aslList.getSelectedValue(partita.getIdAslRiferimento()) %>
	</td>
</tr>
<input type="hidden" id="idTipoPartita" name="idTipoPartita" value="<%=partita.getIdTipoPartita() %>"/>
 <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Operatore commerciale</dhv:label>
    </td>
    <td>
    
    <dhv:evaluate if="<%= (partita != null && partita.getOperatoreCommerciale() != null && partita.getOperatoreCommerciale().getIdOperatore()>0) %>">
  
      <%=toHtml(partita.getOperatoreCommerciale().getRagioneSociale()) %>
 
	</dhv:evaluate>
      
	    
    </td>

  </tr>
    <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Numero certificato</dhv:label>
    </td>
    <td>
   <%=partita.getNrCertificato() %>
    </td>

  </tr>
<%
String label = "Numero animali";
if (partita.getIdTipoPartita() == Gatto.idSpecie) {
	label = "Numero Gatti";
}else if (partita.getIdTipoPartita() == Cane.idSpecie) {
	label = "Numero Cani";
}

%>
    <tr>
      <td class="formLabel" nowrap>
      <dhv:label name=""><%=label%></dhv:label>
    </td>
    <td>
   <%=partita.getNrAnimaliPartita() %>
    </td>

  </tr>
  
      <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Nazione di provenienza</dhv:label>
    </td>
    
    <td>
	<%=nazioniList.getSelectedValue(partita.getIdNazioneProvenienza()) %>
    </td>

  </tr>
  
  
  <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Data di arrivo previsto</dhv:label>
    </td>

    
    <td>      
		<%=toDateasString(partita.getDataArrivoPrevista()) %>
    </td> 
  </tr>
</table>

</br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Dettagli partita (Veterinario Asl)</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Data di arrivo effettiva</dhv:label>
    </td>
    <td>
    <dhv:evaluate if="<%=(partita.getDataArrivoEffettiva() != null) %>">
   	 <%=toDateasString(partita.getDataArrivoEffettiva()) %>
    </dhv:evaluate>
    </td>
  </tr>
    <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Vincolo sanitario</dhv:label>
    </td>
    <td>
    <% if (partita.isFlagPresenzaVincoloSanitario() ){%>
    <img src="images/check/check.JPG">
    <%}else{ %>   
   <img src="images/check/nocheck.JPG">
    <%} %>
    
	<select multiple name="animaliSelezionati" id="animaliSelezionati" size="5">
		<dhv:evaluate if="<%= (partita.getListMicrochipAnimaliConVincolo() == null || partita.getListMicrochipAnimaliConVincolo().size() <= 0) %>">
		      		<option value="-1"><dhv:label name="">Nessuno selezionato</dhv:label></option>
		      	  </dhv:evaluate>
		      	  <dhv:evaluate if="<%= (partita.getListMicrochipAnimaliConVincolo() != null && 
		      			  partita.getListMicrochipAnimaliConVincolo().size() > 0) %>">
					<%
		        	  ArrayList<String> c = partita.getListMicrochipAnimaliConVincolo();
		        	  for ( int j=0; j<c.size(); j++) {
		          		String microchip = (String) c.get(j);
					%>
		        		<option value="<%= microchip %>"><%= microchip %></option>
		      		<%}%>
		</dhv:evaluate>
	</select>
	&nbsp;
	Animali Vincolati n. <font color="red"> <%=(partita.getListMicrochipAnimaliConVincolo() == null)? "0" : partita.getListMicrochipAnimaliConVincolo().size() %> </font>
    
    </td>
  </tr>
</table>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Controlli richiesti</dhv:label></strong>
	  </th>
  </tr>
  
  
   <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Controllo documentale</dhv:label>
    </td>
    
    <td>
    <% if (partita.isFlagControlloDocumentaleRichiesto() ){%>
    <img src="images/check/check.JPG">
    <%}else{ %>   
   <img src="images/check/nocheck.JPG">
    <%} %>   
    </td>
  </tr>
  
     <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Controllo d'identità</dhv:label>
    </td>
    
    <td>
    <% if (partita.isFlagControlloIdentitaRichiesto() ){%>
    <img src="images/check/check.JPG">
    <%}else{ %>   
   <img src="images/check/nocheck.JPG">
    <%} %>   
    </td>
  </tr>
  
   <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Controllo fisico</dhv:label>
    </td>
    
    <td>
    <% if (partita.isFlagControlloFisicoRichiesto() ){%>
    <img src="images/check/check.JPG">
    <%}else{ %>   
   <img src="images/check/nocheck.JPG">
    <%} %>   
    </td>
  </tr>
  
       <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Controllo di laboratorio (anticorpale rabbia)</dhv:label>
    </td>
    
    <td>
    <% if (partita.isFlagControlloLaboratorioRichiesto() ){%>
    <img src="images/check/check.JPG">
    <%}else{ %>   
   <img src="images/check/nocheck.JPG">
    <%} %>   
    </td>
  </tr>
  
</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Informazioni di inserimento/modifica</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Inserito</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= partita.getIdUtenteInserimento() %>" />
      <zeroio:tz timestamp="<%= partita.getDataInserimento()%>" />
    </td>
  </tr>
    <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Modificato</dhv:label>
    </td>
    <td>
      <dhv:username id="<%= partita.getIdUtenteModifica() %>" />
      <zeroio:tz timestamp="<%= partita.getDataModifica()%>" />
    </td>
  </tr>
</table>

</form>
</dhv:container>