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
<script language="JavaScript">

	function abilitaAll(){
		document.searchServiceContracts.microchip.disabled      = false;
		document.searchServiceContracts.codiceFiscale.disabled  = false;
	}
	
	function disabilitaCF(){
		document.searchServiceContracts.codiceFiscale.disabled = true;
		document.searchServiceContracts.microchip.disabled     = false;
	}
	
	function disabilitaMC(){
	    //document.searchServiceContracts.microchip.disabled      = true;
	    //document.searchServiceContracts.codiceFiscale.disabled  = false;
	    document.searchServiceContracts.aslRif.value            = -1;
	}

</script>
<body>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  Cerca microchip
</td>
</tr>
</table>
<%-- End Trails --%>
<form name="searchServiceContracts" action="Microchip.do?command=List&auto-populate=true" method="post">


<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="100%" valign="top">
    
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
	    <th colspan="2">
		  <strong>Informazioni Microchip a priori</strong>
		</th>
	  </tr>
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Asl di Riferimento
	    </td>
	    <td>
	      <%= aslRifList.getHtmlSelect("aslRif", User.getSiteId()) %>
	    </td>
	  </tr>
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Microchip
	    </td>
	    <td>
	      <input name="microchip" id ="microchip" type="text" size="20" maxlength="15"  >
	    </td>
	  </tr>
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Codice Fiscale LP
	    </td>
	    <td>
	      <input name="codiceFiscale" id = "codiceFiscale" type="text" size="20" maxlength="16" onFocus = "javascript:disabilitaMC();"  >
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