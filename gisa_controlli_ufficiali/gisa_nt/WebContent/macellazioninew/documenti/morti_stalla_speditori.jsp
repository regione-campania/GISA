<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%@page import="org.aspcfs.modules.macellazioninew.utils.MacelliUtil"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />

<%@ include file="../../initPage.jsp"%>

	<head>
		<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
	</head>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Stabilimenti.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> >
			<%
				if (request.getParameter("return") == null)
				{
			%>
					<a href="Stabilimenti.do?command=Search"><dhv:label
					name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
					<a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getOrgId() %>">Scheda Stabilimento</a> >
			<%
				}
				else if (request.getParameter("return").equals("dashboard"))
				{
			%>
					<a href="Stabilimenti.do?command=Dashboard"><dhv:label
					name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
				}
			%>
			<a href="MacellazioniNew.do?command=List&orgId=<%=OrgDetails.getOrgId() %>">Macellazioni</a> > Anim. morti in stalla/trasporto
		</td>
	</tr>
</table>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>



<dhv:container 
	name="<%=(OrgDetails.isMacelloUngulati()) ? ("stabilimenti_macellazioni_ungulati") : ("stabilimenti") %>"
	selected="macellazioni" 
	object="OrgDetails" 
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
	hideContainer="<%= !OrgDetails.getEnabled() || OrgDetails.isTrashed() %>">

<br/>



<font color="red"> <%=toHtmlValue( (String)request.getAttribute( "messaggio" ) ) %> </font>
<br/><br/>

	<form name="macellazioniForm" action="MacellazioniDocumentiNew.do">
		<input type="hidden" name="command" value="SpeditoriMortiStalla" />
		<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>" />
		<input type="hidden" name="data" value="<%=request.getParameter( "data" ) %>" />
       <%=request.getAttribute( "tabella" )%>
    </form>
	    
	 <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
                location.href = 'MacellazioniDocumentiNew.do?command=SpeditoriMortiStalla&' + parameterString;
            }
    </script>
 
</dhv:container>

