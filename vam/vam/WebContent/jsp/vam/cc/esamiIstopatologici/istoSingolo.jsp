<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<link rel="stylesheet" documentale_url="" href="jsp/vam/cc/print.css" type="text/css" media="print" />

<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<%@page import="it.us.web.bean.vam.TerapiaDegenza"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="it.us.web.bean.vam.Diagnosi"%>
<%@page import="it.us.web.bean.vam.Trasferimento"%>
<%@page import="it.us.web.bean.vam.TipoIntervento"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.EsameIstopatologico"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page
	import="java.util.Date"%>


<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.us.web.util.properties.Application"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>

<style>

@media all {
	table.fondo {
position: absolute;
	font-size: 12px;
	margin-top: 650px;
	width:100%;
}
div.header {
	display:none;
}
.stampaSezione {
display:none;
}
.coloreIdentificativo {
	color:#000000;
}
.boxIdDocumento {  
       border: 1px solid black;
       width: 60px;
       height: 20px;
       margin-top: 30px;
       text-align: center;
       padding-top: 5px;
       font-size: 8px; 
}
.boxOrigineDocumento {
	position: absolute;
	width: 160px;
	height: 20px;
	top: 15px;
	left: 70px;
	text-align: left;
	padding-top: 10px;
	font-size: 8px;
}
table.tabella
{
	-webkit-print-color-adjust:exact;
	background-color:#E5EAE7;
	font-size:16px;
	margin:0.5%;
	width:94%;	
	border-collapse: collapse;
	
}

th
{
	background:#A8C4DC;
	color:#000044;
	font-weight:bold;
	text-align:center;
	padding: 0px;
	border: none;	
}

th.sub
{
	background:#CCCCCC;
	color:#000044;
	font-weight:bold;
	text-align:center;
	padding: 0px;
	border: none;	
}
#divTerap, #divChir {
page-break-before: always;
}


tr.odd {background-color: #FFFFFF}
tr.even {background-color: #E5EAE7;}

table.griglia  {
width:100%;
background-color:#000;
}
table.griglia td {
background-color:#FFF;

}

table.grigliaEsami  {
background-color:#000;
}
table.grigliaEsami td {
background-color:#FFF;

}

table {
line-height:1.8em;
}

.underline {
border-bottom:1px solid;
}
.esamiSangue tr td{
	font-size: 0.8em;
}
}

body{
	-webkit-print-color-adjust:exact;
    font-family: Trebuchet MS,Verdana,Helvetica,Arial,san-serif;	
}

p.intestazione{
	background: none repeat scroll 0 0 #A8C4DC;
    border: medium none;
    color: #000044;
    font-weight: bold;
    padding: 0;
    text-align: center;
    width: 50%;
    text-align:left;
    
}
.pagebreak, #divChir {
page-break-after:always;

}

#intestazione table td img {
	heigth:100px;
}
h2 {
	border:none !important;
	text-decoration: underline;
	display:inline;
}
table td h3 {
	display:inline
	}
table.griglia  {
width:100%;
background-color:#000;
}
table.griglia td {
background-color:#FFF;
text-align:center;
}

table.grigliaEsami  {
background-color:#000;
}
table.grigliaEsami td {
background-color:#FFF;
text-align:center;
}

table.bordo {
border:1px solid;
width:100%;
}
.esamiSangue {
	font-size: 10px;
	}
	table.fondo {

position: absolute;
	font-size: 12px;
	width:98%;
}

	
</style>
<%@ include file="barcode.jsp"%>
<%

EsameIstopatologico esame = (EsameIstopatologico)request.getAttribute("esame");
%>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>
<!-- <h4 class="titolopagina">
     Cartella Clinica
</h4>  -->
<br/>
<c:set var="tipo" scope="request" value="stampaAcc" />
<c:import url="../../jsp/documentale/home.jsp"/>
      
