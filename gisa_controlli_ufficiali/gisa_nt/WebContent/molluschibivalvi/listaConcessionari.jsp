<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="org.aspcfs.modules.stabilimenti.base.SottoAttivita,java.util.*,java.text.DateFormat,org.aspcfs.modules.stabilimenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>

<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList" scope="request" />

<jsp:useBean id="impreseAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="stabilimentiAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />

<%@ include file="../initPage.jsp"%>

<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>
<script   src="javascript/jquery-ui.js"></script>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script>
function openaddConcessionario(idZona,idConcessionario)
{
	title  = '_types';
	  width  =  '500';
	  height =  '450';
	  resize =  'yes';
	  bars   =  'no';
	  
	  var posx = (screen.width - width)/2;
	  var posy = (screen.height - height)/2;
	  
	  var selectedIds = '';
	  var selectedDisplays ='';

	  
	  var params = 'WIDTH=' + width + ',HEIGHT=' + height + ',RESIZABLE=' + resize + ',SCROLLBARS=' + bars + ',STATUS=0,LEFT=' + posx + ',TOP=' + posy + 'screenX=' + posx + ',screenY=' + posy;
	 window.location.href='MolluschiBivalvi.do?command=AddConcessione&orgId='+idZona+'&idConcessionario='+idConcessionario;
// 	  newwin.focus();
// 	  if (newwin != null) {
// 	    if (newwin.opener == null)
// 	      newwin.opener = self;
// 	  }
}
</script>
<%if (refreshUrl!=null && !"".equals(refreshUrl)) { %>
<script language="JavaScript" TYPE="text/javascript">
	parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
</script>
<%
}
%>

<%

String orgId = "&orgId=" + request.getParameter("orgId");


%>

<dhv:evaluate if="<%= !isPopup(request) %>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="MolluschiBivalvi.do">Molluschi bivalvi</a> > <%
			if (request.getParameter("return") == null) {
			%>
			<a href="MolluschiBivalvi.do?command=Search"><dhv:label
				name="stabilimenti.SearchResults">Search Results</dhv:label></a> > <%
			} 
			%>
			
			
			<a href="MolluschiBivalvi.do?command=Details<%= orgId %>">Scheda Molluschi Bivalvi</a> > Seleziona Concessionario 
			
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>



	<head>
		<link rel="stylesheet"  type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>

	</head>
	
		
		
	<form name="aiequidiForm" action="Concessionari.do">
		<input type="hidden" name ="command" value="ListaConcessionari" />
		<input type="hidden" name ="orgId" value="<%= request.getParameter("orgId") %>" />
		<%=request.getAttribute( "tabella" )%>
	</form>
	
	<script type="text/javascript">
           function onInvokeAction(id) {
               $.jmesa.setExportToLimit(id, '');
               $.jmesa.createHiddenInputFieldsForLimitAndSubmit(id);
           }
           function onInvokeExportAction(id) {
               var parameterString = $.jmesa.createParameterStringForLimit(id);
               location.href = 'Concessionari.do?command=ListaConcessionari&' + parameterString;
           }
   	</script>
