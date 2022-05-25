<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>
<jsp:useBean id="listaEventi" class="org.aspcfs.modules.registrazioniAnimali.base.EventoList" scope="request"/>
<jsp:useBean id="registrazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="animaleList" class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList" scope="request" />
<jsp:useBean id="EventiListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="razzaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="da" class="java.lang.String" scope="request"/>
<jsp:useBean id="a" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script type="text/javascript">
function openMod12(){
	var res;
	var result;
		window.open('ProfilassiRabbia.do?command=Search12&limit=yes&da='+document.getElementById('da').value +'&a='+document.getElementById('a').value+'&cert=true','popupSelect',
		'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script>

<script type="text/javascript">
function openMailASL(){
	window.open('ProfilassiRabbia.do?command=ListaMailASL','popupSelect',
		'height=200px,width=350px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}
</script> 

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

<a href="RegistrazioniAnimale.do"><dhv:label name="">Registrazioni</dhv:label></a> > 
<a href="RegistrazioniAnimale.do?command=Search12"><dhv:label name="">Risultati Ricerca (Modello trasmissione vaccinazioni antirabbiche)</dhv:label></a>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
			
<a href="#" onclick="openMailASL()" id="" target="_self">Recapiti mail ASL</a>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="EventiListInfo"/>

<input type="hidden" value="<%=a %>" name="a" id="a"/>
<input type="hidden" value="<%=da %>" name="da" id="da"/>

<%if (listaEventi.size() > 0){ %>
<input type="button" value="Genera modello trasmissione vaccinazioni antirabbiche"
				onclick="javascript:openMod12();"
				id="" target="_self">
			<%} %>
<br></br>		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">

    <tr>
      <!--  th>
        &nbsp;
      </th-->
       <th width="10%">
       <b><dhv:label name="">Data inserimento in BDU</dhv:label></b></th>
      <th width="10%">
      <b><dhv:label name="">Data della vaccinazione</dhv:label></b>
      </th>
      <th width="10%">
        <b><dhv:label name="">Animale</dhv:label></b>
      </th>
       <th width="5%">
        <b><dhv:label name="">Specie</dhv:label></b>
      </th>
       <th width="15%">
        <b><dhv:label name="">Razza</dhv:label></b>
      </th>
       <th width="3%">
        <b><dhv:label name="">Sesso</dhv:label></b>
      </th>
       <th width="10%">
        <b><dhv:label name="">Data di nascita</dhv:label></b>
      </th>
       <th width="15%">
        <b><dhv:label name="">Proprietario</dhv:label></b>
      </th>
      <th width="15%">
        <b><dhv:label name="">Indirizzo proprietario</dhv:label></b>
      </th>
       <th width="7%">
       <b><dhv:label name="">Nome Vaccino</dhv:label></b></th>
       <th width="5%">
         <b><dhv:label name="">Lotto Vaccino</dhv:label></b></th>
       <th width="10%">
         <b><dhv:label name="">Produttore Vaccino</dhv:label></b></th>
        <th width="10%">
         <b><dhv:label name="">Scadenza Vaccino</dhv:label></b></th>
       <th width="10%">
       <b><dhv:label name="">Asl di Riferimento</dhv:label></b></th>
    </tr>
  <%
    Iterator j = listaEventi.iterator();
  	Iterator k = animaleList.iterator();
    if ( j.hasNext() ) {
     // int rowid = 0;
      int i =0;
      while (j.hasNext()) {
        i++;
      //  rowid = (rowid != 1?1:2);
        Evento thisEvento = (Evento)j.next();
        Animale thisAnimale = (Animale)k.next();
        EventoInserimentoVaccinazioni thisVaccino = (EventoInserimentoVaccinazioni)thisEvento;
    	
  %>
    <tr class="">
  
      
	  <td >
      	<%=toDateasString(thisEvento.getEntered()) %>&nbsp;
      </td>
      	  <td >
      	<%=toDateasString(thisVaccino.getDataVaccinazione()) %>&nbsp;
      </td>
	  <td>
	    <%= thisEvento.getMicrochip() %>
         <!-- a href="AnimaleAction.do?command=Details&animaleId=<%=thisEvento.getIdAnimale() %>&idSpecie=<%=thisEvento.getSpecieAnimaleId() %>"> <%= thisEvento.getMicrochip() %></a-->
	  </td>
	  
	  <td><%= thisAnimale.getNomeSpecieAnimale()%></td>
	  
	   <td><%=toHtml(razzaList.getSelectedValue(thisAnimale.getIdRazza()))%> </td>
	   
	   <td><%= thisAnimale.getSesso()%></td>
	   
	    <td><%= toDateasString(thisAnimale.getDataNascita())%></td>
	    
	    <%Operatore proprietario = thisAnimale.getProprietario(); %>
	    <td width="7%"><center><%=(proprietario != null && proprietario.getIdOperatore()>0 ) ? proprietario.getRagioneSociale() : "" %></center></td>
		<td width="7%"><center>
<%
		if(proprietario != null && proprietario.getIdOperatore()>0 )
		{	
			Stabilimento stab = (Stabilimento) proprietario.getListaStabilimenti().get(0);
	   	 	Indirizzo ind = (Indirizzo) stab.getSedeOperativa();
		
%>
	   		<%=ind.toString() %>
<%
		} 
		else
		{
%>
			--
<%			
		}
%>
		
	   	</center></td>  
	   <td><%=(thisVaccino.getNomeVaccino() != null) ? thisVaccino.getNomeVaccino(): "--"%></td>
		<td><%=(thisVaccino.getNumeroLottoVaccino() != null) ? thisVaccino.getNumeroLottoVaccino(): "--"%></td>
		<td><%=(thisVaccino.getProduttoreVaccino() != null) ? thisVaccino.getProduttoreVaccino(): "--"%></td>
		<td><%=(thisVaccino.getDataScadenzaVaccino() != null) ? toDateasString(thisVaccino.getDataScadenzaVaccino()): "--"%></td>
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
<dhv:pagedListControl object="EventiListInfo"/>