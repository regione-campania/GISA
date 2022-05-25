<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.*" %>

<%@page import="org.aspcfs.modules.opu.base.Operatore"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>


<jsp:useBean id="OrgList" class="org.aspcfs.modules.opu.base.OperatoreList" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>

<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AuditList" class="org.aspcfs.modules.audit.base.AuditList" scope="request"/>
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>

</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= (User.getRoleType() > 0) %>" >
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="accounts.manage">Select an account to manage.</dhv:label></td>
  </tr>
</table>
</dhv:evaluate>
<%
String param = "stabId=-1&opId="+Operatore.getIdOperatore();
%>
<dhv:container name="opu" selected="cessazionevariazione"
	object="Operatore" param="<%=param%>"
	appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>


<dhv:permission name=""><a href="StabilimentoAction.do?command=Add&flagDia=true&idOp=<%=Operatore.getIdOperatore() %>"><dhv:label name="">Aggiungi Dia</dhv:label></a></dhv:permission>

<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="SearchOrgListInfo"/>
<% int columnCount = 0; %>

<% java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
Timestamp d = new Timestamp (datamio.getTime()); %>
<table cellpadding="8" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    
    <th nowrap <% ++columnCount; %>>
      <strong><a href="Accounts.do?command=Search&column=o.name"><dhv:label name="">Impresa</dhv:label></a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
   <th nowrap <% ++columnCount; %>>

      <strong><dhv:label name="organization.accountNumber">Numero Registrazione</dhv:label></strong>

    </th>
    <th nowrap <% ++columnCount; %>>
          <strong>Targa Autoveicolo/Sede Operativa</strong>
		</th>
    
    
         <th <% ++columnCount; %>>
          <strong><dhv:label name="accounts.site">Site</dhv:label></strong>
        </th>
      
        <th nowrap <% ++columnCount; %>>
          <strong>Partita IVA</strong>
		</th>
		 <th nowrap <% ++columnCount; %>>
          <strong>Codice Fiscale</strong>
		</th>
        <th nowrap <% ++columnCount; %>>
          <strong>Codice ISTAT Principale</strong>
		</th>
		<th nowrap <% ++columnCount; %>>
      <strong>Categoria Rischio</strong>
    </th>
    <th nowrap <% ++columnCount; %>>
      <strong>Stato</strong>
    </th>
    
        <th nowrap <% ++columnCount; %>>
          <strong>Inserito da</strong>
		</th>
		<th nowrap <% ++columnCount; %>>
          <strong>Modificato da</strong>
		</th>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() || OrgList.iterator().hasNext() ) {
    int rowid = 0;
    int i = 0;
	j = OrgList.iterator();
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Operatore thisOrg = (Operatore)j.next();
%>
  <tr class="row<%= rowid %>">
    
	<td>
	
	<%if (thisOrg.getListaStabilimenti().size()>0) {%>
      <a href="StabilimentoAction.do?command=Details&stabId=<%=((Stabilimento)thisOrg.getListaStabilimenti().get(0)).getIdStabilimento() %>&opId=<%=thisOrg.getIdOperatore()%>">
      <%}%>
      <%= toHtml(thisOrg.getRagioneSociale()) %>
      </a>
	</td>
	
	<td>
     <%= (thisOrg.getListaStabilimenti().size()>0) ? toHtmlValue(((Stabilimento) thisOrg.getListaStabilimenti().get(0) ).getInfoStab().getCodice_registrazione()) : "&nbsp;"%>	
	</td>

     <td>
 <%= (thisOrg.getListaStabilimenti().size()>0) ? ((Stabilimento) thisOrg.getListaStabilimenti().get(0) ).getSedeOperativa().getDescrizioneComune() : "&nbsp;"%>	
 </td> 
	
		
        <td valign="top" nowrap>
        <%= (thisOrg.getListaStabilimenti().size()>0) ? SiteIdList.getSelectedValue( ((Stabilimento) thisOrg.getListaStabilimenti().get(0) ).getSedeOperativa().getIdAsl()):"&nbsp;" %>	
        </td>  


	  
    
	<td nowrap>
       <%= toHtml(thisOrg.getPartitaIva()) %>
       </td>
       <td>
       <%= toHtml(thisOrg.getCodFiscale()) %>
	</td>
	<td nowrap>
     BOOOO
	</td>
	<td>
	<%= "Da GESTIRE" %>
	</td>
	<td>
	
		<%="da gestire" %>
	</td>
    <td nowrap>
      <dhv:username id="<%= thisOrg.getEnteredBy() %>" />
	</td>
	<td nowrap>
      <dhv:username id="<%= thisOrg.getModifiedBy() %>" />
	</td>
  </tr>
<%}%>
<%} else {%>

  <tr class="containerBody">
    <td colspan="<%= SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId").equals(String.valueOf(Constants.INVALID_SITE))?columnCount+1:columnCount %>">
      Non Presenti Dia Per Impresa<br />   
    </td>
  </tr>
<%}%>
</table>
<br />
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>
</dhv:container>

