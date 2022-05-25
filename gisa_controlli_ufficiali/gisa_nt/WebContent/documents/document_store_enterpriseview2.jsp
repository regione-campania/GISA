<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.documents.base.*" %>

<%@page import="com.zeroio.iteam.base.FileFolder"%>
<%@page import="com.zeroio.iteam.base.FileItem"%><jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="documentStoreList" class="org.aspcfs.modules.documents.base.DocumentStoreList" scope="request"/>
<jsp:useBean id="documentStoreListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>

<script language="JavaScript" type="text/javascript" src="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>


<%-- Trails --%>
<%@ include file="../initPage.jsp" %>
<form method="get" action="DocumentManagement.do?">
<table cellpadding="6" cellspacing="0" width="100%" border="0">
		<input type = "hidden" name = "command" value ="DocumentStoreCenterBacheca">
		<tr><td bgcolor="#d3d1d1" colspan="2" align="center"><strong><dhv:label name="button.search">Search</dhv:label></strong></td></tr>
		<tr>
		
			<td   bgcolor="#d3d1d1">
				Nome File
				</td>
				<td bgcolor="#d3d1d1"><input type="text" name="nomeFile" id = "nomeFile" > 
				</td></tr>
				
			
				<tr>
				<td bgcolor="#d3d1d1">
				<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
				</td>
				<td bgcolor="#d3d1d1">
				<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="javascript : clearForm()">
			</td>
		</tr>
		
	</table>
</form>

<h3>Seleziona i File Da Allegare</h3>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="100%"><strong><dhv:label name="documents.documents.file">File</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.type">Type</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.size">Size</dhv:label></strong></th>
    <th align="center"><strong><dhv:label name="documents.documents.version">Version</dhv:label></strong></th>
    <th align="center" nowrap><strong><dhv:label name="documents.documents.dateModified">Date Modified</dhv:label></strong></th>
  </tr>
  
  
  <%
  ArrayList<FileItem> lista = (ArrayList<FileItem>) request.getAttribute("listafile");
 int rowid=0;
 int i =1 ;
  for(FileItem file: lista)
  {
	  rowid = (rowid != 1?1:2);
	  %>
	  <tr class="row<%= rowid %>">
    
    <td width="100%">
      <%= file.getImageTag("-23") %>
           <a href="javascript:addAllegato('<%=file.getSubject() %>','<%=file.getLinkItemId() %>','<%=file.getId() %>','<%=file.getVersion() %>','<%=file.getExtension() %>')"><b><%= file.getSubject() %></b>
      
    </a>
      </td>
    <td align="center" nowrap><%= toHtml(file.getExtension()) %></td>
    <td align="right" nowrap>
      <%= file.getRelativeSize() %> <dhv:label name="admin.oneThousand.abbreviation">k</dhv:label>&nbsp;
    </td>
    <td align="center" nowrap>
      <%= file.getVersion() %>&nbsp;
    </td>
    <td align="center" nowrap>
      <zeroio:tz timestamp="<%= file.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="true"/><br />
      <dhv:username id="<%= file.getModifiedBy() %>"/>
    </td>
  </tr>
	  
	  <%
	  i ++ ;
  }
  %>
  <script type="text/javascript">
function clearForm()
{
document.getElementById("nomeFile").value = "";
	}

</script>
