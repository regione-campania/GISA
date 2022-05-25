<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html40/strict.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*,org.aspcfs.modules.anagrafe_animali.base.*,java.io.IOException,org.aspcfs.modules.opu.base.*, java.util.Date, com.sun.org.apache.xerces.internal.impl.dv.util.Base64,java.io.ByteArrayOutputStream,javax.imageio.ImageIO"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ include file="../../initPage.jsp"%>

<link rel="stylesheet" type="text/css" media="screen"
	href="anagrafe_animali/documenti/screen.css">
<link rel="stylesheet" documentale_url="" href="anagrafe_animali/documenti/print.css"
	type="text/css" media="print" />

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="aslList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="microchips" scope="request" class="java.util.ArrayList" />

<script>function catturaHtml(form){
	//popolaCampi();
	h=document.getElementsByTagName('html')[0].innerHTML;
	form.html.value = h;
	}</script>

<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<!-- input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" / -->
		 
	<form name="generaPDF" action="GestioneDocumenti.do?command=GeneraPDF" method="POST">
<input type="button" id ="generapdf" class="buttonClass"  value="Genera PDF" 	
onClick="this.disabled=true; this.value='GENERAZIONE PDF IN CORSO'; catturaHtml(this.form); this.form.submit()" />
<input type="hidden" name="html" id="html" value=""></input>
<input type="hidden" name="tipo" id="tipo" value="PrintRichiestaCampioniCanile"></input>
<input type="hidden" name="idLinea" id="idLinea" value="<%=request.getParameter("lineaId") %>"></input>
</form>	 

<!-- script type="text/javascript">
setTimeout('Redirect()',1000);
function Redirect()
{
	catturaHtml(document.forms[0]);
generaPDF.submit();
}
</script--> 

<table align="center" cellspacing="17" cellpadding="10">

<%for (int h = 0; h < microchips.size(); h++){ %>


<%if (h == 0){ %>
<tr>
<%} else if ( h % 4 == 0){%>
</tr><tr>
<%}%>
<td><img src="<%=createBarcodeImage((String)microchips.get(h))%>" /></td>


<%} %>

</table>

</body>

