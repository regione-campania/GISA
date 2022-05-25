<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script language="javascript">

function openChk_farmaco(orgId,idControllo,url){
	var res;
	var result;
		window.open('PrintModulesHTML.do?command=SchedaFarmacosorveglianza&idControllo='+idControllo+'&orgId='+orgId+'&url='+url,'popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function inviaChecklist_farmaco(idIstanza, idControllo) {
	window.open('GestioneInvioChecklist.do?command=InviaChecklistFarmacosorveglianza&id='+idIstanza+'&idControllo='+idControllo,'popupSelect',
	'height=300px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}
</script>


<% 
boolean hasFarmacosorveglianza = false;

for(Piano p :TicketDetails.getPianoMonitoraggio()) {
	if (PopolaCombo.hasEventoMotivoCU("isFarmacosorveglianza", p.getId(), -1)){
		hasFarmacosorveglianza = true;
		break;
	}
	}
%>


<%
if (hasFarmacosorveglianza) {
	String statoGisa = toHtml(PopolaCombo.getInfoChecklistFarmacosorveglianzaIstanza(TicketDetails.getId(), "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistFarmacosorveglianzaIstanza(TicketDetails.getId(), "bozza")).equals("f") ? "CHIUSA" : "";
	String idIstanza = toHtml(PopolaCombo.getInfoChecklistFarmacosorveglianzaIstanza(TicketDetails.getId(), "id"));
	String dataInvio =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistFarmacosorveglianzaIstanza(TicketDetails.getId(), "data_invio"));
	String idEsitoClassyfarm = toHtml(PopolaCombo.getInfoChecklistFarmacosorveglianzaIstanza(TicketDetails.getId(), "id_esito_classyfarm"));
	String descrizioneErroreClassyfarm =  toHtml(PopolaCombo.getInfoChecklistFarmacosorveglianzaIstanza(TicketDetails.getId(), "descrizione_errore_classyfarm"));
	String descrizioneMessaggioClassyfarm =  toHtml(PopolaCombo.getInfoChecklistFarmacosorveglianzaIstanza(TicketDetails.getId(), "descrizione_messaggio_classyfarm"));
	%>
<!-- 	<div align="right" style="padding-left: 210px; margin-top: 35px"> -->
<!-- 	<table class="details" cellpadding="10" cellspacing="10" width="40%"> -->
<!-- 	<col width="30%"> -->
<!-- 	<tr><th colspan="2"> -->
<%-- 	<a href="javascript:openChk_farmaco('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>');">  --%>
<!-- 	<input type="button" value="Compila/Visualizza checklist di Farmacosorveglianza"/></a> -->
<!-- 	</th></tr>	 -->
<%-- 	<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idEsitoClassyfarm == null || !idEsitoClassyfarm.equals("0")))  { %> --%>
<%-- 	<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist_farmaco('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr> --%>
<%-- 	<% } %> --%>
<%-- 	<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr> --%>
<%-- 	<tr <%="0".equals(idEsitoClassyfarm) ? " style= 'background: lime'" : " style= 'background: lightcoral'" %>><td class="formLabel">ESITO ULTIMO INVIO CLASSYFARM</td> <td><%= "0".equals(idEsitoClassyfarm) ? "CHECKLIST INSERITA" : "1".equals(idEsitoClassyfarm) ? "CHECKLIST INSERITA CON ERRORI" : "2".equals(idEsitoClassyfarm) ? "CHECKLIST NON INSERITA" : "" %></td></tr> --%>
<%-- 	<tr><td class="formLabel">DESCRIZIONE MESSAGGIO ULTIMO INVIO CLASSYFARM</td> <td><%= descrizioneMessaggioClassyfarm %></td></tr> --%>
<%-- 	<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO CLASSYFARM</td> <td><%= descrizioneErroreClassyfarm %></td></tr> --%>
<%-- 	<tr><td class="formLabel">DATA ULTIMO INVIO CLASSYFARM</td> <td><%=dataInvio %></td></tr> --%>
<!-- 	<tr><td colspan="2"></td></tr> -->
<!-- 	</table> -->
<!-- 	</div>	 -->
				
<%   } %>

