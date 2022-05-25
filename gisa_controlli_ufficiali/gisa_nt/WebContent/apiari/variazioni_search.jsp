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
<jsp:useBean id="StabilimentoDetails" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />


<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>


<script>

RegExp.escape = function(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
}
String.prototype.replaceAll = function(search, replace) {
    return this.replace(new RegExp(RegExp.escape(search),'g'), replace);
};


</script>

<body onload="">


<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
apicoltura > MOVIMENTAZIONI
</td>
</tr>
</table>



<%

String param = "stabId="+StabilimentoDetails.getIdStabilimento()+"&opId=" 
		+ StabilimentoDetails.getIdOperatore()+"&searchcodeidApiario="+StabilimentoDetails.getIdStabilimento()+"&searchcodeidAzienda="+StabilimentoDetails.getIdOperatore() +"&searchcodeCodiceAziendaSearch="+StabilimentoDetails.getOperatore().getCodiceAzienda()+"&searchcodeProgressivoApiarioSearch="+StabilimentoDetails.getProgressivoBDA() ;

%>

<dhv:container name="apiari" selected="Scheda"
	object="Operatore" param="<%=param%>" hideContainer="false">
<br>
<br>
<form name="searchmovimentazione" method="POST" action="ApicolturaApiari.do?command=SearchVariazioni">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		 <tr>
    <th colspan="2">
      <strong>RICERCA MOVIMENTAZIONI</strong>
    </th>
  </tr>

		
		<tr>
			<td nowrap class="formLabel">Tipo di Variazione</td>
			<td>
			UBICAZIONE <input type="radio" required="required"  name="idTipoVariazione" id = "tipoMovimentazione1" value="1" >  
			CENSIMENTI <input type="radio" required="required"  name="idTipoVariazione" id = "tipoMovimentazione2" value="2" >  
			DETENTORE <input type="radio" required="required"  name="idTipoVariazione" id = "tipoMovimentazion3" value="3" >
			
			</td>
		</tr>
		
		
		
		<tr>
			<td nowrap class="formLabel">Codice</td>
			<td>
			<%=toHtml2(StabilimentoDetails.getOperatore().getCodiceAzienda()) %>
			
			</td>
		</tr>
		
		<tr>
			<td nowrap class="formLabel">Denominazione</td>
			<td>
			<%=StabilimentoDetails.getOperatore().getRagioneSociale() %>
			
			</td>
		</tr>
		
		
			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td>
				<%=StabilimentoDetails.getOperatore().getRappLegale().getCodFiscale() %>
					
				</td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome) </td>
				<td>
				<%=StabilimentoDetails.getOperatore().getRappLegale().getCognome() +" "+ StabilimentoDetails.getOperatore().getRappLegale().getNome() %>
				</td>

			</tr>
			
			<tr>
				<td nowrap class="formLabel">Progressivo</td>
				<td>
				<%=StabilimentoDetails.getProgressivoBDA() %>
				</td>

			</tr>
			
			
			<tr>
				<td nowrap class="formLabel">Apiario</td>
				<td>
				<input type="hidden"  id="idApiario"  name="searchcodeidApiario"   value="<%=StabilimentoDetails.getIdStabilimento()%>">
				<%=StabilimentoDetails.getSedeOperativa().getDescrizioneComune() %>
				</td>

			</tr>

		 
	
		  
			
			<tr>
				<td colspan="2" > <input type="submit" value = "Ricerca"> </td>
			</tr>
			
	</table>


</form>
</dhv:container>
	
<iframe src="empty.html" name="server_commands" id="server_commands"
		style="visibility: hidden" height="0"></iframe>
		</body>
