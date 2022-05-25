<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.ConfigTipo"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Partita"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />
<jsp:useBean id="Partita" class="org.aspcfs.modules.macellazioninew.base.Partita" scope="request" />
<jsp:useBean id="partite" class="java.util.ArrayList" scope="request" />
<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>
<script type="text/javascript">

function gestisciCampoDataMacellazione(){
	var select = document.getElementById('comboDateMacellazione'); //Recupero la SELECT

	 var NewOpt = document.createElement('option');
	 NewOpt.value=document.getElementById('campoDataMacellazione').value;
	 NewOpt.text=document.getElementById('campoDataMacellazione').value;
	 NewOpt.selected = true;
	 try{
	 select.add(NewOpt, null); //Metodo Standard, non funziona con IE
	 }
	 catch(e){
		 select.add(NewOpt); 
	 }
	//document.getElementById('campoDataMacellazione').disabled = true;
	 
	//document.getElementById('campoDataMacellazione').style.display = "";
	//document.getElementById('comboDateMacellazione').disabled = true;
	//document.getElementById('comboDateMacellazione').style.display = "none";
}
		
</script>
<!-- ******************************************************************** -->

	<head>
		<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
		
	</head>

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
			<a href="MacellazioniNew.do?command=List&orgId=<%=OrgDetails.getOrgId()%>">Macellazioni</a> > Lista Sedute della partita <%=Partita.getCd_partita() %>
		</td>
	</tr>
</table>
<%
ConfigTipo configTipo = (ConfigTipo)request.getSession().getAttribute("configTipo");
%>


<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="../initPage.jsp"%>

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

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNew.do?command=ToRegistroMacellazioni&<%=param1 %>"><input type="button" value="Registro Macellazioni"/></a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<!-- a href="MacellazioniDocumentiNew.do?command=ToMod10&<%=param1 %>"><input type="button" value="Modello 10"/></a -->
</dhv:permission>

<%if(ApplicationProperties.getProperty("visibilita_link_macelli").equals("si")){ %>
<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNew.do?command=ToMortiStalla&<%=param1 %>">Anim. morti in stalla/trasporto</a>
</dhv:permission>



<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNew.do?command=ToBSE&<%=param1 %>">Modulo BSE</a>
</dhv:permission>



<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniDocumentiNew.do?command=ToAbbattimento&<%=param1 %>">Abbattimento</a>
</dhv:permission>


<br/><br/>


<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintBRCRilevazioneMacelli&file=BRC_rilevazione_macelli.xml&<%=param1 %>">BRC rilevazione macelli</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintTBCRilevazioneMacelli&file=TBC_rilevazione_macelli.xml&<%=param1 %>">TBC rilevazione macelli</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=ToModelloIdatidosi&<%=param1 %>">Modello idatidosi</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintModelloMarchi&file=modello_marchi.xml&<%=param1 %>">Modello marchi</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintMacellazioneAnimaliInfetti&file=macellazione_animali_infetti.xml&<%=param1 %>">Macellazione animali infetti</a>
</dhv:permission>

<dhv:permission name="stabilimenti-stabilimenti-macellazioni-view">
	<a href="MacellazioniNew.do?command=PrintDisinfezioneMezziTrasporto&file=disinfezione_mezzi_di_trasporto.xml&<%=param1 %>">Disinfezione mezzi di trasporto</a>
</dhv:permission>
<%} %>


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><br/><%=toHtmlValue( (String)request.getAttribute( "messaggio" ) ) %></font>

 
<br/>
<%
	if(!partite.isEmpty())
	{
		String specie1 = "Ovini";
		String specie2 = "Caprini";
		if (Partita.isSpecie_suina()){ 
			specie1="Cinghiali";
			specie2 = "Suini";
		}
%>
		Lista sedute macellazioni partita <%=Partita.getCd_partita() %><br/>
		
		Numero capi <%=specie1 %> partita: <%=Partita.getCd_num_capi_ovini() %><br/>
		
		Numero capi <%=specie2 %> partita: <%=Partita.getCd_num_capi_caprini() %>
		
		<form name="macellazioniForm" method="post" action="MacellazioniNew.do?command=List">
			<input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId() %>" />
	       <%=request.getAttribute( "tabella" )%>
	    </form>
<%
	}
	else
	{
		out.println("Nessuna seduta di macellazione aggiunta alla partita " + Partita.getCd_partita());
	}
%>	 
	    
	 <script type="text/javascript">
            function onInvokeAction(id) {
                $.jmesa.setExportToLimit(id, '');
                $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
            }
            function onInvokeExportAction(id) {
                var parameterString = $.jmesa.createParameterStringForLimit(id);
                alert(parameterString);
                //location.href = 'MacellazioniNew.do?command=List&' + parameterString;
            }
    </script>
 
</dhv:container>