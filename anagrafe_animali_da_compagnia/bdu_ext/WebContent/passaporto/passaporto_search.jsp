<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.microchip.base.*,java.text.DateFormat" %>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>

<body>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  Cerca passaporto
</td>
</tr>
</table>
<%-- End Trails --%>
<form name="searchServiceContracts" action="Passaporto.do?command=List&auto-populate=true" method="post">


<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="100%" valign="top">
    
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
	    <th colspan="2">
		  <strong>Informazioni Passaporti a priori</strong>
		</th>
	  </tr>
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Asl di Riferimento
	    </td>
	    <dhv:evaluate if="<%=User.getSiteId() > 0 %>">
	    <td>
	    <input type="hidden" name="aslRif" id="aslRif" value="<%=User.getSiteId() %>"/>
	      <%= aslRifList.getSelectedValue(User.getSiteId()) %>
	    </td>
	    </dhv:evaluate>
	    <dhv:evaluate if="<%=User.getSiteId() < 0 %>">
	    <td>
	      <%= aslRifList.getHtmlSelect("aslRif", User.getSiteId()) %>
	    </td>
	   </dhv:evaluate>
	  </tr>
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Passaporto
	    </td>
	    <td>
	      <input name="nrpassaporto" id ="nrpassaporto" type="text" size="20" maxlength="13"  />
	    </td>
	  </tr>
	</table>
   <!--  --> 
   <!--  -->
   </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
</form>
</body>