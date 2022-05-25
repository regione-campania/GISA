<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.soa.base.*,org.aspcfs.modules.contributo.base.*,org.aspcfs.modules.audit.base.*,org.aspcfs.utils.web.*,org.aspcfs.utils.*" %>
<%@page import="org.postgresql.jdbc2.TimestampUtils"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.soa.base.Organization" scope="request"/>
<jsp:useBean id="ContributoList" class="org.aspcfs.modules.contributo.base.ContributoList" scope="request"/>
<jsp:useBean id="ContributoListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="Specie" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<head>
	<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
	<script type="text/javascript" src="javascript/jquery-1.3.min.js"></script>
	<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
	<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
	<script type="text/javascript" src="javascript/jmesa.js"></script>
</head>

<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  

  function checkForm( form )
  {
	if( form.data.value == '' || form.id_specie.value == "-1" )
	{
		alert( "Selezionare una Data ed una Specie" );
		return false;
	}
	else
	{
		return confirm('Sicuro di voler aggiungere l\'elemento al diario?');
	}
  };
</script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Soa.do"><dhv:label name="soa.soa">Accounts</dhv:label></a> > 
			<a href="Soa.do?command=Search"><dhv:label name="soa.SearchResults">Search Results</dhv:label></a> >
			<a href="Soa.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="soa.details">Account Details</dhv:label></a> >
			Diario di Macellazione
		</td>
	</tr>
</table>

<dhv:container name="soa" selected="diario_macellazione" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' style="sidetabs" >
	
	<dhv:evaluate if="<%= !OrgDetails.isTrashed() %>">
		<dhv:permission name="soa-soa-diario-add">
		
			<form name="newDiario" action="SoaDiarioMacellazione.do?command=Add" method="post" onsubmit="javascript:return checkForm(this);">
				<fieldset>
					<legend>Aggiungi</legend>
					<table >
						<tr>
							<td>
								data:<zeroio:dateSelect field="data" form="newDiario" showTimeZone="false" />
								specie:<%=Specie.getHtmlSelect( "id_specie", "-1" ) %>
								<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>" />
								<input type="submit" value="Aggiungi">
							</td>
						</tr>
					</table>
				</fieldset>
			</form>
		
		</dhv:permission>
	</dhv:evaluate>

	<p>
		<font color="red" ><%=toHtmlValue( (String)request.getAttribute( "messaggio" ) ) %></font>
	</p>
		
	<form name="diarioForm" action="SoaDiarioMacellazione.do?command=List">
	<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>">
		<%=request.getAttribute( "tabella" )%>
	</form>
	
	<script type="text/javascript">
		function onInvokeAction(id) {
		    $.jmesa.setExportToLimit(id, '');
		    $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function onInvokeExportAction(id) {
		    var parameterString = $.jmesa.createParameterStringForLimit(id);
		    location.href = 'SoaDiarioMacellazione.do?command=List&orgId=<%=OrgDetails.getOrgId() %>&' + parameterString;
		}
	</script>
  
</dhv:container>








