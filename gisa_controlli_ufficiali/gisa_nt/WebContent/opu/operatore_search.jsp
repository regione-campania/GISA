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
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>

<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*"%>
	

<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%><jsp:useBean id="ListaLineeProduttive"
	class="org.aspcfs.modules.opu.base.LineaProduttivaList" scope="request" />
<jsp:useBean id="SearchOrgListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="AccountStateSelect"
	class="org.aspcfs.utils.web.StateSelect" scope="request" />
<jsp:useBean id="ContactStateSelect"
	class="org.aspcfs.utils.web.StateSelect" scope="request" />
<jsp:useBean id="CountrySelect"
	class="org.aspcfs.utils.web.CountrySelect" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.accounts.base.Organization" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="LineeProduttiveList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LineaProduttiva"
	class="org.aspcfs.modules.opu.base.LineaProduttiva" scope="request" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkString.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkPhone.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkEmail.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/setSalutation.js"></script>
<%@ include file="../initPage.jsp"%>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>


<body onLoad="">

<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
		<%
			String label = (request.getAttribute("TipologiaSoggetto") != null) ? "opu.operatore.intestazione_"
					+ (String) request.getAttribute("TipologiaSoggetto")
					: "opu.operatore.intestazione";
		%> <dhv:label name="<%=label%>"></dhv:label> > <dhv:label name="">Ricerca rapida</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>


<form name="searchOperatore"
	action="OperatoreAction.do?command=Search<%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post">
	
<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="50%" valign="top">
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label
					name="opu.operatore"></dhv:label></strong></th>
			</tr>
			<tr>
				<td class="formLabel">
					<dhv:label name="opu.operatore.intestazione"></dhv:label>
				</td>
				<td><input type="text" size="23" name="searchRagioneSociale"
					value="<%=SearchOrgListInfo
							.getSearchOptionValue("searchRagioneSociale")%>">
				</td>
			</tr>
			<tr>
				<td class="formLabel"><dhv:label name="opu.operatore.cf"></dhv:label>
				</td>
				<td><input type="text" size="23" name="CodiceFiscale"
					value="<%=SearchOrgListInfo.getSearchOptionValue("CodiceFiscale")%>">
				</td>
			</tr>
		
			<tr>
				<td class="formLabel"><dhv:label name="opu.operatore.piva"></dhv:label>
				</td>
				<td><input type="text" size="23" name="PartitaIva"
					value="<%=SearchOrgListInfo
									.getSearchOptionValue("PartitaIva")%>">
				</td>
			</tr>
		</table>

		</td>

		</tr>
</table>
<input type="hidden" name="doContinue" id="doContinue" value="">
<input type="hidden" name="popup" id="popup" value=""> 

<input
	type="button" onclick="this.form.submit()"
	value="<dhv:label name="button.search">Search</dhv:label>">
  <input type="hidden" name="source" value="searchForm">
  
  <%if (request.getAttribute("PopUp") != null) {%>
  <input type = "hidden" name = "popup" value = "<%=request.getAttribute("PopUp") %>">
  <%} %>
  </form>





</body>



