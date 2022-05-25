<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<%@ page import="org.aspcfs.modules.base.Constants" %>

<%@page import="org.aspcfs.modules.allerte.base.Ticket"%><jsp:useBean id="TicketDetails" class="org.aspcfs.modules.allerte.base.Ticket" scope="request"/>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoAlimento" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Origine" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentoInteressato" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaCommercializzazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="TicketTypeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="TicListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="userGroup" class="org.aspcfs.modules.admin.base.UserGroup" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactDetails" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popContacts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
    
    if (formTest == false) {
      alert(label("check.campaign.criteria","Criteria could not be processed, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
  
  function getSiteId() {
    var site = document.forms['searchTicket'].searchcodeSiteId;
    var siteId = '';
    if ('<%= User.getUserRecord().getSiteId() == -1 && SiteList.size() > 2 %>' == 'true') {
      siteId = site.options[site.options.selectedIndex].value;
    } else {
      siteId = site.value;
    }
    if (siteId == '<%= Constants.INVALID_SITE %>') {
      siteId = '&includeAllSites=true';
    } else {
      siteId = siteId + '&thisSiteIdOnly=true';
    }
    return siteId
  }
  
  function clearSiteSpecificInfo() {
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount',label('label.all','All'));
    //document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    //changeDivContent('changeowner',label('label.anyone','Anyone'));
    //document.forms['searchTicket'].searchcodeUserGroupId.value="-1";
    //changeDivContent('changegroup',label('label.any','Any'));
  }

  function clearForm() {
   // document.forms['searchTicket'].searchcodeId.value="";
    document.forms['searchTicket'].searchDescription.value="";
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount',label('label.all','All'));
    //document.forms['searchTicket'].searchcodeSeverity.options.selectedIndex = 0;
    //document.forms['searchTicket'].searchcodePriority.options.selectedIndex = 0;
    //document.forms['searchTicket'].listFilter1.options.selectedIndex = 0;
    //document.forms['searchTicket'].searchcodeEscalationLevel.options.selectedIndex = 0;
    //document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    //changeDivContent('changeowner',label('label.anyone','Anyone'));
    //document.forms['searchTicket'].searchcodeUserGroupId.value="-1";
    //changeDivContent('changegroup',label('label.any','Any'));
    <dhv:evaluate if="<%=User.getSiteId() == -1 && SiteList.size() > 2 %>" >
    document.forms['searchTicket'].searchcodeSiteId.options.selectedIndex = 0;
    </dhv:evaluate>
  }
  
  function clearAccount(){
    document.forms['searchTicket'].searchcodeOrgId.value="-1";
    changeDivContent('changeaccount',label('label.all','All'));
  }
  
  function clearOwner(){
    document.forms['searchTicket'].searchcodeAssignedTo.value="-1";
    changeDivContent('changeowner',label('label.anyone','Anyone'));
  }
</script>
<body onLoad="javascript:document.searchTicket.searchcodiceAllerta.focus();">
<form name="searchTicket" action="TroubleTicketsAllerte.do?command=SearchTickets" method="post" onSubmit="javascript:return checkForm(this);">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="TroubleTicketsAllerte.do"><dhv:label name="sanzioniaaa">Allerte</dhv:label></a> > 
<dhv:label name="tickets.searchForm">Search Form</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="sanzioni.cercass">Form Ricerca</dhv:label></strong>
    </th>
  </tr>
  <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.numerodd">Codice allerta</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="searchcodiceAllerta" value="<%= TicListInfo.getSearchOptionValue("searchcodeId") %>">
    </td>
  </tr>
   <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.numerodd">Data apertura</dhv:label>
    </td>
    <td>
       Dal <zeroio:dateSelect form="searchTicket" field="searchtimestampDataApertura"  timestamp="<%= TicListInfo.getSearchOptionValue("searchtimestampDataApertura") %>"   showTimeZone="false" />     
       al <zeroio:dateSelect form="searchTicket" field="searchtimestampDataApertura2"  timestamp="<%= TicListInfo.getSearchOptionValue("searchtimestampDataApertura2") %>"   showTimeZone="false" />     
    </td>
  </tr>
  
  		<tr>
          <td class="formLabel">
            <dhv:label name="stabilimenti.accountStatusss">Stato allerta (Regione)</dhv:label>
          </td>
          <td align="left" valign="bottom">
          <%
          String stato = TicListInfo.getSearchOptionValue("searchcodeStato");
          %>
            <select size="1" name="searchcodeStato">
              <option value="-1" <%=("-1".equals( stato )) ? ("selected=\"selected\"") : ("") %> >Tutti</option>
              <option value="1" <%=("1".equals( stato )) ? ("selected=\"selected\"") : ("") %> ><%=Ticket.ATTIVA %></option>
              <option value="2" <%=("2".equals( stato )) ? ("selected=\"selected\"") : ("") %> ><%=Ticket.INATTIVA %></option>
            </select>
          </td>
  		</tr>
  		
  	<%if( User.getSiteId() > 0 ){ %>
  		<tr>
          <td class="formLabel">
            Stato allerta (<%=SiteList.getSelectedValue( User.getSiteId() ) %>)
          </td>
          <td align="left" valign="bottom">
          <%
          String stato_asl = TicListInfo.getSearchOptionValue("searchcodeStatoAslCorrente");
          %>
            <select size="1" name="searchcodeStatoAslCorrente">
              <option value="-1" <%=("-1".equals( stato_asl )) ? ("selected=\"selected\"") : ("") %> >Tutti</option>
              <option value="1" <%=("1".equals( stato_asl )) ? ("selected=\"selected\"") : ("") %> ><%=Ticket.ATTIVA %></option>
              <option value="2" <%=("2".equals( stato_asl )) ? ("selected=\"selected\"") : ("") %> ><%=Ticket.CONTROLLI_IN_CORSO %></option>
              <option value="3" <%=("3".equals( stato_asl )) ? ("selected=\"selected\"") : ("") %> ><%=Ticket.CONTROLLI_COMPLETATI %></option>
              <option value="4" <%=("4".equals( stato_asl )) ? ("selected=\"selected\"") : ("") %> ><%=Ticket.CHIUSA %></option>
            </select>
          </td>
  		</tr>
  	<%} %>
  		
	<!-- aggiunto da d.dauria -->
	<tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.sitewww">Tipo Alimento</dhv:label>
    </td>
    <td>
      <%= TipoAlimento.getHtmlSelect("searchcodeTipoAlimento", TicListInfo.getSearchOptionValue("searchcodeTipoAlimento") ) %>
    </td>
  </tr>
  
  <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.sitewww">Origine</dhv:label>
    </td>
    <td>
      <%= Origine.getHtmlSelect("searchcodeOrigine", TicListInfo.getSearchOptionValue("searchcodeOrigine") ) %>
    </td>
  </tr>
  
    <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.sitewww">Alimento Interessato</dhv:label>
    </td>
    <td>
      <%= AlimentoInteressato.getHtmlSelect("searchcodeAlimentoInteressato", TicListInfo.getSearchOptionValue("searchcodeAlimentoInteressato") ) %>
    </td>
  </tr>
      <tr>
    <td nowrap class="formLabel">
      <dhv:label name="accounts.sitewww">Lista di Commercializzazione</dhv:label>
    </td>
    <td>
      <%= ListaCommercializzazione.getHtmlSelect("searchcodeListaCommercializzazione", TicListInfo.getSearchOptionValue("searchcodeListaCommercializzazione") ) %>
    </td>
  </tr>
  
  <tr>
    <td class="formLabel">
      <dhv:label name="richieste.note">Issue</dhv:label>
    </td>
    <td>
      <input type="text" size="40" name="searchDescription" value="<%= TicListInfo.getSearchOptionValue("searchDescription") %>">
    </td>
  </tr>
	<!-- fine delle modifiche -->
  <dhv:evaluate if="<%= SiteList.size() > 2 %>">
  <tr>
    <td nowrap class="formLabel">
      A.S.L. Coinvolta
    </td>
    <td>
     <dhv:evaluate if="<%=User.getSiteId() == -1 %>" >
      <% SiteList.setJsEvent("onChange=\"clearSiteSpecificInfo();\""); %>
      <%= SiteList.getHtmlSelect("searchcodeSiteId", ("".equals(TicListInfo.getSearchOptionValue("searchcodeSiteId")) ? String.valueOf(Constants.INVALID_SITE) : TicListInfo.getSearchOptionValue("searchcodeSiteId"))) %>
     </dhv:evaluate>
     <dhv:evaluate if="<%=User.getSiteId() > -1 %>" >
      <input type="hidden" name="searchcodeSiteId" value="<%= User.getSiteId() %>">
      <%= SiteList.getSelectedValue(User.getSiteId()) %>
     </dhv:evaluate>
    </td>
  </tr>
  </dhv:evaluate> 
  <dhv:evaluate if="<%= SiteList.size() <= 2 %>">
    <input type="hidden" name="searchcodeSiteId" id="searchcodeSiteId" value="-1" />
  </dhv:evaluate>
</table>
<br>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<%-- input type="button" value="<dhv:label name="accouna.accountasset_include.clear">Pulisci</dhv:label>" onClick="javascript:clearForm();" --%>
</form>
</body>
