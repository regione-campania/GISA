<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.anagrafe_animali.base.*" %>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Assets.do"><dhv:label name="accounts.Assets">Assets</dhv:label></a> > 
    <a href="Assets.do?command=Regresso"><dhv:label name="">Pregresso</dhv:label></a> >
    <dhv:label name="">Risultati Ricerca</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<!-- <dhv:container name="assetsmain" selected=""> -->

<!-- dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SavedBeanListInfo"/ -->

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="10%" >
      <strong><dhv:label name="">Microchip</dhv:label></strong>
    </th>
    <th width="8%" >
      <strong><dhv:label name="">Tatuaggio</dhv:label></strong>
    </th>
    <th width="8%">
      <strong><dhv:label name="">Data</dhv:label></strong>
    </th>
    <th width="5%">
      <strong><dhv:label name="">Ultima</dhv:label></strong>
    </th>
    <th width="5%">
      <strong><dhv:label name="">Tipo</dhv:label></strong>
    </th>
    <th width="10%" >
      <strong><dhv:label name="">Proprietario</dhv:label></strong>
    </th>
    <th width="10%" >
      <strong><dhv:label name="">Detentore</dhv:label></strong>
    </th>
    <th width="10%" >
      <strong><dhv:label name="">Nome Canile</dhv:label></strong>
    </th>
    <th width="10%" >
      <strong><dhv:label name="">Operatore</dhv:label></strong>
    </th>
    <th width="10%" >
      <strong><dhv:label name="">Chippatore</dhv:label></strong>
    </th>
  </tr>
  <%

  	Vector<Regresso> regresso = (Vector<Regresso>)request.getAttribute( "regressoList" );
    Iterator itr = regresso.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        Regresso thisOperazione = (Regresso)itr.next();
        
        String descrTipo = "";
        if (thisOperazione.getTipo().equals("I"))
        	descrTipo = "Inserimento";
        if (thisOperazione.getTipo().equals("A"))
        	descrTipo = "Ri-Cattura";
        if (thisOperazione.getTipo().equals("D"))
        	descrTipo = "Decesso";
        if (thisOperazione.getTipo().equals("C"))
        	descrTipo = "Cessione";
        if (thisOperazione.getTipo().equals("F"))
        	descrTipo = "Furto";
        if (thisOperazione.getTipo().equals("R"))
        	descrTipo = "Registrato";
        if (thisOperazione.getTipo().equals("E"))
        	descrTipo = "Fuori Regione";
        if (thisOperazione.getTipo().equals("S"))
        	descrTipo = "Smarrimento";
        
    %>    
  <tr class="row<%=rowid%>">
	<td>
		<%= toHtml( thisOperazione.getMicrochip() ) %>
	</td>
	<td>
		<%= toHtml( thisOperazione.getTatuaggio() ) %>
	</td>
    <td>
    	<%= toHtml( thisOperazione.getData() ) %> 
	</td>
    <td>
    	<%= toHtml( thisOperazione.getUltima() ) %> 
	</td>
    <td>
    	<%= toHtml( descrTipo ) %> 
	</td>
    <td>
		<%= toHtml( thisOperazione.getProprietarioCognome() + " " + thisOperazione.getProprietarioNome() ) %>
    </td>
    <td>
		<%= toHtml( thisOperazione.getDetentoreCognome() + " " + thisOperazione.getDetentoreNome() ) %>
    </td>
    <td>
		<%= toHtml( thisOperazione.getCanileNome() ) %>
    </td>
    <td>
		<%= toHtml( thisOperazione.getOperatore() ) %>
    </td>
    <td>
		<%= toHtml( thisOperazione.getChippatore() ) %>
    </td>
   </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="4">
        <dhv:label name="">Nessuna Informazione Trovata</dhv:label>
      </td>
    </tr>
    <%
    }
    %></table>


<br />
<!-- dhv:pagedListControl object="SavedBeanListInfo"/ -->
<!-- </dhv:container> -->