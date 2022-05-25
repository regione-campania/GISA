<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<jsp:useBean id="OrgList" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipologiaList" class="org.aspcfs.utils.web.LookupList" scope="request" />

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
<a href="Passaporto.do?command=ListUOS">Distribuzione passaporti</a> > 
Lista UOS
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

<% int columnCount = 0; %>
<table cellpadding="2" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
   <th nowrap <% ++columnCount; %>>
      Ragione sociale
    </th>
    <th nowrap <% ++columnCount; %>>
    	
	</th>
    
  </tr>
<%
	Iterator<Integer> j = OrgList.keySet().iterator();
	if (j.hasNext() ) 
	{
	    int rowid = 0;
	    int i=0;
	    int k = 0;
	    while (j.hasNext()) 
	    {
	    			k=j.next();
	    			String dato = (String)OrgList.get(k);
			      	String ragioneSociale = dato.split(";")[0];
			      	String asl = dato.split(";")[1];
			    	
			    		i++;
			    		rowid = (rowid != 1 ? 1 : 2);
			%>
			  	<tr class="row<%= rowid %>">
				<td nowrap><%=ragioneSociale%>
			     </td>
			     <td nowrap> <a href="Passaporto.do?command=ListDistribuzionePassaporti&aslRif=<%=asl%>&assegnato=false&abilitato=false&nome_uos=<%=ragioneSociale.replaceAll("\"","")%>&id_uos=<%=k%>">Seleziona</a>
			     </td>
					

			  	</tr>
			<%	
		}%>
<%
}
%>
</table>
<br />
