<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="Richiesta" class="org.aspcfs.modules.sintesis.base.StabilimentoSintesisImport" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>

<jsp:useBean id="StatiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiLinea" class="org.aspcfs.utils.web.LookupList" scope="request" />


<%@ include file="../initPage.jsp" %>

<style>
.rosso { background-color:#ffcccc; }

</style>

   
   <%if (Stabilimento.getIdStabilimento()<=0){ %>
   <script>
   alert('Stabilimento non presente in GISA con questo Approval Number, questa Partita IVA e questa Linea Produttiva');
   window.close();
   </script>
    <%} %>
   
  		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<tr><th colspan="4">Informazioni</th></tr>
<tr><th colspan="2">Sintesis</th><th colspan="2">GISA</th></tr>

<tr>
<td class="formLabel">Stato sede operativa</td>

<td <%= !Richiesta.getStatoSedeOperativa().equalsIgnoreCase(StatiStabilimento.getSelectedValue(Stabilimento.getStato())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getStatoSedeOperativa() %></td> 
<td <%= !Richiesta.getStatoSedeOperativa().equalsIgnoreCase(StatiStabilimento.getSelectedValue(Stabilimento.getStato())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=StatiStabilimento.getSelectedValue(Stabilimento.getStato()) %></td>

</tr>

<tr>
<td class="formLabel">Approval number</td>
<td <%= !Richiesta.getApprovalNumber().equalsIgnoreCase(Stabilimento.getApprovalNumber()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getApprovalNumber() %></td> 
<td <%= !Richiesta.getApprovalNumber().equalsIgnoreCase(Stabilimento.getApprovalNumber()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getApprovalNumber() %></td>

</tr>

<tr>
<td class="formLabel">Denominazione sede operativa</td>
<td <%= !Richiesta.getDenominazioneSedeOperativa().equalsIgnoreCase(Stabilimento.getDenominazione()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getDenominazioneSedeOperativa() %></td>
<td <%= !Richiesta.getDenominazioneSedeOperativa().equalsIgnoreCase(Stabilimento.getDenominazione()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getDenominazione() %></td>

</tr>

<tr>
<td class="formLabel">Ragione Sociale Impresa</td>
<td <%= !Richiesta.getRagioneSocialeImpresa().equalsIgnoreCase(Stabilimento.getOperatore().getRagioneSociale()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getRagioneSocialeImpresa() %></td> 
<td <%= !Richiesta.getRagioneSocialeImpresa().equalsIgnoreCase(Stabilimento.getOperatore().getRagioneSociale()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getOperatore().getRagioneSociale() %></td>

</tr>

<tr>
<td class="formLabel">Partita IVA</td>
<td <%= !Richiesta.getPartitaIva().equalsIgnoreCase(Stabilimento.getOperatore().getPartitaIva()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getPartitaIva() %></td> 
<td <%= !Richiesta.getPartitaIva().equalsIgnoreCase(Stabilimento.getOperatore().getPartitaIva()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getOperatore().getPartitaIva() %></td>

</tr>

<tr>
<td class="formLabel">Codice Fiscale</td>
<td <%= !Richiesta.getCodiceFiscale().equalsIgnoreCase(Stabilimento.getOperatore().getCodiceFiscaleImpresa()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getCodiceFiscale() %></td> 
<td <%= !Richiesta.getCodiceFiscale().equalsIgnoreCase(Stabilimento.getOperatore().getCodiceFiscaleImpresa()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getOperatore().getCodiceFiscaleImpresa()%></td>
</tr>

<tr>
<td class="formLabel">Indirizzo</td>
<td <%= !Richiesta.getIndirizzo().replaceAll(" ", "").equalsIgnoreCase((Stabilimento.getIndirizzo().getDescrizioneToponimo() +" "+ Stabilimento.getIndirizzo().getVia() +" "+Stabilimento.getIndirizzo().getCivico()).replaceAll(" ", "")) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getIndirizzo() %></td> 
<td <%= !Richiesta.getIndirizzo().replaceAll(" ", "").equalsIgnoreCase((Stabilimento.getIndirizzo().getDescrizioneToponimo() +" "+ Stabilimento.getIndirizzo().getVia() +" "+Stabilimento.getIndirizzo().getCivico()).replaceAll(" ", "")) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=(Stabilimento.getIndirizzo().getDescrizioneToponimo() +" "+ Stabilimento.getIndirizzo().getVia() +" "+Stabilimento.getIndirizzo().getCivico()).trim() %></td>
</tr>

<tr>
<td class="formLabel">Comune</td>
<td <%= !Richiesta.getComune().equalsIgnoreCase(Stabilimento.getIndirizzo().getDescrizioneComune()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getComune() %></td> 
<td <%= !Richiesta.getComune().equalsIgnoreCase(Stabilimento.getIndirizzo().getDescrizioneComune()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getIndirizzo().getDescrizioneComune() %></td>
</tr>

<tr>
<td class="formLabel">Provincia</td>
<td <%= !Richiesta.getProvincia().equalsIgnoreCase(Stabilimento.getIndirizzo().getDescrizione_provincia()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getProvincia() %></td> 
<td <%= !Richiesta.getProvincia().equalsIgnoreCase(Stabilimento.getIndirizzo().getDescrizione_provincia()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getIndirizzo().getDescrizione_provincia() %></td>
</tr>


<tr>
<td class="formLabel">Attivita'</td>
<td>
<%=Richiesta.getDescrizioneSezione() %> -> <%=Richiesta.getAttivita() %> </td> 
<td>
<%=Stabilimento.getLinee().get(0).getPathCompleto() %></td>
</tr>

<tr>
<td class="formLabel">Stato attivita'</td>
<td <%= !Richiesta.getStatoAttivita().equalsIgnoreCase(StatiLinea.getSelectedValue(Stabilimento.getLinee().get(0).getStato())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getStatoAttivita() %></td> 
<td <%= !Richiesta.getStatoAttivita().equalsIgnoreCase(StatiLinea.getSelectedValue(Stabilimento.getLinee().get(0).getStato())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=StatiLinea.getSelectedValue(Stabilimento.getLinee().get(0).getStato()) %></td>

</tr>

<tr>
<td class="formLabel">Data inizio attivita'</td>
<td <%= !Richiesta.getDataInizioAttivita().replaceAll("-", "").equalsIgnoreCase(toDateasString(Stabilimento.getLinee().get(0).getDataInizio())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getDataInizioAttivita() %></td>  
<td <%= !Richiesta.getDataInizioAttivita().replaceAll("-", "").equalsIgnoreCase(toDateasString(Stabilimento.getLinee().get(0).getDataInizio())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=toDateasString(Stabilimento.getLinee().get(0).getDataInizio()) %></td>
</tr>

<tr>
<td class="formLabel">Data fine attivita'</td>
<td <%= !Richiesta.getDataFineAttivita().replaceAll("-", "").equalsIgnoreCase(toDateasString(Stabilimento.getLinee().get(0).getDataFine())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getDataFineAttivita() %></td>  
<td <%= !Richiesta.getDataFineAttivita().replaceAll("-", "").equalsIgnoreCase(toDateasString(Stabilimento.getLinee().get(0).getDataFine())) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=toDateasString(Stabilimento.getLinee().get(0).getDataFine()) %></td>
</tr>

<tr>
<td class="formLabel">Tipo autorizzazione</td>
<td <%= !Richiesta.getTipoAutorizzazione().equalsIgnoreCase(Stabilimento.getLinee().get(0).getTipoAutorizzazione()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getTipoAutorizzazione() %></td>  
<td <%= !Richiesta.getTipoAutorizzazione().equalsIgnoreCase(Stabilimento.getLinee().get(0).getTipoAutorizzazione()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getLinee().get(0).getTipoAutorizzazione() %></td>
</tr>

<tr>
<td class="formLabel">Imballaggio</td>
<td <%= !Richiesta.getImballaggio().equalsIgnoreCase(Stabilimento.getLinee().get(0).getImballaggio()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getImballaggio() %></td>  
<td <%= !Richiesta.getImballaggio().equalsIgnoreCase(Stabilimento.getLinee().get(0).getImballaggio()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getLinee().get(0).getImballaggio() %></td>
</tr>

<tr>
<td class="formLabel">Paesi abilitati export</td>
<td <%= !Richiesta.getPaesiAbilitatiExport().equalsIgnoreCase(Stabilimento.getLinee().get(0).getPaesiAbilitatiExport()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getPaesiAbilitatiExport() %></td>  
<td <%= !Richiesta.getPaesiAbilitatiExport().equalsIgnoreCase(Stabilimento.getLinee().get(0).getPaesiAbilitatiExport()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getLinee().get(0).getPaesiAbilitatiExport() %></td>
</tr>

<tr>
<td class="formLabel">Remark</td>
<td <%= !Richiesta.getRemark().equalsIgnoreCase(Stabilimento.getLinee().get(0).getRemark()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getRemark() %></td>  
<td <%= !Richiesta.getRemark().equalsIgnoreCase(Stabilimento.getLinee().get(0).getRemark()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getLinee().get(0).getRemark() %></td>
</tr>

<tr>
<td class="formLabel">Species</td>
<td <%= !Richiesta.getSpecies().equalsIgnoreCase(Stabilimento.getLinee().get(0).getSpecies()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Richiesta.getSpecies() %></td>  
<td <%= !Richiesta.getSpecies().equalsIgnoreCase(Stabilimento.getLinee().get(0).getSpecies()) ? "class=\"rosso\"" : "class=\"bianco\"" %>>
<%=Stabilimento.getLinee().get(0).getSpecies() %></td>
</tr>



</table>
	