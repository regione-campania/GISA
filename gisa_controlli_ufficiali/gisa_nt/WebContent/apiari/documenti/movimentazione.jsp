<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
 

<%@page import="java.net.InetAddress"%>

<jsp:useBean id="Movimentazione" class="ext.aspcfs.modules.apiari.base.ModelloC" scope="request"/>
<jsp:useBean id="Apiario" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>

<jsp:useBean id="LookupTipoMovimentazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
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
<style>
/* form details */
.details {
	border-top: 1px solid #888888;
	border-left: 1px solid #888888;
}

.details th {
	padding-top: 2px;
	padding-bottom: 2px;
	padding-left: 2px;
	padding-right: 2px;
	background-color: #BDCFFF;
	border-bottom: 1px solid #888888;
	border-right: 1px solid #888888;
	color: #000;
	text-align: left;
}

.details td {
	background-image: url(../images/layout/dot-h.gif);
	background-position: bottom left;
	background-repeat: repeat-x;
	border-right: 1px solid #888888;
}

.details th a:link,.details th a:visited {
	color: #FFF;
}

.formLabel {
	text-align: right !important;
	width: 100 !important;
	background-color: #EDEDED !important;
	border-right: 1px solid #9C9A9C;
}

</style>


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
<center><u><b>Riepilogo Movimentazione</b></u></center>
<br/><br/>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
			<tr>
				<th colspan="4"><strong>ATTIVITA DI APICOLTURA</strong></th>
			</tr>
	
			<tr>
				<td nowrap class="formLabel">Comune Sede Legale</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? toHtml2(Apiario.getOperatore().getSedeLegale()
							.getDescrizioneComune()) : ""%></td>
			
			<td nowrap class="formLabel">Cap</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? Apiario.getOperatore().getSedeLegale().getCap() : ""%></td>
			</tr>

			<tr id="searchcodeIdprovinciaTR">

				<td nowrap class="formLabel">Provincia</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? toHtml2(Apiario.getOperatore().getSedeLegale()
							.getDescrizione_provincia()) : ""%>
				</td>
				
				<td nowrap class="formLabel"></td>
				<td></td>
			</tr>


			<tr>
				<td nowrap class="formLabel">Denominazione</td>
				<td><%=Apiario.getOperatore().getRagioneSociale()%></td>
			
				<td nowrap class="formLabel">STATO</td>
				<td><%=LookupStati.getSelectedValue(Apiario.getOperatore().getStato())%> <%=Apiario.getOperatore().getDataChiusura() != null ? toDateasString(Apiario.getOperatore().getDataChiusura()) : ""%>
				</td>
			</tr>



			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td><%=Apiario.getOperatore().getRappLegale() != null ? Apiario.getOperatore().getRappLegale().getCodFiscale() : ""%></td>
			
				<td nowrap class="formLabel">Proprietario (cognome e nome)</td>
				<td><%=Apiario.getOperatore().getRappLegale() != null ? Apiario.getOperatore().getRappLegale().getCognome() + " "
							+ Apiario.getOperatore().getRappLegale().getNome() : ""%>

				</td>

			</tr>



			<tr>
				<td class="formLabel" nowrap>Codice Azienda</td>
				<td><%=toHtml2(Apiario.getOperatore().getCodiceAzienda())%></td>
			
			<td class="formLabel" nowrap>Data Inizio Attivita</td>
				<td><%=toDateasString(Apiario.getOperatore().getDataInizio())%></td>
			</tr>


			<tr>
				<td nowrap class="formLabel">Indirizzo Sede Legale</td>
				<td><%=Apiario.getOperatore().getSedeLegale() != null ? Apiario.getOperatore().getSedeLegale().getVia() : ""%></td>
		
				<td nowrap class="formLabel">Tipo Attivita</td>
				<td><%=TipoAttivitaApi.getSelectedValue(Apiario.getOperatore().getIdTipoAttivita())%>

				</td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Domicilio Digitale<br>(Pec)
				</td>
				<td><%=toHtml2(Apiario.getOperatore().getDomicilioDigitale())%></td>
			
				<td nowrap class="formLabel">Telefono Fisso</td>
				<td><%=toHtml2(Apiario.getOperatore().getTelefono1())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Telefono Cellulare</td>
				<td><%=toHtml2(Apiario.getOperatore().getTelefono2())%></td>
			
				<td nowrap class="formLabel">Fax</td>
				<td><%=toHtml2(Apiario.getOperatore().getFax())%></td>
			</tr>

	<tr>
				<th colspan="4"><strong>APIARIO</strong></th>
			</tr>

			
			<tr>
				<td nowrap class="formLabel">ASL di Competenza</td>
				<td><%=AslList.getSelectedValue(Apiario.getIdAsl())%></td>
			
				<td nowrap class="formLabel">PROGRESSIVO</td>
				<td><%=Apiario.getProgressivoBDA()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">DETENTORE</td>
				<td><%=Apiario.getDetentore() != null ? toHtml2(Apiario
							.getDetentore().getCognome())
							+ " "
							+ toHtml2(Apiario.getDetentore().getNome()) : ""%></td>
			
				<td nowrap class="formLabel">CODICE FISCALE DETENTORE</td>
				<td><%=Apiario.getDetentore() != null ? toHtml2(Apiario
							.getDetentore().getCodFiscale()) : ""%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">CLASSIFICAZIONE</td>
				<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(Apiario
							.getIdApicolturaClassificazione()))%></td>
			
				<td nowrap class="formLabel">SOTTOSPECIE</td>
				<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(Apiario
							.getIdApicolturaSottospecie()))%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">MODALITA</td>
				<td><%=toHtml2(ApicolturaModalita.getSelectedValue(Apiario
							.getIdApicolturaModalita()))%></td>
							<td nowrap class="formLabel"></td>
				<td></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO ALVEARI</td>
				<td><%=Apiario.getNumAlveari()%></td>
			
				<td nowrap class="formLabel">NUMERO SCIAMI / NUCLEI</td>
				<td><%=Apiario.getNumSciami()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">NUMERO PACCHI API</td>
				<td><%=Apiario.getNumPacchi()%></td>
			
				<td nowrap class="formLabel">NUMERO API REGINE</td>
				<td><%=Apiario.getNumRegine()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">DATA APERTURA</td>
				<td><%=toDateasString(Apiario.getDataApertura())%></td>
			
				<td nowrap class="formLabel">DATA CHIUSURA</td>
				<td><%=toDateasString(Apiario.getDataChiusura())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">UBICAZIONE</td>
				<td><%=Apiario.getSedeOperativa().getDescrizioneComune() + " - "
							+ Apiario.getSedeOperativa().getVia() + " , "
							+ Apiario.getSedeOperativa().getLatitudine() + " - "
							+ Apiario.getSedeOperativa().getLongitudine()%></td>
							
							<td nowrap class="formLabel"></td>
				<td></td>
			</tr>

			
	
			<tr>
				<th colspan="4"><strong>MOVIMENTAZIONE</strong></th>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Tipo movimentazione</td>
			<td><%=LookupTipoMovimentazione.getSelectedValue(Movimentazione.getIdTipoMovimentazione())%></td>
			
				<td nowrap class="formLabel">Data variazione</td>
				<td><%=toDateasString(Movimentazione.getDataMovimentazione())%></td>
			</tr>

