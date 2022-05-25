<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ page import="java.util.*,org.aspcfs.modules.allevamenti.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="fileFolder" class="com.zeroio.iteam.base.FileFolder" scope="request"/>
<%@ page import="java.util.*,org.aspcfs.modules.allevamenti.base.*, org.aspcfs.modules.base.*" %>
<%@ page import="java.util.*,org.aspcfs.modules.allevamenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="ContactTypeList" class="org.aspcfs.modules.contacts.base.ContactTypeList" scope="request"/>
<jsp:useBean id="assetContact" class="org.aspcfs.modules.contacts.base.Contact" scope="request"/>
<jsp:useBean id="asset" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="serviceContract" class="org.aspcfs.modules.servicecontracts.base.ServiceContract" scope="request"/>
<jsp:useBean id="assetMaterialList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="assetStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="assetVendorList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="assetManufacturerList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impiegoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="codici_izs" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.allevamenti.base.*,com.zeroio.iteam.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<script language="JavaScript" type="text/javascript" src="javascript/tasks.js"></script>

<% String path = (String) request.getAttribute("pathLog"); %>

<body onLoad="">
<form method="post" name="inputForm" action="Allevamenti.do?command=UploadDoc" enctype="multipart/form-data" onSubmit="return checkFileForm(this);">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="Assets.do"><dhv:label name="">Allevamenti</dhv:label></a> >
  <dhv:label name="">Importa Capi</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr class="subtab">
    Operazione completata. Verifica i file di Log per il resoconto.
  </tr>
</table>

<br>

<table cellpadding="4" cellspacing="0" border="0" width="50%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Log</dhv:label></strong>
    </th>
  </tr>
   <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">RIEPILOGO</dhv:label>
      </td>
      <td>
        <a href="<%= "logAllevamenti/"+path+"_rpg.txt" %>" ><%= path %></a>
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">INSERITI</dhv:label>
      </td>
      <td>
        <a href="<%= "logAllevamenti/"+path+"_ok.txt" %>" ><%= path %></a>
      </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="">ERRORI</dhv:label>
      </td>
      <td>
        <a href="<%= "logAllevamenti/"+path+"_err.txt" %>" ><%= path %></a>
      </td>
    </tr>
 </table>
 
<br>

  <input type="submit" value="<dhv:label name="global.button.c">Chiudi</dhv:label>" onClick="this.form.action='Allevamenti.do'" /><br/>
  <dhv:formMessage />
  <br />
</form>
</body>