<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page
	import="org.aspcfs.modules.registrazioniAnimali.base.EventoPrelievoDNA,org.aspcfs.modules.opu.base.*"%>


<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="evento"
	class="org.aspcfs.modules.registrazioniAnimali.base.Evento"
	scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="veterinariList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<%
EventoPrelievoDNA eventoF = (EventoPrelievoDNA) evento;
%>
<%@ include file="../initPage.jsp"%>
<%@ include file="../initPopupMenu.jsp"%>


<script type="text/javascript">
function openCertificatoPrelievoDNA(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintCertificatoPrelievo&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="pagedList">
	<th colspan="2">Dettagli della registrazione di prelievo -- 
	
	<a href="#"
				onclick="openRichiestaPDF('PrintCertificatoPrelievo', '<%=eventoF.getIdAnimale()%>','<%=eventoF.getSpecieAnimaleId()%>', '-1', '-1', '<%=eventoF.getIdEvento() %>')"
				id="" target="_self">Certificato di prelievo</a> --
				</th>

	<tr>
		<td><b><dhv:label name="">Data del prelievo</dhv:label></b></td>
		<td><%=toDateasString(eventoF.getDataPrelievo())%>&nbsp;</td>
	</tr>
	
	<tr>
		<td><b><dhv:label name="">Veterinario</dhv:label></b></td>
		<td><%=veterinariList.getSelectedValue(eventoF.getIdVeterinario())%>&nbsp;</td>
	</tr>
	

</table>