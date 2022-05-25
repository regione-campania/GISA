<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>HTML Table Filter Generator script | Examples - jsFiddle demo by koalyptus</title>
    <script type='text/javascript' src='/js/lib/dummy.js'></script>
  <link rel="stylesheet" type="text/css" href="/css/result-light.css">
<script type='text/javascript' src="javascript/tablefilter/tablefilter_all_min.js"></script>
<link rel="stylesheet" type="text/css" href="javascript/tablefilter/filtergrid.css">
</head>

<%@ include file="../../initPage.jsp" %>

<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />	
<%@page import="org.aspcfs.modules.mu.base.SedutaUnivoca"%>
<jsp:useBean id="listaSedute" class="java.util.Vector" scope="request"/>
<jsp:useBean id="specieList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoSeduta" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ page import="java.util.*" %>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

 <table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Stabilimenti.do?command=Details&orgId=<%=request.getParameter("orgId")%>">Home macellazioni </a> > Lista Sedute
		</td>
	</tr>
</table>

<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>
<dhv:container name="stabilimenti_macellazioni_ungulati" selected="macellazioniuniche" object="OrgDetails" param="<%= param1 %>" >


<input type="button" name="ricerca" id="ricerca" value="RICERCA" onclick="location.href='MacellazioneUnica.do?command=Ricerca&orgId=<%=OrgDetails.getId()%>';" />
<input type="button" name="listaSedute" id="listaSedute" value="LISTA SEDUTE" onclick="location.href='MacellazioneUnica.do?command=List&orgId=<%=OrgDetails.getId()%>';" />
<input type="button" name="prepareNuovaSeduta" id="prepareNuovaSeduta" value="AGGIUNGI SEDUTA" onclick="location.href='MacellazioneUnica.do?command=NuovaSeduta&orgId=<%=OrgDetails.getId()%>';" />
<input type="button" name="listaPartite" id="listaPartite" value="LISTA PARTITE" onclick="location.href='MacellazioneUnica.do?command=CercaPartita&orgId=<%=OrgDetails.getId()%>';" />
<input type="button" name="nuovaPartita" id="nuovaPartita" value="AGGIUNGI PARTITA" onclick="location.href='MacellazioneUnica.do?command=NuovaPartita&orgId=<%=OrgDetails.getId()%>';" />
<%-- <input type="button" name="gestioneEsercenti" id="gestioneEsercenti" value="GESTIONE ESERCENTI" onclick="window.open('MacellazioneUnica.do?command=GestisciEsercenti&idMacello=<%=OrgDetails.getId()%>', 'titolo', 'width=400, height=200, resizable, status, scrollbars=1, location');" /> --%> 
<br/>




<table width="100%"><tr bgcolor="#b2b2ff"><td align="center"> <b>Lista sedute </b> </td></tr></table>


<table id="table2" cellpadding="0" cellspacing="0"  width="100%">
    <tr>
    		<th width="15">Alert scadenza</th>
			<th>Data della seduta</th>
			<th>Numero della seduta</th>
			<th>Capi</th>
			<th>Stato</th>
			<th>Operazioni</th>
			
		</tr>		
   <%
	
	if (listaSedute.size()>0){
		for (int i=0;i<listaSedute.size(); i++){
			SedutaUnivoca seduta = (SedutaUnivoca) listaSedute.get(i); 
			if (seduta.getListaCapi().size()>0) {
			%>
			
			
			<%
			String color = "";
			if (seduta.getIdStato() != SedutaUnivoca.idStatoMacellato && seduta.getGiorniAperturaSeduta() > 5){
				
				color = "style=\"background-color:rgba(255,0,0,0.5);\"";
				}else  if (seduta.getIdStato() != SedutaUnivoca.idStatoMacellato && seduta.getGiorniAperturaSeduta() < 5 && seduta.getGiorniAperturaSeduta() >3 ){
					color = "style=\"background-color:rgba(255,255,0,0.5);\"";
					}%>
				
			
			<tr <%=color %>>
						<td style="text-align:center;"  width="15">
			<dhv:evaluate if="<%=(seduta.getIdStato() != SedutaUnivoca.idStatoMacellato && seduta.getGiorniAperturaSeduta() > 5) %>">
			<img width="15" height="15" src="images/alert.gif"/>
			</dhv:evaluate>
			</td>
			<td><%=toDateasString(seduta.getData()) %> &nbsp;  </td>
			<td><%=seduta.getNumeroSeduta() %> &nbsp;  </td>
		
		<td>
			<%
			HashMap<Integer, Integer> grigliaSpecie= seduta.getListaCapiNumeri();
			Iterator<Integer> itSpecie = grigliaSpecie.keySet().iterator();
			String numericapi = "";
			while(itSpecie.hasNext())
			{
				
			int specie = itSpecie.next(); 
			int num = grigliaSpecie.get(specie); 
			
			numericapi = numericapi+specieList.getSelectedValue(specie)+": "+num+"; ";
			} %> 
			<%=numericapi %> &nbsp;</td>
			<td><%=statoSeduta.getSelectedValue(seduta.getIdStato()) %></td>
			<td><a href="MacellazioneUnica.do?command=DettaglioSeduta&idSeduta=<%=seduta.getId()%>">Dettaglio</a>
			<dhv:evaluate if="<%=seduta.getIdStato() != SedutaUnivoca.idStatoMacellato %>">
			<a href="MacellazioneUnica.do?command=MacellaSeduta&idSeduta=<%=seduta.getId()%>">Procedi</a>
			</dhv:evaluate>
			</td>

		
			</tr>

<% } }
%>
	
	
	<% } else {%>
		<tr><td colspan="8"> Non sono state trovate sedute. &nbsp; </td></tr>
		
		<% } %>
</table>
  
  <br/><br/>
  </dhv:container>  
<script type='text/javascript'>//<![CDATA[ 

var table2_Props = {
	col_0: "select",
	col_1: "select",
	col_2: "none",
	col_3: "none",
	display_all_text: " [ Mostra tutte ] ",
    sort_select: true
};
var tf2 = setFilterGrid("table2", table2_Props);
//]]>  

</script>


</script>