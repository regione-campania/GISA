<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.microchip.base.*,org.aspcfs.utils.web.*,java.util.*,java.text.DateFormat" %>
<jsp:useBean id="microchipList" class="org.aspcfs.modules.microchip.base.MicrochipList" scope="request"/>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LookupSpecie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MicrochipListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="Microchip.do">Cerca microchip</a> > 
  <dhv:label name="accounts.SearchResults">Search Results</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<% if ( request.getAttribute("statistiche") != null ) { %>
<table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
	<tr>
		<th colspan="2">
			<strong>Statistiche</strong>
		</th>
	</tr>
	<tr>
		<td class="formLabel2"> Microchip Totali </td>
		<td>
			<%= request.getAttribute("mcTotali") != null ? request.getAttribute("mcTotali") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Microchip Assegnati (Canina)</td>
		<td>
			<%= request.getAttribute("mcAssegnati") != null ? request.getAttribute("mcAssegnati") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Microchip Assegnati (Felina)</td>
		<td>
			<%= request.getAttribute("mcAssegnatiFelina") != null ? request.getAttribute("mcAssegnatiFelina") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Microchip Asseganti (Furetti) </td>
		<td>
			<%= request.getAttribute("mcAssegnatiFuretti") != null ? request.getAttribute("mcAssegnatiFuretti") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Microchip Non Assegnati</td>
		<td>
			<%= request.getAttribute("mcNonAssegnati") != null ? request.getAttribute("mcNonAssegnati") : "" %>
		</td>
	</tr>
	<tr>
		<td class="formLabel2"> Microchip Fuori Uso</td>
		<td>
			<%= request.getAttribute("mcFuoriUso") != null ? request.getAttribute("mcFuoriUso") : "" %>
		</td>
	</tr>
	
</table>
<br>
<% } %>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="MicrochipListInfo"/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
    <tr>
      <th nowrap>
        <strong>Microchip</strong>
      </th>
      <th nowrap>
        <strong><%=( request.getAttribute("veterinario") !=null ? "Codice Fiscale" : "ASL" )%></strong>
      </th>
      <th nowrap width="10%">
        <strong>Assegnato</strong>
      </th>
      <th nowrap width="10%">
        <strong>Abilitato</strong>
      </th>
      
      <th nowrap width="10%">
        <strong>Caricato da</strong>
      </th>
      
       <th nowrap width="10%">
        <strong>Caricato il</strong>
      </th>
      
    </tr>
  <%
    Iterator itr = microchipList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        Microchip mc = (Microchip)itr.next();
        
        
    %>      
    <tr class="row<%= rowid %>">
      <td>
      <%if (mc.getIdAnimale()>0 && (mc.getTrashed_date()==null)){ %>
       <a href="AnimaleAction.do?command=Details&animaleId=<%=mc.getIdAnimale() %>&idSpecie=<%=mc.getIdSpecie() %>"> <%= toHtml(mc.getMicrochip()) %>&nbsp;</a>
        <%}else{ %>
        <%= toHtml(mc.getMicrochip()) %>&nbsp;
        <%}%>
      </td>
      <td>
        <%= toHtml(mc.getAsl()) %>&nbsp;
      </td>
      <td width="10%">
        <%= (mc.getIdAnimale()>0 && mc.getTrashed_date()==null && mc.isAbilitato()) ?  "SI : "+LookupSpecie.getSelectedValue(mc.getIdSpecie()) :  "NO"  %>&nbsp;
      </td>
      <td width="10%">
        <%=  (mc.isAbilitato()) ?  "SI" : "NO"  %>&nbsp;
      </td>
      
         <td width="10%">
        <dhv:username id="<%= mc.getEnteredBy() %>"></dhv:username>   &nbsp;
      </td>
      
        <td width="10%">
     	 <%=toDateasString(mc.getDataCaricamento()) %>   <%=  (mc.getImportId()>0) ?  "MASSIVO DA FILE" : ""  %>&nbsp; &nbsp;
      </td>
      
      
    </tr>
    <%  
      }
    }else{
    %>
    <tr class="containerBody">
      <td colspan="3">
        Nessun microchip trovato.
      </td>
    </tr>
    <%
    }
    %>
</table>
<br>
<dhv:pagedListControl object="MicrochipListInfo"/>