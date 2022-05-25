<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
 

<%@page import="java.net.InetAddress"%>

<jsp:useBean id="Censimento" class="ext.aspcfs.modules.apiari.base.StabilimentoVariazioneCensimento" scope="request"/>
<jsp:useBean id="Apiario" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>


<jsp:useBean id="LookupStati" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaSottospecie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaModalita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<%!
	public static String fixValore(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;";
	else
		return code.toUpperCase();
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />


<style>@media all{
.boxIdDocumento {
	left: 5px;
	top:90px;
	}
.boxOrigineDocumento {
	left: 5px;
	top:115px;
	}
}</style>
<body>
<br/><br/><br/>

<table width="100%">
<col width="40%"><col width="60%">
<tr>
<td>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div></td>
<td><div align="right"><img style="text-decoration: none;" height="80px" documentale_url="" src="gestione_documenti/schede/images/<%=AslList.getSelectedValue(Apiario.getIdAsl()).toLowerCase() %>.jpg" /></div></td>
</tr>
</table>

<br/>
<center><u><b>Riepilogo Censimento</b></u></center>
<br/><br/>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<tr>
				<th colspan="2"><strong>ATTIVITA DI APICOLTURA</strong></th>
			</tr>
	
			<tr>
				<td nowrap class="formLabel">Comune Sede Legale</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? toHtml2(Apiario.getOperatore().getSedeLegale()
							.getDescrizioneComune()) : ""%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Cap</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? Apiario.getOperatore().getSedeLegale().getCap() : ""%></td>
			</tr>

			<tr id="searchcodeIdprovinciaTR">

				<td nowrap class="formLabel">Provincia</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? toHtml2(Apiario.getOperatore().getSedeLegale()
							.getDescrizione_provincia()) : ""%>
				</td>
			</tr>


			<tr>
				<td nowrap class="formLabel">Denominazione</td>
				<td><%=Apiario.getOperatore().getRagioneSociale()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">STATO</td>
				<td><%=LookupStati.getSelectedValue(Apiario.getOperatore().getStato())%> <%=Apiario.getOperatore().getDataChiusura() != null ? toDateasString(Apiario.getOperatore().getDataChiusura()) : ""%>
				</td>
			</tr>




			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td><%=Apiario.getOperatore().getRappLegale() != null ? Apiario.getOperatore().getRappLegale().getCodFiscale() : ""%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome)</td>
				<td><%=Apiario.getOperatore().getRappLegale() != null ? Apiario.getOperatore().getRappLegale().getCognome() + " "
							+ Apiario.getOperatore().getRappLegale().getNome() : ""%>

				</td>

			</tr>



			<tr>
				<td class="formLabel" nowrap>Codice Azienda</td>
				<td><%=toHtml2(Apiario.getOperatore().getCodiceAzienda())%></td>
			</tr>


			<tr>
				<td class="formLabel" nowrap>Data Inizio Attivita</td>
				<td><%=toDateasString(Apiario.getOperatore().getDataInizio())%></td>
			</tr>


			<tr>
				<td nowrap class="formLabel">Indirizzo Sede Legale</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? Apiario.getOperatore().getSedeLegale().getVia() : ""%></td>
			</tr>




			<tr>
				<td nowrap class="formLabel">Tipo Attivita</td>
				<td><%=TipoAttivitaApi.getSelectedValue(Apiario.getOperatore().getIdTipoAttivita())%>

				</td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Domicilio Digitale<br>(Pec)
				</td>
				<td><%=toHtml2(Apiario.getOperatore().getDomicilioDigitale())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Telefono Fisso</td>
				<td><%=toHtml2(Apiario.getOperatore().getTelefono1())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Telefono Cellulare</td>
				<td><%=toHtml2(Apiario.getOperatore().getTelefono2())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Fax</td>
				<td><%=toHtml2(Apiario.getOperatore().getFax())%></td>
			</tr>

	<tr>
				<th colspan="2"><strong>APIARIO</strong></th>
			</tr>

			
			<tr>
				<td nowrap class="formLabel">ASL di Competenza</td>
				<td><%=AslList.getSelectedValue(Apiario.getIdAsl())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">PROGRESSIVO</td>
				<td><%=Apiario.getProgressivoBDA()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">DETENTORE</td>
				<td><%=Apiario.getDetentore() != null ? toHtml2(Apiario
							.getDetentore().getCognome())
							+ " "
							+ toHtml2(Apiario.getDetentore().getNome()) : ""%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">CODICE FISCALE DETENTORE</td>
				<td><%=Apiario.getDetentore() != null ? toHtml2(Apiario
							.getDetentore().getCodFiscale()) : ""%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">CLASSIFICAZIONE</td>
				<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(Apiario
							.getIdApicolturaClassificazione()))%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">SOTTOSPECIE</td>
				<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(Apiario
							.getIdApicolturaSottospecie()))%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">MODALITA</td>
				<td><%=toHtml2(ApicolturaModalita.getSelectedValue(Apiario
							.getIdApicolturaModalita()))%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO ALVEARI</td>
				<td><%=Apiario.getNumAlveari()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO SCIAMI / NUCLEI</td>
				<td><%=Apiario.getNumSciami()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">NUMERO PACCHI API</td>
				<td><%=Apiario.getNumPacchi()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO API REGINE</td>
				<td><%=Apiario.getNumRegine()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">DATA APERTURA</td>
				<td><%=toDateasString(Apiario.getDataApertura())%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">DATA CHIUSURA</td>
				<td><%=toDateasString(Apiario.getDataChiusura())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">UBICAZIONE</td>
				<td><%=Apiario.getSedeOperativa().getDescrizioneComune() + " - "
							+ Apiario.getSedeOperativa().getVia() + " , "
							+ Apiario.getSedeOperativa().getLatitudine() + " - "
							+ Apiario.getSedeOperativa().getLongitudine()%></td>
			</tr>

			
	
			<tr>
				<th colspan="2"><strong>CENSIMENTO</strong></th>
			</tr>
	
			<tr>
				<td nowrap class="formLabel">Data variazione</td>
				<td><%=toDateasString(Censimento.getDataCensimento())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Numero alveari</td>
				<td><%=Censimento.getNumAlveari()%></td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Numero sciami/nuclei</td>
				<td><%=Censimento.getNumSciami()%></td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Numero pacchi api</td>
				<td><%=Censimento.getNumPacchi()%></td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Numero api regine</td>
				<td><%=Censimento.getNumRegine()%></td>
			</tr>

</table>




<br/><br/><br/>
<div align="left">Data _______________________</div> <div align="right">Timbro e Firma _________________________</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
</body>
</html>