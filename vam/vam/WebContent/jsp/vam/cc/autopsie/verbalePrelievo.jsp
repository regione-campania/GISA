<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa"%>
<%@page import="it.us.web.bean.vam.Autopsia"%>
<%@page import="it.us.web.bean.vam.CartellaClinica"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.net.InetAddress"%>
<%@page import="it.us.web.util.properties.Application"%>

<script language="JavaScript" type="text/javascript"
	src="js/vam/cc/autopsie/detail.js"></script>

<%	
	Autopsia a 	         = (Autopsia)request.getAttribute("a");
	CartellaClinica cc = a.getCartellaClinica();
%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/stampaCC/popupDetails.js"></script>
<%@ include file="../../../../js/barcode.jsp"%>

<style>

@media all {
@page { margin-top: 5px}
.blue {
	 background-color:#e6f3ff;
	  border: 1px solid black;
	}
	.layout {
		  border: 1px solid black;
	}

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
       margin-top: 15px;
       text-align: center;
       padding-top: 5px;
       font-size: 8px; 
}
.boxOrigineDocumento {
	position: absolute;
	width: 160px;
	height: 20px;
	top: 15px;
	left: 80px;
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
line-height:1.2em;
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
border-collapse: collapse;
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

<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../../hostName.jsp" %></div>
<br/>
      
<div id="print_div" >

	<div id="intestazioneCC" style="display: block;" >
	<table class="griglia" style="margin:0 auto;" >
		<tr>
			
		<td>
			<c:choose>
				<c:when test="${a.tipoAccettazione=='Criuv'}">
					<img documentale_url="" src="images/criuv.jpg" style="height:100px"/>
				</c:when>
				<c:otherwise>
					<img documentale_url="" src="images/asl/${utente.clinica.lookupAsl}.jpg" style="height:100px"/>
				</c:otherwise>
			</c:choose>
		</td>
		
		<td>
			<c:choose>
				<c:when test="${a.tipoAccettazione=='Criuv'}">
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
				<c:when test="${a.tipoAccettazione=='Criuv'}">
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
	
	
		<fmt:setLocale value="en_US" /> <!-- ALTRIMENTI LA STAMPA NON FUNZIONA -->
		<br/>
		
		
		<table width="100%">
		<col width="50%">
		<tr><td>Prot. n. (numero accettaz/protocollo)</td>
		<td>Esame necroscopico</td></tr>
		<tr><td><b>${a.numeroAccettazioneSigla}</b></td>
		<% String barcodeNecroscopico = (String) request.getAttribute("barcodeNecroscopico");  %>
		<td><img src="<%=createBarcodeImage(barcodeNecroscopico)%>" /></td>
		</tr>
		
		</table>
		
		<br/>
		<center><h2>Verbale di Prelievo</h2></center>
		<br/>
		<table width="100%">
		<tr>
		<td> QUESITO:</td>
		<td>
		[ <c:if test="${cc.accettazione.animale.lookupSpecie.id==1}">X</c:if> <c:if test="${cc.accettazione.animale.lookupSpecie.id!=1}">&nbsp;&nbsp;&nbsp;</c:if>] Screening necroscopico cane </br>
		[ <c:if test="${cc.accettazione.animale.lookupSpecie.id==2}">X</c:if> <c:if test="${cc.accettazione.animale.lookupSpecie.id!=2}">&nbsp;&nbsp;&nbsp;</c:if>] Screening necroscopico gatto </br>
		[ <c:if test="${cc.accettazione.animale.lookupSpecie.id==3}">X</c:if> <c:if test="${cc.accettazione.animale.lookupSpecie.id!=3}">&nbsp;&nbsp;&nbsp;</c:if>] Screening necroscopico sinantropo </br> 
		</td>
		</tr>
		
		<tr>
		<td> ATTIVITA':</td>
		<td>
		
		
		<c:set var="inVam" value="${cc.accettazione.animale.decedutoNonAnagrafe==true || cc.accettazione.animale.lookupSpecie.id==3}"></c:set>
		<c:set var="statoAttualeRandagio" value="<%=((it.us.web.bean.vam.AnimaleAnagrafica)request.getAttribute("animaleAnagrafica")).getStatoAttuale().indexOf("Randagio")>=0%>"></c:set>
		[ <c:if test="${(cc.accettazione.animale.randagio==false && inVam=='true') || (statoAttualeRandagio=='false' && inVam=='false')}">X</c:if> <c:if test="${(cc.accettazione.animale.randagio==false && inVam=='true') || (statoAttualeRandagio=='false' && inVam=='false')}">&nbsp;&nbsp;&nbsp;</c:if>] Padronale </br>
		[ <c:if test="${(cc.accettazione.animale.randagio==true  && inVam=='true') || (statoAttualeRandagio=='true' &&  inVam=='false')}">X</c:if> <c:if test="${(cc.accettazione.animale.randagio==true &&  inVam=='true') || (statoAttualeRandagio=='true' &&  inVam=='false')}">&nbsp;&nbsp;&nbsp;</c:if>] Randagio </br>
		 
		</td>
		</tr>
		
		<tr>
		<td>
			<c:if test="${cc.accettazione.animale.randagio || cc.accettazione.animale.lookupSpecie.id==3}">	
				COMUNE (del ritrovamento)
			</c:if>
			<c:if test="${cc.accettazione.animale.lookupSpecie.id!=3 && !cc.accettazione.animale.randagio}">	
				COMUNE (del proprietario)
			</c:if>
		</td>
		<td>
		
		
		<c:if test="${cc.accettazione.animale.randagio || cc.accettazione.animale.lookupSpecie.id==3}">	
		<c:out value="${cc.accettazione.animale.comuneRitrovamento.description}"/>
		</c:if>
		<c:if test="${cc.accettazione.animale.lookupSpecie.id!=3 && !cc.accettazione.animale.randagio}">	
		<c:out value="${cc.accettazione.proprietarioComune}"/>
		</c:if>
		</td>
		</tr>
		
		<tr>
		<td>
		Data Richiesta Necroscopico
		</td>
		<td><fmt:formatDate type="date" value="${a.dataAutopsia}"
				pattern="dd/MM/yyyy" var="dataAutopsia" /> ${dataAutopsia}</td>
		</tr>
				
		</table>
		
		
		<h3>Dati anagrafici animale</h3>
		<table width="100%">
		<col width="25%"><col width="25%"><col width="25%"><col width="25%">
		<tr><td><i>Identificativo</i></td>
			<td align="left">
    		<img src="<%=createBarcodeImage(cc.getAccettazione().getAnimale().getIdentificativo())%>" />
    		</td>  
		<td><i>Specie</i></td> 
	<td><c:out value="${cc.accettazione.animale.lookupSpecie.description}" /></td>
		</tr>
		
		<tr>
<td><i>
    		<c:if test="${cc.accettazione.animale.lookupSpecie.id!=3}">
    			Razza
    			</c:if>
    		<c:if test="${cc.accettazione.animale.lookupSpecie.id==3}">
    			Famiglia/Genere
    			</c:if>
    			
    			</i></td><td><c:if test="${(cc.accettazione.animale.lookupSpecie.id == specie.cane or cc.accettazione.animale.lookupSpecie.id == specie.gatto) && cc.accettazione.animale.decedutoNonAnagrafe==false}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe==true}">${anagraficaAnimale.razza}</c:if>
				<c:if test="${cc.accettazione.animale.lookupSpecie.id == specie.sinantropo && cc.accettazione.animale.decedutoNonAnagrafe==false}">${cc.accettazione.animale.specieSinantropoString} - ${cc.accettazione.animale.razzaSinantropo}</c:if>
    		   </td>  	

		<td><i>Sesso</i></td> 
		<td>${cc.accettazione.animale.sesso }</td>
		</tr>
		
			<tr><td><i>Taglia</i></td>
		<td>${anagraficaAnimale.taglia}</td>
		<td><i>Mantello</i></td> 
		<td>${anagraficaAnimale.mantello}</td>
		</tr>
		
			<tr>
<c:choose>
<c:when test="${cc.accettazione.animale.lookupSpecie.id==3}">
<td><i>Età</i></td>
<td><fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${cc.accettazione.animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
</td>
</c:when>
<c:otherwise>
<td><i>Data nascita</i></td>
<td><fmt:formatDate type="date" value="${cc.accettazione.animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"/>
${dataNascita}</td>
</c:otherwise></c:choose> 

</tr>
		
		<tr>
			<td><i>Probabile causa del decesso</i>
			</td>
		<td>
			<c:choose>
	    		<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    			${cc.accettazione.animale.causaMorte.description}
	    		</c:when>
	    		<c:otherwise>
	    			${res.decessoValue}
	    		</c:otherwise>
	    	</c:choose>
	   	 </td>
		 </tr>		
	</table>
<br/>
<c:if test="${cc.accettazione.animale.decedutoNonAnagrafe==true && cc.accettazione.randagio==true}">	
	<table width="100%">
	<tr>
	<td>
		<h3>Ritrovamento carcassa</h3>
	</td>
	</tr>	
		<tr><td><i>Comune</i></td>
			<td align="left">
    		${cc.accettazione.animale.comuneRitrovamento.description}
    		</td>  
		<td><i>Provincia</i></td> 
	<td>${cc.accettazione.animale.provinciaRitrovamento}</td>
		</tr>
		<tr><td><i>Indirizzo</i></td>
			<td align="left">
    		${cc.accettazione.animale.indirizzoRitrovamento}
    		</td>  
		<td><i>Note</i></td> 
	<td>${cc.accettazione.animale.noteRitrovamento}</td>
		</tr>
</table>
</c:if>		
		
		<%
	    				Iterator<Entry<String, Set<String>>> iter = a.getDettaglioEsamiForJspDetail().entrySet().iterator();
	    				if (iter.hasNext()){ %>
	    				<br/>
			<center><h2>Esami richiesti</h2></center>


			<table width="100%" class="bordo">
			<col width="15%"><col width="15%"><col width="40%"><col width="30%">
				<tr>
					<td class="blue"><b><i>Organo</b></i></td>
					<td class="blue"><b><i>Tipologia</b></td>
					<td class="blue"><b><i>Dettaglio Richiesta</i></b></td>
					<td class="blue"><b><i>Esiti</i></b></td>
					<!--td class="blue"><b><i>Identificativo campione</i></b></td-->
				</tr>

				<c:set var="i" value="1" />
				<% while(iter.hasNext())
	    				{
	    					Entry<String, Set<String>> e = iter.next();
	    			%>
				<tr>
					<td><%=e.getKey().split(";")[0].split("---")[1]%> <input
						type="hidden" id="organo${i}"
						value="<%=e.getKey().split(";")[0].split("---")[0]%>" /> <input
						type="hidden" id="organoDesc${i}"
						value="<%=e.getKey().split(";")[0].split("---")[1]%>" /></td>
					<td><%=e.getKey().split(";")[1].split("---")[1]%> <input
						type="hidden" id="tipo${i}"
						value="<%=e.getKey().split(";")[1].split("---")[0]%>" /> <input
						type="hidden" id="tipoDesc${i}"
						value="<%=e.getKey().split(";")[1].split("---")[1]%>" /></td>
					<td><%=((e.getKey().split(";").length>2)?(e.getKey().split(";")[2]):(""))%>
					</td>
					<td><input type="hidden" name="esitiTemp${i}"
						id="esitiTemp${i}" value="<%=e.getValue()%>" /> <input
						type="hidden" name="esiti${i}" id="esiti${i}"
						value="<%=e.getValue()%>" /> <%=e.getValue()%></td>
					<!--td class="layout">&nbsp;&nbsp;&nbsp; <img src="<%=createBarcodeImage("null")%>" /></td-->
				</tr>
				<c:set var="i" value="${i+1}" />
<%
	    			} %>
	    			</table>
<%
	    			}
	    				
	    				if(a.getDettaglioEsamiForJspDetail().entrySet().isEmpty())
	    				{
%>
							<table width="100%" class="bordo">
								<col width="15%"><col width="15%"><col width="40%"><col width="30%">
								<tr>
									<td colspan="4" class="blue"><b><i>Non sono stati richiesti esami per questo esame necroscopico</b></i></td>
								</tr>
							</table>
<%
	    				}
%>		
		<br/>		
		<table width="100%">
		<col width="50%">
		<tr><td><center>Data</center></td>
		<td><center><b>Il Veterinario Dirigente</b></center></td></tr>
		<tr><td><center><fmt:formatDate type="date" value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy"/></center></td>
	 <td><center>_______________________</center></td></tr>
        	
	
	
	</table>

		
	<br/>	
		
		
		
		</div>
		
		
		
		
		