<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../../initPage.jsp" %> 

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<jsp:useBean id="Scorta" class="org.aspcfs.modules.scortefarmaci.base.ScortaAllevamento" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Response" class="java.lang.String" scope="request"/>
<%@ page import="org.aspcfs.modules.scortefarmaci.base.*"%>

<script>
function modifica(orgId){
	    loadModalWindow();
		window.location.href="GestioneScortaFarmaci.do?command=ModifyScortaAllevamento&orgId="+orgId;
}

function aggiungiVeterinario(orgId){
	loadModalWindow();
	window.location.href="GestioneScortaFarmaci.do?command=ModifyVeterinarioAllevamento&orgId="+orgId;
}
function modificaVeterinario(id, orgId){
	loadModalWindow();
	window.location.href="GestioneScortaFarmaci.do?command=ModifyVeterinarioAllevamento&id="+id+"&orgId="+orgId;
}
</script>

<center><b><U>Gestione Scorta Farmaci su allevamento <%=OrgDetails.getName() %> </U></label></b></center><br/>

<%if (Response!=null && !Response.equals("")){ %>
<div align="center" style="border:thick solid #000000; background-color: lime"><%=Response %></div><br/><br/>
<%} %>

<table cellpadding="5" style="border-collapse: collapse" width="100%" class="details">
<col width="30%">

<tr><th colspan="2">Scorta farmaci</th></tr>

<tr>
<td class="formLabel"> Tipologia della scorta</td>
<td> 
<%= "ALLEVAMENTO".equals(Scorta.getTipoScortaCodice()) ? "ALLEVAMENTO" : ""%> 
</td>
</tr>

<tr>
<td class="formLabel">Numero di registrazione in GISA alla detenzione della scorta</td>
<td>
<%=toHtml(Scorta.getScortaNumAutorizzazione())%>
</td>
</tr>

<tr>
<td class="formLabel">Data di registrazione della scorta<br/> (La data e' quella di cui alla comunicazione/SCIA pervenuta dal  SUAP)</td>
<td>
<%=toHtml(Scorta.getScortaDataInizio()) %>
</td>
</tr>

<tr>
<td class="formLabel">Data di fine validita' della scorta</td>
<td>
<%=toHtml(Scorta.getScortaDataFine()) %>
</td>
</tr>

<tr>
<td class="formLabel">Asl Allevamento</td>
<td> <%=AslList.getSelectedValue(Scorta.getAslCodice())  %>
</td>
</tr>

<tr>
<td class="formLabel">Codice Azienda</td>
<td>
<%=toHtml(Scorta.getCodAzienda())%>
</td>
</tr>

<tr>
<td class="formLabel">ID Fiscale Allevamento</td>
<td>
<%=toHtml(Scorta.getIdFiscaleAllevamento())%>
</td>
</tr>

<tr><td class="formLabel">Modificato da: </td><td><dhv:username id="<%= Scorta.getEnteredBy() %>" /></td></tr>
<tr><td class="formLabel">Modificato il: </td><td><%=toDateasStringWitTime(Scorta.getEntered()) %></td></tr>

<tr>
<td align="center" colspan="2">
<dhv:permission name="scortefarmaci-edit">
<input type="button" value="MODIFICA" onClick="modifica('<%=OrgDetails.getOrgId()%>')"/>
</dhv:permission>
</td></tr>
 

</table>


<% if (Scorta.getLovscortaId()!=null) { %>
<table cellpadding="5" style="border-collapse: collapse" width="100%" class="details">
<col width="30%">

<tr><th colspan="2">Veterinari</th></tr>

<% int i = 0;
for (i = 0; i<Scorta.getVeterinariList().size(); i++) {
	VeterinarioAllevamento vet = (VeterinarioAllevamento)Scorta.getVeterinariList().get(i); %>
<tr><td class="formLabel">Veterinario</td><td><%=vet.getVetPersIdFiscale() %> <%=vet.getFlagResponsabile().equals("S") ? "RESPONSABILE" : vet.getFlagResponsabile().equals("N") ? "DELEGATO" : ""  %> <input type="button" value="MODIFICA" onClick="modificaVeterinario('<%=vet.getId()%>', '<%=OrgDetails.getOrgId()%>')"/></td></tr>
<%} 
if (i<3) { %>
<tr><td class="formLabel">Veterinario</td><td><input type="button" value="AGGIUNGI" onClick="aggiungiVeterinario('<%=OrgDetails.getOrgId()%>')"/></td></tr>
<% }%>
<% } %>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>


