<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="tipoSoggettoSterilizz" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaPratiche" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request"/>

<%EventoFurto eventoF = (EventoFurto) evento; %>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>

<%@page import="org.aspcfs.modules.registrazioniAnimali.base.EventoFurto"%>

<script type="text/javascript">
function openDichiarazioneFurto(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintDichiarazioneSmarrimento&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<script type="text/javascript">
function openCertificatoSmarrimento(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintCertificatoSmarrimento&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
<th colspan="2">Dettagli della registrazione di furto
-- 	 <a href="#"
		onclick="openRichiestaPDF('PrintDichiarazioneFurto', '<%=eventoF.getIdAnimale()%>','<%=eventoF.getSpecieAnimaleId()%>', '-1', '-1', '<%=eventoF.getIdEvento() %>')"
		id="" target="_self">Dichiarazione di furto</a> -- <a href="#"
				onclick="openRichiestaPDF('PrintCertificatoFurto', '<%=eventoF.getIdAnimale()%>','<%=eventoF.getSpecieAnimaleId()%>', '-1', '-1', <%=eventoF.getIdEvento() %>)"
				id="" target="_self">Certificato di furto</a>
</th>

    <tr>  <td><b><dhv:label name="">Data del furto</dhv:label></b></td>
      	  <td >
      	<%=toDateasString(eventoF.getDataFurto()) %>&nbsp;
      </td></tr>
	<tr>  <td width="20%">
        <b><dhv:label name="">Luogo del furto</dhv:label></b>
      </td>
       <td>
       <%=(eventoF.getLuogoFurto() != null)? eventoF.getLuogoFurto() : "" %>
  
	   </td></tr>
	<tr><td><b><dhv:label name="">Dati della denuncia</dhv:label></b></td>
  	<td>
  	<%=(eventoF.getDatiDenuncia() != null)? eventoF.getDatiDenuncia() : "" %>
  	</td>
     </tr>


  </table>