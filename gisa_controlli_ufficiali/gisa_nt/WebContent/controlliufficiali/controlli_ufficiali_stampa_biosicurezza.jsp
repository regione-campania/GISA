<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="javascript">

function openChk_bio(orgId,idControllo,url,specie){
	var res;
	var result;
		window.open('PrintModulesHTML.do?command=SchedaBiosicurezza&idControllo='+idControllo+'&orgId='+orgId+'&url='+url+'&specie='+specie,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function inviaChecklist_bio(idIstanza, idControllo) {
	window.open('GestioneInvioChecklist.do?command=InviaChecklistBiosicurezza&id='+idIstanza+'&idControllo='+idControllo,'popupSelect',
	'height=300px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}
</script>


<% 
int specieAllev = OrgDetails.getSpecieA();
boolean hasBioSicurezzaSuini = false;

for(Piano p :TicketDetails.getPianoMonitoraggio()) {
	if (PopolaCombo.hasEventoMotivoCU("isBioSicurezzaSuini", p.getId(), -1)){
		hasBioSicurezzaSuini = true;
		break;
	}
	}

%>


<%
if (specieAllev == 122 && hasBioSicurezzaSuini) {
	String statoGisa = toHtml(PopolaCombo.getInfoChecklistBiosicurezzaIstanza(TicketDetails.getId(), "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBiosicurezzaIstanza(TicketDetails.getId(), "bozza")).equals("f") ? "CHIUSA" : "";
	String idIstanza = toHtml(PopolaCombo.getInfoChecklistBiosicurezzaIstanza(TicketDetails.getId(), "id"));
	String dataInvio =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBiosicurezzaIstanza(TicketDetails.getId(), "data_invio"));
	String idEsitoClassyfarm = toHtml(PopolaCombo.getInfoChecklistBiosicurezzaIstanza(TicketDetails.getId(), "id_esito_classyfarm"));
	String descrizioneErroreClassyfarm =  toHtml(PopolaCombo.getInfoChecklistBiosicurezzaIstanza(TicketDetails.getId(), "descrizione_errore_classyfarm"));
	String descrizioneMessaggioClassyfarm =  toHtml(PopolaCombo.getInfoChecklistBiosicurezzaIstanza(TicketDetails.getId(), "descrizione_messaggio_classyfarm"));
	%>
	<div align="right" style="padding-left: 210px; margin-top: 35px">
	<table class="details" cellpadding="10" cellspacing="10" width="40%">
	<col width="30%">
	<tr><th colspan="2">
	<a href="javascript:openChk_bio('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','122');"> 
	<input type="button" value="Compila/Visualizza checklist di Biosicurezza per Suini"/>
	</a>
	</th></tr>	
	<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idEsitoClassyfarm == null || !idEsitoClassyfarm.equals("0")))  { %>
	<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist_bio('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
	<% } %>
	<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
	<tr <%="0".equals(idEsitoClassyfarm) ? " style= 'background: lime'" : " style= 'background: lightcoral'" %>><td class="formLabel">ESITO ULTIMO INVIO CLASSYFARM</td> <td><%= "0".equals(idEsitoClassyfarm) ? "CHECKLIST INSERITA" : "1".equals(idEsitoClassyfarm) ? "CHECKLIST INSERITA CON ERRORI" : "2".equals(idEsitoClassyfarm) ? "CHECKLIST NON INSERITA" : "" %></td></tr>
	<tr><td class="formLabel">DESCRIZIONE MESSAGGIO ULTIMO INVIO CLASSYFARM</td> <td><%= descrizioneMessaggioClassyfarm %></td></tr>
	<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO CLASSYFARM</td> <td><%= descrizioneErroreClassyfarm %></td></tr>
	<tr><td class="formLabel">DATA ULTIMO INVIO CLASSYFARM</td> <td><%=dataInvio %></td></tr>
	<tr><td colspan="2"></td></tr>
	</table>
	</div>	
				
<%   } %>