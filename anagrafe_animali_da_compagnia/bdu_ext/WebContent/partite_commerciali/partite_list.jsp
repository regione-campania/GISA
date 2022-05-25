<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.partitecommerciali.base.*" %>


<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="partiteList" class="org.aspcfs.modules.partitecommerciali.base.PartiteCommercialiList" scope="request"/>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="nazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="PartiteCommerciali.do?command=ListaPartite"><dhv:label name="">Partite commerciali</dhv:label></a> 
			<%-- ><dhv:label name="product.editor">Editor</dhv:label> --%>
		</td>
	</tr>
</table>
<%-- End Trails --%>


<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="partiteListInfo"/>

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" align="center" nowrap>&nbsp;</th>
    <th width="100%"><strong><dhv:label name="product.name">Name</dhv:label></strong></th>
    <th><strong><dhv:label name="">Operatore Commerciale</dhv:label></strong></th>
    <th align="right"><strong><dhv:label name="">Quantità Animali</dhv:label></strong></th>
    <th align="right"><strong><dhv:label name="">Tipologia di partita</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="">Arrivo Previsto</dhv:label></strong></th>
    <!-- th align="center" nowrap><strong><dhv:label name="">Tipo Merce</dhv:label></strong></th-->
    <th align="center" nowrap><strong><dhv:label name="">Provenienza</dhv:label></strong></th>
  </tr>
  <dhv:evaluate if="<%= partiteList.size() == 0 %>">
    <tr class="row2">
      <td colspan="7"><dhv:label name="product.noItemsMsg">No items to display.</dhv:label></td>
    </tr>
  </dhv:evaluate>
<%
  int rowid = 0;
  int count = 0;
  
    Iterator j = partiteList.iterator();
    while (j.hasNext()) {
      PartitaCommerciale thisPartita = (PartitaCommerciale) j.next();
      rowid = (rowid != 1 ? 1 : 2);
      count++;
  %>
  <tr class="row<%= rowid %>">
    <td align="center" nowrap>
      <a href="javascript:displayMenu('select<%= count %>', 'menuCatalog', <%= thisPartita.getIdPartitaCommerciale() %>,<%= thisPartita.getIdPartitaCommerciale() %>, -1, 'PRODUCT','<%= false %>');"
        onMouseOver="over(0, <%= count %>)" onmouseout="out(0, <%= count %>); hideMenu('menuCatalog');">
        <img src="images/select.gif" name="select<%= count %>" id="select<%= count %>" align="absmiddle" border="0"></a>
    </td>
    <td width="100%">
      <a href="PartiteCommerciali.do?command=DettagliPartita&amp;idPartita=<%=thisPartita.getIdPartitaCommerciale() %>"><%= toHtml(thisPartita.getNrCertificato()) %></a>
    </td>
    <td nowrap>
    <dhv:evaluate if="<%= (thisPartita.getIdImportatore() > -1) %>">
      <%= toHtml(thisPartita.getOperatoreCommerciale().getRagioneSociale()) %>
    </dhv:evaluate>
    </td>
    <td align="right" nowrap>
      <dhv:evaluate if="<%= thisPartita.getNrAnimaliPartita() > 0 %>">
        <%=  thisPartita.getNrAnimaliPartita() %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisPartita.getNrAnimaliPartita() == -1 %>">
        --
      </dhv:evaluate>
    </td>
    <td align="right" nowrap>
		<%=specieList.getSelectedValue(thisPartita.getIdTipoPartita()) %>
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= thisPartita.getDataArrivoPrevista() %>" dateOnly="true" default="&nbsp;"/>
    </td>

    <td align="center" nowrap>
    	<% if ( thisPartita.getIdNazioneProvenienza() > -1 ) { %>
    		<%= nazioniList.getSelectedValue(thisPartita.getIdNazioneProvenienza()) %>
    	<% } %>
    </td>
  </tr>
  <%
    }
  %>
</table>
</form>
<dhv:pagedListControl object="partiteListInfo"/>