<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script>

function openChk_bns(orgId,idControllo,url,specie){
	var res;
	var result;
		window.open('PrintModulesHTML.do?command=AddSchedaAllegato&idControllo='+idControllo+'&orgId='+orgId+'&url='+url+'&specie='+specie,'popupSelectViewChk'+idControllo+specie,
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function openChk_cnd(orgId,stabId, idControllo){
	var res;
	var result;
	
		 window.open('PrintModulesHTML.do?command=AddSchedaAllegato&idControllo='+idControllo+'&idStabilimento='+stabId+'&orgId='+orgId+'&specie=-1','popupSelect',
			'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
		
}

function openGuidaChk_cnd(){
	var res;
	var result;
		window.open('man/#inviob26','popupSelect',
		'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function inviaChecklist(idIstanza, idControllo) {
	window.open('GestioneInvioChecklist.do?command=InviaChecklist&id='+idIstanza+'&idControllo='+idControllo,'popupSelectInviaChk'+idIstanza,
	'height=300px,width=400px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');

}
</script>



<% 
boolean hasBenessere = false;
boolean hasCondizionalita = false;

boolean hasSuini = false;
boolean hasBoviniBufalini = false;
boolean hasGallus = false;
boolean hasBroiler = false;
boolean hasVitelli = false;
boolean hasAltreSpecie = false;
boolean hasOviCaprini = false;

for(Piano p :TicketDetails.getPianoMonitoraggio()) {
	if (PopolaCombo.hasEventoMotivoCU("isBenessereAnimale", p.getId(), -1)){
		hasBenessere = true;
		
		if (PopolaCombo.hasChecklistBenessereMotivoCU("isSuini", p.getId(), -1, TicketDetails.getId())){
			hasSuini = true;
		}
		if (PopolaCombo.hasChecklistBenessereMotivoCU("isBoviniBufalini", p.getId(), -1, TicketDetails.getId())){
			hasBoviniBufalini = true;
		}
		if (PopolaCombo.hasChecklistBenessereMotivoCU("isGallus", p.getId(), -1, TicketDetails.getId())){
			hasGallus = true;
		}
		if (PopolaCombo.hasChecklistBenessereMotivoCU("isBroiler", p.getId(), -1, TicketDetails.getId())){
			hasBroiler = true;
		}
		if (PopolaCombo.hasChecklistBenessereMotivoCU("isVitelli", p.getId(), -1, TicketDetails.getId())){
			hasVitelli = true;
		}
		if (PopolaCombo.hasChecklistBenessereMotivoCU("isAltreSpecie", p.getId(), -1, TicketDetails.getId())){
			hasAltreSpecie = true;
		}
		if (PopolaCombo.hasChecklistBenessereMotivoCU("isOviCaprini", p.getId(), -1, TicketDetails.getId())){
			hasOviCaprini = true;
		}
		
	}
	
	if (PopolaCombo.hasEventoMotivoCU("isCondizionalita", p.getId(), -1)){
		hasCondizionalita = true;
	}
}
%>

	<% if (hasBenessere) { 
		
		int idSpecie = -1;
		String idIstanza = null;
		String idBdn = null;
		String statoGisa = null;
		String esitoImport = null;
		String descrizioneErrore = null;
		String nomeChecklist = null;
		String dataImport = null;
		int col = 0;
		%>
		<div align="right" style="padding-left: 210px; margin-top: 35px">
		<table width="70%">
		<tr><td valign="top" align="right">
			
			<% if (hasSuini) { 
				idSpecie = 122; 
				idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
				statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
				nomeChecklist = "SUINI";
				idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
				dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
				esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
				descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
			%>
				<%= col%2==0 ? "</td></tr><tr><td valign='top' align='right'>" : "</td><td valign='top'>" %>
				<table class="details" cellpadding="10" cellspacing="10" width="40%">
				<col width="30%">
				<tr><th colspan="2">
				<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=idSpecie%>');"> 
				<input type="button" value="Compila/Visualizza lista di riscontro per <%=nomeChecklist %>"/></a>
				</th></tr>	
				<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
					<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
				<% } %>
				<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
				<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
				<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
				<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
				<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
				<tr><td colspan="2"></td></tr>
				</table>
			
			<% col++;
			} %>
			
			<% if (hasBoviniBufalini) { 
				idSpecie = 121; 
				idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
				statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
				nomeChecklist = "BOVINI/BUFALINI";
				idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
				dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
				esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
				descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
				%>
				<%= col%2==0 ? "</td></tr><tr><td valign='top' align='right'>" : "</td><td valign='top'>" %>
				<table class="details" cellpadding="10" cellspacing="10" width="40%">
				<col width="30%">
				<tr><th colspan="2">
				<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=idSpecie%>');"> 
				<input type="button" value="Compila/Visualizza lista di riscontro per <%=nomeChecklist %>"/></a>
				</th></tr>	
				<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
					<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
				<% } %>
				<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
				<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
				<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
				<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
				<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
				<tr><td colspan="2"></td></tr>
				</table>
			
			<% col++;
			} %>
		
			<% if (hasGallus) { 
				idSpecie = 131; 
				idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
				statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
				nomeChecklist = "GALLINE OVAIOLE";
				idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
				dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
				esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
				descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
				%>
				<%= col%2==0 ? "</td></tr><tr><td valign='top' align='right'>" : "</td><td valign='top'>" %>
				<table class="details" cellpadding="10" cellspacing="10" width="40%">
				<col width="30%">
				<tr><th colspan="2">
				<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=idSpecie%>');"> 
				<input type="button" value="Compila/Visualizza lista di riscontro per <%=nomeChecklist %>"/></a>
				</th></tr>	
				<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
					<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
				<% } %>
				<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
				<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
				<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
				<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
				<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
				<tr><td colspan="2"></td></tr>
				</table>
			
			<% col++;
			} %>
			
			<% if (hasBroiler) { 
				idSpecie = 1461; 
				idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
				statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
				nomeChecklist = "POLLI DA CARNE";
				idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
				dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
				esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
				descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
				%>
				<%= col%2==0 ? "</td></tr><tr><td valign='top' align='right'>" : "</td><td valign='top'>" %>
				<table class="details" cellpadding="10" cellspacing="10" width="40%">
				<col width="30%">
				<tr><th colspan="2">
				<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=idSpecie%>');"> 
				<input type="button" value="Compila/Visualizza lista di riscontro per <%=nomeChecklist %>"/></a>
				</th></tr>	
				<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
					<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
				<% } %>
				<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
				<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
				<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
				<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
				<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
				<tr><td colspan="2"></td></tr>
				</table>			
			<% col++;
			} %>
			
			<% if (hasVitelli) { 
				idSpecie = 1211; 
				idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
				statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
				nomeChecklist = "VITELLI";
				idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
				dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
				esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
				descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
				%>
				<%= col%2==0 ? "</td></tr><tr><td valign='top' align='right'>" : "</td><td valign='top'>" %>
				<table class="details" cellpadding="10" cellspacing="10" width="40%">
				<col width="30%">
				<tr><th colspan="2">
				<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=idSpecie%>');"> 
				<input type="button" value="Compila/Visualizza lista di riscontro per <%=nomeChecklist %>"/></a>
				</th></tr>	
				<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
					<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
				<% } %>
				<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
				<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
				<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
				<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
				<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
				<tr><td colspan="2"></td></tr>
				</table>
			
			<% col++;
			} %>
			
			<% if (hasOviCaprini) { 
				idSpecie = 124; 
				idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
				statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
				nomeChecklist = "OVICAPRINI";
				idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
				dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
				esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
				descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
				%>
				<%= col%2==0 ? "</td></tr><tr><td valign='top' align='right'>" : "</td><td valign='top'>" %>
				<table class="details" cellpadding="10" cellspacing="10" width="40%">
				<col width="30%">
				<tr><th colspan="2">
				<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=idSpecie%>');"> 
				<input type="button" value="Compila/Visualizza lista di riscontro per <%=nomeChecklist %>"/></a>
				</th></tr>	
				<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
					<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
				<% } %>
				<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
				<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
				<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
				<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
				<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
				<tr><td colspan="2"></td></tr>
				</table>
			
			<% col++;
			} %>
			
			<% if (hasAltreSpecie) { 
				idSpecie = -2; 
				idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
				statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
				nomeChecklist = "ALTRE SPECIE";
				idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
				dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
				esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
				descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
				%>
				<%= col%2==0 ? "</td></tr><tr><td valign='top' align='right'>" : "</td><td valign='top'>" %>
				<table class="details" cellpadding="10" cellspacing="10" width="40%">
				<col width="30%">
				<tr><th colspan="2">
				<a href="javascript:openChk_bns('<%= TicketDetails.getOrgId() %>','<%=TicketDetails.getId()%>','<%=TicketDetails.getURlDettaglio() %>','<%=idSpecie%>');"> 
				<input type="button" value="Compila/Visualizza lista di riscontro per <%=nomeChecklist %>"/></a>
				</th></tr>	
				<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
					<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
				<% } %>
				<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
				<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
				<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
				<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
				<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
				<tr><td colspan="2"></td></tr>
				</table>
			
			<% col++;
			} %>
		</td></tr></table></div>
	<% } %>
	
	
	<% if (hasCondizionalita) { 
		
		int idSpecie = -1;
		String idIstanza = null;
		String idBdn = null;
		String statoGisa = null;
		String esitoImport = null;
		String descrizioneErrore = null;
		String nomeChecklist = null;
		String dataImport = null;
		
		idSpecie = -1; 
		idBdn = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id_bdn"));
		statoGisa = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("t") ? "APERTA" : toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "bozza")).equals("f") ? "CHIUSA" : "";
		nomeChecklist = "ATTO B11-SICUREZZA ALIMENTARE";
		idIstanza = toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "id"));
		dataImport =  toDateasStringFromStringWithTime(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "data_import"));
		esitoImport =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "esito_import"));
		descrizioneErrore =  toHtml(PopolaCombo.getInfoChecklistBenessereIstanza(TicketDetails.getId(), idSpecie, "descrizione_errore"));
		%>
	
			<div align="right" style="padding-left: 210px; margin-top: 35px">
			<table class="details" cellpadding="10" cellspacing="10" width="40%">
			<col width="30%">
			<tr><th colspan="2">
			<a href="javascript:openChk_cnd('<%= TicketDetails.getOrgId() %>','<%=idSpecie%>', '<%=TicketDetails.getId()%>');"> 
			<input type="button" value="Compila/Visualizza <%=nomeChecklist %>"/></a>
			</th></tr>	
			<tr><td class="formLabel" colspan="2"><a href="#" onClick="openGuidaChk_cnd()">Guida compilazione Atto B11</a></td></tr>
			
			<% if (TicketDetails.getClosed()!=null && statoGisa.equals("CHIUSA") && (idBdn == null || idBdn.equals("-1") || idBdn.equals("")) && (esitoImport==null || !esitoImport.equalsIgnoreCase("OK")) )  { %>
			<tr><td colspan="2" align="center"><input type="button" value="INVIA CHECKLIST" onClick="inviaChecklist('<%= idIstanza %>', '<%=TicketDetails.getId()%>')"/></td></tr>
			<% } %>
			<tr><td class="formLabel">STATO GISA</td> <td><%= statoGisa %></td></tr>
			<tr <%="OK".equals(esitoImport) ? " style= 'background: lime'" : "KO".equals(esitoImport) ? " style= 'background: lightcoral'" : "" %>><td class="formLabel">ESITO ULTIMO INVIO BDN</td> <td><%= esitoImport %></td></tr>
			<tr><td class="formLabel">DESCRIZIONE ERRORE ULTIMO INVIO BDN</td> <td><%= descrizioneErrore %></td></tr>
			<tr><td class="formLabel">DATA ULTIMO INVIO BDN</td> <td><%=dataImport %></td></tr>
			<tr><td class="formLabel">ID BDN</td> <td><%= idBdn %></td></tr>
			<tr><td colspan="2"></td></tr>
			</table>
			</div>	
	
	<% } %>




