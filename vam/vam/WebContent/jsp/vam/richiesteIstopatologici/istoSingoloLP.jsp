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
<%@ include file="../../../js/barcode.jsp"%>

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>
<!-- <h4 class="titolopagina">
     Cartella Clinica
</h4>  -->
<br/>

<%

EsameIstopatologico esame = (EsameIstopatologico)request.getAttribute("esame");
%>
	
			<table width="100%" style="border-collapse: collapse">
		  <col width="50%">  <col width="50%">
    	<tr>
    		<td style="padding:10px" align="right">
    			<img src="<%=createBarcodeImage(esame.getAnimale().getIdentificativo())%>" />
    		</td>
    		<td style="padding:10px" align="center">
    		<img src="<%=createBarcodeImage(esame.getNumero())%>" />
    		</td>  
    		<td>
    		</td>  		
        </tr>
        </table>
        
	<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
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
<td> ${esame.peso} </td>
<td><i> Habitat </i></td>
<td>${esame.cartellaClinica.lookupHabitats}</td>
</tr>
<tr>
<td><i>Alimentazione</i></td>
<td>${esame.lookupAlimentazionis}</td>
</tr>

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
<td><i>Numero rif. mittente</i></td>
<td>${esame.numeroAccettazioneSigla}  </td>
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
<td colspan="2"><b>Risultato</b></td>
</tr>
<tr>
<tr>
<td><i>Descrizione Morfologica</i></td>
<td>${esame.descrizioneMorfologica }    </td>
</tr>
<tr>
<td><i>Diagnosi</i></td>
<td>${esame.tipoDiagnosi.description }: ${esame.whoUmana } ${esame.diagnosiNonTumorale }  </td>
</tr>
</table>	
	
</br></br>		

<div align="right"><b>Timbro e firma del veterinario</b></div>
		
	</div>
  	