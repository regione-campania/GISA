<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.praticacontributi.base.Pratica,org.aspcfs.modules.base.*" %>

<jsp:useBean id="praticaList" class="org.aspcfs.modules.praticacontributi.base.PraticaList" scope="request"/>
<jsp:useBean id="parent" class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request"/>
<jsp:useBean id="SearchPraticaListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PraticaListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="praticaStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script language="JavaScript" type="text/javascript">



  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  function reopen() {
    scrollReload('PraticaContributi.do?command=Search');
  }
</script>

<table class="trails" cellspacing="0">
<tr>
	<td>
		<a href="PraticaContributi.do"><dhv:label name="">Pratica Contributi</dhv:label></a> > 
		<dhv:label name="">Risultati Ricerca</dhv:label>
		</td>
</tr>
</table>


<%
if (parent != null && parent.getId() != -1 && parent.getParentList() != null && parent.getParentList().size() > 0) {
	Iterator iter = (Iterator) parent.getParentList().iterator();
	while (iter.hasNext()) {
		Pratica parentPratica = (Pratica) iter.next();
		String param1 = "id=" + parentPratica.getId() + "|parentId=" + parentPratica.getId();
%>
	<dhv:container name="pratica" selected="billofmaterials" object="parentPratica" item="<%= parentPratica %>" param="<%= param1 %>"  appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'/>
<% }} %>


<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="PraticaListInfo"/>

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList" >
	<tr>
	<th width="10%" >
    		<strong>
    			<dhv:label name="">ASL</dhv:label>
    		</strong>
  		</th> 
		<th width="10%" >
    		<strong>
    			<dhv:label name="">Numero Decreto</dhv:label>
    		</strong>
    		 <%= SearchPraticaListInfo.getSortIcon("") %>
  		</th> 
  		<th width="20%" nowrap="true">
    		<strong>
    			<a href="">
    				<dhv:label name="">Data Decreto</dhv:label>
    			</a>
    		</strong>
		    <%= SearchPraticaListInfo.getSortIcon("") %>
  		</th>
  		<th width="10%" >
    		<strong>
    			<dhv:label name="">Data Inizio Piano Sterilizzazione</dhv:label>
    		</strong>
	   </th>   
  		<th width="10%" >
    		<strong>
    			<dhv:label name="">Data Fine Piano Sterilizzazione</dhv:label>
    		</strong>
	   </th>
	   <!-- Flusso 251: modifiche del 03/08 - INIZIO -->
	   <!-- th width="10%" >
    		<strong>
    			<dhv:label name="">Cani maschi sterilizzati</dhv:label>
    		</strong>
	   </th>
	   <th width="10%" >
    		<strong>
    			<dhv:label name="">Cani femmina sterilizzati</dhv:label>
    		</strong>
	   </th -->
	   <!-- Flusso 251: modifiche del 03/08 - FINE -->
	   
	   <dhv:evaluate if='<%= SearchPraticaListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE) %>'>
  		<th width="10%" >
    		<strong>
    			<dhv:label name="praticacontributi.site">Site</dhv:label>
    		</strong>
	   </th>
  </dhv:evaluate>
	     
  	</tr>
	
  	<%
	Iterator j = praticaList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      		Pratica thisContact = (Pratica)j.next();
%>      
 <tr class="row<%=rowid %>">
  	    <%--  <td valign="center" nowrap>--%>
        	<%-- Use the unique id for opening the menu, and toggling the graphics --%>
        <%-- 	<a href="javascript:displayMenu('select<%= i %>','menuContact', '<%= thisContact.getOrgId() %>', '<%= thisContact.getId() %>', '<%= thisContact.getPrimaryContact() %>','<%= (((thisContact.getTrashedDate() != null) || !thisContact.getEnabled()) || ((thisContact.getOrgTrashedDate() != null) || !thisContact.getOrgEnabled()))%>');"
         	onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>--%>
      	<%-- %></td>--%>
      	<td valign="center">
    	    <%=(thisContact.getDescrizioneAslPratica()==null)?("Non previsto"):(thisContact.getDescrizioneAslPratica())%>
      	</td>
	  	<td valign="center">
    	    <a href="PraticaContributi.do?command=Details&id=<%=thisContact.getId()%>"><%=thisContact.getNumeroDecretoPratica()%></a>
      	</td>
      	<td valign="center">
    	   <%=thisContact.getDataDecretoFormattata()%>
      	</td>
      	<td valign="center"><%=thisContact.getDataInizioSterilizzazioneStringa()%>
   
   	</td>
      	<td valign="center"><%=thisContact.getDataFineSterilizzazioneStringa()%>
      	</td>
      	 <!-- Flusso 251: modifiche del 03/08 - INIZIO -->
      	<!-- td valign="center"><!--%if(thisContact.getIdTipologiaPratica()!=Pratica.idPraticaLP){out.println("Non previsto");}else{out.println(thisContact.getTotaleCaniMaschi()-thisContact.getCaniRestantiMaschi() + "/" + thisContact.getTotaleCaniMaschi());}%>
      	</td>
      	<td valign="center"><!--%if(thisContact.getIdTipologiaPratica()!=Pratica.idPraticaLP){out.println("Non previsto");}else{out.println(thisContact.getTotaleCaniFemmina()-thisContact.getCaniRestantiFemmina() + "/" + thisContact.getTotaleCaniFemmina());}%>
      	</td -->
      	 <!-- Flusso 251: modifiche del 03/08 - FINE -->
  	
  	</tr>
  	<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="<%= SearchPraticaListInfo.getSearchOptionValue("searchcodeSiteId") == String.valueOf(Constants.INVALID_SITE)?"7":"6" %>">
        Nessuna Pratica disponibile.
      </td>
    </tr>
<%}%>
</table>


<dhv:pagedListControl object="PraticaListInfo" tdClass="row1"/>
