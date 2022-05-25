<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="AllevamentoBDN" class="org.aspcfs.modules.allevamenti.base.AllevamentoAjax" scope="request"/>
<jsp:useBean id="AziendaBDN" class="it.izs.bdn.bean.InfoAziendaBean" scope="request"/>
<jsp:useBean id="DiffenzeBDN" class="java.util.ArrayList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popLookupSelect.js"></script>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>		
		
<html>
<head>
<title>INTERROGAZIONE BDN</title>

</head>

<body>
	

		
		
		<dhv:evaluate if="<%= !(DiffenzeBDN.isEmpty()) %>">
		<h3 style="color:red;text-align:center">RISCONTRATO DISALLINEAMENTO CON BDN</h3>
		</dhv:evaluate>
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr><th colspan="2"><strong>Dati Allevamento in BDN</strong></th></tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Denominazione</td>
		<td>
			<font color="<%=(DiffenzeBDN.contains("denominazione")) ? "red" : ""%>">
				<%= AllevamentoBDN.getDenominazione() %>
			</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Codice Azienda</td>
		<td><%= AllevamentoBDN.getCodice_azienda() %></td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Partita IVA/Codice fiscale</td>
		<td>
			<font color="<%=(DiffenzeBDN.contains("partita_iva_codice_fiscale") ) ? "red" : ""%>">
			<%=AllevamentoBDN.getCodice_fiscale() %></td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Indirizzo sede legale</td>
		<td>
		<font color="<%=(DiffenzeBDN.contains("indirizzoLegale") || DiffenzeBDN.contains("comuneLegale")) ? "red" : ""%>">
			<%= AllevamentoBDN.getIndirizzo()%> <%= AllevamentoBDN.getComune()%>
		</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Indirizzo sede operativa</td>
		<td>
		<font color="<%=(DiffenzeBDN.contains("indirizzoOperativa") || DiffenzeBDN.contains("comuneOperativa")) ? "red" : ""%>">
			<%= AziendaBDN.getIndirizzo_Azienda()%> <%= AziendaBDN.getNomeComuneAzienda()%>
		</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Data Inizio Attività</td>
		<td> 
		<font color="<%=(DiffenzeBDN.contains("data_inizio_attivita")) ? "red" : ""%>">
		<%= AllevamentoBDN.getData_inizio_attivita() %>
		</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Data Fine Attività</td>
		<td>
		<font color="<%=(DiffenzeBDN.contains("data_fine_attivita")) ? "red" : ""%>">
		<%=(AllevamentoBDN.getData_fine_attivita()!=null)?(AllevamentoBDN.getData_fine_attivita()):("Non presente")%>
		</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Specie Allevata</td>
			<td>
				<font color="<%=(DiffenzeBDN.contains("specie_allev")) ? "red" : ""%>">
					<%= AllevamentoBDN.getDescrizione_specie() %>
				</font>
			</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Orientamento Produttivo</td>
		<td>
			<font color="<%=(DiffenzeBDN.contains("orientamento_prod")) ? "red" : ""%>">
				<%= AllevamentoBDN.getOrientamento_prod() %>
			</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Codice Tipo Allevamento</td>
		<td>
			<font color="<%=(DiffenzeBDN.contains("codice_tipo_allevamento")) ? "red" : ""%>">
				<%= AllevamentoBDN.getCodiceTipoAllevamento()%>
			</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Tipologia Struttura</td>
		<td>
			<font color="<%=(DiffenzeBDN.contains("tipologia_strutt")) ? "red" : ""%>">
				<%= AllevamentoBDN.getTipologia_strutt() %>
			</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Numero di Capi Totali</td>
		<td>
		<font color="<%=(DiffenzeBDN.contains("numero_capi")) ? "red" : ""%>">
		<%= AllevamentoBDN.getNumero_capi() %>
		</font>
		</td>
		</tr>
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Note:</td>
		<td><%if (AllevamentoBDN.getNote() != null){ %><%= AllevamentoBDN.getNote() %><% } %></td>
		</tr>	
		
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Cf Proprietario</td>
		<td>
		<font color="<%=(DiffenzeBDN.contains("cfProprietario")) ? "red" : ""%>">
		<%if (AllevamentoBDN.getCfProprietario() != null){ %><%= AllevamentoBDN.getCfProprietario() %><% } %>
		</font>
		</td>
		</tr>	
		
		<tr class="containerBody">
		<td nowrap class="formLabel">Cf Detentore</td>
		<td>
		<font color="<%=(DiffenzeBDN.contains("cfDetentore")) ? "red" : ""%>">
		<%if (AllevamentoBDN.getCfDetentore() != null){ %><%= AllevamentoBDN.getCfDetentore() %><% } %>
		</font>
		</td>
		</tr>	
		
		</table>
		
		<form method="post" action = "Allevamenti.do?command=SincronizzaConBDN" onSubmit="loadModalWindow()">
	    <input type = "hidden" name = "denominazione" value = "<%=AllevamentoBDN.getDenominazione().replaceAll("\"","&quot;")%>">
	    <input type = "hidden" name = "codiceTipoAllevamento" value = "<%= AllevamentoBDN.getCodiceTipoAllevamento()%>">
	    
	    
		
		<input type = "hidden" name = "codAzienda" value = "<%=OrgDetails.getAccountNumber().trim() %>">
		<input type = "hidden" name = "idFiscale" value = "<%=AllevamentoBDN.getCodice_fiscale()%>">
		<input type = "hidden" name = "specie" value = "<%="0"+OrgDetails.getSpecieA() %>">
		<input type = "hidden" name = "orgId" value = "<%=OrgDetails.getOrgId() %>">
		
		<input type = "hidden" name = "capLegale" value = "<%=(AllevamentoBDN.getCap()!=null && !AllevamentoBDN.getCap().equals("null"))?(AllevamentoBDN.getCap()):("")%>">
		<input type = "hidden" name = "indirizzoLegale" value = "<%=(AllevamentoBDN.getIndirizzo()!=null && !AllevamentoBDN.getIndirizzo().equals("null"))?(AllevamentoBDN.getIndirizzo()):("")%>">
		<input type = "hidden" name = "comuneLegale" value = "<%= AllevamentoBDN.getComune()%>">
	
		<input type = "hidden" name = "indirizzoOperativa" value = "<%=(AziendaBDN.getIndirizzo_Azienda()!=null && !AziendaBDN.getIndirizzo_Azienda().equals("null"))?(AziendaBDN.getIndirizzo_Azienda()):("")%>">
		<input type = "hidden" name = "codComuneOperativa" value = "<%= AziendaBDN.getCod_comune_azienda()%>">
		<input type = "hidden" name = "istatComuneOperativa" value = "<%= AziendaBDN.getComuneAziendaCalcolato()%>">
		<input type = "hidden" name = "capOperativa" value = "<%= AziendaBDN.getCap_azienda()%>">
		<input type = "hidden" name = "latitudineOperativa" value = "<%=(AziendaBDN.getLatitudine()!=null)?(AziendaBDN.getLatitudine()):("")%>">
		<input type = "hidden" name = "longitudineOperativa" value = "<%=(AziendaBDN.getLongidutine()!=null)?(AziendaBDN.getLongidutine()):("")%>">

		<input type="hidden"  id="dataInizioAttivita" name="dataInizioAttivita" value="<%= AllevamentoBDN.getData_inizio_attivita()%>"/>
		<input type="hidden"  id="orientamento_prod" name="orientamento_prod" value="<%= AllevamentoBDN.getOrientamento_prod()%>"/>	
		<input type="hidden"  id="tipologia_strutt" name="tipologia_strutt" value="<%= AllevamentoBDN.getTipologia_strutt()%>"/>	
		<input type="hidden"  id="dataFineAttivita" name="dataFineAttivita" value="<%= AllevamentoBDN.getData_fine_attivita() %>"/>
		<input type="hidden"  id="specieAllevata" name="specieAllevata" value="0<%= AllevamentoBDN.getSpecie_allevata() %>"/>
		<input type="hidden"  id="numeroCapi" name="numeroCapi" value="<%= AllevamentoBDN.getNumero_capi() %>"/>
		<input type="hidden"  id="codiceFiscaleProp" name="codiceFiscaleProp" value="<%= AllevamentoBDN.getCfProprietario() %>"/>
		<input type="hidden"  id="codiceFiscaleDet" name="codiceFiscaleDet" value="<%= AllevamentoBDN.getCfDetentore() %>"/>
		
		
		
		
		<input type = "submit" value = "Sincronizza">
		</form>
		
	</body>
	
</html>
		