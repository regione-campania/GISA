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

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>


<script language="javascript">

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

	  url = url + '&idAsl=' + document.addPartita.idAslRiferimento.value;
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

function checkForm(form){

	formTest = true;
	message = "";
	
 	if (form.nrCertificato.value==""){
	    	message += label("", "- Campo nr certificato richiesto.\r\n");
 		formTest = false;
	 }

 	if (form.nrAnimaliPartita.value==""){
	    	message += label("", "- Campo nr animali richiesto.\r\n");
 		formTest = false;
	 }


 	if (form.idProprietario.value=="-1"){
	    	message += label("", "- Campo importatore richiesto.\r\n");
 		formTest = false;
	 }


 	


 	
if (formTest == false) {
alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
return false;
}else{

return true;
}


}
</script>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0"> 
<tr>
<td width="100%">
  <a href="PartiteCommerciali.do"><dhv:label name="circuito.commerciale">Circuito Commerciale</dhv:label></a> >
  <dhv:label name="circuito.commerciale.aggiungi">Aggiungi</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<body >
<%if (partita.getIdTipoPartita() == -1){ %> 
	<form name="addPartita" action="PartiteCommerciali.do?command=AggiungiPartita&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" method="post">


	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Informazioni partita</dhv:label></strong>
	  </th>
  </tr>
 
  <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Animali nella partita</dhv:label>
    </td>
    <td>

  	<%=specieList.getHtmlSelect("idTipoPartita", -1) %>
  		
  	</td>
  </tr>
  </table>
  <br>
  <input type="button" value="<dhv:label name="">Prosegui</dhv:label>" onClick="this.form.submit();" />
<%}else {%>
<form name="addPartita" action="PartiteCommerciali.do?command=InserisciPartita&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" method="post">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" >
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="">Informazioni partita</dhv:label></strong>
	  </th>
  </tr>
<tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Asl di riferimento</dhv:label>
    </td>
	<td>
	<%if (User.getSiteId() > -1){ %>
		<%=aslList.getSelectedValue(User.getSiteId()) %>
		<input type="hidden" value="<%=User.getSiteId() %>" name="idAslRiferimento" id="idAslRiferimento">
	<%}else{ %>
	<%=aslList.getHtmlSelect("idAslRiferimento", -1) %>
		<%} %>
	</td>
</tr>
<input type="hidden" id="idTipoPartita" name="idTipoPartita" value="<%=partita.getIdTipoPartita() %>"/>

<%if (User.getUserRecord().getIdImportatore()>0) 
{
%>
<input type="hidden" name="idProprietario" id="idProprietario" value="<%=User.getUserRecord().getIdImportatore() %>"/>
<%	
}
else
{
%>
 <tr>
    <td class="formLabel" nowrap>
      <dhv:label name="">Operatore commerciale</dhv:label>
    </td>
    <td>
    <dhv:evaluate if="<%= (partita != null && partita.getOperatoreCommerciale() != null && partita.getOperatoreCommerciale().getIdOperatore()>0) %>">
  
      <%=toHtml(partita.getOperatoreCommerciale().getRagioneSociale()) %>
 
   <input type="hidden" name="idProprietario" id="idProprietario" value="<%=(partita.getOperatoreCommerciale()!=null ) ?  ((LineaProduttiva)(((Stabilimento)partita.getOperatoreCommerciale().getListaStabilimenti().get(0)).getListaLineeProduttive().get(0))).getId()  : "" %>">

 </dhv:evaluate>
      
	<a href="javascript:popUp('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true&tipoRegistrazione=7&idLineaProduttiva1=4');">Ricerca</a>
 


 <dhv:evaluate if="<%= (partita.getOperatoreCommerciale() == null  || ( partita.getOperatoreCommerciale() != null &&  partita.getOperatoreCommerciale().getIdOperatore()<=0)) %>">
<input type="hidden" name="idProprietario" id="idProprietario" value="-1">
 </dhv:evaluate>
    
    </td>

  </tr>
  <%} %>
  
    <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Numero certificato</dhv:label>
    </td>
    <td>
    <input type="text" name="nrCertificato" id="nrCertificato" value="<%=partita.getNrCertificato() %>"> 
     <font color="red">*</font>
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
    <input type="text" name="nrAnimaliPartita" id="nrAnimaliPartita" value="<%=(partita.getNrAnimaliPartita() > 0)? partita.getNrAnimaliPartita() : "" %>"> 
     <font color="red">*</font>
    </td>

  </tr>
  
      <tr>
      <td class="formLabel" nowrap>
      <dhv:label name="">Nazione di provenienza</dhv:label>
    </td>
    <td>
	<%=nazioniList.getHtmlSelect("idNazioneProvenienza", -1) %>
    </td>

  </tr>
  
  
      <tr class="containerBody" >
    <td class="formLabel">
      <dhv:label name="">Data di arrivo previsto</dhv:label>
    </td>

    
    <td>	
  
      	<input  readonly type="text" name="dataArrivoPrevista" size="10" value="<%=toDateString(partita.getDataArrivoPrevista()) %>"  nomecampo="dataArrivoPrevista" labelcampo="Data arrivo previsto" />&nbsp;
        <a href="#" onClick="cal19.select(document.forms[0].dataArrivoPrevista,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>

      

    </td> 
  </tr>
  
 </table>
 </br>
<input type="button" value="<dhv:label name="">Prosegui</dhv:label>" onClick="if(checkForm(this.form)){this.form.submit()};" />
<% }%>


<input type="hidden" name="doContinue" id="doContinue" value="">

</form>