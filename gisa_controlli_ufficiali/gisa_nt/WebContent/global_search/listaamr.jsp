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
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<jsp:useBean id="SearchOrgListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="AccountStateSelect"
	class="org.aspcfs.utils.web.StateSelect" scope="request" />
<jsp:useBean id="ContactStateSelect"
	class="org.aspcfs.utils.web.StateSelect" scope="request" />
<jsp:useBean id="CountrySelect"
	class="org.aspcfs.utils.web.CountrySelect" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.global_search.base.OrganizationView"
	scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkDate.js"></script>
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
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCheckList.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/confirmDelete.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/checkURL.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/setSalutation.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>
<!-- ******************************************************************** -->

<%@ include file="../initPage.jsp"%>


<style type="text/css">
#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>




<dhv:include name="accounts-search-name" none="true">
	<body onLoad="">
</dhv:include>
<%-- Trails --%>

	<table class="trails" cellspacing="0">
		<tr>
			<td>Ricerca</td>
		</tr>
	</table>

	<%-- End Trails --%>
<form name="searchAccount"
	action="GlobalSearch.do?command=SearchAMR&items=-1" method="post">

	<table cellpadding="2" cellspacing="2" border="0" width="100%">
		<tr>
			<td width="50%" valign="top">
				<table cellpadding="4" cellspacing="0" border="0" width="50%"
					class="details">

				
					<dhv:permission name="global-search-attivita-view">

						<tr>
							<th colspan="2"><strong>Ricerca controlli con campioni AMR</strong>
							</th>
							
						</tr>
						<dhv:evaluate if="<%= SiteList.size() > 2 %>">
							<tr>
								<td nowrap class="formLabel"><dhv:label name="">ASL</dhv:label>
								</td>
								<td><dhv:evaluate
										if="<%=User.getUserRecord().getSiteId() == -1 %>">
<%-- 										<%SiteList.setJsEvent("onChange=popolaComboComuni()");%> --%>
										<%= SiteList.getHtmlSelect("searchcodeOrgSiteId", ("".equals(SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) ? String.valueOf(Constants.INVALID_SITE) : SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId"))) %>
									</dhv:evaluate> <dhv:evaluate
										if="<%=User.getUserRecord().getSiteId() != -1 %>">
										<input type="hidden" id="asl" name="searchcodeOrgSiteId"
											value="<%= User.getUserRecord().getSiteId()%>">
										<%= SiteList.getSelectedValue(User.getUserRecord().getSiteId()) %>
									</dhv:evaluate></td>
							</tr>
						</dhv:evaluate>
						<dhv:evaluate if="<%= SiteList.size() <= 2 %>">
							<input type="hidden" name="searchcodeOrgSiteId"
								id="searchcodeOrgSiteId" value="-1" />
						</dhv:evaluate>
						
						
						<tr>
							<td nowrap class="formLabel"><dhv:label name="">Tipo Attività</dhv:label>
							</td>
							<td>
								<!-- Tipo Attività --> <input type="hidden" name="searchcodeTipologiaAttivita" value="3" id="tipoA"/>
									Controlli Ufficiali
							</td>
						</tr>

						<tr class="containerBody">
							<td nowrap class="formLabel"><dhv:label name="">Identificativo C.U.</dhv:label>
							</td>
							<td><input id="identificativo" type="text" maxlength="70"
								size="50" name="searchAccountIdentificativo" /></td>
						</tr>
						<tr>
							<td nowrap class="formLabel"><dhv:label name="Comune">Numero Verbale</dhv:label>
							</td>
							<td><input id="num_verbale" type="text" maxlength="70"
								size="50" name="searchAccountNumVerbale" /></td>
						</tr>

						<tr id="riga_intervallo">
							<td class="formLabel"><dhv:label name="">Nell'intervallo compreso tra: </dhv:label>
							</td>
							<td>
								<%-- value="<%=DateUtils.timestamp2string(Capo.getCd_data_nascita())%>" />&nbsp; --%>

								<input readonly type="text" name="searchtimestampInizio"
								id="data1" size="10" /> <a href="#"
								onClick="cal19.select(document.forms[0].searchtimestampInizio,'anchor19','dd/MM/yyyy'); return false;"
								NAME="anchor19" ID="anchor19"> <img
									src="images/icons/stock_form-date-field-16.gif" border="0"
									align="absmiddle"></a> <input readonly type="text"
								name="searchtimestampFine" id="data2" size="10" />&nbsp; <a
								href="#"
								onClick="cal19.select(document.forms[0].searchtimestampFine,'anchor19','dd/MM/yyyy'); return false;"
								NAME="anchor19" ID="anchor19"> <img
									src="images/icons/stock_form-date-field-16.gif" border="0"
									align="absmiddle"></a>

							</td>
						</tr>

<tr><td colspan="2" align="center">
<input type="submit" value="CERCA" onClick="loadModalWindow()"/>
</td></tr>

					</table>
</form>

					<!--  </form> -->
				</td>
			</tr>
		</dhv:permission>
		</div>
		<input type="hidden" id="tipoRicerca" name="tipoRicerca" value="cu" />
		<input type="hidden" name="source" value="searchForm">

	</table>
	
	
	
	
	
	
	
	
	
	
	
	
</form>





