<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mu_wkf.base.*" %>
<%@page import="java.util.*"%> 
<jsp:useBean id="path" class="org.aspcfs.modules.mu_wkf.base.Path" scope="request"/>
<jsp:useBean id="capo" class="org.aspcfs.modules.mu.base.CapoUnivoco" scope="request" />
<jsp:useBean id="listaCapi" class="java.lang.String" scope="request"/>
  
  
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<!-- <script type="text/javascript" src="javascript/ui.tabs.js"></script> -->

<%@ include file="include.jsp" %>

<script>



</script>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MacellazioneUnica.do?command=List&orgId=<%=seduta.getIdMacello()%>">Home macellazioni </a> 
			> <a href="MacellazioneUnica.do?command=DettaglioSeduta&idSeduta=<%=dettaglioCapo.getIdSeduta()%>"> Dettaglio seduta </a> > Dettaglio macellazione
		</td>
	</tr>
</table>

<dhv:container name="stabilimenti_macellazioni_ungulati" selected="macellazioniuniche" object="OrgDetails" param="<%= param1 %>" >


 
 
 

<br/>

<%-- <input type="hidden" id="listaCapi" name="listaCapi" value="<%=listaCapi %>"/> --%>

<%
	ArrayList<Step> steps = path.getListaSteps();
	Iterator i = steps.iterator();
	while (i.hasNext()) {
		Step thisStep = (Step) i.next();
		String file_to_include = "/mu/operazioni_dettaglio/"+thisStep.getJspPageToInclude();
		
%>
<div>

<jsp:include page='<%=file_to_include %>'  flush="true"/> 

</div>
<br/>
<%
	}
%>


</dhv:container>