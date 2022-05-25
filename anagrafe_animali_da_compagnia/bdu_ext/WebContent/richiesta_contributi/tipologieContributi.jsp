<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>

<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*, java.text.DateFormat"%>
<%@page import="org.aspcfs.modules.registrazione_canili.base.RegistrazioneCanile"%>
<jsp:useBean id="RC" class="org.aspcfs.modules.richiestecontributi.base.RichiestaContributi" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

	<head>
		
		
		</head>
		<p>
			<font color="red">
				<%=toHtmlValue( (String)request.getAttribute( "Error" ) ) %>
			</font>
		</p>
		
		
				
		<form method="post" name="addRichiesta" action="ContributiSterilizzazioni.do?command=AvviaRichiestaCatturati" onSubmit="return verifica();">
			
			<input type="submit" value="Avvia la richiesta" />
			
			<input type="button" value="Annulla" onclick="location.href='ContributiSterilizzazioni.do'" />
			
			<table class="details"  cellspacing="0" cellpadding="4" border="0" width="100%">
							
				
				
			</table>
			
	    </form>
