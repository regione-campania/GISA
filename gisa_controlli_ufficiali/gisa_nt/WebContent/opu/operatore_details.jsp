<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*,java.util.HashMap,java.util.Map"%>
<%@page import="org.aspcfs.modules.opu.base.ComuniAnagrafica"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="mapAttributi" class="java.util.HashMap" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="OperatoreDettagli" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="nome_entered" class="java.lang.String" scope="request" />
<jsp:useBean id="nome_modified" class="java.lang.String" scope="request" />

<%@ include file="../initPage.jsp"%>


<script>

function setOperatorePopUp(idOperatore)
{
	if (window.opener.document != null)
	{
		window.opener.document.getElementById('id_operatore').value = idOperatore;
		window.opener.document.getElementById('idOperatore').value=idOperatore;
		window.opener.document.forms[0].doContinueStab.value = 'false';
		
		window.opener.document.forms[0].submit();
		window.opener.loadModalWindowCustom('Attendere caricamento dati Impresa in aggiungi stabilimento');
		window.close();
		
	}
	
}

</script>


<dhv:evaluate if="<%=!isPopup(request)%>">
	<table class="trails" cellspacing="0">
		<tr>
			<td>Impresa -> Scheda Impresa </td>
		</tr>
	</table>
</dhv:evaluate>

<div align="right"><input type="button" value="STAMPA SCHEDA" onClick="alert('Da implementare')"/></div>

<dhv:container name="anagrafica" selected="operatore" object="OperatoreDettagli" param="<%="stabId=-1&opId="+OperatoreDettagli.getIdOperatore()%>" appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>

<dhv:evaluate if="<%=!isPopup(request)%>">
	<!-- PULSANTE MODIFICA IMPRESA -->
</dhv:evaluate>
<br/>
	<sc:namecontext></sc:namecontext>
	<sc:context id="opu;gisa_nt">
		<%@ include file="operatore_details_generic.jsp"%>
	</sc:context>
<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
	<tr>
		<th colspan="2">Altre informazioni </th>
	</tr>
    
    <tr class="">
      	<td>
       		<strong>Inserito</strong>
      	</td>
      	<td nowrap>
        	<dhv:username id="<%= OperatoreDettagli.getEnteredBy() %>" /> il 	<%=toDateasString(OperatoreDettagli.getEntered()) %>&nbsp;
      	</td>
     </tr>

    <tr class="">
      	<td>
       		<strong>Modificato</strong>
      	</td>
     
      	<td nowrap>
        	<dhv:username id="<%= OperatoreDettagli.getModifiedBy() %>" /> il 	<%=(OperatoreDettagli.getModified() != null)? toDateasString(OperatoreDettagli.getModified()) : "" %>&nbsp;
      	</td>
	</tr>
</table>

</dhv:container>

<script>
setOperatorePopUp(<%=OperatoreDettagli.getIdOperatore() %>);

</script>