<div id="print_div" >

	<div id="intestazioneCC" style="display: block;" >
	<table class="griglia" style="margin:0 auto;" >
		<tr>
			
		<td>
			<c:choose>
				<c:when test="${esame.tipoAccettazione=='Criuv'}">
					<img documentale_url="" src="images/criuv.jpg" style="height:100px"/>
				</c:when>
				<c:otherwise>
					<img documentale_url="" src="images/asl/${utente.clinica.lookupAsl}.jpg" style="height:100px"/>
				</c:otherwise>
			</c:choose>
		</td>
		
		<td>
			<c:choose>
				<c:when test="${esame.tipoAccettazione=='Criuv'}">
					<h3>CRIUV</h3><br/>
					Via M.R. di Torrepadula - P.O. Frullone - Plesso Ulisse<br/>
					80143 Napoli<br/>
					Tel.  0812549555/52/56/58 - Fax 0812548740<br/>
					email: criuv@regione.campania.it
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${utente.clinica.lookupAsl.id==204}">
							<h3>ASL NAPOLI 1 CENTRO - SSD EPIDEMIOLOGIA VETERINARIA</h3><br/>
							Via M.R. di Torrepadula - P.O. Frullone - Plesso Ulisse<br/>
							80143 Napoli<br/>
							Tel.  081-2549555 - Fax 081- 2548740<br/>
							email: epidevet@aslnapoli1centro.it                       
						</c:when>
						<c:otherwise>
							<h3>${utente.clinica.nome }</h3><br/>
							${utente.clinica.indirizzo }<br/>
							${utente.clinica.lookupComuni.description }
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>
		<td>
			<c:choose>
				<c:when test="${esame.tipoAccettazione=='Criuv'}">
					<b><i>Cod. SIGLA 282961</i></b>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${utente.clinica.lookupAsl.id==204}">
							<b><i>Cod. SIGLA 283779</i></b>
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>			
		</tr>
	
	</table></div>
	
			<table width="100%" style="border-collapse: collapse">
		  <col width="40%">  <col width="30%"> <col width="30%">
		  
		  
		  
		  
		  <tr>
<td><i></td>
</tr>
<tr>
	<td style="padding:10px" align="right">
		<i>Numero rif. mittente Asl:  </i> ${esame.numeroAccettazioneSigla}
	</td>
	<td style="padding:10px" align="right">
		<img src="<%=createBarcodeImage(esame.getAnimale().getIdentificativo())%>" />
	</td>
	<td style="padding:10px" align="center">
		<img src="<%=createBarcodeImage(esame.getNumero())%>" />
	</td>  
</tr>
</table>
	
	
	
	
	
    			<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
    			<fmt:formatDate type="date" value="${esame.getCartellaClinica().getAccettazione().data}" pattern="dd/MM/yyyy" var="dataApertura"/>
	<center><h2>Richiesta Esame Istopatologico</h2></center>
	
		<br/>
		
		<center><h2>Dati del ${esame.animale.lookupSpecie.description}</h2></center>
	<table class="bordo"> <col width="25%"><col width="25%"><col width="25%"><col width="25%">
<tr>
<td><i>Identificativo</i></td>
<td>${esame.animale.identificativo}</td>

<td><i>
    		<c:if test="${esame.animale.lookupSpecie.id!=3}">
    			Razza
    			</c:if>
    		<c:if test="${esame.animale.lookupSpecie.id==3}">
    			Famiglia/Genere
    			</c:if>
    			
    			</i></td><td><c:if test="${(esame.animale.lookupSpecie.id == specie.cane or esame.animale.lookupSpecie.id == specie.gatto) && esame.animale.decedutoNonAnagrafe==false}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${esame.animale.decedutoNonAnagrafe==true}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${esame.animale.lookupSpecie.id == specie.sinantropo && esame.animale.decedutoNonAnagrafe==false}">${esame.animale.specieSinantropoString} - ${esame.animale.razzaSinantropo}</c:if>
    		   </td>  		
</tr>
<c:if test="${esame.animale.lookupSpecie.id!=3}">
	<tr>
		<td>
			Mantello
		</td>
		<td colspan="3">
			${anagraficaAnimale.mantello}
		</td>
	</tr>
</c:if>
<tr>
<td><i>Sesso</i></td>
<td>${esame.animale.sesso}</td>

<c:choose>
<c:when test="${esame.animale.lookupSpecie.id==3}">
<td><i>Età</i></td>
<td><fmt:formatDate type="date" value="${esame.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${esame.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
</td>
</c:when>
<c:otherwise>
<td><i>Data nascita</i></td>
<td><fmt:formatDate type="date" value="${esame.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${dataNascita}</td>
</c:otherwise></c:choose> 

</tr>
<tr>
<td><i>Peso (in Kg)</i></td>
<td> ${esame.cartellaClinica.peso} </td>
<td><i> Habitat </i></td>
<td>${esame.cartellaClinica.lookupHabitats}</td>
</tr>
<tr>
<td><i>Alimentazione</i></td>
<td>${esame.cartellaClinica.lookupAlimentazionis}</td>
</tr>

</table>	
	
</br></br>

<c:if test="${esame.cartellaClinica.accettazione.animale.decedutoNonAnagrafe==true && esame.cartellaClinica.accettazione.randagio==true}">	
	<center><h2>Ritrovamento carcassa</h2></center>
	<table width="100%" class="bordo">
		<col width="25%"><col width="25%">
	<tr>
	<td><i>Comune</i></td>
	<td>${esame.cartellaClinica.accettazione.animale.comuneRitrovamento.description}</td>
	<td><i>Provincia</i></td>
	<td>${esame.cartellaClinica.accettazione.animale.provinciaRitrovamento}</td>
	</tr>
	<tr>
	<td><i>Indirizzo</i></td>
	<td>${esame.cartellaClinica.accettazione.animale.indirizzoRitrovamento}</td>
	<td><i>Note</i></td>
	<td>${esame.cartellaClinica.accettazione.animale.noteRitrovamento}</td>
	</tr>
