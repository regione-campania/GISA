<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>
<%@page import="ext.aspcfs.modules.apicolture.actions.OperatoreAction"%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<SCRIPT src="javascript/apiari.js"></SCRIPT>
<jsp:useBean id="Operatore" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />



<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>


<script src="javascript/jquery.searchable-1.0.0.min.js"></script>
<!--   <link rel="stylesheet" type="text/css" media="screen" href="http://combogrid.justmybit.com/resources/css/smoothness/jquery-ui-1.10.1.custom.css"/> -->

  <link rel="stylesheet" type="text/css" media="screen" href="css/jquery.ui.combogrid.css"/>
  <script type="text/javascript" src="javascript/jquery.ui.combogrid.js"></script>
  
  
<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>

<style>
input[readonly="readonly"]
{
    border:0;
}


</style>
<script>

RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};



</script>

<body onload="resizeGlobalItemsPane('hide')">


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="ApicolturaAttivita.do?command=Home">ANAGRAFICA ATTIVITA</a> > 
RICERCA MOVIMENTAZIONI 
</td>
</tr>
</table>


<br>
<br>
<form name="searchmovimentazione" method="POST" action="ApicolturaMovimentazioni.do?command=Search">

<%=showError(request, "ErrorValidazioneError") %>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>RICERCA MOVIMENTAZIONI IN USCITA</strong>
    </th>
  </tr>

		
		
		
		<tr>
			<td nowrap class="formLabel">Codice Azienda</td>
			<td>
			<input type="text" name = "searchcodeCodiceAziendaOrigine" >
			
			</td>
		</tr>
		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td>
			<input type="text" name = "searchcodeDenominazioneAziendaOrigine" >
			
			</td>
		</tr>
		
		
		<tr>
			<td nowrap class="formLabel">Progressivo Azienda</td>
			<td>
			<input type="text" name = "searchcodeProgressivoApiarioOrigine" >
			
			
			</td>
		</tr>
		
		
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>
				<input type="text" name = "searchcodeCfProprietarioOrigine" >
					
				</td>
			</tr>
			
			
			  
			
			<tr>
				<td colspan="2" > <input type="submit" value = "Ricerca"> </td>
			</tr>
			
	</table>


</form>
	
	
<iframe src="empty.html" name="server_commands" id="server_commands"
		style="visibility: hidden" height="0"></iframe>
		</body>
