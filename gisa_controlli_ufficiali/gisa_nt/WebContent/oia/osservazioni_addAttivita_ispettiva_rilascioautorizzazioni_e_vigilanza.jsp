<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.oia.base.Organization" scope="request"/>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.text.DateFormat, org.aspcfs.modules.actionplans.base.*" %>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>

<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.osservazioni.base.Osservazioni" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Osservazioni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaAmministrative" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformitaPenali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="NonConformita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/osservazioni.js"></SCRIPT>
<script type="text/javascript" src="tabber.js"></script>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<link rel="stylesheet" href="example.css" type="text/css" media="screen"></link>

<div  id = "nc" >
<div>
<body onunload="deleteAttivita('<%=CU.getId() %>')" >
<%


String idMacchinetta="";

if (request.getAttribute("idMacchinetta")!=null)
	{
	idMacchinetta=""+OrgDetails.getOrgId();
	
	} %>
<form name="addticket" action="OiaOsservazioni.do?command=Insert&idNodo=<%=idMacchinetta %>&auto-populate=true&" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Oia.do?command=SearchForm"><dhv:label name="">Autorità Competenti</dhv:label></a> > 

<a href="Oia.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>&idNodo=<%=idMacchinetta %>"><dhv:label name="">Scheda Dipartimento</dhv:label></a> >
 <a href="OiaVigilanza.do?command=ViewVigilanza&idNodo=<%=idMacchinetta %>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
 <a href="OiaVigilanza.do?command=TicketDetails&idNodo=<%=idMacchinetta %>&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
 

<%-- %>a href="Accounts.do?command=ViewNonConformita&orgId=<%=OrgDetails.getOrgId() %>"><dhv:label name="nonconformitaa"> Non Conformità Rilevate</dhv:label></a> --%> 
<dhv:label name="nonconformita.aggiungi">Aggiungi Osservazione/Raccomandazione</dhv:label>

</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" id = "btn_salva" value="Inserisci>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla" onClick="window.location.href='OiaVigilanza.do?command=TicketDetails&id=<%=CU.getId() %>&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&idNodo=<%=idMacchinetta %>';this.form.dosubmit.value='0';" />

<br>
<dhv:formMessage />
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>



<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">

      <strong><dhv:label name="">Aggiungi Osservazione/Raccomandazione</dhv:label></strong>

    </th>
	</tr>
	
   <%
  int aslcu= -1 ;
   if (request.getAttribute("aslCU")!=null && !request.getAttribute("aslCU").equals("null") && ! request.getAttribute("aslCU").equals("") )
	   {
	   aslcu = Integer.parseInt((String)request.getAttribute("aslCU"));
	   }
  %>
   <dhv:include name="stabilimenti-sites" none="true">
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
      <%
	UserBean user=(UserBean)session.getAttribute("User");
   
      
      int idAsl = (OrgDetails.getSiteId())>0 ? OrgDetails.getSiteId() : 14 ;
     
      
      %>
      
      
       <%= SiteIdList.getSelectedValue(idAsl)%>
          <input type="hidden" name="siteId" value="<%=idAsl%>" >
      
      </td>
    </tr>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>
    <td class="formLabel">
      <dhv:label name="nonconformita.richiedente">Ragione Sociale O.S.A.</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
  
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
    <td>
      <%= (String)request.getAttribute("idControllo") %>
      <input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale" value="<%= (String)request.getParameter("idControllo") %>">
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getParameter("idC") %>">
    </td>
  </tr>
   <% String dataC = request.getAttribute("dataC").toString(); %>
   <tr class="containerBody" style="display: none">
    <td nowrap class="formLabel">
      <dhv:label name="">Data </dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="assignedDate" timestamp="<%= dataC %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <%if(request.getAttribute("idIspezione")!=null && !request.getAttribute("idIspezione").equals("3")){ %>
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.azioni">Punteggio Totale</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <input type="text" readonly="readonly" name="punteggio" value="" id="totale" title="INUTILE CHE SBAREI : punteggio determinato automaticamente dal sistema">
          </td>
         
        </tr>
       </table>
    </td>
  </tr>
 <%}else
	 {
	if(! (""+CU.getTipoIspezione()).equals("3")){ %>
	  <tr>
	    <td valign="top" class="formLabel">
	      <dhv:label name="nonconformita.azioni">Punteggio Totale</dhv:label>
	    </td>
	    <td>
	      <table border="0" cellspacing="0" cellpadding="0" class="empty">
	        <tr>
	          <td>
	            <input type="text" readonly="readonly" name="punteggio" value="" id="totale">
	          </td>
	         
	        </tr>
	       </table>
	    </td>
	  </tr>
	 <%
	 }} %>
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.note">Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
           
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
	</table>
  
  
 <%@ include file="../nonconformita/osservazioni_add.jsp" %>
  
 
  
  

<input type = "hidden" id = "dosubmit" name = "dosubmit" value = "0">
<input type="hidden" name="close" value="">
<input type="hidden" name="refresh" value="-1">
<input type="hidden" name="modified" value="<%=  TicketDetails.getModified() %>" />
<input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
<input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
<input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
<%= addHiddenParams(request, "popup|popupType|actionId") %>


<br><input type="submit" id = "btn_salva2"  value="Inserisci>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla" onClick="window.location.href='OiaVigilanza.do?command=TicketDetails&id=<%=CU.getId() %>&orgId=<%= OrgDetails.getOrgId() %><%= addLinkParams(request, "popup|popupType|actionId") %>&idNodo=<%=idMacchinetta %>';this.form.dosubmit.value='0';" />


<br><br>

   <%-- <% String dataR = request.getAttribute("dataR").toString(); --%>
  <table cellpadding="2" cellspacing="0" width="50%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Dettagli Risoluzione</dhv:label></strong>
    </th>
	</tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">Data di risoluzione</dhv:label>
      </td>
       <td>
      	<zeroio:dateSelect form="addticket" field="resolutionDate" timestamp="<%= dataC %>"  timeZone="" showTimeZone="false" />
      		
    	</td>
    </tr>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Risolto</dhv:label>
    </td>
    <td>
     <input type="radio" value="SI" name="resolvable" id="resolvable">SI
     <input type="radio" value="NO" name="resolvable" id="resolvable">NO
    </td>
  </tr>
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="nonconformita.azioni">Descrizione</dhv:label>
    </td>
    <td>
      <textarea  name="note_altro" cols="55" rows="8"></textarea>
    </td>
         
    </tr>
  </table>
 

</form>


</body>

</div>
</div>