<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<input type ="hidden" name = "idC" value="<%=request.getAttribute("idC") %>">
<input type ="hidden" name = "idNC" value="<%=request.getAttribute("idNC") %>">
	<%
	
	UserBean user=(UserBean)session.getAttribute("User");
if(user.getSiteId()==-1){	

%>  
<tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
          <%= SiteIdList.getSelectedValue(TicketDetails.getSiteId())%>
        <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
      
         
      
      </td>
    </tr>



<%}else{ %>
   
   
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%= SiteIdList.getSelectedValue(user.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=user.getSiteId()%>" >
      
      </td>
    </tr>

<%} %>
	<%
String idCU = TicketDetails.getIdControlloUfficiale() ;

%>


  
  <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="stabId" value="<%=OrgDetails.getIdStabilimento()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getIdAsl() %>" />
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



 
   <tr class="containerBody">
      <td name="followupPenali1" id="followupPenali1" nowrap class="formLabel">
        <dhv:label name="">Provvedimenti Adottati</dhv:label>
      </td>
    <td>
     <table border=0>
      <tr>
      <td >
          <%--Followup.setMultiple(false); --%>
     
        <%= Provvedimenti.getHtmlSelect("limitazioniFollowup",-1) %>
         
       
    		</td>
    		
       </tr>
       </table>
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
            <textarea name="problem" cols="55" rows="8" ><%= toString(TicketDetails.getNote()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
