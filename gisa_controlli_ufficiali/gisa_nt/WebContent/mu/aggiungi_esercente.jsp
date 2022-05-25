<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--  <jsp:useBean id="seduta"			class="org.aspcfs.modules.mu.base.SedutaUnivoca" scope="request" /> --%>
<%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>HTML Table Filter Generator script | Examples - jsFiddle
	demo by koalyptus</title>
<script type='text/javascript' src='/js/lib/dummy.js'></script>

<link rel="stylesheet" type="text/css" href="/css/result-light.css">
<script type='text/javascript'
	src="http://tablefilter.free.fr/TableFilter/tablefilter_all_min.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://tablefilter.free.fr/TableFilter/filtergrid.css">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/gestione_esercenti.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>


<script src='javascript/modalWindow.js'></script>
<script src='javascript/jquerymini.js'></script>
</head>

<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.stabilimenti.base.Organization"
	scope="request" />
<jsp:useBean id="listaCapi"
	class="org.aspcfs.modules.mu.base.CapoUnivocoList" scope="request" />
<jsp:useBean id ="seduta" class="org.aspcfs.modules.mu.base.SedutaUnivoca" scope="request" />


<script type="text/javascript" src="dwr/interface/Articolo17.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<script type="text/javascript" src="dwr/util.js"></script>
<script type="text/javascript">
function impostaDestinatarioMacelloCorrente(index){
	document.getElementById('destinatario_' + index + '_id').value = document.getElementById('id_macello').value;
	document.getElementById('destinatario_' + index + '_nome').value = "<%=OrgDetails.getName().replaceAll("\"", "'")%>";
	document.getElementById('destinatario_label_' + index).innerHTML = "<%=OrgDetails.getName().replaceAll("\"", "'")%>";
		
	document.getElementById('esercenteNoGisa' + index).style.display = 'none';
}


function verificaEsistenzaArticolo17 ()
{
		Articolo17.checkEsistenza(document.getElementById("destinatario_1_id").value ,  document.getElementById('id_macello').value, document.getElementById('idSeduta').value, {callback:verificaArticolo17,async:false});

}

function verificaArticolo17(value)
{

if (value){
	alert('Attenzione, esiste un articolo 17 aperto per l\'esercente selezionato. Modifica quello esistente o chiudilo prima di procedere');
	return false;
}else{
	document.forms[0].submit();
	window.close();
}
}

</script>
<%@ include file="../initPage.jsp" %>
<form action="MacellazioneUnica.do?command=SalvaArticolo17"
	name="salva17" id="salva17" method="post" onsubmit="return verificaEsistenzaArticolo17();">

	<input type="button" value="CONFERMA CAPI" onclick="verificaEsistenzaArticolo17();" />

	<table>

		<tr>
			<th colspan="2"><strong>Esercenti</strong></th>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%" border="0" cellpadding="2" cellspacing="0"
					align="left">
					<tr class="containerBody">
						<td class="formLabel">In Regione</td>
						<td><input type="hidden" name="id_macello" id="id_macello"
							value="<%=OrgDetails.getOrgId()%>"> Si <input
							type="radio" name="destinatario_1_in_regione" value="si"
							onclick="selectDestinazione(1)" id="inRegione_1" <%-- 	<%=/* (update && !Capo.isDestinatario_1_in_regione()) ? ("") : ("checked=\"checked\"")  */%> --%> />

							No <input type="radio" name="destinatario_1_in_regione"
							value="no" onclick="selectDestinazione(1)" id="outRegione_1" <%-- <%=/* (update && !Capo.isDestinatario_1_in_regione()) ? ("checked=\"checked\"") : ("") */ %> --%> />
						</td>
					</tr>
					<tr>
					<tr class="containerBody">
						<td class="formLabel">Destinatario delle Carni</td>
						<td>
							<div
								style="<%-- <%=(update && !Capo.isDestinatario_1_in_regione()) ? ("display:none") : ("") %> --%>"
								id="imprese_1">
								<a
									href="javascript:popLookupSelectorDestinazioneCarni( 'si', 1, 'impresa' );"
									onclick="selectDestinazione(1);">[Seleziona Impresa] </a><br />
								<a
									href="javascript:popLookupSelectorDestinazioneCarni( 'si', 3, 'stab');"
									onclick="selectDestinazione(1);">[Seleziona Stabilimento]
								</a><br /> <a
									href="javascript:mostraTextareaEsercente('esercenteNoGisa1');"
									onclick="selectDestinazioneFromLinkTextarea(1);">[Inserisci
									Esercente non in G.I.S.A.]</a><br /> <a
									href="javascript:impostaDestinatarioMacelloCorrente(1);"
									onclick="">[Macello corrente]</a>
								<textarea style="display: none;" rows="3" cols="36"
									id="esercenteNoGisa1" name="esercenteNoGisa1"
									onchange="valorizzaDestinatario(this,'destinatario_1');"><%-- <%=toHtmlValue( Capo.getDestinatario_1_nome() ) %> --%></textarea>
							</div>
							<div
								style="<%-- <%=(update && !Capo.isDestinatario_1_in_regione()) ? ("") : ("display:none") %> --%>"
								id="esercenti_1">
								<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 1 );" >[Seleziona Esercente]</a> -->
								<a
									href="javascript:mostraTextareaEsercente('esercenteFuoriRegione1');"
									onclick="selectDestinazioneFromLinkTextarea(1);">[Inserisci
									Esercente fuori Regione]</a>
								<textarea style="display: none;" rows="3" cols="36"
									id="esercenteFuoriRegione1" name="esercenteFuoriRegione1"
									onchange="valorizzaDestinatario(this,'destinatario_1');"><%-- <%=toHtmlValue( Capo.getDestinatario_1_nome() ) %> --%></textarea>
							</div> <br />
							<div id="destinatario_label_1" align="center">
								<%-- 	<%=(Capo.getDestinatario_1_id() != -1) ? (toHtmlValue( Capo.getDestinatario_1_nome() )) : ("-- Seleziona Esercente --") %> --%>
							</div> <input type="hidden" name="destinatario_1_id"
							id="destinatario_1_id"
							value="<%-- <%=(Capo.getDestinatario_1_id() != -1) ? (Capo.getDestinatario_1_id()) : ("-1") %> --%>" />
							<input type="hidden" name="destinatario_1_nome"
							id="destinatario_1_nome"
							onchange="<!-- gestisciObbligatorietaVisitaPostMortem(); -->"
							value="<%-- <%=toHtmlValue( Capo.getDestinatario_1_nome() ) %> --%>" />
							<p id="destinatarioCarni1" align="center" style="display: none;">
								<font color="red">*</font>
							</p>
						</td>
				</table>


				<table id="tabellaCapiPartita" cellpadding="0" cellspacing="0"
					width="100%" class="order-table table">
					<thead>
						<tr>
							<th>Specie</th>
							<th>Matricola</th>
							<th>Identificativo partita</th>
							<th>Date creazione partita</th>
							<th>Mumero seduta macellazione</th>
							<th>Data seduta di macellazione</th>
							<th>Aggiungi capo/ Singola parte</th>
							<!-- <th>Selezione multipla</th></tr> -->
					</thead>
					<tbdody> <%-- <input type="hidden" id="mumero_seduta" value="<%=seduta.getNumeroSeduta() %>"/> --%>
