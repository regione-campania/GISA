<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<link rel="stylesheet" documentale_url="" href="jsp/vam/cc/print.css" type="text/css" media="print" />

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>

<style>

@media all {

.title1 {
	font-size: 14px;
	text-decoration: underline;
	text-align: center;
	margin-top: 70px;
}
.nodott {
	margin-top: 0px;
	margin-right: 4px;
	float: left;
}

.Section1 {
	color: black;
	position: relative;
	border: 0px solid red;
}

.dott {
	width: 180px;
	margin-top: 50px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
}
.nodott_margin_low {
	margin-top: 10px;
	margin-right: 4px;
	float: left;
	font-size: 14px;
}

.dott_margin_low {
	width: 180px;
	margin-top: 10px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
	font-size: 12px;
	font-weight: lighter;
}

.dott_long_margin_low {
	width: 380px;
	margin-top: 10px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
	font-size: 12px;
	font-weight: lighter;
}
.dott_short_margin_low {
	width: 20px;
	margin-top: 10px;
	margin-left: 0px;
	border-bottom: 1px dashed #000;
	float: left;
	font-size: 12px;
	font-weight: lighter;
}
.nodott {
	margin-top: 50px;
	margin-right: 4px;
	font-size: 14px;
}

.clear1 {
	clear: both;
}

.firma {
	position: absolute;
	right: 25px;
	font-size: 12px;
}

.firmavalore {
	position: absolute;
	right: 25px;
	border-bottom: 1px dashed #000;
	margin-top: 30px;
	width: 150px;
}
.data {
	position: absolute;
	left: 25px;
	font-size: 12px;
}

.datavalore {
	position: absolute;
	left: 25px;
	border-bottom: 1px dashed #000;
	margin-top: 30px;
	width: 150px;
}

.imgAsl {
	position: absolute;
	right: 10px;
	border: 0px solid black;
	width: 80px;
	height: 80px;
	margin-bottom: 200px;
}

.imgRegione {
	position: absolute;
	left: 10px;
	border: 0px solid black;
	width: 80px;
	height: 80px;
	margin-bottom: 200px;
}


.boxIdDocumento {
	position: absolute;
	left: 100px;
	border: 0.5px solid black;
	width: 60px;
	height: 20px;
	margin-top: 20px;
	text-align: center;
	padding-top: 5px;
	font-size: 8px;
}

.boxOrigineDocumento {
	position: absolute;
	left: 100px;
	width: 160px;
	height: 20px;
	margin-top: 45px;
	text-align: left;
	padding-top: 10px;
	font-size: 9px;
}

}
	
</style>

<%

Accettazione accettazione = (Accettazione)request.getAttribute("accettazione");
%>

<%-- <body onload="javascript:closeAndRefresh('<%= request.getAttribute("chiudi")%>','<%= request.getAttribute("redirect")%>')">--%>
<body>
<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
 <br>
<c:set var="tipo" scope="request" value="stampaVerbaleAccompagnamentoCampioni" />
<c:import url="../../jsp/documentale/home.jsp"/>
<br>
<br>
<div class="imgRegione">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="images/asl/regionecampania.jpg" />
</div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
	
<div class="imgAsl">
<img style="text-decoration: none;" width="80" height="80" documentale_url="" src="images/asl/${utente.clinica.lookupAsl}.jpg" />
</div>

<div class="Section1">
 </br>
<div class="title1">SARS-CoV-2 SORVEGLIANZA IN ANIMALI D'AFFEZIONE E SINANTROPI PIANO B79</div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;


<table style="border: 1px solid black;" cellspacing="0" width="100%">
	<tr>	
		<td style="border: 1px solid black;text-align: center;" cellspacing="0" colspan="2">
			<b>TIPOLOGIA CAMPIONE</b>
		</td>
	</tr>

