<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="esitoPregressa" class="java.lang.String" scope="request" />

<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@ include file="../initPage.jsp" %>

<script>
function vaiAnagrafica(id){
	var url = "OpuStab.do?command=Details&idFarmacia="+id+"&opId="+id+"&stabId="+id;
	loadModalWindow();
	window.location.href=url;
	}
</script>


<%
String nomeContainer = StabilimentoDettaglio.getContainer();
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
nomeContainer = "suap";
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore()+"&altId="+StabilimentoDettaglio.getAltId();
%>

<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>"  hideContainer="false">

<center>
<font size="3px">Operazione di aggiunta linea pregressa</font><br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="2">Riepilogo</th></tr>

<tr>
<th colspan="2"><%=esitoPregressa %></th>
</tr>

<tr><th colspan="2">Riepilogo</th></tr>


<tr>
<td class="formLabel">Numero registrazione</td>
<td><%=StabilimentoDettaglio.getNumero_registrazione() %></td>
</tr>

<tr>
<td class="formLabel">Ragione Sociale Impresa</td>
<td><%=StabilimentoDettaglio.getOperatore().getRagioneSociale() %></td>
</tr>

<tr>
<td class="formLabel">Data inizio attivita'</td>
<td><%=toDateasString(StabilimentoDettaglio.getDataInizioAttivita()) %>
<input type="hidden" id="dataInizio" name="dataInizio" value="<%=toDateasString(StabilimentoDettaglio.getDataInizioAttivita())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">Linee produttive</td>
<td>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<% for (int i = 0; i<StabilimentoDettaglio.getListaLineeProduttive().size(); i++){
	LineaProduttiva linea = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
	%>
<tr>
<td><%=linea.getNumeroRegistrazione() %></td>
<td><%=linea.getDescrizione_linea_attivita().replace("->", "-><br/>") %>
</tr>
<% } %>
</table>

</td>
</tr>
	

<tr><th>TORNA ALL'ANAGRAFICA</th>
<td><input type="button" value="VAI" onClick="vaiAnagrafica('<%=StabilimentoDettaglio.getIdStabilimento()%>')"/> </td>
</tr>

</table>

</center></dhv:container>