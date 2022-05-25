<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,
				 java.text.DateFormat,
				 org.aspcfs.controller.*,
				 org.aspcfs.utils.*,
				 org.aspcfs.utils.HTTPUtils ,
				 org.aspcfs.utils.web.*,
				 org.aspcfs.modules.contacts.base.*,
				 org.aspcfs.controller.SystemStatus" %>

<%@page import="org.aspcfs.modules.unitacrisi.base.UnitaCrisiBean"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="lookup_ambito_unita_di_crisi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="oUnitaCrisi" class="org.aspcfs.modules.unitacrisi.base.UnitaCrisiBean" scope="request"/>

<script type="text/javascript">

	function controllaForm()
	{
		var ret = true;
		message = "";

		if( document.getElementById('id_ambito').value == -1 )
		{
			message += "\"Ambito di Competenza\" obbligatorio\r\n";
			ret = false;
		}

		if( document.getElementById('responsabile').value == '' )		
		{
			message += "\"Responsabile\" obbligatorio\r\n";
			ret = false;
		}
		
		if (!ret) {
			alert(message);
		}

		return ret;
	}

	function annulla() {
		document.aggiungiForm.click_annulla.value = '1' ;
		window.close();
	}

	function controlla_modifica() {
		if (document.aggiungiForm.click_annulla.value == '0' ) {
			window.opener.location.reload ();
		}
	}

</script>


<body onunload="javascript:controlla_modifica();">

	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="UnitaCrisi.do">Modellatore Organizzazione ASL</a>  
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
	
	
	<form id="aggiungiForm" 
		  name="aggiungiForm" 
		  action="UnitaCrisi.do?command=Update" 
		  method="post"
		  >
		<input id="campo_id" type="hidden" name="id" value="<%= oUnitaCrisi.getId() %>" >
		<input id="campo_click_annulla" type="hidden" name="click_annulla" value="0" >
		
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<tr>
				<td id="td_campo_id_ambito" nowrap class="formLabel">Ambito di Competenza</td>
				<td>
					<input disabled id="campo_disabled_ambito_stringa" type="text" size="30" name="ambito_stringa" maxlength="30" value="<%= request.getAttribute("ambito_stringa") %>"></input>
					<input id="id_ambito" type="hidden" name="id_ambito" value="<%= oUnitaCrisi.getId_ambito() %>" >
				</td>
		    </tr>	
	        
	        <tr>
	  			<td id="td_campo_responsabile" nowrap class="formLabel">Responsabile</td>
	  			<td>
					<input id="responsabile" type="text" size="30" name="responsabile" maxlength="30" value="<%= oUnitaCrisi.getResponsabile() %>"></input><font color="red">*</font>
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_mail" nowrap class="formLabel">E-mail</td>
	  			<td>
					<input id="mail" type="text" size="100" name="mail" maxlength="100" value="<%= oUnitaCrisi.getMail() %>"></input>
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_telefono" nowrap class="formLabel">Telefono</td>
	  			<td>
					<input id="telefono" type="text" size="100" name="telefono" maxlength="100" value="<%= oUnitaCrisi.getTelefono() %>"></input>
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_fax" nowrap class="formLabel">Fax</td>
	  			<td>
					<input id="fax" type="text" size="30" name="fax" maxlength="30" value="<%= oUnitaCrisi.getFax() %>"></input>
	  			</td>
			</tr>
		</table>
		
		<input type="submit" value="Aggiorna" onclick="javascript:return controllaForm()"/>
		<input type="button" value="Annulla" onclick="javascript:annulla()">
		
	</form>
	

</body>