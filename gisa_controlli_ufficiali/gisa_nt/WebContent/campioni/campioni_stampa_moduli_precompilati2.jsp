<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>

<script language="javascript">
function openPopupModules(orgId, barcode, tipo, sizeA, sizeM, analiti, matrici, motivazione_campione, motivazione_piano_campione, ticketId) {

		window.open('PrintModulesHTML.do?command=ViewStampa&orgId='+orgId+'&barcodeId='+barcode+'&tipo='+tipo+'&sizeA='+sizeA+'&sizeM='+sizeM+analiti+matrici+'&motivazione_campione='+motivazione_campione+'&motivazione_piano_campione='+motivazione_piano_campione+'&ticketId='+ticketId,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
} 

</script>

<div align="right" style="padding-left: 210px; margin-bottom: 45px">
<%
	ArrayList<Analita> tipiCampioni = TicketDetails.getTipiCampioni();
	
	String descrizione = "";
	String analiti = "";
	int tipoAnalita = 0;
	for (int len = 0; len < tipiCampioni.size(); len++) {
		Analita a = tipiCampioni.get(len);
		descrizione += a.getDescrizione() + "---"; 
		int idA=a.getIdAnalita();
		analiti = analiti+"&IdA_"+len+"="+idA; 
	}
	int sizeA = tipiCampioni.size();
	
	HashMap<Integer,String> matrici= TicketDetails.getMatrici();
	Iterator<Integer> itMatrici = matrici.keySet().iterator();
	int i = -1 ;
	String mat = "";
	while(itMatrici.hasNext())
	{
		i++ ;
		int chiave = itMatrici.next(); 
		mat = mat+"&IdM_"+i+"="+chiave; 
		
	}
	int sizeM = i+1;
	
	int motivazione_campione = TicketDetails.getMotivazione_campione();
	int motivazione_piano_campione = TicketDetails.getMotivazione_piano_campione();
	
	String [] split = descrizione.split("---");
	for (int l=0;l<split.length;l++){
		if (split[l].contains("BATTERIOLOGICO")){
			tipoAnalita=2;
			break;
		}
		if (split[l].contains("CHIMICO")){
			tipoAnalita=3;
			break;
		}
	}
	if (tipoAnalita==2){
	%> <a
		href="javascript:openPopupModules('<%=OrgDetails.getOrgId()%>','<%=TicketDetails.getLocation()%>','<%=tipoAnalita%>','<%=sizeA %>','<%=sizeM %>','<%=analiti %>','<%=mat %>','<%=motivazione_campione %>','<%=motivazione_piano_campione %>','<%=TicketDetails.getId() %>' );">
		<font size="3px" color="#006699" style="font-weight: bold;"> Stampa Mod.2 Verbale Campione Batteriologico</font> </a> <%
 	} else if (tipoAnalita==3) {
 	%> <a
		href="javascript:openPopupModules('<%=OrgDetails.getOrgId()%>','<%=TicketDetails.getLocation()%>','<%=tipoAnalita%>','<%=sizeA %>','<%=sizeM %>','<%=analiti %>','<%=mat %>','<%=motivazione_campione %>','<%=motivazione_piano_campione %>','<%=TicketDetails.getId() %>' );">
		<font size="3px" color="#006699" style="font-weight: bold;"> Stampa Mod.3 Verbale Campione Chimico </font> </a> <%
 	}
 %>
</div>

<input type="hidden" name="tipoAnalita" id="tipoAnalita" value="<%=tipoAnalita%>" />