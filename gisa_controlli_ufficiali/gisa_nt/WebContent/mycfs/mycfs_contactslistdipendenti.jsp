<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.modules.base.Filter,org.aspcfs.modules.base.EmailAddress" %>
<%@page import="org.apache.batik.svggen.font.table.LookupList"%>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="listaAsl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
 
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>


<%-- Navigating the contact list, not the final submit --%>
<body onLoad="">
<form name="contactListView" method="post" action="ContactsList.do?command=ContactList&dipendenti=dipendenti">
  <table cellpadding="6" cellspacing="0" width="100%" border="0">
		<tr><td bgcolor="#d3d1d1" colspan="2" align="center"><strong><dhv:label name="button.search">Search</dhv:label></strong></td></tr>
		<tr>
		
			<td   bgcolor="#d3d1d1">
				Nome e/o Cognome
				</td>
				<td bgcolor="#d3d1d1"><input type="text" name="lastName" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("lastName")) %>"> <b>, </b>
				</td></tr>
				<tr>
				<td bgcolor="#d3d1d1">
				Cognome</td>
				<td bgcolor="#d3d1d1">
				 <input type="text" name="firstName" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("firstName")) %>">
				</td>
				</tr>
				<tr>
				<td bgcolor="#d3d1d1">
				<%
				ArrayList<Role> listaruoli = (ArrayList<Role>)request.getAttribute("listaRuoli");
				String idR=request.getParameter("ruolo");
				String idA=request.getParameter("asl");
		%>
				 Ruolo 
				 </td>
				 <td bgcolor="#d3d1d1">
				<select multiple name = "ruolo" size="11">
				<%
				for(Role ruolo : listaruoli)
				{
				%>
				<option value = "<%=ruolo.getId() %>" ><%=ruolo.getDescription() %></option>

				<%} %>
				</select>
				</td></tr>
				<tr>
				<td bgcolor="#d3d1d1">
				Asl
				</td><td bgcolor="#d3d1d1">
				<%if(idA!=null) {%>
				<%=listaAsl.getHtmlSelect("asl",Integer.parseInt(idA)) %>
				<%}else
					{%>
						<%=listaAsl.getHtmlSelect("asl",-1) %>
					<%} %>
				
				</td>
				</tr>
				<tr>
				<td bgcolor="#d3d1d1">
				<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
				</td>
				<td bgcolor="#d3d1d1">
				<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="clearSearchFields(true, '')">
			</td>
		</tr>
		
	</table>
	&nbsp;<br>
 

   <%@ include file="messaggi_contactlist_include.jsp" %>

  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:addItem(<%=ContactList.size() %>)">
  <input type="button" value="Annulla" onClick="javascript:window.close();">
  
  [<a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
  [<a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
  <br>

  &nbsp;<br>
</form>
</body>