</table>
<br/><br/>
</c:if>
		
<center><h2>Dati del proprietario</h2></center>
<table class="bordo"> <col width="25%"><col width="25%"><col width="25%"><col width="25%">
	<c:if test="${esame.cartellaClinica.accettazione.proprietarioTipo=='Privato' || esame.cartellaClinica.accettazione.proprietarioTipo=='Sindaco'}">
	<tr>
<td><i>Cognome</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCognome}</td>
<td><i>Nome</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioNome}</td>
</tr>
<tr>
<td><i>Codice fiscale</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCodiceFiscale}</td>
<td><i>Documento</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioDocumento}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>
	<c:if test="${esame.cartellaClinica.accettazione.proprietarioTipo=='Canile'}">
<tr>
<td><i>Ragione sociale</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioNome}</td>
<td></td>
<td></td>
</tr>
<tr>
<td><i>Partita IVA</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCognome}</td>
<td><i>Rappr. Legale</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCodiceFiscale}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>

<c:if test="${esame.cartellaClinica.accettazione.proprietarioTipo=='Colonia'}">
<tr>
<td><i>Nome colonia</i></td>
<td>${esame.cartellaClinica.accettazione.nomeColonia}</td>
<td></td>
<td></td>
</tr>
<tr>
<td><i>Nominativo referente</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioNome}</td>
<td><i>Codice fiscale referente</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCodiceFiscale}</td>
</tr>
<tr>
<td><i>Indirizzo</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioIndirizzo}</td>
<td><i>CAP</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioComune}</td>
<td><i>Provincia</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>


<c:if test="${esame.cartellaClinica.accettazione.proprietarioTipo=='Importatore' || esame.cartellaClinica.accettazione.proprietarioTipo=='Operatore Commerciale'}">
<tr>
<td><i>Ragione sociale</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioNome}</td>
<td><i>Partita IVA</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCognome}</td>
</tr>
<tr>
<td><i>Indirizzo sede operativa</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioIndirizzo}</td>
<td><i>CAP sede operativa</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioCap}</td>
</tr>
<tr>
<td><i>Comune sede operativa</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioComune}</td>
<td><i>Provincia sede operativa</i></td>
<td>${esame.cartellaClinica.accettazione.proprietarioProvincia}</td>
</tr>
</c:if>

</table>	
		</br></br>
		
	<center><h2>Dati dell'esame</h2></center>
	<table class="bordo"> <col width="25%">
<tr>
<td><i>Data della richiesta</i></td>
<td><fmt:formatDate type="date" value="${esame.dataRichiesta }" pattern="dd/MM/yyyy" var="dataRichiesta"/>
${dataRichiesta}</td>
</tr>
<tr>
<td><i>Laboratorio destinazione</i></td>
<td>${esame.lass.description}</td>
</tr>
<tr>
<td><i>Tipo prelievo</i></td>
<td>${esame.tipoPrelievo.description }</td>
</tr>
<tr>
<td><i>Tumori precedenti</i></td>
<td>	${esame.tumoriPrecedenti.description }  </td>
</tr>

 <c:if test="${esame.tumoriPrecedenti.id == 2 }">
        <tr>
    		<td><i>
    			 T
    		</i></td>
    		<td> 
    			${esame.t }  
    		</td>
        </tr>    
        
        <tr>
    		<td><i>
    			 N
    		</i></td>
    		<td> 
    			${esame.n }  
    		</td>
        </tr>  
        
        <tr>
    		<td><i>
    			 M
    		</i></td>
    		<td>  
    			${esame.m }  
    		</td>
        </tr> 
    </c:if>
<tr>
<td><i>Dimensione (in cm)</i></td>
<td>${esame.dimensione }</td>
</tr>
<tr>
<td><i>Interessamento linfondale</i></td>
<td>${esame.interessamentoLinfonodale.description }  </td>
</tr>
<tr>
<td><i>Sede lesione e sottospecifica</i></td>
<td>${esame.sedeLesione }  </td>
</tr>
<tr>
<td><i>Numero riferimento mittente</i></td>
<td>${esame.enteredBy.superutente.siglaProvincia } ${esame.enteredBy.superutente.numIscrizioneAlbo }  </td>
</tr>


</table>	
	
</br></br>		

<div align="right"><b>Timbro e firma del veterinario</b></div>
		
	</div>
<br/>
	
  	