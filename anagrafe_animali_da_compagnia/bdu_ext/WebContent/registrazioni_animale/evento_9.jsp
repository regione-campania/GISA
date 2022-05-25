<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.registrazioniAnimali.base.EventoDecesso"%>
<script type="text/javascript">
function openDichiarazioneDecesso(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintDichiarazioneDecesso&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>
<script type="text/javascript">
function openCertificatoDecesso(idAnimale, idSpecie){
	var res;
	var result;
		window.open('AnimaleAction.do?command=PrintCertificatoDecesso&idAnimale='+idAnimale+'&idSpecie='+idSpecie,'popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<jsp:useBean id="tipoSoggettoSterilizz" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeDecessoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="evento" class="org.aspcfs.modules.registrazioniAnimali.base.Evento" scope="request"/>
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
	

<%EventoDecesso eventoF = (EventoDecesso) evento; %>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>



<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
<th colspan="2">Dettagli della registrazione di decesso -- <a href="#"
				onclick="openRichiestaPDF('PrintDichiarazioneDecesso', '<%=eventoF.getIdAnimale()%>','<%=eventoF.getSpecieAnimaleId()%>',  '-1', '-1', '<%=eventoF.getIdEvento() %>')"
				id="" target="_self">Dichiarazione di decesso</a> --
				
				 <a href="#"
				onclick="openRichiestaPDF('PrintCertificatoDecesso', '<%=eventoF.getIdAnimale()%>','<%=eventoF.getSpecieAnimaleId()%>',  '-1', '-1', '<%=eventoF.getIdEvento() %>')"
				id="" target="_self">Certificato di decesso</a> --
				</th>
				
				

    <tr>  <td><b><dhv:label name="">Data del decesso</dhv:label></b></td>
      	  <td >
      	<%=toDateasString(eventoF.getDataDecesso()) %>&nbsp;
      </td></tr>
     <tr><td><b><dhv:label name="">Provincia Decesso</dhv:label></b></td>
  	<td>
  	 <%=(eventoF.getIdProvinciaDecesso() > 0 )? provinceList.getSelectedValue(eventoF.getIdProvinciaDecesso()) : "--" %>
  	</td>
     </tr>
    <tr><td><b><dhv:label name="">Comune Decesso</dhv:label></b></td>
  	<td>
  	 <%=(eventoF.getIdComuneDecesso() > 0 )? comuniList.getSelectedValue(eventoF.getIdComuneDecesso()) : "--" %>
  	</td>
     </tr>
    <tr><td><b><dhv:label name="">Indirizzo Decesso</dhv:label></b></td>
  	<td>
  	 <%=(eventoF.getIndirizzoDecesso() != null && !("").equals(eventoF.getIndirizzoDecesso()) ) ? eventoF.getIndirizzoDecesso() : "--" %>
  	</td>
     </tr>
	<tr>  <td width="20%">
        <b><dhv:label name="">Informazioni aggiuntive luogo del decesso</dhv:label></b>
      </td>
       <td>
      <%=(eventoF.getLuogoDecesso()!=null) ? eventoF.getLuogoDecesso() : ""%>
	   </td></tr>
	<tr><td><b><dhv:label name="">Decesso Presunto</dhv:label></b></td>
  	<td>
  	 <%=(eventoF.isFlagDecessoPresunto())? "Si" : "No" %>
  	</td>
     </tr>
     
     	<tr><td><b><dhv:label name="">Causa del decesso</dhv:label></b></td>
  	<td>
  	 <%=causeDecessoList.getSelectedValue(eventoF.getIdCausaDecesso())%>
  	</td>
     </tr>


  </table>