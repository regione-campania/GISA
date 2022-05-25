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
   
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
  <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();


function popUp(url) {

	//Controllare asl ma con anagrafica centralizzata non sappiamo come fare
/**	if (document.addAnimale.idAslRiferimento.value == "-1"){
		alert ("Selezionare Asl di riferimento");
		return;
	}*/
	  title  = '_types';
	  width  =  '500';
	  height =  '600';
	  resize =  'yes';
	  bars   =  'no';

	 
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var windowParams = 'scrollbars=yes ,WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	  var newwin=window.open(url, title, windowParams);
	  newwin.focus();

	  if (newwin != null) {
	    if (newwin.opener == null)
	      newwin.opener = self;
	  }
	}</script>

<script>
function selezionaTutti(){
	var selectBox = document.getElementById("listMicrochipAnimaliConVincolo");
	for (var i = 0; i < selectBox.options.length; i++)  
        selectBox.options[i].selected = true;
	 document.forms[0].submit();
}
</SCRIPT>

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
<form name="modificaPartita" action="PartiteCommerciali.do?command=ModificaPartita&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" method="post">
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
	<input type="hidden" name="idAslRiferimento" id="idAslRiferimento" value="<%=partita.getIdAslRiferimento() %>">
	<input type="hidden" name="idPartitaCommerciale" id="idPartitaCommerciale" value="<%=partita.getIdPartitaCommerciale() %>">
	<input type="hidden" name="doContinue" value=""> 
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
 	<input type="hidden" name="idImportatore" id="idImportatore" value="<%=partita.getIdImportatore() %>"/>
	</dhv:evaluate>
      
	    
    </td>

  </tr>
    <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Numero certificato</dhv:label>
    </td>
    <td>
   <%=partita.getNrCertificato() %>
   
   <input type="hidden" value="<%=partita.getNrCertificato() %>" name="nrCertificato" id="nrCertificato" />
    </td>

  </tr>
<%
String label = "Numero cani";
if (partita.getIdTipoPartita() == Gatto.idSpecie) {
	label = "Numero Gatti";
}

%>
    <tr>
      <td class="formLabel" nowrap>
      <dhv:label name=""><%=label%></dhv:label>
    </td>
    <td>
   <%=partita.getNrAnimaliPartita() %>
   <input type="hidden" value="<%=partita.getNrAnimaliPartita() %>" name="nrAnimaliPartita" id="nrAnimaliPartita" />
    </td>

  </tr>
  
      <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Nazione di provenienza</dhv:label>
    </td>
    
    <td>
	<%=nazioniList.getSelectedValue(partita.getIdNazioneProvenienza()) %>
	 <input type="hidden" value="<%=partita.getIdNazioneProvenienza() %>" name="idNazioneProvenienza" id="idNazioneProvenienza" />
    </td>

  </tr>
  
  
  <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Data di arrivo previsto</dhv:label>
    </td>

    
    <td>      
		<%=toDateasString(partita.getDataArrivoPrevista()) %>
    </td> 
     <input type="hidden" value="<%=partita.getDataArrivoPrevista() %>" name="dataArrivoPrevista" id="dataArrivoPrevista" />
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
 	
  
      	<input  readonly type="text" name="dataArrivoEffettiva" size="10" value="<%=toDateString(partita.getDataArrivoEffettiva()) %>"  nomecampo="dataArrivoEffettiva" labelcampo="Data arrivo effettiva" />&nbsp;
        <a href="#" onClick="cal19.select(document.forms[0].dataArrivoEffettiva,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>

      

    
    </td>
  </tr>
    <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Vincolo sanitario</dhv:label>
    </td>
    <td>
<input type="checkbox" name="flagPresenzaVincoloSanitario" <%=(partita.isFlagPresenzaVincoloSanitario()? "checked" : "" )%> id="flagPresenzaVincoloSanitario" >
    
	<select multiple name="listMicrochipAnimaliConVincolo" id="listMicrochipAnimaliConVincolo" size="5">
		<dhv:evaluate if="<%= (partita.getListMicrochipAnimaliConVincolo() == null || partita.getListMicrochipAnimaliConVincolo().size() <= 0) %>">
		      	
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
	Cani Vincolati n. <font color="red"> <%=(partita.getListMicrochipAnimaliConVincolo() == null)? "0" : partita.getListMicrochipAnimaliConVincolo().size() %> </font>
      	<a href="javascript:popUp('PartiteCommerciali.do?command=ListaAnimali&idPartita=<%=partita.getIdPartitaCommerciale() %>&popup=true');">Seleziona</a>
      <tr><td> 

  </td></tr>
    </td>
  </tr>
</table>

</br>

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
	<input type="checkbox" name="flagControlloDocumentaleRichiesto" <%=(partita.isFlagControlloDocumentaleRichiesto()? "checked" : "")%> id="flagControlloDocumentaleRichiesto" >
    </td>
  </tr>
  
     <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Controllo d'identità</dhv:label>
    </td>
    
    <td>
    <input type="checkbox" name="flagControlloIdentitaRichiesto" <%=(partita.isFlagControlloIdentitaRichiesto()? "checked" : "") %> id="flagControlloIdentitaRichiesto" >  
    </td>
  </tr>
  
   <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Controllo fisico</dhv:label>
    </td>
    
    <td>
<input type="checkbox" name="flagControlloFisicoRichiesto" <%=(partita.isFlagControlloFisicoRichiesto()? "checked" : "") %> id="flagControlloFisicoRichiesto" >   
    </td>
  </tr>
  
       <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Controllo di laboratorio (anticorpale rabbia)</dhv:label>
    </td>
    
    <td>
<input type="checkbox" name="flagControlloLaboratorioRichiesto" <%=(partita.isFlagControlloLaboratorioRichiesto()? "checked" : "") %> id="flagControlloLaboratorioRichiesto" >   
    </td>
  </tr>
  
</table>

</br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Dettagli addizionali</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Note speciali</dhv:label>
    </td>
    <td>
	<textarea ROWS="3" COLS="50" name="noteSpeciali" id="noteSpeciali"></textarea>
    </td>
  </tr>
</table>
</br>
<input type="button" value="<dhv:label name="">Aggiorna dettagli</dhv:label>" onClick="javascript:selezionaTutti();" />
</form>
</dhv:container>
</br>

</form>