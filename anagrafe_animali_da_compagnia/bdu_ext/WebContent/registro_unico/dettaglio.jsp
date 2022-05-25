<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<jsp:useBean id="dettaglio" class="org.aspcfs.modules.anagrafe_animali.base.RegistroUnico" scope="request" />

<link rel="stylesheet" href="css/jquery-ui.css" />
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"/>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="registro" class="org.aspcfs.modules.anagrafe_animali.base.RegistroUnico" scope="request" />
<%@ include file="../initPage.jsp"%>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
</SCRIPT>


<body onload="">

<style>
/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 20%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
</style>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

		<th colspan="2">
			REGISTRO UNICO CANI A RISCHIO ELEVATO DI AGGRESSIVITA' O.M. 06/08/2003 e s.m.i. art 3 comma 3
		</th>
		<tr class="containerBody">
			<td nowrap class="formLabel">Microchip</td>
			<td><%=registro.getMicrochip()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Asl Proprietario</td>
			<td><%=registro.getAslProprietario()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Data</td>
			<td><%=toDateasString(registro.getDataRegistrazione())%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Proprietario</td>
			<td><%=registro.getProprietario()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Id Evento</td>
			<td><%=registro.getIdEvento()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Evento</td>
			<td><%=registro.getEvento()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Numero provvedimento</td>
			<td><%=registro.getIdCu()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Veterinari</td>
			<td><%=registro.getVeterinariEstesi()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Misure formative</td>
			<td><%=registro.getMisureFormative()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Misure riabilitative</td>
			<td><%=registro.getMisureRiabilitative()%></td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">Misure restrittive</td>
			<td><%=registro.getMisureRestrittive()%></td>
		</tr>
</table>

<input type="button" value="Stampa" onclick="openRichiestaPDF('PrintRegistroUnico', '<%=registro.getIdAnimale()%>',null, '-1', '-1', '<%=registro.getIdEvento() %>');" id="stampa"/>
					
					
</body>
