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
			<a href="PartiteCommerciali.do?command=ListaPartiteImportatori"><dhv:label name="">Partite commerciali importatori</dhv:label></a> 
			<%-- ><dhv:label name="product.editor">Editor</dhv:label> --%>
		</td>
	</tr>
</table>
<%-- End Trails --%>


<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="partiteListInfo"/>

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="7%"><strong><dhv:label name="product.name">Name</dhv:label></strong></th>
    <th><strong><dhv:label name="">Operatore Commerciale</dhv:label></strong></th>
    <th align="right"><strong><dhv:label name="">Quantità Animali</dhv:label></strong></th>
    <th align="right"><strong><dhv:label name="">Tipologia di partita</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="">Arrivo Previsto</dhv:label></strong></th>
    <!-- th align="center" nowrap><strong><dhv:label name="">Tipo Merce</dhv:label></strong></th-->
    <th align="center" nowrap><strong><dhv:label name="">Provenienza</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="">Percentuale di approvati</dhv:label></strong></th>
    <th width="7%"><strong>&nbsp;</strong></th>
  </tr>
  <dhv:evaluate if="<%= partiteList.size() == 0 %>">
    <tr class="row2">
      <td colspan="6"><dhv:label name="product.noItemsMsg">No items to display.</dhv:label></td>
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
   
    <td width="100%">
  <a href="PartiteCommerciali.do?command=ListaAnimaliFromPartitaImportatori&idPartita=<%=thisPartita.getIdPartitaCommerciale() %>">   <%= toHtml(thisPartita.getNrCertificato()) %></a>
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
    <td><%=thisPartita.getPercentualeApprovati() %>% </td>
    <td>
    <input type = "button" value = "VALIDA" onclick="document.location.href='PartiteCommerciali.do?command=ListaAnimaliFromPartitaImportatori&idPartita=<%=thisPartita.getIdPartitaCommerciale() %>'">
    <br><br>
    <dhv:evaluate if ="<%=(thisPartita.getNumeroAnimaliValidati() > 0) %>">
    <input type = "button" value = "IMPORTA" onclick="document.location.href='PartiteCommerciali.do?command=InsertAnimaleFromPartitaImportatori&idPartita=<%=thisPartita.getIdPartitaCommerciale() %>'">
    <br><br>
    </dhv:evaluate>
    <input type = "button" value = "RESPINGI" onclick="document.location.href='PartiteCommerciali.do?command=RespingiPratica&idPartita=<%=thisPartita.getIdPartitaCommerciale() %>'">
    </td>
    
  </tr>
  <%
    }
  %>
</table>
</form>
<dhv:pagedListControl object="partiteListInfo"/>