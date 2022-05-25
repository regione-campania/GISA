<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request" />	

<script>
function checkFormPartite(form){
	var numero = form.numero.value;
	var mod4 = form.mod4.value;
	var message = '';
	var esito = true;
	if (numero.length < 5 && mod4.length < 3){
		message += 'Inserire almeno cinque caratteri nel campo NUMERO PARTITA o tre nel campo MOD 4.';
		esito = false;
	}
		if (esito==false)
			alert (message);
		return esito;
}


function checkFormCapi(form){
	var matricola = form.matricola.value;
	var message = '';
	var esito = true;
	if (matricola.length < 5){
		message += 'Inserire almeno cinque caratteri nel campo MATRICOLA.';
		esito = false;
	}
		if (esito==false)
			alert (message);
		return esito;
}
</script>

<table class="trails" cellspacing="0">
	<tr>
		<td>
					<a href="MacellazioneUnica.do?command=List&orgId=<%=OrgDetails.getOrgId()%>">Home macellazioni </a> > Ricerca
		</td>
	</tr>
</table>


<%
String param1 = "orgId=" + OrgDetails.getOrgId();
%>
<dhv:container name="stabilimenti_macellazioni_ungulati" selected="macellazioniuniche" object="OrgDetails" param="<%= param1 %>" >

<table width="100%">
<col width="50%">
<tr><td valign="top">

 <form name="ricercaPartita" id="ricercaPartita" action="MacellazioneUnica.do?command=CercaPartita" method="post" >
 <input type="hidden" id="idMacello" name="idMacello" value="<%=OrgDetails.getOrgId()%>"/> 
<table class="details">
<col width="180px">
<th colspan="2">Ricerca partita</th> 
<tr><td class="formLabel">Stato partita</td> <td><input type="text" id="statoPartita" name="statoPartita"/></td></tr>
<tr><td class="formLabel">Numero partita</td> <td><input type="text" id="numero" name="numero"/></td></tr>
<tr><td class="formLabel">Numero mod. 4</td> <td><input type="text" id="mod4" name="mod4"/></td></tr>
<tr><td colspan="2" align="right"><input type="button" value="CERCA" onClick="if (checkFormPartite(this.form)){this.form.submit();}"/></td></tr>
</table>
</form>

</td>
<td valign="top">

 <form name="ricercaCapo" id="ricercaCapo" action="MacellazioneUnica.do?command=RicercaGlobale" method="post" >
<input type="hidden" id="idMacello" name="idMacello" value="<%=OrgDetails.getOrgId()%>"/> 
<table class="details">
<col width="180px">
<th colspan="2">Ricerca capo</th> 
<tr><td class="formLabel">Matricola</td> <td><input type="text" id="matricola" name="matricola"/></td></tr>
<tr><td colspan="2" align="right"><input type="button" value="CERCA" onClick="if (checkFormCapi(this.form)){this.form.submit();}"/> </td></tr>
</table>
</form>

</td></tr>
</table>

</dhv:container>