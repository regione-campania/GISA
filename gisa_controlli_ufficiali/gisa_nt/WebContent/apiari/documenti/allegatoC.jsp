<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="ext.aspcfs.modules.apiari.base.Operatore"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
<%@ page import="java.net.InetAddress"%>
<%@ include file="../../initPage.jsp" %>

<jsp:useBean id="Movimentazione" class="ext.aspcfs.modules.apiari.base.ModelloC" scope="request"/>
<jsp:useBean id="Apiario" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>
<jsp:useBean id="ApiarioDestinazione" class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request"/>
<jsp:useBean id="OperatoreDestinazione" class="ext.aspcfs.modules.apiari.base.Operatore" scope="request"/>
<jsp:useBean id="LookupTipoMovimentazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupStati" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoAttivitaApi" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaSottospecie" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaModalita" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />


<%!
	public static String fixValore(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;";
	else
		return code.toUpperCase();
}
%>

<%
	Operatore operatoreDestinazione = ApiarioDestinazione.getOperatore(); 
	if(operatoreDestinazione==null)
		operatoreDestinazione = OperatoreDestinazione; 

	String labelTipoMovimentazione = "PER COMPRAVENDITA/IMPOLLINAZIONE";
	String labelComune = "Comune Sede Legale";
	if(Movimentazione.getIdTipoMovimentazione()==2)
	{
		labelTipoMovimentazione = "PER NOMADISMO/ALTRO";
		labelComune = "Comune e localita' di destinazione e coordinate geografiche";
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
<center><b>DOCUMENTO DI ACCOMPAGNAMENTO</b></center>
<br/><br/>
Il sottoscritto <%=Apiario.getOperatore().getRappLegale().getCognome() %> <%=Apiario.getOperatore().getRappLegale().getNome() %> residente a <%=Apiario.getOperatore().getRappLegale().getIndirizzo().getDescrizioneComune()%><br/>
in <%=Apiario.getOperatore().getRappLegale().getIndirizzo().getVia() %> tel. <%=Apiario.getOperatore().getTelefono1() %> in qualita' di proprietario degli alveari/dell'apiario sito nel Comune di <%=Apiario.getSedeOperativa().getDescrizioneComune()%><br/>
Prov. <%=ProvinceList.getSelectedValue(Apiario.getSedeOperativa().getProvincia())%> Cod. aziendale <%=Apiario.getOperatore().getCodiceAzienda() %> e Progressivo <%=Apiario.getProgressivoBDA() %><br/>
e-mail <%=Apiario.getOperatore().getDomicilioDigitale() %><br/>
<br/>
<center>dichiara sotto la propria responsabilita' i seguenti spostamenti:</center>
<br/><br/>




<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="4"><strong><b><%=labelTipoMovimentazione%></b></strong></th>
	</tr>
	
	<tr>
		<td nowrap class="formLabel"><b>Tipologia</b></td>
		<td nowrap class="formLabel"><b>Quantita'</b></td>
		<td nowrap class="formLabel"><b>Contrassegnati con i seguenti <br/>numeri identificativi (solo per <br/>alveari, e solo nei casi previsti)</b></td>
		<td nowrap class="formLabel"><b><%=labelComune%></b></td>
	</tr>			
	
	<tr>
		<td nowrap>
			Alveari
		</td>
		<td nowrap>
			<%=Movimentazione.getNumAlveariDaSpostare()%>
		</td><
		<td nowrap>
		</td>
		<td nowrap>
		<%
		if((Movimentazione.getIdTipoMovimentazione()==1 || Movimentazione.getIdTipoMovimentazione()==2) && ApiarioDestinazione!=null)
		{
			 %>
			<%=ApiarioDestinazione.getSedeOperativa().getVia()%> - <%=ApiarioDestinazione.getSedeOperativa().getDescrizioneComune()%>
			<%="Lat.: " + ApiarioDestinazione.getSedeOperativa().getLatitudine()%>
			<%="Long.: " + ApiarioDestinazione.getSedeOperativa().getLongitudine()%> 
			<%
		}
		else if(Movimentazione.getIdTipoMovimentazione()==3)
		{
			out.println(Movimentazione.getIndirizzo_dest() + ", " + Movimentazione.getComune_dest());
		}
			%>
		</td>
	</tr>
	<tr>
		<td nowrap>
			Sciami/Nuclei
		</td nowrap>
		<td nowrap>
			<%=Movimentazione.getNumSciamiDaSpostare() %>
		</td>
		<td nowrap>
		</td>
		<td nowrap>
		<%
		if((Movimentazione.getIdTipoMovimentazione()==1 || Movimentazione.getIdTipoMovimentazione()==2) && ApiarioDestinazione!=null)
		{
			 %>
			<%=ApiarioDestinazione.getSedeOperativa().getVia()%> - <%=ApiarioDestinazione.getSedeOperativa().getDescrizioneComune()%>
			<%="Lat.: " + ApiarioDestinazione.getSedeOperativa().getLatitudine()%>
			<%="Long.: " + ApiarioDestinazione.getSedeOperativa().getLongitudine()%> 
			<%
		}
		else if(Movimentazione.getIdTipoMovimentazione()==3)
		{
			out.println(Movimentazione.getIndirizzo_dest() + ", " + Movimentazione.getComune_dest());
		}
			%>
		</td>
	</tr>
	<tr >
		<td nowrap>
			Pacchi d'api
		</td>
		<td nowrap>
			<%=Movimentazione.getNumPacchiDaSpostare() %>
		</td>
		<td nowrap>
		</td>
		<td nowrap>
		<%
		if((Movimentazione.getIdTipoMovimentazione()==1 || Movimentazione.getIdTipoMovimentazione()==2) && ApiarioDestinazione!=null)
		{
			 %>
			<%=ApiarioDestinazione.getSedeOperativa().getVia()%> - <%=ApiarioDestinazione.getSedeOperativa().getDescrizioneComune()%>
			<%="Lat.: " + ApiarioDestinazione.getSedeOperativa().getLatitudine()%>
			<%="Long.: " + ApiarioDestinazione.getSedeOperativa().getLongitudine()%> 
			<%
		}
		else if(Movimentazione.getIdTipoMovimentazione()==3)
		{
			out.println(Movimentazione.getIndirizzo_dest() + ", " + Movimentazione.getComune_dest());
		}
			%>
		</td>
	</tr>
<%
	//Nel caso è compravendita non si deve vedere
	//Ma se il dato sulle api regine e' stato inserito prima della modifica del flusso 109 - req.2, faccio vedere lo stesso la riga
	if(Movimentazione.getIdTipoMovimentazione()!=1 || Movimentazione.getNumRegineDaSpostare()>0)
	{
%>
	<tr>
		<td nowrap>
			Api Regine
		</td>
		<td nowrap>
			<%=Movimentazione.getNumRegineDaSpostare() %>
		</td><
		<td nowrap>
		</td>
		<td nowrap>
		<%
		if((Movimentazione.getIdTipoMovimentazione()==1 || Movimentazione.getIdTipoMovimentazione()==2) && ApiarioDestinazione!=null)
		{
			 %>
			<%=ApiarioDestinazione.getSedeOperativa().getVia()%> - <%=ApiarioDestinazione.getSedeOperativa().getDescrizioneComune()%>
			<%="Lat.: " + ApiarioDestinazione.getSedeOperativa().getLatitudine()%>
			<%="Long.: " + ApiarioDestinazione.getSedeOperativa().getLongitudine()%> 
			<%
		}
		else if(Movimentazione.getIdTipoMovimentazione()==3)
		{
			out.println(Movimentazione.getIndirizzo_dest() + ", " + Movimentazione.getComune_dest());
		}
			%>
		</td>
	</tr>
<%
	}	
%>
</table>

	<br/><br/>
	<%
	
	if(Movimentazione.getIdTipoMovimentazione()==1)
	{
		String nominativo = Movimentazione.getProprietarioDestinazione();
		if(operatoreDestinazione.getCodiceAzienda()!=null)
			nominativo = operatoreDestinazione.getRappLegale().getCognome() + operatoreDestinazione.getRappLegale().getNome();
		String cf = Movimentazione.getCfProprietarioDestinazione();
		if(operatoreDestinazione.getCodiceAzienda()!=null)
			cf = operatoreDestinazione.getCodFiscale();
		String indirizzo = Movimentazione.getIndirizzo_dest();
		if(operatoreDestinazione.getCodiceAzienda()!=null)
			indirizzo = operatoreDestinazione.getSedeLegale().getVia();
		String comune = Movimentazione.getComune_dest();
		if(operatoreDestinazione.getCodiceAzienda()!=null)
			comune = operatoreDestinazione.getSedeLegale().getDescrizioneComune();
		String prov = Movimentazione.getSigla_prov_comune_dest();
		if(operatoreDestinazione.getCodiceAzienda()!=null)
			prov = ProvinceList.getSelectedValue(   operatoreDestinazione.getSedeLegale().getProvincia());
	%>
		
		Destinati all'azienda* del Sig. <%=nominativo%><br/>
		Cod. aziendale <%=Movimentazione.getCodiceAziendaDestinazione()%> CF <%=cf%><br/>
		Indirizzo sede legale <%=indirizzo%><br/>
		Comune <%=comune%> Prov <%=prov%> Data  <%=toDateasString(Movimentazione.getDataMovimentazione())%>
		<br/><br/>
	<%
	}
	else if(Movimentazione.getIdTipoMovimentazione()==3)
	{
	%>
		Destinati all'azienda* con cf/partita iva <%=Movimentazione.getCfPartitaApicoltore() %><br/>
		Denominazione <%=Movimentazione.getDenominazioneApicoltore()%> <br/>
		Indirizzo sede legale <%=Movimentazione.getIndirizzo_dest()%><br/>
		Comune <%=Movimentazione.getComune_dest()%>
		<br/><br/>
	<%
	}
	%>
	
	* Apicoltore, grossista/distributore, agricoltore (in quest'ultimo caso va indicato il Codice Fiscale)
<%
%>
<br/><br/>

Le presenti informazioni sono registrate direttamente in BDA ad opera del proprietario degli alveari o da persona delegata.
<br/><br/>
<br/><br/>
<br/><br/>
<center><b>ATTESTAZIONE SANITARIA<br/>da compilare nei casi previsti</b></center>
<br/><br/>
Si attesta che l'apiario del Sig. <%=Apiario.getOperatore().getRappLegale().getCognome()%> <%=Apiario.getOperatore().getRappLegale().getNome()%><br/>
sito nel Comune di <%=Apiario.getSedeOperativa().getDescrizioneComune()%> Prov <%=ProvinceList.getSelectedValue(Apiario.getSedeOperativa().getProvincia())%><br/>
<%=Apiario.getSedeOperativa().getVia()%><br/>
Cod. aziendale <%=Apiario.getOperatore().getCodiceAzienda()%><br/>
Coordinate geografiche: Latitudine <%=Apiario.getSedeOperativa().getLatitudine()%>, Longitudine <%=Apiario.getSedeOperativa().getLongitudine()%><br/>
e' sotto controllo sanitario e non e' sottoposto a divieto di spostamento e/o vincoli o misure restrittive di Polizia Veterinaria. <br/><br/>


<br/><br/><br/>
<div align="left">Data _______________________</div> <div align="right">Timbro e Firma _________________________</div>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
</body>
</html>