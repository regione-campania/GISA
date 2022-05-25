<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,org.aspcfs.modules.assets.base.*,java.text.DateFormat"%>
<jsp:useBean id="assetVendorList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="assetManufacturerList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="asset" class="org.aspcfs.modules.assets.base.Asset"
	scope="request" />
<jsp:useBean id="assetStatusList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="sessoList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ include file="../initPage.jsp"%>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<body>
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td width="100%"><a href="AnimaleAction.do"><dhv:label
			name="">Animali</dhv:label></a> &gt; <dhv:label name="">Pregresso</dhv:label>
		</td>
	</tr>
</table>
<%-- End Trails --%>
<form name="storico"
	action="AnimaleAction.do?command=CercaRegresso" method="post">

<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="50%" valign="top">

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			
			<tr>
				<th colspan="2"><strong><dhv:label
					name="">Filtro</dhv:label></strong>
				</th>
			</tr>
			<tr class="containerBody">
				<td class="formLabel"><dhv:label name="">Microchip/Tatuaggio</dhv:label>
				</td>
				<td><input type="text" size="20" name="serialNumber"
					maxlength="15" value="">
				</td>
			</tr>
			
		</table>

	
		
		</td>

</table>
<br />	
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>" /> 
<input type="reset" value="<dhv:label name="button.clear">Clear</dhv:label>" />
</form>
</body>