<input type="hidden" name="idSeduta" id="idSeduta" value="<%=seduta.getId() %>"/>
					<%
						int specie = -1;
						for (int i = 0; i < listaCapi.size(); i++) {
							CapoUnivoco capo = (CapoUnivoco) listaCapi.get(i);
							
							if (capo.getIdStato() == CapoUnivoco.idStatoMacellato) {
								for (int k = 0; k < capo.getNumeroParti() - capo.getNumeroPartiAssegnate(); k++) {
					%> <input type="hidden" id="capo_<%=i%>_id" value="<%=capo.getId()%>" />
					<input type="hidden" id="capo_<%=i%>_matricola"
						value="<%=capo.getMatricola()%>" /> <input type="hidden"
						id="capo_<%=i%>_idspecie" value="<%=capo.getSpecieCapo()%>" /> <input
						type="hidden" id="capo_<%=i%>_specie"
						value="<%=capo.getSpecieCapoNome()%>" />

					<tr class="rowmu<%=capo.getSpecieCapo()%>">
						<%-- <td><%=seduta.getNumeroSeduta() %></td> --%>
						<td><%=capo.getSpecieCapoNome()%> &nbsp;</td>
						<td><%=capo.getMatricola()%> &nbsp;</td>
						<td><%=capo.getPartita().getNumeroPartita() %></td>
						<td><%=toDateasString(capo.getPartita().getDataArrivoMacello()) %></td>
						<td><%=(capo.getSeduta().getNumeroSeduta()) %>
						<td><%=toDateasString(capo.getSeduta().getData()) %></td>
						<td><input type="checkbox" id="capo_<%=i%>"
							name="capo_<%=capo.getId() + "_" + k%>"
							onClick="<%-- gestisciAggiuntaSingola(this, '<%=capo.getSpecieCapo() %>', '<%=capo.getIdPartita()%>') --%>" /></td>
						<script><%-- controllaSelezione('<%=i%>', '<%=capo.getId() %>') --%></script>
						<%-- <td>
<% if (specie!=capo.getSpecieCapo()){ %>
<input type="checkbox" id="specie_<%=capo.getSpecieCapo() %>_<%=capo.getIdPartita() %>" name="specie_<%=capo.getSpecieCapo() %>" onClick="gestisciAggiuntaTotale(this, '<%=capo.getSpecieCapo() %>', '<%=capo.getIdPartita()%>', '-1')"/> Seleziona tutti i capi di questa specie <br/>
Seleziona <input type ="text" id="specie_num_<%=capo.getSpecieCapo() %>_<%=capo.getIdPartita() %>" size="2" maxlength="3"  onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"  name="specie_num_<%=capo.getSpecieCapo() %>" value="0"/> capi di questa specie <input type="button" onClick="selezionaNumCapi('<%=capo.getSpecieCapo() %>', '<%=capo.getIdPartita() %>')" value="ok"/>
<% } %>
&nbsp;  </td> --%>
					</tr>
					<%
						}
							}
							specie = capo.getSpecieCapo();
						}
					%> </tbdody>
				</table>
				</form> <script type='text/javascript'>//<![CDATA[ 

var tabellaCapiPartita_Props = {
	col_0: "none",
	col_1: "select",
	col_3: "none",
    col_4: "none",
  display_all_text: " [ Mostra tutte ] ",
    sort_select: true
};
var tf3 = setFilterGrid("tabellaCapiPartita", tabellaCapiPartita_Props);
//]]>  

</script>