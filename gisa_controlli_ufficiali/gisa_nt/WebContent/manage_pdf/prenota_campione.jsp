<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stampa_verbale_prelievo.base.Organization" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DestinatarioCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />
<%
String orgId 	= (String)request.getAttribute("orgId");
%>

<script>

function openPopupModulesForm(form){

	window.open('PrintModulesHTML.do?command=ViewModulesFromPrenotaCampione','popupSelect','height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	form.target = 'popupSelect';
	document.printmodules.submit();
}

function check(){
	var rad_val = "";
	for (var i=0; i < document.printmodules.tipo.length; i++)
	{
	   if (document.printmodules.tipo[i].checked)
	      {
	      		rad_val = document.printmodules.tipo[i].value;
	      }
	}
	
	if(rad_val == "")	
	{
		alert("Controlla di aver selezionato un modulo.");
		return false;
	
	}

	/*if(rad_val == 1)	
	{
		alert("Modulo non disponibile per questo Operatore.");
	
	}*/
	else
	{
		openPopupModulesForm(printmodules);
		//openPopupModules(rad_val);
		//window.close();
	}

}
</script>

<table class="trails" cellspacing="0">
<tr>
<td>
<a href="OsaSearch.do"><dhv:label name=" ">Operatori</dhv:label></a> > 
<a href="OsaSearch.do?command=Search"><dhv:label name="">Ricerca Operatori</dhv:label></a> >
<dhv:label name="">Prenotazione Campione</dhv:label>
</td>
</tr>
</table>
<dhv:permission name="campioni-prenotati-view">
<div align="left">
	<a class="ovalbutton" href = "Campioni.do?command=ViewElencoPrenotazioni&orgId=<%=orgId%>" ><span >Visualizza Campioni Prenotati</span></a>
</div><br/>
</dhv:permission>

<br>
<br>

<form method="post"  name = "printmodules" action="PrintModulesHTML.do?command=ViewModulesFromPrenotaCampione&orgId=<%=orgId%>">
<input type = "hidden" name = "orgId" value = "<%=orgId %>"> 
<table cellpadding="4" cellspacing="0" width="70%" class="details">
<tr>
	<th colspan="2"><strong>Selezionare il modulo di interesse</strong></th>
</tr>
<tr class="containerBody">
 	<td nowrap class="formLabel"></td>
	<td>
		<input type="radio" id="tipo" name="tipo" value="1">Mod 1 Verbale campione molluschi in produzione primaria<br>
		<input type="radio" id="tipo" name="tipo" value="2">Mod 2 Verbale campione battereologico<br>
		<input type="radio" id="tipo" name="tipo" value="3">Mod 3 Verbale campione chimico<br>
		<%-- <input type="radio" name="tipo" value="6">Mod 6 Verbale prelievo campioni superficie ambientale<br>
		<input type="radio" name="tipo" value="10">Mod 10 Verbale prelievo campioni superficie di carcasse<br>
		--%>
	</td>
</tr>
</table>
<br><br/>

<div id="campione">

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Informazione Campione</dhv:label></strong>
    </th>
	</tr>
  
     <%@ include file="/campioni/campioni_add.jsp" %>
  
   </table> <!--  chiusura tabella generale -->
	<input type="hidden" id="tipoAction" name="tipoAction" value="" />
</div>

<br>
	<div align="left">
		<a class="ovalbutton" href = "#" onclick = "javascript:check();" ><span>Stampa Verbale</span></a>
		<a class="ovalbutton" href = "#" onclick="javascript:document.printmodules.tipoAction.value='prenota';document.printmodules.submit();"><span>Prenota Campione </span></a>
	</div>
</form>