<% 
	ArrayList<LookupOperazioniAccettazione> operazioni = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioni");
	Iterator i = operazioni.iterator(); 
	while (i.hasNext())
	{
		LookupOperazioniAccettazione loa = (LookupOperazioniAccettazione)i.next();
%>
		<tr>
			<td style="border: 1px solid black;" cellspacing="0" width="30%">
				<%=loa.getDescription().toUpperCase()%>
			</td>
			<td style="border: 1px solid black;" cellspacing="0"  width="70%">
<%
				if(accettazione.contain(loa.getId()))
					out.println("X");
%>
			</td>
		</tr>		
<%
	}
%>	

	<tr>
		<td style="border: 0px solid black;" cellspacing="0" colspan="2">&nbsp;</td>
	</tr>					
	<tr>	
		<td style="border: 1px solid black;text-align: center;" cellspacing="0" colspan="2">
			<b>SEGNALAMENTO</b>
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			SPECIE
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.animale.lookupSpecie.description}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			DATA PRELIEVO
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			DATA DI NASCITA
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			<fmt:formatDate type="date" value="${accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
			${dataNascita}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			SESSO
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.animale.sesso}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			<c:if test="${accettazione.animale.lookupSpecie.id!=3}">
				RAZZA
			</c:if>
			<c:if test="${accettazione.animale.lookupSpecie.id==3}">
				FAMIGLIA/GENERE
			</c:if>
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			<c:if test="${(accettazione.animale.lookupSpecie.id == specie.cane or accettazione.animale.lookupSpecie.id == specie.gatto) && accettazione.animale.decedutoNonAnagrafe==false}">
				${anagraficaAnimale.razza}
			</c:if>
			<c:if test="${accettazione.animale.decedutoNonAnagrafe==true}">
				${anagraficaAnimale.razza}
			</c:if>
			<c:if test="${accettazione.animale.lookupSpecie.id == specie.sinantropo && accettazione.animale.decedutoNonAnagrafe==false}">
				${accettazione.animale.specieSinantropoString} - ${accettazione.animale.razzaSinantropo}
			</c:if>
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			TAGLIA
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${anagraficaAnimale.taglia}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			ASL ANIMALE
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.enteredBy.clinica.lookupAsl.description}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			ASL PRELEVATORE
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.enteredBy.clinica.lookupAsl.description}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			IDENTIFICATIVO
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.animale.identificativo}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			ANAMNESI
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			SINTOMATOLOGIA
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			COMUNE CATTURA/RITROVAMENTO
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${comunecattura}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			ALTRE OSSERVAZIONI
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0" colspan="2">
			LA FIALA DEVE ESSERE IDENTIFICATA CON IL NUMERO DEL MICROCHIP
		</td>
	</tr>
	<tr>
		<td style="border: 0px solid black;" cellspacing="0" colspan="2">&nbsp;</td>
	</tr>
	<tr>	
		<td style="border: 1px solid black;text-align: center;" cellspacing="0" colspan="2">
			<b>PROPRIETARIO</b>
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			COGNOME 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.proprietarioCognome}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			NOME 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.proprietarioNome}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			INDIRIZZO 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.proprietarioIndirizzo}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			CAP - CITTA' 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.proprietarioCap}
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			COMUNE 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.proprietarioComune}
		</td>
	</tr>
	<tr>
		<td style="border: 0px solid black;" cellspacing="0" colspan="2">&nbsp;</td>
	</tr>
	<tr>	
		<td style="border: 1px solid black;text-align: center;" cellspacing="0" colspan="2">
			<b>VETERINARIO</b>
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			COGNOME 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			NOME 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			ASL 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			
		</td>
	</tr>
	<tr>
		<td style="border: 1px solid black;" cellspacing="0">
			STRUTTURA 
		</td>
		<td style="border: 1px solid black;" cellspacing="0">
			${accettazione.enteredBy.clinica.nome}
		</td>
	</tr>
	
	
</table>

<br/><br/><br/>						

<div class="data">DATA</div>
<jsp:useBean id="now" class="java.util.Date"/>    
<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="dataOra"/>
<div class="datavalore">&nbsp;${dataOra}</div>
<div class="firma">TIMBRO E FIRMA DEL VETERINARIO</div>

<div class="firmavalore">&nbsp;</div>


</div>
<br/>
</body>

