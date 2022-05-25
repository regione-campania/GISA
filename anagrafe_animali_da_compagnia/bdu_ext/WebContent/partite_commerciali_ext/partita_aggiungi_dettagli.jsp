<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.io.*,java.util.*,org.aspcfs.utils.web.*" %>
<%@page import="org.aspcfs.modules.opu_ext.base.Stabilimento"%>
<%@page import="org.aspcfs.modules.opu_ext.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.anagrafe_animali_ext.base.*"%>


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="partita" class="org.aspcfs.modules.partitecommerciali_ext.base.PartitaCommerciale" scope="request"/>

<%@ include file="../initPage.jsp" %>
<% String param1 = "idPartita=" + partita.getIdPartitaCommerciale() +"&idSpecie=" + partita.getIdTipoPartita();
   %>
   
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

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
<input type="button" value="<dhv:label name="">Aggiorna dettagli</dhv:label>" onClick="this.form.submit();" />
</form>
</dhv:container>
</br>

</form>