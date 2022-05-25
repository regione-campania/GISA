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

<jsp:useBean id="Veterinario" class="org.aspcfs.modules.scortefarmaci.base.VeterinarioAllevamento" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="Response" class="java.lang.String" scope="request"/>
<jsp:useBean id="comunicazioneOK" class="java.lang.String" scope="request"/>

<script>

function salva(form){

	if (confirm("Confermare l'inserimento? ATTENZIONE. Verra' eseguito l'invio dei dati.")){
		form.action = "GestioneScortaFarmaci.do?command=InsertVeterinarioAllevamento&auto-populate=true";
		loadModalWindow();
		form.submit();
		}
	}
	
function aggiorna(form){

	if (confirm("Confermare l'aggiornamento? ATTENZIONE. Verra' eseguito l'invio dei dati.")){
		form.action = "GestioneScortaFarmaci.do?command=UpdateVeterinarioAllevamento&auto-populate=true";
		loadModalWindow();
		form.submit();
		}
	}
	
function annulla(orgId){
	if (confirm("Annullare l'operazione?")){
		loadModalWindow();
		window.location.href="GestioneScortaFarmaci.do?command=ViewScortaAllevamento&orgId="+orgId;
	}
}

</script>

<center><b><U>Gestione Scorta Farmaci su allevamento <%=OrgDetails.getName() %> </U></label></b></center><br/>

<%if (Response!=null && !Response.equals("")){ %>
<div align="center" style="border:thin dotted #000000; background-color: #ffccbb">Salvataggio annullato. Motivazione:</div>
<div align="center" style="border:thick solid #000000; background-color: yellow"><%=Response %></div><br/><br/>
<%} %>

<%if (comunicazioneOK!=null && comunicazioneOK.equals("false")){ %>
<div align="center" style="border:thick solid #000000; background-color: red">ERRORE NELLA COOPERAZIONE APPLICATIVA</div><br/><br/>
<%} %>

<form id = "addAccount" name="addAccount" action="" method="post" >

<input type="hidden" id="orgId" name="orgId" value="<%=OrgDetails.getOrgId()%>"/>
<input type="hidden" id="id" name="id" value="<%=Veterinario.getId()%>"/>

<table cellpadding="5" style="border-collapse: collapse" width="100%" class="details">
<col width="30%">

<tr><th colspan="2">Veterinario</th></tr>

<tr>
<td class="formLabel">Tipologia della scorta </td>
<td> 
<input type="text" readonly="readonly" id="tipoScortaCodice" name="tipoScortaCodice" value="<%=toHtml(Veterinario.getTipoScortaCodice())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">Codice Azienda</td>
<td>
<input type="text" readonly="readonly" id="codAzienda" name="codAzienda" value="<%=toHtml(Veterinario.getCodAzienda())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">ID Fiscale Allevamento </td>
<td>
<input type="text" readonly="readonly" id="idFiscaleAllevamento" name="idFiscaleAllevamento" value="<%=toHtml(Veterinario.getIdFiscaleAllevamento())%>"/>
</td>
</tr>

<tr>
<td class="formLabel">Codice fiscale veterinario</td>
<td>
<input type="text" id="vetPersIdFiscale" name="vetPersIdFiscale" value="<%=toHtml(Veterinario.getVetPersIdFiscale())%>" maxlength="16"/>
</td>
</tr>

<tr>
<td class="formLabel">Responsabile/Delegato</td>
<td>
<input type="radio" id="flagResponsabile1" name="flagResponsabile" value="S" <%=Veterinario.getFlagResponsabile().equals("S") ? "checked=\"checked\"" : "" %>/> Responsabile
<input type="radio" id="flagResponsabile2" name="flagResponsabile" value="N"<%=Veterinario.getFlagResponsabile().equals("N") ? "checked=\"checked\"" : "" %>/> Delegato   
</td>
</tr>

<tr>
<td align= "center" colspan="2"><input type="button" value="ANNULLA" onClick="annulla('<%=OrgDetails.getOrgId()%>')"/> 

<%if (Veterinario.getScovetId()==null) { %>
<input type="button" value="SALVA E INVIA" onClick="salva(this.form)"/>
<% } else { %>
<input type="button" value="AGGIORNA E SINCRONIZZA" onClick="aggiorna(this.form)"/>
<%} %>
</td>
</tr>
</table>

</form>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
