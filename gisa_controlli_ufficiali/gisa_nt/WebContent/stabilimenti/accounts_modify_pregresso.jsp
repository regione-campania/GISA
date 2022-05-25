<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.stabilimenti.base.OrganizationAddress"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp"%>

<script>



function checkLenCodiceFiscale(){

	formTest = true;
	var codiceFiscale = document.getElementById('codice_fiscale_rappresentante').value
	if( codiceFiscale !='' && codiceFiscale.length !=16 ) {
		message = 'Il campo "\Codice Fiscale\" deve essere di 16 cifre';
		formTest = false;
	}
	
	if(formTest){
		 document.addAccount.submit();
	}else{
		alert(message);
	}
	
}

</script>

<%
HashMap<String,String> listaCampi = (HashMap<String,String>)request.getAttribute("ListaCampiPregressi");

if(listaCampi.isEmpty())
{
%>
<script>
document.location.href = 'Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getId()%>';



</script>
<%	
}
else
{
	%>
	<script>
if(confirm('Attenzione: Da qui si potranno aggiungere le informazioni che mancano  (ragione sociale,partita iva , codice fiscale ,legale rappresentante ,sede operativa)Premere Ok per continuare')==false)
{
	document.location.href = 'Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getId()%>';

}
</script>

<%
}

%>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
<a href="Stabilimenti.do">Stabilimenti</a> >Aggiungi Campi Pregressi
</td>
</tr>
</table>
<br/>

<form name = "addAccount" method="post" action="Stabilimenti.do?command=UpdatePregresso">

<input type = "hidden" name = "orgId" value = "<%=OrgDetails.getOrgId() %>">
<input type = "hidden" name = "addressId" value = "<%=(OrgDetails.getAddressList().getAddress(1)!= null ) ? OrgDetails.getAddressList().getAddress(1).getId() : "-1" %>">

<input type = "button" value="Salva"  onclick="javascript:checkLenCodiceFiscale();">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong>Informazioni Principali</strong> </th>
		</tr>

<%
Iterator<String> itKiavi = listaCampi.keySet().iterator();
while(itKiavi.hasNext())
{
String kiave = itKiavi.next();
String val = listaCampi.get(kiave);
if(val==null)
{
	val = "" ;
}
if(val.equalsIgnoreCase(""))
{
	%>
	<tr class="containerBody">
					<td nowrap class="formLabel"><%=kiave.replaceAll("_"," ") %></td>
					<td><input type = "text" name = "<%=kiave %>" id = "<%=kiave %>"> </td>
	</tr>
	<%
}



}

%>
				
				
			
		</table>
		
	
</form>