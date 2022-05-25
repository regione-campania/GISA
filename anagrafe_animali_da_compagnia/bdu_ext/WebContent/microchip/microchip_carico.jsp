<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.microchip.base.*,java.text.DateFormat" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>


<script language="JavaScript">
	
	function doCheck(form) {
      if (form.dosubmit!=null && form.dosubmit.value == "false") {
      	return true;
     } else {
      return(checkMC(form));
     }
   	}
   	
	function checkMC(form){
		formTest = true;
    	message = "";
    	if ( !( (document.getElementById('microchip').value.length == 15) && ( /^([0-9]+)$/.test( document.getElementById('microchip').value )) ) ){
    		message += label("", "- La lunghezza del microchip deve essere di 15 caratteri numerici\r\n");
      		formTest = false;
    	}
    	
    	if (document.getElementById('ruolo').value == '24'){
    		 if (  !(document.getElementById('microchip').value.substring(0, 6) == "380260")){
    			 message += label("","- MC non valido: selezionare un Mc del tipo 380260... \r\n");
    			 formTest = false;
    		 }
    			
    		}
    	
    	if (formTest == false) {
      		alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      		return false;
    	}
	}

</script>
<body>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  Carico microchip
</td>
</tr>
</table>
<%-- End Trails --%>
<form action="Microchip.do?command=EseguiCarico&auto-populate=true" method="post" onSubmit="return doCheck(this);">

<input type="hidden" id="ruolo" name="ruolo" value="<%=User.getRoleId()%>">

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="100%" valign="top">
    
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	  <tr>
	    <th colspan="2">
		  <strong>Carico Microchip a priori</strong>
		</th>
	  </tr>
	  
	  <tr class="containerBody">
	    <td nowrap class="formLabel">
	 	  Inserisci Microchip
	    </td>
	    <td>
	      <input id="microchip" name="microchip" type="text" size="20" maxlength="15">
	    </td>
	  </tr>

	</table>
		  
    <%= showError( request, "errore") %>
    
    <% if ( request.getAttribute("ok") != null ) { %>
    	<br>
    	<%= showAttribute( request, "ok") %>
    <% } %>
    
   </td>
  </tr>
</table>
<br />
<input type="submit" value="<dhv:label name="">Esegui Carico</dhv:label>" onClick="this.form.dosubmit.value='true';">
<input type="hidden" name="dosubmit" value="true" />
</form>
</body>