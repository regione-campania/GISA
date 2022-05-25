<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<%@page import="org.aspcfs.utils.web.CustomLookupElement"%>
<%@page import="java.util.ArrayList"%><jsp:useBean id="TipoCampione_istologico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sierologico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriciCanili" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="ListaMc" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="Motivazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PianiMonitoraggio" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script type="text/javascript" src="dwr/interface/ServiziRestJson.js"> </script>
<script type="text/javascript" src="dwr/engine.js"></script>
<script type="text/javascript" src="dwr/util.js"></script>


<!-- JQUERY UI PER FINESTRE DI CONFERMA  -->
<script type="text/javascript" src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.9.2.custom.css" />

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script language="JavaScript">



function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
} 


</script>

<%
String idCU = "" ;
if(request.getAttribute("idControllo") !=null)
{
	  idCU = request.getAttribute("idControllo").toString();
 } 
	 
else {
	  idCU = "N.D";
}
%>
<tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">ASL</dhv:label>
      </td>
      <td>
 
 		<%if (OrgDetails.getSiteId()>0){ %>
       <%=SiteIdList.getSelectedValue(OrgDetails.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
     <%}
 		else
 		{
 			%> 
 			<%=SiteIdList.getSelectedValue(CU.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=CU.getSiteId()%>" >
 			<%
 			
 		}
 		%>
      </td>
    </tr>

  
  <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>
    
  </tr>
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
    <td>
      <%= idCU %>
      <input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale" value="<%= idCU%>"/>
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getParameter("idC") %>"/>
    </td>
  </tr>
  
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Provvedimenti adottati</dhv:label>
    </td>
    <td>
      <%=Provvedimenti.getHtmlSelect("provvedimenti",-1)%>
    </td>
	</tr>
      

  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
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