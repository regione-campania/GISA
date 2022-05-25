<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.base.*,org.aspcfs.modules.registrazioniAnimali.base.*" %>
<jsp:useBean id="listaEventi" class="org.aspcfs.modules.registrazioniAnimali.base.EventoList" scope="request"/>
<jsp:useBean id="registrazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="animaleDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>
<jsp:useBean id="cane" class="org.aspcfs.modules.anagrafe_animali.base.Cane" scope="request"/>
<jsp:useBean id="gatto" class="org.aspcfs.modules.anagrafe_animali.base.Gatto" scope="request"/>
<jsp:useBean id="wkf" class="org.aspcfs.modules.registrazioniAnimali.base.RegistrazioniWKF" scope="request" />
<jsp:useBean id="OperatoreDettagli"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script type="text/javascript">
function openDelete(registrazioneId, tipologiaRegistrazioneId, animaleId){
	var res;
	var result;
	window.open('AnimaleAction.do?command=PrepareDeleteRegistrazione&registrazioneId='+registrazioneId+'&animaleId='+animaleId+'&tipologiaRegistrazioneId='+tipologiaRegistrazioneId,'popupSelect',
		'height=410px,width=410px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script> 


<%
	Stabilimento stabOperatore = (Stabilimento) OperatoreDettagli
			.getListaStabilimenti().get(0);
	LineaProduttiva lineaOperatore = (LineaProduttiva) stabOperatore
			.getListaLineeProduttive().get(0);
	int idLineaOperatore = lineaOperatore.getId();

	String param1 = "idLinea=" + idLineaOperatore;
%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href ="OperatoreAction.do?command=Details&opId=<%=idLineaOperatore %>"> <dhv:label
						name="">Dettaglio operatore</dhv:label></a> >
						<dhv:label
						name="">Lista registrazioni animali</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<dhv:container name="anagrafica" selected="Registrazioni animali" object="OperatoreDettagli" param="<%=param1 %>" appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>

    <br>

    

  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <!--  th>
        &nbsp;
      </th-->
      <th width="30%" valign="center" align="left">
        <strong>Numero</strong>
      </th>
      <th><b><dhv:label name="">Categoria</dhv:label></b></th>
     <th><b><dhv:label name="">Data inserimento in BDU</dhv:label></b></th>
     <th><b><dhv:label name="">Data della registrazione</dhv:label></b></th>
      <th width="15%">
        <b><dhv:label name="">Proprietario/Detentore</dhv:label></b>
      </th>
      <th width="20%">
        <b><dhv:label name="">Cane</dhv:label></b>
      </th>
      <th width="30%">
       <b><dhv:label name="">Destinazione (in caso di cessione, trasferimento, etc) <br> Provenienza (in caso di rientro da fuori regione/fuori stato, etc) </dhv:label></b>
      </th>
      <th><b><dhv:label name="">Asl di Riferimento</dhv:label></b></th>
      
       <dhv:evaluate if="<%=User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || User.getRoleId()==new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"))%>">
 	<th><b><dhv:label name="">HelpDesk</dhv:label></b></th></dhv:evaluate>
        
    </tr>
  <%
    Iterator j = listaEventi.iterator();
    if ( j.hasNext() ) {
     // int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
      //  rowid = (rowid != 1?1:2);
        Evento thisEvento = (Evento)j.next();
  %>
    <tr class="">

      <td nowrap>
        <a href="javascript:popURL('RegistrazioniAnimale.do?command=Details&id=<%= thisEvento.getIdEvento() %>&tipologiaEvento=<%= thisEvento.getIdTipologiaEvento()%>&popup=true', 'AccountDetails','650','500','yes','yes'); "><%= thisEvento.getIdEvento() %></a>
      </td>
      <td  nowrap>
	   	<%= toHtml(registrazioniList.getSelectedValue(thisEvento.getIdTipologiaEvento())) %>
	  </td>
	  <td >
      	<%=toDateasString(thisEvento.getEntered()) %>&nbsp;
      </td>
      	  <td >
      	<%=toDateasString(thisEvento.getDataRegistrazione()) %>&nbsp;
      </td>
 	  <td>
 	  <% 
 	  
 	  Operatore proprietario = thisEvento.getIdProprietarioOriginarioRegistrazione();
 	  if (proprietario != null) { 
        Stabilimento stab = (Stabilimento) (proprietario.getListaStabilimenti().get(0));
        LineaProduttiva linea = (LineaProduttiva)( stab.getListaLineeProduttive().get(0));
 	  %>
 	  
 	  <a href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=linea.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(proprietario.getRagioneSociale()) %></a>
 	  
 	  
			<!-- a href="LineaProduttivaAction.do?command=Details&lineaId=<%= linea.getId() %>"><%= toHtml(proprietario.getRagioneSociale()) %></a-->
	<%}else{ %>
	--
	<%} %>/
	  <% 
	  Operatore detentore = thisEvento.getIdDetentoreOriginarioRegistrazione();
	  if (detentore != null) { 
	        Stabilimento stab1 = (Stabilimento) (detentore.getListaStabilimenti().get(0));
	        LineaProduttiva linea1 = (LineaProduttiva)( stab1.getListaLineeProduttive().get(0));
	  %>
	  
	   <a href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=linea1.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(detentore.getRagioneSociale()) %>&nbsp;</a>
		<!-- a href="LineaProduttivaAction.do?command=Details&lineaId=<%= linea1.getId() %>"><%= toHtml(detentore.getRagioneSociale()) %>&nbsp;</a-->
	 <%}
	  else{ %>  
	--
	 <%} %>
	  </td>
	  <td>
	    
         <%= thisEvento.getMicrochip() %>

       
	   </td>
  	<td>
  	 <% 
 	  
 	  Operatore proprietarioDest = thisEvento.getIdProprietarioDestinatarioRegistrazione();
 	  if (proprietarioDest != null) { 
        Stabilimento stabprpDest = (Stabilimento) (proprietarioDest.getListaStabilimenti().get(0));
         LineaProduttiva lineaDeststa = (LineaProduttiva)( stabprpDest.getListaLineeProduttive().get(0));
 	  %><a href="javascript:popURL('OperatoreAction.do?command=Details&opId=<%=lineaDeststa.getId()%>&popup=true&viewOnly=true','AccountDetails','650','500','yes','yes');"><%= toHtml(proprietarioDest.getRagioneSociale()) %>&nbsp;</a>
		
	 <%}
	  else{ %>  
	<%=thisEvento.getDestinazione() %>
	 <%} %>
	  
  	
  	</td>
  	<td>
  	<%=AslList.getSelectedValue(thisEvento.getIdAslRiferimento()) %>
  	</td>

  	
     </tr>

  <%}%>
  <%} else {%>
    <tr class="containerBody">
      <td colspan="9">
        <dhv:label name="">Nessuna registrazione individuata.</dhv:label>
      </td>
    </tr>
  <%}%>
  </table>
	<br>
  
</dhv:container>