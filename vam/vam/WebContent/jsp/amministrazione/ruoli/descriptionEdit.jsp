<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page contentType="text/html; charset=windows-1252" language="java" errorPage="" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>

<%@page import="it.us.web.bean.BUtente"%>
<%@page import="it.us.web.util.properties.Message"%>

<sp:useBean id="ruolo" scope="request" />

		
 <div class="titolo">Modifica descrizione ruolo: ${ruolo.ruolo }</div>
 
 <div class="area-contenuti-2" style="width:50%">

	<form action="ruoli.DescriptionEdit.us" method="post" onsubmit="return checkForm();" >
    	 <input type="hidden" name="ruolo" value="${ruolo.ruolo }" />
    	 <input id="ruoloDesc" maxlength="80" size="50" type="text" name="descrizione" value="${ruolo.descrizione }" /> <font color="red">*</font>
		 <input type="submit" value="Modifica" class="button" />
		 <br/><font color="red">Campo obbligatorio</font>
	</form>	
		
</div>

<script type="text/javascript">
function checkForm()
{
	if( $('#ruoloDesc')[0].value.length > 0 )
	{
		return true;
	}
	else
	{
		alert( "Inserire una descrizione" );
		return false;
	}
		
}
</script>