<tr>
				<td nowrap class="formLabel">Data modello</td>
				<td><%=toDateasString(Movimentazione.getData_modello())%></td>
			
				<td nowrap class="formLabel">Codice azienda origine</td>
				<td><%=Movimentazione.getCodiceAziendaOrigine()%></td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Progr. apiario origine</td>
				<td><%=Movimentazione.getProgressivoApiarioOrigine()%></td>
			
				<td nowrap class="formLabel">Num. alveari origine</td>
				<td><%=Movimentazione.getNumApiariOrigine()%></td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Azienda destinazione</td>
				<td><%=(Movimentazione.getIdTipoMovimentazione()!=3)?("Codice azienda: " + Movimentazione.getCodiceAziendaDestinazione())     :("Cf: " + Movimentazione.getCfPartitaApicoltore() + "<br/>" + "Denominazione: " + Movimentazione.getDenominazioneApicoltore())%></td>
			
			
				<td nowrap class="formLabel"><%=(Movimentazione.getIdTipoMovimentazione()!=3)?("Progr. apiario destinazione") :("Sede legale azienda destinazione")%></td>
				<td><%=(Movimentazione.getIdTipoMovimentazione()!=1)?((Movimentazione.getProgressivoApiarioDestinazione()!=null)?(Movimentazione.getProgressivoApiarioDestinazione()):("")):(Movimentazione.getIndirizzo_dest() + ", " + Movimentazione.getComune_dest())%></td>
			</tr>
			
			<%
			if(Movimentazione.getComune_dest()!=null && !Movimentazione.getComune_dest().equals("") && !Movimentazione.getComune_dest().equalsIgnoreCase("null"))
			{
			%>
			<tr>
				<td nowrap class="formLabel">Comune apiario di destinazione</td>
				<td><%=Movimentazione.getComune_dest()%></td>
			
			
				<td nowrap class="formLabel">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<%
			}
			%>
			
			
			<tr>
				<td nowrap class="formLabel">Num. alveari apiario destinazione</td>
				<td><%=Movimentazione.getNumApiariDestinazione()%></td>
			
				<td nowrap class="formLabel"><%=(Movimentazione.getIdTipoMovimentazione()!=3)?("&nbsp;") :("Recupero materiale biologico")%></td>
				<td><%=(Movimentazione.getIdTipoMovimentazione()!=3)?("&nbsp;") :((Movimentazione.getRecuperoMaterialeBiologico())?("SI"):("NO"))%></td>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Num. sciami da spostare</td>
				<td><%=Movimentazione.getNumSciamiDaSpostare()%></td>
<%
			//Nel caso è compravendita non si deve vedere
			//Ma se il dato sulle api regine e' stato inserito prima della modifica del flusso 109 - req.2, faccio vedere lo stesso la riga
			if(Movimentazione.getIdTipoMovimentazione()!=1 || Movimentazione.getNumRegineDaSpostare()>0)
			{
%>				
				<td nowrap class="formLabel">Num. regine da spostare</td>
				<td><%=Movimentazione.getNumRegineDaSpostare()%></td>
<%
			}
%>
			</tr>
			
			<tr>
				<td nowrap class="formLabel">Num. pacchi da spostare</td>
				<td><%=Movimentazione.getNumPacchiDaSpostare()%></td>
			
				<td nowrap class="formLabel">Num. alveari da spostare</td>
				<td><%=Movimentazione.getNumAlveariDaSpostare()%></td>
			</tr>
<%
			if(Movimentazione.getIdTipoMovimentazione()==3)
			{
%>
				<tr>
					<td nowrap class="formLabel">Recupero materiale biologico</td>
					<td><%=(Movimentazione.getRecuperoMaterialeBiologico())?("SI"):("NO") %></td>
					<td nowrap class="formLabel">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>	
<%
			}
%>
			

</table>




<br/><br/><br/>
<div align="left">Data _______________________</div> <div align="right">Timbro e Firma _________________________</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
</body>
</html>